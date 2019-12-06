# This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow:
#
# 1. A merchant clicks the authorization link served by the root path (http://localhost:8080/)
# 2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
#    is already signed in to Square, and if the merchant has already authorized your application,
#    the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
# 3. Square sends a request to your application's Redirect URL
#    (which should be set to http://localhost:8080/callback on your application dashboard)
# 4. The server extracts the authorization code provided in Square's request and passes it
#    along to the Obtain Token endpoint.
# 5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
#    to the Connect API.

from bottle import get, request, static_file, run, response

from square.client import Client

# Your application's ID and secret, available from your application dashboard.
# If you are testing this sample in the sandbox, use the sandbox app id and secret
# from your application dashboard in "Sandbox Settings" mode.
application_id = 'REPLACE_ME'
application_secret = 'REPLACE_ME'
sandboxURL = 'https://connect.squareupsandbox.com'
productionURL = 'https://connect.squareup.com'
scopes = 'MERCHANT_PROFILE_READ PAYMENTS_READ SETTLEMENTS_READ BANK_ACCOUNTS_READ'

# For Sandbox testing: Set the base URL to the Square sandbox domain
# Default environment is production
client = Client(
    environment='sandbox'
)

o_auth_api = client.o_auth


# Serves the link that merchants click to authorize your application
# If you have set the client environment to 'sandbox', you must format
# this authorization URL with the sandboxURL.  Otherwise, use the productionURL
@get('/')
def authorize():
  return '''<a href="{0}/oauth2/authorize?client_id={1}&scope={2}">Click here</a>
            to authorize the application.'''.format(sandboxURL, application_id, scopes)

# Serves requsts from Square to your application's redirect URL
# Note that you need to set your application's Redirect URL to
# http://localhost:8080/callback from your application dashboard
@get('/callback')
def callback():

  # Extract the returned authorization code from the URL
  authorization_code = request.query.get('code')
  if authorization_code:

    # Provide the code in a request to the Obtain Token endpoint

    body = {}
    body['client_id'] = application_id
    body['client_secret'] = application_secret
    body['code'] = authorization_code
    body['grant_type'] = 'authorization_code'

    result = o_auth_api.obtain_token(body)

    if result.is_success():
      # Here, instead of printing the access token, your application server should store it securely
      # and use it in subsequent requests to the Connect API on behalf of the merchant.
      print ('Access token: ' + result.body['access_token'])
      return 'Authorization succeeded!'
    elif result.is_error():
        return 'Code exchange failed!'


  # The request to the Redirect URL did not include an authorization code. Something went wrong.
  else:
    return 'Authorization failed!'

# Start up the server
run(host='localhost', port=8080)
