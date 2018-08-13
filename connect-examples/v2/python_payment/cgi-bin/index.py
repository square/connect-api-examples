#!/usr/bin/env python
# coding: utf-8
import ConfigParser

# To read your secret credentials
config = ConfigParser.ConfigParser()
config.read('config.ini')

if config.get("DEFAULT", "is_prod") == "true":
    app_id = config.get("PRODUCTION", "app_id")
else:
    app_id = config.get("SANDBOX", "app_id")

if config.get("DEFAULT", "is_prod") == "true":
    location_id = config.get("PRODUCTION", "location_id")
else:
    location_id = config.get("SANDBOX", "location_id")

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
      <form id="nonce-form" novalidate action="/cgi-bin/process_card.py" method="post">

        Pay with a Credit Card

        <table>
        <tbody>
          <tr>
            <td>Card Number:</td>
            <td><div id="sq-card-number"></div></td>
          </tr>
          <tr>
            <td>CVV:</td>
            <td><div id="sq-cvv"></div></td>
          </tr>
          <tr>
            <td>Expiration Date: </td>
            <td><div id="sq-expiration-date"></div></td>
          </tr>
          <tr>
            <td>Postal Code:</td>
            <td><div id="sq-postal-code"></div></td>
          </tr>
          <tr>
            <td colspan="2">
              <button id="sq-creditcard" class="button-credit-card" onclick="requestCardNonce(event)">
                Pay with card
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
      <!-- Placholder for Apple Pay for Web button -->
      <button id="sq-apple-pay" class="button-apple-pay"></button>

      <div id="sq-masterpass-label" class="wallet-not-enabled">Masterpass not enabled</div>
      <!-- Placholder for Masterpass button -->
      <button id="sq-masterpass" class="button-masterpass"></button>
    </div>
  </body>
</html>
"""
print(html)
