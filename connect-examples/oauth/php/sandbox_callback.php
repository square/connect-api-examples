<?php
require_once('sandbox_config.php');
require_once('sandbox_messages.php');

// The obtainOAuthToken function shows you how to obtain a OAuth access token
// with the OAuth API with the authorization code returned to OAuth callback.
function obtainOAuthToken($authorizationCode) {
  // Initialize SquareConnect OAuth API client.
  $apiConfig = new \SquareConnect\Configuration();
  $apiConfig->setHost(_SQ_SANDBOX_BASEURL);
  $apiClient = new \SquareConnect\ApiClient($apiConfig);
  $oauthApi = new SquareConnect\Api\OAuthApi($apiClient);

  // Initialize the request parameters for the obtainToken request.
  $body = new \SquareConnect\Model\ObtainTokenRequest();
  $body->setClientId(_SQ_SANDBOX_APP_ID);
  $body->setClientSecret(_SQ_SANDBOX_APP_SECRET);
  $body->setGrantType('authorization_code');
  $body->setCode($authorizationCode);

  // Call obtainToken endpoint to get the OAuth tokens.
  try {
      $response = $oauthApi->obtainToken($body);
  } catch (ApiException $e) {
      error_log($e->getMessage());
      error_log($e->getResponseBody());
      throw new Exception("Error Processing Request: obtainToken failed!\n" . $e->getMessage() . "\n" . $e->getResponseBody(), 1);
  }

  // Extract the tokens from the response.
  $accessToken = $response->getAccessToken();
  $refreshToken = $response->getRefreshToken();
  $expiresAt = $response->getExpiresAt();
  $merchantId = $response->getMerchantId();

  // Return the tokens along with the expiry date/time and merchant ID.
  return array($accessToken, $refreshToken, $expiresAt, $merchantId);
}

// Handle the response.
try {
    // Verify the state to protect against cross-site request forgery.
    if ($_COOKIE["Auth_State"] !== $_GET['state']) {
      displayStateError();
      return;
    }

    // When the response_type is "code", the seller clicked Allow
    // and the authorization page returned the auth tokens.
    if ("code" === $_GET["response_type"]) {
      // Get the authorization code and use it to call the obtainOAuthToken wrapper function.
      $authorizationCode = $_GET['code'];
      list($accessToken, $refreshToken, $expiresAt, $merchantId) = obtainOAuthToken($authorizationCode);
      // Because we want to keep things simple and we're using Sandbox, 
      // we call a function that writes the tokens to the page so we can easily copy and use them directly.
      // In production, you should never write tokens to the page. You should encrypt the tokens and handle them securely.
      writeTokensOnSuccess($accessToken, $refreshToken, $expiresAt, $merchantId);
    }
    elseif ($_GET['error']) {
      // Check to see if the seller clicked the Deny button and handle it as a special case.
      if(("access_denied" === $_GET["error"]) && ("user_denied" === $_GET["error_description"])) {
        displayError("Authorization denied", "You chose to deny access to the app.");
      }
      // Display the error and description for all other errors.
      else {
        displayError($_GET["error"], $_GET["error_description"]);
      }
    }
    else {
      // No recognizable parameters were returned.
      displayError("Unknown parameters", "Expected parameters were not returned");
    }
} catch (Exception $e) {
  // If the obtainToken call fails, you'll fall through to here.
  displayError("Exception", $e->getMessage()); 
}

?>