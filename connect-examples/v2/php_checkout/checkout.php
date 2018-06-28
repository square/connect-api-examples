<?php
  require 'vendor/autoload.php';
  // Change to true if production, false if sandbox
  define("IS_PROD", false);

  // dotenv is used to read from the '.env' file created
  $dotenv = new Dotenv\Dotenv(__DIR__);
  $dotenv->load();

  $access_token = (IS_PROD)  ?  $_ENV["PROD_ACCESS_TOKEN"]
                             :  $_ENV["SANDBOX_ACCESS_TOKEN"];
  $location_id =  (IS_PROD)  ?  $_ENV["PROD_LOCATION_ID"]
                             :  $_ENV["SANDBOX_LOCATION_ID"];
  // setup authorization
  \SquareConnect\Configuration::getDefaultConfiguration()->setAccessToken($access_token);

  // make sure we actually are on a POST with an amount
  if (isset($_POST["amount"])) {
    try {
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
                // multiply by 100 due to it being in cents
                "amount" => intval($_POST["amount"] * 100),
                "currency" => "USD"
              ]
            ]]
          ]
        ]
      );
      $response = $checkout_api->createCheckout($location_id, $request_body);
    } catch (Exception $e) {
      // if an error occurs, output the message
      echo $e->getMessage();
      exit();
    }
    // this redirects to the Square hosted checkout page
    header("Location: ".$response->getCheckout()->getCheckoutPageUrl());
    exit();
  }
?>
