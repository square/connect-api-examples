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

import com.squareup.catalog.demo.api.CatalogApi;
import com.squareup.catalog.demo.api.LocationApi;
import com.squareup.catalog.demo.example.CreateItemExample;
import com.squareup.catalog.demo.example.DeleteCategoryExample;
import com.squareup.catalog.demo.example.Example;
import com.squareup.catalog.demo.example.LocationSpecificPriceExample;
import com.squareup.catalog.demo.example.SearchItemsExample;
import java.io.IOException;

public class Main {

  static final String API_BASE_URL = "https://connect.squareup.com/v2/";

  private static String USAGE = "USAGE:\n" +
      "  Execute Example: java <example_name> -token <accessToken>\n" +
      "  List Examples:   java -list-examples\n" +
      "  Print Usage:     java -usage";

  /**
   * Argument used to print usage information.
   */
  private static String ARG_USAGE = "-usage";

  /**
   * Argument used to print example in a readable format.
   */
  private static String ARG_LIST_EXAMPLES = "-list-examples";

  /**
   * Optional Argument used to set the base URL of the Catalog API.
   */
  private static String ARG_BASE_URL = "-base-url";

  /**
   * Argument used to set the access token. Required when executing an example.
   */
  private static String ARG_TOKEN = "-token";

  public static void main(String[] args) {
    Main main = new Main(new Logger.SystemLogger(),
        new CreateItemExample(),
        new DeleteCategoryExample(),
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
    String baseUrl = API_BASE_URL;
    for (int i = 1; i < args.length; i++) {
      String arg = args[i];
      if (ARG_BASE_URL.equalsIgnoreCase(arg)) {
        if (i == args.length - 1) {
          usage(ARG_BASE_URL + " specified without a url");
          return;
        }
        baseUrl = args[i + 1];
        if (!baseUrl.endsWith("/")) {
          baseUrl += "/";
        }
        i++;
      } else if (ARG_TOKEN.equalsIgnoreCase(arg)) {
        if (i == args.length - 1) {
          usage(ARG_TOKEN + " specified without a token");
          return;
        }
        accessToken = args[i + 1];
        i++;
      } else {
        usage("Unrecognized argument: " + arg);
        return;
      }
    }
    if (accessToken == null || accessToken.trim().isEmpty()) {
      usage("You must specify a valid access token");
      return;
    }
    CatalogApi catalogApi = new CatalogApi(baseUrl, accessToken, logger);
    LocationApi locationApi = new LocationApi(baseUrl, accessToken, logger);
    executeExample(command, catalogApi, locationApi);
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
   * @param catalogApi the CatalogApi utility
   */
  private void executeExample(String exampleName, CatalogApi catalogApi, LocationApi locationApi) {
    for (Example example : examples) {
      if (example.getName().equalsIgnoreCase(exampleName)) {
        try {
          example.execute(catalogApi, locationApi);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return;
      }
    }
    throw new IllegalArgumentException("Example " + exampleName + " not found");
  }
}
