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
const { v4: uuidv4 } = require("uuid");
const { program } = require("commander");
const sample_data = require("./sample-seed-data.json");

const defaultClient = SquareConnect.ApiClient.instance;
// Default connect to sandbox.
defaultClient.basePath = config.path;

// Configure OAuth2 access token for authorization: oauth2
const oauth2 = defaultClient.authentications["oauth2"];
oauth2.accessToken = config.squareAccessToken;

// Configure customer API instance
const catalogApi = new SquareConnect.CatalogApi();
const customersApi = new SquareConnect.CustomersApi();

/**
 * Add two customers, one with card on file and one without card on file.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/customers-api/create-customer
 */
async function addCustomers() {
  try {
    // Create first customer with card on file
    const { customer } = await customersApi.createCustomer({
      idempotency_key: uuidv4(),
      given_name: "John",
      family_name: "Doe",
      email_address: "johndoe@square-example.com" // it is a fake email
    });

    await customersApi.createCustomerCard(customer.id, {
      card_nonce: "cnon:card-nonce-ok"
    });

    // create second customer with no card on file
    await customersApi.createCustomer({
      idempotency_key: uuidv4(),
      given_name: "Amelia",
      family_name: "Earhart",
      email_address: "ameliae@square-example.com" // it is a fake email
    });

    console.log("Successfully created customers");
  } catch (error) {
    console.error("Create customers failed: ", error);
  }
}

/**
 * Function that clears all customers in your sandbox.
 * WARNING: This is permanent and irreversable!
 */
async function clearCustomers() {
  try {
    const { customers } = await customersApi.listCustomers();
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

/**
 * addSubscriptionPlans adds all the plans defined in the sample-seed-data.json file as catalog objects
 * Visit for more information:
 * https://developer.squareup.com/reference/square/catalog-api/batch-upsert-catalog-objects
 */
async function addSubscriptionPlans() {
  const batches = [{
    objects: []
  }];
  const batchUpsertCatalogRequest = {
    // Each request needs a unique idempotency key.
    idempotency_key: uuidv4(),
    batches: batches,
  };

  // Iterate through each item in the sample-seed-data.json file.
  for (const key in sample_data) {
    const currentCatalogItem = sample_data[key];
    // Add the object data to the batch request item.
    batchUpsertCatalogRequest.batches[0].objects.push(currentCatalogItem.data);
  }

  try {
    // We call the Catalog API function batchUpsertCatalogObjects to upload all our
    // items at once.
    await catalogApi.batchUpsertCatalogObjects(
      batchUpsertCatalogRequest
    );

    console.log("Successfully created catalog items.");
  } catch (error) {
    console.error("Updating catalog items failed: ", error);
  }
}

/**
 * subscription plans cannot be deleted, we can dislable all the subscripiton plans for all locations
 */
async function disableSubscriptionPlans() {
  const batches = [{
    objects: []
  }];
  const batchUpsertCatalogRequest = {
    // Each request needs a unique idempotency key.
    idempotency_key: uuidv4(),
    batches: batches,
  };

  try {
    const { objects } = await catalogApi.listCatalog({ types: "SUBSCRIPTION_PLAN" });
    if (objects && objects.length > 0) {
      for (const key in objects) {
        const object = objects[key];
        // Add the object data with `present_at_all_locations: false` to the batch request item.
        batchUpsertCatalogRequest.batches[0].objects.push({
          id: object.id,
          type: object.type,
          present_at_all_locations: false,
          subscription_plan_data: {
            name: object.subscription_plan_data.name,
            phases: object.subscription_plan_data.phases
          },
          version: object.version
        });
      }

      // We call the Catalog API function batchUpsertCatalogObjects to update all subscription plans at once
      await catalogApi.batchUpsertCatalogObjects(
        batchUpsertCatalogRequest
      );
      console.log("Successfully disabled all subscription plans.");
    } else {
      console.log("No subscription plans to disable from catalog");
    }
  } catch (error) {
    console.error("Error in deleting catalog items:", error);
  }
}

/*
 * Main driver for the script.
 */
program
  .command("clear")
  .description("clear all customers and disable all subscription plans in your sandbox.")
  .action(() => {
    const rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout,
    });
    rl.question("Are you sure you want to clear all customers and disable all subscription plans in your sandbox? (y/N) ", async (ans) => {
      if (ans.toUpperCase() === "Y") {
        await disableSubscriptionPlans();
        await clearCustomers();
      } else if (ans.toUpperCase() === "N") {
        console.log("Aborting clear.");
      }
      rl.close();
    });
  });

program
  .command("generate")
  .description("create three subscription plans and create two customers, one with card on file.")
  .action(async () => {
    await addCustomers();
    await addSubscriptionPlans();
  });

program.parse(process.argv);
