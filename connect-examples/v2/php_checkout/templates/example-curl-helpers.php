<?php

/*******************************************************************************
 * Create constants for the Square Endpoint URLs
 ******************************************************************************/

// Location - used to query the location ID for the store
if (!defined(_SQ_ENDPOINT_LOCATION)) {
  define(
    "_SQ_ENDPOINT_LOCATION",
    "https://connect.squareup.com/v2/locations"
  ) ;
}

// Square Checkout - used to send JSON messages to Square Checkout
if (!defined(_SQ_ENDPOINT_CHECKOUT)) {
  define(
    "_SQ_ENDPOINT_CHECKOUT",
    "https://connect.squareup.com/v2/locations/{LOCATIONID}/checkouts"
  ) ;
}

/*******************************************************************************
 * @name: querySquareEndpoint
 * @param:
 *  $authzToken - string; a personal access, or OAuth, token that authorizes
 *  the request
 *  $type - string; indicates whether this is a POST or GET message
 *  $endpointUrl - string;
 *  $jsonData - array; the data/message to be encoded as JSON
 * @return: $jsonResponse - string; JSON response from the endpoint
 *
 * @desc:
 * Takes in an authorization token and messaage details, queries the endpoint
 * and returns the JSON data provided by the endpoint
 ******************************************************************************/
function querySquareEndpoint($authzToken, $type=POST, $endpointUrl, $jsonData=null) {

  /*
   * We can't connect to Square Endpoints w/o an authorization token;
   * This is either the OAuth token for a specific instance of this
   * application, or a Personal Access token from your merchant dashboard
   */

  if ($authzToken == null) { return null ; }

  // Initialize a curl handle and create an empty array for the request
  // header information
  $curl_handle = curl_init($endpointUrl) ;
  $request_headers = array();

  // We are sending/receiving JSON data
  $request_headers[] = "Content-Type: application/json" ;
  $request_headers[] = "Accept: application/json" ;

  // REQUIRED: Without a valid authorization token, Square Endpoints will reject
  // the request
  $request_headers[] = "Authorization: Bearer $authzToken" ;

  // Encode the JSON data and set the message length
  if ($jsonData != null) {
    $encodedData = json_encode($jsonData) ;
    curl_setopt($curl_handle, CURLOPT_POSTFIELDS, $encodedData);
    $request_headers[] = "Content-Length: " . strlen($encodedData) ;
  }

  curl_setopt($curl_handle, CURLOPT_CUSTOMREQUEST, $type) ;
  curl_setopt($curl_handle, CURLOPT_HTTPHEADER, $request_headers) ;
  curl_setopt($curl_handle, CURLOPT_RETURNTRANSFER, 1) ;

  // Save the response and close the curl handle
  $jsonResponse = curl_exec($curl_handle) ;
  curl_close($curl_handle) ;

  return $jsonResponse ;
}

/*******************************************************************************
 * @name: printSquareEndpointError
 * @param: $errorResponse - string; the JSON error message
 * @return: none
 *
 * @desc:
 * Takes in a JSON message representing an error message from a Square endpoint
 * and prints the error details to the browser
 ******************************************************************************/
function printSquareEndpointError($errorResponse) {

  if ($errorResponse == null) { return ; }
  foreach ($errorResponse as $errorDetail) {
    foreach ($errorDetail as $errorElement => $value) {
      echo($errorElement . ": " . $value . "<br>") ;
    }
  }
  echo("<br><hr>") ;
  return ;
}

/*******************************************************************************
 * @name: getLocationId
 * @param:
 *  $authzToken - string; a personal access, or OAuth, token that authorizes
 *  the request
 *  $storeName - string; store name (from the Merchant Dashboard) for which the
 *  transaction is being processed
 * @return: $location['id'] - the internal location ID for the named store
 *
 * @desc:
 * Takes in an authorization token and store name, queries the Location endpoint
 * and returns the location ID for that store. If no store name is provided, the
 * first viable location ID is returned instead.
 ******************************************************************************/
function getLocationId($authzToken = "", $storeName = "") {

  // Query the location endpoint using GET
  $jsonResponse = querySquareEndpoint($authzToken, "GET", _SQ_ENDPOINT_LOCATION) ;
  $responseArray = json_decode($jsonResponse, true) ;

  // If the location endpoint returned an error, print the details and
  // return null
  if ($responseArray["errors"]) {
    printSquareEndpointError($responseArray["errors"]) ;
    return null ;
  }

  // A given account may have multiple locations. If no store name was provided
  // just grab the first valid location ID. Otherwise, look for the ID
  // associated with the provided store
  foreach ($responseArray["locations"] as $location) {
    if ($storeName == "") { return $location['id'] ; }
    else if ($storeName == $location['name']) { return $location['id'] ; }
  }

  // If we get this far, the store name couldn't be found with this account so
  // we return null

  return null ;
}

/*******************************************************************************
 * @name: getCheckoutUrl
 * @param:
 *  $authzToken - string; a personal access, or OAuth, token that authorizes
 *  the request
 *  $orderData - array; the order details being sent to Checkout
 *  $checkoutURL - string; the Checkout endpoint for a specific location
 * @return: array; the checkout ID and checkout URL provided by Checkout
 *
 * @desc:
 * Takes in an authorization token, order data, and customized checkout endpoint
 * URL queries the endpoint, and returns an array with the the checkout ID (used
 * for transaction verification later) and checkout URL for customer redirect.
 ******************************************************************************/
function getCheckoutUrl($authzToken = "", $orderData = "", $checkoutURL) {

  // Query the Checkout endpoint
  $jsonResponse = querySquareEndpoint($authzToken, "POST", $checkoutURL, $orderData) ;

  // Decode the JSON data as an array
  $responseArray = json_decode($jsonResponse, true) ;

  // If there was an error, print the details and return nothing
  if ($responseArray["errors"]) {
    printSquareEndpointError($responseArray["errors"]) ;
    return null ;
  }

  // Return the checkout ID and customer redirect URL
  return array(
    'checkoutID' => $responseArray["checkout"]["id"],
    'checkoutUrl' => $responseArray["checkout"]["checkout_page_url"],
  ) ;
}

?>
