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
import com.squareup.connect.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.connect.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.ListCatalogResponse;
import java.util.List;

import static com.squareup.connect.models.CatalogObjectType.ITEM;

/**
 * This example deletes all items and variations in a merchant's Item Library.
 */
public class DeleteAllItemsExample extends Example {

  private static final String CONFIRMATION_PROMPT =
      "Are you sure you want to delete ALL items in your Item Library?"
          + " This action cannot be undone. Type 'DELETE' to confirm: ";

  public DeleteAllItemsExample(Logger logger) {
    super("delete_all_items",
        "Delete ALL items. This is a destructive action and cannot be undone.", logger);
  }

  @Override public void execute(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException {

    // Prompt the  user to verify that they actually want to delete all items in the library.
    if (!promptUserInput(CONFIRMATION_PROMPT).equals("DELETE")) {
      logger.info("Example aborted");
      return;
    }

    String cursor = null;
    int totalDeletedItems = 0;
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
          logger.info("No items found. Item Library was already empty.");
          return;
        }
      } else {
        // Delete all items in the page of results.
        BatchDeleteCatalogObjectsRequest deleteRequest = new BatchDeleteCatalogObjectsRequest();
        for (CatalogObject catalogObject : items) {
          deleteRequest.addObjectIdsItem(catalogObject.getId());
        }
        BatchDeleteCatalogObjectsResponse deleteResponse =
            catalogApi.batchDeleteCatalogObjects(deleteRequest);
        if (checkAndLogErrors(deleteResponse.getErrors())) {
          return;
        }

        // Log info about this page of items we just deleted.
        long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        totalDeletedItems += items.size();
        logger.info("Deleted "
            + items.size()
            + " items ("
            + totalDeletedItems
            + " total in "
            + elapsedTimeSeconds
            + " seconds)");
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);

    // Show completion message.
    long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
    logger.info("Complete! Deleted "
        + totalDeletedItems
        + " total items in "
        + elapsedTimeSeconds
        + " seconds");
  }
}
