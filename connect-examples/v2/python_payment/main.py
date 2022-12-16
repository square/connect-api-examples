#!/usr/bin/env python
# coding: utf-8
import configparser
import logging
import uuid
from fastapi import FastAPI
from fastapi.responses import HTMLResponse
from fastapi.staticfiles import StaticFiles
from pydantic import BaseModel

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
    user_agent_detail="sample_app_python_payment", # Remove or replace this detail when building your own app
)

location = client.locations.retrieve_location(location_id=LOCATION_ID).body["location"]
ACCOUNT_CURRENCY = location["currency"]
ACCOUNT_COUNTRY = location["country"]

def generate_index_html():
    html_content = (
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
            window.idempotencyKey = '"""
        + str(uuid.uuid4())
        + """';
        </script>

        <!-- link to the custom styles for Web SDK -->
        <link rel='stylesheet', href='/static/stylesheets/sq-payment.css' />
        <link rel='stylesheet', href='/static/stylesheets/style.css' />
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
        <script type="text/javascript" src="/static/js/sq-ach.js"></script>
        <script type="text/javascript" src="/static/js/sq-apple-pay.js"></script>
        <script type="text/javascript" src="/static/js/sq-google-pay.js"></script>
        <script type="text/javascript" src="/static/js/sq-card-pay.js"></script>
      </body>

      <!-- link to the local Web SDK initialization -->
      <script type="text/javascript" src="/static/js/sq-payment-flow.js"></script>
    </html>
    """
    )
    return HTMLResponse(content=html_content, status_code=200)


class Payment(BaseModel):
    token: str
    idempotencyKey: str


app = FastAPI()
app.mount("/static", StaticFiles(directory="static"), name="static")


@app.get("/", response_class=HTMLResponse)
def read_root():
    return generate_index_html()


@app.post("/process-payment")
def create_payment(payment: Payment):
    logging.info("Creating payment")
    # Charge the customer's card
    create_payment_response = client.payments.create_payment(
        body={
            "source_id": payment.token,
            "idempotency_key": payment.idempotencyKey,
            "amount_money": {
                "amount": 100,  # $1.00 charge
                "currency": ACCOUNT_CURRENCY,
            },
        }
    )

    logging.info("Payment created")
    if create_payment_response.is_success():
        return create_payment_response.body
    elif create_payment_response.is_error():
        return create_payment_response
