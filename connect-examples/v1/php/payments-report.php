<?php

# Demonstrates generating last year's payments report with the Square Connect API.
# Replace the value of the `$accessToken` variable below before running this script.
#
# This sample assumes all monetary amounts are in US dollars. You can alter the
# formatMoney function to display amounts in other currency formats.
#
# This sample requires the Unirest PHP library. See README.md in this directory for
# installation instructions.
#
# Results are rendered in a simple HTML pre block.


require 'vendor/autoload.php';

# Replace this value with your application's personal access token,
# available from your application dashboard (https://connect.squareup.com/apps)
$accessToken = 'REPLACE_ME';

# The base URL for every Connect API request
$connectHost = 'https://connect.squareup.com';

# Standard HTTP headers for every Connect API request
$requestHeaders = array (
  'Authorization' => 'Bearer ' . $accessToken,
  'Accept' => 'application/json',
  'Content-Type' => 'application/json'
);

# Helper function to convert cent-based money amounts to dollars and cents
function formatMoney($money) {
  return money_format('%+.2n', $money / 100);
}

# Obtains all of the business's location IDs. Each location has its own collection of payments.
function getLocationIds() {
  global $accessToken, $connectHost, $requestHeaders;
  $requestPath = $connectHost . '/v1/me/locations';
  $response = Unirest\Request::get($requestPath, $requestHeaders);
  $locations = $response->body;
  $locationIds = array();

  foreach ($locations as $location) {
    $locationIds[] = $location->id;
  }

  return $locationIds;
}

# Retrieves all of a merchant's payments from last year
function getPayments($location_ids) {
  global $accessToken, $connectHost, $requestHeaders;

  # Restrict the request to the last calendar year, eight hours behind UTC
  # Make sure to URL-encode all parameters
  $parameters = http_build_query(
  	array(
  	  'begin_time' => date("m-d-y",strtotime("last year January 1st")),
  	  'end_time'   => date("m-d-y",strtotime("this year January 1st"))
  	)
  );

  $payments = array();

  foreach ($location_ids as $location_id) {
    $requestPath = $connectHost . '/v1/' . $location_id . '/payments?' . $parameters;
    # Send a GET request to the List Payments endpoint
    $response = Unirest\Request::get($requestPath, $requestHeaders);
    if ($response->code != 200) {
      break;
    }
    $moreResults = true;

    while ($moreResults && $response->code == 200) {
      # Read the converted JSON body into the cumulative array of results
      $payments = array_merge($payments, $response->body);

      # Check whether pagination information is included in a response header, indicating more results
      if (array_key_exists('Link', $response->headers)) {
        $paginationHeader = $response->headers['Link'];
        if (strpos($paginationHeader, "rel='next'") !== false) {

          # Extract the next batch URL from the header.
          #
          # Pagination headers have the following format:
          # <https://connect.squareup.com/v1/MERCHANT_ID/payments?batch_token=BATCH_TOKEN>;rel='next'
          # This line extracts the URL from the angle brackets surrounding it.
          $requestPath = explode('>', explode('<', $paginationHeader)[1])[0];

          # Send a GET request to the List Payments endpoint
          $response = Unirest\Request::get($requestPath, $requestHeaders);
          if ($response->code != 200) {
            break;
          }
        } else {
          $moreResults = false;
        }
      } else {
        $moreResults = false;
      }
    }
  }

  if ($response->code != 200) {
    echo $response->raw_body;
    return null;
  }

  # Remove potential duplicate values from the list of payments
  $seenPaymentIds = array();
  $uniquePayments = array();
  foreach ($payments as $payment) {
  	if (array_key_exists($payment->id, $seenPaymentIds)) {
  	  continue;
  	}
  	$seenPaymentIds[$payment->id] = true;
  	array_push($uniquePayments, $payment);
  }

  return $uniquePayments;
}

# Prints a sales report based on an array of payments
function printSalesReport($payments) {

  # Variables for holding cumulative values of various monetary amounts
  $collectedMoney = $taxes = $tips = $discounts = $processingFees = 0;
  $returned_processingFees = $netMoney = $refunds = 0;

  # Add appropriate values to each cumulative variable
  foreach ($payments as $payment) {
    $collectedMoney  = $collectedMoney  + $payment->total_collected_money->amount;
    $taxes           = $taxes           + $payment->tax_money->amount;
    $tips            = $tips            + $payment->tip_money->amount;
    $discounts       = $discounts       + $payment->discount_money->amount;
    $processingFees  = $processingFees  + $payment->processing_fee_money->amount;
    $netMoney        = $netMoney        + $payment->net_total_money->amount;
    $refunds         = $refunds         + $payment->refunded_money->amount;


    # When a refund is applied to a credit card payment, Square returns to the merchant a percentage
    # of the processing fee corresponding to the refunded portion of the payment. This amount
    # is not currently returned by the Connect API, but we can calculate it as shown:

    # If a processing fee was applied to the payment AND some portion of the payment was refunded...
    if ($payment->processing_fee_money->amount < 0 and $payment->refunded_money->amount < 0) {

      # ...calculate the percentage of the payment that was refunded...
      $percentage_refunded = $payment->refunded_money->amount / (float)$payment->total_collected_money->amount;

      # ...and multiply that percentage by the original processing fee
      $returned_processingFees = $returned_processingFees + ($payment->processing_fee_money->amount * $percentage_refunded);
    }
  }

  # Calculate the amount of pre-tax, pre-tip money collected
  $basePurchases = $collectedMoney - $taxes - $tips;


  # Print a sales report similar to the Sales Summary in the merchant dashboard.
  echo '<pre>';
  echo '==SALES REPORT FOR ' . date("Y",strtotime("last year")) . '==' . '<br/>';
  echo 'Gross Sales:       ' . formatMoney($basePurchases - $discounts) . '<br/>';
  echo 'Discounts:         ' . formatMoney($discounts) . '<br/>';
  echo 'Net Sales:         ' . formatMoney($basePurchases) . '<br/>';
  echo 'Tax collected:     ' . formatMoney($taxes) . '<br/>';
  echo 'Tips collected:    ' . formatMoney($tips) . '<br/>';
  echo 'Total collected:   ' . formatMoney($basePurchases + $taxes + $tips) . '<br/>';
  echo 'Fees:              ' . formatMoney($processingFees) . '<br/>';
  echo 'Refunds:           ' . formatMoney($refunds) . '<br/>';
  echo 'Fees returned:     ' . formatMoney($returned_processingFees) . '<br/>';
  echo 'Net total:         ' . formatMoney($netMoney + $refunds + $returned_processingFees) . '<br/>';
  echo '</pre>';

}

# Call the functions defined above
$payments = getPayments(getLocationIds());
if ($payments != null) {
  printSalesReport($payments);
}

?>
