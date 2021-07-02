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
const { bookingsApi, customersApi, catalogApi } = require("../util/square-client");
const { v4: uuidv4 } = require("uuid");

/**
 * POST /booking/create
 * 
 * Create a new booking, booking details and customer information is submitted
 * by form data. Create a new customer if necessary, otherwise use an existing
 * customer that matches the `firstName`, `lastName`, `emailAddress`, and `phoneNumber`
 * to create the booking.
 * 
 * accepted query params are:
 * `serviceId` - the ID of the service
 * `staffId` - the ID of the staff
 * `startAt` - starting time of the booking
 * `serviceVariationVersion` - the version of the service initially selected. If the version mistaches the
 * current version of the service, fail with an error message.
 */
router.post("/create", async (req, res, next) => {
  const serviceId = req.query.serviceId;
  const serviceVariationVersion = req.query.version;
  const staffId = req.query.staffId;
  const startAt = req.query.startAt;

  const customerNote = req.body.customerNote;
  const emailAddress = req.body.emailAddress;
  const familyName = req.body.familyName;
  const givenName = req.body.givenName;
  const phoneNumber = req.body.phoneNumber;


  try {
    // Retrieve catalog object by the variation ID
    const { result: { object: catalogItemVariation } } = await catalogApi.retrieveCatalogObject(serviceId);
    const durationMinutes = convertMsToMins(catalogItemVariation.itemVariationData.serviceDuration);

    // Create booking
    const { result: { booking } } = await bookingsApi.createBooking({  
      booking: {
        appointmentSegments: [
          {
            durationMinutes,
            serviceVariationId: serviceId,
            serviceVariationVersion,
            teamMemberId: staffId,
          }
        ],
        customerId: await getCustomerID(givenName, familyName, emailAddress, phoneNumber),
        customerNote,
        locationId: process.env["SQUARE_LOCATION_ID"].toLowerCase(),
        startAt,
      },
      idempotencyKey: uuidv4(),
    });

    //TODO: redirect to confirmation page
    res.json({ booking: booking.id });

  } catch (error) {
    const timeNotAvailable = error.errors.find(e => e.detail.match(/That time slot is no longer available/));
    if (timeNotAvailable) {
      //TODO: redirect with some error message
      return res.redirect("/");
    }

    console.error(error);
    next(error);
  }
});

/**
 * POST /booking/:bookingId/reschedule
 * 
 * Update an existing booking, you may update the starting date
 */
router.post("/:bookingId/reschedule", async (req, res, next) => {
  const bookingId = req.params.bookingId;
  const startAt = req.query.startAt;

  try {
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);
    const updateBooking = {
      startAt,
      version: booking.version,
    };

    const { result: { booking: newBooking } } = await bookingsApi.updateBooking(bookingId, { booking: updateBooking });

    //TODO: redirect to confirmation page
    res.json({ booking: newBooking.id });

  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * POST /booking/:bookingId/delete
 * 
 * delete a booking by booking ID
 */
router.post("/:bookingId/delete", async (req, res, next) => {
  const bookingId = req.params.bookingId;

  try {
    const { result: { booking } } = await bookingsApi.retrieveBooking(bookingId);
    
    await bookingsApi.cancelBooking(bookingId, { bookingVersion: booking.version });
    
    //TODO: redirect to confirmation page or home page
    res.sendStatus(200);

  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * Convert a duration in milliseconds to minutes
 * 
 * @param {*} duration - duration in milliseconds
 * @returns 
 */
function convertMsToMins(duration) {
  return Math.round(Number(duration) / 1000 / 60);
}  

/**
 * Return the id of a customer that matches the firstName, lastName, email, 
 * and phoneNumber. If such customer doesn't exist, create a new customer.
 * 
 * @param {string} givenName
 * @param {string} familyName 
 * @param {string} emailAddress 
 * @param {string} phoneNumber 
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
      customer.familyName === familyName
    );

    // If a matching customer is found, return the first matching customer
    if (matchingCustomers.length > 0) {
      return matchingCustomers[0].id;
    }
  }

  // If no matching customer is found, create a new customer and return its ID
  const { result: { customer } } = await customersApi.createCustomer({
    emailAddress,
    familyName,
    givenName,
    idempotencyKey: uuidv4(),
    phoneNumber,
  });

  return customer.id;
}


module.exports = router;
