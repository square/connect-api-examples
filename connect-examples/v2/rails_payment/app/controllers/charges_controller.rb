class ChargesController < ApplicationController
  skip_before_action :verify_authenticity_token
  def charge_card
    api_config = SquareConnect::Configuration.new()
    # Set 'host' to switch between sandbox env and production env
    # sandbox: connect.squareupsandbox.com
    # production: connect.squareup.com
    api_config.host = ENV['IS_PRODUCTION'] == 'false' ? 'connect.squareupsandbox.com' : 'connect.squareup.com'
    # Configure OAuth2 access token for authorization: oauth2
    api_config.access_token = Rails.application.secrets.square_access_token
    api_client = SquareConnect::ApiClient.new(api_config)

    payments_api = SquareConnect::PaymentsApi.new(api_client)

    # To learn more about splitting payments with additional recipients,
    # see the Payments API documentation on our [developer site]
    # (https://developer.squareup.com/docs/payments-api/overview).
    # Charge 1 dollar (100 cent)
    request_body = {
      :source_id => params[:nonce],
      :amount_money => {
        :amount => 100,
        :currency => 'USD'
      },
      :idempotency_key => SecureRandom.uuid
    }

    begin
      resp = payments_api.create_payment(request_body)
      @payment = resp.payment
    rescue SquareConnect::ApiError => e
      @error = e.response_body
    end
  end
end
