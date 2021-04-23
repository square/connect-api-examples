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
} = require("../util/square-client");

const router = express.Router();

/**
 * Matches: GET /management/:locationId/:customerId
 *
 * Description:
 *  Renders the membership management page that:
 *  * display available subscription plans for the current customer
 *  * display the current subscription status of each plan for the current customer
 *
 * Query Parameters:
 *  customerId: Id of the selected customer
 *  locationId: Id of the location that the subscription belongs to
 */
router.get("/:locationId/:customerId", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const {
    customerId,
    locationId,
  } = req.params;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(customerId);

    // Get the subscription plans that are available for current location, an available plan is either
    // * presentAtAllLocations is true and current location id NOT in absentAtLocationIds
    // * presentAtAllLocations is false and current location id is in presentAtLocationIds
    const { result: { objects } } = await catalogApi.listCatalog(undefined,"SUBSCRIPTION_PLAN");
    const activeSubscriptionPlans = objects ?
      objects.filter(plan => (
        (plan.presentAtAllLocations && (!plan.absentAtLocationIds || plan.absentAtLocationIds.indexOf(locationId) < 0 )
          || (plan.presentAtLocationIds && plan.presentAtLocationIds.indexOf(locationId) >= 0)
        )
      ))
      : [];

    // Find the all the subcriptions for the current customer at current location
    const { result: { subscriptions } } = await subscriptionsApi.searchSubscriptions({
      query: {
        filter: {
          locationIds: [locationId],
          customerIds: [customer.id]
        }
      }
    });

    // filter the subscriptions that are active
    const activeSubscriptions = subscriptions ?
      subscriptions.filter(sub => (sub.status === "ACTIVE" || sub.status === "PENDING"))
      : [];
    const activeSubscriptionsMap = activeSubscriptions
      .reduce((map, sub) => {
        map.set(sub.planId, sub);
        return map;
      }, new Map());

    // build enrolled subscription info and additional subscription plan info for display
    const enrolledSubscriptionsInfo = [];
    const additionalSubscriptionsInfo = [];
    for (const key in activeSubscriptionPlans) {
      const subscriptionPlan = activeSubscriptionPlans[key];
      if (activeSubscriptionsMap.has(subscriptionPlan.id)) {
        enrolledSubscriptionsInfo.push({
          planId: subscriptionPlan.id,
          name: subscriptionPlan.subscriptionPlanData.name,
          id: activeSubscriptionsMap.get(subscriptionPlan.id).id,
          status: activeSubscriptionsMap.get(subscriptionPlan.id).status,
        });
      } else {
        additionalSubscriptionsInfo.push({
          planId: subscriptionPlan.id,
          name: subscriptionPlan.subscriptionPlanData.name,
        });
      }
    }

    res.render("management", {
      idempotencyKey: uuidv4(),
      locationId,
      customer,
      enrolledSubscriptionsInfo,
      additionalSubscriptionsInfo,
    });
  } catch (error) {
    next(error);
  }
});

module.exports = router;
