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

/* eslint no-console: 0 */

const SquareConnect = require("square-connect");
const config = require("../../config.json")["sandbox"]; // We don't recommend to run this script in production environment
const readline = require("readline");
const crypto = require("crypto");
const { program } = require("commander");

const defaultClient = SquareConnect.ApiClient.instance;
// Default connect to sandbox.
defaultClient.basePath = config.path;

// Configure OAuth2 access token for authorization: oauth2
const oauth2 = defaultClient.authentications["oauth2"];
oauth2.accessToken = config.squareAccessToken;

// Configure customer API instance
const customerApi = new SquareConnect.CustomersApi();

/*
 * Add two customers, one with card on file and one without card on file.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/customers-api/create-customer
 */
async function addCustomers() {
  try {
    // Create first customer with card on file
    const { customer } = await customerApi.createCustomer({
      idempotency_key: crypto.randomBytes(32).toString("hex"),
      given_name: "Ryan",
      family_name: "Nakamura",
      email_address: "nakamura710@square-example.com" // it is a fake email
    });

    await customerApi.createCustomerCard(customer.id, {
      card_nonce: "cnon:card-nonce-ok"
    });

    // create second customer with no card on file
    await customerApi.createCustomer({
      idempotency_key: crypto.randomBytes(32).toString("hex"),
      given_name: "Kaitlyn",
      family_name: "Spindel",
      email_address: "kaitlyn@square-example.com" // it is a fake email
    });

    console.log("Successfully created customers");
  } catch (error) {
    console.error("Create customers failed: ", error);
  }
}

/*
 * Function that clears all customers in your sandbox.
 * WARNING: This is permanent and irreversable!
 */
async function clearCustomers() {
  try {
    const { customers } = await customerApi.listCustomers();
    if (customers) {
      for (const key in customers) {
        const customer = customers[key];
        await customerApi.deleteCustomer(customer.id);
      }
      console.log("Successfully deleted customers");
    } else {
      console.log("No customers to delete");
    }
  } catch (error) {
    console.error("Error in deleting customers:", error);
  }
}

/*
 * Main driver for the script.
 */
program
  .command("clear")
  .description("clear all customers in your sandbox.")
  .action(() => {
    const rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout,
    });
    rl.question("Are you sure you want to clear all customers in your sandbox? (y/N) ", (ans) => {
      if (ans.toUpperCase() === "Y") {
        clearCustomers();
      } else if (ans.toUpperCase() === "N") {
        console.log("Aborting clear.");
      }
      rl.close();
    });
  });

program
  .command("generate")
  .description("create two customers, one with card on file and one without.")
  .action(() => {
    addCustomers();
  });

program.parse(process.argv);
