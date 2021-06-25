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
  locationsApi,
  teamApi,
} = new Client(config);

/**
 * Retrieve the location
 * @param {*} locationId
 * @returns {Location} location specified in .env file
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
  const services = [
    {
      amount: 6400,
      description: "Leave-in treatment that will instantly intensity your hair color radiance. Will result in increased shine.",
      name: "Hair Color Treatment"
    }, {
      amount: 3500,
      description: "Any women's haircut - examples include bobs, pixie, shag, bangs, layers, etc.",
      name: "Women's Haircut"
    }, {
      amount: 3000,
      description: "Any men's haircut - examples include blunt cut fringe, buzzcut, undercut, etc.",
      name: "Men's Haircut"
    }, {
      amount: 4900,
      description: "We will shampoo and blow dry your hair in a style that suits your hair and personality.",
      name: "Shampoo & Blow Dry"
    }
  ];
  try {
    // create appointment services
    await Promise.all(services.map(service => {
      catalogApi.upsertCatalogObject({
        idempotencyKey: uuidv4(),
        object: {
          id: `#${uuidv4()}`,
          itemData: {
            description: service.description,
            name: service.name,
            productType: "APPOINTMENTS_SERVICE",
            variations: [
              {
                id: `#${uuidv4()}`,
                itemVariationData: {
                  availableForBooking: true,
                  inventoryAlertType: "NONE",
                  name: "Regular",
                  priceMoney: {
                    amount: service.amount,
                    currency: location.currency,
                  },
                  pricingType: "FIXED_PRICING",
                  serviceDuration: 1800000, // 30 minutes
                  teamMemberIds,
                },
                presentAtAllLocations: false,
                presentAtLocationIds: [location.id],
                type: "ITEM_VARIATION",
              }
            ],
          },
          presentAtAllLocations: false,
          presentAtLocationIds: [location.id],
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
        idempotencyKey: uuidv4(),
        teamMember: {
          assignedLocations: {
            assignmentType: "EXPLICIT_LOCATIONS",
            location_ids: [locationId],
          },
          ...newTeamMember
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

/*
 * Main driver for the script.
 */
program
  .command("generate")
  .description("creates two team members using team API and hair services using catalog API")
  .action(async() => {
    // retrieve the location
    const locationId = process.env.SQUARE_LOCATION_ID;
    const location = await retrieveLocation(locationId);
    if (!location) {
      console.error("Fetching location failed. Exiting script");
      return;
    }
    // create team members
    const teamMembers = await createTeamMembers(location.id);
    if (teamMembers.length > 0) {
      // create appointment services
      createAppointmentServices(teamMembers, location)
    }
  });

program.parse(process.argv);
