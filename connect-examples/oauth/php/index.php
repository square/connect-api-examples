<?php
# Please review the Square LICENSE (/connect-examples/oauth/php/LICENSE) before using this code.

# This file simply serves the link that merchants click to authorize your application.
# When authorization completes, a notification is sent to your redirect URL, which should
# be handled in callback.php.
require 'vendor/autoload.php';
require_once('sq_config.php');
$dotenv = Dotenv\Dotenv::create(__DIR__);
$dotenv->load();

$appId = ($_ENV["USE_PROD"] == 'true') ? $_ENV["PROD_APP_ID"]
                                        : $_ENV["SANDBOX_APP_ID"];

// echo ($_ENV["USE_PROD"] == 'true') ? "<a href=\"https://connect.squareup.com/oauth2/authorize?client_id=$appId\">Click here</a> to authorize the application."
//                                    : "<a href=\"https://connect.squareupsandbox.com/oauth2/authorize?client_id=$appId\">Click here</a> to authorize the application.";

  // Set the permissions
  $permissions = urlencode(
    "PAYMENTS_WRITE " .
    "PAYMENTS_READ"
 );

 $useSandbox = TRUE;
 $credentialManager = new CredentialManager();
// Display the OAuth link
// Use _SQ_DOMAIN if you want to authorize in the production environment
$connectV2Client = $credentialManager->getConnectClient($useSandbox);
$appId = $credentialManager->getApplicationId($useSandbox);
phpinfo();
echo ($useSandbox == FALSE) ? "<a href=\"https://"._SQ_DOMAIN._SQ_AUTHZ_URL
."?client_id=$appId&scope=$permissions\">Click here</a> to authorize the application."
: "<a href=\"https://"._SQ_SANDBOX_DOMAIN._SQ_AUTHZ_URL
."?client_id=$appId&scope=$permissions\">Click here</a> to authorize the application in the sandbox.";

?>
