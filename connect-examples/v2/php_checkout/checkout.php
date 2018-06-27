<?php
  require 'vendor/autoload.php';

  // dotenv is used to read from the '.env' file created
  $dotenv = new Dotenv\Dotenv(__DIR__);
  $dotenv->load();

  $access_token = $_ENV["ACCESS_TOKEN"];
  $location_id = $_ENV["LOCATION_ID"];
  // setup authorization
  \SquareConnect\Configuration::getDefaultConfiguration()->setAccessToken($access_token);

  // make sure we actually are on a POST with an amount
  if (isset($_POST["amount"])) {
    $checkout_api = new \SquareConnect\Api\CheckoutApi();
    $request_body = new \SquareConnect\Model\CreateCheckoutRequest(
      [
        "idempotency_key" => uniqid(),
        "order" => [
          "line_items" => [
          [
            "name" => "Test Payment",
            "quantity" => "1",
            "base_price_money" => [
              "amount" => intval($_POST["amount"] * 100),
              "currency" => "USD"
            ]
          ]]
        ]
      ]
    );
    $response = $checkout_api->createCheckout($location_id, $request_body);
    header("Location: ".$response->getCheckout()->getCheckoutPageUrl());
  }
?>
