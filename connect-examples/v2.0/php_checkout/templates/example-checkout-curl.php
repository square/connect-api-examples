<?php
// Include the Square Connect API resources, store config, and helper functions
require_once '/local/path/to/sdk/SquareConnect/autoload.php';
require_once '/local/path/to/curl-helpers.php' ;

// Configure your online store information
$GLOBALS['ACCESS_TOKEN'] = "{{YOUR ACCESS TOKEN}}" ;
$GLOBALS['STORE_NAME'] = "{{STORE NAME FROM SQUARE DASHBOARD}}" ;
$GLOBALS['LOCATION_ID'] = "" ; // We'll set this in a moment

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

// HELPER FUNCTION: Repackage the order information as an array
$orderArray = getOrderAsArray(/*Order ID or object from cart workflow */) ;

// CURL HELPER FUNCTION: Get the location ID for the store
$GLOBALS['LOCATION_ID'] = getMyLocationId($authzToken, $GLOBALS['STORE_NAME']) ;
if ($GLOBALS['LOCATION_ID'] == null) {
  echo "The location ID for '" . $GLOBALS['STORE_NAME'] . "' not found." ;
  return ;
}

// Build the custom Checkout endpoint for this store
$myCheckoutEndpoint = str_replace (
  "{LOCATIONID}",
  $GLOBALS['LOCATION_ID'],
  _SQ_ENDPOINT_CHECKOUT) ;

// CURL HELPER FUNCTION: Get the checkout ID and checkout URL
$checkoutResponse = getCheckoutUrl(
  $authzToken,
  $orderArray,
  $myCheckoutEndpoint) ;

// Grab the redirect url and checkout ID sent back
$checkoutUrl = $checkoutResponse['checkoutUrl'] ;
$checkoutID =  $checkoutResponse['checkoutID'] ;

// HELPER FUNCTION: save the checkoutID so it can be used to confirm the
// transaction after payment processing
saveCheckoutID($orderArray['order']['reference_id'], $checkoutID) ;

// Redirect the customer to Square Checkout
header("Location: $checkoutUrl") ;

?>
