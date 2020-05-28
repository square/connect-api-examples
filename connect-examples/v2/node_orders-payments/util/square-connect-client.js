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

const SquareConnect = require("square-connect");
const config = require("../config.json")[process.env.NODE_ENV];
const OrderInfo = require("../models/order-info");
const LocationInfo = require("../models/location-info");

// Set Square Connect credentials
const defaultClient = SquareConnect.ApiClient.instance;
defaultClient.basePath = config.path;

// Configure OAuth2 access token for authorization: oauth2
const oauth2 = defaultClient.authentications["oauth2"];
oauth2.accessToken = config.squareAccessToken;

// Instances of Api that are used
// You can add additional APIs here if you so choose
const catalogApi = new SquareConnect.CatalogApi();
const locationApi = new SquareConnect.LocationsApi();
const orderApi = new SquareConnect.OrdersApi();
const paymentApi = new SquareConnect.PaymentsApi();
const loyaltyApi = new SquareConnect.LoyaltyApi();

/**
 * Description:
 * Retrieve the order and location informaiton that are widely used in many pages in this example.
 *
 * @param {*} order_id The id of the order
 * @param {*} location_id The id of the location where the order belongs to
 *
 * @returns object{ order_info, location_info }
 */
async function retrieveOrderAndLocation(order_id, location_id) {
  const { orders } = await orderApi.batchRetrieveOrders(location_id, {
    order_ids: [order_id],
  });
  const { location } = await locationApi.retrieveLocation(location_id);
  if (!orders || orders.length == 0 || !location) {
    const error = new Error("Cannot find order");
    error.status = 404;
    throw error;
  }

  return {
    order_info: new OrderInfo(orders[0]),
    location_info: new LocationInfo(location),
  };
}

/**
 * Description:
 * get the default loyalty program.
 *
 * @returns The loyalty propgram; if there is no loyalty program, return `null`.
 */
async function getDefaultLoyaltyProgram() {
  const { programs } = await loyaltyApi.listLoyaltyPrograms();
  return programs && programs.length > 0 ? programs[0] : null;
}

/**
 * Description:
 * Get the loyalty account from a phone number.
 *
 * @param {*} formated_phone_number The phone number that's formatted as expected.
 *                                  The format must be like "+13334441111"
 *
 * @returns The loyalty account that associate with the phone number; If not found, return `null`.
 */
async function getLoyaltyAccountByPhoneNumber(formated_phone_number) {
  const { loyalty_accounts } = await loyaltyApi.searchLoyaltyAccounts({
    query: {
      mappings: [
        {
          type: "PHONE",
          value: formated_phone_number
        }
      ]
    }
  });

  return loyalty_accounts ? loyalty_accounts[0] : null;
}

/**
 * Description:
 * Retrieve the loyalty reward information about:
 * 1. whether we should show the reward option for the given order.
 * 2. the reward option that are either available or unavailable.
 *
 * Note: This method should be called before the order is paid
 *
 * @param {*} formated_phone_number The phone number that's formatted as expected.
 *                                  The format must be like "+13334441111"
 *
 * @returns The loyalty reward information.
 */
async function getLoyaltyRewardInformation(order_info, loyalty_account_id) {
  const loyalty_reward_info = {};
  const program = await getDefaultLoyaltyProgram();
  // Show loyalty reward option only when loyalty program is active and no reward has been redeemed for this order.
  // By default, the loyalty reward option is hidden.
  loyalty_reward_info.show_reward_option = false;
  if (program && program.status === "ACTIVE" && !order_info.rewards) {
    // Loyalty API support redeem multiple rewards for one order, this example only allows one reward for each order.
    loyalty_reward_info.show_reward_option = true;
    if (loyalty_account_id) {
      // if loyalty_account_id is specified, start list all the available rewards
      // A reward is available when:
      //  * the reward can be applied to the order item and
      //  * the point balance of the account is greater than the reward point
      let loyalty_account;
      try {
        const result = await loyaltyApi.retrieveLoyaltyAccount(loyalty_account_id);
        loyalty_account = result.loyalty_account;
      } catch (error) {
        if (error.status === 404) {
          // If the loyalty account is not found, we mark this status so that the UI can show the account not found status.
          loyalty_reward_info.account_not_found = true;
        } else {
          // Unknonw error, throw to display error page.
          throw error;
        }
      }

      if (loyalty_account) {
        loyalty_reward_info.loyalty_account_id = loyalty_account_id;
        loyalty_reward_info.balance = loyalty_account.balance;
        loyalty_reward_info.available_reward_tiers = [];
        loyalty_reward_info.unavailable_reward_tiers = [];

        // The category information is not in the order_info, so we need get the category_id from the item
        // which is related to the current item variation object.
        const { related_objects } = await catalogApi.retrieveCatalogObject(order_info.lineItems[0].catalog_object_id, {
          includeRelatedObjects: true
        });
        const related_items = related_objects.filter(object => object.type === "ITEM");
        const eligible_category_id = related_items.length > 0 ? related_items[0].item_data.category_id : null;
        // get the catalog item variation object id associated with this order in order to
        // check if the item based reward can apply to the order
        const eligible_item_id = order_info.lineItems[0].catalog_object_id;
        for (const reward_tier of program.reward_tiers) {
          if (reward_tier.points <= loyalty_account.balance &&
            (reward_tier.definition.scope === "ORDER" ||
              (reward_tier.definition.scope === "ITEM_VARIATION" && reward_tier.definition.catalog_object_ids.indexOf(eligible_item_id) >= 0 ) ||
              (reward_tier.definition.scope === "CATEGORY" && eligible_category_id && reward_tier.definition.catalog_object_ids.indexOf(eligible_category_id) >= 0 )
            )
          ) {
            // In this example, the reward is available when:
            // loyalty account balance is enough to redeem this reward AND
            // the reward is either a order level reward or can be applied to this order item
            loyalty_reward_info.available_reward_tiers.push(reward_tier);
          } else {
            loyalty_reward_info.unavailable_reward_tiers.push(reward_tier);
          }
        }
      }
    }
  }

  return loyalty_reward_info;
}

/**
 * Description:
 * Get the information about:
 * 1. whether we should show the loyalty point accumulate option.
 * 2. if #1 is true, return the eligible points or accumulated points
 *
 * Notes: this method should be called only when the order is paid.
 *
 * @param {*} order_id The current order that is for accumulating loyal points
 *
 * @returns The loyalty point accumulate information.
 */
async function getLoyaltyPointAccumulateInformation(order_id) {
  const program = await getDefaultLoyaltyProgram();
  const loyalty_accumulate_info = {};

  // By default, is_eligible_for_accrue_point is set to false to hide the loyalty point accumulate option.
  loyalty_accumulate_info.is_eligible_for_accrue_point = false;

  // Add loyalty point accumulate only when the program is activated
  if (program && program.status === "ACTIVE") {
    // Check if this order is eligible for accumulating loyalty point, we check two things:
    // 1. If the order has had loyalty points accumulated
    // 2. If #1 is false, calculate how many points should be earned and check if the order amount meet the minimum accumulating amount.

    // First check if this order has had points accumulated
    // Filter the events that is related to accumulate point
    const { events } = await loyaltyApi.searchLoyaltyEvents({
      query: {
        filter: {
          order_filter: {
            order_id: order_id
          },
          type_filter: {
            types: ["ACCUMULATE_POINTS"]
          }
        }
      }
    });

    // We skip accruing point if a loyalty accumulate point event has been found with the order_id,
    // which means this order has been used to accumulated points for an loyalty account,
    //
    // Otherwise, we check if the order amount is big enough to accumulate at least 1 point
    if (!events || events.length == 0) {
      // There is no event indicating the point is accumulated for this order.
      // Calculate how many points should be earned
      const { points } = await loyaltyApi.calculateLoyaltyPoints(program.id, {
        order_id: order_id
      });
      // Set is_eligible_for_accrue_point to true when there is more than 1 point to be accumulated
      loyalty_accumulate_info.is_eligible_for_accrue_point = points > 0;
      // Set the eligible_point so that UI can show how many points can be accumulated
      loyalty_accumulate_info.eligible_point = points;
    } else {
      // The loyalty point has been accumulated from this current order
      // Set the points that is accumulated
      loyalty_accumulate_info.accumulate_points = events[0].accumulate_points;
    }
  }

  return loyalty_accumulate_info;
}

// Makes API instances and util functions importable
module.exports = {
  config,
  catalogApi,
  locationApi,
  paymentApi,
  orderApi,
  loyaltyApi,
  retrieveOrderAndLocation,
  getDefaultLoyaltyProgram,
  getLoyaltyAccountByPhoneNumber,
  getLoyaltyRewardInformation,
  getLoyaltyPointAccumulateInformation
};
