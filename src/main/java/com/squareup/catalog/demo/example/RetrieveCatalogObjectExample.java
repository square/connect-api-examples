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

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.CatalogCategory;
import com.squareup.connect.models.CatalogDiscount;
import com.squareup.connect.models.CatalogItem;
import com.squareup.connect.models.CatalogItemModifierListInfo;
import com.squareup.connect.models.CatalogItemVariation;
import com.squareup.connect.models.CatalogModifier;
import com.squareup.connect.models.CatalogModifierList;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogTax;
import com.squareup.connect.models.RetrieveCatalogObjectResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * This example retrieves a single catalog object by ID and all related objects.
 */
public class RetrieveCatalogObjectExample extends Example {

  public RetrieveCatalogObjectExample(Logger logger) {
    super("retrieve_catalog_object", "Retrieve a catalog object by ID.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    String catalogObjectId = promptUserInput("Enter catalog object ID: ");

    // Send a request to retrieve the catalog object by ID. The second boolean arguments indicates
    // the we want related objects, such as the taxes linked to an item.
    RetrieveCatalogObjectResponse response =
        catalogApi.retrieveCatalogObject(catalogObjectId, true);
    if (checkAndLogErrors(response.getErrors())) {
      return;
    }

    // Put the related objects into a map keyed by catalog object ID so we can look them up faster.
    HashMap<String, CatalogObject> relatedObjectsMap = new HashMap<>();
    for (CatalogObject relatedObject : response.getRelatedObjects()) {
      String relatedObjectId = relatedObject.getId();
      relatedObjectsMap.put(relatedObjectId, relatedObject);
    }

    CatalogObject catalogObject = response.getObject();
    logCatalogObjectDetails(catalogObject, relatedObjectsMap);
  }

  /**
   * Log info about the retrieved catalog object based on it's type.
   *
   * @param catalogObject the {@link CatalogObject} to log info about
   * @param relatedObjectsMap a map of related objects keyed by their IDs
   */
  void logCatalogObjectDetails(CatalogObject catalogObject,
      Map<String, CatalogObject> relatedObjectsMap) {
    CatalogObject.TypeEnum type = catalogObject.getType();
    switch (type) {
      case CATEGORY:
        logCategoryDetails(catalogObject);
        break;
      case DISCOUNT:
        logDiscountDetails(catalogObject);
        break;
      case ITEM:
        logItemDetails(catalogObject, relatedObjectsMap);
        break;
      case ITEM_VARIATION:
        logger.info(getItemVariationLogMessage(catalogObject, ""));
        break;
      case MODIFIER:
        logger.info(getModifierLogMessage(catalogObject, ""));
        break;
      case MODIFIER_LIST:
        logModifierListData(catalogObject);
        break;
      case TAX:
        logger.info(getTaxLogMessage(catalogObject, ""));
        break;
      default:
        logger.info("Unrecognized catalog object type: " + type);
    }
  }

  /**
   * Logs information about a {@link CatalogCategory}.
   */
  private void logCategoryDetails(CatalogObject categoryObject) {
    CatalogCategory category = categoryObject.getCategoryData();
    logger.info("[" + categoryObject.getType() + "] " + category.getName());
  }

  /**
   * Logs information about a {@link CatalogDiscount}.
   */
  private void logDiscountDetails(CatalogObject discountObject) {
    CatalogDiscount discount = discountObject.getDiscountData();
    String logMessage = ("[" + discountObject.getType() + "] " + discount.getName())
        + "\n  ID: " + discountObject.getId()
        + "\n  Type: " + discount.getDiscountType()
        + "\n  Amount: " + Moneys.format(discount.getAmountMoney())
        + "\n  Percentage: " + discount.getPercentage();
    logger.info(logMessage);
  }

  /**
   * Logs information about a {@link CatalogItem}.
   */
  private void logItemDetails(CatalogObject itemObject,
      Map<String, CatalogObject> relatedObjectsMap) {
    CatalogItem item = itemObject.getItemData();
    String logMessage = "[" + itemObject.getType() + "] " + item.getName()
        + "\n  ID: " + itemObject.getId()
        + "\n  Image URL: " + item.getImageUrl();

    // Get the category from the related objects.
    logMessage += "\n  Category: ";
    if (item.getCategoryId() == null) {
      logMessage += "<uncategorized>";
    } else {
      CatalogObject categoryObject = relatedObjectsMap.get(item.getCategoryId());
      logMessage +=
          categoryObject.getCategoryData().getName() + " (" + categoryObject.getId() + ")";
    }

    // Add item variations.
    logMessage += "\n  Item Variations:";
    for (CatalogObject variationObject : item.getVariations()) {
      logMessage += "\n" + getItemVariationLogMessage(variationObject, "    ");
    }

    // Add taxes.
    logMessage += "\n  Taxes:";
    if (item.getTaxIds().isEmpty()) {
      logMessage += " <none>";
    } else {
      for (String taxId : item.getTaxIds()) {
        CatalogObject taxObject = relatedObjectsMap.get(taxId);
        logMessage += "\n" + getTaxLogMessage(taxObject, "    ");
      }
    }

    // Add modifier lists.
    logMessage += "\n  Modifier Lists:";
    boolean hasModifierList = false;
    for (CatalogItemModifierListInfo modifierListInfo : item.getModifierListInfo()) {
      // If a CatalogItemModifierListInfo is disabled, it means that the item was once linked to
      // the modifier list, but has since been unlinked. We keep the CatalogItemModifierListInfo
      // around so we can restore the item-specific modifier list configuration (ex. pre-selected
      // modifiers) when the item is re-linked to the modifier list.
      Boolean modifierListEnabledForItem = modifierListInfo.getEnabled();
      if (modifierListEnabledForItem != null && modifierListEnabledForItem) {
        String modifierListId = modifierListInfo.getModifierListId();
        CatalogObject modifierListObject = relatedObjectsMap.get(modifierListId);
        CatalogModifierList modifierList = modifierListObject.getModifierListData();
        logMessage += "\n    " + modifierList.getName() + " (" + modifierListId + ")";
        hasModifierList = true;
      }
    }
    if (!hasModifierList) {
      logMessage += " <none>";
    }

    // Log the message.
    logger.info(logMessage);
  }

  /**
   * Returns a log message describing a {@link CatalogItemVariation}.
   *
   * @param itemVariationObject the {@link CatalogObject} containing the {@link
   * CatalogItemVariation}.
   * @param prefix the prefix to apply to each line of the log message
   */
  private String getItemVariationLogMessage(CatalogObject itemVariationObject, String prefix) {
    CatalogItemVariation itemVariation = itemVariationObject.getItemVariationData();
    return prefix + "[" + itemVariationObject.getType() + "] " + itemVariation.getName()
        + "\n" + prefix + "  ID: " + itemVariationObject.getId()
        + "\n" + prefix + "  Price: " + Moneys.format(itemVariation.getPriceMoney())
        + "\n" + prefix + "  SKU: " + itemVariation.getSku();
  }

  /**
   * Logs information about a {@link CatalogModifierList}.
   */
  private void logModifierListData(CatalogObject modifierListObject) {
    CatalogModifierList modifierList = modifierListObject.getModifierListData();
    String logMessage = "[" + modifierListObject.getType() + "] " + modifierList.getName()
        + "\n  ID: " + modifierListObject.getId()
        + "\n  Selection Type: " + modifierList.getSelectionType()
        + "\n  Modifiers: ";

    // Add the modifiers.
    for (CatalogObject modifierObject : modifierList.getModifiers()) {
      CatalogModifier modifier = modifierObject.getModifierData();
      logMessage += "\n    " + modifier.getName() + " (" + modifierObject.getId() + ")";
    }

    logger.info(logMessage);
  }

  /**
   * Returns a log message describing a {@link CatalogModifier}.
   *
   * @param modifierObject the {@link CatalogObject} containing the {@link CatalogModifier}.
   * @param prefix the prefix to apply to each line of the log message
   */
  private String getModifierLogMessage(CatalogObject modifierObject, String prefix) {
    CatalogModifier modifier = modifierObject.getModifierData();
    return prefix + "[" + modifierObject.getType() + "] " + modifier.getName()
        + "\n" + prefix + "  ID: " + modifierObject.getId()
        + "\n" + prefix + "  Price: " + Moneys.format(modifier.getPriceMoney());
  }

  /**
   * Returns a log message describing a {@link CatalogTax}.
   *
   * @param taxObject the {@link CatalogObject} containing the {@link CatalogTax}.
   * @param prefix the prefix to apply to each line of the log message
   */
  private String getTaxLogMessage(CatalogObject taxObject, String prefix) {
    CatalogTax tax = taxObject.getTaxData();
    return prefix + "[" + taxObject.getType() + "] " + tax.getName()
        + "\n" + prefix + "  ID: " + taxObject.getId()
        + "\n" + prefix + "  Percentage: " + tax.getPercentage()
        + "\n" + prefix + "  Inclusion Type: " + tax.getInclusionType();
  }
}
