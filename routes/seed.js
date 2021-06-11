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
  giftCardsApi,
  giftCardActivitiesApi,
  customersApi,
  ordersApi,
  paymentsApi,
  locationsApi
} = require("../util/square-client");

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
    const cardNonce = "cnon:card-nonce-ok"
    await customersApi.createCustomerCard(customerId, { cardNonce });

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

module.exports = router;
