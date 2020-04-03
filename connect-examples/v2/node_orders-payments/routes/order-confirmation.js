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
const { retrieveOrderAndLocation } = require("../util/square-connect-client");

const router = express.Router();

/**
 * Matches: GET /order-confirmation
 *
 * Description:
 *  Renders a confirmation page with order details.
 *
 *  If the order isn't paid, we throw error.
 *
 * Query Parameters:
 *  order_id: Id of the order to be updated
 *  location_id: Id of the location that the order belongs to
 */
router.get("/", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const { order_id, location_id } = req.query;
  try {
    const { order_info, location_info } = await retrieveOrderAndLocation(order_id, location_id);
    if (!order_info.order.tenders || order_info.order.tenders.length == 0 ) {
      // For simplicity, we throw error. You can handle this more gracefully
      throw new Error("order not paid");
    }

    res.render("order-confirmation", {
      location_info,
      order_info
    });
  }
  catch (error){
    next(error);
  }
});

module.exports = router;
