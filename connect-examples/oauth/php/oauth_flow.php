<?php

/**
 * OAuth callback endpoint
 *
 * This file declares the endpoint that accepts the redirected POST from the
 * Square authorization endpoint
 *
 * Please review the Square LICENSE (/connect-examples/oauth/php/LICENSE) before using this code.
 */


require_once('sq_config.php');



function getAuthzCode($authorizationResponse) {

  // Extract the returned authorization code from the URL
  $authorizationCode = $authorizationResponse['code'];

  # If there is no authorization code, log the error and throw an exception
  if (!$authorizationCode) {
    error_log('Authorization failed!');
    throw new \Exception("Error Processing Request: Authorization failed!", 1);
  }

  return $authorizationCode ;
}

function getOAuthToken($authorizationCode) {

  $credentialManager = new CredentialManager();
  $connectV2Client = $credentialManager->getConnectClient(TRUE);
  # Your application's ID and secret, available from your application dashboard
  $applicationId = $credentialManager->getApplicationId(TRUE);
  $applicationSecret = $credentialManager->getAppSecret(TRUE);

  // Create an OAuth API client
  $oauthApi = new SquareConnect\Api\OAuthApi($connectV2Client);
  $body = new \SquareConnect\Model\ObtainTokenRequest();

  // Set the POST body
  $body->setClientId($applicationId);
  $body->setClientSecret($applicationSecret);
  $body->setGrantType("authorization_code");
  $body->setCode($authorizationCode);

  try {
      $result = $oauthApi->obtainToken($body);
  } catch (Exception $e) {
      error_log  ($e->getMessage());
      throw new Exception("Error Processing Request: Token exchange failed!", 1);
  }

  $accessToken = $result->getAccessToken();
  $refreshToken = $result->getRefreshToken();

  // Return both the access token and refresh token
  return array($accessToken, $refreshToken);
}
# Call the function
try {
  $authorizationCode = getAuthzCode($_GET);
  list($accessToken, $refreshToken) = getOAuthToken($authorizationCode);

  # Use the OAuth access token and refresh token. For testing, we will simply write it to the log
  error_log('OAuth access token: ' . $accessToken);
  error_log('OAuth refresh token: ' . $refreshToken);
  error_log('Authorization succeeded!');
} catch (Exception $e) {
  echo $e->getMessage();
  error_log($e->getMessage());
}



?>

