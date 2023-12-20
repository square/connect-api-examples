/*
Copyright 2019 Square Inc.

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
const url = require("url");
const crypto = require("crypto")
const {
  squareApplicationId,
  retrieveOrderAndLocation,
  getLoyaltyAccountByPhoneNumber,
  getLoyaltyRewardInformation,
  ordersApi,
  paymentsApi,
  loyaltyApi,
  locationsApi
} = require("../util/square-client");
const DeliveryPickUpTimes = require("../models/delivery-pickup-times");

const router = express.Router();

/**
 * Matches: GET /checkout/choose-delivery-pickup/
 *
 * Description:
 *  Render the page for customer to submit delivery or pickup informaiton
 *  to update the corresponding order (orderId)
 *
 * Query Parameters:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 */
router.get("/choose-delivery-pickup", async (req, res, next) => {
  const {
    orderId,
    locationId
  } = req.query;
  try {
    const {
      orderInfo,
      locationInfo
    } = await retrieveOrderAndLocation(
      orderId,
      locationId
    );
    res.render("checkout/choose-delivery-pickup", {
      locationInfo,
      orderInfo,
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /checkout/choose-delivery-pickup/
 *
 * Description:
 *  Take the pickup or delivery choice that are submitted from the page,
 *  then redirect to the page that will take the corresponding inputs.
 *
 * Request Body:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 *  fulfillmentType: One of the fulfillment types, learn more https://developer.squareup.com/reference/square/objects/OrderFulfillmentType
 */
router.post("/choose-delivery-pickup", async (req, res, next) => {
  const {
    orderId,
    locationId,
    fulfillmentType
  } = req.body;
  if (fulfillmentType === "PICKUP") {
    res.redirect(
      `/checkout/add-pickup-details?orderId=${orderId}&locationId=${locationId}`
    );
  } else {
    // if (fulfillmentType === "SHIPMENT")
    res.redirect(
      `/checkout/add-delivery-details?orderId=${orderId}&locationId=${locationId}`
    );
  }
});

/**
 * Matches: GET /checkout/add-pickup-details/
 *
 * Description:
 *  Render the page for customer to submit delivery or pickup informaiton
 *  to update the corresponding order (orderId)
 *
 * Query Parameters:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 */
router.get("/add-pickup-details", async (req, res, next) => {
  const {
    orderId,
    locationId
  } = req.query;
  try {
    const {
      orderInfo,
      locationInfo
    } = await retrieveOrderAndLocation(
      orderId,
      locationId
    );
    res.render("checkout/add-pickup-details", {
      locationInfo,
      expectedPickUpTimes: new DeliveryPickUpTimes(),
      orderInfo,
      idempotencyKey: crypto.randomUUID(),
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /checkout/add-pickup-details/
 *
 * Description:
 *  Take the pickup or delivery infomration that are submitted from the page,
 *  then call UpdateOrder api to update the fulfillment.
 *
 *  You learn more about the UpdateOrder endpoint here:
 *  https://developer.squareup.com/reference/square/orders-api/update-order
 *
 *  NOTE: This example is to show you how to update an order, however, you don't
 *  have to create an order and update it in each step; Instead, you can also
 *  collect all the order information that include purchased catalog items and
 *  fulfillment inforamiton, and create an order all together.
 *
 * Request Body:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 *  pickupName: Name of the individual who ordered
 *  pickupEmail: Email of the individual who ordered
 *  pickupNumber: Phone number of the individual who ordered
 *  pickupTime: Expected pickup time
 */
router.post("/add-pickup-details", async (req, res, next) => {
  const {
    orderId,
    locationId,
    idempotencyKey,
    pickupName,
    pickupEmail,
    pickupNumber,
    pickupTime,
  } = req.body;
  try {
    const { result: { orders } } = await ordersApi.batchRetrieveOrders({
      locationId,
      orderIds: [orderId],
    });
    const order = orders[0];
    await ordersApi.updateOrder(order.id, {
      order: {
        locationId,
        fulfillments: [{
          // replace fulfillment if the order is updated again, otherwise add a new fulfillment details.
          uid: order.fulfillments && order.fulfillments[0] ?
            order.fulfillments[0].uid :
            undefined,
          type: "PICKUP", // pickup type is determined by the endpoint
          state: "PROPOSED",
          pickupDetails: {
            recipient: {
              displayName: pickupName,
              phoneNumber: pickupNumber,
              email: pickupEmail,
            },
            pickupAt: pickupTime,
          },
        }, ],
        // Add an 10% Curbside Pickup promotion discount to the order
        discounts: [{
          // replace discount if the order is updated again, otherwise add a new discount.
          uid: order.discounts && order.discounts[0] ?
            order.discounts[0].uid :
            undefined,
          name: "Curbside Pickup Promotion",
          percentage: "10",
          scope: "ORDER"
        }],
        version: order.version,
        idempotencyKey,
      },
    });
    res.redirect(
      `/checkout/payment?orderId=${order.id}&locationId=${order.locationId}`
    );
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: GET /checkout/add-delivery-details/
 *
 * Description:
 *  Render the page for customer to submit delivery informaiton
 *  to update the corresponding order (orderId)
 *
 * Query Parameters:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 */
router.get("/add-delivery-details", async (req, res, next) => {
  const {
    orderId,
    locationId
  } = req.query;
  try {
    const {
      orderInfo,
      locationInfo
    } = await retrieveOrderAndLocation(
      orderId,
      locationId
    );
    res.render("checkout/add-delivery-details", {
      locationInfo,
      expectedDeliveryTimes: new DeliveryPickUpTimes(),
      orderInfo,
      idempotencyKey: crypto.randomUUID(),
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /checkout/add-delivery-details/
 *
 * Description:
 *  Take the delivery infomration that are submitted from the page,
 *  then call UpdateOrder api to update the fulfillment.
 *
 *  You learn more about the UpdateOrder endpoint here:
 *  https://developer.squareup.com/reference/square/orders-api/update-order
 *
 *  NOTE: This example is to show you how to update an order, however, you don't
 *  have to create an order and update it in each step; Instead, you can also
 *  collect all the order information that include purchased catalog items and
 *  fulfillment inforamiton, and create an order all together.
 *
 * Request Body:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 *  deliveryName: Name of the individual who will receive the delivery
 *  deliveryEmail: Email of the recipient
 *  deliveryNumber: Phone number of the recipient
 *  deliveryTime: Expected delivery time
 *  deliveryAddress: Street address of the recipient
 *  deliveryCity: City of the recipient
 *  deliveryState: State of the recipient
 *  deliveryPostal: Postal code of the recipient
 */
router.post("/add-delivery-details", async (req, res, next) => {
  const {
    orderId,
    locationId,
    idempotencyKey,
    deliveryName,
    deliveryEmail,
    deliveryNumber,
    deliveryTime,
    deliveryAddress,
    deliveryCity,
    deliveryState,
    deliveryPostal,
  } = req.body;
  try {
    const { result: { orders } } = await ordersApi.batchRetrieveOrders({
      locationId,
      orderIds: [orderId],
    });
    const order = orders[0];

    // get the currency for the location
    const locationResponse = await locationsApi.retrieveLocation(locationId);
    const currency = locationResponse.result.location.currency;

    await ordersApi.updateOrder(order.id, {
      order: {
        locationId,
        fulfillments: [{
          // replace fulfillment if the order is updated again, otherwise add a new fulfillment details.
          uid: order.fulfillments && order.fulfillments[0] ?
            order.fulfillments[0].uid :
            undefined,
          type: "SHIPMENT", // SHIPMENT type is determined by the endpoint
          state: "PROPOSED",
          shipmentDetails: {
            recipient: {
              displayName: deliveryName,
              phoneNumber: deliveryNumber,
              email: deliveryEmail,
              address: {
                addressLine1: deliveryAddress,
                administrativeDistrictLevel1: deliveryState,
                locality: deliveryCity,
                postalCode: deliveryPostal,
              },
            },
            expectedShippedAt: deliveryTime,
          },
        }, ],
        // Add an arbitratry $2.00 taxable delivery fee to the order
        serviceCharges: [{
          // replace serviceCharges if the order is updated again, otherwise add a new serviceCharge.
          uid: order.serviceCharges && order.serviceCharges[0] ?
            order.serviceCharges[0].uid :
            undefined,
          name: "delivery fee",
          amountMoney: {
            amount: 200,
            currency: currency
          },
          taxable: true,
          calculationPhase: "SUBTOTAL_PHASE",
        }],
        version: order.version,
        idempotencyKey,
      },
    });
    res.redirect(
      `/checkout/payment?orderId=${order.id}&locationId=${order.locationId}`
    );
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: GET /checkout/payment/
 *
 * Description:
 *  Render the page for customer to submit payment information (a nounce) in order to pay the order
 *
 *  We will render the payment page using the Web Payment SDK in this step.
 *  It takes credit card information on the client, and converts them into
 *  a nonce through square service securely. You can learn more about the Web Payment SDK here:
 *  https://developer.squareup.com/docs/web-payments/overview
 *
 * Query Parameters:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 */
router.get("/payment", async (req, res, next) => {
  const {
    orderId,
    locationId,
    loyaltyAccountId
  } = req.query;
  try {
    const {
      orderInfo,
      locationInfo
    } = await retrieveOrderAndLocation(
      orderId,
      locationId
    );
    if (!orderInfo.hasFulfillments) {
      // if the order doesn't have any fulfillment informaiton, fallback to previous step to collect fulfillment information
      res.redirect(
        `/checkout/choose-delivery-pickup?orderId=${orderId}&locationId=${locationId}`
      );
    }

    // collect loyalty account and reward tiers information so that the page can render reward options for customer to choose
    const loyaltyRewardInfo = await getLoyaltyRewardInformation(orderInfo, loyaltyAccountId);

    res.render("checkout/payment", {
      orderInfo,
      locationInfo,
      loyaltyRewardInfo,
      applicationId: squareApplicationId,
      idempotencyKey: crypto.randomUUID(), // Payments api has 45 max length limit on idempotencyKey
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /checkout/payment/
 *
 * Description:
 *  Take the payment infomration that are submitted from the /checkout/payment page,
 *  then call payment api to pay the order
 *
 *  You learn more about the CreatePayment endpoint here:
 *  https://developer.squareup.com/reference/square/payments-api/create-payment
 *
 * Request Body:
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 *  nonce: Card nonce (a secure single use token) created by the Square Payment Form
 */
router.post("/payment", async (req, res, next) => {
  const {
    orderId,
    locationId,
    idempotencyKey,
    token,
  } = req.body;

  try {
    // get the latest order information in case the price is changed from a different session
    const { result: { orders } } = await ordersApi.batchRetrieveOrders({
      locationId,
      orderIds: [orderId],
    });
    const order = orders[0];
    if (order.totalMoney.amount > 0) {
      try {
        // Payment can only be made when order amount is greater than 0
        const { result: { payment } } = await paymentsApi.createPayment({
          sourceId: token, // Card nonce created by the payment form
          idempotencyKey,
          amountMoney: order.totalMoney, // Provides total amount of money and currency to charge for the order.
          orderId: order.id, // Order that is associated with the payment
        });

        const result = JSON.stringify(payment, (key, value) => {
          return typeof value === "bigint" ? parseInt(value) : value;
        }, 4);
        res.json(result);

      } catch (error) {
        res.json(error.result);
      }
    } else {
      try {
        // Settle an order with a total of 0.
        const { result: { payment } } = await ordersApi.payOrder(orderId, {
          idempotencyKey
        });

        const result = JSON.stringify(payment, (key, value) => {
          return typeof value === "bigint" ? parseInt(value) : value;
        }, 4);
        res.json(result);

      } catch (error) {
        res.json(error.result);
      }
    }
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /checkout/add-loyalty-account/
 *
 * Description:
 *  Search for the loyalty account that is associated with the specified phone number.
 *  Add the loyaltyAccountId in the query parameter and redirect back to the current page,
 *  if the loyalty account isn't found, still add the query parameter with 'null' value.
 *
 * Request Body:
 *  orderId: Id of the order
 *  locationId: Id of the location that the order belongs to
 *  phoneNumber: Card nonce (a secure single use token) created by the Square Payment Form
 */
router.post("/add-loyalty-account", async (req, res, next) => {
  const {
    orderId,
    locationId,
    phoneNumber
  } = req.body;
  try {
    const formattedPhoneNumber = `+1${phoneNumber}`;
    const currentLoyaltyAccount = await getLoyaltyAccountByPhoneNumber(formattedPhoneNumber);
    if (currentLoyaltyAccount) {
      // Get the referrer path and redirect back with the loyalty account id
      const referrerPath = url.parse(req.get("referrer")).pathname;
      res.redirect(`${referrerPath}?orderId=${orderId}&locationId=${locationId}&loyaltyAccountId=${currentLoyaltyAccount && currentLoyaltyAccount.id}`);
    } else {
      // Go back to confirmation page
      res.redirect(req.get("referrer"));
    }

  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /checkout/redeem-loyalty-reward/
 *
 * Description:
 *  Redeem the loyalty points with the reward tier selected by customer.
 *
 * Request Body:
 *  orderId: Id of the order which will be applied a reward
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 *  loyaltyAccountId: Id of the loyalty account where we get point from
 *  rewardTierId: Id of the reward tier that is going to applied to the order
 */
router.post("/redeem-loyalty-reward", async (req, res, next) => {
  const {
    orderId,
    locationId,
    idempotencyKey,
    loyaltyAccountId,
    rewardTierId
  } = req.body;
  try {
    // apply the specified reward with `rewardTierId` to the order
    await loyaltyApi.createLoyaltyReward({
      reward: {
        orderId,
        loyaltyAccountId,
        rewardTierId,
      },
      idempotencyKey,
    });

    // Get the referrer path and redirect back with the loyalty account id
    const referrerPath = url.parse(req.get("referrer")).pathname;
    res.redirect(`${referrerPath}?orderId=${orderId}&locationId=${locationId}&loyaltyAccountId=${loyaltyAccountId}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
