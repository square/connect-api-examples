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

import static com.squareup.catalog.demo.util.Errors.checkAndLogErrors;
import static com.squareup.catalog.demo.util.Prompts.promptUserInput;

import com.squareup.square.exceptions.ApiException;
import java.util.Locale;

import com.squareup.catalog.demo.example.ApplyTaxToAllIItemsExample;
import com.squareup.catalog.demo.example.CreateItemExample;
import com.squareup.catalog.demo.example.DeduplicateTaxesExample;
import com.squareup.catalog.demo.example.DeleteAllItemsExample;
import com.squareup.catalog.demo.example.DeleteCategoryExample;
import com.squareup.catalog.demo.example.Example;
import com.squareup.catalog.demo.example.GloballyEnableAllItemsExample;
import com.squareup.catalog.demo.example.ListCategoriesExample;
import com.squareup.catalog.demo.example.ListDiscountsExample;
import com.squareup.catalog.demo.example.LocationSpecificPriceExample;
import com.squareup.catalog.demo.example.RetrieveCatalogObjectExample;
import com.squareup.catalog.demo.example.SearchItemsExample;
import com.squareup.catalog.demo.example.clone.CloneCatalogExample;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.SquareClient.Builder;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.Location;

public class Main {

  private static final String USAGE = "USAGE:\n"
      + "  Execute Example: java <example_name> [-token <accessToken>] [-cleanup] [-env <sandbox/production>]\n"
      + "  List Examples:   java -list-examples\n"
      + "  Print Usage:     java -usage";

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
   * Optional argument used to specify that the example should be cleaned up
   * instead of being
   * executed.
   */
  private static final String ARG_CLEANUP = "-cleanup";

  /**
   * Argument used to set the access token. Required when executing an example.
   */
  private static final String ARG_TOKEN = "-token";

  /**
   * Argument used to set environment. If set to sandbox, the APIs will hit the
   * sandbox environment.
   * If set to production, the APIs will hit the production environment.
   */
  private static final String ENV_FLAG = "-env";

  public static void main(String[] args) {
    Logger logger = new Logger.SystemLogger();
    Main main = new Main(logger,
        new CreateItemExample(logger),
        new DeleteAllItemsExample(logger),
        new ApplyTaxToAllIItemsExample(logger),
        new DeduplicateTaxesExample(logger),
        new DeleteCategoryExample(logger),
        new ListCategoriesExample(logger),
        new ListDiscountsExample(logger),
        new LocationSpecificPriceExample(logger),
        new SearchItemsExample(logger),
        new RetrieveCatalogObjectExample(logger),
        new GloballyEnableAllItemsExample(logger),
        new CloneCatalogExample(logger));
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
    // By default, set the value of the access token to the environment variable
    // SQUARE_ACCESS_TOKEN,
    // as mentioned in the README.
    String accessToken = System.getenv("SQUARE_ACCESS_TOKEN");
    boolean cleanup = false;
    String environment = null;
    String customUrl = null;

    for (int i = 1; i < args.length; i++) {
      String arg = args[i].toLowerCase(Locale.US);
      switch (arg) {
        case ARG_BASE_URL:
          if (i == args.length - 1) {
            usage(ARG_BASE_URL + " specified without a url");
            return;
          }
          customUrl = args[i + 1];
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
        case ENV_FLAG:
          if (i == args.length - 1) {
            usage(ENV_FLAG + " specified without an environment");
            return;
          }
          environment = args[i + 1];
          i++;
          break;
        default:
          usage("Unrecognized argument: " + arg);
          return;
      }
    }

    // Prompt for the access token if not specified as an arg.
    if (accessToken == null) {
      accessToken = promptUserInput("Enter access token: ");
    }

    // Show error if access token is blank.
    if (accessToken == null || accessToken.trim().isEmpty()) {
      logger.error("You must specify a valid access token");
      return;
    }

    Builder apiClientBuilder = new SquareClient.Builder();

    if (environment != null && !environment.equalsIgnoreCase("sandbox")
        && !environment.equalsIgnoreCase("production")) {
      // was set to something that we do not support.
      logger.error(
          "If you choose to use the -env flag, you must either specify \"sandbox\" or \"production\"");
      return;
    }

    // Decide on environment.
    // If both environment and base-url were set, the environment choice will
    // override.
    Environment env;
    if (environment == null) {
      // if environment was not set, check if base url was provided. If so, set
      // environment to custom. Otherwise, set environment to be sandbox by default.
      if (customUrl != null) {
        env = Environment.CUSTOM;
        apiClientBuilder.customUrl(customUrl);
      } else {
        env = Environment.SANDBOX;
      }
    } else {
      env = environment.equalsIgnoreCase("sandbox") ? Environment.SANDBOX : Environment.PRODUCTION;
    }

    // Build the client using the arguments provided
    SquareClient apiClient = apiClientBuilder
        .environment(env)
        .accessToken(accessToken)
        .userAgentDetail("sample_app_java_catalog") // Remove or replace this detail when building your own app
        .build();

    CatalogApi catalogApi = apiClient.getCatalogApi();
    LocationsApi locationsApi = apiClient.getLocationsApi();

    setCurrencyAcrossApplication(locationsApi);

    executeExample(command, cleanup, catalogApi, locationsApi);

    // Necessary in order for the program not to hang (kill background threads).
    System.exit(0);
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
   * @param cleanup     if true, cleanup the example instead of executing it
   * @param catalogApi  the CatalogApi utility
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
        } catch (Exception e) {
          // This is bad practice. In a real app, you'd want to handle this exception and
          // take action according to what went wrong.
          logger.error(e.getMessage());
          System.exit(1);
        }
        return;
      }
    }
    throw new IllegalArgumentException("Example " + exampleName + " not found");
  }

  /**
   * Finds out the currency to be used across all examples. The listLocation api
   * call will block, as
   * we need the currency information in order to proceed with processing the
   * examples. NOTE: This
   * step can be avoided if you know what currency you will be using. In that
   * case, you can simply
   * comment out the below function, and call 'Moneys.setCurrency(<YOUR APP
   * CURRENCY>);' with values
   * such as CAD, USD, EUR, GBP, etc.
   *
   * @param locationsApi the LocationsApi utility
   */
  private void setCurrencyAcrossApplication(LocationsApi locationsApi) {
    locationsApi.retrieveLocationAsync("main").thenAccept(result -> {
      // grab the first location for the user, and use that to determine currency.
      if (result.getLocation() != null) {
        Location currentLocation = result.getLocation();
        Moneys.setCurrency(currentLocation.getCurrency());
      }
    }).exceptionally(exception -> {
      // Log exception, exit application as this step is necessary.
      logger.error("Currency could not be retrieved. "
          + "Please verify that your access token is correct.");
      // Extract the actual exception
      ApiException e = (ApiException) exception.getCause();
      checkAndLogErrors(e.getErrors(), logger);
      System.exit(1);
      return null;
    }).join();
  }
}
