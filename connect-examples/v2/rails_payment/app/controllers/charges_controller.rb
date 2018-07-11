class ChargesController < ApplicationController

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
      # print entire transaction to terminal
      puts resp.transaction
      render json: {:status => 200}
    rescue SquareConnect::ApiError => e
      Rails.logger.error("Error encountered while charging card:: #{e.message}")
      render json: {:status => 400, :errors => JSON.parse(e.response_body)["errors"]}
      return
    end
  end
end
