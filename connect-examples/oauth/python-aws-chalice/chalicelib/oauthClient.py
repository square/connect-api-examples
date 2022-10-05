# Copyright 2020 Square Inc.

# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

'''This module defines all the Square OAuth related operations'''

from square.client import Client
import os

client_id = os.environ['application_id']
client_secret = os.environ['application_secret']

# initialize square oauth client
square_client = Client(
  environment=os.environ['environment'],
  user_agent_detail='sample_app_oauth_python-aws-chalice' # Remove or replace this detail when building your own app
)
oauth_api = square_client.o_auth

def conduct_authorize_url(state):
  '''Conduct a Square authorization url 

  This method creates a Square authroization url that takes client to Square
  authorization page to finish OAuth permission grant process.

  This url contains several important query parameters:

  client_id - aka. application id, the Square application which is requesting permissions.

  scope - s space-separated list of the permissions the application is requesting, different square api require different permissions.

  session - Suggest to set it to `false` all the time in production, only set it to `true` in sandbox

  state - we should set state value and verify the value in the callback to help protect against cross-site request forgery.

  Parameters
  ----------
  state : str
    The state value that will be verified in the authorizae callback.
  '''
  authorize_url = (
    os.environ['base_url'] + '/oauth2/authorize'
    '?client_id=' + client_id +
    '&scope=' + os.environ['permissions'] +
    '&session=' + os.environ['session'] +
    '&state=' + state
  )
  return authorize_url


def exchange_oauth_tokens(code):
  '''Exchange Square OAuth tokens with authorization code 

  This method exchanges two OAuth tokens (Access Token and Refresh Token) with
  the authorization code that is returned with the authorize callback.

  We call `obtain_token` api with authorization code to get OAuth tokens.

  Parameters
  ----------
  code : str
    The authorization code to exchange OAuth tokens.
  '''
  request_body = {}
  request_body['client_id'] = client_id
  request_body['client_secret'] = client_secret
  request_body['code'] = code
  request_body['grant_type'] = 'authorization_code'
  response = oauth_api.obtain_token(request_body)
  return response


def refresh_oauth_access_token(refresh_token):
  '''Refresh Square OAuth access token with refresh token 

  This method calls `obtain_token` api with the refresh token that was retrieved from `exchangeOAuthToken`.

  Parameters
  ----------
  refresh_token : str
    A valid refresh token for generating a new OAuth access token.
  '''
  request_body = {}
  request_body['client_id'] = client_id
  request_body['client_secret'] = client_secret
  # Set the refresh_token
  request_body['refresh_token'] = refresh_token
  request_body['grant_type'] = 'refresh_token'
  response = oauth_api.obtain_token(request_body)
  return response


def revoke_oauth_access_token(merchant_id):
  '''Revoke All Square OAuth tokens from a merchant

  This method calls `revoke_token` api with id of the merchant whose token you want to revoke.

  Parameters
  ----------
  merchant_id : str
    The id of the merchant whose token you want to revoke.
  '''
  request_body = {}
  request_body['client_id'] = client_id
  request_body['merchant_id'] = merchant_id
  # The authentication need a special prefix 'Client ' before appending client secret
  response = oauth_api.revoke_token(request_body, 'Client ' + client_secret)
  return response
