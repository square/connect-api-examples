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
const locationId = process.env[`SQUARE_LOCATION_ID`];
const {
  giftCardsApi,
  giftCardActivitiesApi,
  customersApi,
  ordersApi,
  paymentsApi,
  locationsApi
} = require("../util/square-client");

const { checkLoginStatus, checkCardOwner } = require("../util/middleware");

/**
 * GET /gift-card/create
 * 
 * Renders the `Create a new gift card` page.
 * This endpoint retrieves all cards on file for the customer currently logged in.
 * You can add additional logic to filter out payment methods that might not be allowed
 * (i.e. paying for new gift cards using an existing gift card).
 */
router.get("/create", checkLoginStatus, async (req, res, next) => {
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
router.get("/load", checkLoginStatus, async (req, res, next) => {
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
router.get("/:gan", checkLoginStatus, checkCardOwner, async (req, res, next) => {
  const giftCard = res.locals.giftCard;
  res.render("pages/card-detail", { giftCard });
});

router.post("/create", async (req, res, next) => {
  // to create a gift card, I need:
  // 1. order (amount to activate to)
  // 2. retreieve customer (so I need the ID)
  // 3. payment using the orderId and source_id (card on file for cust)
  // 4. create gift card
  // 5. create gift card activity (ACTIVATE)
  // for now, assume the payload contains the customerID, amount to create with, payment method.
  try {
    const customerId = "TA3WENG538TEV1PPG86MJK6RRW";
    const amount = 82500;
    const paymentSource = "ccof:6tkulkGbcTkqSSoe3GB";

    // Grab the currency to be used based on the location.
    const currency = await getCurrency();
    console.log(currency);

    // The following code runs the order/payment flow and the gift card creation flow in parallel as they are independent.
    const orderPromise = createGiftCardOrder(customerId, amount, currency);
    const giftCardPromise = createGiftCard()
    const { result: { order } } = await orderPromise;
    // extract useful information from the order
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    await createGiftCardPayment(customerId, amount, currency, paymentSource, orderId);

    // Do not proceed until the gift card creation is done
    const { result: { giftCard } } = await giftCardPromise;
    const gan = giftCard.gan;

    // No that we have an actual gift card created and paid for, activate it.
    const { result: { giftCardActivity }} = await activateGiftCard(gan, orderId, lineItemId);
    const giftCardId = giftCardActivity.giftCardId;

    // Now link it to the customer that paid for it!
    await linkCustomerToGiftCard(customerId, giftCardId);

    //DONE!!! (VERIFY BY LISTING GIFT CARDS BY CUSTOMER ID)
    console.log("DONE");
  } catch (error) {
    // TODO: fix
    console.error(error);
  }
});



router.post("/load", async (req, res, next) => {
  try {
    const customerId = "TA3WENG538TEV1PPG86MJK6RRW";
    const amount = 82500;
    const paymentSource = "ccof:6tkulkGbcTkqSSoe3GB";

    // Grab the currency to be used based on the location.
    const currency = await getCurrency();
    console.log(currency);

    // The following code runs the order/payment flow and the gift card creation flow in parallel as they are independent.
    const orderPromise = createGiftCardOrder(customerId, amount, currency);
    const giftCardPromise = createGiftCard()
    const { result: { order } } = await orderPromise;
    // extract useful information from the order
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    await createGiftCardPayment(customerId, amount, currency, paymentSource, orderId);

    // Do not proceed until the gift card creation is done
    const { result: { giftCard } } = await giftCardPromise;
    const gan = giftCard.gan;

    // No that we have an actual gift card created and paid for, activate it.
    const { result: { giftCardActivity }} = await activateGiftCard(gan, orderId, lineItemId);
    const giftCardId = giftCardActivity.giftCardId;

    // Now link it to the customer that paid for it!
    await linkCustomerToGiftCard(customerId, giftCardId);

    //DONE!!! (VERIFY BY LISTING GIFT CARDS BY CUSTOMER ID)
    console.log("DONE");
  } catch (error) {
    // TODO: fix
    console.error(error);
  }
});

function createGiftCardOrder(customerId, amount, currency) {
  const orderRequest = {
    idempotencyKey: uuidv4(),
    order: {
      lineItems: [
        {
          name: "A cool gift card",
          quantity: "1",
          itemType: "GIFT_CARD",
          basePriceMoney: {
            amount: amount,
            currency: currency
          }
        }
      ],
      locationId: locationId,
      customerId: customerId
    }
  };


  return ordersApi.createOrder(orderRequest);
}

function createGiftCardPayment(customerId, amount, currency, paymentSource, orderId) {
  const paymentRequest = {
    idempotencyKey: uuidv4(),
    sourceId: paymentSource,
    amountMoney: {
      amount: amount,
      currency: currency
    },
    orderId: orderId,
    locationId: locationId,
    customerId: customerId
  };

  return paymentsApi.createPayment(paymentRequest);
}

async function createGiftCard() {
  const createGiftCardRequest = {
    idempotencyKey: uuidv4(),
    locationId: locationId,
    giftCard: {
      type: "DIGITAL"
    }
  };

  return giftCardsApi.createGiftCard(createGiftCardRequest);
}

async function activateGiftCard(gan, orderId, lineItemId) {
  const createGiftCardActivityRequest = {
    idempotencyKey: uuidv4(),
    giftCardActivity: {
      giftCardGan: gan,
      type: "ACTIVATE",
      locationId: locationId,
      activateActivityDetails: {
        orderId: orderId,
        lineItemUid: lineItemId
      }
    }
  };

  return giftCardActivitiesApi.createGiftCardActivity(createGiftCardActivityRequest);
}

async function linkCustomerToGiftCard(customerId, giftCardId) {
  return giftCardsApi.linkCustomerToGiftCard(giftCardId, {
    customerId: customerId
  });
}

async function getCurrency() {
  try {
    const { result: { location } } = await locationsApi.retrieveLocation(locationId);
    return location.currency;
  } catch (error) {
    console.error("Could not fetch currency from location ID");
  }
}

module.exports = router;