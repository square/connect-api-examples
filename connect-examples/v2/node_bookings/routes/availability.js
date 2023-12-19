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

const dateHelpers = require("../util/date-helpers");
const express = require("express");
const router = express.Router();
require("dotenv").config();

const locationId = process.env["SQ_LOCATION_ID"];

const {
  bookingsApi,
  catalogApi,
  teamApi,
} = require("../util/square-client");

// the path param for staffId when user is searching for all staff member availability
const ANY_STAFF_PARAMS = "anyStaffMember";

/**
 * Retrieve all the staff that can perform a specific service variation.
 * 1. Get the service using catalog API.
 * 2. Get the booking profiles for all staff members in the current location (that are bookable).
 * 3. Get all active team members for the location.
 * 4. Cross reference 1, 2, and 3 so we can find all available staff members for the service.
 * @param {String} serviceId
 * @return {[CatalogItem, String[]]} array where first item is the service item and
 * second item is the array of all the team member ids that can be booked for the service
 */
async function searchActiveTeamMembers(serviceId) {
  // Send request to get the service associated with the given item variation ID.
  const retrieveServicePromise = catalogApi.retrieveCatalogObject(serviceId, true);

  // Send request to list staff booking profiles for the current location.
  const listBookingProfilesPromise = bookingsApi.listTeamMemberBookingProfiles(true, undefined, undefined, locationId);

  // Send request to list all active team members for this merchant at this location.
  const listActiveTeamMembersPromise = teamApi.searchTeamMembers({
    query: {
      filter: {
        locationIds: [ locationId ],
        status: "ACTIVE"
      }
    }
  });

  const [ { result: services }, { result: { teamMemberBookingProfiles } }, { result: { teamMembers } } ] =
  await Promise.all([ retrieveServicePromise, listBookingProfilesPromise, listActiveTeamMembersPromise ]);
  // We want to filter teamMemberBookingProfiles by checking that the teamMemberId associated with the profile is in our serviceTeamMembers.
  // We also want to verify that each team member is ACTIVE.
  const serviceVariation = services.object;

  const serviceTeamMembers = serviceVariation.itemVariationData.teamMemberIds || [];
  const activeTeamMembers = teamMembers.map(teamMember => teamMember.id);

  const bookableStaff = teamMemberBookingProfiles
    .filter(profile => serviceTeamMembers.includes(profile.teamMemberId) && activeTeamMembers.includes(profile.teamMemberId));
  return [ services, bookableStaff.map(staff => staff.teamMemberId) ];
}

/**
 * GET /availability/:staffId/:serviceId?version
 *
 * This endpoint is in charge of retrieving the availability for the service + team member
 * If the team member is set as anyStaffMember then we retrieve the availability for all team members
 */
router.get("/:staffId/:serviceId", async (req, res, next) => {
  const serviceId = req.params.serviceId;
  const serviceVersion = req.query.version;
  const staffId = req.params.staffId;
  const startAt = dateHelpers.getStartAtDate();
  const searchRequest = {
    query: {
      filter: {
        locationId,
        segmentFilters: [
          {
            serviceVariationId: serviceId,
          },
        ],
        startAtRange: {
          endAt: dateHelpers.getEndAtDate(startAt).toISOString(),
          startAt: startAt.toISOString(),
        },
      }
    }
  };
  try {
    // get service item - needed to display service details in left pane
    const retrieveServicePromise = catalogApi.retrieveCatalogObject(serviceId, true);
    let availabilities;
    // additional data to send to template
    let additionalInfo;
    // search availability for the specific staff member if staff id is passed as a param
    if (staffId === ANY_STAFF_PARAMS) {
      const [ services, teamMembers ] = await searchActiveTeamMembers(serviceId);
      searchRequest.query.filter.segmentFilters[0].teamMemberIdFilter = {
        any: teamMembers,
      };
      // get availability
      const { result } = await bookingsApi.searchAvailability(searchRequest);
      availabilities = result.availabilities;
      additionalInfo = {
        serviceItem: services.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0],
        serviceVariation: services.object
      };
    } else {
      searchRequest.query.filter.segmentFilters[0].teamMemberIdFilter = {
        any: [
          staffId
        ],
      };
      // get availability
      const availabilityPromise = bookingsApi.searchAvailability(searchRequest);
      // get team member booking profile - needed to display team member details in left pane
      const bookingProfilePromise = bookingsApi.retrieveTeamMemberBookingProfile(staffId);
      const [ { result }, { result: services }, { result: { teamMemberBookingProfile } } ] = await Promise.all([ availabilityPromise, retrieveServicePromise, bookingProfilePromise ]);
      availabilities = result.availabilities;
      additionalInfo = {
        bookingProfile: teamMemberBookingProfile,
        serviceItem: services.relatedObjects.filter(relatedObject => relatedObject.type === "ITEM")[0],
        serviceVariation: services.object
      };
    }
    // send the serviceId & serviceVersion since it's needed to book an appointment in the next step
    res.render("pages/availability", { availabilities, serviceId, serviceVersion, ...additionalInfo });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

module.exports = router;
