package com.squareup.catalog.demo.api;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.resources.CatalogObject;
import com.squareup.catalog.service.ListLocationsResponse;
import java.io.IOException;

/**
 * Utility class used to retrieve location information.
 */
public class LocationApi extends BaseApi {

  public LocationApi(String baseUrl, String accessToken, Logger logger) {
    super(baseUrl + "locations", accessToken, logger);
  }

  /**
   * Deletes multiple {@link CatalogObject CatalogObjects}.
   */
  public ListLocationsResponse listLocations()
      throws IOException {
    return get("", ListLocationsResponse.class);
  }
}
