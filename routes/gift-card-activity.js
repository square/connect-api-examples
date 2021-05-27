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

/**
 * Handler for "/gift-card/create" route.
 * Steps are:
 * 1. order (amount to activate to)
 * 2. payment using the orderId and source_id (card on file for cust)
 * 3. create gift card
 * 4. create gift card activity (ACTIVATE)
 * 5. link gift card to customer
 * Steps (1,2) and (3) can happen in parallel. Once those are done, can proceed with (4,5).
 * TODO: COMBINE ORDER AND PAYMENT INTO ONE FUNCTION!!
 */
router.post("/create", checkAuth, async (req, res, next) => {
  try {
    // The following information will come from the request/session. Hardcoded for now.
    const customerId = req.session.customerId;
    const amount = getAmountInSmallestDenomination(req.body.amount);
    const paymentSource = req.body.cardId;

    // Grab the currency to be used based on the location.
    const currency = await getCurrency();

    // The following code runs the order/payment flow and the gift card creation flow concurrently as they are independent.
    const orderPromise = createGiftCardOrder(customerId, amount, currency);
    const giftCardPromise = createGiftCard()

    // Await order call, as payment needs order information.
    const { result: { order } } = await orderPromise;

    // extract useful information from the order
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    await createGiftCardPayment(customerId, amount, currency, paymentSource, orderId);

    // Do not proceed until the gift card creation is done
    const { result: { giftCard } } = await giftCardPromise;
    const gan = giftCard.gan;

    // Now that we have an actual gift card created and paid for, activate it.
    const { result: { giftCardActivity }} = await createGiftCardActivity("ACTIVATE", gan, orderId, lineItemId);
    const giftCardId = giftCardActivity.giftCardId;

    // Now link it to the customer that paid for it!
    await linkCustomerToGiftCard(customerId, giftCardId);

    // TODO: render confirmation page
  } catch (error) {
    console.error(error);
    next(error);
  }
});

router.post("/load/:gan", checkAuth, async (req, res, next) => {
  try {
    // The following information will come from the request/session. Hardcoded for now.
    const customerId = req.session.customerId;
    const amount = getAmountInSmallestDenomination(req.body.amount);
    const paymentSource = req.body.cardId;
    const gan = req.params.gan;

    // Grab the currency to be used based on the location.
    const currency = await getCurrency();

    // The following code runs the order/payment flow.
    // Await order call, as payment needs order information.
    const { result: { order } } = await createGiftCardOrder(customerId, amount, currency);

    // extract useful information from the order
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    await createGiftCardPayment(customerId, amount, currency, paymentSource, orderId);

    // Load the gift card
    await createGiftCardActivity("LOAD", gan, orderId, lineItemId);

    // TODO: render confirmation page
  } catch (error) {
    console.error(error);
    next(error);
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

function createGiftCard() {
  const createGiftCardRequest = {
    idempotencyKey: uuidv4(),
    locationId: locationId,
    giftCard: {
      type: "DIGITAL"
    }
  };

  return giftCardsApi.createGiftCard(createGiftCardRequest);
}

function createGiftCardActivity(activityName, gan, orderId, lineItemId) {
  const request = generateRequestBasedOnActivity(activityName, gan, orderId, lineItemId);
  return giftCardActivitiesApi.createGiftCardActivity(request);
}

function generateRequestBasedOnActivity(activityName, gan, orderId, lineItemId) {
  const activityObject = getActivityObject(activityName, orderId, lineItemId);
  const [ key ] = Object.keys(activityObject);
  const [ value ] = Object.values(activityObject);
  const request = {
    idempotencyKey: uuidv4(),
    giftCardActivity: {
      giftCardGan: gan,
      type: activityName,
      locationId: locationId,
    }
  };
  // Add the correct activity object to our request.
  request.giftCardActivity[key] = value;
  return request;
}

function getActivityObject(activityName, orderId, lineItemId) {
  switch(activityName) {
    case "ACTIVATE":
      return {
        activateActivityDetails: {
          orderId: orderId,
          lineItemUid: lineItemId
        }
      };
    case "LOAD":
      return {
        loadActivityDetails: {
          orderId: orderId,
          lineItemUid: lineItemId
        }
      };
    // Add more Gift Card Activities types you wish to support here!
    default:
      console.error("Unrecognized type");
  }
}

function linkCustomerToGiftCard(customerId, giftCardId) {
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

function getAmountInSmallestDenomination(amount) {
  return amount * 100;
}

module.exports = router;
