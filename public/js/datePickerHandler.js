/**
 * DatePickerHandler handles the action when a new date on the
 * calendar is selected - we show the available times for that date
 */
class DatePickerHandler {
  /**
   * Constructor for DatePickerHandler
   * @param {Object} availabilityMap
   * @param {String} serviceId
   * @param {String} serviceVersion
   * @param {String} bookingId - booking id if rescheduling an existing booking. Else undefined
   */
  constructor(availabilityMap, serviceId, serviceVersion, bookingId) {
    this.availabilityMap = availabilityMap;
    this.serviceId = serviceId;
    this.serviceVersion = serviceVersion;
    this.bookingId = bookingId;
    // show the available times for today's date
    const now = new Date();
    this.selectNewDate(new Date(now.getTime() - (now.getTimezoneOffset() * 60000)).toISOString().split("T")[0]);
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
      form.action = this.bookingId ? `/booking/${this.bookingId}/reschedule?startAt=${availability.date}` : `/contact?serviceId=${this.serviceId}&version=${this.serviceVersion}&staff=${availability.teamMemberId}&startAt=${availability.date}`;
      form.method = this.bookingId ? "post" : "get";
      const timeItem = document.createElement("button");
      timeItem.innerHTML = availability.time;
      timeItem.href = `/contact?serviceId=${this.serviceId}&version=${this.serviceVersion}&staff=${availability.teamMemberId}&startAt=${availability.date}`;
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
    // let date be selectable if there's availabilities for the date or
    // if the date is today
    return [ this.availabilityMap[formattedDate] || formattedDate === today ];
  }
}
