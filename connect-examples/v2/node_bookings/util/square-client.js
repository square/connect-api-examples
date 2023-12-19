/*
Copyright 2021 Square Inc.
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

const env = process.env["ENVIRONMENT"].toLowerCase();
const accessToken = process.env["SQ_ACCESS_TOKEN"];

// Set Square credentials
const config = {
  accessToken,
  environment: env,
  userAgentDetail: "sample_app_node_bookings" // Remove or replace this detail when building your own app
};

// Extract instances of Api that are used
// You can add additional APIs here if you so choose
const {
  customersApi,
  bookingsApi,
  catalogApi,
  locationsApi,
  teamApi
} = new Client(config);

module.exports = {
  bookingsApi,
  catalogApi,
  customersApi,
  locationsApi,
  teamApi
};
