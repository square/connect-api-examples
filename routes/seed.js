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
const {
  giftCardsApi,
  giftCardActivitiesApi,
  customersApi,
  ordersApi,
  paymentsApi,
  locationsApi
} = require("../util/square-client");

const { checkLoginStatus, checkCustomerIdMatch, checkSandboxEnv } = require("../util/middleware");

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
    const cardNonce = "cnon:card-nonce-ok"
    await customersApi.createCustomerCard(customerId, { cardNonce });

    // Redirect to the add-funds page, which will now present the newly created card.
    res.redirect("/gift-card/" + req.body.gan + "/add-funds/?cardCreated=success");
  } catch (error) {
    console.error(error);
    next(error);
  }
});

module.exports = router;
