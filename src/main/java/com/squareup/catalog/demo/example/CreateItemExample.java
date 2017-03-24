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

import com.squareup.catalog.demo.api.CatalogApi;
import com.squareup.catalog.demo.api.LocationApi;
import com.squareup.catalog.resources.CatalogObject;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsRequest;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsResponse;
import com.squareup.catalog.service.CatalogObjectBatch;
import com.squareup.catalog.service.RetrieveCatalogObjectResponse;
import java.io.IOException;
import java.util.UUID;

import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.catalog.demo.util.CatalogObjects.itemVariation;
import static java.util.Collections.singletonList;

/**
 * This example creates a new item called "Soda" with three variations: Small, Medium, and Large.
 * It then retrieves the item from the server.
 */
public class CreateItemExample extends Example {

  public CreateItemExample() {
    super("create_item", "Create an item, then retrieve it.");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationApi locationApi) throws IOException {
    // First create the item.
    CatalogObject newItem = createItem(catalogApi);
    if (newItem == null) {
      return;
    }

    // Now let's retrieve the item.
    retrieveItem(catalogApi, newItem.id);
  }

  /**
   * Creates a new item and returns it.
   */
  private CatalogObject createItem(CatalogApi catalogApi) throws IOException {
    // Build the request to create the new item.
    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder()
        .idempotency_key(UUID.randomUUID().toString())
        .batches(singletonList(new CatalogObjectBatch.Builder()
            .objects(singletonList(
                item("#SODA", "Soda", null,
                    itemVariation("#SODA-SMALL", "Small", 150),
                    itemVariation("#SODA-MEDIUM", "Medium", 175),
                    itemVariation("#SODA-LARGE", "Large", 200)
                )))
            .build()))
        .build();

    // Post the batch upsert to insert the new item.
    logger.info("Creating new Soda item");
    BatchUpsertCatalogObjectsResponse response = catalogApi.batchUpsert(request);

    // If the response is null, we already logged errors.
    if (response == null) {
      return null;
    }

    CatalogObject newItem = response.objects.get(0);
    logger.info("Created item " + newItem.id);
    return newItem;
  }

  /**
   * Retrieves the item we just created.
   *
   * @param itemId the ID of the newly created item.
   */
  private void retrieveItem(CatalogApi catalogApi, String itemId) {
    try {
      // Send GET request to retrieve object.
      logger.info("Retrieving item with id: " + itemId);
      RetrieveCatalogObjectResponse response = catalogApi.retrieveObject(itemId);

      // If the response is null, we already logged errors.
      if (response == null) {
        return;
      }

      CatalogObject retrieveditem = response.object;
      logger.info("Retrieved Item " + retrieveditem.item_data.name + " (" + retrieveditem.id + ")");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
