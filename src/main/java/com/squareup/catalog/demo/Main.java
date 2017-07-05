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
package com.squareup.catalog.demo;

import com.google.gson.JsonSyntaxException;
import com.squareup.catalog.demo.example.ApplyTaxToAllIItemsExample;
import com.squareup.catalog.demo.example.ListCategoriesExample;
import com.squareup.catalog.demo.example.clone.CloneCatalogExample;
import com.squareup.catalog.demo.example.CreateItemExample;
import com.squareup.catalog.demo.example.DeduplicateTaxesExample;
import com.squareup.catalog.demo.example.DeleteAllItemsExample;
import com.squareup.catalog.demo.example.DeleteCategoryExample;
import com.squareup.catalog.demo.example.Example;
import com.squareup.catalog.demo.example.ListDiscountsExample;
import com.squareup.catalog.demo.example.LocationSpecificPriceExample;
import com.squareup.catalog.demo.example.SearchItemsExample;
import com.squareup.catalog.demo.util.GsonProvider;
import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.Error;
import java.util.List;
import java.util.Locale;

import static com.squareup.catalog.demo.util.Errors.checkAndLogErrors;

public class Main {

  private static final String USAGE = "USAGE:\n" +
      "  Execute Example: java <example_name> -token <accessToken> [-cleanup]\n" +
      "  List Examples:   java -list-examples\n" +
      "  Print Usage:     java -usage";

  /**
   * Argument used to print usage information.
   */
  private static final String ARG_USAGE = "-usage";

  /**
   * Argument used to print example in a readable format.
   */
  private static final String ARG_LIST_EXAMPLES = "-list-examples";

  /**
   * Optional Argument used to set the base URL of the Catalog API.
   */
  private static final String ARG_BASE_URL = "-base-url";

  /**
   * Optional argument used to specify that the example should be cleaned up instead of being
   * executed.
   */
  private static final String ARG_CLEANUP = "-cleanup";

  /**
   * Argument used to set the access token. Required when executing an example.
   */
  private static final String ARG_TOKEN = "-token";

  public static void main(String[] args) {
    Main main = new Main(new Logger.SystemLogger(),
        new ApplyTaxToAllIItemsExample(),
        new CloneCatalogExample(),
        new CreateItemExample(),
        new DeduplicateTaxesExample(),
        new DeleteAllItemsExample(),
        new DeleteCategoryExample(),
        new ListCategoriesExample(),
        new ListDiscountsExample(),
        new LocationSpecificPriceExample(),
        new SearchItemsExample());
    main.processArgs(args);
  }

  private final Logger logger;
  private final Example[] examples;

  Main(Logger logger, Example... examples) {
    this.logger = logger;
    this.examples = examples;
  }

  /**
   * Processes command line arguments.
   */
  void processArgs(String[] args) {
    if (args == null || args.length == 0) {
      logger.info(USAGE);
      return;
    }

    // Check for special commands.
    String command = args[0];
    if (ARG_USAGE.equalsIgnoreCase(command)) {
      logger.info(USAGE);
      return;
    } else if (ARG_LIST_EXAMPLES.equalsIgnoreCase(command)) {
      listExamples();
      return;
    }

    // Process arguments associated with the example.
    String accessToken = null;
    boolean cleanup = false;
    ApiClient apiClient = Configuration.getDefaultApiClient();
    for (int i = 1; i < args.length; i++) {
      String arg = args[i].toLowerCase(Locale.US);
      switch (arg) {
        case ARG_BASE_URL:
          if (i == args.length - 1) {
            usage(ARG_BASE_URL + " specified without a url");
            return;
          }
          apiClient = new ApiClient().setBasePath(args[i + 1]);
          i++;
          break;
        case ARG_CLEANUP:
          cleanup = true;
          break;
        case ARG_TOKEN:
          if (i == args.length - 1) {
            usage(ARG_TOKEN + " specified without a token");
            return;
          }
          accessToken = args[i + 1];
          i++;
          break;
        default:
          usage("Unrecognized argument: " + arg);
          return;
      }
    }
    if (accessToken == null || accessToken.trim().isEmpty()) {
      usage("You must specify a valid access token");
      return;
    }

    // Configure OAuth2 access token for authorization: oauth2
    OAuth oauth2 = (OAuth) apiClient.getAuthentication("oauth2");
    oauth2.setAccessToken(accessToken);

    CatalogApi catalogApi = new CatalogApi(apiClient);
    LocationsApi locationsApi = new LocationsApi(apiClient);
    executeExample(command, cleanup, catalogApi, locationsApi);
  }

  /**
   * Logs usage information.
   *
   * @param errorMessage the error message indicating invalid usage.
   */
  private void usage(String errorMessage) {
    logger.error(errorMessage);
    logger.info(USAGE);
  }

  /**
   * Logs available examples in a readable format.
   */
  private void listExamples() {
    // Find the character length of the longest example name.
    int longestName = 0;
    for (Example example : examples) {
      longestName = Math.max(longestName, example.getName().length());
    }

    // Print the examples.
    StringBuilder sb = new StringBuilder("Examples:\n");
    for (Example example : examples) {
      sb.append("  ").append(example.getName());
      // Pad the name.
      for (int i = example.getName().length(); i < longestName + 4; i++) {
        sb.append(" ");
      }
      sb.append(example.getDescription()).append("\n");
    }
    logger.info(sb.toString());
  }

  /**
   * Executes a single example.
   *
   * @param exampleName the name of the example to execute
   * @param cleanup if true, cleanup the example instead of executing it
   * @param catalogApi the CatalogApi utility
   */
  private void executeExample(String exampleName, boolean cleanup, CatalogApi catalogApi,
      LocationsApi locationsApi) {
    for (Example example : examples) {
      if (example.getName().equalsIgnoreCase(exampleName)) {
        try {
          if (cleanup) {
            example.cleanup(catalogApi, locationsApi);
          } else {
            example.execute(catalogApi, locationsApi);
          }
        } catch (ApiException e) {
          handleApiException(e);
        }
        return;
      }
    }
    throw new IllegalArgumentException("Example " + exampleName + " not found");
  }

  /**
   * Attempts to log {@link Error}s from an {@link ApiException}, or rethrows if the response body
   * cannot be parsed.
   */
  private void handleApiException(ApiException apiException) {
    try {
      // If the response includes a body, it means that he server returned some error message.
      ErrorResponse response =
          GsonProvider.gson().fromJson(apiException.getResponseBody(), ErrorResponse.class);

      // If we found errors in the response body, log them. Otherwise, rethrow.
      if (!checkAndLogErrors(response.errors, logger)) {
        throw new RuntimeException(apiException);
      }
    } catch (JsonSyntaxException e) {
      // If the error message isn't in JSON format, rethrow the error.
      throw new RuntimeException(apiException);
    }
  }

  /**
   * Represents the response body of an {@link ApiException} that contains server specified errors.
   */
  private static class ErrorResponse {
    List<Error> errors;
  }
}
