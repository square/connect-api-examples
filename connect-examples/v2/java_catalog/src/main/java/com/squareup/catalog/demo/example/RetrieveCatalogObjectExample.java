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

import static com.squareup.catalog.demo.util.Prompts.promptUserInput;

import com.squareup.square.exceptions.ApiException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * This example retrieves a single catalog object by ID and all related objects.
 */
public class RetrieveCatalogObjectExample extends Example {

  public RetrieveCatalogObjectExample(Logger logger) {
    super("retrieve_catalog_object", "Retrieves a catalog object by ID.", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {
    String catalogObjectId = promptUserInput("Enter catalog object ID: ");
    // Send a request to retrieve the catalog object by ID. The second boolean
    // argument indicates
    // that we want related objects, such as the taxes linked to an item.
    // Optional variable version is set to null.
    Long catalogVersion = null;
    catalogApi.retrieveCatalogObjectAsync(catalogObjectId, true, catalogVersion)
        .thenAccept(result -> {

          if (result.getObject() != null) {
            // Put the related objects into a map keyed by catalog object ID so we can look
            // them up faster.
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
          // Extract the actual exception
          ApiException e = (ApiException) exception.getCause();
          checkAndLogErrors(e.getErrors());
          return null;
        }).join();
  }

  /**
   * Log info about the retrieved catalog object based on it's type.
   *
   * @param catalogObject     the {@link CatalogObject} to log info about
   * @param relatedObjectsMap a map of related objects keyed by their IDs
   */
  void logCatalogObjectDetails(CatalogObject catalogObject,
      Map<String, CatalogObject> relatedObjectsMap) {
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

    String logMessage = "[" + discountObject.getType() + "] " + discount.getName()
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
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[")
        .append(itemObject.getType())
        .append("] ")
        .append(item.getName()).append("\n  ID: ")
        .append(itemObject.getId());

    // Append image ids if exists.
    if (itemObject.getItemData().getImageIds() != null) {
      logMessage.append("\n  Image IDs: ")
          .append(String.join(",", itemObject.getItemData().getImageIds()));
    }

    // Get the category from the related objects.
    logMessage.append("\n  Category: ");
    if (item.getCategoryId() == null) {
      logMessage.append("<uncategorized>");
    } else {
      CatalogObject categoryObject = relatedObjectsMap.get(item.getCategoryId());
      logMessage.append(categoryObject.getCategoryData().getName())
          .append(" (")
          .append(categoryObject.getId())
          .append(")");
    }

    // Add item variations.
    logMessage.append("\n  Item Variations:");
    for (CatalogObject variationObject : item.getVariations()) {
      CatalogItemVariation variation = variationObject.getItemVariationData();
      logMessage.append("\n    [")
          .append(variationObject.getType())
          .append("] ")
          .append(variation.getName())
          .append("\n      ID: ")
          .append(variationObject.getId())
          .append("\n      Price: ")
          .append(Moneys.format(variation.getPriceMoney()))
          .append("\n      SKU: ")
          .append(variation.getSku() == null ? "<none>" : variation.getSku());
    }

    // Add taxes.
    logMessage.append("\n  Taxes:");
    if (item.getTaxIds() == null || item.getTaxIds().isEmpty()) {
      logMessage.append(" <none>");
    } else {
      for (String taxId : item.getTaxIds()) {
        CatalogObject taxObject = relatedObjectsMap.get(taxId);
        logMessage.append("\n").append(getTaxLogMessage(taxObject, "    "));
      }
    }

    // Add modifier lists.
    logMessage.append("\n  Modifier Lists:");
    boolean hasModifierList = false;
    if (item.getModifierListInfo() != null) {
      for (CatalogItemModifierListInfo modifierListInfo : item.getModifierListInfo()) {
        // If a CatalogItemModifierListInfo is disabled, it means that the item was once
        // linked to
        // the modifier list, but has since been unlinked. We keep the
        // CatalogItemModifierListInfo
        // around so we can restore the item-specific modifier list configuration (ex.
        // pre-selected
        // modifiers) when the item is re-linked to the modifier list.
        Boolean modifierListEnabledForItem = modifierListInfo.getEnabled();
        if (modifierListEnabledForItem != null && modifierListEnabledForItem) {
          String modifierListId = modifierListInfo.getModifierListId();
          CatalogObject modifierListObject = relatedObjectsMap.get(modifierListId);
          CatalogModifierList modifierList = modifierListObject.getModifierListData();
          logMessage.append("\n    ")
              .append(modifierList.getName())
              .append(" (")
              .append(modifierListId)
              .append(")");
          hasModifierList = true;
        }
      }
    }
    if (!hasModifierList) {
      logMessage.append(" <none>");
    }

    // Add image. If the image_id exists, we can grab the image object from the
    // related objects map and log it.
    logMessage.append("\n  Image:");
    List<String> imageIds = itemObject.getItemData().getImageIds();
    if (imageIds != null) {
      for (String id : imageIds) {
        logMessage.append("\n")
            .append(getImageLogMessage(relatedObjectsMap.get(id), "    "));
      }
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

    logMessage.append("[")
        .append(itemVariationObject.getType())
        .append("] ")
        .append(itemVariation.getName())
        .append("\n  ID: ")
        .append(itemVariationObject.getId())
        .append("\n  Price: ")
        .append(Moneys.format(itemVariation.getPriceMoney()))
        .append("\n  SKU: ")
        .append(itemVariation.getSku() == null ? "<none>" : itemVariation.getSku());

    // Get the item from the related objects.
    CatalogObject itemObject = relatedObjectsMap.get(itemVariation.getItemId());
    CatalogItem item = itemObject.getItemData();
    logMessage.append("\n  Item: ")
        .append(item.getName())
        .append(" (")
        .append(itemObject.getId())
        .append(")");

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogModifierList}.
   */
  private void logModifierListDetails(CatalogObject modifierListObject) {
    CatalogModifierList modifierList = modifierListObject.getModifierListData();
    StringBuilder logMessage = new StringBuilder();

    logMessage.append("[")
        .append(modifierListObject.getType())
        .append("] ")
        .append(modifierList.getName())
        .append("\n  ID: ")
        .append(modifierListObject.getId())
        .append("\n  Selection Type: ")
        .append(modifierList.getSelectionType())
        .append("\n  Modifiers: ");

    // Add the modifiers.
    for (CatalogObject modifierObject : modifierList.getModifiers()) {
      CatalogModifier modifier = modifierObject.getModifierData();
      logMessage.append("\n    ")
          .append(modifier.getName())
          .append(" (")
          .append(modifierObject.getId())
          .append(")");
    }

    logger.info(logMessage.toString());
  }

  /**
   * Logs information about a {@link CatalogModifier}.
   */
  private void logModifierDetails(CatalogObject modifierObject, String prefix) {
    CatalogModifier modifier = modifierObject.getModifierData();
    StringBuilder logMessage = new StringBuilder(prefix);

    logMessage.append("[")
        .append(modifierObject.getType())
        .append("] ")
        .append(modifier.getName())
        .append("\n")
        .append(prefix)
        .append("  ID: ")
        .append(modifierObject.getId())
        .append("\n")
        .append(prefix)
        .append("  Price: ")
        .append(Moneys.format(modifier.getPriceMoney()));

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

    logMessage.append("[")
        .append(taxObject.getType())
        .append("] ")
        .append(tax.getName())
        .append("\n")
        .append(prefix)
        .append("  ID: ")
        .append(taxObject.getId())
        .append("\n")
        .append(prefix)
        .append("  Percentage: ")
        .append(tax.getPercentage())
        .append("\n")
        .append(prefix)
        .append("  Inclusion Type: ")
        .append(tax.getInclusionType());

    return logMessage.toString();
  }

  /**
   * Returns a log message describing a {@link CatalogImage}.
   */
  private String getImageLogMessage(CatalogObject imageObject, String prefix) {
    CatalogImage image = imageObject.getImageData();
    StringBuilder logMessage = new StringBuilder(prefix);

    logMessage.append("[")
        .append(imageObject.getType())
        .append("] ")
        .append(image.getName())
        .append("\n")
        .append(prefix)
        .append("  ID: ")
        .append(imageObject.getId())
        .append("\n")
        .append(prefix)
        .append("  Caption: ")
        .append(image.getCaption())
        .append("\n")
        .append(prefix)
        .append("  URL: ")
        .append(image.getUrl());

    return logMessage.toString();
  }
}
