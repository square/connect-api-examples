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
const { Client } = require("square");
const colors = require("colors/safe");

// Load environment variables from .env file
const { error } = require("dotenv").config();
if (error && process.env.NODE_ENV !== "production") {
  const warningMessage = `Failed to load .env file. Be sure that create a .env file at the root of this examples directory
or set environment variables.You can find an example in the .env.example file provided.`;

  console.error(colors.bold.yellow(warningMessage));
}

// Create client config from environment variables
const clientConfig = {
  environment: process.env.NODE_ENV,
  accessToken: process.env.SQUARE_ACCESS_TOKEN,
  userAgentDetail: "sample_app_node_invoices" // Remove or replace this detail when building your own app
};

const defaultClient = new Client(clientConfig);

// Instances of Api that are used
// You can add additional APIs here if you so choose
const {
  cardsApi,
  customersApi,
  locationsApi,
  ordersApi,
  invoicesApi
} = defaultClient;

// Makes API instances and util functions importable
module.exports = {
  cardsApi,
  customersApi,
  locationsApi,
  ordersApi,
  invoicesApi,
};
