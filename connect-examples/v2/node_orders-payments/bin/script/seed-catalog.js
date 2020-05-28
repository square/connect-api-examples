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

const SquareConnect = require("square-connect");
const config = require("../../config.json")["sandbox"]; // We don't recommend to run this script in production environment
const sample_data = require("./sample-seed-data.json");
const request = require("request");
const fs = require("fs");
const readline = require("readline");

const defaultClient = SquareConnect.ApiClient.instance;
// Default connect to sandbox.
defaultClient.basePath = config.path;

// Configure OAuth2 access token for authorization: oauth2
const oauth2 = defaultClient.authentications["oauth2"];
oauth2.accessToken = config.squareAccessToken;

// Configure catalog API instance
const catalogApi = new SquareConnect.CatalogApi();

/*
 * Given an object with image data and a corresponding catalogObjectId,
 * calls the createCatalogImage API and uploads the image to the corresponding catalogObjectId.
 * For more information on the createCatalogImage API, visit:
 * https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-createcatalogimage
 * @param Object with Image information
 * @param String catalogObjectId
 */
async function addImages(image, catalogObjectId, success) {
  // Create JSON request with required image information requirements.
  const img_req = {
    idempotency_key: require("crypto").randomBytes(64).toString("hex"),
    object_id: catalogObjectId,
    image: {
      id: image.id,
      type: "IMAGE",
      image_data: {
        caption: image.caption,
      },
    },
  };

  // Headers for REST request.
  const headers = {
    Accept: "application/json",
    Authorization: "Bearer " + oauth2.accessToken,
    "Cache-Control": "no-cache",
    "Square-Version": "2019-06-12",
    "Content-Disposition": "form-data; name=\"name\"; filename=\"name.jpg\"",
    "Content-Type": "multipart/form-data",
  };

  // Build form data since the createCatalogAPI only accepts multipart/form-data content-type.
  // It consists of a JSON request and the image file.
  const formData = {
    request: JSON.stringify(img_req),
    img_file: fs.createReadStream(image.url),
  };

  // Make the request to createCatalogImage API.
  request.post({
    headers: headers,
    url: `${config.path}/v2/catalog/images`,
    formData: formData,
  },
  function result(err, httpResponse, body) {
    if (err) {
      console.error("Image upload failed with error: ", err);
    } else {
      success();
    }
  }
  );
}

/*
 * addItems adds all the objects from the sample-seed-data.json file
 * and add each object to the catalog in a batch. It also calls addImages to upload
 * the corresponding image file after getting the new Square Object IDs from uploading.
 * Visit for more information:
 * https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-batchupsertcatalogobjects
 */
async function addItems() {
  const batches = [{
    objects: []
  }];
  const batchUpsertCatalogRequest = {
    // Each request needs a unique idempotency key.
    idempotency_key: require("crypto").randomBytes(64).toString("hex"),
    batches: batches,
  };

  // Iterate through each item in the sample-seed-data.json file.
  for (const item in sample_data) {
    const currentCatalogItem = sample_data[item];
    // Add the object data to the batch request item.
    batchUpsertCatalogRequest.batches[0].objects.push(currentCatalogItem.data);
  }

  try {
    // We call the Catalog API function batchUpsertCatalogObjects to upload all our
    // items at once.
    const newCatalogObjects = await catalogApi.batchUpsertCatalogObjects(
      batchUpsertCatalogRequest
    );

    // The new catalog objects will be returned with a corresponding Square Object ID.
    // Using the new Square Object ID, we map each object with their image and upload their image.
    newCatalogObjects.id_mappings.forEach(function (id_mapping) {
      const client_object_id = id_mapping.client_object_id;
      const object_id = id_mapping.object_id;

      if (sample_data[client_object_id] && sample_data[client_object_id].image) {
        const image = sample_data[client_object_id].image;
        addImages(image, object_id, () => {
          console.log("Successfully uploaded image for item:", client_object_id);
        });
      }
    });
  } catch (error) {
    console.error("Updating catalog items failed: ", error);
  }
}

/*
 * Given a list of catalogObjects, returns a list of the catalog object IDs.
 * @param Object of CatalogObjects (https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-listcatalog)
 * @returns Object with an array of Object Ids
 */
function getCatalogObjectIds(catalogObjects) {
  const catalogObjectIds = {
    object_ids: []
  };
  for (const key in catalogObjects.objects) {
    catalogObjectIds["object_ids"].push(catalogObjects.objects[key].id);
  }
  return catalogObjectIds;
}

/*
 * Function that clears every object in the catalog.
 * WARNING: This is permanent and irreversable!
 */
async function clearCatalog() {
  try {
    const catalogObjects = await catalogApi.listCatalog({ types: "ITEM,ITEM_VARIATION,TAX,IMAGE,CATEGORY" });
    if (catalogObjects.objects && catalogObjects.objects.length > 0) {
      const catalogObjectIds = getCatalogObjectIds(catalogObjects);
      const result = await catalogApi.batchDeleteCatalogObjects(
        catalogObjectIds
      );
      console.log("Successfully deleted catalog items ", result);
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
