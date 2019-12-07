<?php
# This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow:
#
# 1. A merchant clicks the authorization link served by the root path (http://localhost:8000/)
# 2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
#    is already signed in to Square, and if the merchant has already authorized your application,
#    the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
# 3. Square sends a request to your application's Redirect URL
#    (which should be set to http://localhost:8000/callback.php on your application dashboard)
# 4. The server extracts the authorization code provided in Square's request and passes it
#    along to the Obtain Token endpoint.
# 5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
#    to the Connect API.
#

require 'vendor/autoload.php';

  /**
  * Square domain for REST API calls
  */
  if (!defined(_SQ_DOMAIN)) {
    define('_SQ_DOMAIN', "https://connect.squareup.com") ;
  }

  /**
  * Square sandbox domain for REST API calls
  */
  if (!defined(_SQ_SANDBOX_DOMAIN)) {
  define('_SQ_SANDBOX_DOMAIN', "https://connect.squareupsandbox.com") ;
  }

# Your application's ID and secret, available from your application dashboard
$applicationId = 'REPLACE_ME';
$applicationSecret = 'REPLACE_ME';

$api_config = new \SquareConnect\Configuration();
$api_config->setHost(_SQ_SANDBOX_DOMAIN);
$api_client = new \SquareConnect\ApiClient($api_config);
$oauth_api = new SquareConnect\Api\OAuthApi($api_client);

# Serves requests from Square to your application's redirect URL
# Note that you need to set your application's Redirect URL to
# http://localhost:8000/callback.php from your application dashboard
function callback() {
  global $oauth_api, $applicationId, $applicationSecret;

  # Extract the returned authorization code from the URL
  $authorizationCode = $_GET['code'];
  if ($authorizationCode) {

    # Create Obtain Token requests
    $body = new \SquareConnect\Model\ObtainTokenRequest();
    $body->setClientId($applicationId);
    $body->setClientSecret($applicationSecret);
    $body->setCode($authorizationCode);
    $body->setGrantType('authorization_code');

    try {
      $response = $oauth_api->obtainToken($body);

      # Extract the returned access token from the ObtainTokenResposne
      $accessToken = $response->getAccessToken();
      if ($accessToken != null) {

        # Here, instead of printing the access token, your application server should store it securely
        # and use it in subsequent requests to the Connect API on behalf of the merchant.
        error_log('Access token: ' . $accessToken);
        error_log('Authorization succeeded!');

        # The response from the Obtain Token endpoint did not include an access token. Something went wrong.
      } else {
        error_log('Code exchange failed!');
      }
    } catch (SquareConnect\ApiException $e) {
      error_log($e->getMessage());
    }

    # The request to the Redirect URL did not include an authorization code. Something went wrong.
  } else {
    error_log('Authorization failed!');
  }
}

# Execute the callback
callback();

?>
