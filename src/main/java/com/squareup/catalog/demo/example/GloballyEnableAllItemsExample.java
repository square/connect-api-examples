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
import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogItem;
import com.squareup.connect.models.CatalogItem.ProductTypeEnum;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogObjectBatch;
import com.squareup.connect.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.squareup.connect.models.CatalogItem.ProductTypeEnum.REGULAR;
import static com.squareup.connect.models.CatalogObjectType.ITEM;
import static java.util.Collections.emptyList;

/**
 * This example makes all items available at all current and future locations.
 **/
public class GloballyEnableAllItemsExample extends Example {

  public GloballyEnableAllItemsExample(Logger logger) {
    super("globally_enable_items", "Make all items available at all locations.", logger);
  }

  @Override public void execute(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException {
    String cursor = null;
    int totalItemsUpdated = 0;
    int totalItemsVisited = 0;
    final long startTimeMillis = System.currentTimeMillis();
    do {
      // Retrieve a page of items.
      ListCatalogResponse listResponse = catalogApi.listCatalog(cursor, ITEM.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        return;
      }

      List<CatalogObject> items = listResponse.getObjects();
      if (items.size() == 0) {
        if (cursor == null) {
          logger.info("No items found in catalog.");
          return;
        }
      } else {
        // Iterate over the items, enabling them at all locations.
        List<CatalogObject> itemsToUpdate = new ArrayList<>();
        for (CatalogObject itemObject : items) {
          CatalogItem item = itemObject.getItemData();
          ProductTypeEnum productType = item.getProductType();
          boolean isRegularItem = productType == null || productType == REGULAR;
          boolean isGloballyEnabled = itemObject.getPresentAtAllLocations()
              && itemObject.getPresentAtLocationIds().isEmpty()
              && itemObject.getAbsentAtLocationIds().isEmpty();

          // Ignore items that are not REGULAR or are already globally enabled.
          if (isRegularItem && !isGloballyEnabled) {
            // Globally enable the item.
            itemObject.setPresentAtAllLocations(true);
            itemObject.setPresentAtLocationIds(emptyList());
            itemObject.setAbsentAtLocationIds(emptyList());

            // Update each variation's location settings to match the locations of the parent item.
            for (CatalogObject variation : item.getVariations()) {
              variation.setPresentAtAllLocations(itemObject.getPresentAtAllLocations());
              variation.setPresentAtLocationIds(itemObject.getPresentAtLocationIds());
              variation.setAbsentAtLocationIds(itemObject.getAbsentAtLocationIds());
            }

            // Add this item to the list of items to update.
            itemsToUpdate.add(itemObject);
          }
        }

        // Send a batch upsert request to apply the new location settings to the items.
        if (itemsToUpdate.size() > 0) {
          BatchUpsertCatalogObjectsRequest batchUpsertRequest =
              new BatchUpsertCatalogObjectsRequest()
                  .idempotencyKey(UUID.randomUUID().toString())
                  .addBatchesItem(new CatalogObjectBatch()
                      .objects(itemsToUpdate)
                  );
          BatchUpsertCatalogObjectsResponse batchUpsertResponse =
              catalogApi.batchUpsertCatalogObjects(batchUpsertRequest);
          if (checkAndLogErrors(batchUpsertResponse.getErrors())) {
            return;
          }

          // Add the updated item count to the total number of updated items.
          totalItemsUpdated += itemsToUpdate.size();
        }

        // Log info about this page of items we just deleted.
        long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        totalItemsVisited += items.size();
        logger.info("Updated "
            + itemsToUpdate.size()
            + " items ("
            + totalItemsVisited
            + " total items processed in "
            + elapsedTimeSeconds
            + " seconds)");
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);

    // Log overall results.
    long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
    logger.info("Success! Updated "
        + totalItemsUpdated
        + " total items in "
        + elapsedTimeSeconds
        + " seconds");
  }
}
