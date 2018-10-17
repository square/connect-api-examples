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
import squareconnect
from squareconnect.apis.o_auth_api import OAuthApi
from squareconnect.models.obtain_token_request import ObtainTokenRequest

# Your application's ID and secret, available from your application dashboard.
application_id = 'REPLACE_ME'
application_secret = 'REPLACE_ME'

# OAuth API instance
oauth_api = OAuthApi()

# Serves the link that merchants click to authorize your application
@get('/')
def authorize():
  return '''<a href="https://connect.squareup.com/oauth2/authorize?client_id={0}">Click here</a>
            to authorize the application.'''.format(application_id)

# Serves requsts from Square to your application's redirect URL
# Note that you need to set your application's Redirect URL to
# http://localhost:8080/callback from your application dashboard
@get('/callback')
def callback():

  # Extract the returned authorization code from the URL
  authorization_code = request.query.get('code')
  if authorization_code:

    # Provide the code in a request to the Obtain Token endpoint
    oauth_request_body = ObtainTokenRequest()
    oauth_request_body.client_id = application_id
    oauth_request_body.client_secret = application_secret
    oauth_request_body.code = authorization_code

    response = oauth_api.obtain_token(oauth_request_body)

    if response.access_token:

      # Here, instead of printing the access token, your application server should store it securely
      # and use it in subsequent requests to the Connect API on behalf of the merchant.
      print ('Access token: ' + response.access_token)
      return 'Authorization succeeded!'

    # The response from the Obtain Token endpoint did not include an access token. Something went wrong.
    else:
      return 'Code exchange failed!'

  # The request to the Redirect URL did not include an authorization code. Something went wrong.
  else:
    return 'Authorization failed!'

# Start up the server
run(host='localhost', port=8080)
