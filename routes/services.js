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
require("dotenv").config();

const locationId = process.env["SQUARE_LOCATION_ID"];

const {
  bookingsApi,
  catalogApi,
  teamApi
} = require("../util/square-client");

router.get("/", async (req, res, next) => {
  const { result: { items } } = await catalogApi.searchCatalogItems({
    enabledLocationIds: [locationId],
    productTypes: ["APPOINTMENTS_SERVICE"]
  });

  res.render("pages/landing", { items });
});


/**
 * two options to do this:
 * 1. Retrieve the service given by the ID. Grab the team members for this service.
 * List all bookings profiles for the current location that are bookable
 * for each team member of the service, if bookable, add to list to send back.
 *
 * 2. Retrieve the service given by the ID. Grab the team members for this service.
 * For each team member, make an API call to retrieve booking profile and check if bookable.
 * if so, add to list. (could be slow if many team members)
 *
 * sending back: list of booking profiles (which will contain name and ID).
 * Limitation: no way to provide description or gender, cant support pictures or background info).
 */
router.get("/:id/staff", async (req, res, next) => {
  const serviceId = req.params.id;

  // Send request to get the service associated with the given catalog object ID.
  const retrieveServicePromise = catalogApi.retrieveCatalogObject(serviceId);

  // Send request to list staff booking profiles for the current location.
  const listBookingProfilesPromise = bookingsApi.listTeamMemberBookingProfiles(true, undefined, undefined, locationId);

  const responses = await Promise.all([retrieveServicePromise, listBookingProfilesPromise]);

  const serviceTeamMembers = responses[0].result.object.itemData.variations[0].itemVariationData.teamMemberIds;
  const bookableTeamMemberProfiles = responses[1].result.teamMemberBookingProfiles;
  console.log(serviceTeamMembers);
  console.log(bookableTeamMemberProfiles);

  // We want to filter bookableTeamMemberProfiles by checking that the teamMemberId associated with the profile is in our serviceTeamMembers.
  const staff = bookableTeamMemberProfiles.filter(profile => serviceTeamMembers.includes(profile.teamMemberId));

  console.log(staff);
  // res.render("pages/booking", { staff });
});

module.exports = router;
