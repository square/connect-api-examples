/*
Copyright 2020 Square Inc.

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
  v4: uuidv4
} = require("uuid");
const {
  customersApi,
  catalogApi,
  subscriptionsApi,
} = require("../util/square-connect-client");

const router = express.Router();

/**
 * Matches: GET /management/:location_id/:customer_id
 *
 * Description:
 *  Renders the membership management page that:
 *  * display available subscription plans for the current customer
 *  * display the current subscription status of each plan for the current customer
 *
 * Query Parameters:
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the subscription belongs to
 */
router.get("/:location_id/:customer_id", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const {
    customer_id,
    location_id,
  } = req.params;
  try {
    const { customer } = await customersApi.retrieveCustomer(customer_id);

    // Get the subscription plans that are available for current location, an available plan is either
    // * present_at_all_locations is true and current location id NOT in absent_at_location_ids
    // * present_at_all_locations is false and current location id is in present_at_location_ids
    const { objects } = await catalogApi.listCatalog({ types: "SUBSCRIPTION_PLAN" });
    const active_subscriptoin_plans = objects ?
      objects.filter(plan => (
        (plan.present_at_all_locations && (!plan.absent_at_location_ids || plan.absent_at_location_ids.indexOf(location_id) < 0 )
          || (plan.present_at_location_ids && plan.present_at_location_ids.indexOf(location_id) >= 0)
        )
      ))
      : [];

    // Find the all the subcriptions for the current customer at current location
    const { subscriptions } = await subscriptionsApi.searchSubscriptions({
      location_ids: [location_id],
      customer_ids: [customer.id]
    });

    // filter the subscriptions that are active
    const active_subscriptions = subscriptions ?
      subscriptions.filter(sub => (sub.status === "ACTIVE" || sub.status === "PENDING"))
      : [];
    const active_subscriptions_map = active_subscriptions
      .reduce((map, sub) => {
        map.set(sub.plan_id, sub);
        return map;
      }, new Map());

    // build enrolled subscription info and additional subscription plan info for display
    const enrolled_subscriptions_info = [];
    const additional_subscriptions_info = [];
    for (const key in active_subscriptoin_plans) {
      const subscription_plan = active_subscriptoin_plans[key];
      if (active_subscriptions_map.has(subscription_plan.id)) {
        enrolled_subscriptions_info.push({
          plan_id: subscription_plan.id,
          name: subscription_plan.subscription_plan_data.name,
          id: active_subscriptions_map.get(subscription_plan.id).id,
          status: active_subscriptions_map.get(subscription_plan.id).status,
        });
      } else {
        additional_subscriptions_info.push({
          plan_id: subscription_plan.id,
          name: subscription_plan.subscription_plan_data.name,
        });
      }
    }

    res.render("management", {
      idempotency_key: uuidv4(),
      location_id,
      customer,
      enrolled_subscriptions_info,
      additional_subscriptions_info,
    });
  } catch (error) {
    next(error);
  }
});

module.exports = router;
