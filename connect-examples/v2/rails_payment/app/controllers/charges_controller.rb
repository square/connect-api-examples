PRODUCT_COST = {
  "001" => 100,
  "002" => 4900,
  "003" => 500000
}

class ChargesController < ApplicationController

  def charge_card
    transactions_api = SquareConnect::TransactionsApi.new

    #check if product exists
    if !PRODUCT_COST.has_key? params[:product_id]
      render json: {:status => 400, :errors => [{"detail": "Product unavailable"}]  }
      return
    end

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

    data = {
      amount: amount,
      user: {
        name: params[:name],
        street_address_1: params[:street_address_1],
        street_address_2: params[:street_address_2],
        state: params[:state],
        zip: params[:zip],
        city: params[:city]
      },
      card: resp.transaction.tenders[0].card_details.card
    }

    # send receipt email to user
    ReceiptMailer.charge_email(params[:email],data).deliver_now if Rails.env == "development"

    render json: {:status => 200}
  end
end
