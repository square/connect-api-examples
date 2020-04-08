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
const {
  config,
  retrieveOrderAndLocation,
  orderInstance,
  paymentInstance,
} = require("../util/square-connect-client");
const DeliveryPickUpTimes = require("../models/delivery-pickup-times");

const router = express.Router();

/**
 * Matches: GET /checkout/choose-delivery-pickup/
 *
 * Description:
 *  Render the page for customer to submit delivery or pickup informaiton
 *  to update the corresponding order (order_id)
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/choose-delivery-pickup", async (req, res, next) => {
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(
      order_id,
      location_id
    );
    res.render("checkout/choose-delivery-pickup", {
      location_info,
      order_info,
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  fulfillment_type: One of the fulfillment types, learn more https://developer.squareup.com/docs/api/connect/v2#type-orderfulfillmenttype
 */
router.post("/choose-delivery-pickup", async (req, res, next) => {
  const { order_id, location_id, fulfillment_type } = req.body;
  if (fulfillment_type === "PICKUP") {
    res.redirect(
      `/checkout/add-pickup-details?order_id=${order_id}&location_id=${location_id}`
    );
  } else {
    // if (fulfillment_type === "SHIPMENT")
    res.redirect(
      `/checkout/add-delivery-details?order_id=${order_id}&location_id=${location_id}`
    );
  }
});

/**
 * Matches: GET /checkout/add-pickup-details/
 *
 * Description:
 *  Render the page for customer to submit delivery or pickup informaiton
 *  to update the corresponding order (order_id)
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/add-pickup-details", async (req, res, next) => {
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(
      order_id,
      location_id
    );
    res.render("checkout/add-pickup-details", {
      location_info,
      expected_pick_up_times: new DeliveryPickUpTimes(),
      order_info,
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  pickup_name: Name of the individual who ordered
 *  pickup_email: Email of the individual who ordered
 *  pickup_number: Phone number of the individual who ordered
 *  pickup_time: Expected pickup time
 */
router.post("/add-pickup-details", async (req, res, next) => {
  const {
    order_id,
    location_id,
    pickup_name,
    pickup_email,
    pickup_number,
    pickup_time,
  } = req.body;
  try {
    const { orders } = await orderInstance.batchRetrieveOrders(location_id, {
      order_ids: [order_id],
    });
    const order = orders[0];
    await orderInstance.updateOrder(order.location_id, order.id, {
      order: {
        fulfillments: [
          {
            // replace fulfillment if the order is updated again, otherwise add a new fulfillment details.
            uid:
              order.fulfillments && order.fulfillments[0]
                ? order.fulfillments[0].uid
                : undefined,
            type: "PICKUP", // pickup type is determined by the endpoint
            state: "PROPOSED",
            pickup_details: {
              recipient: {
                display_name: pickup_name,
                phone_number: pickup_number,
                email: pickup_email,
              },
              pickup_at: pickup_time,
            },
          },
        ],
        version: order.version,
        idempotency_key: randomBytes(45).toString("hex"),
      },
    });
    res.redirect(
      `/checkout/payment?order_id=${order.id}&location_id=${order.location_id}`
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
 *  to update the corresponding order (order_id)
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/add-delivery-details", async (req, res, next) => {
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(
      order_id,
      location_id
    );
    res.render("checkout/add-delivery-details", {
      location_info,
      expected_delivery_times: new DeliveryPickUpTimes(),
      order_info,
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  delivery_name: Name of the individual who will receive the delivery
 *  delivery_email: Email of the recipient
 *  delivery_number: Phone number of the recipient
 *  delivery_time: Expected delivery time
 *  delivery_address: Street address of the recipient
 *  delivery_city: City of the recipient
 *  delivery_state: State of the recipient
 *  delivery_postal: Postal code of the recipient
 */
router.post("/add-delivery-details", async (req, res, next) => {
  const {
    order_id,
    location_id,
    delivery_name,
    delivery_email,
    delivery_number,
    delivery_time,
    delivery_address,
    delivery_city,
    delivery_state,
    delivery_postal,
  } = req.body;
  try {
    const { orders } = await orderInstance.batchRetrieveOrders(location_id, {
      order_ids: [order_id],
    });
    const order = orders[0];
    await orderInstance.updateOrder(order.location_id, order.id, {
      order: {
        fulfillments: [
          {
            // replace fulfillment if the order is updated again, otherwise add a new fulfillment details.
            uid:
              order.fulfillments && order.fulfillments[0]
                ? order.fulfillments[0].uid
                : undefined,
            type: "SHIPMENT", // SHIPMENT type is determined by the endpoint
            state: "PROPOSED",
            shipment_details: {
              recipient: {
                display_name: delivery_name,
                phone_number: delivery_number,
                email: delivery_email,
                address: {
                  address_line_1: delivery_address,
                  administrative_district_level_1: delivery_state,
                  locality: delivery_city,
                  postal_code: delivery_postal,
                },
              },
              expected_shipped_at: delivery_time,
            },
          },
        ],
        version: order.version,
        idempotency_key: randomBytes(45).toString("hex"),
      },
    });
    res.redirect(
      `/checkout/payment?order_id=${order.id}&location_id=${order.location_id}`
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
    const { order_info, location_info } = await retrieveOrderAndLocation(
      order_id,
      location_id
    );
    if (!order_info.hasFulfillments) {
      // if the order doesn't have any fulfillment informaiton, fallback to previous step to collect fulfillment information
      res.redirect(
        `/checkout/choose-delivery-pickup?order_id=${order_id}&location_id=${location_id}`
      );
    }

    res.render("checkout/payment", {
      application_id: config.squareApplicationId,
      order_info,
      location_info,
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
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  nonce: Card nonce (a secure single use token) created by the Square Payment Form
 */
router.post("/payment", async (req, res, next) => {
  const { order_id, location_id, nonce } = req.body;
  try {
    // get the latest order information in case the price is changed from a different session
    const { orders } = await orderInstance.batchRetrieveOrders(location_id, {
      order_ids: [order_id],
    });
    const order = orders[0];
    await paymentInstance.createPayment({
      source_id: nonce, // Card nonce created by the payment form
      idempotency_key: randomBytes(45).toString("hex").slice(0, 45), // Unique identifier for request that is under 46 characters
      amount_money: order.total_money, // Provides total amount of money and currency to charge for the order.
      order_id: order.id, // Order that is associated with the payment
    });

    // redirect to order confirmation page once the order is paid
    res.redirect(
      `/order-confirmation?order_id=${order.id}&location_id=${order.location_id}`
    );
  } catch (error) {
    next(error);
  }
});

module.exports = router;
