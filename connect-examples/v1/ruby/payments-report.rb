# Demonstrates generating a 2014 payments report with the Square Connect API.
# Replace the value of the `$access_token` variable below before running this script.
#
# This sample assumes all monetary amounts are in US dollars. You can alter the
# formatMoney function to display amounts in other currency formats.
#
# This sample requires the Unirest Ruby gem. Download instructions are here:
# http://unirest.io/ruby.html

require 'set'
require 'unirest'
require 'uri'


# Replace this value with your application's personal access token,
# available from your application dashboard (https://connect.squareup.com/apps)
$access_token = 'REPLACE_ME'

# The base URL for every Connect API request
$connect_host = 'https://connect.squareup.com'

# Helper function to convert cent-based money amounts to dollars and cents
def format_money(money)
  money_string = format("$%.2f", (money.abs/100.to_f))
  if money < 0
    money_string = '-' + money_string
  end
  return money_string
end

# Retrieves all of a merchant's payments from 2014
def get_2014_payments()

  # Restrict the request to the 2014 calendar year, eight hours behind UTC
  # Make sure to URL-encode all parameters
  parameters = URI.encode_www_form(
    'begin_time' => '2014-01-01T00:00:00-08:00',
    'end_time'   => '2015-01-01T00:00:00-08:00'
  )

  # Standard HTTP headers for every Connect API request
  request_headers = {
  	'Authorization' => 'Bearer ' + $access_token,
  	'Accept' => 'application/json',
  	'Content-Type' => 'application/json'
  }

  payments = []
  request_path = $connect_host + '/v1/me/payments?' + parameters
  more_results = true

  while more_results do

    # Send a GET request to the List Payments endpoint
    response = Unirest.get request_path,
    					   headers: request_headers,
    					   parameters: parameters

    # Read the converted JSON body into the cumulative array of results
    payments += response.body

    # Check whether pagination information is included in a response header, indicating more results
    if response.headers.has_key?(:link)
      pagination_header = response.headers[:link]
      if pagination_header.include? "rel='next'"

        # Extract the next batch URL from the header.
        #
        # Pagination headers have the following format:
        # <https://connect.squareup.com/v1/MERCHANT_ID/payments?batch_token=BATCH_TOKEN>;rel='next'
        # This line extracts the URL from the angle brackets surrounding it.
        request_path = pagination_header.split('<')[1].split('>')[0]
      else
        more_results = false
      end
    else
      more_results = false
    end
  end

  # Remove potential duplicate values from the list of payments
  seen_payment_ids = Set.new
  unique_payments = []
  for payment in payments
    if seen_payment_ids.include? payment['id']
      next 
    end
    seen_payment_ids.add(payment['id'])
    unique_payments.push(payment)
  end

  return unique_payments

end

# Prints a sales report based on an array of payments
def print_sales_report(payments)

  # Variables for holding cumulative values of various monetary amounts
  collected_money = taxes = tips = discounts = processing_fees = 0
  returned_processing_fees = net_money = refunds = 0

  # Add appropriate values to each cumulative variable
  for payment in payments
    collected_money = collected_money + payment['total_collected_money']['amount']
    taxes           = taxes           + payment['tax_money']['amount']
    tips            = tips            + payment['tip_money']['amount']
    discounts       = discounts       + payment['discount_money']['amount']
    processing_fees = processing_fees + payment['processing_fee_money']['amount']
    net_money       = net_money       + payment['net_total_money']['amount']
    refunds         = refunds         + payment['refunded_money']['amount']


    # When a refund is applied to a credit card payment, Square returns to the merchant a percentage 
    # of the processing fee corresponding to the refunded portion of the payment. This amount
    # is not currently returned by the Connect API, but we can calculate it as shown:

    # If a processing fee was applied to the payment AND some portion of the payment was refunded...
    if payment['processing_fee_money']['amount'] < 0 && payment['refunded_money']['amount'] < 0

        # ...calculate the percentage of the payment that was refunded...
        percentage_refunded = payment['refunded_money']['amount'] / 
                              payment['total_collected_money']['amount'].to_f

        # ...and multiply that percentage by the original processing fee
        returned_processing_fees = returned_processing_fees + 
                                   (payment['processing_fee_money']['amount'] * percentage_refunded)
    end
  end

  

  # Calculate the amount of pre-tax, pre-tip money collected
  base_purchases = collected_money - taxes - tips

  # Print a sales report similar to the Sales Summary in the merchant dashboard.
  puts ''
  puts '==SALES REPORT FOR 2014=='
  puts 'Gross Sales:       ' + format_money(base_purchases - discounts)
  puts 'Discounts:         ' + format_money(discounts)
  puts 'Net Sales:         ' + format_money(base_purchases)
  puts 'Tax collected:     ' + format_money(taxes)
  puts 'Tips collected:    ' + format_money(tips)
  puts 'Total collected:   ' + format_money(base_purchases + taxes + tips)
  puts 'Fees:              ' + format_money(processing_fees)
  puts 'Refunds:           ' + format_money(refunds)
  puts 'Fees returned:     ' + format_money(returned_processing_fees)
  puts 'Net total:         ' + format_money(net_money + refunds + returned_processing_fees)
end

# Call the functions defined above
payments = get_2014_payments()
print_sales_report(payments)
