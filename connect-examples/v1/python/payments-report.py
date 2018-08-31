#!/usr/bin/python
#
# Demonstrates generating last year's payments report with the Square Connect API.
# Replace the value of the `access_token` variable below before running this script.
#
# This sample assumes all monetary amounts are in US dollars. You can alter the
# format_money function to display amounts in other currency formats.
#
# To run this script from the command line:
# python payments-report.py
#
# To install Python on Windows:
# https://www.python.org/download/

import httplib, urllib, json, locale, datetime
from urlparse import urlparse

# Your application's personal access token.
# Get this from your application dashboard (https://connect.squareup.com/apps)
access_token = 'REPLACE_ME'

# Standard HTTP headers for every Connect API request
request_headers = {'Authorization': 'Bearer ' + access_token,
                   'Accept': 'application/json',
                   'Content-Type': 'application/json'}

# The base URL for every Connect API request
connection = httplib.HTTPSConnection('connect.squareup.com')

# Uses the locale to format currency amounts correctly
locale.setlocale(locale.LC_ALL, 'en_US')

# Helper function to convert cent-based money amounts to dollars and cents
def format_money(amount):
  return locale.currency(amount / 100.)


# Obtains all of the business's location IDs. Each location has its own collection of payments.
def get_location_ids():
  request_path = '/v1/me/locations'
  connection.request('GET', request_path, '', request_headers)
  response = connection.getresponse()

  # Transform the JSON array of locations into a Python list
  locations = json.loads(response.read())

  if response.status != 200:
    raise ValueError("Error while getting locations", locations)

  location_ids = []
  for location in locations:
    location_ids.append(location['id'])

  return location_ids

# Downloads all of a business's payments from last year
def get_payments(location_ids):

  # Restrict all requests to the last calendar year, eight hours behind UTC
  # Make sure to URL-encode all parameters
  today = datetime.date.today()
  begin_time = datetime.date(today.year - 1, 1, 1)
  end_time = datetime.date(today.year, 1, 1)
  parameters = urllib.urlencode({'begin_time': begin_time,
                                 'end_time'  : end_time})

  payments = []

  # For each location...
  for location_id in location_ids:

    print 'Downloading payments for location with ID ' + location_id + '...'

    request_path = '/v1/' + location_id + '/payments?' + parameters
    more_results = True

    # ...as long as there are more payments to download from the location...
    while more_results:

      # ...send a GET request to /v1/LOCATION_ID/payments
      connection.request('GET', request_path, '', request_headers)
      response = connection.getresponse()
      response_body = json.loads(response.read());

      if response.status != 200:
        raise ValueError("Error while getting payments", response_body)

      # Read the response body JSON into the cumulative list of results
      payments = payments + response_body

      # Check whether pagination information is included in a response header, indicating more results
      pagination_header = response.getheader('link', '')
      if "rel='next'" not in pagination_header:
        more_results = False
      else:

        # Extract the next batch URL from the header.
        #
        # Pagination headers have the following format:
        # <https://connect.squareup.com/v1/LOCATION_ID/payments?batch_token=BATCH_TOKEN>;rel='next'
        # This line extracts the URL from the angle brackets surrounding it.
        next_batch_url = urlparse(pagination_header.split('<')[1].split('>')[0])

        request_path = next_batch_url.path + '?' + next_batch_url.query

  # Remove potential duplicate values from the list of payments
  seen_payment_ids = set()
  unique_payments = []

  for payment in payments:
    if payment['id'] in seen_payment_ids:
      continue
    seen_payment_ids.add(payment['id'])
    unique_payments.append(payment)

  return unique_payments


# Prints a sales report based on a list of payments
def print_sales_report(payments):

   # Variables for holding cumulative values of various monetary amounts
  collected_money = taxes = tips = discounts = processing_fees = \
  returned_processing_fees = net_money = refunds = 0

  # Add appropriate values to each cumulative variable
  for payment in payments:

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
    if payment['processing_fee_money']['amount'] < 0 and payment['refunded_money']['amount'] < 0:

      # ...calculate the percentage of the payment that was refunded...
      percentage_refunded = payment['refunded_money']['amount'] / float(payment['total_collected_money']['amount'])

      # ...and multiply that percentage by the original processing fee
      returned_processing_fees = returned_processing_fees + (payment['processing_fee_money']['amount'] * percentage_refunded)

  # Calculate the amount of pre-tax, pre-tip money collected
  base_purchases = collected_money - taxes - tips

  # Print a sales report similar to the Sales Summary in the merchant dashboard.
  year = datetime.date(datetime.date.today().year - 1, 1, 1).year
  print ''
  print '==SALES REPORT FOR ' + str(year) + '=='
  print 'Gross Sales:       ' + format_money(base_purchases - discounts)
  print 'Discounts:         ' + format_money(discounts)
  print 'Net Sales:         ' + format_money(base_purchases)
  print 'Tax collected:     ' + format_money(taxes)
  print 'Tips collected:    ' + format_money(tips)
  print 'Total collected:   ' + format_money(base_purchases + taxes + tips)
  print 'Fees:              ' + format_money(processing_fees)
  print 'Refunds:           ' + format_money(refunds)
  print 'Fees returned:     ' + format_money(returned_processing_fees)
  print 'Net total:         ' + format_money(net_money + refunds + returned_processing_fees)


if __name__ == '__main__':
  try:
    # Get all of last year's payments from all of the business's locations
    payments = get_payments(get_location_ids())

    # Print a sales summary report of the payments
    print_sales_report(payments)
  except ValueError as e:
      print(e.args)

  connection.close()
