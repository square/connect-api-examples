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

import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.connect.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogObjectBatch;
import com.squareup.connect.models.CatalogTax;
import com.squareup.connect.models.ListCatalogResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This example merges identical taxes (same name, percentage, and inclusion type).
 */
public class DeduplicateTaxesExample extends Example {

  public DeduplicateTaxesExample() {
    super("deduplicate_taxes",
        "Merge identical taxes (same name, percentage, and inclusion type)");
  }

  @Override public void execute(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException {

    // A map that allows us to look up a tax based on his name, percentage, and inclusion type.
    Map<String, DuplicateTaxInfo> taxInfoMap = new HashMap<>();
    // The list of taxes that we need to update.
    List<CatalogObject> taxesToUpdate = new ArrayList<>();
    // The IDs of duplicate taxes that will be deleted.
    List<String> taxIdsToDelete = new ArrayList<>();

    String cursor = null;
    do {
      // Retrieve a page of taxes.
      ListCatalogResponse listResponse = catalogApi.listCatalog(null, "TAX");
      if (checkAndLogErrors(listResponse.getErrors())) {
        return;
      }

      List<CatalogObject> taxes = listResponse.getObjects();
      for (CatalogObject tax : taxes) {
        String key = createTaxKey(tax);
        DuplicateTaxInfo duplicateTaxInfo = taxInfoMap.get(key);
        if (duplicateTaxInfo == null) {
          // If this is the first time we've seen this tax, add it to the map.
          duplicateTaxInfo = new DuplicateTaxInfo(tax);
          taxInfoMap.put(key, duplicateTaxInfo);
        } else {
          // Otherwise, merge this tax into the first occurrence of the tax. The first time we find
          // a duplicate, we add the tax to list of taxes that need to be updated.
          if (!duplicateTaxInfo.foundDuplicate) {
            taxesToUpdate.add(duplicateTaxInfo.masterTax);
          }
          duplicateTaxInfo.mergeDuplicate(tax);
          taxIdsToDelete.add(tax.getId());
        }
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);

    // Batch update taxes.
    if (taxesToUpdate.size() > 0) {
      BatchUpsertCatalogObjectsRequest batchUpsertRequest = new BatchUpsertCatalogObjectsRequest()
          .idempotencyKey(UUID.randomUUID().toString())
          .addBatchesItem(new CatalogObjectBatch()
              .objects(taxesToUpdate)
          );
      BatchUpsertCatalogObjectsResponse batchUpsertResponse =
          catalogApi.batchUpsertCatalogObjects(batchUpsertRequest);
      if (checkAndLogErrors(batchUpsertResponse.getErrors())) {
        return;
      }
    }

    // Batch delete taxes.
    if (taxIdsToDelete.size() > 0) {
      BatchDeleteCatalogObjectsRequest deleteTaxRequest = new BatchDeleteCatalogObjectsRequest()
          .objectIds(taxIdsToDelete);
      BatchDeleteCatalogObjectsResponse deleteTaxResponse =
          catalogApi.batchDeleteCatalogObjects(deleteTaxRequest);
      if (checkAndLogErrors(deleteTaxResponse.getErrors())) {
        return;
      }
    }

    // Log results.
    int totalMerged = taxesToUpdate.size() + taxIdsToDelete.size();
    if (totalMerged == 0) {
      logger.info("No duplicate taxes found");
    } else {
      logger.info(totalMerged + " taxes merged into " + taxesToUpdate.size());
    }
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
     * The first occurrence our unique tax is treated as the master. Additional  dentical taxes
     * will be merged into this one.
     */
    private final CatalogObject masterTax;

    /**
     * bullion indicating whether or not we have found a duplicate tax matching the master.
     */
    private boolean foundDuplicate = false;

    DuplicateTaxInfo(CatalogObject masterTax) {
      this.masterTax = masterTax;
    }

    void mergeDuplicate(CatalogObject duplicateTax) {
      this.foundDuplicate = true;

      if (duplicateTax.getPresentAtAllLocations()) {
        if (masterTax.getPresentAtAllLocations()) {
          // Both the master tax and the duplicate tax are present at all locations.
          Set<String> absentAtLocationIds = new HashSet<>(masterTax.getAbsentAtLocationIds());
          absentAtLocationIds.retainAll(duplicateTax.getAbsentAtLocationIds());
          masterTax.setAbsentAtLocationIds(new ArrayList<>(absentAtLocationIds));
          masterTax.setPresentAtLocationIds(new ArrayList<>());
        } else {
          // The duplicate tax is present at all locations, the master is not.
          Set<String> absentAtLocationIds = new HashSet<>(duplicateTax.getAbsentAtLocationIds());
          absentAtLocationIds.removeAll(masterTax.getPresentAtLocationIds());
          masterTax.setPresentAtAllLocations(true);
          masterTax.setAbsentAtLocationIds(new ArrayList<>(absentAtLocationIds));
          masterTax.setPresentAtLocationIds(new ArrayList<>());
        }
      } else {
        if (masterTax.getPresentAtAllLocations()) {
          // The master tax is present at all locations but the duplicate tax is not.
          Set<String> absentAtLocationIds = new HashSet<>(masterTax.getAbsentAtLocationIds());
          absentAtLocationIds.removeAll(duplicateTax.getPresentAtLocationIds());
          masterTax.setAbsentAtLocationIds(new ArrayList<>(absentAtLocationIds));
          masterTax.setPresentAtLocationIds(new ArrayList<>());
        } else {
          // Neither the master tax nor the duplicate tax are present at all locations.
          Set<String> presentAtLocationIds = new HashSet<>(masterTax.getPresentAtLocationIds());
          presentAtLocationIds.addAll(duplicateTax.getPresentAtLocationIds());
          masterTax.setPresentAtLocationIds(new ArrayList<>(presentAtLocationIds));
          masterTax.setAbsentAtLocationIds(new ArrayList<>());
        }
      }
    }
  }
}

