#!/usr/bin/env python
# coding: utf-8
import ConfigParser

# To read your secret credentials
config = ConfigParser.ConfigParser()
config.read('config.ini')


# Retrive credentials based on is_prod
config_type = "PRODUCTION" if config.get("DEFAULT", "is_prod") == "true" else "SANDBOX"
app_id = config.get(config_type, "app_id")
location_id = config.get(config_type, "location_id")

# print out the entire SqPaymentForm web page
html = """<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Sample Payment Form</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- link to the SqPaymentForm library -->
    <script type="text/javascript" src="https://js.squareup.com/v2/paymentform"></script>

    <script type="application/javascript">
        window.applicationId = '""" + app_id + """';
        window.locationId = '""" + location_id + """';
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
        <form id="nonce-form" novalidate action="process_card.py" method="post">
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
"""
print(html)
