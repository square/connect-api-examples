#!/usr/bin/env python
# coding: utf-8
import configparser

# To read your secret credentials
config = configparser.ConfigParser()
config.read('config.ini')


# Retrive credentials based on is_prod
config_type = "PRODUCTION" if config.get("DEFAULT", "is_prod") == "true" else "SANDBOX"
payment_form_url = "https://js.squareup.com/v2/paymentform" if config_type == "PRODUCTION" else "https://js.squareupsandbox.com/v2/paymentform";
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
    <script type="text/javascript" src=""" + payment_form_url + """></script>

    <script type="application/javascript">
        window.applicationId = '""" + app_id + """';
        window.locationId = '""" + location_id + """';
    </script>

    <!-- link to the local SqPaymentForm initialization -->
    <script type="text/javascript" src="/sq-payment-form.js"></script>

    <!-- link to the custom styles for SqPaymentForm -->
    <link rel="stylesheet" type="text/css" href="/sq-payment-form.css">
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
        <form id="nonce-form" novalidate action="process_card.py">
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
"""
print(html)
