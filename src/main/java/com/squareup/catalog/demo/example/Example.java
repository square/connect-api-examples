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
import java.util.ArrayList;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.Errors;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogQuery;
import com.squareup.square.models.CatalogQueryExact;
import com.squareup.square.models.Error;
import com.squareup.square.models.SearchCatalogObjectsRequest;

/**
 * Base class used to define Examples.
 */
public abstract class Example {

  private final String name;
  private final String description;
  protected final Logger logger;

  public Example(String name, String description, Logger logger) {
    this.name = name;
    this.description = description;
    this.logger = logger;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  /**
   * Executes the example.
   *
   * @param catalogApi   the API abstraction used to interact with the Catalog API
   * @param locationsApi the API abstraction used to interact with merchant locations
   */
  public abstract void execute(CatalogApi catalogApi, LocationsApi locationsApi);

  /**
   * Cleans up {@link CatalogObject}s created by this example.
   * <p>
   * Note that this is a destructive operation and may delete items by name, which may include items
   * that were not created by this example.
   *
   * @param catalogApi   the API abstraction used to interact with the Catalog API
   * @param locationsApi the API abstraction used to interact with merchant locations
   */
  public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi) {
    logger.info("Nothing to cleanup");
  }

  /**
   * Logs errors received from the Catalog API.
   *
   * @param errors the list of errors returned in the API response
   * @return true if errors were logged, false if no errors
   */
  protected boolean checkAndLogErrors(List<Error> errors) {
    return Errors.checkAndLogErrors(errors, logger);
  }

  /**
   * Deletes objects of the specified type and name.
   *
   * @param catalogApi the API abstraction used to interact with the Catalog API
   * @param type       the type of objects to delete
   * @param name       the name of objects to delete
   */
  protected void cleanCatalogObjectsByName(CatalogApi catalogApi, String type, String name) {
    // Search for objects by name and type.
    logger.info("Search for " + type + " named " + name);

    CatalogQuery query = new CatalogQuery.Builder()
        // An Exact query searches for exact matches of the specified attribute.
        .exactQuery(new CatalogQueryExact("name", name))
        .build();

    SearchCatalogObjectsRequest searchRequest = new SearchCatalogObjectsRequest.Builder()
        .objectTypes(singletonList(type)).query(query)
        .build();

    catalogApi.searchCatalogObjectsAsync(searchRequest).thenAccept(result -> {
      if (result.getObjects() == null || result.getObjects().size() == 0) {
        logger.info("No " + type + " found");
      } else {
        List<CatalogObject> found = result.getObjects();
        // Delete the objects.
        logger.info("Deleting " + found.size() + " " + type);
        List<String> ids = new ArrayList<>();
        for (CatalogObject catalogObject : found) {
          ids.add(catalogObject.getId());
        }
        BatchDeleteCatalogObjectsRequest deleteRequest = new BatchDeleteCatalogObjectsRequest(ids);
        catalogApi.batchDeleteCatalogObjectsAsync(deleteRequest);
      }
    }).exceptionally(exception -> {
      // Extract the actual exception
      ApiException e = (ApiException) exception.getCause();
      checkAndLogErrors(e.getErrors());
      return null;
    }).join();
  }
}
