<?php

# This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow:
#
# 1. A merchant clicks the authorization link served by the root path (http://localhost:8000/)
# 2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
#    is already signed in to Square, and if the merchant has already authorized your application,
#    the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
# 3. Square sends a request to your application's Redirect URL
#    (which should be set to http://localhost:8080/callback.php on your application dashboard)
# 4. The server extracts the authorization code provided in Square's request and passes it
#    along to the Obtain Token endpoint.
# 5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
#    to the Connect API.
#
# This sample requires the Unirest PHP library. Follow the instructions in README.md
# to install it.

require 'vendor/autoload.php';

# Your application's ID and secret, available from your application dashboard
$applicationId = 'REPLACE_ME';
$applicationSecret = 'REPLACE_ME';

$connectHost = 'https://connect.squareup.com';

# Headers to provide to OAuth API endpoints
$oauthRequestHeaders = array (
  'Authorization' => 'Client ' . $applicationSecret,
  'Accept' => 'application/json',
  'Content-Type' => 'application/json'
);

# Serves requests from Square to your application's redirect URL
# Note that you need to set your application's Redirect URL to
# http://localhost:8000/callback.php from your application dashboard
function callback() {
  global $connectHost, $oauthRequestHeaders, $applicationId, $applicationSecret;

  # Extract the returned authorization code from the URL
  $authorizationCode = $_GET['code'];
  if ($authorizationCode) {

    # Provide the code in a request to the Obtain Token endpoint
    $oauthRequestBody = array(
      'client_id' => $applicationId,
      'client_secret' => $applicationSecret,
      'code' => $authorizationCode
    );
    $response = Unirest\Request::post($connectHost . '/oauth2/token', $oauthRequestHeaders, json_encode($oauthRequestBody));

    # Extract the returned access token from the response body
    if (property_exists($response->body, 'access_token')) {

      # Here, instead of printing the access token, your application server should store it securely
      # and use it in subsequent requests to the Connect API on behalf of the merchant.
      error_log('Access token: ' . $response->body->access_token);
      error_log('Authorization succeeded!');

      # The response from the Obtain Token endpoint did not include an access token. Something went wrong.
    } else {
      error_log('Code exchange failed!');
    }

    # The request to the Redirect URL did not include an authorization code. Something went wrong.
  } else {
    error_log('Authorization failed!');
  }
}

# Execute the callback
callback();

?>
