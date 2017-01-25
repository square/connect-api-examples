<?php
// Include the Square Connect API resources, store config, and helper functions
require_once '/local/path/to/sdk/SquareConnect/autoload.php';
require_once '/local/path/to/store-config.php' ;
require_once '/local/path/to/sc-helper-func.php' ;

// HELPER FUNCTION: Repackage the order information as an array
$orderArray = getOrderAsArray(/*Order ID or object from cart workflow */) ;

// CONFIG FUNCTION: Create a Square Checkout API client if needed
initApiClient() ;

// Create a new API object to send order information to Square Checkout
$checkoutClient = new \SquareConnect\Api\CheckoutApi($GLOBALS['API_CLIENT']) ;

try {

  // Send the order array to Square Checkout
  $apiResponse = $checkoutClient->createCheckout(
    $GLOBALS['LOCATION_ID'],
    $orderArray
  ) ;

  // Grab the redirect url and checkout ID sent back
  $checkoutUrl = $apiResponse['checkout']['checkout_page_url'] ;
  $checkoutID =  $apiResponse['checkout']['id'] ;

  // HELPER FUNCTION: save the checkoutID so it can be used to confirm the
  // transaction after payment processing
  saveCheckoutId($orderArray['order']['reference_id'],$checkoutID) ;

} catch (Exception $e) {
  echo "The SquareConnect\Configuration object threw an exception while " .
       "calling CheckoutApi->createCheckout: ", $e->getMessage(), PHP_EOL ;
  exit ;
}

// Redirect the customer to Square Checkout
header("Location: $checkoutUrl") ;

?>
