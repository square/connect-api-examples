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
package com.squareup.catalog.demo.example.clone;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.example.Example;
import com.squareup.square.exceptions.ApiException;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogObjectBatch;
import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.squareup.catalog.demo.util.Prompts.promptUserInput;
import static com.squareup.catalog.demo.util.Prompts.promptUserInputYesNo;
import static java.util.Collections.singletonList;


/**
 * This example clones catalog objects from one merchant account to another. The accessToken
 * specified when the script is executed is assumed to be the source account from which catalog
 * objects are copied.
 *
 * NOTE: This example is in development. Currently it only clones discounts, modifier lists, and
 * taxes. Items are not yet cloned.
 */
public class CloneCatalogExample extends Example {

    private String cursor = null;

    private int count = 0;

  public CloneCatalogExample(Logger logger) {
    super("clone_catalog", "Clones catalog objects from one merchant account to another.", logger);
  }

  @Override
  public void execute(CatalogApi sourceCatalogApi, LocationsApi locationsApi) throws ApiException {
    // Prompt the user for configuration info.
    Config config = promptForConfigurationOptions();

    // Create a CatalogApi to access the target account.
    CatalogApi targetCatalogApi = createTargetCatalogApi(sourceCatalogApi, config.targetAccessToken);

    try {
       /*
        * Clone discounts from source to target account.
        *
        * If a discount in the source account has the same name, discount type, percentage, and amount
        * as a discount in the target account, then the discount in the source account is not cloned.
        */
      if (config.discountCloneUtil != null) {
        cloneCatalogObjectType(sourceCatalogApi, targetCatalogApi, config.discountCloneUtil);
      }

      /*
       * Clone modifier lists from source to target account.
       *
       * If a modifier list in the source account has the same name and selection type as a modifier
       * list in the target account, then the modifier list in the source account is not cloned. In
       * that case, the modifiers from the modifier list in the source account will be added to the
       * modifier list in the target account if they do not match any of the existing modifiers.
       */
      if (config.modifierListCloneUtil != null) {
        cloneCatalogObjectType(sourceCatalogApi, targetCatalogApi, config.modifierListCloneUtil);
      }

      /*
       * Clone taxes from source to target account.
       *
       * If a tax in the source account has the same name, percentage, and inclusion type as a tax in
       * the target account, then the tax in the source account is not cloned.
       */
      if (config.taxCloneUtil != null) {
        cloneCatalogObjectType(sourceCatalogApi, targetCatalogApi, config.taxCloneUtil);
      }
    } catch (CloneCatalogException e) {
      if (e.getMessage() != null) {
        logger.error(e.getMessage());
      }
    }
  }

  /**
   * Prompt the user for configuration options required to clone the account.
   *
   * @return the configuration to apply when cloning accounts
   */
  private Config promptForConfigurationOptions() {
    Config config = new Config();
    config.targetAccessToken = promptUserInput("Enter access token of target account: ").trim();

    if (promptUserInputYesNo("Clone all discounts (y/n)? ", logger)) {
      boolean presentAtAllLocationsByDefault = promptUserInputYesNo("  Make cloned discounts available at all locations (y/n)? ",logger);
      config.discountCloneUtil = new DiscountCloneUtil(presentAtAllLocationsByDefault);
    }

    if (promptUserInputYesNo("Clone all modifier lists (y/n)? ", logger)) {
      logger.info("  Note: Modifier lists will be enabled at all locations");
      config.modifierListCloneUtil = new ModifierListCloneUtil();
    }

    if (promptUserInputYesNo("Clone all taxes (y/n)? ", logger)) {
      config.taxCloneUtil = new TaxCloneUtil();
    }

    return config;
  }

  /**
   * Create a CatalogApi instance to access the target account.
   *
   * @param sourceCatalogApi the {@link CatalogApi} instance used to access the source account
   * @param targetAccessToken the access token of the target account
   * @return a {@link CatalogApi} for the target account
   */
  private CatalogApi createTargetCatalogApi(CatalogApi sourceCatalogApi, String targetAccessToken) {
    SquareClient targetClient = new SquareClient.Builder()
    .environment(Environment.SANDBOX)
    .accessToken(targetAccessToken)
    .build();

    return targetClient.getCatalogApi();
  }

  /**
   * Clones all catalog objects of a single type.
   *
   * The basic approach for cloning catalog objects is the same for all types other than items.
   * 1) Fetch all the catalog objects of the specified type from the target account
   * 2) For each object in the target account, generate a unique identifier based on certain field
   * values. For example, for taxes we compare name, percentage, and inclusion type.
   * 3) Store all unqiue identifiers in a HashSet
   * 4) Fetch all the catalog objects of the specified type from the target account
   * 5) For each object in the source account, generate a unique identifier using the same method
   * and check to see if it exists in the HashSet. If it does not, then add it to the target
   * account.
   *
   * @param sourceCatalogApi the API used to access the catalog of the source account
   * @param targetCatalogApi the API used to access the catalog of the target account
   * @param cloneUtil the clone utility methods for the specified catalog object type
   */
  private void cloneCatalogObjectType(CatalogApi sourceCatalogApi, CatalogApi targetCatalogApi,
      CatalogObjectCloneUtil<?> cloneUtil) throws ApiException {
    logger.info("\nCloning " + cloneUtil.type.toString());

    // Retrieve identifiers from the target account.
    HashMap<String, CatalogObject> targetCatalogObjects = retrieveTargetCatalogObjects(targetCatalogApi, cloneUtil);

    // Retrieve catalog objects from the source account.
    logger.info("  Retrieving " + cloneUtil.type.toString() + " from source account");
    cursor = null;
    Long catalogVersion = null;

    do {
        List<CatalogObject> catalogObjectsToUpsert = new ArrayList<>();

        // Retrieve a page of catalog objects from the source account.
        sourceCatalogApi.listCatalogAsync(cursor, cloneUtil.type.toString(), catalogVersion).thenAccept(result -> {
            if (checkAndLogErrors(result.getErrors())) {
                return;
            }

            // Log and return if no objects are found.
            if (result.getObjects() == null && cursor == null) {
                logger.info(
                    "No " + cloneUtil.type.toString().toLowerCase(Locale.US) + " found in source account.");
                return;
            }

            for (CatalogObject sourceCatalogObject : result.getObjects()) {
                String encodedString = cloneUtil.encodeCatalogObject(sourceCatalogObject);

                // Check if a similar catalog object already exists in the target account.
                CatalogObject targetCatalogObject = targetCatalogObjects.get(encodedString);
                if (targetCatalogObject == null) {
                  // Clone this CatalogObject into the target account.
                  CatalogObject cleanCatalogObject = cloneUtil.removeSourceAccountMetaData(sourceCatalogObject);
                  catalogObjectsToUpsert.add(cleanCatalogObject);
                } else {
                  // If a similar CatalogObject already exists in the target account, attempt to merge the
                  // source CatalogObject into the existing target CatalogObject.
                  CatalogObject modifiedTargetCatalogObject =
                      cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceCatalogObject,
                          targetCatalogObject);

                  // If something changed in the target catalog object, upsert it into the target account.
                  if (modifiedTargetCatalogObject != null) {
                    catalogObjectsToUpsert.add(modifiedTargetCatalogObject);
                  }
                }
            }

            // Upsert the catalog objects into the target account.
            if (catalogObjectsToUpsert.size() > 0) {
                upsertCatalogObjectsIntoTargetAccount(targetCatalogApi, catalogObjectsToUpsert);
            }

            // Log the number of objects cloned.
            logger.info("    Cloned " + catalogObjectsToUpsert.size() + " out of " + result.getObjects().size());

             // Move to the next page.
            cursor = result.getCursor();
        }).exceptionally(exception -> {
            // Rethrow.
            throw new CloneCatalogException(exception);
        }).join();
    } while (cursor != null);
  }

  /**
   * Retrieves all of the CatalogObjects of the specified type from the target account and return
   * them in a HashSet keyed by the encoded string.
   *
   * @param targetCatalogApi the API used to access the catalog of the target account
   * @param cloneUtil the clone utility methods for the specified catalog object type
   * @return a map of the encoded string to the {@link CatalogObject} in the target account
   */
  private HashMap<String, CatalogObject> retrieveTargetCatalogObjects(CatalogApi targetCatalogApi,CatalogObjectCloneUtil<?> cloneUtil) throws ApiException {
    logger.info("  Retrieving " + cloneUtil.type.toString() + " from target account");
    HashMap<String, CatalogObject> identifierToCatalogObject = new HashMap<>();
    Long catalogVersion = null;


    do {
        targetCatalogApi.listCatalogAsync(cursor, cloneUtil.type.toString(), catalogVersion).thenAccept(result -> {
            if (checkAndLogErrors(result.getErrors())) {
                return;
            }

            List<CatalogObject> items = result.getObjects();
            if (items == null || items.size() == 0) {
                if (cursor == null) {
                    logger.info("No items found. Item Library was already empty.");
                    return;
                }
            } else {
                // Generate an encoded string for each object and add it to the hash map.
                for (CatalogObject catalogObject : items) {
                    String encodedString = cloneUtil.encodeCatalogObject(catalogObject);
                    identifierToCatalogObject.put(encodedString, catalogObject);
                }

                // Move to the next page.
                count += result.getObjects().size();
                logger.info("    Retrieved " + count + " total");
            }
            cursor = result.getCursor();
        }).exceptionally(exception -> {
            // Rethrow.
            throw new CloneCatalogException(exception);
        }).join();
    } while (cursor != null);

    return identifierToCatalogObject;
  }

  /**
   * Inserts or updates catalog objects into the target account.
   *
   * @param targetCatalogApi the API used to access the catalog of the target account
   * @param catalogObjectsToUpsert the CatalogObjects to insert or update
   */

  private void upsertCatalogObjectsIntoTargetAccount(CatalogApi targetCatalogApi,
      List<CatalogObject> catalogObjectsToUpsert) {
    BatchUpsertCatalogObjectsRequest batchUpsertRequest = new BatchUpsertCatalogObjectsRequest.Builder(
        UUID.randomUUID().toString(),
        singletonList(new CatalogObjectBatch(catalogObjectsToUpsert)))
        .build();


    targetCatalogApi.batchUpsertCatalogObjectsAsync(batchUpsertRequest).thenAccept(result -> {
        if (checkAndLogErrors(result.getErrors())) {
            return;
        }
    }).exceptionally(exception -> {
         // Rethrow.
         throw new CloneCatalogException(exception);
    });
  }

  /**
   * User specified configuration options.
   */
  private static class Config {
    private String targetAccessToken;
    private DiscountCloneUtil discountCloneUtil;
    private ModifierListCloneUtil modifierListCloneUtil;
    private TaxCloneUtil taxCloneUtil;
  }
}
