#!/usr/bin/env python
# coding: utf-8
import configparser
from http.server import BaseHTTPRequestHandler, HTTPServer
import logging
import uuid
import json

from square.client import Client

# To read your secret credentials
config = configparser.ConfigParser()
config.read("config.ini")


# Retrieve credentials based on is_prod
CONFIG_TYPE = config.get("DEFAULT", "environment").upper()
PAYMENT_FORM_URL = (
    "https://web.squarecdn.com/v1/square.js"
    if CONFIG_TYPE == "PRODUCTION"
    else "https://sandbox.web.squarecdn.com/v1/square.js"
)
APPLICATION_ID = config.get(CONFIG_TYPE, "square_application_id")
LOCATION_ID = config.get(CONFIG_TYPE, "square_location_id")
ACCESS_TOKEN = config.get(CONFIG_TYPE, "square_access_token")

client = Client(
    access_token=ACCESS_TOKEN,
    environment=config.get("DEFAULT", "environment"),
)

ACCOUNT_CURRENCY = client.locations.retrieve_location(location_id=LOCATION_ID).body[
    "location"
]["currency"]
ACCOUNT_COUNTRY = client.locations.retrieve_location(location_id=LOCATION_ID).body[
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
    + PAYMENT_FORM_URL
    + """></script>

    <script type="application/javascript">
        window.applicationId = '"""
    + APPLICATION_ID
    + """';
        window.locationId = '"""
    + LOCATION_ID
    + """';
        window.currency = '"""
    + ACCOUNT_CURRENCY
    + """';
        window.country = '"""
    + ACCOUNT_COUNTRY
    + """';
    </script>

    <!-- link to the custom styles for Web SDK -->
    <link rel='stylesheet', href='/stylesheets/sq-payment.css' />
    <link rel='stylesheet', href='/stylesheets/style.css' />
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
    <script type="text/javascript" src="/js/sq-ach.js"></script>
    <script type="text/javascript" src="/js/sq-apple-pay.js"></script>
    <script type="text/javascript" src="/js/sq-google-pay.js"></script>
    <script type="text/javascript" src="/js/sq-card-pay.js"></script>
  </body>

  <!-- link to the local Web SDK initialization -->
  <script type="text/javascript" src="/js/sq-payment-flow.js"></script>
</html>
"""
)


class Server(BaseHTTPRequestHandler):
    def do_GET(self):
        # Serve the index.html, else a static file
        if self.path == "" or self.path == "/":
            self.send_response(200)
            self.send_header("Content-type", "text/html")
            self.end_headers()
            self.wfile.write(html.encode("utf-8"))
        else:
            try:
                with open("./static" + self.path, "rb") as f:
                    data = f.read()
                self.send_response(200)
                self.end_headers()
                self.wfile.write(data)
            except FileNotFoundError:
                self.send_response(404)
                self.end_headers()
                self.wfile.write(b"not found")
            except PermissionError:
                self.send_response(403)
                self.end_headers()
                self.wfile.write(b"no permission")
            except Exception:
                self.send_response(500)
                self.end_headers()
                self.wfile.write(b"error")

    def do_POST(self):
        if self.path == "/process-payment":
            try:
                content_length = int(
                    self.headers["Content-Length"]
                )  # Gets the size of data
                body = self.rfile.read(content_length).decode(
                    "utf-8"
                )  # Gets the data itself

                token = json.loads(body)["token"]
                # length of idempotency_key should be less than 45
                idempotency_key = uuid.uuid4()

                logging.info("Charging payment")
                # Charge the customer's card
                create_payment_response = client.payments.create_payment(
                    body={
                        "source_id": token,
                        "idempotency_key": str(idempotency_key),
                        "amount_money": {
                            "amount": 100,  # $1.00 charge
                            "currency": ACCOUNT_CURRENCY,
                        },
                    }
                )

                logging.info("Charged")
                if create_payment_response.is_success():
                    response_body = json.dumps(create_payment_response.body)
                elif create_payment_response.is_error():
                    response_body = json.dumps(create_payment_response.errors)
                else:
                    raise Exception("create_payment is neither a success or has errors")

                self.send_response(200)
                self.send_header("Content-type", "application/json")
                self.end_headers()
                self.wfile.write(response_body.encode("utf-8"))

            except Exception:
                self.send_response(500)
                self.end_headers()
                self.wfile.write(b"error")
        else:
            self.send_response(404)
            self.end_headers()
            self.wfile.write(b"not found")


def run(port=8000):
    logging.basicConfig(level=logging.INFO)
    server_address = ("", port)
    httpd = HTTPServer(server_address, Server)
    logging.info("Starting server at http://localhost:" + str(port) + " ...\n")
    try:
        httpd.serve_forever()
    except KeyboardInterrupt:
        pass
    httpd.server_close()
    logging.info("Stopping server...\n")


if __name__ == "__main__":
    from sys import argv

    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()
