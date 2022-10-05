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

from chalice import Chalice, Rate, Response
from http import cookies
import os
import uuid
from datetime import datetime, timedelta
from square.client import Client
from chalicelib.oauthDB import add_oauth_record, create_oauth_dynamodb_table, get_expiring_oauth_records, get_oauth_record, remove_oauth_record, update_oauth_record
from chalicelib.oauthClient import conduct_authorize_url, exchange_oauth_tokens, refresh_oauth_access_token, revoke_oauth_access_token
from cryptography.fernet import Fernet


app = Chalice(app_name='oauth-example')

# intialize Fernet to encrypt oauth tokens
token_encrypt_key = os.environ['token_encrypt_secret'].encode('ASCII');
fernet = Fernet(token_encrypt_key)

# create html template for html response
html_template = '''
  <!DOCTYPE html>
  <html>
    <body>
      {0}
    </body>
  </html>
'''

@app.route('/authorize', methods=['GET'])
def authorize():
  '''The endpoint that renders the link to Square authorization page.'''

  # set the Auth_State cookie with a random uuid string to protect against cross-site request forgery
  # Auth_State will expire in 60 seconds once the page is loaded, you can customize the timeout based 
  # on your scenario.
  # `HttpOnly` helps mitigate XSS risks and `SameSite` helps mitigate CSRF risks. 
  state = str(uuid.uuid4())
  cookie_str = 'OAuthState={0}; HttpOnly; Max-Age=60; SameSite=Lax'.format(state)

  # create the authorize url with the state
  authorize_url = conduct_authorize_url(state)

  # render the page
  body = ('<a href="' + authorize_url + '">Click here</a> ' +
      'to authorize the application -' + os.environ['application_id'])
  return Response(
    body=html_template.format(body),
    status_code=200,
    headers={
      'Content-Type': 'text/html',
      'Set-Cookie': cookie_str
      }
  )


@app.route('/authorize-callback', methods=['GET'])
def authorize_callback():
  '''The endpoint that handles Square authorization callback.

  The endpoint receives authorization result from the Square authorization page.
  If it is a successful authorization, it will use the code to exchange an
  access_token and refresh_token and store them in db table.
  If it is a failed authorization, it collects the failure oauth_apin and render the response.

  This callback endpoint should be added as the OAuth Redirect URL of your Square application
  in the Square Developer Dashboard.

  Query Parameters
  ----------------
  response_type : str
    The type of the response, it should be 'code' with a succesful authorization callback.

  code : str
    A valid authorization code. Authorization codes are exchanged for OAuth access tokens with the ObtainToken endpoint.

  state : str
    The state that was set in the original authorization url. verify this value to help
    protect against cross-site request forgery.

  error : str
    The error code of a failed authrization. Only exists when failed to authorize.

  error_description : str
    The error description of a failed authrization. Only exists when failed to authorize.
  '''
  query_params = app.current_request.query_params

  # get the state that was set in the authorization url
  state = query_params.get('state')

  # get the auth state cookie to compare with the state that is in the callback
  cookie_state = ''
  cookie = app.current_request.headers.get('cookie')
  if cookie:
    c = cookies.SimpleCookie(cookie)
    cookie_state = c['OAuthState'].value

  body = ''
  if cookie_state == '' or state != cookie_state:
    body = '''
      <h1>Authorization failed</h1>
      <div>Invalid reqeust due to invalid auth state.</div>
    '''
  elif 'code' == query_params.get('response_type'):
    response = exchange_oauth_tokens(code=query_params.get('code'))
    if response.is_success():
      body = response.body
      # encrypt the refresh_token and access_token before save to db
      encrypted_refresh_token = fernet.encrypt(body['refresh_token'].encode('ASCII'))
      encrypted_access_token = fernet.encrypt(body['access_token'].encode('ASCII'))
      # save or overwrite the oauth record to db
      add_oauth_record(
        merchant_id=body['merchant_id'],
        refresh_token=encrypted_refresh_token,
        access_token=encrypted_access_token,
        expires_at=body['expires_at']
      )
      body = '''
        <h1>Authorization succeeded</h1>
      '''
    elif response.is_error():
      body = '''
        <h1>Authorization failed</h1>
        <div>Error: {0}</div>
        <div>Error Type: {1}</div>
      '''.format(response.body['message'], response.body['type'])
  elif 'error' in query_params:
    body = '''
      <h1>Authorization failed</h1>
      <div>Error: {0}</div>
      <div>Error Details: {1}</div>
    '''.format(query_params.get('error'), query_params.get('error_description'))
  else:
    body = '''
      <h1>Authorization failed</h1>
      <div>Error: Unknown</div>
    '''
  return Response(body=html_template.format(body), status_code=200, headers={"Content-Type": "text/html"})


@app.route('/revokeOAuthToken/{merchant_id}', methods=['GET'])
def revoke_token(merchant_id):
  '''The endpoint that revoke all the access token from the specified merchant for this application.

  This endpoint does two things:
    1. call Square OAuth to revoke the tokens
    2. remove the OAuth record from the database

  We suggest that a method must be provided for merchants to revoke an access token using
  the Revoke Token api.

  Path Parameters
  ----------------
  merchant_id : str
    The ID of the merchant whose token you want to revoke. 
  '''
  # revoke the token from Square for this merchant
  response = revoke_oauth_access_token(merchant_id)
  if response.is_success():
    # remove the OAuth record from db
    remove_oauth_record(merchant_id)
    body = '''
      <h1>Access token successfully revoked for merchant {0}.</h1>
    '''.format(merchant_id)
  elif response.is_error():
    # failed to revoke OAuth token from square
    body = '''
        <h1>Revoke OAuth token failed</h1>
        <div>Error: {0}</div>
        <div>Error Type: {1}</div>
      '''.format(response.body['message'], response.body['type'])
  return Response(body=html_template.format(body), status_code=200, headers={"Content-Type": "text/html"})


@app.route('/checkAccessTokenStatus/{merchant_id}', methods=['GET'])
def check_access_token_status(merchant_id):
  '''The endpoint that check access token status of a merchant.

  The OAuth access token expires or is revoked due to unforeseen reasons, you need always show the correct status
  of the merchant's access token, so that the merchant can recover the access with your app whenever the access token
  becomes invalid.

  Path Parameters
  ----------------
  merchant_id : str
    The ID of the merchant whose access token you want to check. 
  '''
  # get the access_token from db
  oauth_record = get_oauth_record(merchant_id)
  if not oauth_record:
    body = '''
      <h1>merchant {0} not found.</h1>
    '''.format(merchant_id)
  else:
    access_token = fernet.decrypt(oauth_record['AccessToken']['B']).decode('ASCII')
    # call the list_locations to check the access token status
    # you can use other endpoint to check the access token status within the api permission scope
    square_client_with_auth = Client(
      environment=os.environ['environment'],
      access_token=access_token,
      user_agent_detail="sample_app_oauth_python-aws-chalice" # Remove or replace this detail when building your own app
    )
    api_payments = square_client_with_auth.payments
    response = api_payments.list_payments(
      begin_time = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ') # use now as begin_time to reduce the payload
    )
    if response.is_success():
      # successful api call indicates access token is valid
      body = '''
        <h1>Access token is valid for merchant {0}.</h1>
      '''.format(merchant_id)
    elif response.is_error():
      # get the error code from the response
      error_code = response.errors[0]['code']
      if error_code in ['UNAUTHORIZED', 'ACCESS_TOKEN_EXPIRED', 'ACCESS_TOKEN_REVOKED', 'INSUFFICIENT_SCOPES']:
        # the error code indicates the access token is invalid
        body = '''
          <h1>Access token is invalid for merchant {0}.</h1>
        '''.format(merchant_id)
      else:
        # non-auth related error, the status becomes unknown
        # you may want to retry in case it's an transient issue
        body = '''
          <h1>Access token staus is unknown for merchant {0}.</h1>
        '''.format(merchant_id)
  return Response(body=html_template.format(body), status_code=200, headers={"Content-Type": "text/html"})


@app.schedule(Rate(12, unit=Rate.HOURS))
def renew_token_job():
  '''The cron job that trigger renew all OAuth access token every 12 hours

  The job doesn't neccessarily renew any OAuth access in the OAuth table, it's up to function renewToken
  to decide wheter an access token should be renewed.
  '''
  refresh_access_token()


def refresh_access_token():
  '''The function that scan OAuth table and refresh expiring access tokens.

  The function doesn't refresh every access tokens in the table, it only finds those access tokens that are
  expiring (expire in 7 days) and call Square OAuth api to refresh the access token.
  '''
  # get all the expiring oauth records
  expiring_oauth_records = get_expiring_oauth_records()
  # iterate through all expiring oauth records
  for auth_record in expiring_oauth_records:
    # get the refresh token from the oauth record, refresh token is used to refresh the access token
    # decrypt the refresh token before send to the square oauth service
    decrypted_refresh_token = fernet.decrypt(auth_record['RefreshToken']['B']).decode('ASCII')
    # refresh the OAuth access token
    response = revoke_oauth_access_token(decrypted_refresh_token)
    if response.is_success():
      body = response.body
      # encrypt the access_token before save to db
      encrypted_access_token = fernet.encrypt(body['access_token'].encode('ASCII'))
      # update the oauth user in db
      update_oauth_record(
        merchant_id = auth_record['MerchantId']['S'],
        access_token = encrypted_access_token,
        expires_at = body['expires_at']
      )
      print('Access token successfully updated for merchant {0}.'.format(auth_record['MerchantId']['S']))
    elif response.is_error():
      print('''
        Authorization failed,
        Error: {0}
        Error Type: {1}
      '''.format(response.body['message'], response.body['type']))
  pass


@app.route('/createOAuthTable', methods=['GET'])
def create_oauth_table():
  '''The endpoint that create a OAuth table in db if not exists'''
  # create the OAuth table in dynamodb
  result = create_oauth_dynamodb_table()
  body = '<h1>Table {0}.</h1>'.format(result)
  return Response(body=html_template.format(body), status_code=200, headers={"Content-Type": "text/html"})
