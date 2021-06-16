#!/usr/bin/python
#
# Demonstrates a server listening for webhook notifications from the Square Connect API
#
# See Webhooks Overview for more information:
# https://developer.squareup.com/docs/webhooks-api/v1-tech-ref
#
# To install Python on Windows:
# https://www.python.org/download/
#
# This sample requires the Bottle web framework:
# http://bottlepy.org/docs/dev/tutorial.html#installation

from bottle import post, request, run
from hashlib import sha1
import hmac, httplib, json, locale

# Your application's access token
access_token = 'REPLACE_ME'

# Your application's webhook signature key, available from your application dashboard
webhook_signature_key = 'REPLACE_ME'

# The URL that this server is listening on (e.g., 'http://example.com/events')
# Note that to receive notifications from Square, this cannot be a localhost URL
webhook_url = 'REPLACE_ME'

# Headers to provide to Connect API endpoints
request_headers = { 'Authorization': 'Bearer ' + access_token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'}


# Listens for PAYMENT_UPDATED webhook notifications and retrieves associated payments
#
# Note that you need to set your application's webhook URL from your application dashboard
# to receive these notifications. In this sample, if your host's base URL is 
# http://example.com, you'd set your webhook URL to http://example.com/events
@post('/events')
def webhooks_callback():

  # Get the JSON body and HMAC-SHA1 signature of the POST request
  callback_body = request.body.getvalue()
  callback_signature = request.get_header('X-Square-Signature', default='')

  # Validate the signature
  if not is_valid_callback(callback_body, callback_signature):

    # Fail if the signature is invalid
    print 'Webhook event with invalid signature detected!'
    return

  # Load the JSON body into a Python dict
  callback_body_dict = json.loads(callback_body)
  
  # If the notification indicates a PAYMENT_UPDATED event...
  if 'event_type' in callback_body_dict and callback_body_dict['event_type'] == 'PAYMENT_UPDATED':
  
    # Get the ID of the updated payment
    payment_id = callback_body_dict['entity_id']

    # Get the ID of the payment's associated location
    location_id = callback_body_dict['location_id']
    
    # Send a request to the Retrieve Payment endpoint to get the updated payment's full details
    connection = httplib.HTTPSConnection('connect.squareup.com')
    connection.request('GET', '/v1/' + location_id + '/payments/' + payment_id, '', request_headers)
    connect_response_body = json.loads(connection.getresponse().read())

    # Perform an action based on the returned payment (in this case, simply print the payment)
    print connect_response_body

  # The HMAC-SHA1 signature was valid, but the request wasn't for a PAYMENT_UPDATED event.
  else:
    print 'Unknown webhook event type detected!'

# Validates HMAC-SHA1 signatures included in webhook notifications to ensure notifications came from Square
def is_valid_callback(callback_body, callback_signature):

  # Combine your webhook notification URL and the JSON body of the incoming request into a single string
  string_to_sign = webhook_url + callback_body

  # Generate the HMAC-SHA1 signature of the string, signed with your webhook signature key
  string_signature = hmac.new(webhook_signature_key, string_to_sign, sha1).digest().encode('base64')

  # Remove the trailing newline from the generated signature (this is a quirk of the Python library)
  string_signature = string_signature.rstrip('\n')

  # Compare your generated signature with the signature included in the request
  return hmac.compare_digest(string_signature, callback_signature)

# Start up the server
run(host='localhost', port=8080)
