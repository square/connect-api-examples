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
