/*
Copyright 2021 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

const express = require("express");
const router = express.Router();
const { v4: uuidv4 } = require("uuid");
const {
  customersApi,
  giftCardsApi,
  giftCardActivitiesApi,
  cardsApi
} = require("../util/square-client");

const locationId = process.env[`SQUARE_LOCATION_ID`];
const faker = require("faker");
const { checkLoginStatus, checkCustomerIdMatch, checkSandboxEnv } = require("../util/middleware");
const REFERENCE_ID = "GiftCardSampleApp";

/**
 * POST /seed/:customerId/create-card
 *
 * This endpoint creates a card on file (i.e. a credit card) for the customerId specified.
 * It will first verify that the customer is logged in, and that the ID of the customer logged in
 * is the same as the customer passed in as a path parameter.
 * This operation is only allowed in sandbox environment.
 */
router.post("/:customerId/create-card", checkLoginStatus, checkCustomerIdMatch, checkSandboxEnv, async (req, res, next) => {
  try {
    const customerId = req.params.customerId;
    const gan = req.query.gan;

    const createCardRequest = generateCreateCardRequest(customerId);
    await cardsApi.createCard(createCardRequest);

    // There is a small lag between card creation and when this card is available through listCards.
    // so, sleep for 4.5 seconds to be safe.
    await new Promise(r => setTimeout(r, 4500));
    // Redirect to the add-funds page, which will now present the newly created card.
    res.redirect("/gift-card/" + gan + "/add-funds/?cardCreated=success");
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * POST /seed/create-customer
 *
 * This endpoint creates a test customer that can be used in the sample app.
 */
router.post("/create-customer", async (req, res, next) => {
  try {
    const [givenName, familyName] = faker.name.findName().split(" ");

    // Create a customer with a fake name.
    const { result: { customer } } = await customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName,
      familyName,
      referenceId: REFERENCE_ID
    });

    // Since listCustomers endpoint has a delay, we want to store this newly
    // created customerId temporarily.
    if (!req.session.missingCustomers) {
      req.session.missingCustomers = [];
    }

    req.session.missingCustomers.push(
      {
        id: customer.id,
        givenName: customer.givenName,
        familyName: customer.familyName
      }
    );

    res.redirect("/login?customerCreated=success&"
      + "givenName=" + customer.givenName + "&"
      + "familyName=" + customer.familyName);
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * POST /seed/reset
 *
 * This endpoint resets all data changes that were introduced using this app.
 * This includes customers, cards on file, and gift cards.
 */
router.post("/reset", async (req, res, next) => {
  try {
    // Get all customers with the reference ID of our sample app.
    // These are the customers we want to delete.
    const searchCustomersRequest = generateSearchCustomersRequest();
    let { result: { customers } } = await customersApi.searchCustomers(searchCustomersRequest);

    if (!customers) {
      customers = [];
    }

    let customersToDelete = customers.map(customer => customer.id);

    // Check if there are any missing customers that need to be added to the list of available customers.
    if (req.session.missingCustomers) {
      req.session.missingCustomers = req.session.missingCustomers
        .filter(missingCustomer => !customersToDelete.includes(missingCustomer.id));

      // Those who aren't available yet, should be appended to our retrieved list of customers.
      customersToDelete = customersToDelete.concat(req.session.missingCustomers.map(customer => customer.id));
    }

    if (customersToDelete.length > 0) {
      // Make request to get gift cards associated with each customer to be deleted.
      const giftCardsPromises = customersToDelete.map(id => giftCardsApi.listGiftCards(undefined, undefined, undefined, undefined, id));

      const data = await Promise.all(giftCardsPromises);

      // Get all gift card objects related to customers we are about to delete.
      const set = new Set();
      const uniqueGiftCardObjects = data
        .filter(body => body.result.giftCards)
        .flatMap(body => body.result.giftCards)
        .filter(giftCard => {
          const exists = set.has(giftCard.id);
          set.add(giftCard.id);
          return !exists;
        });

      // Make requests to unlink cards from soon-to-be deleted customers.
      const toDeactivate = [];
      const unlinkPromises = uniqueGiftCardObjects.flatMap(giftCard => {
        // get all common IDs between the gift card owners and the customers to be deleted.
        // these are the ones we have to unlink.
        const idsToUnlink = customersToDelete.filter(customerId => giftCard.customerIds.includes(customerId));
        if (idsToUnlink.length == giftCard.customerIds.length && giftCard.state === "ACTIVE") {
          // If all of the owners are customers to be deleted AND the card is active, we also
          // mark it for deactivation.
          toDeactivate.push(giftCard.gan);
        }

        return idsToUnlink.map(customerId => giftCardsApi.unlinkCustomerFromGiftCard(giftCard.id, {
          customerId: customerId
        })
        );
      });

      // Unlink soon to be deleted customers.
      await Promise.all(unlinkPromises);

      // Make requests to deactivate all marked GANs.
      const giftCardDeactivationPromises = toDeactivate.map(gan => {
        const giftCardDeactivationRequest = generateGiftCardDecativationRequest(gan);
        return giftCardActivitiesApi.createGiftCardActivity(giftCardDeactivationRequest);
      });

      // Make requests to delete customers.
      const customerDeletionPromises = customersToDelete.map(id => customersApi.deleteCustomer(id));

      // Deactivate all gift cards.
      await Promise.all(giftCardDeactivationPromises);

      // Delete all customers.
      await Promise.all(customerDeletionPromises);

      // Reset our "cache".
      req.session.missingCustomers = [];
    }

    res.redirect("/login?reset=success");
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * Helper function to build a `search customers` API request payload.
 */
function generateSearchCustomersRequest() {
  return {
    query: {
      filter: {
        referenceId: {
          exact: REFERENCE_ID
        }
      }
    }
  }
}

/**
 * Helper function to build a `gift card activity` API request payload to deactivate a gift card.
 */
function generateGiftCardDecativationRequest(gan) {
  return {
    idempotencyKey: uuidv4(),
    giftCardActivity: {
      giftCardGan: gan,
      locationId,
      type: "DEACTIVATE",
      deactivateActivityDetails: {
        // In the future we will support other reasons.
        reason: "SUSPICIOUS_ACTIVITY"
      }
    }
  }
}

/**
 * Helper function to build a `create card` API request payload to create a test card on file.
 */
function generateCreateCardRequest(customerId) {
  return {
    idempotencyKey: uuidv4(),
    sourceId: "cnon:card-nonce-ok",
    card: {
      customerId
    }
  }
}

module.exports = router;
