<?php
require 'vendor/autoload.php';

use Dotenv\Dotenv;
use Square\SquareClient;
use Square\Exceptions\ApiException;

// dotenv is used to read from the '.env' file created
$dotenv = Dotenv::create(__DIR__);
$dotenv->load();

// Use the environment and the key name to get the appropriate values from the .env file.
$access_token = getenv('SQUARE_ACCESS_TOKEN');
$location_id =  getenv('SQUARE_LOCATION_ID');

// Initialize the authorization for Square
$client = new SquareClient([
  'accessToken' => $access_token,
  'environment' => getenv('ENVIRONMENT')
]);

$transaction_id = $_GET["transactionId"];

try {
  $orders_api = $client->getOrdersApi();
  $response = $orders_api->retrieveOrder($transaction_id);
} catch (ApiException $e) {
  // If an error occurs, output the message
  echo 'Caught exception!<br/>';
  echo '<strong>Response body:</strong><br/>';
  echo '<pre>';
  var_dump($e->getResponseBody());
  echo '</pre>';
  echo '<br/><strong>Context:</strong><br/>';
  echo '<pre>';
  var_dump($e->getContext());
  echo '</pre>';
  exit();
}

// If there was an error with the request we will
// print them to the browser screen here
if ($response->isError()) {
  echo 'Api response has Errors';
  $errors = $response->getErrors();
  echo '<ul>';
  foreach ($errors as $error) {
    echo '<li>❌ ' . $error->getDetail() . '</li>';
  }
  echo '</ul>';
  exit();
} else {
  $order = $response->getResult()->getOrder();
}
?>

<!DOCTYPE html>
<html>

<head>
  <title>Checkout Confirmation</title>
  <meta name="description" content="An example of Square Checkout on Glitch">
  <link rel='stylesheet' href='/main.css'>
  <link rel='stylesheet' href='/normalize.css'>
  <link id="favicon" rel="icon" href="https://cdn.glitch.com/4c9bc573-ca4c-48de-8afe-501eddad0b79%2Fsquare-logo.svg?1521834224783" type="image/x-icon">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name='viewport' content='width=device-width, initial-scale=1'>
</head>

<body>
  <header class="container">
    <div id="square-logo"></div>
    <h1 class="header">Custom Checkout Confirmation</h1>
  </header>

  <div class="container" id="confirmation">
    <div>
      <div>
        <?php
        echo ("Order " . $order->getId());
        ?>
      </div>
      <div>
        <?php
        echo ("Status: " . $order->getState());
        ?>
      </div>
    </div>
    <div>
      <?php
      foreach ($order->getLineItems() as $line_item) {
        // Display each line item in the order, you may want to consider formatting the money amount using different currencies
        echo ("
          <div class=\"item-line\">
            <div class=\"item-label\">" . $line_item->getName() . " x " . $line_item->getQuantity() . "</div>
            <div class=\"item-amount\">$" . number_format((float)$line_item->getTotalMoney()->getAmount() / 100, 2, '.', '') . "</div>
          </div>");
      }

      // Display total amount paid for the order, you may want to consider formatting the money amount using different currencies
      echo ("
        <div>
          <div class=\"item-line total-line\">
            <div class=\"item-label\">Total</div>
            <div class=\"item-amount\">$" . number_format((float)$order->getTotalMoney()->getAmount() / 100, 2, '.', '') . "</div>
          </div>
        </div>
        ");
      ?>
    </div>
    <div>
      <span>Payment Successful!</span>
      <a href="http://localhost:8888">Back to home page</a>
    </div>
  </div>
</body>

</html>