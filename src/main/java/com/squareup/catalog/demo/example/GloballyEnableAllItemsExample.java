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

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.square.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogObjectBatch;
import java.util.concurrent.CompletionException;

/**
 * This example makes all items available at all current and future locations.
 **/
public class GloballyEnableAllItemsExample extends Example {

  public GloballyEnableAllItemsExample(Logger logger) {
    super("globally_enable_items", "Makes all items available at all locations.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {

    // Optional parameters can be set to null.
    Long catalogVersion = null;

    String cursor = null;
    int totalItemsUpdated = 0;
    int totalItemsVisited = 0;
    final long startTimeMillis = System.currentTimeMillis();

    do {
      try {
        // Retrieve a page of items.
        ListCatalogResponse result =
            catalogApi.listCatalogAsync(cursor, CatalogObjectTypes.ITEM.toString(), catalogVersion)
                .join();

        List<CatalogObject> items = result.getObjects();
        if (items == null || items.size() == 0) {
          if (cursor == null) {
            logger.info("No items found in catalog.");
            return;
          }
        } else {
          // Iterate over the items, enabling them at all locations.
          List<CatalogObject> itemsToUpdate = new ArrayList<>();
          for (CatalogObject itemObject : items) {
            CatalogItem item = itemObject.getItemData();
            String productType = item.getProductType();
            boolean isRegularItem = productType == null || productType.equals("REGULAR");
            boolean isGloballyEnabled = itemObject.getPresentAtAllLocations()
                && (itemObject.getPresentAtLocationIds() == null
                || itemObject.getPresentAtLocationIds().isEmpty())
                && (itemObject.getAbsentAtLocationIds() == null
                || itemObject.getAbsentAtLocationIds().isEmpty());

            // Ignore items that are not REGULAR or are already globally enabled.
            if (isRegularItem && !isGloballyEnabled) {
              // Globally enable the item.
              // Newer SDKs don't have a setter for fields, so we need to reconstruct the
              // object with the information we need.
              List<CatalogObject> variationClones = new ArrayList<>();
              // Update each variation's location settings to match the locations of the
              // parent item.
              for (CatalogObject variation : item.getVariations()) {
                CatalogObject variationClone =
                    new CatalogObject.Builder(variation.getType(), variation.getId())
                        .itemVariationData(variation.getItemVariationData())
                        .presentAtAllLocations(true)
                        .presentAtLocationIds(emptyList())
                        .absentAtLocationIds(emptyList())
                        .version(variation.getVersion())
                        .build();

                variationClones.add(variationClone);
              }

              CatalogItem itemClone = new CatalogItem.Builder()
                  .name(item.getName())
                  .productType(item.getProductType())
                  .variations(variationClones)
                  .taxIds(item.getTaxIds())
                  .categoryId(item.getCategoryId())
                  .build();

              CatalogObject objectClone =
                  new CatalogObject.Builder(itemObject.getType(), itemObject.getId())
                      .itemData(itemClone)
                      .presentAtAllLocations(true)
                      .presentAtLocationIds(emptyList())
                      .absentAtLocationIds(emptyList())
                      .version(itemObject.getVersion())
                      .build();

              // Add this item to the list of items to update.
              itemsToUpdate.add(objectClone);
            }
          }

          // Send a batch upsert request to apply the new location settings to the items.
          if (itemsToUpdate.size() > 0) {
            BatchUpsertCatalogObjectsRequest batchUpsertRequest =
                new BatchUpsertCatalogObjectsRequest.Builder(
                    UUID.randomUUID().toString(),
                    singletonList(new CatalogObjectBatch(itemsToUpdate))).build();

            catalogApi.batchUpsertCatalogObjectsAsync(batchUpsertRequest).join();

            // Add the updated item count to the total number of updated items.
            totalItemsUpdated += itemsToUpdate.size();
          }

          // Log info about this page of items we just updated.
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
        cursor = result.getCursor();
      } catch (CompletionException e) {
        // Extract the actual exception
        ApiException exception = (ApiException) e.getCause();
        if (checkAndLogErrors(exception.getErrors())) {
          return;
        }
      }
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
