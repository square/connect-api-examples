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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogObject;

/**
 * Utility methods used to clone a {@link CatalogItem}.
 */
public class ItemCloneUtil extends CatalogObjectCloneUtil<CatalogItem> {

  ItemCloneUtil() {
    super(CatalogObjectTypes.ITEM);
  }

  @Override
  CatalogItem getCatalogData(CatalogObject catalogObject) {
    return catalogObject.getItemData();
  }

  @Override
  String encodeCatalogData(CatalogItem item) {
    List<String> variationNames = new ArrayList<>();
    if (item.getVariations() != null) {
      for (CatalogObject variation : item.getVariations()) {
        variationNames.add(variation.getItemVariationData().getName());
      }

      // Sort to keep order of variations consistent.
      Collections.sort(variationNames);
    }

    String encodedVariations = String.join("::", variationNames);
    return item.getName()
        + ":::"
        + item.getDescription()
        + ":::"
        + encodedVariations;
  }

  @Override
  CatalogObject removeSourceAccountMetaData(CatalogObject catalogObject) {
    CatalogObject cleanObject = super.removeSourceAccountMetaData(catalogObject);
    CatalogItem oldItemData = cleanObject.getItemData();

    List<CatalogObject> oldVariations = oldItemData.getVariations();
    List<CatalogObject> newVariations = new ArrayList<>();

    // remove metadata for the item variation
    for (CatalogObject oldVariation : oldVariations) {
      // we want to remove item_id from the itemVariationData
      CatalogItemVariation oldItemVariationData = oldVariation.getItemVariationData();
      CatalogObject newVariation = super.removeSourceAccountMetaData(oldVariation).toBuilder()
          .itemVariationData(
              oldItemVariationData.toBuilder().itemId(null).locationOverrides(null).build())
          .build();
      newVariations.add(newVariation);
    }

    // set the new variations, reset the tax IDs, image IDs
    return cleanObject.toBuilder()
        .itemData(oldItemData.toBuilder()
            .variations(newVariations)
            .taxIds(Collections.emptyList())
            .categoryId(null)
            .build())
        .presentAtAllLocations(true)
        .build();
  }
}
