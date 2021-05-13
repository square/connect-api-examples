# Demonstrates a Sinatra server listening for webhook notifications from the Square Connect API
#
# See Webhooks Overview for more information:
# https://developer.squareup.com/docs/webhooks-api/v1-tech-ref
#
# This sample requires the following gems:
#   sinatra (http://www.sinatrarb.com/)
#   unirest (http://unirest.io/ruby.html)

require 'base64'
require 'digest/sha1'
require 'json'
require 'openssl'
require 'sinatra'
require 'unirest'

# Your application's access token
ACCESS_TOKEN = 'REPLACE_ME'

# Your application's webhook signature key, available from your application dashboard
WEBHOOK_SIGNATURE_KEY = 'REPLACE_ME'

# The URL that this server is listening on (e.g., 'http://example.com/events')
# Note that to receive notifications from Square, this cannot be a localhost URL
WEBHOOK_URL = 'REPLACE_ME'

# The base URL for every Connect API request
CONNECT_HOST = 'https://connect.squareup.com'

# Headers to provide to Connect API endpoints
REQUEST_HEADERS = { 'Authorization' => 'Bearer ' + ACCESS_TOKEN,
                     'Accept' => 'application/json',
                     'Content-Type' => 'application/json'}


# Retrieves payments by the IDs provided in webhook notifications.
#
# Note that you need to set your application's webhook URL from your application dashboard
# to receive these notifications. In this sample, if your host's base URL is
# http://example.com, you'd set your webhook URL to http://example.com/events.
post '/events' do

  # Get the JSON body and HMAC-SHA1 signature of the incoming POST request
  callback_body = request.body.string
  callback_signature = request.env['HTTP_X_SQUARE_SIGNATURE']

  # Validate the signature
  if not is_valid_callback(callback_body, callback_signature)

  	# Fail if the signature is invalid
    puts 'Webhook event with invalid signature detected!'
    return
  end

  # Load the JSON body into a hash
  callback_body_json = JSON.parse(callback_body)

  # If the notification indicates a PAYMENT_UPDATED event...
  if callback_body_json.has_key?('event_type') and callback_body_json['event_type'] == 'PAYMENT_UPDATED'

  	# Get the ID of the updated payment
    payment_id = callback_body_json['entity_id']

    # Get the ID of the payment's associated location
    location_id = callback_body_json['location_id']

    # Send a request to the Retrieve Payment endpoint to get the updated payment's full details
    response = Unirest.get CONNECT_HOST + '/v1/' + location_id + '/payments/' + payment_id,
                  headers: REQUEST_HEADERS

    # Perform an action based on the returned payment (in this case, simply log it)
    puts JSON.pretty_generate(response.body)
  end
end


# Validates HMAC-SHA1 signatures included in webhook notifications to ensure notifications came from Square
def is_valid_callback(callback_body, callback_signature)

  # Combine your webhook notification URL and the JSON body of the incoming request into a single string
  string_to_sign = WEBHOOK_URL + callback_body

  # Generate the HMAC-SHA1 signature of the string, signed with your webhook signature key
  string_signature = Base64.strict_encode64(OpenSSL::HMAC.digest('sha1', WEBHOOK_SIGNATURE_KEY, string_to_sign))

  # Hash the signatures a second time (to protect against timing attacks)
  # and compare them
  return Digest::SHA1.base64digest(string_signature) == Digest::SHA1.base64digest(callback_signature)
end
