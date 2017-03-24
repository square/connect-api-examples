package com.squareup.catalog.demo.example;

import com.squareup.catalog.demo.api.CatalogApi;
import com.squareup.catalog.demo.api.LocationApi;
import com.squareup.catalog.resources.CatalogItemVariation;
import com.squareup.catalog.resources.CatalogObject;
import com.squareup.catalog.resources.CatalogObjectType;
import com.squareup.catalog.resources.CatalogPricingType;
import com.squareup.catalog.resources.ItemVariationLocationOverrides;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsRequest;
import com.squareup.catalog.service.BatchUpsertCatalogObjectsResponse;
import com.squareup.catalog.service.CatalogObjectBatch;
import com.squareup.catalog.service.ListLocationsResponse;
import java.io.IOException;
import java.util.UUID;

import static com.squareup.catalog.demo.util.CatalogObjects.item;
import static com.squareup.catalog.demo.util.Moneys.usd;
import static java.util.Collections.singletonList;

/**
 * This example creates a CatalogItemVariation with a location-specific price
 * that overrides its predefined global price. It then uploads the new objects
 * and prints the CatalogItem name and ID to the screen.
 **/
public class LocationSpecificPriceExample extends Example {

  public LocationSpecificPriceExample() {
    super("location_specific_price", "Create an item with a location-specific price");
  }

  @Override
  public void execute(CatalogApi catalogApi, LocationApi locationApi) throws IOException {
    // Get the list of locations for this merchant.
    logger.info("Retrieving locations");
    ListLocationsResponse locationsResponse = locationApi.listLocations();
    if (locationsResponse == null) {
      return;
    }
    logger.info("Retrieved " + locationsResponse.locations.size() + " locations");

    // Choose the first location to apply an override.
    String locationId = locationsResponse.locations.get(0).id;

    /**
     * Build the request to create the new item.
     *
     * This function call creates a BatchUpsertCatalogObjectsRequest object
     * (request) populated with two new CatalogObjects:
     *   - a CatalogItem called "Soda" with ID "#SODA"
     *   - a CatalogItemVariation child called "Regular" with ID "#SODA-REGULAR"
     *     and a global price of $2.00
     * and assigns a location-specific price override so a Regular Soda will be
     * $2.50 at that location instead of the normal $2.00 price found everywhere
     * else (maybe don't shop there!? :P)
     *
     * Note: this call only *creates* the new objects and packages them for
     * upsert. Nothing has been uploaded to the server at this point.
     **/
    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest.Builder()
        .idempotency_key(UUID.randomUUID().toString())
        .batches(singletonList(new CatalogObjectBatch.Builder()
            .objects(singletonList(
                item("#SODA", "Soda", null,
                    new CatalogObject.Builder()
                        .type(CatalogObjectType.ITEM_VARIATION)
                        .id("#SODA-REGULAR")
                        .present_at_all_locations(true)
                        .item_variation_data(new CatalogItemVariation.Builder()
                            .name("Regular")
                            .pricing_type(CatalogPricingType.FIXED_PRICING)
                            .price_money(usd(200)) // Global price is $2.00
                            .location_overrides(singletonList(
                                // Apply an override to the first location.
                                new ItemVariationLocationOverrides.Builder()
                                    .location_id(locationId)
                                    .price_money(usd(250))
                                    .pricing_type(CatalogPricingType.FIXED_PRICING)
                                    .build()))
                            .build())
                        .build()
                )))
            .build()))
        .build();

    /**
     * Post the batch upsert to insert the new item.
     *
     * Use the BatchUpsertCatalogObjectsRequest object we just created to
     * upsert the new CatalogObjects to the catalog associated with the
     * access token included on the command line.
     **/
    logger.info("Creating new item with location");
    BatchUpsertCatalogObjectsResponse response = catalogApi.batchUpsert(request);

    // If the response is null, we already logged errors.
    if (response == null) {
      return;
    }

    /*
     * Otherwise, grab the name and object ID of the CatalogItem that was
     * just created and print them to the screen.
     **/
    CatalogObject newItem = response.objects.get(0);
    logger.info("Created item " + newItem.item_data.name + " (" + newItem.id + ")");
  }
}
