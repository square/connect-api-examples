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
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.CatalogCategory;
import com.squareup.square.models.CatalogDiscount;
import com.squareup.square.models.CatalogImage;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogItemModifierListInfo;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogModifier;
import com.squareup.square.models.CatalogModifierList;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogTax;
import java.util.HashMap;
import java.util.Map;

import static com.squareup.catalog.demo.util.Prompts.promptUserInput;

/**
 * This example retrieves a single catalog object by ID and all related objects.
 */
public class RetrieveCatalogObjectExample extends Example {

  public RetrieveCatalogObjectExample(Logger logger) {
    super("retrieve_catalog_object", "Retrieve a catalog object by ID.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {
    String catalogObjectId = promptUserInput("Enter catalog object ID: ");

    // Send a request to retrieve the catalog object by ID. The second boolean arguments indicates
    // the we want related objects, such as the taxes linked to an item. Optional variable version
    // is set to null.
    Long catalogVersion = null;
    catalogApi.retrieveCatalogObjectAsync(catalogObjectId, true, catalogVersion).thenAccept(result -> {
        if (checkAndLogErrors(result.getErrors())) {
            return;
        }

        if(result.getObject() != null) {
            // Put the related objects into a map keyed by catalog object ID so we can look them up faster.
            HashMap<String, CatalogObject> relatedObjectsMap = new HashMap<>();
            if (result.getRelatedObjects() != null) {
                for (CatalogObject relatedObject : result.getRelatedObjects()) {
                    String relatedObjectId = relatedObject.getId();
                    relatedObjectsMap.put(relatedObjectId, relatedObject);
                }
            }

            CatalogObject catalogObject = result.getObject();
            logCatalogObjectDetails(catalogObject, relatedObjectsMap);
        } else {
            // There is no object with the given id.
            logger.info("No items with the given id were found.");
        }
    }).exceptionally(exception -> {
        // Log exception, return null.
        logger.error(exception.getMessage());
        return null;
    });
  }

  /**
   * Log info about the retrieved catalog object based on it's type.
   *
   * @param catalogObject the {@link CatalogObject} to log info about
   * @param relatedObjectsMap a map of related objects keyed by their IDs
   */
  void logCatalogObjectDetails(CatalogObject catalogObject, Map<String, CatalogObject> relatedObjectsMap) {
    CatalogObjectTypes type = CatalogObjectTypes.valueOf(catalogObject.getType());
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
        logItemVariationDetails(catalogObject, relatedObjectsMap);
        break;
      case MODIFIER:
        logModifierDetails(catalogObject, "");
        break;
      case MODIFIER_LIST:
        logModifierListDetails(catalogObject);
        break;
      case TAX:
        logTaxDetails(catalogObject, "");
        break;
      case IMAGE:
        logImageDetails(catalogObject, "");
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
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[" + discountObject.getType() + "] " + discount.getName())
        .append("\n  ID: " + discountObject.getId())
        .append("\n  Type: " + discount.getDiscountType())
        .append("\n  Amount: " + Moneys.format(discount.getAmountMoney()))
        .append("\n  Percentage: " + discount.getPercentage());

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogItem}.
   */
  private void logItemDetails(CatalogObject itemObject, Map<String, CatalogObject> relatedObjectsMap) {
    CatalogItem item = itemObject.getItemData();
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[" + itemObject.getType() + "] ")
        .append(item.getName())
        .append("\n  ID: " + itemObject.getId());

    // Append image id if one exists.
    if(itemObject.getImageId() != null) {
        logMessage.append("\n  Image ID: " + itemObject.getImageId());
    }

    // Get the category from the related objects.
    logMessage.append("\n  Category: ");
    if (item.getCategoryId() == null) {
      logMessage.append("<uncategorized>");
    } else {
      CatalogObject categoryObject = relatedObjectsMap.get(item.getCategoryId());
      logMessage.append(categoryObject.getCategoryData().getName() + " (" + categoryObject.getId() + ")");
    }

    // Add item variations.
    logMessage.append("\n  Item Variations:");
    for (CatalogObject variationObject : item.getVariations()) {
      CatalogItemVariation variation = variationObject.getItemVariationData();
      logMessage.append("\n    [" + variationObject.getType() + "] " + variation.getName())
        .append("\n      ID: " + variationObject.getId())
        .append("\n      Price: " + Moneys.format(variation.getPriceMoney()))
        .append("\n      SKU: " + (variation.getSku() == null ? "<none>" : variation.getSku()));
    }

    // Add taxes.
    logMessage.append("\n  Taxes:");
    if (item.getTaxIds() == null || item.getTaxIds().isEmpty()) {
      logMessage.append(" <none>");
    } else {
      for (String taxId : item.getTaxIds()) {
        CatalogObject taxObject = relatedObjectsMap.get(taxId);
        logMessage.append("\n" + getTaxLogMessage(taxObject, "    "));
      }
    }

    // Add modifier lists.
    logMessage.append("\n  Modifier Lists:");
    boolean hasModifierList = false;
    if (item.getModifierListInfo() != null) {
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
              logMessage.append("\n    " + modifierList.getName() + " (" + modifierListId + ")");
              hasModifierList = true;
            }
        }
    }
    if (!hasModifierList) {
      logMessage.append(" <none>");
    }

    // Add image. If the image_id exists, we can grab the image object from the related objects map and log it.
    logMessage.append("\n  Image:");
    if (itemObject.getImageId() != null) {
        logMessage.append("\n" + getImageLogMessage(relatedObjectsMap.get(itemObject.getImageId()), "    "));
    } else {
        logMessage.append(" <none>");
    }

    // Log the message.
    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogItemVariation}.
   */
  private void logItemVariationDetails(CatalogObject itemVariationObject,
      Map<String, CatalogObject> relatedObjectsMap) {
    CatalogItemVariation itemVariation = itemVariationObject.getItemVariationData();
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[" + itemVariationObject.getType() + "] " + itemVariation.getName())
        .append("\n  ID: " + itemVariationObject.getId())
        .append("\n  Price: " + Moneys.format(itemVariation.getPriceMoney()))
        .append("\n  SKU: " + (itemVariation.getSku() == null ? "<none>" : itemVariation.getSku()));

    // Get the item from the related objects.
    CatalogObject itemObject = relatedObjectsMap.get(itemVariation.getItemId());
    CatalogItem item = itemObject.getItemData();
    logMessage.append("\n  Item: " + item.getName() + " (" + itemObject.getId() + ")");

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogModifierList}.
   */
  private void logModifierListDetails(CatalogObject modifierListObject) {
    CatalogModifierList modifierList = modifierListObject.getModifierListData();
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[" + modifierListObject.getType() + "] " + modifierList.getName())
        .append("\n  ID: " + modifierListObject.getId())
        .append("\n  Selection Type: " + modifierList.getSelectionType())
        .append("\n  Modifiers: ");

    // Add the modifiers.
    for (CatalogObject modifierObject : modifierList.getModifiers()) {
      CatalogModifier modifier = modifierObject.getModifierData();
      logMessage.append("\n    " + modifier.getName() + " (" + modifierObject.getId() + ")");
    }

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogModifier}.
   */
  private void logModifierDetails(CatalogObject modifierObject, String prefix) {
    CatalogModifier modifier = modifierObject.getModifierData();
    StringBuilder logMessage = new StringBuilder(prefix);

    logMessage.append("[" + modifierObject.getType() + "] " + modifier.getName())
        .append("\n" + prefix + "  ID: " + modifierObject.getId())
        .append("\n" + prefix + "  Price: " + Moneys.format(modifier.getPriceMoney()));

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogTax}.
   */
  private void logTaxDetails(CatalogObject taxObject, String prefix) {
    logger.info(getTaxLogMessage(taxObject, prefix));
  }

  /**
   * Logs information about a {@link CatalogImage}.
   */
  private void logImageDetails(CatalogObject imageObject, String prefix) {
      logger.info(getImageLogMessage(imageObject, prefix));
  }

  /**
   * Returns a log message describing a {@link CatalogTax}.
   */
  private String getTaxLogMessage(CatalogObject taxObject, String prefix) {
    CatalogTax tax = taxObject.getTaxData();
    StringBuilder logMessage = new StringBuilder(prefix);

    logMessage.append("[" + taxObject.getType() + "] " + tax.getName())
        .append("\n" + prefix + "  ID: " + taxObject.getId())
        .append("\n" + prefix + "  Percentage: " + tax.getPercentage())
        .append("\n" + prefix + "  Inclusion Type: " + tax.getInclusionType());

    return logMessage.toString();
  }

  /**
   * Returns a log message describing a {@link CatalogImage}.
   */
  private String getImageLogMessage(CatalogObject imageObject, String prefix) {
    CatalogImage image = imageObject.getImageData();
    StringBuilder logMessage = new StringBuilder(prefix);

    logMessage.append("[" + imageObject.getType() + "] " + image.getName())
    .append("\n" + prefix + "  ID: " + imageObject.getId())
    .append("\n" + prefix + "  Caption: " + image.getCaption())
    .append("\n" + prefix + "  URL: " + image.getUrl());

    return logMessage.toString();
  }
}
