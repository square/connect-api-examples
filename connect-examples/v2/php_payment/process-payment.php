<?php

// Note this line needs to change if you don't use Composer:
// require('square-php-sdk/autoload.php');
require 'vendor/autoload.php';
include 'utils/location-info.php';

use Dotenv\Dotenv;
use Square\SquareClient;
use Square\Models\Money;
use Square\Models\CreatePaymentRequest;
use Square\Exceptions\ApiException;
use Ramsey\Uuid\Uuid;

// dotenv is used to read from the '.env' file created for credentials
$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

if ($_SERVER['REQUEST_METHOD'] != 'POST') {
  error_log('Received a non-POST request');
  echo 'Request not allowed';
  http_response_code(405);
  return;
}

$json = file_get_contents('php://input');
$data = json_decode($json);
$token = $data->token;
$idempotencyKey = $data->idempotencyKey;

$square_client = new SquareClient([
  'accessToken' => getenv('SQUARE_ACCESS_TOKEN'),
  'environment' => getenv('ENVIRONMENT'),
  'userAgentDetail' => 'sample_app_php_payment', // Remove or replace this detail when building your own app
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

try {
  // Every payment you process with the SDK must have a unique idempotency key.
  // If you're unsure whether a particular payment succeeded, you can reattempt
  // it with the same idempotency key without worrying about double charging
  // the buyer.
  $create_payment_request = new CreatePaymentRequest($token, $idempotencyKey, $money);
  $create_payment_request->setLocationId($location_info->getId());

  $response = $payments_api->createPayment($create_payment_request);

  if ($response->isSuccess()) {
    echo json_encode($response->getResult());
  } else {
    echo json_encode(array('errors' => $response->getErrors()));
  }
} catch (ApiException $e) {
  echo json_encode(array('errors' => $e));
}
