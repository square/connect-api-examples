class PaymentsController < ApplicationController
  include SquareApiHelper

  skip_before_action :verify_authenticity_token

  def process_payment
    # To learn more about splitting payments with additional recipients,
    # see the Payments API documentation on our [developer site]
    # (https://developer.squareup.com/docs/payments-api/overview).
    # Charge 1 dollar (100 cent)
    currency = square_api_client
      .locations
      .retrieve_location(location_id: ENV['SQUARE_LOCATION_ID'])
      .data
      .location[:currency]

    request_body = {
      :source_id => params[:token],
      :amount_money => {
        :amount => 100,
        :currency => currency
      },
      :idempotency_key => SecureRandom.uuid
    }

    resp = square_api_client.payments.create_payment(body: request_body)
    render json: resp.data.payment
  end
end
