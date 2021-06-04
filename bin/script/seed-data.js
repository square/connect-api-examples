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

const { Client, ApiError } = require("square");
require('dotenv').config();
const readline = require("readline");
const { v4: uuidv4 } = require("uuid");

const env = process.env[`ENVIRONMENT`].toLowerCase();
const accessToken = process.env[`SQUARE_ACCESS_TOKEN`];
const locationId = process.env[`SQUARE_LOCATION_ID`];
const referenceId = "GiftCardSampleApp"

// Only run the script if the application is in sandbox environment.
if (env !== "sandbox") {
  console.error(
    "This script should only run in sandbox environment. It is not intended to be used in production."
    + "\nPlease check your .env file."
  );
  return;
}

// Client configuration
const config = {
  accessToken,
  environment: env
};

const {
  customersApi,
  giftCardsApi,
  giftCardActivitiesApi
} = new Client(config);

/**
 * Function to create a couple of fake customers (with card on file), so that those can be used
 * throughout the sample app.
 */
async function createCustomers() {
  try {
    const customer1Promise = customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Michael",
      familyName: "Scott",
      emailAddress: "michaelscott123@example.com",
      referenceId: referenceId
    });

    const customer2Promise = customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Dwight",
      familyName: "Schrute",
      emailAddress: "dwightschrute@beetfarm.com",
      referenceId: referenceId
    });

    const data = await Promise.all([customer1Promise, customer2Promise]);

    await Promise.all(data.map(body => {
      return customersApi.createCustomerCard(body.result.customer.id, {
        cardNonce: "cnon:card-nonce-ok"
      });
    }));

    console.log("Successfully created two customers");
  } catch (error) {
    if (error instanceof ApiError) {
      const errors = error.errors;
      console.error("Create customers failed: ", JSON.stringify(errors));
    } else {
      console.error("Create customers failed: ", error);
    }
  }
}

/**
 * Function to clear customers created using the seeding script.
 *
 * The steps taken are:
 * 1. Get all the customers to be deleted based on the reference_id for our app
 * 2. Get all gift cards associated with each customer (there may be duplicates b/c multiple ownership)
 * 3. Create a unique list of all gift card objects
 * 4. Unlink card owners that are about to be deleted
 * 5. Deactivate cards that were active before, and have no remaining owners
 * 6. Delete the customers
 * WARNING: This is permanent and irreversible!
 */
 async function clearCustomers() {
  try {
    // Get all customers with the reference ID of our sample app.
    const { result: { customers } } = await customersApi.searchCustomers({
      query: {
        filter: {
          referenceId: {
            exact: referenceId
          }
        }
      }
    });

    if (!customers) {
      console.log("No customers to delete");
      return;
    }

    const customersToDelete = customers.map(customer => {
      return customer.id;
    });

    // Make request to get gift cards associated with each customer to be deleted.
    const giftCardsPromises = customersToDelete.map(id => {
      return giftCardsApi.listGiftCards(undefined, undefined, undefined, undefined, id);
    });

    const data = await Promise.all(giftCardsPromises);

    // Get all gift card objects related to customers we are about to delete.
    const allGiftCards = data
        .filter(body => body.result.giftCards)
        .flatMap(body => {
          return body.result.giftCards;
        }
    );

    // Getting rid of duplicate gift card objects (b/c of multiple ownership).
    const uniqueGiftCardObjects = [];
    const map = {};
    allGiftCards.forEach(giftCard => {
      if (!map[giftCard.id]) {
        map[giftCard.id] = true;
        uniqueGiftCardObjects.push(giftCard);
      }
    });

    // Make requests to unlink cards from soon-to-be deleted customers.
    const toDeactivate = [];
    const unlinkPromises = uniqueGiftCardObjects.flatMap(giftCard => {
      // get all common IDs between the gift card owners and the customers to be deleted.
      // these are the ones we have to unlink.
      // If all of the owners are customers to be deleted AND the card is active, we also
      // mark it for deactivation.
      const idsToUnlink = customersToDelete.filter(customerId => giftCard.customerIds.includes(customerId));
      if (idsToUnlink.length == giftCard.customerIds.length && giftCard.state === "ACTIVE") {
        toDeactivate.push(giftCard.gan);
      }

      return idsToUnlink.map(customerId => {
        return giftCardsApi.unlinkCustomerFromGiftCard(giftCard.id, {
          customerId: customerId
        })
      });
    });

    // Unlink deleted customers
    await Promise.all(unlinkPromises);

    // Make requests to deactivate all marked GANs.
    const giftCardDeactivationPromises = toDeactivate.map(gan => {
      return giftCardActivitiesApi.createGiftCardActivity({
        idempotencyKey: uuidv4(),
        giftCardActivity: {
          giftCardGan: gan,
          locationId: locationId,
          type: "DEACTIVATE",
          deactivateActivityDetails: {
            // In the future we will support other reasons.
            reason: "SUSPICIOUS_ACTIVITY"
          }
        }
      });
    });

    // Delete all previously created customers.
    await Promise.all(customersToDelete.map(id => {
      return customersApi.deleteCustomer(id);
    }));

    // Deactivate all gift cards.
    await Promise.all(giftCardDeactivationPromises);

    console.log("Successfully cleared all customers previously created by this script");
  } catch (error) {
    if (error instanceof ApiError) {
      const errors = error.errors;
      console.error("Clear customers failed: ", JSON.stringify(errors));
    } else {
      console.error("Clear customers failed: ", error);
    }
  }
}

/*
* Main driver for the script.
*/
const args = process.argv.slice(2);
if (args[0] === "generate") {
  // Generate new customers
  createCustomers();
} else if (args[0] === "clear") {
  // Clear customers
  const ioInterface = readline.createInterface({
    input: process.stdin,
    output: process.stdout
  });
  ioInterface.question(
    "Are you sure you want to clear all the customers previously created by this script (Y/N) ",
     (ans) => {
    ans = ans.toUpperCase();
    if (ans === "Y") {
      clearCustomers();
    } else if (ans === "N") {
      console.log("Aborting clear.");
    }
    ioInterface.close();
  });
} else if (args[0] === "-h" || args[0] === "--help") {
  console.log(
    "Please check the README.md for more information on how to run our seeding script."
    + "\nAvailable commands include:\n npm run seed - Generates fake customers."
    + "\n npm run clear - Clears your customers."
  );
} else {
  console.log("Command not recognized. Please try again.");
}
