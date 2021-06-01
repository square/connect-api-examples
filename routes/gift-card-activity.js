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

const { checkAuth, checkCardOwner } = require("../util/check-auth");

/**
 * GET /gift-card/create
 * 
 * Renders the `Create a new gift card` page.
 * This endpoint retrieves all cards on file for the customer currently logged in.
 * You can add additional logic to filter out payment methods that might not be allowed
 * (i.e. paying for new gift cards using an existing gift card).
 */
router.get("/create", checkAuth, async (req, res, next) => {
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(req.session.customerId);
    const cards = customer.cards;
    res.render("pages/create-gift-card", { cards });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * GET /gift-card/load
 * 
 * Renders the `Load an existing gift card` page.
 * This endpoint is very similar to GET /gift-card/create, but returns more information regarding the gift card.
 * You can add additional logic to filter out payment methods that might not be allowed
 * (i.e. loading gift cards using an existing gift card).
 */
router.get("/load", checkAuth, async (req, res, next) => {
  // TODO: GAN value should either come from session or from path, currently hardcoded FOR TESTING.
  // TODO: might want to return more information - like current balance.
  // const gan = req.query.gan;
  const gan = 7783320008099368;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(req.session.customerId);
    const cards = customer.cards;
    res.render("pages/load-gift-card", { cards: cards, gan: gan });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * GET /gift-card/:gan
 * 
 * Shows the details of a gift card by its GAN
 */
router.get("/:gan", checkAuth, checkCardOwner, async (req, res, next) => {
  const giftCard = res.locals.giftCard;
  res.render("pages/card-detail", { giftCard });
});

module.exports = router;
