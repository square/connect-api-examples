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

/* eslint no-console: 0 */

const { Client, Environment } = require("square");
const { v4: uuidv4 } = require("uuid");
const { program } = require("commander");
require("dotenv").config()

// We don't recommend to run this script in the production environment
const config = {
  accessToken: process.env.SQUARE_ACCESS_TOKEN,
  environment: Environment.Sandbox,
}

// Configure catalog & team API instance
const {
  catalogApi,
  teamApi,
} = new Client(config);

/**
 * Creates the appointment services using Catalog API
 * You can create the services via the seller dashboard as well:
 * https://squareupsandbox.com/dashboard/items/services
 * https://squareup.com/dashboard/items/services
 * @param {String[]} teamMemberIds - team member ids to assign to services
 */
async function createAppointmentServices(teamMemberIds) {
  const serviceNames = ["Hair Color Treatment", "Women's Haircut", "Men's Haircut", "Shampoo & Blow Dry"];
  try {
    Promise.all(serviceNames.map(serviceName => {
      catalogApi.upsertCatalogObject({
        idempotencyKey: uuidv4(),
        object: {
          id: uuidv4(),
          itemData: {
            name: serviceName,
            productType: "APPOINTMENTS_SERVICE",
            variations: [
              {
                id: uuidv4(),
                itemVariationData: {
                  availableForBooking: true,
                  inventoryAlertType: "NONE",
                  name: "Regular",
                  pricingType: "VARIABLE_PRICING",
                  serviceDuration: 1800000, // 30 minutes
                  teamMemberIds,
                },
                presentAtAllLocations: true,
                type: "ITEM_VARIATION",
              }
            ]
          },
          type: "ITEM",
        }
      }
      );
    }));
    console.log("Creation of appointment services succeeded");
  } catch (error) {
    console.error("Creating appointment services failed: ", error);
  }
}

/**
 * Creates two team members and returns the ids of the new team members.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/team-api/create-team-member
 * @returns {String[]} array of the new team members' ids
 */
async function createTeamMembers() {
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
    Promise.all(teamMembers.map(newTeamMember =>
      teamApi.createTeamMember({
        idempotencyKey: uuidv4(),
        teamMember: newTeamMember
      })
    ))
      .then(responses => {
        responses.map(response => {
          const { teamMember } = response.result;
          teamMemberIds.push(teamMember.id);
        })
      })
    console.log("Creation of team members succeeded");
  } catch (error) {
    console.error("Creating team members failed: ", error);
  }
  return teamMemberIds;
}

/*
 * Main driver for the script.
 */
program
  .command("generate")
  .description("creates two team members using team API and hair services using catalog API")
  .action(async() => {
    const teamMembers = await createTeamMembers();
    if (teamMembers) {
      createAppointmentServices(teamMembers)
    }
  });

program.parse(process.argv);
