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

const express = require("express");
const router = express.Router();
const { bookingsApi, customersApi } = require("../util/square-client");
const { v4: uuidv4 } = require("uuid");

/**
 * GET /bookings/create
 * 
 * Create a new booking, booking details and customer information is submitted
 * by form data. Create a new customer if necessary, otherwise use an existing
 * customer that matches the `firstName`, `lastName`, and `phoneNumber` to create
 * the booking.
 */
router.post("/create", async (req, res, next) => {
  const durationMinutes = req.body.duration;
  const serviceVariationId = req.body.serviceVariationId;
  const serviceVariationVersion = req.body.serviceVariationVersion;
  const startingAt = req.body.startingAt;
  const teamMemberId = req.body.teamMemberId;

  const givenName = req.body.givenName;
  const familyName = req.body.familyName;
  const emailAddress = req.body.emailAddress;
  const phoneNumber = req.body.phoneNumber;

  try {
    const response = await bookingsApi.createBooking({
      idempotencyKey: uuidv4(),
      booking: {
        startingAt,
        locationId: process.env[`SQUARE_LOCATION_ID`].toLowerCase(),
        customerId: getCustomerID(givenName, familyName, emailAddress, phoneNumber),
        appointmentSegments: [
          {
            durationMinutes,
            serviceVariationId,
            teamMemberId,
            serviceVariationVersion,
          }
        ]
      }
    });

  } catch (error) {
    console.log(error);
    next(error);
  }
});

/**
 * Return the id of a customer that matches the firstName, lastName, email, and phoneNumber
 * If such customer doesn't exist, create a new customer.
 * 
 * @param {*} givenName
 * @param {*} familyName 
 * @param {*} emailAddress 
 * @param {*} phoneNumber 
 */
async function getCustomerID(givenName, familyName, emailAddress, phoneNumber) {
  const { result: { customers } } = await customersApi.searchCustomers({
    query: {
      filter: {
        emailAddress: {
          exact: emailAddress,
        },
        phoneNumber: {
          exact: "+" + phoneNumber,
        }
      }
    }
  });


  if (customers && customers.length > 0) {
    const matchingCustomers = customers.filter(customer =>
      customer.givenName === givenName &&
      customer.familyName === familyName &&
      customer.phoneNumber === phoneNumber &&
      customer.emailAddress === emailAddress
    );

    // If a matching customer is found, return the first matching customer
    if (matchingCustomers.length > 0) {
      return matchingCustomers[0].id;
    }
  }

  // If no matching customer is found, create a new customer and return its ID
  const { result: { customer } } = await customersApi.createCustomer({
    idempotencyKey: uuidv4(),
    givenName,
    familyName,
    emailAddress,
    phoneNumber,
  });
  return customer.id;
}


module.exports = router;
