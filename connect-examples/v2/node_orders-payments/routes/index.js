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
const { randomBytes } = require("crypto");
const {
  catalogApi,
  locationApi,
  orderApi,
} = require("../util/square-connect-client");

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
  const opt = {
    types: "ITEM,IMAGE", // To retrieve TAX or CATEGORY objects add them to types
  };

  try {
    // Retrieves locations in order to display the store name
    const {
      locations
    } = await locationApi.listLocations();
    // Get CatalogItem and CatalogImage object
    const catalogList = await catalogApi.listCatalog(opt);
    // Renders index view, with catalog and location information
    res.render("index", {
      items: new CatalogList(catalogList).items,
      location_info: new LocationInfo(locations[0]), // take the first location for the sake of simplicity.
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
 *  item_var_id: Id of the CatalogItem which will be purchased
 *  item_quantity: Quantility of the item
 *  location_id: The Id of the location
 */
router.post("/create-order", async (req, res, next) => {
  const {
    item_var_id,
    item_id,
    item_quantity,
    location_id
  } = req.body;
  try {
    const orderRequestBody = {
      idempotency_key: randomBytes(45).toString("hex"), // Unique identifier for request
      order: {
        line_items: [{
          quantity: item_quantity,
          catalog_object_id: item_var_id // Id for CatalogItem object
        }]
      }
    };
    // Apply the taxes that's related to this catalog item.
    // Order API doesn't calculate the tax automatically even if you have apply the tax to the catalog item
    // You must add the tax yourself when create order.
    const catalogItem = await catalogApi.retrieveCatalogObject(item_id);
    if (!!catalogItem.object.item_data.tax_ids && catalogItem.object.item_data.tax_ids.length > 0) {
      orderRequestBody.order.taxes = [];
      for (let i = 0; i < catalogItem.object.item_data.tax_ids.length; i++) {
        orderRequestBody.order.taxes.push({
          catalog_object_id: catalogItem.object.item_data.tax_ids[i],
          scope: "ORDER",
        });
      }
    }
    const {
      order
    } = await orderApi.createOrder(location_id, orderRequestBody);
    res.redirect(`/checkout/choose-delivery-pickup?order_id=${order.id}&location_id=${location_id}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
