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

const express = require("express");
const crypto = require("crypto");
const {
  catalogApi,
  locationsApi,
  ordersApi,
} = require("../util/square-client");

const router = express.Router();
const CatalogList = require("../models/catalog-list");
const LocationInfo = require("../models/location-info");

/**
 * Matches: /checkout or /order-status, respectively.
 *
 * Description:
 *  If the rquest url matches one of the router.use calls, then the routes used are in the
 *  required file.
 */
router.use("/checkout", require("./checkout"));
router.use("/order-confirmation", require("./order-confirmation"));

/**
 * Matches: GET /
 *
 * Description:
 *  Retrieves list of locations and CatalogItems, then renders views/index.pug template.
 *  Note: In this example we only use the first location in the list.
 */
router.get("/", async (req, res, next) => {
  // Set to retrieve ITEM and IMAGE CatalogObjects
  const types = "ITEM,IMAGE"; // To retrieve TAX or CATEGORY objects add them to types
  try {
    // Retrieves locations in order to display the store name
    const { result: { locations } } = await locationsApi.listLocations();
    // Get CatalogItem and CatalogImage object
    const { result: { objects } } = await catalogApi.listCatalog(undefined, types);

    // Renders index view, with catalog and location information
    res.render("index", {
      items: new CatalogList(objects).items,
      locationInfo: new LocationInfo(locations[0]), // take the first location for the sake of simplicity.
    });
  } catch (error) {
    next(error);
  }
});

/**
 * Matches: POST /create-order
 *
 * Description:
 *  Creates an order for a single item variation, this method kicks off the checkout workflow.
 *  Learn more about Orders here: https://developer.squareup.com/docs/orders-api/what-it-does
 *
 *  This method currently only takes one item to checkout, you can potentially pass multiple
 *  items to create an order and then proceed with the checkout process.
 *
 * Request Body:
 *  itemVarId: Id of the CatalogItem which will be purchased
 *  itemQuantity: Quantility of the item
 *  locationId: The Id of the location
 */
router.post("/create-order", async (req, res, next) => {
  const {
    itemVarId,
    itemId,
    itemQuantity,
    locationId
  } = req.body;
  try {
    const orderRequestBody = {
      idempotencyKey: crypto.randomUUID(), // Unique identifier for request
      order: {
        locationId,
        lineItems: [{
          quantity: itemQuantity,
          catalogObjectId: itemVarId // Id for CatalogItem object
        }]
      }
    };
    // Apply the taxes that's related to this catalog item.
    // Order API doesn't calculate the tax automatically even if you have apply the tax to the catalog item
    // You must add the tax yourself when create order.
    const { result: { object } } = await catalogApi.retrieveCatalogObject(itemId);
    if (!!object.itemData.taxIds && object.itemData.taxIds.length > 0) {
      orderRequestBody.order.taxes = [];
      for (let i = 0; i < object.itemData.taxIds.length; i++) {
        orderRequestBody.order.taxes.push({
          catalogObjectId: object.itemData.taxIds[i],
          scope: "ORDER",
        });
      }
    }
    const { result: { order } } = await ordersApi.createOrder(orderRequestBody);
    res.redirect(`/checkout/choose-delivery-pickup?orderId=${order.id}&locationId=${locationId}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
