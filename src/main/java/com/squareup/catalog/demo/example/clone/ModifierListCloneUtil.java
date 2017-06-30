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

import com.squareup.connect.models.CatalogModifier;
import com.squareup.connect.models.CatalogModifierList;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.Money;
import java.util.HashSet;

/**
 * Utility methods used to clone a {@link CatalogModifierList}.
 */
class ModifierListCloneUtil extends CatalogObjectCloneUtil<CatalogModifierList> {

  ModifierListCloneUtil() {
    super(CatalogObject.TypeEnum.MODIFIER_LIST);
  }

  @Override CatalogModifierList getCatalogData(CatalogObject catalogObject) {
    return catalogObject.getModifierListData();
  }

  @Override public String encodeCatalogData(CatalogModifierList modifierList) {
    return modifierList.getName() + ":::" + modifierList.getSelectionType();
  }

  @Override
  void removeSourceAccountMetaData(CatalogObject catalogObject) {
    super.removeSourceAccountMetaData(catalogObject);

    // Make modifier lists available at all locations by default.
    catalogObject.setPresentAtAllLocations(true);

    // Also remove meta data from the embedded modifiers.
    CatalogModifierList modifierList = catalogObject.getModifierListData();
    for (CatalogObject modifierObject : modifierList.getModifiers()) {
      removeSourceAccountMetaDataFromModifier(modifierObject, catalogObject);
    }
  }

  @Override
  CatalogObject mergeSourceCatalogObjectIntoTarget(CatalogObject sourceCatalogObject,
      CatalogObject targetCatalogObject) {
    // Create a HashSet of the modifiers in the target modifier list.
    CatalogModifierList targetModifierList = targetCatalogObject.getModifierListData();
    HashSet<String> targetModifiers = getEncodedModifiers(targetModifierList);

    // Add modifiers from the source modifier list that are not already in the target list.
    boolean addedModifierToTarget = false;
    CatalogModifierList sourceModifierList = sourceCatalogObject.getModifierListData();
    for (CatalogObject modifierObject : sourceModifierList.getModifiers()) {
      String encodeModifier = encodeModifier(modifierObject);
      if (!targetModifiers.contains(encodeModifier)) {
        // Prepare the catalog object for the target account.
        removeSourceAccountMetaDataFromModifier(modifierObject, targetCatalogObject);

        // Add the modifier to the target modifier list.
        targetModifierList.addModifiersItem(modifierObject);

        // Mark that we've updated the target modifier list.
        addedModifierToTarget = true;
      }
    }

    // Return the modifier target CatalogObject if it changed.
    return addedModifierToTarget ? targetCatalogObject : null;
  }

  /**
   * Returns a set of all encoded modifiers in the modifier list.
   */
  private HashSet<String> getEncodedModifiers(CatalogModifierList modifierList) {
    HashSet<String> encodedModifiers = new HashSet<>();
    for (CatalogObject modifierObject : modifierList.getModifiers()) {
      encodedModifiers.add(encodeModifier(modifierObject));
    }
    return encodedModifiers;
  }

  private String encodeModifier(CatalogObject modifierObject) {
    CatalogModifier modifier = modifierObject.getModifierData();

    // If the price is null, coerece it to 0. A null price is the same as a $0 price.
    Money price = modifier.getPriceMoney();
    long amount = (price == null) ? 0 : price.getAmount();

    return modifier.getName() + ":::" + amount;
  }

  /**
   * Removes meta data from the {@link CatalogObject} that only applies to the source account, such
   * as location IDs and version. Also matches locations with the parent modifier list.
   */
  private void removeSourceAccountMetaDataFromModifier(CatalogObject modifier,
      CatalogObject modifierList) {
    super.removeSourceAccountMetaData(modifier);

    // Make the locations of the modifier match the locations of the modifier list.
    modifier.setPresentAtAllLocations(modifierList.getPresentAtAllLocations());
    modifier.setPresentAtLocationIds(modifierList.getPresentAtLocationIds());
    modifier.setAbsentAtLocationIds(modifierList.getAbsentAtLocationIds());
  }
}
