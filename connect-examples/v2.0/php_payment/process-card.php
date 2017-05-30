<?php

require 'vendor/autoload.php';

# Replace these values. You probably want to start with your Sandbox credentials
# to start: https://docs.connect.squareup.com/articles/using-sandbox/

# The ID of the business location to associate processed payments with.
# If you're testing things out, use a sandbox location ID.
#
# See [Retrieve your business's locations](https://docs.connect.squareup.com/articles/getting-started/#retrievemerchantprofile)
# for an easy way to get your business's location IDs.
$location_id = 'REPLACE_ME';

# The access token to use in all Connect API requests. Use your *sandbox* access
# token if you're just testing things out.
$access_token = 'REPLACE_ME';

# Helps ensure this code has been reached via form submission
if ($_SERVER['REQUEST_METHOD'] != 'POST') {
  error_log("Received a non-POST request");
  echo "Request not allowed";
  http_response_code(405);
  return;
}

# Fail if the card form didn't send a value for `nonce` to the server
$nonce = $_POST['nonce'];
if (is_null($nonce)) {
  echo "Invalid card data";
  http_response_code(422);
  return;
}

$transaction_api = new \SquareConnect\Api\TransactionApi();

$request_body = array (

  "card_nonce" => $nonce,

  # Monetary amounts are specified in the smallest unit of the applicable currency.
  # This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
  "amount_money" => array (
    "amount" => 100,
    "currency" => "USD"
  ),

  # Every payment you process with the SDK must have a unique idempotency key.
  # If you're unsure whether a particular payment succeeded, you can reattempt
  # it with the same idempotency key without worrying about double charging
  # the buyer.
  "idempotency_key" => uniqid()
);

# The SDK throws an exception if a Connect endpoint responds with anything besides
# a 200-level HTTP code. This block catches any exceptions that occur from the request.
try {
  $result = $transaction_api->charge($access_token, $location_id, $request_body);
  echo "<pre>";
  print_r($result);
  echo "</pre>";
} catch (\SquareConnect\ApiException $e) {
  echo "Caught exception!<br/>";
  print_r("<strong>Response body:</strong><br/>");
  echo "<pre>"; var_dump($e->getResponseBody()); echo "</pre>";
  echo "<br/><strong>Response headers:</strong><br/>";
  echo "<pre>"; var_dump($e->getResponseHeaders()); echo "</pre>";
}
