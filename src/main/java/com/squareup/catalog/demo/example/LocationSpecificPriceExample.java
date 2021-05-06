package com.squareup.catalog.demo.example;

import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.catalog.demo.util.Moneys.createMoneyObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import java.util.UUID;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.api.CatalogApi;
import com.squareup.square.api.LocationsApi;
import com.squareup.square.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogObjectBatch;
import com.squareup.square.models.ItemVariationLocationOverrides;

/**
 * This example creates a CatalogItemVariation with a location-specific price
 * that overrides its predefined global price. It then uploads the new objects
 * and prints the CatalogItem name and ID to the screen.
 */
public class LocationSpecificPriceExample extends Example {

  public LocationSpecificPriceExample(Logger logger) {
    super("location_specific_price", "Create an item with a location-specific price", logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {
    // Get the list of locations for this merchant.
    logger.info("Retrieving locations");
    locationsApi.listLocationsAsync().thenAccept(result -> {
      if (checkAndLogErrors(result.getErrors())) {
        return;
      }
      logger.info("Retrieved " + result.getLocations().size() + " locations");

      // Choose the first location to apply an override.
      String locationId = result.getLocations().get(1).getId();

      /*
       * Build the request to create the new item.
       *
       * This function call creates a BatchUpsertCatalogObjectsRequest object
       * (request) populated with two new CatalogObjects: - a CatalogItem called
       * "Soda" with ID "#SODA" - a CatalogItemVariation child called "Regular" with
       * ID "#SODA-REGULAR" and a global price of $2.00 and assigns a
       * location-specific price override so a Regular Soda will be $2.50 at that
       * location instead of the normal $2.00 price found everywhere else (maybe don't
       * shop there!? :P)
       *
       * Note: this call only *creates* the new objects and packages them for upsert.
       * Nothing has been uploaded to the server at this point.
       */
      CatalogItemVariation itemVariationData = new CatalogItemVariation.Builder().name("Regular")
          .pricingType("FIXED_PRICING").priceMoney(createMoneyObject(200))
          .locationOverrides(singletonList(new ItemVariationLocationOverrides.Builder().locationId(locationId)
              .priceMoney(createMoneyObject(250)).pricingType("FIXED_PRICING").build()))
          .build();

      BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder(
          UUID.randomUUID().toString(),
          singletonList(
              new CatalogObjectBatch.Builder(
                  asList(item("#SODA", "Soda", null,
                      new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(), "#SODA-REGULAR")
                          .presentAtAllLocations(true).itemVariationData(itemVariationData).build()))).build()))
                              .build();

      /*
       * Post the batch upsert to insert the new item.
       *
       * Use the BatchUpsertCatalogObjectsRequest object we just created to upsert the
       * new CatalogObjects to the catalog associated with the access token included
       * on the command line.
       */
      logger.info("Creating new item with override at location id " + locationId);

      catalogApi.batchUpsertCatalogObjectsAsync(request).thenAccept(upsertResponse -> {
        if (checkAndLogErrors(upsertResponse.getErrors())) {
          return;
        }

        /*
         * Otherwise, grab the name and object ID of the CatalogItem that was just
         * created and print them to the screen.
         **/
        CatalogObject newItem = upsertResponse.getObjects().get(0);
        logger.info("Created item " + newItem.getItemData().getName() + " (" + newItem.getId() + ")");
      }).join();
    }).exceptionally(exception -> {
      // Log exception, return null.
      logger.error(exception.getMessage());
      return null;
    });
  }

  @Override
  public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi) {
    cleanCatalogObjectsByName(catalogApi, CatalogObjectTypes.ITEM.toString(), "Soda");
  }
}
