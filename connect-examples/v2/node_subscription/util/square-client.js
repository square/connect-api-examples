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

const { Client, Environment } = require("square");
const axios = require("axios").default;
const colors = require("colors");

// Load environment variables from .env file
const { error } = require("dotenv").config();
if (error && process.env.NODE_ENV !== "production") {
  const warningMessage = `WARNING: Failed to load .env file. Be sure that create a .env file at the root of this examples directory
or set environment variables.You can find an example in the .env.example file provided.`;

  console.error(colors.bold.yellow(warningMessage));
}
// Set Square Connect credentials
const config = {
  environment: process.env.NODE_ENV,
  accessToken: process.env.SQUARE_ACCESS_TOKEN,
  userAgentDetail: "sample_app_node_subscription" // Remove or replace this detail when building your own app
};

// Create new Client instance
// Extract instances of the API that are used
const {
  catalogApi,
  locationsApi,
  customersApi,
  subscriptionsApi
} = new Client(config);

/**
 * This utlity method is used to clear the `canceledDate` field of a subscription.
 * This method sends a request to the UpdateSubscription endpoint to update the
 * `canceled_date` field to be null.
 *
 * Currently Square's Node.js sdk does not support sending nulls.
 * @param {subscriptionId, version} object
 */
const revertCanceledSubscription = async ({ subscriptionId, version }) => {
  // Conditionally set base URL for Environment
  let baseUrl = config.environment === Environment.Production
    ? "https://connect.squareup.com/v2" : "https://connect.squareupsandbox.com/v2";

  try {
    await axios(`${baseUrl}/subscriptions/${subscriptionId}`, {
      method: "PUT",
      data: {
        subscription: {
          version: parseInt(version),
          // Note: Since this is a direct call to Square's servers the
          // fields are snake case.
          canceled_date: null
        }
      },
      headers: {
        Authorization: `Bearer ${config.accessToken}`,
        "Square-Version": "2020-11-18"
      }
    });
  } catch (error) {
    const { response } = error;

    let newError = new Error("Failed to revert canceled subscription");
    if (response && response.status) {
      newError.status = response.status;
    }
    if (response && response.data && response.data.errors) {
      newError.errors = response.data.errors;
    }

    // Throws new error object in the format the example expects.
    throw newError;
  }
};

// Makes API instances and util functions importable
module.exports = {
  revertCanceledSubscription,
  catalogApi,
  locationsApi,
  customersApi,
  subscriptionsApi
};
