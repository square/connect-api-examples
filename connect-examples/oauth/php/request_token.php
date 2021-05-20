<?php
  session_start();
  if (empty($_SESSION['auth_state'])) {
    $_SESSION['auth_state'] = bin2hex(random_bytes(32));
  }
?>
<link rel="stylesheet" href="public/style.css" type="text/css">
<meta name="viewport" content="width=device-width">
<?php
require 'vendor/autoload.php';
require_once('messages.php');

use Dotenv\Dotenv;

$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// Specify the permissions and url encode the spaced separated list.
$permissions = urlencode(
                  "ITEMS_READ " . 
                  "MERCHANT_PROFILE_READ " .
                  "PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS " .
                  "PAYMENTS_WRITE " .
                  "PAYMENTS_READ"
               );

// Set the Auth_State cookie with a random md5 string to protect against cross-site request forgery.
// Auth_State will expire in 60 seconds (1 mins) after the page is loaded.
$application_id = getenv('SQ_APPLICATION_ID');    
$environment =  getenv('SQ_ENVIRONMENT');

if ($environment == "sandbox") {
  $base_url = "https://connect.squareupsandbox.com";
} else if ($environment == "production") {
  $base_url = "https://connect.squareup.com";
} else {
  displayError("Unsupported SQ_ENVIRONMENT", "Set the SQ_ENVIRONMENT variable in .env file to 'sandbox' or 'production'.");
}

// Display the OAuth link.
echo '
<div class="wrapper">
  <a class="btn"
   href="' . $base_url . "/oauth2/authorize" .
     '?client_id=' . $application_id .
     '&scope=' . $permissions .
     '&state=' . $_SESSION['auth_state'] .'">
     <strong>Authorize</strong>
  </a>
</div>';
?>
