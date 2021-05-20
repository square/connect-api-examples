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

import static com.squareup.catalog.demo.util.Prompts.promptUserInput;

import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.square.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.square.models.CatalogObject;
import java.util.concurrent.CompletionException;

/**
 * This example deletes all items and variations in a merchant's Item Library.
 */
public class DeleteAllItemsExample extends Example {

  private static final String CONFIRMATION_PROMPT =
      "Are you sure you want to delete ALL items in your Item Library?"
          + " This action cannot be undone. Type 'DELETE' to confirm: ";

  public DeleteAllItemsExample(Logger logger) {
    super("delete_all_items",
        "Deletes ALL items. This is a destructive action and cannot be undone.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {

    // Prompt the user to verify that they actually want to delete all items in the
    // library.
    if (!promptUserInput(CONFIRMATION_PROMPT).equals("DELETE")) {
      logger.info("Example aborted");
      return;
    }

    // Optional parameters can be set to null.
    Long catalogVersion = null;

    final long startTimeMillis = System.currentTimeMillis();
    int totalDeletedItems = 0;
    String cursor = null;

    do {
      try {
        ListCatalogResponse result =
            catalogApi.listCatalogAsync(cursor, CatalogObjectTypes.ITEM.toString(), catalogVersion)
                .join();

        List<CatalogObject> items = result.getObjects();
        if (items == null || items.size() == 0) {
          if (cursor == null) {
            logger.info("No items found. Item Library was already empty.");
            return;
          }
        } else {
          // Delete all items in the page of results.
          List<String> ids = new ArrayList<>();
          for (CatalogObject catalogObject : items) {
            ids.add(catalogObject.getId());
          }
          BatchDeleteCatalogObjectsRequest deleteRequest =
              new BatchDeleteCatalogObjectsRequest(ids);

          catalogApi.batchDeleteCatalogObjectsAsync(deleteRequest).join();

          logger.info("Deleted " + items.size() + " items");
          totalDeletedItems += items.size();
        }
        cursor = result.getCursor();
      } catch (CompletionException e) {
        // Extract the actual exception
        ApiException exception = (ApiException) e.getCause();
        if (checkAndLogErrors(exception.getErrors())) {
          return;
        }
      }
    } while (cursor != null);

    // Log information about the deleted items.
    long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
    logDeletedItems(elapsedTimeSeconds, totalDeletedItems);
  }

  private void logDeletedItems(long elapsedTime, int totalDeletedItems) {
    logger.info("Complete! Deleted "
        + totalDeletedItems
        + " total items in "
        + elapsedTime
        + " seconds");
  }
}
