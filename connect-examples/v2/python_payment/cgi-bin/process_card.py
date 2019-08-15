#!/usr/bin/env python
# coding: utf-8
from __future__ import print_function
import uuid
import cgi
import ConfigParser

import squareconnect
from squareconnect.rest import ApiException
from squareconnect.configuration import Configuration
from squareconnect.api_client import ApiClient
from squareconnect.apis.payments_api import PaymentsApi

# To read your secret credentials
config = ConfigParser.ConfigParser()
config.read('config.ini')

# Create instance of FieldStorage
form = cgi.FieldStorage()
# Get data from fields
nonce = form.getvalue('nonce')

# The access token to use in all Connect API requests. Use your *sandbox* access
# token if you're just testing things out.
config_type = "PRODUCTION" if config.get("DEFAULT", "is_prod") == "true" else "SANDBOX"
access_token = config.get(config_type, "access_token")

api_config = Configuration()
# Set 'host' to switch between sandbox env and production env
# sandbox: https://connect.squareupsandbox.com
# production: https://connect.squareup.com
api_config.host = "https://connect.squareupsandbox.com"
api_client = ApiClient(api_config)
api_client.configuration.access_token = access_token

api_instance = PaymentsApi(api_client)

# Every payment you process with the SDK must have a unique idempotency key.
# If you're unsure whether a particular payment succeeded, you can reattempt
# it with the same idempotency key without worrying about double charging
# the buyer.
idempotency_key = str(uuid.uuid1())

# Monetary amounts are specified in the smallest unit of the applicable currency.
# This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
amount = {'amount': 100, 'currency': 'USD'}

# To learn more about splitting payments with additional recipients,
# see the Payments API documentation on our [developer site]
# (https://developer.squareup.com/docs/payments-api/overview).
body = {'idempotency_key': idempotency_key, 'source_id': nonce, 'amount_money': amount}

# The SDK throws an exception if a Connect endpoint responds with anything besides
# a 200-level HTTP code. This block catches any exceptions that occur from the request.
try:
  api_response = api_instance.create_payment(body)
  res = api_response.payment
except ApiException as e:
  res = "Exception when calling PaymentsApi->create_payment: {}".format(e)

# Display the result
print ('Content-type:text/html\r\n\r\n')
print ('<html>')
print ('<head>')
print ('<title>Square Payment</title>')
print ('</head>')
print ('<body>')
print ('<h2>Result: </h2>')
print ('<p>{}</p>'.format(res))
print ('</body>')
print ('</html>')
