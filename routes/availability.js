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
  bookingsApi
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
  availabilities.map((availability) => {
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
 * GET /availability
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
  // search availability for the specific staff member if staff id is passed as a param
  if (staffId !== ANY_STAFF_PARAMS) {
    searchRequest.query.filter.segmentFilters[0].teamMemberIdFilter = {
      any: [
        staffId
      ],
    };
  }
  try {
    const { result: { availabilities } } = await bookingsApi.searchAvailability(searchRequest);
    // create object where keys are the date in yyyy-mm-dd format and values contain the available times & team member for that date
    const availabilityMap = createDateAvailableTimesMap(availabilities);
    // send the serviceId, serviceVersion & staffId since it's needed to book an appointment in the next step
    res.render("pages/availability", { availabilityMap, serviceId, serviceVersion, staffId });
  } catch (error) {
    console.error(error);
    next(error);
  }
});


module.exports = router;
