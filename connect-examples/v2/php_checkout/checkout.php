<?php
  require 'vendor/autoload.php';

  // dotenv is used to read from the '.env' file created
  $dotenv = Dotenv\Dotenv::create(__DIR__);
  $dotenv->load();

  $access_token = ($_ENV["USE_PROD"] == 'true')  ?  $_ENV["PROD_ACCESS_TOKEN"]
                                                 :  $_ENV["SANDBOX_ACCESS_TOKEN"];
  $location_id =  ($_ENV["USE_PROD"] == 'true')  ?  $_ENV["PROD_LOCATION_ID"]
                                                 :  $_ENV["SANDBOX_LOCATION_ID"];
  # Set 'Host' url to switch between sandbox env and production env
  # sandbox: https://connect.squareupsandbox.com
  # production: https://connect.squareup.com
  $host_url = ($_ENV["USE_PROD"] == 'true')  ?  "https://connect.squareup.com"
                                             :  "https://connect.squareupsandbox.com";

  $api_config = new \SquareConnect\Configuration();
  $api_config->setHost($host_url);
  # Initialize the authorization for Square
  $api_config->setAccessToken($access_token);
  $api_client = new \SquareConnect\ApiClient($api_config);

  // make sure we actually are on a POST with an amount
  // This example assumes the order information is retrieved and hard coded
  // You can find different ways to retrieve order information and fill in the following lineItems object.
  try {
    $checkout_api = new \SquareConnect\Api\CheckoutApi($api_client);
    $request_body = new \SquareConnect\Model\CreateCheckoutRequest(
      [
        "idempotency_key" => uniqid(),
        "order" => [
          "order" => [
            "location_id" => $location_id,
            "line_items" => [
            [
              "name" => "Test Item A",
              "quantity" => "1",
              "base_price_money" => [
                "amount" => 500,
                "currency" => "USD"
              ]
            ],[
              "name" => "Test Item B",
              "quantity" => "3",
              "base_price_money" => [
                "amount" => 1000,
                "currency" => "USD"
              ]
            ]]
          ]
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
?>
