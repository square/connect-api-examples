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
import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.Error;
import java.util.List;

/**
 * Base class used to define Examples.
 */
public abstract class Example {

  private final String name;
  private final String description;
  protected final Logger logger = new Logger.SystemLogger();

  public Example(String name, String description) {
    this.name = name;
    this.description = description;
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
      throws ApiException;

  /**
   * Logs errors received from the Catalog API.
   *
   * @param errors the list of errors returned in the API response
   * @return true if errors were logged, false if no errors
   */
  protected boolean checkAndLogErrors(List<Error> errors) {
    return Errors.checkAndLogErrors(errors, logger);
  }
}
