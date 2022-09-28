<?php

// Note this line needs to change if you don't use Composer:
// require('square-php-sdk/autoload.php');
require 'vendor/autoload.php';

use Dotenv\Dotenv;
use Square\Models\CreateOrderRequest;
use Square\Models\CreateCheckoutRequest;
use Square\Models\Order;
use Square\Models\OrderLineItem;
use Square\Models\Money;
use Square\Exceptions\ApiException;
use Square\SquareClient;

// dotenv is used to read from the '.env' file created
$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// Use the environment and the key name to get the appropriate values from the .env file.
$access_token = getenv('SQUARE_ACCESS_TOKEN');    
$location_id =  getenv('SQUARE_LOCATION_ID');

// Initialize the authorization for Square
$client = new SquareClient([
  'accessToken' => $access_token,
  'environment' => getenv('ENVIRONMENT'),
  'userAgentDetail' => 'sample_app_php_checkout', // Remove or replace this detail when building your own app
]);

// make sure we actually are on a POST with an amount
// This example assumes the order information is retrieved and hard coded
// You can find different ways to retrieve order information and fill in the following lineItems object.
try {
  $checkout_api = $client->getCheckoutApi();

  // Monetary amounts are specified in the smallest unit of the applicable currency.
  // This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
  
  // Set currency to the currency for the location
  $currency = $client->getLocationsApi()->retrieveLocation(getenv('SQUARE_LOCATION_ID'))->getResult()->getLocation()->getCurrency();
  $money_A = new Money();
  $money_A->setCurrency($currency);
  $money_A->setAmount(500);

  $item_A = new OrderLineItem(1);
  $item_A->setName('Test Item A');
  $item_A->setBasePriceMoney($money_A);

  $money_B = new Money();
  $money_B->setCurrency($currency);
  $money_B->setAmount(1000);
  
  $item_B = new OrderLineItem(3);
  $item_B->setName('Test Item B');
  $item_B->setBasePriceMoney($money_B);

  // Create a new order and add the line items as necessary.
  $order = new Order($location_id);
  $order->setLineItems([$item_A, $item_B]);

  $create_order_request = new CreateOrderRequest();
  $create_order_request->setOrder($order);

  // Similar to payments you must have a unique idempotency key.
  $checkout_request = new CreateCheckoutRequest(uniqid(), $create_order_request);
  // Set a custom redirect URL, otherwise a default Square confirmation page will be used
  $checkout_request->setRedirectUrl('http://localhost:8888/confirmation.php');

  $response = $checkout_api->createCheckout($location_id, $checkout_request);
} catch (ApiException $e) {
  // If an error occurs, output the message
  echo 'Caught exception!<br/>';
  echo '<strong>Response body:</strong><br/>';
  echo '<pre>'; var_dump($e->getResponseBody()); echo '</pre>';
  echo '<br/><strong>Context:</strong><br/>';
  echo '<pre>'; var_dump($e->getContext()); echo '</pre>';
  exit();
}

// If there was an error with the request we will
// print them to the browser screen here
if ($response->isError()) {
  echo 'Api response has Errors';
  $errors = $response->getErrors();
  echo '<ul>';
  foreach ($errors as $error) {
      echo '<li>❌ ' . $error->getDetail() . '</li>';
  }
  echo '</ul>';
  exit();
}


// This redirects to the Square hosted checkout page
header('Location: '.$response->getResult()->getCheckout()->getCheckoutPageUrl());
