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
import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogObjectBatch;
import com.squareup.connect.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * This example clones catalog objects from one merchant account to another. The accessToken
 * specified when the script is executed is assumed to be the source account from which catalog
 * objects are copied.
 *
 * NOTE: This example is in development. Currently it only clones discounts, modifier lists, and
 * taxes. Items are not yet cloned.
 */
public class CloneCatalogExample extends Example {

  public CloneCatalogExample(Logger logger) {
    super("clone_catalog", "Clones catalog objects from one merchant account to another.", logger);
  }

  @Override
  public void execute(CatalogApi sourceCatalogApi, LocationsApi locationsApi) throws ApiException {
    // Prompt the user for configuration info.
    Config config = promptForConfigurationOptions();

    // Create a CatalogApi to access the target account.
    CatalogApi targetCatalogApi =
        createTargetCatalogApi(sourceCatalogApi, config.targetAccessToken);

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

    if (promptUserInputYesNo("Clone all discounts (y/n)? ")) {
      boolean presentAtAllLocationsByDefault =
          promptUserInputYesNo("  Make cloned discounts available at all locations (y/n)? ");
      config.discountCloneUtil = new DiscountCloneUtil(presentAtAllLocationsByDefault);
    }

    if (promptUserInputYesNo("Clone all modifier lists (y/n)? ")) {
      logger.info("  Note: Modifier lists will be enabled at all locations");
      config.modifierListCloneUtil = new ModifierListCloneUtil();
    }

    if (promptUserInputYesNo("Clone all taxes (y/n)? ")) {
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
    // Create a new instance of the ApiClient.
    ApiClient targetApiClient = new ApiClient();
    targetApiClient.setBasePath(sourceCatalogApi.getApiClient().getBasePath());

    // Set the auth token of the ApiClient to the target account.
    OAuth oauth2 = (OAuth) targetApiClient.getAuthentication("oauth2");
    oauth2.setAccessToken(targetAccessToken);

    // Return a new CatalogApi instance that uses the new ApiClient.
    return new CatalogApi(targetApiClient);
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
      CatalogObjectCloneUtil<?> cloneUtil)
      throws ApiException, CloneCatalogException {
    logger.info("\nCloning " + cloneUtil.type.toString());

    // Retrieve identifiers from the target account.
    HashMap<String, CatalogObject> targetCatalogObjects =
        retrieveTargetCatalogObjects(targetCatalogApi, cloneUtil);

    // Retrieve catalog objects from the source account.
    logger.info("  Retrieving " + cloneUtil.type.toString() + " from source account");
    String cursor = null;
    do {
      List<CatalogObject> catalogObjectsToUpsert = new ArrayList<>();

      // Retrieve a page of catalog objects from the source account.
      ListCatalogResponse listResponse =
          sourceCatalogApi.listCatalog(cursor, cloneUtil.type.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        throw new CloneCatalogException();
      }

      // Log and return if no objects are found.
      if (listResponse.getObjects().isEmpty() && cursor == null) {
        logger.info(
            "No " + cloneUtil.type.toString().toLowerCase(Locale.US) + " found in source account.");
        return;
      }

      for (CatalogObject sourceCatalogObject : listResponse.getObjects()) {
        String encodedString = cloneUtil.encodeCatalogObject(sourceCatalogObject);

        // Check if a similar catalog object already exists in the target account.
        CatalogObject targetCatalogObject = targetCatalogObjects.get(encodedString);
        if (targetCatalogObject == null) {
          // Clone this CatalogObject into the target account.
          cloneUtil.removeSourceAccountMetaData(sourceCatalogObject);
          catalogObjectsToUpsert.add(sourceCatalogObject);
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
      logger.info("    Cloned " + catalogObjectsToUpsert.size() +
          " out of " + listResponse.getObjects().size());

      // Move to the next page.
      cursor = listResponse.getCursor();
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
  private HashMap<String, CatalogObject> retrieveTargetCatalogObjects(CatalogApi
      targetCatalogApi,
      CatalogObjectCloneUtil<?> cloneUtil) throws ApiException, CloneCatalogException {
    logger.info("  Retrieving " + cloneUtil.type.toString() + " from target account");
    HashMap<String, CatalogObject> identifierToCatalogObject = new HashMap<>();
    int count = 0;
    String cursor = null;
    do {
      // Retrieve a page of catalog objects.
      ListCatalogResponse listResponse =
          targetCatalogApi.listCatalog(cursor, cloneUtil.type.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        throw new CloneCatalogException();
      }

      // Generate an encoded string for each object and add it to the hash map.
      for (CatalogObject catalogObject : listResponse.getObjects()) {
        String encodedString = cloneUtil.encodeCatalogObject(catalogObject);
        identifierToCatalogObject.put(encodedString, catalogObject);
      }

      // Move to the next page.
      count += listResponse.getObjects().size();
      logger.info("    Retrieved " + count + " total");
      cursor = listResponse.getCursor();
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
      List<CatalogObject> catalogObjectsToUpsert) throws ApiException, CloneCatalogException {
    BatchUpsertCatalogObjectsRequest batchUpsertRequest = new BatchUpsertCatalogObjectsRequest()
        .idempotencyKey(UUID.randomUUID().toString())
        .addBatchesItem(new CatalogObjectBatch()
            .objects(catalogObjectsToUpsert)
        );
    BatchUpsertCatalogObjectsResponse batchUpsertResponse =
        targetCatalogApi.batchUpsertCatalogObjects(batchUpsertRequest);
    if (checkAndLogErrors(batchUpsertResponse.getErrors())) {
      throw new CloneCatalogException();
    }
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
