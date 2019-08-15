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
 *  https://developer.squareup.com/docs/api/connect/v2#endpoint-locations-listlocations
 */

class LocationInfo{
  constructor(locationObj){
    this.locationObj = locationObj;
  }

  get id(){
    return this.locationObj.id;
  }

  get nameAddress(){
    const { name } = this.locationObj;
    return `${name} ${this.addressLine}`;
  }

  // Returns the store name stored in the locationObj
  get storeName(){
    return this.locationObj.name;
  }

  // Returns Address Line 1
  get addressLine() {
    const { address } = this.locationObj;
    return address ? address.address_line_1:"";
  }

  // Returns formatted city, state, and zip code as one string
  get cityStateZip(){
    if (this.locationObj.address){
      const { locality, administrative_district_level_1, postal_code } = this.locationObj.address;
      const capitalCaseLocality = locality.charAt(0).toUpperCase() + locality.slice(1).toLowerCase();
      return `${capitalCaseLocality}, ${administrative_district_level_1}, ${postal_code}`;
    } else {
      return "";
    }
  }
}

module.exports = LocationInfo;
