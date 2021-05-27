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

router.get("/activate", async (req, res, next) => {
  // grab customerID from session
  // const customerId = req.session.customerId;
  // hardcode for now
  const customerId = "7ZP819JGYMTXH0D1ASA5086JKG";
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(customerId);
    // we want to return all cards that belong to customer (add filtering logic here if don't want to show customer's gift card as payment method)
    const cards = customer.cards;
    res.render("pages/activate", {cards});
  } catch (error) {
    console.error(error);
    next(error);
  }
});

router.get("/load", async (req, res, next) => {
  // grab customerID from session
  // GAN value should either come from session or from path (need to figure out whether gan is considered private data)
  // const customerId = req.session.customerId;
  // const gan = req.query.gan;
  const customerId = "7ZP819JGYMTXH0D1ASA5086JKG";
  const gan = 7783320008099368;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(customerId);
    // we want to return all cards that belong to customer (add filtering logic here if don't want to show customer's gift card as payment method)
    const cards = customer.cards;
    res.render("pages/load", { cards: cards, gan: gan});
  } catch (error) {
    console.error(error);
    next(error);
  }
});

module.exports = router;
