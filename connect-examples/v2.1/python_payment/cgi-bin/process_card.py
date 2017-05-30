#!/usr/bin/env python
# coding: utf-8
from __future__ import print_function 
import uuid
import cgi

import squareconnect
from squareconnect.rest import ApiException
from squareconnect.apis.transaction_api import TransactionApi

# Create instance of FieldStorage 
form = cgi.FieldStorage()
# Get data from fields
nonce = form.getvalue('nonce')

# The access token to use in all Connect API requests. Use your *sandbox* access
# token if you're just testing things out.
access_token = 'REPLACE_ME'

# The ID of the business location to associate processed payments with.
# See [Retrieve your business's locations]
# (https://docs.connect.squareup.com/articles/getting-started/#retrievemerchantprofile)
# for an easy way to get your business's location IDs.
# If you're testing things out, use a sandbox location ID.
location_id = 'REPLACE_ME' 

api_instance = TransactionApi()

# Every payment you process with the SDK must have a unique idempotency key.
# If you're unsure whether a particular payment succeeded, you can reattempt
# it with the same idempotency key without worrying about double charging
# the buyer.
idempotency_key = str(uuid.uuid1())

# Monetary amounts are specified in the smallest unit of the applicable currency.
# This amount is in cents. It's also hard-coded for $1.00, which isn't very useful.
amount = {'amount': 100, 'currency': 'USD'}
body = {'idempotency_key': idempotency_key, 'card_nonce': nonce, 'amount_money': amount}


# The SDK throws an exception if a Connect endpoint responds with anything besides
# a 200-level HTTP code. This block catches any exceptions that occur from the request.  
try:
  # Charge
  api_response = api_instance.charge(access_token, location_id, body)
  res = api_response.transaction
except ApiException as e:
  res = "Exception when calling TransactionApi->charge: {}".format(e)

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
