package com.squareup.catalog.demo.example;

import com.squareup.connect.ApiException;
import com.squareup.connect.api.CatalogApi;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogItemVariation;
import com.squareup.connect.models.CatalogItemVariation.PricingTypeEnum;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogObject.TypeEnum;
import com.squareup.connect.models.CatalogObjectBatch;
import com.squareup.connect.models.ItemVariationLocationOverrides;
import com.squareup.connect.models.ListLocationsResponse;
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
  public void execute(CatalogApi catalogApi, LocationsApi locationsApi) throws ApiException {
    // Get the list of locations for this merchant.
    logger.info("Retrieving locations");
    ListLocationsResponse locationsResponse = locationsApi.listLocations();
    if (checkAndLogErrors(locationsResponse.getErrors())) {
      return;
    }
    logger.info("Retrieved " + locationsResponse.getLocations().size() + " locations");

    // Choose the first location to apply an override.
    String locationId = locationsResponse.getLocations().get(0).getId();

    /*
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
     */
    BatchUpsertCatalogObjectsRequest request = new BatchUpsertCatalogObjectsRequest()
        .idempotencyKey(UUID.randomUUID().toString())
        .batches(singletonList(new CatalogObjectBatch()
            .objects(singletonList(
                item("#SODA", "Soda", null,
                    new CatalogObject()
                        .type(TypeEnum.ITEM_VARIATION)
                        .id("#SODA-REGULAR")
                        .presentAtAllLocations(true)
                        .itemVariationData(new CatalogItemVariation()
                            .name("Regular")
                            .pricingType(PricingTypeEnum.FIXED_PRICING)
                            .priceMoney(usd(200)) // Global price is $2.00
                            .locationOverrides(singletonList(
                                // Apply an override to the first location.
                                new ItemVariationLocationOverrides()
                                    .locationId(locationId)
                                    .priceMoney(usd(250))
                                    .pricingType(
                                        ItemVariationLocationOverrides.PricingTypeEnum.FIXED_PRICING)))))))));

    /*
     * Post the batch upsert to insert the new item.
     *
     * Use the BatchUpsertCatalogObjectsRequest object we just created to
     * upsert the new CatalogObjects to the catalog associated with the
     * access token included on the command line.
     */
    logger.info("Creating new item with location");
    BatchUpsertCatalogObjectsResponse response = catalogApi.batchUpsertCatalogObjects(request);

    // Check for errors.
    if (checkAndLogErrors(response.getErrors())) {
      return;
    }

    /*
     * Otherwise, grab the name and object ID of the CatalogItem that was
     * just created and print them to the screen.
     **/
    CatalogObject newItem = response.getObjects().get(0);
    logger.info("Created item " + newItem.getItemData().getName() + " (" + newItem.getId()+ ")");
  }
}
