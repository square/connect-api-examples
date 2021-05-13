<?php
/**
 * Connect SDK Configuration File
 *
 * This template provides useful constants and skeleton functions that can make
 * working with Connect SDKs easier. You can find more information on how to
 * use these templates at [developer.squareup.com/docs]
 */

/**
 * Include the Square Connect SDK loader
 * Update the line below to reference the install path of the Connect SDK
 */
require_once 'vendor/autoload.php';


// {{{ constants

/**
* Your Square sandbox location ID
* Used to make test calls against the Square sandbox
* REPLACE_ME = a sandbox location ID from the application Locations tab
*/
if (!defined('_SQ_SANDBOX_LOCATION_ID')) {
  define('_SQ_SANDBOX_LOCATION_ID', "REPLACE_ME") ;
}

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
* Square sandbox domain for REST API calls
*/
if (!defined('_SQ_SANDBOX_DOMAIN')) {
  define('_SQ_SANDBOX_DOMAIN', "connect.squareupsandbox.com") ;
}


/**
* Your Square production location ID
* Used to make test calls against the Square production environment
* REPLACE_ME = a location ID from the application Locations tab
*/
if (!defined('_SQ_LOCATION_ID')) {
  define('_SQ_LOCATION_ID', "REPLACE_ME") ;
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
* Your Square production token
* Used to make test calls against the Square production environment
* REPLACE_ME = a production access token from the application Credentials tab
*/
if (!defined('_SQ_TOKEN')) {
  define('_SQ_TOKEN', "REPLACE_ME") ;
}

/**
* Square domain for REST API calls
*/
if (!defined('_SQ_DOMAIN')) {
    define('_SQ_DOMAIN', "connect.squareup.com") ;
}

if (!defined('_SQ_AUTHZ_URL')) {
  define('_SQ_AUTHZ_URL', "/oauth2/authorize") ;
}

// }}}

// {{{ credentials helper class

class CredentialManager {
  protected $defaultConnectClient = NULL;
  protected $defaultConnectSandboxClient = NULL;
  protected $useSandbox = TRUE;


public function setUseSandbox(bool $requestSandboxToken = TRUE) {
  $this->useSandbox = $requestSandboxToken;
}
  /**
   * Returns a Connect API client configured to hit the sandbox or production
   * environments.
   *
   * By default, the function below returns a sandbox Connect client for testing and
   * development.
   *
   * The first time this function is called, a new instance of either the sandbox
   * or production client is returned. On 2nd call with the same argument value, the
   * "singleton" instance of the client is returned.
   *
   * If the function is called with a different argument value (FALSE vs. TRUE),
   * a singleton production client is created and returned.
   *
   * @return string a valid Connect v2 client
   */
  public function getConnectClient() {

    if (is_null($this->defaultConnectSandboxClient) And $this->useSandbox) {
      // Create and configure a new API client object
      $defaultApiConfig = new \SquareConnect\Configuration();
      //Set Connect Endpoint to Sandbox environment
      // comment this setHost call out if you want to use the production environment
      $defaultApiConfig->setHost("https://" . _SQ_SANDBOX_DOMAIN);
      $defaultApiConfig->setAccessToken($this->getAccessToken());
      $this->defaultConnectSandboxClient =  new \SquareConnect\ApiClient($defaultApiConfig);
    }
    if (is_null($this->defaultConnectClient) And $this->useSandbox == FALSE) {
      // Create and configure a new API client object
      $defaultApiConfig = new \SquareConnect\Configuration();

      //Set Connect Endpoint to production environment
      $defaultApiConfig->setHost("https://" . _SQ_DOMAIN);
      $defaultApiConfig->setAccessToken($this->getAccessToken());
      $this->defaultConnectClient =  new \SquareConnect\ApiClient($defaultApiConfig);
    }

    if ($this->useSandbox) {
      return $this->defaultConnectSandboxClient;
    } else {
      return $this->defaultConnectClient;
    }
  }




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
  public function getAccessToken() {
    if ($this->useSandbox == TRUE) {
      return _SQ_SANDBOX_TOKEN;
    } else {
      return _SQ_TOKEN;
    }
  }

  /**
   * Returns an OAuth app secret for Square API calls
   *
   * By default, the function below returns sandbox credentials for testing and
   * development. For production, return the application secret assigned in the
   * OAuth page of your app registration with the dashboard set to Production Settings.
   *
   * @return string a valid app secret
   */
  public function getAppSecret() {
    if ($this->useSandbox == TRUE) {
      return _SQ_SANDBOX_APP_SECRET;
    } else {
      return _SQ_APP_SECRET;
    }
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
  public function getApplicationId() {
    if ($this->useSandbox == TRUE) {
      return _SQ_SANDBOX_APP_ID;
    } else {
      return _SQ_APP_ID;
    }
  }


  /**
   * Returns a location ID for Square API calls
   *
   * By default, the function below returns a hardcoded sandbox location ID from the
   * Application Dashboard. For production, update the function implementation
   * to fetch a valid location ID programmtically.
   *
   * @return string a valid location ID
   */
  public function getLocationId() {

    if ($this->useSandbox == TRUE) {
      // Replace the string with a sandbox location ID from the Application Dashboard
      return _SQ_SANDBOX_LOCATION_ID ;
    } else {
      // Replace the string with a production location ID from the Application Dashboard
      return _SQ_LOCATION_ID ;
    }
  }
}
// }}}

?>
