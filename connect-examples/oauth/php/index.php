<?php
# This file simply serves the link that merchants click to authorize your application.
# When authorization completes, a notification is sent to your redirect URL, which should
# be handled in callback.php.
require 'vendor/autoload.php';
$dotenv = Dotenv\Dotenv::create(__DIR__);
$dotenv->load();

$appId = ($_ENV["USE_PROD"] == 'true') ? $_ENV["PROD_APP_ID"]
                                        : $_ENV["SANDBOX_APP_ID"];

echo ($_ENV["USE_PROD"] == 'true') ? "<a href=\"https://connect.squareup.com/oauth2/authorize?client_id=$appId\">Click here</a> to authorize the application."
                                    : "<a href=\"https://connect.squareupsandbox.com/oauth2/authorize?client_id=$appId\">Click here</a> to authorize the application.";
?>
