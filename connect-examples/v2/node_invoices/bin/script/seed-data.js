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

const { Client, Environment } = require("square");
const readline = require("readline");
const { v4: uuidv4 } = require("uuid");
const { program } = require("commander");
require('dotenv').config()

// We don't recommend to run this script in production environment
const config = {
  environment: Environment.Sandbox,
  accessToken: process.env.SQUARE_ACCESS_TOKEN

}

// Configure customer API instance
const {
  customersApi
} = new Client(config);

/*
 * Add two customers, one with card on file and one without card on file.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/customers-api/create-customer
 */
async function addCustomers() {
  try {
    // Create first customer with card on file
    const { result : { customer } } = await customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Ryan",
      familyName: "Nakamura",
      emailAddress: "nakamura710@square-example.com" // it is a fake email
    });

    await customersApi.createCustomerCard(customer.id, {
      cardNonce: "cnon:card-nonce-ok"
    });

    // create second customer with no card on file
    await customersApi.createCustomer({
      idempotencyKey: uuidv4(),
      givenName: "Kaitlyn",
      familyName: "Spindel",
      emailAddress: "kaitlyn@square-example.com" // it is a fake email
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
    const { result : { customers } } = await customersApi.listCustomers();
    if (customers) {
      for (const key in customers) {
        const customer = customers[key];
        await customersApi.deleteCustomer(customer.id);
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
