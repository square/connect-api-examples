<?php
  session_start();
?>
<link rel="stylesheet" href="public/style.css" type="text/css">
<?php
require 'vendor/autoload.php';
require_once('messages.php');

use Square\Exceptions\ApiException;
use Square\SquareClient;
use Square\Environment;
use Square\Models\ObtainTokenRequest;
use Dotenv\Dotenv;

$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// The obtainOAuthToken function shows you how to obtain a OAuth access token
// with the OAuth API with the authorization code returned to OAuth callback.
function obtainOAuthToken($authorizationCode) {
  // Initialize Square PHP SDK OAuth API client.
  $environment = getenv('SQ_ENVIRONMENT') == "sandbox" ? Environment::SANDBOX : Environment::PRODUCTION;
  $apiClient = new SquareClient([
    'environment' => $environment,
    'userAgentDetail' => "sample_app_oauth_php" // Remove or replace this detail when building your own app
  ]);
  $oauthApi = $apiClient->getOAuthApi();
  // Initialize the request parameters for the obtainToken request.
  $body_grantType = 'authorization_code';
  $body = new ObtainTokenRequest(
    getenv('SQ_APPLICATION_ID'),
    $body_grantType
  );
  $body->setCode($authorizationCode);
  $body->setClientSecret(getenv('SQ_APPLICATION_SECRET'));

  // Call obtainToken endpoint to get the OAuth tokens.
  try {
      $response = $oauthApi->obtainToken($body);

      if ($response->isError()) {
        $code = $response->getErrors()[0]->getCode();
        $category = $response->getErrors()[0]->getCategory();
        $detail = $response->getErrors()[0]->getDetail();

        throw new Exception("Error Processing Request: obtainToken failed!\n" . $code . "\n" . $category . "\n" . $detail, 1);
      }
  } catch (ApiException $e) {
      error_log($e->getMessage());
      error_log($e->getHttpResponse()->getRawBody());
      throw new Exception("Error Processing Request: obtainToken failed!\n" . $e->getMessage() . "\n" . $e->getHttpResponse()->getRawBody(), 1);
  }

  // Extract the tokens from the response.
  $accessToken = $response->getResult()->getAccessToken();
  $refreshToken = $response->getResult()->getRefreshToken();
  $expiresAt = $response->getResult()->getExpiresAt();
  $merchantId = $response->getResult()->getMerchantId();

  // Return the tokens along with the expiry date/time and merchant ID.
  return array($accessToken, $refreshToken, $expiresAt, $merchantId);
}

// Handle the response.
try {
    // Verify the state to protect against cross-site request forgery.
    if ($_SESSION["auth_state"] !== $_GET['state']) {
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