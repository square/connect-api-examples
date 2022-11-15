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
  revertCanceledSubscription,
  catalogApi,
  customersApi,
  subscriptionsApi,
} = require("../util/square-client");
const SubscriptionDetailsInfo = require("../models/subscription-details-info");

const router = express.Router();


/**
 * Matches: GET /subscription/view/:locationId/:customerId/:subscriptionPlanId
 *
 * Description:
 *  Renders the subscription plan information that includes:
 *    * subscription plan phase information
 *    * active subscription status
 *    * button to subscribe, unsubscribe or revert cancelling subscription depends on the status of subscription
 *
 * Query Parameters:
 *  locationId: Id of the location that the invoice belongs to
 *  customerId: Id of the selected customer
 *  subscriptionPlanId: Id of the subscription plan
 */
router.get("/view/:locationId/:customerId/:subscriptionPlanId", async (req, res, next) => {
  const {
    locationId,
    customerId,
    subscriptionPlanId,
  } = req.params;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(customerId);
    const { result: { object } } = await catalogApi.retrieveCatalogObject(subscriptionPlanId);
    const subscriptionPlan = object.subscriptionPlanData;
    const { result: { subscriptions } } = await subscriptionsApi.searchSubscriptions({
      query: {
        filter: {
          customerIds: [
            customerId
          ],
          locationIds: [
            locationId
          ]
        }
      }
    });

    // find the first active subscription for the current plan
    // In the current workflow of the example, we don't allow more than one active subscription for each customer
    // so we'll assume we can rely on the first active subscription if there are multiple
    const activeSubscription = subscriptions ?
      Object.values(subscriptions).find((subscription) => {
        return subscription.planId === subscriptionPlanId
          && (subscription.status === "ACTIVE" || subscription.status === "PENDING");
      }) : null;

    // create a SubscriptionDetailsInfo object which translate the subscription plan and active subscription
    // information for the subscription page to render
    const subscriptionPlanInfo = new SubscriptionDetailsInfo(subscriptionPlan, activeSubscription);

    // render the subscription plan information and its subscription status
    res.render("subscription", {
      idempotencyKey: uuidv4(),
      locationId,
      customer,
      subscriptionPlanId,
      subscriptionPlanInfo,
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
 *  planId: Id of the subscription plan
 *  customerId: Id of the selected customer
 *  locationId: Id of the location that the order belongs to
 *  idempotencyKey: Unique identifier for request from client
 */
router.post("/subscribe", async (req, res, next) => {
  const {
    planId,
    customerId,
    locationId,
    idempotencyKey,
  } = req.body;
  try {
    const { result: { customer } } = await customersApi.retrieveCustomer(customerId);

    if (customer.cards && customer.cards.length > 0) {
      // create subscription with the first card id
      await subscriptionsApi.createSubscription({
        idempotencyKey,
        locationId,
        planId,
        customerId,
        cardIsd: customer.cards[0].id,
      });
    } else {
      // create subscription without card id
      await subscriptionsApi.createSubscription({
        idempotencyKey,
        locationId,
        planId,
        customerId,
      });
    }

    // redirect to the subscription plan detail page
    res.redirect(`view/${locationId}/${customerId}/${planId}`);
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
 *  planId: Id of the subscription plan
 *  customerId: Id of the selected customer
 *  locationId: Id of the location that the order belongs to
 *  subscriptionId: Id of the subscription
 *  subscriptionVersion: Version of the subscription
 */
router.post("/revertcancel", async (req, res, next) => {
  const {
    planId,
    customerId,
    locationId,
    subscriptionId,
    subscriptionVersion
  } = req.body;
  try {

    // revert a cancelling subscription by
    // removing the `canceledDate` field from the subscription
    await revertCanceledSubscription({
      subscriptionId,
      version: subscriptionVersion
    });

    // redirect to the subscription plan detail page
    res.redirect(`view/${locationId}/${customerId}/${planId}`);
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
 *  customerId: Id of the selected customer
 *  locationId: Id of the location that the order belongs to
 *  subscriptionId: Id of the subscription
 */
router.post("/unsubscribe", async (req, res, next) => {
  const {
    customerId,
    locationId,
    subscriptionId,
  } = req.body;
  try {
    // Cancel the subscription
    const { result: { subscription } } = await subscriptionsApi.cancelSubscription(subscriptionId);
    res.redirect(`view/${locationId}/${customerId}/${subscription.planId}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
