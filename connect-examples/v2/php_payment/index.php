<?php
require 'vendor/autoload.php';
// dotenv is used to read from the '.env' file created for credentials
$dotenv = new Dotenv\Dotenv(__DIR__);
$dotenv->load();
?>
<html>
<head>
  <title>My Payment Form</title>
  <!-- link to the SqPaymentForm library -->
  <script type="text/javascript" src="https://js.squareup.com/v2/paymentform"></script>
  <script type="text/javascript">
    window.applicationId =
      <?php
        echo "\"";
        echo ($_ENV["USE_PROD"] == 'true')  ?  $_ENV["PROD_APP_ID"]
                                            :  $_ENV["SANDBOX_APP_ID"];
        echo "\"";
      ?>;
    window.locationId =
    <?php
      echo "\"";
      echo ($_ENV["USE_PROD"] == 'true')  ?  $_ENV["PROD_LOCATION_ID"]
                                          :  $_ENV["SANDBOX_LOCATION_ID"];
      echo "\"";
    ?>;
  </script>

  <!-- link to the local SqPaymentForm initialization -->
  <script type="text/javascript" src="/sqpaymentform.js"></script>

  <!-- link to the custom styles for SqPaymentForm -->
  <link rel="stylesheet" type="text/css" href="/sqpaymentform.css">
</head>
<body>
  <div id="sq-ccbox">
    <!--
      You should replace the action attribute of the form with the path of
      the URL you want to POST the nonce to (for example, "/process-card")
    -->
    <form id="nonce-form" novalidate action="/process-card.php" method="post">
      <div id="error"></div>
      <table>
      <tbody>
        <tr>
          <td class="label">Card Number:</td>
          <td><div id="sq-card-number"></div></td>
        </tr>
        <tr>
          <td class="label">CVV:</td>
          <td><div id="sq-cvv"></div></td>
        </tr>
        <tr>
          <td class="label">Expiration Date: </td>
          <td><div id="sq-expiration-date"></div></td>
        </tr>
        <tr>
          <td class="label">Postal Code:</td>
          <td><div id="sq-postal-code"></div></td>
        </tr>
        <tr>
          <td colspan="2">
            <button id="sq-creditcard" class="button-credit-card" onclick="requestCardNonce(event)">
              Pay $1.00 Now
            </button>
          </td>
        </tr>
      </tbody>
      </table>

      <!--
        After a nonce is generated it will be assigned to this hidden input field.
      -->
      <input type="hidden" id="card-nonce" name="nonce">
    </form>
  </div>

  <div id="sq-walletbox">
    Pay with a Digital Wallet
    <div id="sq-apple-pay-label" class="wallet-not-enabled">Apple Pay for Web not enabled</div>
    <!-- Placeholder for Apple Pay for Web button -->
    <button id="sq-apple-pay" class="button-apple-pay"></button>

    <div id="sq-masterpass-label" class="wallet-not-enabled">Masterpass not enabled</div>
    <!-- Placeholder for Masterpass button -->
    <button id="sq-masterpass" class="button-masterpass"></button>
  </div>
</body>
</html>
