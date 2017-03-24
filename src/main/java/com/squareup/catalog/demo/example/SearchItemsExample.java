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
import com.squareup.catalog.resources.CatalogObjectType;
import com.squareup.catalog.service.CatalogQuery;
import com.squareup.catalog.service.SearchCatalogObjectsRequest;
import com.squareup.catalog.service.SearchCatalogObjectsResponse;
import java.io.IOException;

import static java.util.Collections.singletonList;

/**
 * This example searches for CatalogItems with the name "Soda" and prints
 * the names and IDs to the screen.
 **/
public class SearchItemsExample extends Example {

  public SearchItemsExample() {
    super("search_items", "Search for items.");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationApi locationApi) throws IOException {
    // Build a search request.
    /**
     * Build the search request
     *
     * This function call creates a SearchCatalogObjectsRequest object
     * (request) configured to search across the catalog for any CatalogObject
     * whose name exactly matches the string "Soda"
     *
     * Note: this call only packages the search request object. Nothing has been
     * queried at this point.
     **/
    SearchCatalogObjectsRequest request = new SearchCatalogObjectsRequest.Builder()
        .object_types(singletonList(CatalogObjectType.ITEM))
        .query(new CatalogQuery.Builder()
            // An Exact query searches for exact matches of the specified attribute.
            .exact_query(new CatalogQuery.Exact.Builder()
                .attribute_name("name")  // Searching on the item name field
                .attribute_value("Soda") // Must exactly match "Soda"
                .build())
            .build())
        .build();

    // Post the search request and log the results
    logger.info("Searching for items named 'Soda'");
    SearchCatalogObjectsResponse response = catalogApi.search(request);
    if (response != null) {
      logger.info("Found " + response.objects.size() + " results");
      for (int i = 0; i < response.objects.size(); i++) {
        CatalogObject item = response.objects.get(i);
        logger.info((i + 1) + ": " + item.item_data.name + " (" + item.id + ")");
      }
    }
  }
}
