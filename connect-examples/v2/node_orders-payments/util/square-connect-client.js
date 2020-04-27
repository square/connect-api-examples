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
const catalogInstance = new SquareConnect.CatalogApi();
const locationInstance = new SquareConnect.LocationsApi();
const orderInstance = new SquareConnect.OrdersApi();
const paymentInstance = new SquareConnect.PaymentsApi();

/**
 * Description:
 * Retrive the order and location informaiton that are widely used in many pages in this example.
 *
 * @param {*} order_id The id of the order
 * @param {*} location_id The id of the location where the order belongs to
 */
async function retrieveOrderAndLocation(order_id, location_id) {
  const { orders } = await orderInstance.batchRetrieveOrders(location_id, {
    order_ids: [order_id],
  });
  const { location } = await locationInstance.retrieveLocation(location_id);
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

// Makes API instances and util functions importable
module.exports = {
  config,
  SquareConnect,
  catalogInstance,
  locationInstance,
  paymentInstance,
  orderInstance,
  retrieveOrderAndLocation,
};
