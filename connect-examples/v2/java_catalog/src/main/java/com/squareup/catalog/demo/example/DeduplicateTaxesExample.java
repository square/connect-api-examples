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

import static java.util.Collections.singletonList;

import com.squareup.square.exceptions.ApiException;
import com.squareup.square.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.square.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.square.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.square.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogObjectBatch;
import com.squareup.square.models.CatalogTax;
import java.util.concurrent.CompletionException;

/**
 * This example merges identical taxes (same name, percentage, and inclusion type).
 */
public class DeduplicateTaxesExample extends Example {

  public DeduplicateTaxesExample(Logger logger) {
    super("deduplicate_taxes", "Merges identical taxes (same name, percentage, and inclusion type).",
        logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {

    String cursor = null;
    // A map that allows us to look up a tax based on his name, percentage, and inclusion type.
    Map<String, DuplicateTaxInfo> taxInfoMap = new HashMap<>();
    // The list of taxes that we need to update.
    List<CatalogObject> taxesToUpdate = new ArrayList<>();
    // The IDs of duplicate taxes that will be deleted.
    List<String> taxIdsToDelete = new ArrayList<>();
    // Optional parameters can be set to null.
    Long catalogVersion = null;

    do {
      try {
        ListCatalogResponse result =
            catalogApi.listCatalogAsync(cursor, CatalogObjectTypes.TAX.toString(), catalogVersion)
                .join();

        List<CatalogObject> taxes =
            result.getObjects() == null ? new ArrayList<>() : result.getObjects();
        for (CatalogObject tax : taxes) {
          String key = createTaxKey(tax);
          DuplicateTaxInfo duplicateTaxInfo;
          if (!taxInfoMap.containsKey(key)) {
            // If this is the first time we've seen this tax, add it to the map.
            duplicateTaxInfo = new DuplicateTaxInfo(tax);
            taxInfoMap.put(key, duplicateTaxInfo);
          } else {
            // Otherwise, merge this tax into the first occurrence of the tax. The first
            // time we find a duplicate, we add the tax to list of taxes that need to be updated.
            duplicateTaxInfo = taxInfoMap.get(key);
            duplicateTaxInfo.mergeDuplicate(tax, logger);
            if (!duplicateTaxInfo.foundDuplicate) {
              duplicateTaxInfo.markObjectAsDuplicate();
              taxesToUpdate.add(duplicateTaxInfo.masterTax);
            }
            taxIdsToDelete.add(tax.getId());
          }
        }
        // Move to the next page.
        cursor = result.getCursor();
      } catch (CompletionException e) {
        // Extract the actual exception
        ApiException exception = (ApiException) e.getCause();
        if (checkAndLogErrors(exception.getErrors())) {
          return;
        }
      }
    } while (cursor != null);

    CompletableFuture<BatchUpsertCatalogObjectsResponse> updateResponseFuture =
        CompletableFuture.completedFuture(null);
    CompletableFuture<BatchDeleteCatalogObjectsResponse> deleteResponseFuture =
        CompletableFuture.completedFuture(null);
    // Batch update taxes.
    if (taxesToUpdate.size() > 0) {
      BatchUpsertCatalogObjectsRequest batchUpsertRequest =
          new BatchUpsertCatalogObjectsRequest.Builder(
              UUID.randomUUID().toString(),
              singletonList(new CatalogObjectBatch(taxesToUpdate))).build();

      updateResponseFuture = catalogApi.batchUpsertCatalogObjectsAsync(batchUpsertRequest);
    }

    // Batch delete taxes.
    if (taxIdsToDelete.size() > 0) {
      BatchDeleteCatalogObjectsRequest deleteTaxRequest =
          new BatchDeleteCatalogObjectsRequest.Builder()
              .objectIds(taxIdsToDelete).build();

      deleteResponseFuture = catalogApi.batchDeleteCatalogObjectsAsync(deleteTaxRequest);
    }

    // When both futures have been processed, do the logging
    updateResponseFuture.thenAcceptBoth(deleteResponseFuture, (updateResult, deleteResult) -> {
      // Log results.
      int totalMerged = taxesToUpdate.size() + taxIdsToDelete.size();
      if (totalMerged == 0) {
        logger.info("No duplicate taxes found");
      } else {
        logger.info(totalMerged + " taxes merged into " + taxesToUpdate.size());
      }
    }).exceptionally(exception -> {
      // Extract the actual exception
      ApiException e = (ApiException) exception.getCause();
      checkAndLogErrors(e.getErrors());
      return null;
    }).join();
  }

  /**
   * Creates a key based on a tax' name, percentage, and inclusion type.
   */
  private String createTaxKey(CatalogObject taxObject) {
    CatalogTax tax = taxObject.getTaxData();
    return tax.getName() + ":::" + tax.getPercentage() + ":::" + tax.getInclusionType();
  }

  /**
   * Information about duplicate taxes.
   */
  static class DuplicateTaxInfo {

    /**
     * The first occurrence our unique tax is treated as the master. Additional identical taxes will
     * be merged into this one.
     */
    private CatalogObject masterTax;

    /**
     * boolean indicating whether or not we have found a duplicate tax matching the master.
     */
    private boolean foundDuplicate = false;

    DuplicateTaxInfo(CatalogObject masterTax) {
      this.masterTax = masterTax;
    }

    void markObjectAsDuplicate() {
      this.foundDuplicate = true;
    }

    CatalogObject getMasterTax() {
      return this.masterTax;
    }

    void mergeDuplicate(CatalogObject duplicateTax, Logger logger) {
      if (duplicateTax.getPresentAtAllLocations()) {
        if (masterTax.getPresentAtAllLocations()) {
          // Both the master tax and the duplicate tax are present at all locations.
          List<String> absentAtMaster =
              masterTax.getAbsentAtLocationIds() == null ? new ArrayList<>()
                  : masterTax.getAbsentAtLocationIds();
          List<String> absentAtDuplicate =
              duplicateTax.getAbsentAtLocationIds() == null ? new ArrayList<>()
                  : duplicateTax.getAbsentAtLocationIds();
          Set<String> absentAtLocationIds = new HashSet<>(absentAtMaster);
          absentAtLocationIds.retainAll(absentAtDuplicate);

          masterTax = new CatalogObject.Builder(masterTax.getType(), masterTax.getId())
              .absentAtLocationIds(new ArrayList<>(absentAtLocationIds))
              .presentAtLocationIds(new ArrayList<>())
              .presentAtAllLocations(masterTax.getPresentAtAllLocations())
              .taxData(masterTax.getTaxData())
              .version(masterTax.getVersion())
              .build();
        } else {
          // The duplicate tax is present at all locations, the master is not.
          List<String> presentAtMaster =
              masterTax.getPresentAtLocationIds() == null ? new ArrayList<>()
                  : masterTax.getPresentAtLocationIds();
          List<String> absentAtDuplicate =
              duplicateTax.getAbsentAtLocationIds() == null ? new ArrayList<>()
                  : duplicateTax.getAbsentAtLocationIds();
          Set<String> absentAtLocationIds = new HashSet<>(absentAtDuplicate);
          absentAtLocationIds.removeAll(presentAtMaster);

          masterTax = new CatalogObject.Builder(masterTax.getType(), masterTax.getId())
              .absentAtLocationIds(new ArrayList<>(absentAtLocationIds))
              .presentAtLocationIds(new ArrayList<>())
              .presentAtAllLocations(true)
              .taxData(masterTax.getTaxData())
              .version(masterTax.getVersion())
              .build();
        }
      } else {
        if (masterTax.getPresentAtAllLocations()) {
          // The master tax is present at all locations but the duplicate tax is not.
          List<String> absentAtMaster =
              masterTax.getAbsentAtLocationIds() == null ? new ArrayList<>()
                  : masterTax.getAbsentAtLocationIds();
          List<String> presentAtDuplicate =
              duplicateTax.getPresentAtLocationIds() == null ? new ArrayList<>()
                  : duplicateTax.getPresentAtLocationIds();
          Set<String> absentAtLocationIds = new HashSet<>(absentAtMaster);
          absentAtLocationIds.removeAll(presentAtDuplicate);

          masterTax = new CatalogObject.Builder(masterTax.getType(), masterTax.getId())
              .absentAtLocationIds(new ArrayList<>(absentAtLocationIds))
              .presentAtLocationIds(new ArrayList<>())
              .presentAtAllLocations(masterTax.getPresentAtAllLocations())
              .taxData(masterTax.getTaxData())
              .version(masterTax.getVersion())
              .build();
        } else {
          // Neither the master tax nor the duplicate tax are present at all locations.
          List<String> presentAtMaster =
              masterTax.getPresentAtLocationIds() == null ? new ArrayList<>()
                  : masterTax.getPresentAtLocationIds();
          List<String> presentAtDuplicate =
              duplicateTax.getPresentAtLocationIds() == null ? new ArrayList<>()
                  : duplicateTax.getPresentAtLocationIds();
          Set<String> presentAtLocationIds = new HashSet<>(presentAtMaster);
          presentAtLocationIds.addAll(presentAtDuplicate);

          masterTax = new CatalogObject.Builder(masterTax.getType(), masterTax.getId())
              .absentAtLocationIds(new ArrayList<>())
              .presentAtLocationIds(new ArrayList<>(presentAtLocationIds))
              .presentAtAllLocations(masterTax.getPresentAtAllLocations())
              .taxData(masterTax.getTaxData())
              .version(masterTax.getVersion())
              .build();
        }
      }
    }
  }
}
