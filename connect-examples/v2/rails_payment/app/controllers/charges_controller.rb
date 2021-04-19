class ChargesController < ApplicationController
  skip_before_action :verify_authenticity_token
  def charge_card

    # Create an instance of the API Client and initialize it with the credentials
    # for the Square account whose assets you want to manage.
    api_client = Square::Client.new(
      access_token: Rails.application.secrets.square_access_token,
      environment: ENV['ENVIRONMENT']
    )

    # To learn more about splitting payments with additional recipients,
    # see the Payments API documentation on our [developer site]
    # (https://developer.squareup.com/docs/payments-api/overview).
    # Charge 1 dollar (100 cent)
    currency = api_client.locations.retrieve_location(location_id: ENV['SQUARE_LOCATION_ID'])
    request_body = {
      :source_id => params[:nonce],
      :amount_money => {
        :amount => 100,
        :currency => currency
      },
      :idempotency_key => SecureRandom.uuid
    }

    resp = api_client.payments.create_payment(body: request_body)
    if resp.success?
      @payment = resp.data.payment
    else
      @error = resp.errors
    end
  end
end
