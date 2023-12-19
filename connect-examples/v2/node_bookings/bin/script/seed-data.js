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

const sampleData = require("./service-items.json");
const { Client, Environment } = require("square");
const readline = require("readline");
const crypto = require("crypto");
const { program } = require("commander");
require("dotenv").config();

// We don't recommend to run this script in the production environment
const config = {
  accessToken: process.env.SQ_ACCESS_TOKEN,
  environment: Environment.Sandbox,
};

// Configure catalog & team API instance
const {
  catalogApi,
  customersApi,
  locationsApi,
  teamApi,
} = new Client(config);

// assign a SKU to all the hair appointment services so we can search & delete them later on
const HAIR_SERVICES_SKU = "BOOKINGS-SAMPLE-APP-HAIR-SERVICE";
// assign a reference id to customers so we can search & delete them later on
const BOOKINGS_SAMPLE_APP_REFERENCE_ID = "BOOKINGS-SAMPLE-APP";

/**
 * Retrieve the location
 * @param {String} locationId
 * @returns {Location}
 */
async function retrieveLocation(locationId) {
  try {
    const { result: { location } } = await locationsApi.retrieveLocation(locationId);
    console.log(`Retriving location ${locationId} succeeded`);
    return location;
  } catch (error) {
    if (error.statusCode === 401) {
      console.error("Unauthorized error - verify that your access token is for the sandbox environment");
    }
    console.error(`Retriving the location ${locationId} failed: `, error);
  }
}

/**
 * Creates the appointment services using Catalog API
 * You can create the services via the seller dashboard as well:
 * https://squareupsandbox.com/dashboard/items/services
 * https://squareup.com/dashboard/items/services
 * @param {String[]} teamMemberIds - team member ids to assign to services
 * @param {Location} location - location to assign services to
 */
async function createAppointmentServices(teamMemberIds, location) {
  const items = [];
  for (const itemId in sampleData) {
    // set the currency and team members and location for the item
    const item = sampleData[itemId];
    item.presentAtLocationIds = [ location.id ];
    const { itemData: { variations } } = item;
    for (const variation of variations) {
      variation.presentAtLocationIds = [ location.id ];
      variation.itemVariationData.sku = HAIR_SERVICES_SKU;
      variation.itemVariationData.priceMoney.currency = location.currency;
      variation.itemVariationData.teamMemberIds = teamMemberIds;
    }
    items.push(item);
  }
  try {
    // create appointment services
    await catalogApi.batchUpsertCatalogObjects({
      batches: [
        {
          objects: items,
        }
      ],
      idempotencyKey: crypto.randomUUID()
    });
    console.log("Creation of appointment services succeeded");
  } catch (error) {
    console.error("Creating appointment services failed: ", error);
  }
}

/**
 * Creates two team members and returns the ids of the new team members.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/team-api/create-team-member
 * @param {String} locationId
 * @returns {String[]} array of the new team members' ids
 */
async function createTeamMembers(locationId) {
  const teamMembers = [
    {
      emailAddress: "johnsmith1234@square-example.com",
      familyName: "Smith",
      givenName: "John",
    },
    {
      emailAddress: "amyjohnson1234@square-example.com",
      familyName: "Johnson",
      givenName: "Amy",
    }
  ];
  const teamMemberIds = [];
  try {
    const responses = await Promise.all(teamMembers.map(newTeamMember =>
      teamApi.createTeamMember({
        idempotencyKey: crypto.randomUUID(),
        teamMember: {
          assignedLocations: {
            assignmentType: "EXPLICIT_LOCATIONS",
            locationIds: [ locationId ],
          },
          ...newTeamMember,
        },
      })
    ));
    responses.map(response => {
      const { teamMember } = response.result;
      teamMemberIds.push(teamMember.id);
    });
    console.log("Creation of team members succeeded");
  } catch (error) {
    console.error("Creating team members failed: ", error);
  }
  return teamMemberIds;
}

/**
 * Search active team members assigned to the location
 * @param {String} locationId
 * @return {TeamMember[]}
 */
async function searchActiveTeamMembers(locationId) {
  try {
    const { result: { teamMembers } } = await teamApi.searchTeamMembers({
      query: {
        filter: {
          locationIds: [ locationId ],
          status: "ACTIVE",
        },
      },
    });
    return teamMembers;
  } catch (error) {
    console.error(`Searching for team members for location ${locationId} failed: `, error);
  }
}

/**
 * Deactivates the team members for the location
 * @param {String} locationId
 */
async function deactivateTeamMembers(locationId) {
  try {
    const teamMembers = await searchActiveTeamMembers(locationId);
    if (!teamMembers || !teamMembers.length) {
      console.log(`No team members for location ${locationId} to deactivate.`);
      return;
    }
    const teamMembersMap = teamMembers.reduce((map, teamMember) => {
      map[teamMember.id] = {
        teamMember: {
          status: "INACTIVE",
        },
      };
      return map;
    }, {});
    const { result } = await teamApi.bulkUpdateTeamMembers({
      teamMembers: teamMembersMap
    });
    console.log("Successfully deactivated team members ", [ ...Object.keys(result.teamMembers) ]);
  } catch (error) {
    console.error(`Deactivating team members for location ${locationId} failed: `, error);
  }
}

/**
 * Search for catalog items with the specified product type and location id and sku
 * @param {String} locationId
 * @param {String} productType
 * @param {String} sku
 * @returns {CatalogObject[]}
 */
async function searchCatalogItems(locationId, productType, sku) {
  try {
    const { result: { items } } = await catalogApi.searchCatalogItems({
      enabledLocationIds: [ locationId ],
      productTypes: [ productType ],
      textFilter: sku,
    });
    console.info(`Successfully retrieved catalog items with product type ${productType} and locationId ${locationId}`);
    return items;
  } catch (error) {
    console.error(`Searching for catalog items with product type ${productType} and locationId ${locationId} failed: `, error);
  }
}

/**
 * Delete all appointment service items for the location
 * WARNING: This is permanent and irreversable
 * @param {*} locationId
 */
async function clearAppointmentServices(locationId) {
  try {
    // get appointment services for the location & sku
    const serviceItems = await searchCatalogItems(locationId, "APPOINTMENTS_SERVICE", HAIR_SERVICES_SKU);
    // delete the appointment services
    if (serviceItems && serviceItems.length > 0) {
      const { result: { deletedObjectIds } } = await catalogApi.batchDeleteCatalogObjects({
        objectIds: serviceItems.map((item) => item.id)
      });
      console.log("Successfully deleted catalog items ", deletedObjectIds);
    } else {
      console.log(`No appointment services to delete for location ${locationId}`);
    }
  } catch (error) {
    console.error(`Failed to clear appointment services for location ${locationId}`, error);
  }
}

/**
 * Delete all customers created by the sample app
 * WARNING: This is permanent and irreversable
 */
async function clearCustomers() {
  try {
    const { result: { customers } }  = await customersApi.searchCustomers({
      query: {
        filter: {
          referenceId: {
            exact: BOOKINGS_SAMPLE_APP_REFERENCE_ID,
          }
        }
      }
    });

    if (!customers) {
      console.log("No customers were created by the app");
      return;
    }

    const customerIds = customers.map((customer) => customer.id);

    const deleteCustomerPromises = customerIds.map((customerId) => {
      return customersApi.deleteCustomer(customerId);
    });

    await Promise.all(deleteCustomerPromises);
    console.log("Successfully deleted customers", customerIds);

  } catch (error) {
    console.error("Failed to clear customers", error);
  }
}

/*
 * Main driver for the script.
 */
program
  .command("generate")
  .description("creates two team members using team API and hair services using catalog API")
  .action(async() => {
    // retrieve the location
    const locationId = process.env.SQ_LOCATION_ID;
    const location = await retrieveLocation(locationId);
    if (!location) {
      console.error("Fetching location failed. Exiting script");
      return;
    }
    // create team members
    const teamMembers = await createTeamMembers(location.id);
    if (teamMembers.length > 0) {
      // create appointment services
      createAppointmentServices(teamMembers, location);
    }
  });

program
  .command("clear")
  .description("clears the team members and appointment services assigned to the location")
  .action(async() => {
    const rl = readline.createInterface({
      input: process.stdin,
      output: process.stdout,
    });
    const locationId = process.env.SQ_LOCATION_ID;
    rl.question(`Are you sure you want to clear all appointment services created for location ${locationId}, deactivate team members and remove all customers created by the app? (y/n) `, async(ans) => {
      if (ans.toUpperCase() === "Y") {
        // deactivate team members
        await deactivateTeamMembers(locationId);
        // clear appointment services
        await clearAppointmentServices(locationId);
        // clear customers created by app
        await clearCustomers();
      } else if (ans.toUpperCase() === "N") {
        console.log("Aborting clear.");
      }
      rl.close();
    });
  });

program.parse(process.argv);
