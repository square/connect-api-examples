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
const {
  randomBytes
} = require("crypto");
const {
  retrieveOrderAndLocation,
  getDefaultLoyaltyProgram,
  getLoyaltyPointAccumulateInformation,
  getLoyaltyAccountByPhoneNumber,
  loyaltyApi
} = require("../util/square-connect-client");

const router = express.Router();

/**
 * Matches: GET /order-confirmation
 *
 * Description:
 *  Renders a confirmation page with order details, including order summary with status,
 *  payment card infomation, and loyalty related details.
 *
 *  If the order isn't paid, we throw error.
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const {
    order_id,
    location_id
  } = req.query;
  try {
    const {
      order_info,
      location_info
    } = await retrieveOrderAndLocation(
      order_id,
      location_id
    );
    if ((!order_info.order.tenders || order_info.order.tenders.length == 0) && order_info.totalMoney > 0) {
      // For simplicity, we throw error. You can handle this more gracefully
      throw new Error("order not paid");
    }

    // Get the information about:
    // 1. whether we can show a loyalty accumulate option
    // 2. the accumulated points if we've accumulated points
    const loyalty_accumulate_info = await getLoyaltyPointAccumulateInformation(order_id);

    // Render the order confirmation page
    res.render("order-confirmation", {
      location_info,
      order_info,
      loyalty_accumulate_info,
      idempotency_key: randomBytes(45).toString("hex")
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /order-confirmation/add-loyalty-point/
 *
 * Description:
 *  Take phone number and accumulate the loyalty point, if phone number is new,
 *  create a new loyalty account automatically.
 *
 * Request Body:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 *  idempotency_key: Unique identifier for request from client
 *  phone_number: Phone number that related to a loyalty account
 */
router.post("/add-loyalty-point", async (req, res, next) => {
  const {
    order_id,
    location_id,
    idempotency_key,
    phone_number
  } = req.body;
  try {
    // Get the program that we'd use for loyalty point accumulate
    const program = await getDefaultLoyaltyProgram();
    // the phone number must be in format like "+12223335252"
    const formated_phone_number = `+1${phone_number}`;
    let current_loyalty_account = await getLoyaltyAccountByPhoneNumber(formated_phone_number);

    if (!current_loyalty_account) {
      // Here we silently create a loyalty account for this new phone number.
      // Alternatively, we can also just deny the request and handle creating new loyalty account separately.
      const {
        loyalty_account
      } = await loyaltyApi.createLoyaltyAccount({
        idempotency_key,
        loyalty_account: {
          mappings: [{
            type: "PHONE",
            value: formated_phone_number
          }],
          "program_id": program.id
        }
      });
      current_loyalty_account = loyalty_account;
    }

    // Up to this point, we have a valid loyalty account to accumulate points on.
    // We can pass order id to seemlessly calculate the eligible points and add to the loyalty account.
    await loyaltyApi.accumulateLoyaltyPoints(current_loyalty_account.id, {
      idempotency_key,
      location_id: location_id,
      accumulate_points: {
        order_id: order_id
      }
    });

    // redirect to order confirmation page after points are accumulated
    res.redirect(`/order-confirmation?order_id=${order_id}&location_id=${location_id}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
