# Demonstrates creating, updating, and deleting an item with the Square Connect API.
# Replace the value of the `$access_token` variable below before running this sample.
#
# This sample requires the Unirest gem. Download instructions are here:
# http://unirest.io/ruby.html

require 'unirest'
require 'json'

# Replace this value with your application's personal access token,
# available from your application dashboard (https://connect.squareup.com/apps)
$access_token = 'REPLACE_ME'

# The base URL for every Connect API request
$connect_host = 'https://connect.squareup.com'

# Standard HTTP headers for every Connect API request
$request_headers = {
  'Authorization' => 'Bearer ' + $access_token,
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

  response = Unirest.post $connect_host + '/v1/me/items',
                  headers: $request_headers,
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

  response = Unirest.put $connect_host + '/v1/me/items/' + item_id,
                   headers: $request_headers,
                   parameters: request_body.to_json

  if response.code == 200
    puts 'Successfully updated item:'
    puts JSON.pretty_generate(response.body)
    return response.body
  else
    puts 'Item update failed'
    return nil
  end
end

# Deletes the Malted Milkshake item.
def delete_item(item_id)
  response = Unirest.delete $connect_host + '/v1/me/items/' + item_id,
                   headers: $request_headers

  if response.code == 200
    puts 'Successfully deleted item'
    return response.body
  else
    puts 'Item deletion failed'
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
end
