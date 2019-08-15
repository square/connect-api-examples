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
const { catalogInstance, locationInstance } = require("../util/square-connect-client");
const IndexPageData = require("../models/index-page-data");

/**
 * Matches: /checkout or /process-payment, respectively.
 *
 * Description:
 *  If the rquest url matches one of the router.use calls, then the routes used are in the
 *  required file.
 */
router.use("/checkout", require("./checkout"));
router.use("/process-payment", require("./process-payment"));

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
    types: "ITEM,IMAGE" // To retrieve TAX or CATEGORY objects add them to types
  };

  try {
    // Retrieves locations and in order to display the store name
    const { locations } = await locationInstance.listLocations();
    // Get CatalogItem and CatalogImage object
    const catalogList = await catalogInstance.listCatalog(opt);
    // Organizes Catalog List into class IndexPageData
    const viewData = new IndexPageData(catalogList, locations[0]); // One location for the sake of simplicity.
    // Renders index view, with catalog information
    res.render("index", viewData);
  } catch (error){
    next(error);
  }
});

module.exports = router;
