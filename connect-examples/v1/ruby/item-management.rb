# Demonstrates creating, updating, and deleting an item with the Square Connect API.
# Replace the values of `ACCESS_TOKEN` and `LOCATION_ID` below before running this sample.
#
# This sample requires the Unirest gem. Download instructions are here:
# http://unirest.io/ruby.html

require 'unirest'
require 'json'

# Replace this value with your application's personal access token,
# available from your application dashboard (https://connect.squareup.com/apps)
ACCESS_TOKEN = 'REPLACE_ME'

# The ID of the location you want to create an item for.
# See payments-report.rb for an example of getting a business's location IDs.
LOCATION_ID = 'REPLACE_ME'

# The base URL for every Connect API request
CONNECT_HOST = 'https://connect.squareup.com'

# Standard HTTP headers for every Connect API request
REQUEST_HEADERS = {
  'Authorization' => 'Bearer ' + ACCESS_TOKEN,
  'Accept' => 'application/json',
  'Content-Type' => 'application/json'
}


# Creates a "Milkshake" item.
def create_item()

  request_body = {
    'name' => 'Milkshake',
    'variations' => [
      {
        'name' => 'Small',
        'pricing_type' => 'FIXED_PRICING',
        'price_money' => {
          'currency_code' => 'USD',
          'amount' => 400
        }
      }
    ]
  }

  response = Unirest.post CONNECT_HOST + '/v1/' + LOCATION_ID + '/items',
                  headers: REQUEST_HEADERS,
                  parameters: request_body.to_json

  if response.code == 200
    puts 'Successfully created item:'
    puts JSON.pretty_generate(response.body)
    return response.body
  else
    puts 'Item creation failed'
    puts response.body
    return nil
  end
end

# Updates the Milkshake item to rename it to "Malted Milkshake"
def update_item(item_id)
  request_body = {
    'name' => 'Malted Milkshake'
  }

  response = Unirest.put CONNECT_HOST + '/v1/' + LOCATION_ID + '/items/' + item_id,
                   headers: REQUEST_HEADERS,
                   parameters: request_body.to_json

  if response.code == 200
    puts 'Successfully updated item:'
    puts JSON.pretty_generate(response.body)
    return response.body
  else
    puts 'Item update failed'
    puts response.body
    return nil
  end
end

# Deletes the Malted Milkshake item.
def delete_item(item_id)
  response = Unirest.delete CONNECT_HOST + '/v1/' + LOCATION_ID + '/items/' + item_id,
                   headers: REQUEST_HEADERS

  if response.code == 200
    puts 'Successfully deleted item'
    return response.body
  else
    puts 'Item deletion failed'
    puts response.body
    return nil
  end
end

my_item = create_item()

# Update and delete the item only if it was successfully created
if my_item
  update_item(my_item['id'])
  delete_item(my_item['id'])
else
  puts 'Aborting'
  puts response.body
end
