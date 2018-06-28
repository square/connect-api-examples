PRODUCT_COST = {
  "001" => 100,
  "002" => 4900,
  "003" => 500000
}

class ChargesController < ApplicationController

  def charge_card
    transactions_api = SquareConnect::TransactionsApi.new

    # Check if product exists
    if !PRODUCT_COST.has_key? params[:product_id]
      render json: {:status => 400, :errors => [{"detail": "Product unavailable"}]  }
      return
    end

    # To learn more about splitting transactions with additional recipients,
    # see the Transactions API documentation on our [developer site]
    # (https://docs.connect.squareup.com/payments/transactions/overview#mpt-overview).
    amount = PRODUCT_COST[params[:product_id]]
    request_body = {
      :card_nonce => params[:nonce],
      :amount_money => {
        :amount => amount,
        :currency => 'USD'
      },
      :idempotency_key => SecureRandom.uuid
    }

    location_id = Rails.application.secrets.square_location_id
    begin
      resp = transactions_api.charge(location_id, request_body)
    rescue SquareConnect::ApiError => e
      Rails.logger.error("Error encountered while charging card:: #{e.message}")
      render json: {:status => 400, :errors => JSON.parse(e.response_body)["errors"]}
      return
    end
    puts resp

    render json: {:status => 200}
  end
end
