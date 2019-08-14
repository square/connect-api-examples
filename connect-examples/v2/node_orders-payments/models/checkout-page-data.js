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

const CatalogItemVariation = require("./catalog-item-variation");
const LocationInfo = require("./location-info");
const PickUpTimes = require("./pickup-times");

/**
 * Description:
 *  An instance of this CheckoutPageData provides the information necessary to render
 *  views/checkout.pug
 *
 * Parameters:
 *  catalogObj:  CatalogItem Object with related objects in this case CatalogImage
 *  locationObj: First location in returned from ListLocations api
 */
class CheckoutPageData{
  constructor(catalogObj, locationObj){
    // Get pricing information from item_data
    const imageObject = catalogObj.related_objects && catalogObj.related_objects.find(cat_obj => cat_obj.type === "IMAGE");
    this.item = new CatalogItemVariation(catalogObj.object, imageObject);
    this.pickUpTimes = new PickUpTimes();
    this.location = new LocationInfo(locationObj);
  }
  set squareApplicationId(id){
    this.applicationId = id;
  }
}

module.exports = CheckoutPageData;
