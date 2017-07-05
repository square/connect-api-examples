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

import com.squareup.catalog.demo.util.Moneys;
import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.CatalogDiscount;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.ListCatalogResponse;

import static com.squareup.connect.models.CatalogObjectType.DISCOUNT;

/**
 * This example lists all discounts in the catalog.
 */
public class ListDiscountsExample extends Example {

  public ListDiscountsExample() {
    super("list_discounts", "List all discounts.");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    String cursor = null;
    do {
      // Retrieve a page of discounts.
      ListCatalogResponse listResponse = catalogApi.listCatalog(cursor, DISCOUNT.toString());
      if (checkAndLogErrors(listResponse.getErrors())) {
        return;
      }

      if (listResponse.getObjects().isEmpty() && cursor == null) {
        logger.info("No discounts found.");
        return;
      }

      for (CatalogObject discountObject : listResponse.getObjects()) {
        CatalogDiscount discount = discountObject.getDiscountData();
        String amount = null;

        // Determine which type of discount this is.
        switch (discount.getDiscountType()) {
          case FIXED_AMOUNT:
            amount = Moneys.format(discount.getAmountMoney());
            break;
          case FIXED_PERCENTAGE:
            amount = discount.getPercentage() + "%";
            break;
          case VARIABLE_AMOUNT:
            amount = "variable $";
            break;
          case VARIABLE_PERCENTAGE:
            amount = "variable %";
            break;
        }

        // Log the name and amount of the discount.
        String logMessage = discount.getName();
        if (amount != null) {
          logMessage += " [" + amount + "]";
        }
        logMessage += " (" + discountObject.getId() + ")";
        logger.info(logMessage);
      }

      // Move to the next page.
      cursor = listResponse.getCursor();
    } while (cursor != null);
  }
}
