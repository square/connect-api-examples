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
import com.squareup.catalog.service.ListLocationsResponse;
import java.io.IOException;

/**
 * Utility class used to retrieve location information.
 */
public class LocationApi extends BaseApi {

  public LocationApi(String baseUrl, String accessToken, Logger logger) {
    super(baseUrl + "locations", accessToken, logger);
  }

  /**
   * Deletes multiple {@link CatalogObject CatalogObjects}.
   */
  public ListLocationsResponse listLocations()
      throws IOException {
    return get("", ListLocationsResponse.class);
  }
}
