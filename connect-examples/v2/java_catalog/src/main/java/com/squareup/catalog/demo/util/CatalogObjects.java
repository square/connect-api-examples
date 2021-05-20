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

import static java.util.Arrays.asList;

import java.util.UUID;

import com.squareup.square.models.CatalogCategory;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.Money;

/**
 * Utility class to create common {@link CatalogObject CatalogObjects}.
 */
public class CatalogObjects {

  /**
   * Builds a category that is available at all locations.
   *
   * @param clientId the temporary client-generated id
   * @param name     the name of the category
   * @return the new {@link CatalogObject} that wraps the category
   */
  public static CatalogObject category(String clientId, String name) {
    // Categories are always available at all locations
    return new CatalogObject.Builder(CatalogObjectTypes.CATEGORY.toString(), clientId)
        .presentAtAllLocations(true)
        .categoryData(new CatalogCategory(name))
        .build();
  }

  /**
   * Builds a simple item with one item variation called Regular.
   *
   * @param clientId   the temporary client-generated id
   * @param name       the name of the item variation
   * @param categoryId the ID of the category that this item belongs to
   * @param cents      the fixed price in cents
   * @return the new {@link CatalogObject} that wraps the item variation
   */
  public static CatalogObject item(String clientId, String name, String categoryId, long cents) {
    return item(clientId, name, categoryId,
        itemVariation("#" + UUID.randomUUID().toString(), "Regular", cents));
  }

  /**
   * Builds an item with the specified variations.
   *
   * @param clientId       the temporary client-generated id
   * @param name           the name of the item variation
   * @param categoryId     the ID of the category that this item belongs to
   * @param itemVariations the item variations in the item
   * @return the new {@link CatalogObject} that wraps the item variation
   */
  public static CatalogObject item(String clientId, String name, String categoryId,
      CatalogObject... itemVariations) {
    return new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(),
        clientId).presentAtAllLocations(true)
        .itemData(
            new CatalogItem.Builder().name(name)
                .categoryId(categoryId)
                .variations(asList(itemVariations))
                .build()
        )
        .build();
  }

  /**
   * Builds a fixed price item variation that is available at all locations.
   *
   * @param clientId the temporary client-generated id
   * @param name     the name of the item variation
   * @param cents    the fixed price in cents
   * @return the new {@link CatalogObject} that wraps the item variation
   */
  public static CatalogObject itemVariation(String clientId, String name, long cents) {
    return new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
        clientId).presentAtAllLocations(true)
        .itemVariationData(
            new CatalogItemVariation.Builder().name(name).pricingType("FIXED_PRICING")
                .priceMoney(new Money(cents, Moneys.getCurrency())).build())
        .build();
  }

  private CatalogObjects() {
  }
}
