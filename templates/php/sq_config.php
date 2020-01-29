<?php
/**
 * Connect SDK Configuration File
 *
 * This template provides useful constants and skeleton functions that can make
 * working with Connect SDKs easier. You can find more information on how to
 * use these templates at [docs.connect.squareup.com]
 */

/**
 * Include the Square Connect SDK loader
 * Update the line below to reference the install path of the Connect SDK
 */
require_once 'local/path/to/autoload.php';


// {{{ constants

/**
* Your Square sandbox token
* Used to make test calls against the Square sandbox
* REPLACE_ME = a sandbox access token from the application Credentials tab
*/
if (!defined('_SQ_SANDBOX_TOKEN')) {
    define('_SQ_SANDBOX_TOKEN', "REPLACE_ME") ;
}

/**
* Your Square sandbox application ID
* Used to make test calls against the Square sandbox
* REPLACE_ME = a sandbox application ID from the application Credentials tab
*/
if (!defined('_SQ_SANDBOX_APP_ID')) {
    define('_SQ_SANDBOX_APP_ID', "REPLACE_ME") ;
}

/**
* Your Square SANDBOX environment application secret
* REPLACE_ME = an application secret from the SANDBOX environment application
* OAuth tab
*/
if (!defined('_SQ_SANDBOX_APP_SECRET')) {
  define('_SQ_SANDBOX_APP_SECRET', "REPLACE_ME") ;
}

/**
* Your Square application ID
* REPLACE_ME = an application ID from the application Credentials tab
*/
if (!defined('_SQ_APP_ID')) {
    define('_SQ_APP_ID', "REPLACE_ME") ;
}

/**
* Your Square application secret
* REPLACE_ME = an application secrete from the application OAuth tab
*/
if (!defined('_SQ_APP_SECRET')) {
    define('_SQ_APP_SECRET', "REPLACE_ME") ;
}

/**
* Square domain for REST API calls
*/
if (!defined('_SQ_DOMAIN')) {
    define('_SQ_DOMAIN', "connect.squareup.com") ;
}

/**
* Square sandbox domain for REST API calls
*/
if (!defined('_SQ_SANDBOX_DOMAIN')) {
  define('_SQ_SANDBOX_DOMAIN', "connect.squareupsandbox.com") ;
}


// }}}

// {{{ functions

/**
 * Returns an access token for Square API calls
 *
 * By default, the function below returns sandbox credentials for testing and
 * development.For production, replace the function implementation with a valid
 * OAuth flow to generate OAuth credentials. See the OAuth Setup Guide for more
 * information on implementing OAuth.
 *
 * @return string a valid access token
 */
function getAccessToken() {

  $accessToken = _SQ_SANDBOX_TOKEN;

  return $accessToken;
}

/**
 * Returns an application Id for Square API calls
 *
 * By default, the function below returns a sandbox application ID for testing
 * and development. For production, replace the function implementation with a
 * valid production credential.
 *
 * @return string a valid application ID token
 */
function getApplicationId() {

  $accessToken = _SQ_SANDBOX_APP_ID;

  return $accessToken;
}


/**
 * Returns a location ID for Square API calls
 *
 * By default, the function below returns a hardcoded location ID from the
 * Application Dashboard. For production, update the function implementation
 * to fetch a valid location ID programmtically.
 *
 * @return string a valid location ID
 */
function getLocationId() {

  // Replace the string with a sandbox location ID from the Application Dashboard
  return "REPLACE_ME" ;

}

// }}}

?>
