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
import com.squareup.catalog.service.BatchDeleteCatalogObjectsRequest;
import com.squareup.catalog.service.BatchDeleteCatalogObjectsResponse;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsRequest;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsResponse;
import com.squareup.catalog.service.CatalogIdMapping;
import com.squareup.catalog.service.CatalogObjectBatch;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.xml.stream.Location;

import static com.squareup.catalog.demo.util.CatalogObjects.category;
import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * This example creates a category containing three items. It then deletes the category and all of
 * the items in the category.
 */
public class DeleteCategoryExample extends Example {

  public DeleteCategoryExample() {
    super("delete_category",
        "Create a category with three items, then delete the category and items.");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationApi locationApi) throws IOException {
    // Create a category with items.
    BatchUpsertCatalogObjectsResponse response = createCategoryWithItems(catalogApi);
    if (response == null) {
      return;
    }

    // Now delete them.
    deleteCategoryAndItems(catalogApi, response.objects);
  }

  /**
   * Creates a new category "Drinks" containing three items: Soda, Water, Juice.
   *
   * @return an array containing the new category and items
   */
  private BatchUpsertCatalogObjectsResponse createCategoryWithItems(CatalogApi catalogApi)
      throws IOException {
    // Build the request to create the item and categories.
    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder() //
        .idempotency_key(UUID.randomUUID().toString())
        .batches(singletonList(new CatalogObjectBatch.Builder()
            .objects(asList(
                category("#CATEGORY-DRINKS", "Drinks"),
                item("#ITEM-SODA", "Soda", "#CATEGORY-DRINKS", 150),
                item("#ITEM-WATER", "Water", "#CATEGORY-DRINKS", 0),
                item("#ITEM-JUIVE", "Juice", "#CATEGORY-DRINKS", 200)
            ))
            .build()))
        .build();

    // Post the batch upsert to insert the new category and items.
    logger.info("Creating new Drinks category with three items");
    BatchUpsertCatalogObjectsResponse response = catalogApi.batchUpsert(request);

    // If the response is null, we already logged errors.
    if (response == null) {
      return null;
    }

    logger.info("Created " + response.objects.size() + " new objects");

    // Lookup the sever generated category ID.
    CatalogIdMapping categoryIdMapping = response.id_mappings.stream()
        .filter(mapping -> mapping.client_object_id.equals("#CATEGORY-DRINKS"))
        .findFirst()
        .orElse(null);
    logger.info("Sever generated category ID: " + categoryIdMapping.object_id);
    return response;
  }

  /**
   * Deletes all of the specified catalog objects.
   */
  private void deleteCategoryAndItems(CatalogApi catalogApi, List<CatalogObject> objectsToDelete)
      throws IOException {
    // Convert list of CatalogObject to list of IDs.
    List<String> idsToDelete =
        objectsToDelete.stream().map(object -> object.id).collect(Collectors.toList());

    // Build the request to create the item and categories.
    BatchDeleteCatalogObjectsRequest request = new BatchDeleteCatalogObjectsRequest.Builder() //
        .object_ids(idsToDelete)
        .build();

    // Post the batch delete to delete the category and items.
    logger.info("Deleting Drinks category");
    BatchDeleteCatalogObjectsResponse response = catalogApi.batchDelete(request);

    // If the response is null, we already logged errors.
    if (response == null) {
      return;
    }

    logger.info("Deleted " + response.deleted_object_ids.size() + " objects");
  }
}
