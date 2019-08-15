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
const OrderInfo = require("./order-info");
const PaymentInfo = require("./payment-info");
const LocationInfo = require("./location-info");

/**
 * Description:
 *  An instance of this ConfirmationPageData provides the information necessary to render
 *  views/process-payment.pug
 *
 * Parameters:
 *  catalogObj:  CatalogItem Object with related objects in this case CatalogImage
 *  locationObj: First location in returned from ListLocations api
 *  payment:     Payment object returned from CreatePayment api
 *  order:       Order object returned from CreateOrder api
 */
class ConfirmationPageData{
  constructor(catalogObj, locationObj, payment, order){
    // Get image object
    const imageObject = catalogObj.related_objects.find(cat_obj => cat_obj.type === "IMAGE");
    this.item = new CatalogItemVariation(catalogObj.object, imageObject);
    // Gets the payment, order, and location objects
    this.payment = new PaymentInfo(payment);
    this.order = new OrderInfo(order);
    this.location = new LocationInfo(locationObj);
  }
}

module.exports = ConfirmationPageData;
