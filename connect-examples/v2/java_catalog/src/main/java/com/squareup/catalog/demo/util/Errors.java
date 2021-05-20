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
package com.squareup.catalog.demo.util;

import com.squareup.catalog.demo.Logger;
import com.squareup.square.models.Error;
import java.util.List;

/**
 * Utility to log errors.
 */
public class Errors {

  /**
   * Logs errors received from the Catalog API.
   *
   * @param errors the list of errors returned in the API response
   * @return true if errors were logged, false if no errors
   */
  public static boolean checkAndLogErrors(List<Error> errors, Logger logger) {
    if (errors == null || errors.isEmpty()) {
      return false;
    }

    for (Error error : errors) {
      // Convert the category and code to a string.
      String categoryAndCode = "";
      if (error.getCategory() != null) {
        categoryAndCode += error.getCategory().toString();
      }
      if (error.getCode() != null) {
        if (categoryAndCode.length() > 0) {
          categoryAndCode += ":";
        }
        categoryAndCode += error.getCode().toString();
      }
      if (categoryAndCode.length() > 0) {
        categoryAndCode = "[" + categoryAndCode + "] ";
      }

      // Log the error.
      logger.error(categoryAndCode + error.getDetail());
    }

    return true;
  }

  private Errors() {
  }
}
