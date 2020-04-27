/*
Copyright 2019 Square Inc.

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

/**
 * Class DeliveryPickUpTimes
 *
 * Description:
 *  The constructor creates a javascript Date object saving the time when the constrictor is run, as this.now.
 *  Then, four other javascript Date objects are created based off of the time and Date of this.now.
 *
 * Methods:
 *  GETTER options:
 *    Returns array of objects, where each object has information for a pickup or delivery time. The three pieces of information are
 *    "value" an ISO string later used to create a Square order, "date" a long form of the pickup or delivery time
 *    and lastly "time" a short 12 hour format for the pickup or delivery time.
 */

class DeliveryPickUpTimes {
  constructor() {
    this.dateFormat = {
      weekday: "long",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "2-digit",
    };
    this.timeFormat = {
      hour: "numeric",
      minute: "2-digit",
    };
    this.now = new Date(); // The Date object that all other date objects are created from.
    this.dates = [];

    // Creates date object for our four time options
    for (let i = 1; i < 5; i++) {
      const hourShift = 1 + Math.floor(i / 2);
      const minutes = i % 2 === 0 ? 0 : 30;
      this.dates.push(
        new Date(
          this.now.getFullYear(),
          this.now.getMonth(),
          this.now.getDate(),
          this.now.getHours() + hourShift,
          minutes
        )
      );
    }
  }

  // Returns objects with formatted times and an ISOString which the Orders API consumes
  get options() {
    return this.dates.map((date) => {
      return {
        value: date.toISOString(),
        date: date.toLocaleDateString("en-US", this.dateFormat),
        time: date.toLocaleTimeString("en-US", this.timeFormat),
      };
    });
  }
}

module.exports = DeliveryPickUpTimes;
