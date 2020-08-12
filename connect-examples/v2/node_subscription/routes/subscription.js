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
  catalogApi,
  customersApi,
  subscriptionsApi,
} = require("../util/square-connect-client");
const SubscriptionDetailsInfo = require("../models/subscription-details-info");

const router = express.Router();


/**
 * Matches: GET /subscription/view/:location_id/:customer_id/:subscription_plan_id
 *
 * Description:
 *  Renders the subscription plan information that includes:
 *    * subscription plan phase information
 *    * active subscription status
 *    * button to subscribe, unsubscribe or revert cancelling subscription depends on the status of subscription
 *
 * Query Parameters:
 *  location_id: Id of the location that the invoice belongs to
 *  customer_id: Id of the selected customer
 *  subscription_plan_id: Id of the subscription plan
 */
router.get("/view/:location_id/:customer_id/:subscription_plan_id", async (req, res, next) => {
  const {
    location_id,
    customer_id,
    subscription_plan_id,
  } = req.params;
  try {
    const { customer } = await customersApi.retrieveCustomer(customer_id);
    const { object } = await catalogApi.retrieveCatalogObject(subscription_plan_id);
    const subscription_plan = object.subscription_plan_data;
    const { subscriptions } = await subscriptionsApi.searchSubscriptions({
      location_ids: [location_id],
      customer_ids: [customer_id]
    });

    // find the first active subscription for the current plan
    // In the current workflow of the example, we don't allow more than one active subscription for each customer
    // so we'll assume we can rely on the first active subscription if there are multiple
    const active_subscription = subscriptions ?
      Object.values(subscriptions).find((subscription) => {
        return subscription.plan_id === subscription_plan_id
          && (subscription.status === "ACTIVE" || subscription.status === "PENDING");
      }) : null;

    // create a SubscriptionDetailsInfo object which translate the subscription plan and active subscription
    // information for the subscription page to render
    const subscription_plan_info = new SubscriptionDetailsInfo(subscription_plan, active_subscription);

    // render the subscription plan information and its subscription status
    res.render("subscription", {
      idempotency_key: uuidv4(),
      location_id,
      customer,
      subscription_plan_id,
      subscription_plan_info,
    });
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /subscription/subscribe
 *
 * Description:
 *  subscribe to the plan by create a subscription with the plan id
 *
 * Request Body:
 *  plan_id: Id of the subscription plan
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the order belongs to
 *  idempotency_key: Unique identifier for request from client
 */
router.post("/subscribe", async (req, res, next) => {
  const {
    plan_id,
    customer_id,
    location_id,
    idempotency_key,
  } = req.body;
  try {
    const { customer } = await customersApi.retrieveCustomer(customer_id);

    if (customer.cards && customer.cards.length > 0) {
      // create subscription with the first card id
      await subscriptionsApi.createSubscription({
        idempotency_key,
        location_id,
        plan_id,
        customer_id,
        card_id: customer.cards[0].id,
      });
    } else {
      // create subscription without card id
      await subscriptionsApi.createSubscription({
        idempotency_key,
        location_id,
        plan_id,
        customer_id,
      });
    }

    // redirect to the subscription plan detail page
    res.redirect(`view/${location_id}/${customer_id}/${plan_id}`);
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /subscription/revertcancel
 *
 * Description:
 *  revert the cancelling subscription and change the status back to active
 *
 * Request Body:
 *  plan_id: Id of the subscription plan
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the order belongs to
 *  subscription_id: Id of the subscription
 *  subscription_version: Version of the subscription
 */
router.post("/revertcancel", async (req, res, next) => {
  const {
    plan_id,
    customer_id,
    location_id,
    subscription_id,
    subscription_version
  } = req.body;
  try {
    // revert a cancelling subscription by
    // removing the `canceled_date` field from the subscription
    await subscriptionsApi.updateSubscription(subscription_id, {
      subscription: {
        version: parseInt(subscription_version)
      },
      fields_to_clear:[
        "canceled_date"
      ]
    });

    // redirect to the subscription plan detail page
    res.redirect(`view/${location_id}/${customer_id}/${plan_id}`);
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /subscription/unsubscribe
 *
 * Description:
 *  unsubscribe from the subscription
 *
 * Request Body:
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the order belongs to
 *  subscription_id: Id of the subscription
 */
router.post("/unsubscribe", async (req, res, next) => {
  const {
    customer_id,
    location_id,
    subscription_id,
  } = req.body;
  try {
    // Cancel the subscription
    const { subscription } = await subscriptionsApi.cancelSubscription(subscription_id);
    res.redirect(`view/${location_id}/${customer_id}/${subscription.plan_id}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
