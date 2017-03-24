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
package com.squareup.catalog.demo.api;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.resources.CatalogObject;
import com.squareup.catalog.service.BatchDeleteCatalogObjectsRequest;
import com.squareup.catalog.service.BatchDeleteCatalogObjectsResponse;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsRequest;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsResponse;
import com.squareup.catalog.service.RetrieveCatalogObjectResponse;
import com.squareup.catalog.service.SearchCatalogObjectsRequest;
import com.squareup.catalog.service.SearchCatalogObjectsResponse;
import java.io.IOException;

/**
 * Utility class to send messages to and receive messages from the Catalog API.
 */
public class CatalogApi extends BaseApi {

  public CatalogApi(String baseUrl, String accessToken, Logger logger) {
    super(baseUrl + "catalog/", accessToken, logger);
  }

  /**
   * Deletes multiple {@link CatalogObject CatalogObjects}.
   */
  public BatchDeleteCatalogObjectsResponse batchDelete(BatchDeleteCatalogObjectsRequest request)
      throws IOException {
    return post("batch-delete", request, BatchDeleteCatalogObjectsResponse.class);
  }

  /**
   * Creates or updates multiple {@link CatalogObject CatalogObjects}.
   */
  public BatchUpsertCatalogObjectsResponse batchUpsert(BatchUpsertCatalogObjectsRequest request)
      throws IOException {
    return post("batch-upsert", request, BatchUpsertCatalogObjectsResponse.class);
  }

  /**
   * Retrieves a single {@link CatalogObject}.
   */
  public RetrieveCatalogObjectResponse retrieveObject(String objectId)
      throws IOException {
    return get("object/" + objectId, RetrieveCatalogObjectResponse.class);
  }

  /**
   * Searches for {@link CatalogObject CatalogObjects}.
   */
  public SearchCatalogObjectsResponse search(SearchCatalogObjectsRequest request)
      throws IOException {
    return post("search", request, SearchCatalogObjectsResponse.class);
  }
}
