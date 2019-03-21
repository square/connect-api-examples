# This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow:
#
# 1. A merchant clicks the authorization link served by the root path (http://localhost:4567/)
# 2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
#    is already signed in to Square, and if the merchant has already authorized your application,
#    the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
# 3. Square sends a request to your application's Redirect URL
#    (which should be set to http://localhost:4567/callback on your application dashboard)
# 4. The server extracts the authorization code provided in Square's request and passes it
#    along to the Obtain Token endpoint.
# 5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
#    to the Connect API.
#
# This sample requires the following gems:
#   sinatra (http://www.sinatrarb.com/)

require 'sinatra'
require 'square_connect'

# Your application's ID and secret, available from your application dashboard.
APP_ID     = 'REPLACE_ME'
APP_SECRET = 'REPLACE_ME'

CONNECT_HOST = "https://connect.squareup.com"

oauth_api = SquareConnect::OAuthApi.new

# Serves the link that merchants click to authorize your application
get '/' do
  "<a href=\"#{CONNECT_HOST}/oauth2/authorize?client_id=#{APP_ID}\">Click here</a>
            to authorize the application."
end

# Serves requsts from Square to your application's redirect URL
# Note that you need to set your application's Redirect URL to
# http://localhost:4567/callback from your application dashboard
get '/callback' do

  # Extract the returned authorization code from the URL
  authorization_code = params['code']

  if authorization_code

    # Provide the code in a request to the Obtain Token endpoint
    oauth_request_body = {
      'client_id' => APP_ID,
      'client_secret' => APP_SECRET,
      'code' => authorization_code,
      'grant_type' => 'authorization_code'
    }

    response = oauth_api.obtain_token(oauth_request_body)

    # Extract the returned access token from the ObtainTokenResponse object
    if response.access_token

      # Here, instead of printing the access token, your application server should store it securely
      # and use it in subsequent requests to the Connect API on behalf of the merchant.
      puts 'Access token: ' + response.access_token
      return 'Authorization succeeded!'

    # The response from the Obtain Token endpoint did not include an access token. Something went wrong.
    else
      return 'Code exchange failed!'
    end

  # The request to the Redirect URL did not include an authorization code. Something went wrong.
  else
    return 'Authorization failed!'
  end
end
