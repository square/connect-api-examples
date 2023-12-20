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
const crypto = require("crypto");
const {
  retrieveOrderAndLocation,
  getDefaultLoyaltyProgram,
  getLoyaltyPointAccumulateInformation,
  getLoyaltyAccountByPhoneNumber,
  loyaltyApi
} = require("../util/square-client");

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
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 */
router.get("/", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
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
    if ((!orderInfo.order.tenders || orderInfo.order.tenders.length == 0) && orderInfo.totalMoney > 0) {
      // For simplicity, we throw error. You can handle this more gracefully
      throw new Error("order not paid");
    }

    // Get the information about:
    // 1. whether we can show a loyalty accumulate option
    // 2. the accumulated points if we've accumulated points
    const loyaltyAccumulateInfo = await getLoyaltyPointAccumulateInformation(orderId);

    // Render the order confirmation page
    res.render("order-confirmation", {
      locationInfo,
      orderInfo,
      loyaltyAccumulateInfo,
      idempotencyKey: crypto.randomUUID()
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
 *  orderId: Id of the order to be updated
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 *  phoneNumber: Phone number that related to a loyalty account
 */
router.post("/add-loyalty-point", async (req, res, next) => {
  const {
    orderId,
    locationId,
    idempotencyKey,
    phoneNumber
  } = req.body;
  try {
    // Get the program that we'd use for loyalty point accumulate
    const program = await getDefaultLoyaltyProgram();
    // the phone number must be in format like "+12223335252"
    const formattedPhoneNumber = `+1${phoneNumber}`;
    let currentLoyaltyAccount = await getLoyaltyAccountByPhoneNumber(formattedPhoneNumber);

    if (!currentLoyaltyAccount) {
      // Here we silently create a loyalty account for this new phone number.
      // Alternatively, we can also just deny the request and handle creating new loyalty account separately.
      const { result: { loyaltyAccount } } = await loyaltyApi.createLoyaltyAccount({
        idempotencyKey,
        loyaltyAccount: {
          mapping: {
            phoneNumber: formattedPhoneNumber
          },
          programId: program.id
        }
      });
      currentLoyaltyAccount = loyaltyAccount;
    }

    // Up to this point, we have a valid loyalty account to accumulate points on.
    // We can pass order id to seamlessly calculate the eligible points and add to the loyalty account.
    await loyaltyApi.accumulateLoyaltyPoints(currentLoyaltyAccount.id, {
      idempotencyKey,
      locationId,
      accumulatePoints: {
        orderId: orderId
      }
    });

    // redirect to order confirmation page after points are accumulated
    res.redirect(`/order-confirmation?orderId=${orderId}&locationId=${locationId}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
