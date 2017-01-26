<?php

// Include the Square Connect API resources
require_once '/local/path/to/sdk/SquareConnect/autoload.php';

// API initialization - Configure your online store information
$GLOBALS['API_HOST'] = 'connect.squareup.com' ;
$GLOBALS['ACCESS_TOKEN'] = "{{YOUR ACCESS TOKEN}}" ;
$GLOBALS['STORE_NAME'] = "{{STORE NAME FROM SQUARE DASHBOARD}}" ;
$GLOBALS['LOCATION_ID'] = "" ; // We'll set this in a moment
$GLOBALS['API_CLIENT_SET'] = false ;

// Sanity check that all the needed configuration elements are set
if ($GLOBALS['STORE_NAME'] == null) {
  print(
    "[ERROR] STORE NAME NOT SET. " .
    "Please set a valid store name to use Square Checkout."
  ) ;
  exit ;
} else if ($GLOBALS['ACCESS_TOKEN'] == null) {
  print(
    "[ERROR] ACCESS TOKEN NOT SET. Please set a valid authorization token " .
    "(Personal Access Token or OAuth Token) to use Square Checkout."
  ) ;
  exit ;
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
  if ($GLOBALS['API_CLIENT_SET']) { return ; }

  // Create and configure a new Configuration object
  $configuration = new \SquareConnect\Configuration() ;
  $configuration->setHost("https://{$GLOBALS['API_HOST']}") ;
  $configuration->setApiKey('Authorization', $GLOBALS['ACCESS_TOKEN']) ;
  $GLOBALS['API_CLIENT'] = new \SquareConnect\ApiClient($configuration) ;

  // Create a LocationsApi client to load the location ID
  $locClient = new \SquareConnect\Api\LocationsApi($GLOBALS['API_CLIENT']) ;

  // Grab the location key for the configured store
  try {

    $apiResponse = $locClient->listLocations($GLOBALS['ACCESS_TOKEN'])->getLocations() ;

    // There may be more than one location assocaited with the account (e.g,. a
    // brick-and-mortar store and an online store), so we need to run through
    // the response and pull the right location ID
    foreach ($apiResponse['locations'] as $location) {

      if ($GLOBALS['STORE_NAME'] == $location['name']) {
        $GLOBALS['LOCATION_ID'] = $location['id'] ;
      }
    }

    if ($GLOBALS['LOCATION_ID'] == null) {
      print(
        "[ERROR] LOCATION ID NOT SET. A location ID for " .
        $GLOBALS['STORE_NAME'] . " could not be found"
      ) ;
      exit ;
    }

    $GLOBALS['API_CLIENT_SET'] = true ;

  } catch (Exception $e) {

    // Display the exception details, clear out the client since it couldn't
    // be properly initialized, and exit
    echo "The SquareConnect\Configuration object threw an exception while " .
         "calling LocationApi->listLocations: ", $e->getMessage(), PHP_EOL ;
    $GLOBALS['API_CLIENT'] = null ;
    exit ;
  }
}
?>
