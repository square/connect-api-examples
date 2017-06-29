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
import com.squareup.connect.models.CatalogCategory;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.ListCatalogResponse;

import static com.squareup.connect.models.CatalogObjectType.CATEGORY;

/**
 * This example lists all categories in the catalog.
 */
public class ListCategoriesExample extends Example {

  public ListCategoriesExample(Logger logger) {
    super("list_categories", "List all categories.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    String cursor = null;
    do {
      // Retrieve a page of categories.
      ListCatalogResponse listResponse = catalogApi.listCatalog(cursor, CATEGORY.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        return;
      }

      if (listResponse.getObjects().isEmpty() && cursor == null) {
        logger.info("No categories found.");
        return;
      }

      for (CatalogObject categoryObject : listResponse.getObjects()) {
        CatalogCategory category = categoryObject.getCategoryData();
        logger.info(category.getName() + " (" + categoryObject.getId() + ")");
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);
  }
}
