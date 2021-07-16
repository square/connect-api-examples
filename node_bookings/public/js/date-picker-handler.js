/**
 * DatePickerHandler handles the action when a new date on the
 * calendar is selected - we show the available times for that date
 */
class DatePickerHandler {
  /**
   * Constructor for DatePickerHandler
   * @param {Availability[]} availabilities
   * @param {String} serviceId
   * @param {String} serviceVersion
   * @param {String} bookingId - booking id if rescheduling an existing booking. Else undefined
   * @param {String} businessTimeZone - the business IANA time zone
   */
  constructor(availabilities, serviceId, serviceVersion, bookingId, businessTimeZone) {
    this.availabilityMap = this.createDateAvailableTimesMap(availabilities, businessTimeZone);
    this.serviceId = serviceId;
    this.serviceVersion = serviceVersion;
    this.bookingId = bookingId;
    // show the available times for today's date
    const now = new Date();
    this.selectNewDate(new Date(now.getTime() - (now.getTimezoneOffset() * 60000)).toISOString().split("T")[0]);
  }

  /**
   * Return a mapping of the availabilities array
   * to enable easy lookup of available times for a date on the client side
   * @param {Availability[]} availabilities
   * @param {String} businessTimeZone IANA timezone of the business
   * @return {Object<String, Object[]>} map where keys are the dates and values are
   * objects that contain the time, the team member id available and
   * the date in RFC 3339 format
   * All dates & times returned are converted to the time zone passed in
   * (business time zone)
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
  createDateAvailableTimesMap(availabilities, businessTimeZone) {
    const dateAvailableTimesMap = {};
    availabilities.forEach((availability) => {
      // get start date
      const startAtDate = new Date(availability.startAt);
      // convert dates to the business time zone
      const businessTime = new Date(startAtDate.toLocaleString("en-US", { timeZone: businessTimeZone }));
      const month = ("0" + (businessTime.getMonth() + 1)).slice(-2);
      const date = ("0" + businessTime.getDate()).slice(-2);
      const startDate = `${businessTime.getFullYear()}-${month}-${date}`;
      const availableTimes = dateAvailableTimesMap[startDate] || [];
      // add the available times as a value to the date
      availableTimes.push({
        date: availability.startAt, // keep date in same RFC 3339 format so it can be used in createBooking
        teamMemberId: availability.appointmentSegments[0].teamMemberId,
        time: this.formatToAmPm(businessTime)
      });
      dateAvailableTimesMap[startDate] = availableTimes;
    });
    return dateAvailableTimesMap;
  }

  /**
   * Reformat time to 12 hour am/pm format
   * @param {Date} date in business's time zone
   * @return {String} time in 12 hour format with am/pm
   */
  formatToAmPm(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    hours = hours % 12 ? hours % 12 : 12;
    minutes = minutes < 10 ? "0" + minutes : minutes;
    return `${hours}:${minutes} ${date.getHours() >= 12 ? "pm" : "am"}`;
  }

  /**
   * Handler for when a date is selected on the datepicker widget
   * Show the available times for that date
   * @param {String} date ie. 2021-10-30
   */
  selectNewDate(date) {
    const availableTimesDiv = document.getElementById("available-times");
    // clear available times and reset it to the new available times for the date
    availableTimesDiv.innerHTML = "";
    const availabities = this.availabilityMap[date];
    if (!availabities) { // no available times for the date
      const noTimesAvailable = document.createElement("p");
      noTimesAvailable.className = "no-times-available-msg";
      noTimesAvailable.innerHTML = "There are no times available for this date - please select a new date.";
      availableTimesDiv.appendChild(noTimesAvailable);
      return;
    }
    // for each available time create a new element that directs user to the next step in booking
    // or reschedules the booking if it's an existing booking
    availabities.forEach((availability) => {
      const form = document.createElement("form");
      form.action = this.bookingId ? `/booking/${this.bookingId}/reschedule?startAt=${availability.date}` : "/contact";
      form.method = this.bookingId ? "post" : "get";
      // create hidden parameters for GET contact action
      if (form.method === "get") {
        const queryParams = {
          serviceId: this.serviceId,
          staff: availability.teamMemberId,
          startAt: availability.date,
          version: this.serviceVersion,
        };
        Object.keys(queryParams).forEach(queryParam => {
          const input = document.createElement("input");
          input.type = "hidden";
          input.name = queryParam;
          input.value = queryParams[queryParam];
          form.appendChild(input);
        });
      }
      const timeItem = document.createElement("button");
      timeItem.innerHTML = availability.time;
      timeItem.className = "available-time";
      timeItem.type = "submit";
      form.appendChild(timeItem);
      availableTimesDiv.appendChild(form);
    });
  }

  /**
   * Format date to yyyy-mm-dd format
   * @param {String} date
   * @returns {String}
   */
  formatDate(date) {
    const d = new Date(date);
    let month = "" + (d.getMonth() + 1);
    let day = "" + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) {
      month = "0" + month;
    }
    if (day.length < 2) {
      day = "0" + day;
    }

    return [ year, month, day ].join("-");
  }

  /**
   * Determines whether a date is selectable or not
   * @param {String} date
   * @returns {Boolean[]} where first item indicates whether the date is selectable
   */
  isSelectable(date) {
    const now = new Date();
    const today = new Date(now.getTime() - (now.getTimezoneOffset() * 60000)).toISOString().split("T")[0];
    const formattedDate = this.formatDate(date);
    // let date be selectable if there's availabilities for the date or if the date is today
    return [ this.availabilityMap[formattedDate] || formattedDate === today ];
  }
}
