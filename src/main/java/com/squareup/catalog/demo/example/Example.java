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
import com.squareup.catalog.demo.util.Errors;
// import com.squareup.connect.ApiException;
// import com.squareup.connect.api.CatalogApi;
// import com.squareup.connect.api.LocationsApi;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.SearchCatalogObjectsRequest;
import com.squareup.square.models.SearchCatalogObjectsResponse;
// import com.squareup.connect.models.BatchDeleteCatalogObjectsRequest;
// import com.squareup.connect.models.CatalogObject;
// import com.squareup.connect.models.CatalogQuery;
// import com.squareup.connect.models.CatalogQueryExact;
// import com.squareup.connect.models.CreateCustomerRequest;
// import com.squareup.connect.models.Error;
// import com.squareup.connect.models.SearchCatalogObjectsRequest;
import com.squareup.connect.models.SearchCatalogObjectsRequest.ObjectTypesEnum;
// import com.squareup.connect.models.CatalogQuery;
// import com.squareup.connect.models.CatalogQueryExact;
import java.util.List;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.square.models.Error;
import com.squareup.square.models.CatalogQuery;
import com.squareup.square.models.CatalogQueryExact;
import com.squareup.square.exceptions.ApiException;

import static java.util.Collections.singletonList;

import java.io.IOException;
import java.util.ArrayList;

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
   * @param catalogApi the API abstraction used to interact with the Catalog API
   * @param locationsApi the API abstraction used to interact with merchant locations
   */
  public abstract void execute(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException, IOException;

  /**
   * Cleans up {@link CatalogObject}s created by this example.
   *
   * Note that this is a destructive operation and may delete items by name, which may include items
   * that were not created by this example.
   *
   * @param catalogApi the API abstraction used to interact with the Catalog API
   * @param locationsApi the API abstraction used to interact with merchant locations
   */
  public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException, IOException {
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
   * @param type the type of objects to delete
   * @param name the name of objects to delete
   */
  protected void cleanCatalogObjectsByName(CatalogApi catalogApi, ObjectTypesEnum type, String name)
      throws ApiException, IOException {
    // Search for objects by name and type.
    logger.info("Search for " + type + " named " + name);

    //TODO: hardcoded "ITEM" NEEDS TO BE GONE, input should come as string to this function
    SearchCatalogObjectsRequest searchRequest = new SearchCatalogObjectsRequest.Builder()
        .objectTypes(singletonList("ITEM"))
        .query(new CatalogQuery.Builder()
            // An Exact query searches for exact matches of the specified attribute.
            .exactQuery(new CatalogQueryExact("name", name))
            .build()
        ).build();

        // .objectTypes(singletonList(type))
        // .query(new CatalogQuery()
        //     // An Exact query searches for exact matches of the specified attribute.
        //     .exactQuery(new CatalogQueryExact()
        //         .attributeName("name")
        //         .attributeValue(name)
        //     )
        // );


        SearchCatalogObjectsResponse searchResponse = catalogApi.searchCatalogObjects(searchRequest);
        if (checkAndLogErrors(searchResponse.getErrors())) {
            return;
        }
        List<CatalogObject> found = searchResponse.getObjects();
        if (found.size() == 0) {
            logger.info("No " + type + " found");
        } else {
            // Delete the objects.
            logger.info("Deleting " + found.size() + " " + type);
            List<String> ids = new ArrayList<>();
            for(CatalogObject catalogObject : found) {
                ids.add(catalogObject.getId());
            }
            BatchDeleteCatalogObjectsRequest deleteRequest = new BatchDeleteCatalogObjectsRequest(ids);
            catalogApi.batchDeleteCatalogObjects(deleteRequest);
        }
    // SearchCatalogObjectsResponse searchResponse = catalogApi.searchCatalogObjects(searchRequest);
    // catalogApi.searchCatalogObjectsAsync(searchRequest)
    // if (checkAndLogErrors(searchResponse.getErrors())) {
    //   return;
    // }

    // List<CatalogObject> found = searchResponse.getObjects();
    // if (found.size() == 0) {
    //   logger.info("No " + type + " found");
    // } else {
    //   // Delete the objects.
    //   logger.info("Deleting " + found.size() + " " + type);
    //   BatchDeleteCatalogObjectsRequest deleteRequest = new BatchDeleteCatalogObjectsRequest();
    //   for (CatalogObject catalogObject : searchResponse.getObjects()) {
    //     deleteRequest.addObjectIdsItem(catalogObject.getId());
    //   }
    //   catalogApi.batchDeleteCatalogObjects(deleteRequest);
    // }
  }
}
