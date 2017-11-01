<?php

// Include the Square Connect API resources
require_once '/local/path/to/sdk/SquareConnect/autoload.php';

// API initialization - Configure your online store information
$GLOBALS['ACCESS_TOKEN'] = "{{YOUR ACCESS TOKEN}}";
$GLOBALS['STORE_NAME'] = "{{STORE NAME FROM SQUARE DASHBOARD}}";
$GLOBALS['LOCATION_ID'] = ""; // We'll set this in a moment
$GLOBALS['API_CLIENT_SET'] = false;

// Sanity check that all the needed configuration elements are set
if ($GLOBALS['STORE_NAME'] == null) {
  print(
    "[ERROR] STORE NAME NOT SET. " .
    "Please set a valid store name to use Square Checkout."
  );
  exit;
} else if ($GLOBALS['ACCESS_TOKEN'] == null) {
  print(
    "[ERROR] ACCESS TOKEN NOT SET. Please set a valid authorization token " .
    "(Personal Access Token or OAuth Token) to use Square Checkout."
  );
  exit;
}

/*******************************************************************************
 * @name: initApiClient
 * @param: none - uses the GLOBAL array settings to initialize the client
 * @return: none - adds the ApiClient object to the GLOBAL array
 *
 * @desc:
 * Initializes a Square Connect API client, loads the appropriate
 * location ID and returns an Api object ready for communicating with Square's
 * various API endpoints
 ******************************************************************************/
function initApiClient() {

  // If we've already set the API client, we don't need to do it again
  if ($GLOBALS['API_CLIENT_SET']) { return; }

  // Create and configure a new Configuration object
  $configuration = new \SquareConnect\Configuration();
  \SquareConnect\Configuration::getDefaultConfiguration()->setAccessToken($GLOBALS['ACCESS_TOKEN']);

  // Create a LocationsApi client to load the location ID
  $locationsApi = new \SquareConnect\Api\LocationsApi();

  // Grab the location key for the configured store
  try {

    $apiResponse = $locationsApi->listLocations()->getLocations();

    // There may be more than one location associated with the account (e.g,. a
    // brick-and-mortar store and an online store), so we need to run through
    // the response and pull the right location ID
    foreach ($apiResponse['locations'] as $location) {
      if ($GLOBALS['STORE_NAME'] == $location->getName()) {
        $GLOBALS['LOCATION_ID'] = $location['id'];
        if (!in_array('CREDIT_CARD_PROCESSING', $location->getCapabilities())) {
          print(
            "[ERROR] LOCATION  " . $GLOBALS['STORE_NAME'] .
            " can't processs payments"
          );
          exit();
        }
      }
    }

    if ($GLOBALS['LOCATION_ID'] == null) {
      print(
        "[ERROR] LOCATION ID NOT SET. A location ID for " .
        $GLOBALS['STORE_NAME'] . " could not be found"
      );
      exit;
    }

    $GLOBALS['API_CLIENT_SET'] = true;

  } catch (Exception $e) {

    // Display the exception details, clear out the client since it couldn't
    // be properly initialized, and exit
    echo "The SquareConnect\Configuration object threw an exception while " .
         "calling LocationApi->listLocations: ", $e->getMessage(), PHP_EOL;
    $GLOBALS['API_CLIENT'] = null;
    exit;
  }
}
?>
