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

/* eslint no-console: 0 */

const { Client, Environment, FileWrapper } = require("square");
const crypto = require("crypto");
const sampleData = require("./sample-seed-data.json");
const fs = require("fs");
const readline = require("readline");
require('dotenv').config();

// We don't recommend to run this script in production environment
// Configure OAuth2 access token for authorization: oauth2
const config = {
  environment: Environment.Sandbox,
  accessToken: process.env.SQ_ACCESS_TOKEN
};
// Configure catalog API instance
const { catalogApi, locationsApi } = new Client(config);

/*
 * Given an object with image data and a corresponding catalogObjectId,
 * calls the createCatalogImage API and uploads the image to the corresponding catalogObjectId.
 * For more information on the createCatalogImage API, visit:
 * https://developer.squareup.com/reference/square/catalog-api/create-catalog-image
 * @param Object with Image information
 * @param String catalogObjectId
 */
const addImages = async (image, catalogObjectId, success) => {
  // Create JSON request with required image information requirements.
  const request = {
    idempotencyKey: crypto.randomUUID(),
    objectId: catalogObjectId,
    image: {
      id: image.id,
      type: "IMAGE",
      imageData: {
        caption: image.caption,
      },
    },
  };

  const fileReadStream = fs.createReadStream(image.url);
  const imageFile = new FileWrapper(fileReadStream, {
    contentType: 'image/jpeg',
  });

  try {
    const { result: { image } } = await catalogApi.createCatalogImage(request,imageFile);
    success();

  } catch (error) {
    console.error("Image upload failed with error: ", error);
  }
}

/*
 * Helper function to get the appropriate currency to be used based on the location ID provided.
 */
const getCurrency = async () => {
  // get the currency for the location
  try {
    const locationResponse = await locationsApi.retrieveLocation("main");
    const currency = locationResponse.result.location.currency;
    return currency;
  } catch (error) {
    console.error("Retrieving currency failed: ", error);
  }
}

/*
 * addItems adds all the objects from the sample-seed-data.json file
 * and add each object to the catalog in a batch. It also calls addImages to upload
 * the corresponding image file after getting the new Square Object IDs from uploading.
 * Visit for more information:
 * https://developer.squareup.com/reference/square/catalog-api/batch-upsert-catalog-objects
 */
const addItems = async () => {
  const currency = await getCurrency();
  // Only proceed if currency is available.
  if (currency) {
    console.log("Currency " + currency + " was successfully detected.");
    const batches = [{
      objects: []
    }];
    const batchUpsertCatalogRequest = {
      // Each request needs a unique idempotency key.
      idempotencyKey: crypto.randomUUID(),
      batches: batches,
    };

    // Iterate through each item in the sample-seed-data.json file.
    // For each item, we want to replace the hardcoded value "CURRENCY" with the actual currency
    // that we retrieved based on the location.
    for (const item in sampleData) {
      const currentCatalogItem = sampleData[item];
      // If the current element is an ITEM, we need to set its currency.
      // Otherwise, there is no currency attached.
      if (currentCatalogItem.data && currentCatalogItem.data.itemData) {
        // It is an ITEM, so set the currency for all variations.
        const variations = currentCatalogItem.data.itemData.variations;
        for (const variation of variations) {
          variation.itemVariationData.priceMoney.currency = currency;
        }
      }
      // Add the object data to the batch request item.
      batchUpsertCatalogRequest.batches[0].objects.push(currentCatalogItem.data);
    }

    try {
      // We call the Catalog API function batchUpsertCatalogObjects to upload all our
      // items at once.
      const { result : { idMappings }} = await catalogApi.batchUpsertCatalogObjects(
        batchUpsertCatalogRequest
      );

      // The new catalog objects will be returned with a corresponding Square Object ID.
      // Using the new Square Object ID, we map each object with their image and upload their image.
      idMappings.forEach(function (idMapping) {
        const clientObjectId = idMapping.clientObjectId;
        const objectId = idMapping.objectId;

        if (sampleData[clientObjectId] && sampleData[clientObjectId].image) {
          const image = sampleData[clientObjectId].image;
          addImages(image, objectId, () => {
            console.log("Successfully uploaded image for item:", clientObjectId);
          });
        }
      });
    } catch (error) {
      console.error("Updating catalog items failed: ", error);
    }
  }
}

/*
 * Given a list of catalogObjects, returns a list of the catalog object IDs.
 * @param Object of CatalogObjects (https://developer.squareup.com/reference/square/catalog-api/list-catalog)
 * @returns Object with an array of Object Ids
 */
function getCatalogObjectIds(objects) {
  const catalogObjectIds = {
    objectIds: []
  };
  for (const key in objects) {
    catalogObjectIds["objectIds"].push(objects[key].id);
  }
  return catalogObjectIds;
}

/*
 * Function that clears every object in the catalog.
 * WARNING: This is permanent and irreversable!
 */
const clearCatalog = async () => {
  try {
    const { result: { objects } } = await catalogApi.listCatalog(undefined, "ITEM,ITEM_VARIATION,TAX,IMAGE,CATEGORY");
    if (objects && objects.length > 0) {
      const catalogObjectIds = getCatalogObjectIds(objects);
      const { result: { deletedObjectIds } } = await catalogApi.batchDeleteCatalogObjects(
        catalogObjectIds
      );
      console.log("Successfully deleted catalog items ", deletedObjectIds);
    } else {
      console.log("No items to delete from catalog");
    }
  } catch (error) {
    console.error("Error in deleting catalog items:", error);
  }
}

/*
 * Main driver for the script.
 */
const args = process.argv.slice(2);
if (args[0] == "clear") {
  const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
  });
  rl.question("Are you sure you want to clear everything in your sandbox catalog? (y/N) ", (ans) => {
    if (ans.toUpperCase() === "Y") {
      clearCatalog();
    } else if (ans.toUpperCase() === "N") {
      console.log("Aborting clear.");
    }
    rl.close();
  });
} else if (args[0] == "generate") {
  addItems();
} else if (args[0] == "-h" || args[0] == "--help") {
  console.log(
    "Please check the README.md for more information on how to run our catalog script.\nAvailable commands include:\n npm run seed - Generates catalog items for your sandbox catalog.\n npm run clear - Clears your sandbox catalog of all items.\n\n More information can also be found on our Quick Start guide at https://developer.squareup.com/docs/orders-api/quick-start/start."
  );
} else {
  console.log("Command not recognized. Please try again.");
}
