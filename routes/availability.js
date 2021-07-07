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

// we will only search availability for the next 30 days
// searchAvailability has a max query range of 32 days
const QUERY_RANGE_PERIOD_DAYS = 30;

// only show booking availability 4 hours from current time
const MIN_BOOKING_START_TIME_HOURS = 4;

// the path param for staffId when user is searching for all staff member availability
const ANY_STAFF_PARAMS = "anyStaffMember";

/**
 * Generate start date for search
 * @returns date
 */
function getStartAtDate() {
  const date = new Date();
  // only allow booking start time 4 hours from now
  date.setHours(date.getHours() + MIN_BOOKING_START_TIME_HOURS);
  return date;
}

/**
 * Generate end date for search
 * @param startDate {Date}
 * @returns date
 */
function getEndAtDate(startDate) {
  const endDate = new Date(startDate);
  // only allow booking end time 30 days from start
  endDate.setDate(endDate.getDate() + QUERY_RANGE_PERIOD_DAYS);
  return endDate;
}

/**
 * Reformat time to 12 hour am/pm format
 * @param {Date} date
 * @return {String} time in 12 hour format with am/pm
 */
function formatToAmPm(date) {
  let hours = date.getHours();
  let minutes = date.getMinutes();
  hours = hours % 12 ? hours % 12 : 12;
  minutes = minutes < 10 ? "0" + minutes : minutes;
  return `${hours}:${minutes} ${date.getHours() >= 12 ? "pm" : "am"}`;
}

/**
 * Return a mapping of the availabilities array
 * to enable easy lookup of available times for a date on the client side
 * @param {Availability[]} availabilities
 * @param {String} teamMemberId
 * @return {Object<String, Object[]>} map where keys are the dates and values are
 * objects that contain the time, the team member id available and
 * the date in RFC 3339 format
 * All dates & times returned are converted to local time zone
 *
 * Ex. If availabilities is:
 * [
    {
      startAt: "2021-11-26T13:00:00Z",
      locationId: "location-id",
      appointmentSegments: [
        {
          durationMinutes: 30,
          teamMemberId: "team-id-1",
          serviceVariationId: "service-id",
          serviceVariationVersion: 1234
        }
      ]
    },
    {
      startAt: "2021-11-26T21:00:00Z",
      locationId: "location-id",
      appointmentSegments: [
        {
          durationMinutes: 30,
          teamMemberId: "team-id-2",
          serviceVariationId: "service-id",
          serviceVariationVersion: 1234
        }
      ]
    },
    {
      startAt "2021-11-30T12:30:00Z",
      locationId: "location-id",
      appointmentSegments: [
        {
          durationMinutes: 30,
          teamMemberId: "team-id-2",
          serviceVariationId: "service-id",
          serviceVariationVersion: 1234
        }
      ]
    }
  ]
  Then the object returned is (if local timezone is UTC-7 hours):
  {
    "2021-11-26": [
      {
        date: "2021-11-26T13:00:00Z",
        teamMemberId: "team-id-1",
        time: "6:00 am"
      },
      {
        date: "2021-11-26T21:00:00Z",
        teamMemberId: "team-id-2",
        time: "2:00 pm"
      }
    ],
    "2021-11-30": [
      {
        date: "2021-11-30T12:30:00Z",
        teamMemberId: "team-id-2",
        time: "5:30 am"
      }
    ]
  }
 */
function createDateAvailableTimesMap(availabilities) {
  const dateAvailableTimesMap = {};
  availabilities.forEach((availability) => {
    // get date
    const startAtDate = new Date(availability.startAt);
    const localStartDate = new Date(startAtDate.getTime() - startAtDate.getTimezoneOffset()*60*1000);
    const startDate = localStartDate.toISOString().split("T")[0];
    const availableTimes = dateAvailableTimesMap[startDate] || [];
    // add the available times as a value to the date
    availableTimes.push({
      date: availability.startAt, // keep date in same RFC 3339 format so it can be used in createBooking
      teamMemberId: availability.appointmentSegments[0].teamMemberId,
      time: formatToAmPm(startAtDate)
    });
    dateAvailableTimesMap[startDate] = availableTimes;
  });
  return dateAvailableTimesMap;
}

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
  const startAt = getStartAtDate();
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
          endAt: getEndAtDate(startAt).toISOString(),
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
      const availabilitiesResult = await bookingsApi.searchAvailability(searchRequest);
      availabilities = availabilitiesResult.result.availabilities;
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
    // create object where keys are the date in yyyy-mm-dd format and values contain the available times & team member for that date
    const availabilityMap = createDateAvailableTimesMap(availabilities);
    // send the serviceId, serviceVersion & staffId since it's needed to book an appointment in the next step
    res.render("pages/availability", { availabilityMap, serviceId, serviceVersion, staffId, ...additionalInfo });
  } catch (error) {
    console.error(error);
    next(error);
  }
});


module.exports = router;
