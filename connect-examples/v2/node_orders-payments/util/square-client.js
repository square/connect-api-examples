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

const { Client } = require("square");
require("dotenv").config();

const OrderInfo = require("../models/order-info");
const LocationInfo = require("../models/location-info");

const env = process.env.NODE_ENV;
const accessToken = process.env["SQ_ACCESS_TOKEN"];
const squareApplicationId = process.env["SQ_APPLICATION_ID"];

// Set Square credentials
const config = {
  accessToken,
  environment: env,
  userAgentDetail: "sample_app_node_orders-payments" // Remove or replace this detail when building your own app
};

// Extract instances of Api that are used
// You can add additional APIs here if you so choose
const {
  catalogApi,
  locationsApi,
  ordersApi,
  paymentsApi,
  loyaltyApi
} = new Client(config);

/**
 * Description:
 * Retrieve the order and location informaiton that are widely used in many pages in this example.
 *
 * @param {*} orderId The id of the order
 * @param {*} locationId The id of the location where the order belongs to
 *
 * @returns object{ orderInfo, locationInfo }
 */
const retrieveOrderAndLocation = async (orderId, locationId) => {
  const { result: { orders } } = await ordersApi.batchRetrieveOrders({
    locationId,
    orderIds: [orderId],
  });
  const { result: { location } } = await locationsApi.retrieveLocation(locationId);
  if (!orders || orders.length == 0 || !location) {
    const error = new Error("Cannot find order");
    error.status = 404;
    throw error;
  }

  return {
    orderInfo: new OrderInfo(orders[0]),
    locationInfo: new LocationInfo(location),
  };
};

/**
 * Description:
 * get the default loyalty program.
 *
 * @returns The loyalty propgram; if there is no loyalty program, return `null`.
 */
const getDefaultLoyaltyProgram = async () => {
  const { result: { program } } = await loyaltyApi.retrieveLoyaltyProgram("main");
  return program;
};

/**
 * Description:
 * Get the loyalty account from a phone number.
 *
 * @param {*} formattedPhoneNumber The phone number that's formatted as expected.
 *                                  The format must be like "+13334441111"
 *
 * @returns The loyalty account that associate with the phone number; If not found, return `null`.
 */
async function getLoyaltyAccountByPhoneNumber(formattedPhoneNumber) {
  const { result: { loyaltyAccounts } } = await loyaltyApi.searchLoyaltyAccounts({
    query: {
      mappings: [
        {
          phoneNumber: formattedPhoneNumber
        }
      ]
    }
  });

  return loyaltyAccounts ? loyaltyAccounts[0] : null;
}

/**
 * Description:
 * Retrieve the loyalty reward information about:
 * 1. whether we should show the reward option for the given order.
 * 2. the reward option that are either available or unavailable.
 *
 * Note: This method should be called before the order is paid
 *
 * @param {*} orderInfo Information about the order
 * @param {*} loyaltyAccountId Loyalty account ID
 *
 * @returns The loyalty reward information.
 */
async function getLoyaltyRewardInformation(orderInfo, loyaltyAccountId) {
  const loyaltyRewardInfo = {};
  const program = await getDefaultLoyaltyProgram();
  // Show loyalty reward option only when loyalty program is active and no reward has been redeemed for this order.
  // By default, the loyalty reward option is hidden.
  loyaltyRewardInfo.showRewardOption = false;
  if (program && program.status === "ACTIVE" && !orderInfo.rewards) {
    // Loyalty API support redeem multiple rewards for one order, this example only allows one reward for each order.
    loyaltyRewardInfo.showRewardOption = true;
    if (loyaltyAccountId) {
      // if loyaltyAccountId is specified, start list all the available rewards
      // A reward is available when:
      //  * the reward can be applied to the order item and
      //  * the point balance of the account is greater than the reward point
      try {
        const { result: { loyaltyAccount } } = await loyaltyApi.retrieveLoyaltyAccount(loyaltyAccountId);

        loyaltyRewardInfo.loyaltyAccountId = loyaltyAccountId;
        loyaltyRewardInfo.balance = loyaltyAccount.balance;
        loyaltyRewardInfo.availableRewardTiers = [];
        loyaltyRewardInfo.unavailableRewardTiers = [];

        // The category information is not in the orderInfo, so we need get the categoryId from the item
        // which is related to the current item variation object.
        const { result: { relatedObjects } } = await catalogApi.retrieveCatalogObject(orderInfo.lineItems[0].catalogObjectId, true);
        const relatedItems = relatedObjects.filter(object => object.type === "ITEM");
        const eligibleCategoryId = relatedItems.length > 0 ? relatedItems[0].itemData.categoryId : null;
        // get the catalog item variation object id associated with this order in order to
        // check if the item based reward can apply to the order
        const eligibleItemId = orderInfo.lineItems[0].catalogObjectId;
        for (const rewardTier of program.rewardTiers) {
          if (rewardTier.points <= loyaltyAccount.balance &&
            (rewardTier.definition.scope === "ORDER" ||
              (rewardTier.definition.scope === "ITEM_VARIATION" && rewardTier.definition.catalogObjectIds.indexOf(eligibleItemId) >= 0) ||
              (rewardTier.definition.scope === "CATEGORY" && eligibleCategoryId && rewardTier.definition.catalogObjectIds.indexOf(eligibleCategoryId) >= 0)
            )
          ) {
            // In this example, the reward is available when:
            // loyalty account balance is enough to redeem this reward AND
            // the reward is either a order level reward or can be applied to this order item
            loyaltyRewardInfo.availableRewardTiers.push(rewardTier);
          } else {
            loyaltyRewardInfo.unavailableRewardTiers.push(rewardTier);
          }
        }
      } catch (error) {
        if (error.status === 404) {
          // If the loyalty account is not found, we mark this status so that the UI can show the account not found status.
          loyaltyRewardInfo.accountNotFound = true;
        } else {
          // Unknonw error, throw to display error page.
          throw error;
        }
      }
    }
  }

  return loyaltyRewardInfo;
}

/**
 * Description:
 * Get the information about:
 * 1. whether we should show the loyalty point accumulate option.
 * 2. if #1 is true, return the eligible points or accumulated points
 *
 * Notes: this method should be called only when the order is paid.
 *
 * @param {*} orderId The current order that is for accumulating loyal points
 *
 * @returns The loyalty point accumulate information.
 */
async function getLoyaltyPointAccumulateInformation(orderId) {
  const program = await getDefaultLoyaltyProgram();
  const loyaltyAccumulateInfo = {};

  // By default, isEligibleForAccruePoint is set to false to hide the loyalty point accumulate option.
  loyaltyAccumulateInfo.isEligibleForAccruePoint = false;

  // Add loyalty point accumulate only when the program is activated
  if (program && program.status === "ACTIVE") {
    // Check if this order is eligible for accumulating loyalty point, we check two things:
    // 1. If the order has had loyalty points accumulated
    // 2. If #1 is false, calculate how many points should be earned and check if the order amount meet the minimum accumulating amount.

    // First check if this order has had points accumulated
    // Filter the events that is related to accumulate point
    const { result: { events } } = await loyaltyApi.searchLoyaltyEvents({
      query: {
        filter: {
          orderFilter: {
            orderId: orderId
          },
          typeFilter: {
            types: ["ACCUMULATE_POINTS"]
          }
        }
      }
    });

    // We skip accruing point if a loyalty accumulate point event has been found with the orderId,
    // which means this order has been used to accumulated points for an loyalty account,
    //
    // Otherwise, we check if the order amount is big enough to accumulate at least 1 point
    if (!events || events.length == 0) {
      // There is no event indicating the point is accumulated for this order.
      // Calculate how many points should be earned
      const { result: { points } } = await loyaltyApi.calculateLoyaltyPoints(program.id, {
        orderId: orderId
      });
      // Set isEligibleForAccruePoint to true when there is more than 1 point to be accumulated
      loyaltyAccumulateInfo.isEligibleForAccruePoint = points > 0;
      // Set the eligiblePoint so that UI can show how many points can be accumulated
      loyaltyAccumulateInfo.eligiblePoint = points;
    } else {
      // The loyalty point has been accumulated from this current order
      // Set the points that is accumulated
      loyaltyAccumulateInfo.accumulatePoints = events[0].accumulatePoints;
    }
  }

  return loyaltyAccumulateInfo;
}

// Makes API instances and util functions importable
module.exports = {
  squareApplicationId,
  catalogApi,
  locationsApi,
  paymentsApi,
  ordersApi,
  loyaltyApi,
  retrieveOrderAndLocation,
  getDefaultLoyaltyProgram,
  getLoyaltyAccountByPhoneNumber,
  getLoyaltyRewardInformation,
  getLoyaltyPointAccumulateInformation
};
