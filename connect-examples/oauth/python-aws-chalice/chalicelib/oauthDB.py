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

'''This module defines all the OAuth table database operations'''

import boto3
import os
from datetime import datetime, timedelta

# initialize dynamodb client
dynamodb_client = boto3.client('dynamodb')
oauth_table_name = os.environ['oauth_table_name'];

def create_oauth_dynamodb_table():
  '''Create the OAuth table in Dynamodb'''
  # Create the DynamoDB table.
  dynamodb_client = boto3.client('dynamodb')
  try:
    response = dynamodb_client.create_table(
        AttributeDefinitions = [
            {
                'AttributeName': 'MerchantId',
                'AttributeType': 'S',
            }
        ],
        KeySchema = [
            {
                'AttributeName': 'MerchantId',
                'KeyType': 'HASH',
            }
        ],
        ProvisionedThroughput = {
            'ReadCapacityUnits': 5,
            'WriteCapacityUnits': 5,
        },
        TableName = oauth_table_name,
    )
  except dynamodb_client.exceptions.ResourceInUseException:
    # the table exists, ignore the exception and return
    return 'Exist'
  except Exception as e:
    # unknow issue, print the error and raise the exception
    print (e)
    raise
  return 'Created'


def add_oauth_record(merchant_id, refresh_token, access_token, expires_at):
  '''Add or overwrite the OAuth record for the given merchant.

  Parameters
  ----------
  merchant_id : str
    The id of the merchant who granted this appliaction permissions.

  refresh_token : str
    The refresh token.

  access_token : str
    A valid OAuth access token.

  expires_at : str
    The date when access_token expires.
  '''
  # get the current UTC time in ISO 8610 format
  current_time_str = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
  # add or update the OAuth record
  dynamodb_client.put_item(
    TableName = oauth_table_name,
    Item = {
      'MerchantId': {'S': merchant_id },
      'RefreshToken': {'B': refresh_token },
      'AccessToken': {'B': access_token },
      'AccessTokenExpiresAt': {'S': expires_at },
      'AccessTokenUpdatedAt': {'S': current_time_str },
      'CreatedAt': {'S': current_time_str },
    }
  )
  pass


def get_expiring_oauth_records():
  '''Get the expiring OAuth records.

  The expiring OAuth record is defined as the OAuth record with access token is 7 days or older.
  '''
  # As recommended by Square, we should renew all token older than 7 days
  # so the expiration date should be 30 - 8 = 22 days ahead
  expire_cut_date = datetime.strftime(datetime.now() + timedelta(22), '%Y-%m-%d')
  print('Renew all the access token that is earlier than ' + expire_cut_date)
  # For simplicity, we scan all the records and filter with the expiring cut date
  # You can find more efficient way to query the oauth records
  expiring_oauth_records_response = dynamodb_client.scan(
    TableName = oauth_table_name,
    FilterExpression = 'AccessTokenExpiresAt <= :exDate',
    ExpressionAttributeValues = { ':exDate': { 'S': expire_cut_date } }
  )
  return expiring_oauth_records_response['Items']


def update_oauth_record(merchant_id, access_token, expires_at):
  '''Update the access token of OAuth record for the specified merchant.

  Parameters
  ----------
  merchant_id : str
    The id of the merchant who granted this appliaction permissions.

  access_token : str
    A valid OAuth access token.

  expires_at : str
    The date when access_token expires.
  '''
  # get the current UTC time in ISO 8610 format
  current_time_str = datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ')
  # update the OAuth record
  update_item_response = dynamodb_client.update_item(
    TableName = oauth_table_name,
    Key = { 'MerchantId': { 'S': merchant_id } },
    AttributeUpdates = {
      'AccessToken': {
        'Value': { 'B': access_token },
        'Action': 'PUT'
      },
      'AccessTokenExpiresAt': {
        'Value': { 'S': expires_at },
        'Action': 'PUT'
      },
      'AccessTokenUpdatedAt': {
        'Value': { 'S': current_time_str },
        'Action': 'PUT'
      },
    }
  )
  pass

def get_oauth_record(merchant_id):
  '''Get the OAuth record for the specified merchant.

  Parameters
  ----------
  merchant_id : str
    The id of the merchant whose OAuth tokens are to be removed.
  '''
  get_item_response = dynamodb_client.get_item(
    TableName = oauth_table_name,
    Key = { 'MerchantId': { 'S': merchant_id } },
  )
  return get_item_response.get('Item')

def remove_oauth_record(merchant_id):
  '''Remove the OAuth record for the specified merchant.

  This method should be called after the OAuth tokens of the merchant is revoked.

  Parameters
  ----------
  merchant_id : str
    The id of the merchant whose OAuth tokens are to be removed.
  '''
  delete_item_response = dynamodb_client.delete_item(
    TableName = oauth_table_name,
    Key = { 'MerchantId': { 'S': merchant_id } },
  )
  pass