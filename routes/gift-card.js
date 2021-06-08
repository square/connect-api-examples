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
    // TODO: might want to filter out gift cards as payment option.
    const cards = customer.cards;
    res.render("pages/create-gift-card", { cards });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * GET /gift-card/:gan/load
 *
 * Renders the `Load an existing gift card` page.
 * This endpoint is very similar to GET /gift-card/create, but returns more information regarding the gift card.
 * You can add additional logic to filter out payment methods that might not be allowed
 * (i.e. loading gift cards using an existing gift card).
 */
router.get("/:gan/load", checkLoginStatus, checkCardOwner, async (req, res, next) => {
  const gan = req.params.gan;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(req.session.customerId);
    // TODO: might want to filter out gift cards as payment option.
    // TODO: might want to return more information about the card, such as balance.
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
 * GET /gift-card/:gan/history
 * 
 * Displays the transaction history for a card
 */
 router.get("/:gan/history", checkLoginStatus, checkCardOwner, async (req, res, next) => {
  try {
    const giftCard = res.locals.giftCard;
    const { result : { giftCardActivities } } = await giftCardActivitiesApi.listGiftCardActivities(giftCard.id);
    
    res.render("pages/history", { giftCard, giftCardActivities });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * POST /gift-card/create
 *
 * Creates and activates a gift card for the logged in customer based on the amount provided.
 * Steps are:
 * 1. Create an order (with the amount provided by the customer)
 * 2. Create a payment using the orderId and sourceId from (1)
 * 3. Create gift card
 * 4. Activate the gift card using information from (1,2,3)
 * 5. Link gift card to the customer
 * Steps (1,2) and (3) can happen in parallel. Once those are done, can proceed with (4,5).
 */
router.post("/create", checkLoginStatus, async (req, res, next) => {
  try {
    // The following information will come from the request/session.
    const customerId = req.session.customerId;
    const amount = getAmountInSmallestDenomination(req.body.amount);
    const paymentSource = req.body.cardId;

    // Get the currency to be used for the order/payment.
    const currency = req.app.locals.currency;

    // The following code runs the order/payment flow and the gift card creation flow concurrently as they are independent.
    const orderRequest = generateOrderRequest(customerId, amount, currency);
    const orderPromise = ordersApi.createOrder(orderRequest);

    const giftCardRequest = generateGiftCardRequest();
    const giftCardPromise = giftCardsApi.createGiftCard(giftCardRequest);

    // Await order call, as payment needs order information.
    const { result: { order } } = await orderPromise;

    // Extract useful information from the order.
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    const paymentRequest = generatePaymentRequest(customerId, amount, currency, paymentSource, orderId);
    await paymentsApi.createPayment(paymentRequest);

    // Do not proceed until the gift card creation is done.
    const { result: { giftCard } } = await giftCardPromise;
    const gan = giftCard.gan;

    // Now that we have an actual gift card created and paid for, activate it.
    const giftCardActivityRequest = generateGiftCardActivityRequest("ACTIVATE", gan, orderId, lineItemId);
    const { result: { giftCardActivity }} = await giftCardActivitiesApi.createGiftCardActivity(giftCardActivityRequest);
    const giftCardId = giftCardActivity.giftCardId;

    // Now link it to the customer that paid for it!
    await giftCardsApi.linkCustomerToGiftCard(giftCardId, {
      customerId: customerId
    });

    res.render("pages/confirmation");
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * POST /gift-card/:gan/load
 *
 * Loads a given gift card with the amount provided.
 * Steps are:
 * 1. Create an order (with the amount provided by the customer)
 * 2. Create a payment using the orderId and sourceId from (1)
 * 3. Load the gift card using information from (1,2)
 */
router.post("/:gan/load", checkLoginStatus, checkCardOwner, async (req, res, next) => {
  try {
    // The following information will come from the request/session.
    const customerId = req.session.customerId;
    const amount = getAmountInSmallestDenomination(req.body.amount);
    const paymentSource = req.body.cardId;
    const gan = req.params.gan;

    // Get the currency to be used for the order/payment.
    const currency = req.app.locals.currency;

    // The following code runs the order/payment flow.
    // Await order call, as payment needs order information.
    const orderRequest = generateOrderRequest(customerId, amount, currency);
    const { result: { order } } = await ordersApi.createOrder(orderRequest);

    // Extract useful information from the order.
    const orderId = order.id;
    const lineItemId = order.lineItems[0].uid;

    // We have the order response, we can move on to the payment.
    const paymentRequest = generatePaymentRequest(customerId, amount, currency, paymentSource, orderId);
    await paymentsApi.createPayment(paymentRequest);

    // Load the gift card.
    const giftCardActivityRequest = generateGiftCardActivityRequest("LOAD", gan, orderId, lineItemId);
    await giftCardActivitiesApi.createGiftCardActivity(giftCardActivityRequest);

    res.render("pages/confirmation");
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * Helper function for generating a gift card order request.
 * This function builds the request that will be used in the POST "v2/orders" API call.
 */
function generateOrderRequest(customerId, amount, currency) {
  return {
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
}

/**
 * Helper function for generating a gift card payment request.
 * This function builds the request that will be used in the POST "v2/payment" API call.
 */
function generatePaymentRequest(customerId, amount, currency, paymentSource, orderId) {
  return {
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
}

/**
 * Helper function for generating a create gift card request.
 * This function builds the request that will be used in the POST "v2/gift-cards" API call.
 */
function generateGiftCardRequest() {
  return {
    idempotencyKey: uuidv4(),
    locationId: locationId,
    giftCard: {
      type: "DIGITAL"
    }
  };
}

/**
 * Helper function for generating a create gift card activity request.
 * This function builds the request that will be used in the POST "v2/gift-cards/activities" API call.
 */
 function generateGiftCardActivityRequest(activityName, gan, orderId, lineItemId) {
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

/**
 * Helper function for getting the correct "Activity Object" for the
 * POST "v2/gift-cards/activities" request, based on the activity needed.
 * Currently, this app supports two activities: ACTIVATE (activating an inactive/new gift card),
 * and LOAD (loading an existing gift card).
 * This functionality can be extended to other activities as well.
 */
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

/**
 * Helper function to convert user money input to the smallest denomination.
 */
function getAmountInSmallestDenomination(amount) {
  return amount * 100;
}

module.exports = router;
