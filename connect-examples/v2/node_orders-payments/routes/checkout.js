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
const router = express.Router();

const { config, catalogInstance, locationInstance } = require("../util/square-connect-client");
const CheckoutPageData = require("../models/checkout-page-data");

/**
 * Matches: POST /process-payments/
 *
 * Description:
 *  Recieves post request with a CatalogItem id. Then, retrieves location
 *  information along with the CatalogItem using retrieveCatalogObject.
 *
 *  You learn more about the RetrieveCatalogObject endpoint here:
 *  https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-retrievecatalogobject
 *
 *  NOTE: The RetrieveCatalogObject api always returns related objects, while the SDK
 *  needs "includeRelatedObjects" to be set tot true.
 *
 * Request Body:
 *  object_id: Id of the CatalogItem which will be purchased
 */
router.post("/", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const { object_id } = req.body;
  try {
    // Retrieves array of Location objects
    const { locations } = await locationInstance.listLocations();
    // Retrieves Catalog Object with related Objects like CatalogImage objects
    const catalogList = await catalogInstance.retrieveCatalogObject(object_id, { includeRelatedObjects: true });
    const viewData  = new CheckoutPageData(catalogList, locations[0]); // One location for the sake of simplicity.
    viewData.squareApplicationId = config.squareApplicationId;
    // Renders template with data from retrieveCatalogObject
    res.render("checkout", viewData);
  }
  catch (error){
    next(error);
  }
});

module.exports = router;
