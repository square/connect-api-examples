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
import com.squareup.connect.models.CatalogItem;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogTax;
import com.squareup.connect.models.ListCatalogResponse;
import com.squareup.connect.models.UpdateItemTaxesRequest;
import com.squareup.connect.models.UpdateItemTaxesResponse;
import java.util.ArrayList;
import java.util.List;

import static com.squareup.connect.models.CatalogItem.ProductTypeEnum.REGULAR;
import static com.squareup.connect.models.CatalogObjectType.ITEM;
import static com.squareup.connect.models.CatalogObjectType.TAX;

/**
 * This example shows a list of taxes to choose from, then applies the selected tax to
 * all items in the Item Library.
 **/
public class ApplyTaxToAllIItemsExample extends Example {

  public ApplyTaxToAllIItemsExample() {
    super("apply_tax_to_all_items", "Applies a selected tax to all items.");
  }

  @Override public void execute(CatalogApi catalogApi, LocationsApi locationsApi)
      throws ApiException {
    // Retrieve taxes from the server.
    List<CatalogObject> allTaxes = retrieveAllTaxes(catalogApi);
    if (allTaxes.isEmpty()) {
      logger.info("No taxes found in catalog");
      return;
    }

    // Prompt the user to select a tax.
    CatalogObject tax = promptUserForTax(allTaxes);
    if (tax == null) {
      return;
    }

    // Apply the tax to all items.
    applyTaxToAllItems(catalogApi, tax);
  }

  /**
   * Retrieves all taxes in the catalog.
   *
   * @return a complete list of taxes
   */
  private List<CatalogObject> retrieveAllTaxes(CatalogApi catalogApi) throws ApiException {
    List<CatalogObject> allTaxes = new ArrayList<>();
    String cursor = null;
    do {
      // Retrieve a page of taxes
      ListCatalogResponse listResponse = catalogApi.listCatalog(cursor, TAX.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        return null;
      }

      // Append the new taxes to the complete list of taxes.
      allTaxes.addAll(listResponse.getObjects());

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);

    return allTaxes;
  }

  /**
   * Displays all taxes in the console so the user can select one. The selected tax is returned.
   *
   * @param allTaxes a complete list of taxes
   * @return the selected tax
   */
  private CatalogObject promptUserForTax(List<CatalogObject> allTaxes) throws ApiException {
    // Display the list of taxes.
    logger.info("Available Taxes:");
    for (int i = 0; i < allTaxes.size(); i++) {
      CatalogTax tax = allTaxes.get(i).getTaxData();
      logger.info(i + ". " + tax.getName() + " (" + tax.getPercentage() + "%)");
    }

    // Prompt the user to select a tax.
    String userTaxIndex = promptUserInput("Select a tax to apply to all items: ");
    int taxIndex;
    try {
      taxIndex = Integer.parseInt(userTaxIndex);
    } catch (NumberFormatException e) {
      logger.error("Invalid tax index specified");
      return null;
    }

    if (taxIndex < 0 || taxIndex >= allTaxes.size()) {
      logger.error("Specified index is out of range.");
    }

    return allTaxes.get(taxIndex);
  }

  /**
   * Iterates over all items, applying the specified tax to each.
   *
   * @param tax the tax to apply to all items
   */
  private void applyTaxToAllItems(CatalogApi catalogApi, CatalogObject tax) throws ApiException {
    logger.info("Applying " + tax.getTaxData().getName() + " to all items");

    final String taxId = tax.getId();
    String cursor = null;
    int totalItemsApplied = 0;
    int totalItemsVisited = 0;
    final long startTimeMillis = System.currentTimeMillis();
    do {
      // Retrieve a page of items.
      ListCatalogResponse listResponse = catalogApi.listCatalog(cursor, ITEM.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        return;
      }

      List<CatalogObject> items = listResponse.getObjects();
      if (items.size() == 0) {
        if (cursor == null) {
          logger.info("No items found in catalog.");
          return;
        }
      } else {
        // Figure out which items to apply the tax to.
        UpdateItemTaxesRequest updateItemTaxesRequest = new UpdateItemTaxesRequest()
            .addTaxesToEnableItem(taxId);
        for (CatalogObject item : items) {
          // Ignore non-regular items and items already linked to the tax.
          CatalogItem.ProductTypeEnum itemType = item.getItemData().getProductType();
          if ((itemType == null || itemType == REGULAR) &&
              !item.getItemData().getTaxIds().contains(taxId)) {
            updateItemTaxesRequest.addItemIdsItem(item.getId());
          }
        }

        // Add the tax to the items.
        if (updateItemTaxesRequest.getItemIds().size() > 0) {
          totalItemsApplied += updateItemTaxesRequest.getItemIds().size();
          UpdateItemTaxesResponse updateItemTaxesResponse =
              catalogApi.updateItemTaxes(updateItemTaxesRequest);
          if (checkAndLogErrors(updateItemTaxesResponse.getErrors())) {
            return;
          }
        }

        // Log info about this page of items we just deleted.
        long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
        totalItemsVisited += items.size();
        logger.info("Added tax to "
            + updateItemTaxesRequest.getItemIds().size()
            + " items ("
            + totalItemsVisited
            + " total items processed in "
            + elapsedTimeSeconds
            + " seconds)");
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);

    // Log results.
    long elapsedTimeSeconds = (System.currentTimeMillis() - startTimeMillis) / 1000;
    logger.info("Success! Applied "
        + tax.getTaxData().getName()
        + " to "
        + totalItemsApplied
        + " items out of "
        + totalItemsVisited
        + " in "
        + elapsedTimeSeconds
        + " seconds");
  }
}
