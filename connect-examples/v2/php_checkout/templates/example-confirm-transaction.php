<?php

// Include the Square Connect API resources, store config, and helper functions
require_once '/local/path/to/sdk/SquareConnect/autoload.php';
require_once '/local/path/to/store-config.php' ;
require_once '/local/path/to/sc-helper-func.php' ;

// pull out the transaction ID returned by Square Checkout
$returnedTransactionId = $_GET["transactionId"] ;

// CONFIG FUNCTION: Create a Square Checkout API client if needed
initApiClient() ;

// Create a new API object to verify the transaction
$transactionClient = new \SquareConnect\Api\TransactionsApi($GLOBALS['API_CLIENT']) ;

// Ping the Transactions API endpoint for transaction details
try {

  // Get transaction details for this order from the Transactions API endpoint
  $apiResponse = $transactionClient->retrieveTransaction(
    $GLOBALS['LOCATION_ID'],
    $returnedTransactionId
  ) ;

} catch (Exception $e) {
  echo "The SquareConnect\Configuration object threw an exception while " .
       "calling TransactionsApi->retrieveTransaction: ",
       $e->getMessage(), PHP_EOL ;
  exit ;
}

// HELPER FUNCTION: verify the order information
$validTransaction = verifyTransaction($_GET, $apiResponse, $savedCheckoutId, $savedOrderTotal) ;

if (!$validTransaction) {

  /* add code to print the order confirmation or redirect to an existing
  confirmation page */

} else {
  // We only get here if there's something hinky with the confirmation
  // information (e.g., a mismatch between the checkout and order IDs) or
  // Square Checkout encountered an unexpected card error.

  /*
     add code to print an error message and provide contact information
     for follow-up
  */

  exit ;

}

?>
