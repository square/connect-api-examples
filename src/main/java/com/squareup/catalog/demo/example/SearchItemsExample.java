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

import static java.util.Collections.singletonList;

import com.squareup.square.exceptions.ApiException;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogQuery;
import com.squareup.square.models.CatalogQueryExact;
import com.squareup.square.models.SearchCatalogObjectsRequest;

/**
 * This example searches for CatalogItems with the name "Soda" and prints the names and IDs to the
 * screen.
 */
public class SearchItemsExample extends Example {

  public SearchItemsExample(Logger logger) {
    super("search_items", "Searches for items.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {
    /*
     * Build the search request
     *
     * This function call creates a SearchCatalogObjectsRequest object (request)
     * configured to search across the catalog for any CatalogObject whose name
     * exactly matches the string "Soda"
     *
     * Note: this call only packages the search request object. Nothing has been
     * queried at this point.
     */

    // An Exact query searches for exact matches of the specified attribute.
    CatalogQuery query = new CatalogQuery.Builder()
        // Searching on the item name field, must exactly match "Soda".
        .exactQuery(new CatalogQueryExact("name", "Soda")).build();

    SearchCatalogObjectsRequest request = new SearchCatalogObjectsRequest.Builder()
        .objectTypes(singletonList(CatalogObjectTypes.ITEM.toString())).query(query).build();

    // Post the search request and log the results
    catalogApi.searchCatalogObjectsAsync(request).thenAccept(result -> {

      if (result.getObjects() != null) {
        List<CatalogObject> catalogObjects = result.getObjects();
        logger.info("Found " + result.getObjects().size() + " results");
        for (int i = 0; i < catalogObjects.size(); i++) {
          CatalogObject item = catalogObjects.get(i);
          logger.info((i + 1)
              + ": "
              + item.getItemData().getName()
              + " ("
              + item.getId()
              + ")");
        }
      } else {
        logger.info("No items with the name \"Soda\" were found.");
      }
    }).exceptionally(exception -> {
      // Extract the actual exception
      ApiException e = (ApiException) exception.getCause();
      checkAndLogErrors(e.getErrors());
      return null;
    }).join();
  }
}
