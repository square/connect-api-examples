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
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogQuery;
import com.squareup.connect.models.CatalogQueryExact;
import com.squareup.connect.models.SearchCatalogObjectsRequest;
import com.squareup.connect.models.SearchCatalogObjectsResponse;
import java.util.List;

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
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    /*
     * Build the search request
     *
     * This function call creates a SearchCatalogObjectsRequest object
     * (request) configured to search across the catalog for any CatalogObject
     * whose name exactly matches the string "Soda"
     *
     * Note: this call only packages the search request object. Nothing has been
     * queried at this point.
     */
    SearchCatalogObjectsRequest request = new SearchCatalogObjectsRequest()
        .objectTypes(singletonList(SearchCatalogObjectsRequest.ObjectTypesEnum.ITEM))
        .query(new CatalogQuery()
            // An Exact query searches for exact matches of the specified attribute.
            .exactQuery(new CatalogQueryExact()
                .attributeName("name")  // Searching on the item name field
                .attributeValue("Soda") // Must exactly match "Soda"
            )
        );

    // Post the search request and log the results
    logger.info("Searching for items named 'Soda'");
    SearchCatalogObjectsResponse response = catalogApi.searchCatalogObjects(request);
    if (response != null) {
      List<CatalogObject> catalogObjects = response.getObjects();
      logger.info("Found " + response.getObjects().size() + " results");
      for (int i = 0; i < catalogObjects.size(); i++) {
        CatalogObject item = catalogObjects.get(i);
        logger.info((i + 1) + ": " + item.getItemData().getName() + " (" + item.getId() + ")");
      }
    }
  }
}
