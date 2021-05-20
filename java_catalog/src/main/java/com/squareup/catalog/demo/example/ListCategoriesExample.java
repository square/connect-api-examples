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

import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.ListCatalogResponse;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.CatalogCategory;
import com.squareup.square.models.CatalogObject;
import java.util.concurrent.CompletionException;

/**
 * This example lists all categories in the catalog.
 */
public class ListCategoriesExample extends Example {

  public ListCategoriesExample(Logger logger) {
    super("list_categories", "Lists all categories.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {

    // Optional parameters can be set to null.
    Long catalogVersion = null;

    String cursor = null;

    do {
      try {
        // Retrieve a page of categories.
        ListCatalogResponse result =
            catalogApi.listCatalogAsync(cursor, CatalogObjectTypes.CATEGORY.toString(),
                catalogVersion).join();

        List<CatalogObject> categories = result.getObjects();
        if (categories == null || categories.size() == 0) {
          if (cursor == null) {
            logger.info("No categories found.");
            return;
          }
        } else {
          for (CatalogObject categoryObject : categories) {
            CatalogCategory category = categoryObject.getCategoryData();
            logger.info(category.getName() + " (" + categoryObject.getId() + ")");
          }
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
  }
}
