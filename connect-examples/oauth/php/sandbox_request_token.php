<?php
require_once('sandbox_config.php');

// Specify the permissions and url encode the spaced separated list.
$permissions = urlencode(
                  "ITEMS_READ " . 
                  "MERCHANT_PROFILE_READ " .
                  "PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS " .
                  "PAYMENTS_WRITE " .
                  "PAYMENTS_READ"
               );

// Set the Auth_State cookie with a random md5 string to protect against cross-site request forgery.
// Auth_State will expire in 300 seconds (5 mins) after the page is loaded.
$state = md5(time());
setcookie("Auth_State", $state, time()+ 300);

// Display the OAuth link.
echo '<p><a href="' . _SQ_SANDBOX_BASEURL . "/oauth2/authorize" .
     '?client_id=' . _SQ_SANDBOX_APP_ID .
     '&scope=' . $permissions .
     '&state=' . $state .
     '">SANDBOX: Authorize this application</a></p>';
?>