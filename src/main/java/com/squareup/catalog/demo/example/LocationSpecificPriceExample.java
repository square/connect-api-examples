package com.squareup.catalog.demo.example;

import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.catalog.demo.util.Moneys.createMoneyObject;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.squareup.square.exceptions.ApiException;
import java.util.List;
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
 * This example creates a CatalogItemVariation with a location-specific price that overrides its
 * predefined global price. It then uploads the new objects and prints the CatalogItem name and ID
 * to the screen.
 */
public class LocationSpecificPriceExample extends Example {

  public LocationSpecificPriceExample(Logger logger) {
    super("location_specific_price",
        "Creates an item with a location-specific price. Must have at least 2 locations to run this example.",
        logger);
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) {
    // Get the list of locations for this merchant.
    logger.info("Retrieving locations");
    locationsApi.listLocationsAsync().thenCompose(result -> {
      logger.info("Retrieved " + result.getLocations().size() + " locations");
      // If there is only one location, this example cannot be executed.
      if (result.getLocations().size() < 2) {
        throw new IllegalArgumentException(
            "You must have at least 2 locations to run this example.");
      }

      // Choose the first location to apply an override.
      String locationId = result.getLocations().get(1).getId();

      /*
       * Build the request to create the new item.
       *
       * This function call creates a BatchUpsertCatalogObjectsRequest object
       * (request) populated with two new CatalogObjects:
       * - a CatalogItem called "Soda" with ID "#SODA"
       * - a CatalogItemVariation child called "Regular" with ID "#SODA-REGULAR"
       * and a global price of $2.00 and assigns a location-specific price override so a Regular
       * Soda will be $2.50 at that location instead of the normal $2.00 price found everywhere else
       * (maybe don't shop there!? :P)
       *
       * Note: this call only *creates* the new objects and packages them for upsert.
       * Nothing has been uploaded to the server at this point.
       */

      List<ItemVariationLocationOverrides> overrides = singletonList(
          new ItemVariationLocationOverrides.Builder()
              .locationId(locationId)
              .priceMoney(createMoneyObject(250))
              .pricingType("FIXED_PRICING")
              .build());

      CatalogItemVariation itemVariationData = new CatalogItemVariation.Builder().name("Regular")
          .pricingType("FIXED_PRICING")
          .priceMoney(createMoneyObject(200))
          .locationOverrides(overrides)
          .build();

      CatalogObject itemVariation =
          new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
              "#SODA-REGULAR")
              .presentAtAllLocations(true)
              .itemVariationData(itemVariationData)
              .build();

      List<CatalogObjectBatch> batches = singletonList(
          new CatalogObjectBatch.Builder(
              singletonList(
                  item("#SODA", "Soda", null, itemVariation)
              )
          ).build());

      BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder(
          UUID.randomUUID().toString(),
          batches)
          .build();

      /*
       * Post the batch upsert to insert the new item.
       *
       * Use the BatchUpsertCatalogObjectsRequest object we just created to upsert the
       * new CatalogObjects to the catalog associated with the access token included
       * on the command line.
       */
      logger.info("Creating new item with override at location id " + locationId);
      return catalogApi.batchUpsertCatalogObjectsAsync(request);
    }).thenAccept(upsertResponse -> {
      /*
       * Grab the name and object ID of the CatalogItem that was just
       * created and print them to the screen.
       **/
      CatalogObject newItem = upsertResponse.getObjects().get(0);
      logger.info(
          "Created item " + newItem.getItemData().getName() + " (" + newItem.getId() + ")");
    }).exceptionally(exception -> {
      // Extract the actual exception
      if (exception.getCause() instanceof ApiException) {
        ApiException e = (ApiException) exception.getCause();
        checkAndLogErrors(e.getErrors());
      } else {
        logger.error(exception.getMessage());
      }
      return null;
    }).join();
  }

  @Override
  public void cleanup(CatalogApi catalogApi, LocationsApi locationsApi) {
    cleanCatalogObjectsByName(catalogApi, CatalogObjectTypes.ITEM.toString(), "Soda");
  }
}
