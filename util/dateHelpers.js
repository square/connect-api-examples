/** Module for creating start & end dates for search availability */
module.exports = {
  // we will only search availability for the next 30 days
  // searchAvailability has a max query range of 32 days
  QUERY_RANGE_PERIOD_DAYS: 30,

  // only show booking availability 4 hours from current time
  /* eslint-disable sort-keys */
  MIN_BOOKING_START_TIME_HOURS: 4,

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
