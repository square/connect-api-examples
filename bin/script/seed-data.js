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
require('dotenv').config();
const readline = require("readline");
const { v4: uuidv4 } = require("uuid");

const env = process.env[`ENVIRONMENT`].toLowerCase();
const accessToken = process.env[`SQUARE_ACCESS_TOKEN`];

// Client configuration
const config = {
  accessToken,
  environment: env
};

const {
  customersApi,
  locationsApi
} = new Client(config);

/**
 * Function to create a couple of customers (with card on file), so that those can be used
 * throughout the sample app.
 */
async function createCustomers() {
  try {
    const { result: { customer: customer1 } } = await customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Michael",
      familyName: "Scott",
      emailAddress: "michaelscott123@example.com"
    });

    await customersApi.createCustomerCard(customer1.id, {
      cardNonce: "cnon:card-nonce-ok"
    });

    const { result: { customer: customer2 } } = await customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Dwight",
      familyName: "Schrute",
      emailAddress: "dwightschrute@beetfarm.com"
    });

    await customersApi.createCustomerCard(customer2.id, {
      cardNonce: "cnon:card-nonce-ok"
    });

    console.log("Successfully created customers")
  } catch (error) {
    console.error("Create customers failed: ", error);
  }
}

/**
 * Function to clear all customers for the configured access token.
 * WARNING: This is permanent and irreversible!
 * Do not run this in production environment.
 */
async function clearCustomers() {
  try {
    const { result: { customers } } = await customersApi.listCustomers();
    if (!customers) {
      console.log("No customers to delete");
      return;
    }

    customers.forEach(customer => {
      customersApi.deleteCustomer(customer.id);
    });

    console.log("Successfully cleared all customers");
  } catch (error) {
    console.error("Clear customers failed: ", error);
  }
}

/*
* Main driver for the script.
*/
const args = process.argv.slice(2);
if (args[0] === "generate") {
  // generate new customers
  createCustomers();
} else if (args[0] === "clear") {
  // clear customers
  const ioInterface = readline.createInterface({
    input: process.stdin,
    output: process.stdout
  });
  ioInterface.question("Are you sure you want to clear all the customers in your business? (Y/N) ", (ans) => {
    ans = ans.toUpperCase();
    if (ans === "Y") {
      clearCustomers();
    } else if (ans === "N") {
      console.log("Aborting clear.");
    }
    ioInterface.close();
  });
} else if (args[0] === "-h" || args[0] === "--help") {
  console.log(
    "Please check the README.md for more information on how to run our catalog script."
    + "\nAvailable commands include:\n npm run seed - Generates catalog items for your sandbox catalog."
    + "\n npm run clear - Clears your sandbox catalog of all items."
    + "\n\n More information can also be found on our Quick Start guide at https://developer.squareup.com/docs/orders-api/quick-start/start."
  );
} else {
  console.log("Command not recognized. Please try again.");
}
