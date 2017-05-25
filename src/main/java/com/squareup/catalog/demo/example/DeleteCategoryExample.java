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

import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.connect.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogIdMapping;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogObjectBatch;
import com.squareup.connect.models.SearchCatalogObjectsRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.squareup.catalog.demo.util.CatalogObjects.category;
import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.connect.models.SearchCatalogObjectsRequest.ObjectTypesEnum.CATEGORY;
import static com.squareup.connect.models.SearchCatalogObjectsRequest.ObjectTypesEnum.ITEM;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * This example creates a new CatalogObject category with three CatalogItems
 * (soda, water, and juice). It then deletes the newly created category and all
 * of the CatalogItems in the category and prints the list of object IDs that
 * were successfully deleted to the screen.
 **/
public class DeleteCategoryExample extends Example {

  public DeleteCategoryExample() {
    super("delete_category",
        "Create a category with three items, then delete the category and items.");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    // Create a category with items.
    BatchUpsertCatalogObjectsResponse response = createCategoryWithItems(catalogApi);
    if (checkAndLogErrors(response.getErrors())) {
      return;
    }

    // Now delete them.
    deleteCategoryAndItems(catalogApi, response.getObjects());
  }

  @Override public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException {
    cleanCatalogObjectsByName(catalogApi, ITEM, "Soda");
    cleanCatalogObjectsByName(catalogApi, ITEM, "Water");
    cleanCatalogObjectsByName(catalogApi, ITEM, "Juice");
    cleanCatalogObjectsByName(catalogApi, CATEGORY, "Drinks");
  }

  /**
   * Creates a new category "Drinks" containing three items: Soda, Water, Juice.
   *
   * @return an array containing the new category and items
   **/
  private BatchUpsertCatalogObjectsResponse createCategoryWithItems(CatalogApi catalogApi)
      throws ApiException {
    /*
     * Build the request to create the item and categories.
     *
     * This function call creates a BatchUpsertCatalogObjectsRequest object
     * (request) and uses the CatalogObjectBatch builder to populate it with
     * three new CatalogObjects and assign them to the "#CATAGORY-DRINKS"
     * category:
     *   - a CatalogItem called "Soda" with ID "#ITEM-SODA"
     *   - a CatalogItem called "Water" with ID "#ITEM-WATER"
     *   - a CatalogItem called "Juice" with ID "#ITEM-JUICE"
     *
     * Note: this call only *creates* the new objects and packages them for
     * upsert. Nothing has been uploaded to the server at this point.
     */
    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest()
        .idempotencyKey(UUID.randomUUID().toString())
        .batches(singletonList(new CatalogObjectBatch()
            .objects(asList(
                category("#CATEGORY-DRINKS", "Drinks"),
                item("#ITEM-SODA", "Soda", "#CATEGORY-DRINKS", 150),
                item("#ITEM-WATER", "Water", "#CATEGORY-DRINKS", 0),
                item("#ITEM-JUICE", "Juice", "#CATEGORY-DRINKS", 200)
            ))));

    /*
     * Post the batch upsert to insert the new category and items.

     * Use the BatchUpsertCatalogObjectsRequest object we just created to
     * upsert the new catagory and CatalogObjects to the catalog associated with
     * the access token included on the command line.
     */
    logger.info("Creating new Drinks category with three items");
    BatchUpsertCatalogObjectsResponse response = catalogApi.batchUpsertCatalogObjects(request);
    if (checkAndLogErrors(response.getErrors())) {
      return null;
    }

    /*
     * If the response is not null, we want to log the list of object IDs that
     * were successfully created in the catalog (e.g., #ITEM-SODA, #ITEM-JUICE)
     **/
    logger.info("Created " + response.getObjects().size() + " new objects");

    /*
     * We also want to log the category ID that was generated by the server when
     * the new CatalogItems were added to the catalog
     **/
    CatalogIdMapping categoryIdMapping = response.getIdMappings().stream()
        .filter(mapping -> mapping.getClientObjectId().equals("#CATEGORY-DRINKS"))
        .findFirst()
        .orElse(null);
    logger.info("Sever generated category ID: " + categoryIdMapping.getObjectId());
    return response;
  }

  /**
   * Deletes all of the specified catalog objects.
   **/
  private void deleteCategoryAndItems(CatalogApi catalogApi, List<CatalogObject> objectsToDelete)
      throws ApiException {
    /*
     * Convert the list of CatalogObjects (objectsToDelete) to a list of string
     * IDs so can use them to populate a new BatchDeleteCatalogObjectsRequest.
     **/
    List<String> idsToDelete =
        objectsToDelete.stream().map(CatalogObject::getId).collect(Collectors.toList());

    /*
     * Build the BatchDeleteCatalogObjectsRequest object (request) to remove the
     * targeted CatalogObjects (in this case our newly created category and
     * CatalogItems)

     * Note: this call only *creates* the new objects and packages them for
     * batch delete. Nothing has been done to the catalog on the server at this
     * point.
     **/
    BatchDeleteCatalogObjectsRequest request = new BatchDeleteCatalogObjectsRequest()
        .objectIds(idsToDelete);

    // Post the batch delete to delete the category and items.
    logger.info("Deleting Drinks category");
    BatchDeleteCatalogObjectsResponse response = catalogApi.batchDeleteCatalogObjects(request);
    if (checkAndLogErrors(response.getErrors())) {
      return;
    }

    // Otherwise, log the IDs of all the objects that were successfully deleted
    logger.info("Deleted " + response.getDeletedObjectIds().size() + " objects");
  }
}
