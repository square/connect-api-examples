#!/usr/bin/env python
# coding: utf-8
import configparser
import logging

from square.client import Client

# To read your secret credentials
config = configparser.ConfigParser()
config.read("config.ini")


# Retrive credentials based on is_prod
config_type = config.get("DEFAULT", "environment").upper()
payment_form_url = (
    "https://web.squarecdn.com/v1/square.js"
    if config_type == "PRODUCTION"
    else "https://sandbox.web.squarecdn.com/v1/square.js"
)
application_id = config.get(config_type, "square_application_id")
location_id = config.get(config_type, "square_location_id")
access_token = config.get(config_type, "square_access_token")

client = Client(
    access_token=access_token,
    environment=config.get("DEFAULT", "environment"),
)

account_currency = client.locations.retrieve_location(location_id=location_id).body[
    "location"
]["currency"]
account_country = client.locations.retrieve_location(location_id=location_id).body[
    "location"
]["country"]

# print out the entire Web SDK web page
html = (
    """<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Make Payment</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- link to the Web SDK library -->
    <script type="text/javascript" src="""
    + payment_form_url
    + """></script>

    <script type="application/javascript">
        window.applicationId = '"""
    + application_id
    + """';
        window.locationId = '"""
    + location_id
    + """';
        window.currency = '"""
    + account_currency
    + """';
        window.country = '"""
    + account_country
    + """';
    </script>

    <!-- link to the custom styles for Web SDK -->
    <link rel='stylesheet', href='/public/stylesheets/sq-payment.css' />
    <link rel='stylesheet', href='/public/stylesheets/style.css' />
  </head>

  <body>
    <form class="payment-form" id="fast-checkout">
      <div class="wrapper">
        <div id="apple-pay-button" alt="apple-pay" type="button"></div>
        <div id="google-pay-button" alt="google-pay" type="button"></div>
        <div class="border">
          <span>OR</span>
        </div>

        <div id="ach-wrapper">
          <label for="ach-account-holder-name">Full Name</label>
          <input
            id="ach-account-holder-name"
            type="text"
            placeholder="Jane Doe"
            name="ach-account-holder-name"
            autocomplete="name"
          />
          <span id="ach-message"></span>
          <button id="ach-button" type="button">
            Pay with Bank Account
          </button>
          <div class="border">
            <span>OR</span>
          </div>
        </div>

        <div id="card-container"></div>
        <button id="card-button" type="button">
          Pay with Card
        </button>
        <span id="payment-flow-message">
      </div>
    </form>
    <script type="text/javascript" src="/public/js/sq-ach.js"></script>
    <script type="text/javascript" src="/public/js/sq-apple-pay.js"></script>
    <script type="text/javascript" src="/public/js/sq-google-pay.js"></script>
    <script type="text/javascript" src="/public/js/sq-card-pay.js"></script>
  </body>

  <!-- link to the local Web SDK initialization -->
  <script type="text/javascript" src="/public/js/sq-payment-flow.js"></script>
</html>
"""
)
print(html)
