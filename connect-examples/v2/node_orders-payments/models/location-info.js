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
 * Location information for the page
 *
 * Description:
 *  This class abstracts away a location object returned by ListLocations api. The number of locations
 *  is limited to one for the sake of simplicity.
 *
 *  Learn more about Location objects and the ListLocation api here:
 *  https://developer.squareup.com/reference/square/locations-api/list-locations
 */

class LocationInfo {
  constructor(locationObj) {
    this.locationObj = locationObj;
  }

  // Returns the id of the location
  get id() {
    return this.locationObj.id;
  }

  // Returns the store name stored in the locationObj
  get storeName() {
    return this.locationObj.businessName || this.locationObj.name;
  }

  // Returns Address Line 1
  get addressLine() {
    const { address } = this.locationObj;
    return address ? address.addressLine1 : "";
  }

  // Returns formatted city, state, and zip code as one string
  get cityStateZip() {
    if (this.locationObj.address) {
      const {
        locality,
        administrativeDistrictLevel1,
        postalCode,
      } = this.locationObj.address;
      return `${locality}, ${administrativeDistrictLevel1}, ${postalCode}`;
    } else {
      return "";
    }
  }
}

module.exports = LocationInfo;
