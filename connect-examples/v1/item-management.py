#!/usr/bin/python
#
# Demonstrates creating, updating, and deleting an item with the Square Connect API.
# Replace the value of the `access_token` variable below before running this script.
#
# To run this script from the command line:
# python item-management.py
#
# To install Python on Windows:
# https://www.python.org/download/

import httplib, urllib, json

# Your application's personal access token. Get this from your Apps page (https://connect.squareup.com/apps)
access_token = 'REPLACE_ME'

# The base URL for every Connect API request
connection = httplib.HTTPSConnection('connect.squareup.com')

# Standard HTTP headers for every Connect API request
request_headers = {'Authorization': 'Bearer ' + access_token,
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'}

# Creates a "Milkshake" item.
def create_item():
  print 'Creating item'
  request_body = ''' {
    "name": "Milkshake",
    "variations": [
      {
        "name": "Small",
        "pricing_type": "FIXED_PRICING",
        "price_money": {
          "currency_code": "USD",
          "amount": 400
        }
      }
    ]
  } '''
  connection.request('POST', '/v1/me/items', request_body, request_headers)
  response = connection.getresponse()
  response_body = json.loads(response.read())

  if response.status == 200:
    print 'Successfully created item:'

    # Pretty-print the returned Item object
    print json.dumps(response_body, sort_keys=True, indent=2, separators=(',', ': '))

    return response_body
  else:
    print 'Item creation failed'
    return None

# Updates the Milkshake item to rename it to "Malted Milkshake"
def update_item(item_id):
  print 'Updating item ' + item_id
  request_body = ''' {
    "name": "Malted Milkshake"
  } '''
  connection.request('PUT', '/v1/me/items/' + item_id, request_body, request_headers)
  response = connection.getresponse()
  response_body = json.loads(response.read())
  if response.status == 200:
    print 'Successfully updated item:'
    print json.dumps(response_body, sort_keys=True, indent=2, separators=(',', ': '))
    return response_body
  else:
    print 'Item update failed'
    return None

# Deletes the Malted Milkshake item.
def delete_item(item_id):
  print 'Deleting item ' + item_id
  connection.request('DELETE', '/v1/me/items/' + item_id, '', request_headers)
  response = connection.getresponse()
  response_body = json.loads(response.read())
  if response.status == 200:
    print 'Successfully deleted item'
    return response_body
  else:
    print 'Item deletion failed'
    return None

if __name__ == '__main__':
  my_item = create_item()

  # Update and delete the item only if it was successfully created
  if my_item:
    update_item(my_item['id'])
    delete_item(my_item['id'])
  else:
    print 'Aborting'
  connection.close()
