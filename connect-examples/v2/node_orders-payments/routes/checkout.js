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
const { randomBytes } = require("crypto");
const { config, retrieveOrderAndLocation, orderInstance, paymentInstance } = require("../util/square-connect-client");
const PickUpTimes = require("../models/pickup-times");

const router = express.Router();

/**
 * Matches: GET /checkout/choose-delivery-pickup/
 *
 * Description:
 *  Render the page for customer to submit delivery or pickup informaiton
 *  to update the corresponding order (order_id)
 *
 *  You learn more about the RetrieveCatalogObject endpoint here:
 *  https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-retrievecatalogobject
 *
 *  NOTE: The RetrieveCatalogObject api always returns related objects, while the SDK
 *  needs "includeRelatedObjects" to be set tot true.
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/choose-delivery-pickup", async (req, res, next) => {
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(order_id, location_id);
    res.render("checkout/choose-delivery-pickup", {
      location_info,
      pick_up_times: new PickUpTimes(),
      order_info
    });
  }
  catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /checkout/choose-delivery-pickup/
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  fulfillment_type: One of the fulfillment types, learn more https://developer.squareup.com/docs/api/connect/v2#type-orderfulfillmenttype
 *  pickup_name: Name of the individual who ordered
 *  pickup_email: Email of the individual who ordered
 */
router.post("/choose-delivery-pickup", async (req, res, next) => {
  const { order_id, location_id, fulfillment_type, pickup_name, pickup_email, pickup_number, pickup_time } = req.body;
  try {
    const { orders } = await orderInstance.batchRetrieveOrders(location_id, { order_ids: [order_id] });
    const order = orders[0];
    await orderInstance.updateOrder(order.location_id, order.id, {
      order: {
        fulfillments: [
          {
            // replace fulfillment if the order is updated again, otherwise add a new fulfillment details.
            uid: order.fulfillments && order.fulfillments[0] ? order.fulfillments[0].uid : undefined,
            type: fulfillment_type,
            state: "PROPOSED",
            pickup_details: {
              recipient: {
                display_name: pickup_name,
                phone_number: pickup_number,
                email: pickup_email
              },
              pickup_at: pickup_time
            }
          }
        ],
        version: order.version,
        idempotency_key: randomBytes(45).toString("hex")
      }
    });
    res.redirect(`/checkout/payment?order_id=${order.id}&location_id=${order.location_id}`);
  }
  catch (error) {
    next(error);
  }
});

/**
 * Matches: GET /checkout/payment/
 *
 * Description:
 *  Render the page for customer to submit payment information (a nounce) in order to pay the order
 *
 *  We will render SqPaymentForm in this step, it takes credit card informaiton on the client, convert them into
 *  a nonce through square service securely. You can learn more about the SqPaymentForm here:
 *  https://developer.squareup.com/docs/payment-form/overview
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/payment", async (req, res, next) => {
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(order_id, location_id);
    if (!order_info.hasFulfillments) {
      // if the order doesn't have any fulfillment informaiton, fallback to previous step to collect fulfillment information
      res.redirect(`/checkout/choose-delivery-pickup?order_id=${order_id}&location_id=${location_id}`);
    }

    res.render("checkout/payment", {
      application_id: config.squareApplicationId,
      order_info,
      location_info
    });
  }
  catch (error) {
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  nonce: Card nonce (a secure single use token) created by the Square Payment Form
 */
router.post("/payment", async (req, res, next) => {
  const { order_id, location_id, nonce } = req.body;
  try {
    // get the latest order information in case the price is changed from a different session
    const { orders } = await orderInstance.batchRetrieveOrders(location_id, { order_ids: [order_id] });
    const order = orders[0];
    await paymentInstance.createPayment(
      {
        source_id: nonce, // Card nonce created by the payment form
        idempotency_key: randomBytes(45).toString("hex").slice(0, 45), // Unique identifier for request that is under 46 characters
        amount_money: order.total_money, // Provides total amount of money and currency to charge for the order.
        order_id: order.id // Order that is associated with the payment
      });

    // redirect to order confirmation page once the order is paid
    res.redirect(`/order-confirmation?order_id=${order.id}&location_id=${order.location_id}`);
  }
  catch (error) {
    next(error);
  }
});

module.exports = router;
