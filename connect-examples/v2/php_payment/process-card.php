<?php

// Note this line needs to change if you don't use Composer:
// require('square-php-sdk/autoload.php');
require 'vendor/autoload.php';

use Dotenv\Dotenv;
use Square\Models\Money;
use Square\Models\CreatePaymentRequest;
use Square\Exceptions\ApiException;
use Square\SquareClient;

// dotenv is used to read from the '.env' file created for credentials
$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// Pulled from the .env file and upper cased e.g. SANDBOX, PRODUCTION.
$upper_case_environment = strtoupper(getenv('ENVIRONMENT'));

// The access token to use in all Connect API requests.
// Set your environment as *sandbox* if you're just testing things out.
$access_token =  getenv($upper_case_environment.'_ACCESS_TOKEN');    

// Initialize the Square client.
$client = new SquareClient([
  'accessToken' => $access_token,  
  'environment' => getenv('ENVIRONMENT')
]);

// Helps ensure this code has been reached via form submission
if ($_SERVER['REQUEST_METHOD'] != 'POST') {
  error_log('Received a non-POST request');
  echo 'Request not allowed';
  http_response_code(405);
  return;
}

// Fail if the card form didn't send a value for `nonce` to the server
$nonce = $_POST['nonce'];
if (is_null($nonce)) {
  echo 'Invalid card data';
  http_response_code(422);
  return;
}

$payments_api = $client->getPaymentsApi();

// To learn more about splitting payments with additional recipients,
// see the Payments API documentation on our [developer site]
// (https://developer.squareup.com/docs/payments-api/overview).

$money = new Money();
  // Monetary amounts are specified in the smallest unit of the applicable currency.
  // This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
$money->setAmount(100);
$money->setCurrency('USD');

  // Every payment you process with the SDK must have a unique idempotency key.
  // If you're unsure whether a particular payment succeeded, you can reattempt
  // it with the same idempotency key without worrying about double charging
  // the buyer.
$create_payment_request = new CreatePaymentRequest($nonce, uniqid(), $money);

// The SDK throws an exception if a Connect endpoint responds with anything besides
// a 200-level HTTP code. This block catches any exceptions that occur from the request.
try {
  $response = $payments_api->createPayment($create_payment_request);
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
  echo '<pre>';
  print_r($response);
  echo '</pre>';
} catch (ApiException $e) {
  echo 'Caught exception!<br/>';
  echo('<strong>Response body:</strong><br/>');
  echo '<pre>'; var_dump($e->getResponseBody()); echo '</pre>';
  echo '<br/><strong>Context:</strong><br/>';
  echo '<pre>'; var_dump($e->getContext()); echo '</pre>';
  exit();
}
