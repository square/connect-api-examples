<?php
/*
|--------------------------------------------------------------------------
| OAuth Authorization Link Example
|--------------------------------------------------------------------------
|
| This file simply serves the link that merchants click to authorize your
| application. When authorization completes, a notification is sent to
| your redirect URL, which should be handled in callback.php.
*/

use Square\Environment;

require 'vendor/autoload.php';

$dotenv = Dotenv\Dotenv::create(__DIR__);
$dotenv->load();

$upper_case_environment = strtoupper(getenv('ENVIRONMENT'));

$appId = getenv($upper_case_environment.'_APP_ID');
$baseUrl = (getenv('ENVIRONMENT') === Environment::PRODUCTION)
    ? 'https://connect.squareup.com'
    : 'https://connect.squareupsandbox.com';

// Show a link to the user to start the OAuth flow
echo sprintf('<a href="%s/oauth2/authorize?client_id=%s">Click here</a> to authorize the application.',
    $baseUrl,
    $appId,
);