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
const router = express.Router();
const { paymentInstance, orderInstance, catalogInstance, locationInstance } = require("../util/square-connect-client");
const { randomBytes } = require("crypto");
const ConfirmationPageData = require("../models/confirmation-page-data");


/**
 * Matches: POST /process-payments
 *
 * Description:
 *  Creates an order for a single item variation.
 *  Learn more about Orders here: https://developer.squareup.com/docs/orders-api/what-it-does
 *
 *  Creates payment from the previously created order and nonce from SqPayment form
 *  Learn more about the SqPayment form here: https://developer.squareup.com/docs/payment-form/what-it-does
 *
 *  Upon successful creation of payment, renders a confirmation page with order details.
 *
 * Request Body:
 *  nonce: Card nonce (a secure single use token) created by the Square Payment Form
 *  item_var_id: Id of the CatalogItemVariation which will be purchased
 *  pickup_name: Name of the individual who ordered
 *  pickup_email: Email of the individual who ordered
 *  fulfillment_type: One of the fulfillment types, learn more https://developer.squareup.com/docs/api/connect/v2#type-orderfulfillmenttype
 *  card_name: Name of the on cardholder
 *  card_email: Email of cardholder
 */
router.post("/", async (req, res, next) => {
  const { nonce, item_obj_id,  item_var_id, pickup_name, pickup_email, pickup_number, pickup_time, fulfillment_type, location } = req.body;
  try {
    // Creates order
    const { order } = await orderInstance.createOrder(location,
      {
        idempotency_key: randomBytes(45).toString("hex"), // Unique identifier for request
        order: {
          line_items: [
            {
              quantity: "1",
              catalog_object_id: item_var_id // Id for CatalogItemVariation object
            }
          ],
          fulfillments: [
            {
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
          ]
        }
      });
    // Create Payment
    const { payment } = await paymentInstance.createPayment(
      {
        source_id: nonce, // Card nonce created by the payment form
        idempotency_key: randomBytes(45).toString("hex").slice(0, 45), // Unique identifier for request that is under 46 characters
        amount_money: order.total_money, // Provides total amount of money and currency to charge for the order.
        order_id: order.id // Order that is associated with the payment
      });
    // Retrieve list of locations
    const { locations } = await locationInstance.listLocations();
    // Retrieve catalog object in order to get image and object info.
    const catalogObject = await catalogInstance.retrieveCatalogObject(item_obj_id, { includeRelatedObjects: true });
    // Compile all the data together into a ConfirmationPageData class
    const viewData = new ConfirmationPageData(catalogObject, locations[0], payment, order); // One location for the sake of simplicity.
    res.render("process-payment", viewData);
  } catch (error){
    next(error);
  }
});

module.exports = router;
