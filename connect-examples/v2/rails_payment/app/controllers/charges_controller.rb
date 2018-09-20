class ChargesController < ApplicationController
  skip_before_action :verify_authenticity_token
  def charge_card
    transactions_api = SquareConnect::TransactionsApi.new

    # To learn more about splitting transactions with additional recipients,
    # see the Transactions API documentation on our [developer site]
    # (https://docs.connect.squareup.com/payments/transactions/overview#mpt-overview).
    # Charge 1 dollar (100 cent)
    request_body = {
      :card_nonce => params[:nonce],
      :amount_money => {
        :amount => 100,
        :currency => 'USD'
      },
      :idempotency_key => SecureRandom.uuid
    }

    location_id = Rails.application.secrets.square_location_id
    begin
      resp = transactions_api.charge(location_id, request_body)
      @transaction = resp.transaction
    rescue SquareConnect::ApiError => e
      @error = e.response_body
    end
  end
end
