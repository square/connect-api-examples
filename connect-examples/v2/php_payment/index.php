<?php
require 'vendor/autoload.php';

use Square\Environment;
// dotenv is used to read from the '.env' file created for credentials
$dotenv = Dotenv\Dotenv::create(__DIR__);
$dotenv->load();

// Pulled from the .env file and upper cased e.g. SANDBOX, PRODUCTION.
$upper_case_environment = strtoupper(getenv('ENVIRONMENT'));

?>
<html>
<head>
  <title>My Payment Form</title>
  <!-- link to the SqPaymentForm library -->
  <script type="text/javascript" src=
    <?php
        echo "\"";
        echo ($_ENV["ENVIRONMENT"] === Environment::PRODUCTION)  ?  "https://js.squareup.com/v2/paymentform"
                                            :  "https://js.squareupsandbox.com/v2/paymentform";
        echo "\"";
    ?>
  ></script>
  <script type="text/javascript">
    window.applicationId =
      <?php
        echo "\"";
        echo getenv('SQUARE_APPLICATION_ID');
        echo "\"";
      ?>;
    window.locationId =
    <?php
      echo "\"";
      echo getenv('SQUARE_LOCATION_ID');
      echo "\"";
    ?>;
  </script>

  <!-- link to the local SqPaymentForm initialization -->
  <script type="text/javascript" src="js/sq-payment-form.js"></script>
  <!-- link to the custom styles for SqPaymentForm -->
  <link rel="stylesheet" type="text/css" href="css/sq-payment-form.css">
</head>
<body>
  <!-- Begin Payment Form -->
  <div class="sq-payment-form">
    <!--
      Square's JS will automatically hide these buttons if they are unsupported
      by the current device.
    -->
    <div id="sq-walletbox">
      <button id="sq-google-pay" class="button-google-pay"></button>
      <button id="sq-apple-pay" class="sq-apple-pay"></button>
      <button id="sq-masterpass" class="sq-masterpass"></button>
      <div class="sq-wallet-divider">
        <span class="sq-wallet-divider__text">Or</span>
      </div>
    </div>
    <div id="sq-ccbox">
      <!--
        You should replace the action attribute of the form with the path of
        the URL you want to POST the nonce to (for example, "/process-card").

        You need to then make a "Charge" request to Square's Payments API with
        this nonce to securely charge the customer.

        Learn more about how to setup the server component of the payment form here:
        https://developer.squareup.com/docs/payments-api/overview
      -->
      <form id="nonce-form" novalidate action="/process-card.php" method="post">
        <div class="sq-field">
          <label class="sq-label">Card Number</label>
          <div id="sq-card-number"></div>
        </div>
        <div class="sq-field-wrapper">
          <div class="sq-field sq-field--in-wrapper">
            <label class="sq-label">CVV</label>
            <div id="sq-cvv"></div>
          </div>
          <div class="sq-field sq-field--in-wrapper">
            <label class="sq-label">Expiration</label>
            <div id="sq-expiration-date"></div>
          </div>
          <div class="sq-field sq-field--in-wrapper">
            <label class="sq-label">Postal</label>
            <div id="sq-postal-code"></div>
          </div>
        </div>
        <div class="sq-field">
          <button id="sq-creditcard" class="sq-button" onclick="onGetCardNonce(event)">
            Pay $1.00 Now
          </button>
        </div>
        <!--
          After a nonce is generated it will be assigned to this hidden input field.
        -->
        <div id="error"></div>
        <input type="hidden" id="card-nonce" name="nonce">
      </form>
    </div>
  </div>
  <!-- End Payment Form -->
</body>
</html>
