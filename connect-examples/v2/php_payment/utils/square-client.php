<?php

// Note this line needs to change if you don't use Composer:
// require('square-php-sdk/autoload.php');
require 'vendor/autoload.php';

use Dotenv\Dotenv;
use Square\SquareClient;

// dotenv is used to read from the '.env' file created for credentials
$dotenv = Dotenv::create(dirname(__DIR__));
$dotenv->load();

// The access token to use in all Connect API requests.
// Set your environment as *sandbox* if you're just testing things out.


class SqClient {
  // Initialize the Square client.
  var $currency;
  var $country;
  var $square_client;
  
  function __construct() {
    $access_token =  getenv('SQUARE_ACCESS_TOKEN');

    $this->square_client = new SquareClient([
      'accessToken' => $access_token,  
      'environment' => getenv('ENVIRONMENT')
    ]);
    $location = $this->square_client->getLocationsApi()->retrieveLocation(getenv('SQUARE_LOCATION_ID'))->getResult()->getLocation();
    $this->currency = $location->getCurrency();
    $this->country = $location->getCountry();  
  }

  public function getCurrency() {
    return $this->currency;
  }

  public function getCountry() {
    return $this->country;
  }

  public function getSquareClient() {
    return $this->square_client;
  }
}

$square_client = new SqClient();

?>