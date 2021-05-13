<?php

// Note this line needs to change if you don't use Composer:
// require('square-php-sdk/autoload.php');
require 'vendor/autoload.php';
include 'utils/location-info.php';

use Dotenv\Dotenv;
use Square\Models\Money;
use Square\Models\CreatePaymentRequest;
use Square\SquareClient;
use Ramsey\Uuid\Uuid;

// dotenv is used to read from the '.env' file created for credentials
$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// Helps ensure this code has been reached via form submission
if ($_SERVER['REQUEST_METHOD'] != 'POST') {
  error_log('Received a non-POST request');
  echo 'Request not allowed';
  http_response_code(405);
  return;
}

$json = file_get_contents('php://input');
$data = json_decode($json);
$token = $data->token;

$square_client = new SquareClient([
  'accessToken' => $access_token,  
  'environment' => getenv('ENVIRONMENT')
]);

$payments_api = $square_client->getPaymentsApi();

// To learn more about splitting payments with additional recipients,
// see the Payments API documentation on our [developer site]
// (https://developer.squareup.com/docs/payments-api/overview).

$money = new Money();
// Monetary amounts are specified in the smallest unit of the applicable currency.
// This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
$money->setAmount(100);
// Set currency to the currency for the location
$money->setCurrency($location_info->getCurrency());

// Every payment you process with the SDK must have a unique idempotency key.
// If you're unsure whether a particular payment succeeded, you can reattempt
// it with the same idempotency key without worrying about double charging
// the buyer.
$create_payment_request = new CreatePaymentRequest($token, Uuid::uuid4(), $money);

// The SDK throws an exception if a Connect endpoint responds with anything besides
// a 200-level HTTP code. This block catches any exceptions that occur from the request.
#header('Content-type: application/json');
$response = $payments_api->createPayment($create_payment_request);
// If there was an error with the request we will
// print them to the browser screen here
if ($response->isSuccess()) {
  echo json_encode($response->getResult());
} else {
  echo json_encode($response->getErrors());
}
