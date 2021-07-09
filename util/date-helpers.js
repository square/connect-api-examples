

/** Module for datetime helper functions */
module.exports = {
  // we will only search availability for the next 30 days
  // searchAvailability has a max query range of 32 days
  QUERY_RANGE_PERIOD_DAYS: 30,

  // only show booking availability 4 hours from current time
  /* eslint-disable sort-keys */
  MIN_BOOKING_START_TIME_HOURS: 4,

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
  createDateAvailableTimesMap: function (availabilities) {
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
        time: this.formatToAmPm(startAtDate)
      });
      dateAvailableTimesMap[startDate] = availableTimes;
    });
    return dateAvailableTimesMap;
  },

  /**
   * Reformat time to 12 hour am/pm format
   * @param {Date} date
   * @return {String} time in 12 hour format with am/pm
   */
  formatToAmPm: function (date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    hours = hours % 12 ? hours % 12 : 12;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    return `${hours}:${minutes} ${date.getHours() >= 12 ? "pm" : "am"}`;
  },

  /**
   * Generate end date for search
   * @param startDate {Date}
   * @returns date
   */
  getEndAtDate: function (startDate) {
    const endDate = new Date(startDate);
    // only allow booking end time 30 days from start
    endDate.setDate(endDate.getDate() + this.QUERY_RANGE_PERIOD_DAYS);
    return endDate;
  },

  /**
   * Generate start date for search
   * @returns date
   */
  getStartAtDate: function () {
    const date = new Date();
    // only allow booking start time 4 hours from now
    date.setHours(date.getHours() + this.MIN_BOOKING_START_TIME_HOURS);
    return date;
  },
};
