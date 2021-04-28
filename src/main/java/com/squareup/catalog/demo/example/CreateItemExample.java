/*
 * Copyright 2017 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.catalog.demo.example;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogObjectBatch;

import java.util.List;
import java.util.UUID;

import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.catalog.demo.util.CatalogObjects.itemVariation;
import static java.util.Collections.singletonList;

import java.io.IOException;

/**
 * This example creates a new CatalogItem called "Soda" with three
 * CatalogItemVariations: Small, Medium, and Large. It then uploads the new
 * objects, retrieves the newly creted CatalogItem from the server, and prints
 * the name and ID to the screen.
 **/
public class CreateItemExample extends Example {

  public CreateItemExample(Logger logger) {
    super("create_item", "Create an item, then retrieve it.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException, IOException {
    // First create the parent CatalogItem.
    CatalogObject newItem = createItem(catalogApi);
    if (newItem == null) {
      return;
    }

    // Now let's retrieve the CatalogItem.
    retrieveItem(catalogApi, newItem.getId());
  }

  @Override public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException, IOException {
    cleanCatalogObjectsByName(catalogApi, CatalogObjectTypes.ITEM.toString(), "Soda");
  }

  /**
   * Creates a new item and returns it.
   **/
  private CatalogObject createItem(CatalogApi catalogApi) throws ApiException, IOException {
    /*
     * Build the request to create the new item.
     *
     * This function call creates a BatchUpsertCatalogObjectsRequest object
     * (request) populated with four new CatalogObjects:
     *   - a CatalogItem called "Soda" with ID "#SODA"
     *   - a CatalogItemVariation child called "Small" with ID "#SODA-SMALL"
     *   - a CatalogItemVariation child called "Medium" with ID "#SODA-MEDIUM"
     *   - a CatalogItemVariation child called "Large" with ID "#SODA-LARGE"
     *
     * Note: this call only *creates* the new objects and packages them for
     * upsert. Nothing has been uploaded to the server at this point.
     */
    List<CatalogObject> objects = singletonList(
        item("#SODA", "Soda", null,
        itemVariation("#SODA-SMALL", "Small", 150),
        itemVariation("#SODA-MEDIUM", "Medium", 175),
        itemVariation("#SODA-LARGE", "Large", 200))
    );

    CatalogObjectBatch batch = new CatalogObjectBatch(objects);

    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder(
        UUID.randomUUID().toString(),
        singletonList(batch))
        .build();

    /*
     * Post the batch upsert to insert the new item.
     *
     * Use the BatchUpsertCatalogObjectsRequest object we just created to
     * upsert the new CatalogObjects to the catalog associated with the
     * access token included on the command line.
     */
    logger.info("Creating new Soda item");
    return catalogApi.batchUpsertCatalogObjectsAsync(request).thenApply(result -> {
        /*
        * If the response is successful, we want to log the list of object IDs that
        * were successfully created in the catalog (e.g., #SODA, #SODA-LARGE)
        */
        if (checkAndLogErrors(result.getErrors())) {
            return null;
        }
        CatalogObject newItem = result.getObjects().get(0);
        logger.info("Created item " + newItem.getId());
        return newItem;
    }).exceptionally(exception -> {
        // Log excpetion, return null.
        logger.error(exception.getMessage());
        return null;
    }).join();
  }

  /**
   * Retrieves the item we just created.
   *
   * @param itemId the ID of the newly created item.
   **/
  private void retrieveItem(CatalogApi catalogApi, String itemId) throws ApiException, IOException {
    // Send GET request to retrieve a single object based on the object ID.
    logger.info("Retrieving item with id: " + itemId);

    // Set optional parameter as null.
    Long catalogVersion = null;
    catalogApi.retrieveCatalogObjectAsync(itemId, false, catalogVersion).thenAccept(result -> {
        if (checkAndLogErrors(result.getErrors())) {
            return;
        }
        /*
        * Otherwise, grab the name and object ID of the CatalogItem that was
        * fetched from the catalog and print them to the screen.
        */
        CatalogObject retrieveditem = result.getObject();
        logger.info("Retrieved Item "
            + retrieveditem.getItemData().getName()
            + " ("
            + retrieveditem.getId()
            + ")");
    }).exceptionally(exception -> {
        // Log exception, return null.
        logger.error(exception.getMessage());
        return null;
    });
  }
}
