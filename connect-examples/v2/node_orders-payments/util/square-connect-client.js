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

// Makes API instances importable
module.exports = {
  config,
  SquareConnect,
  catalogInstance,
  locationInstance,
  paymentInstance,
  orderInstance,
};

