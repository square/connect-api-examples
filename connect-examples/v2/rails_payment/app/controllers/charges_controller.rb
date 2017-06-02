PRODUCT_COST = {
  "001" => 100,
  "002" => 4900,
  "003" => 500000
}

class ChargesController < ApplicationController

  def charge_card
    SquareConnect.configure do |config|
      # Configure OAuth2 access token for authorization: oauth2
      config.access_token = Rails.application.secrets.square_access_token
    end

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

    # The SDK throws an exception if a Connect endpoint responds with anything besides 200 (success).
    # This block catches any exceptions that occur from the request.
    locations_api = SquareConnect::LocationsApi.new
    begin
      locations = locations_api.list_locations.locations
    rescue SquareConnect::ApiError => e
      Rails.logger.error("Error encountered while loading the locations: #{e.message}")
      render json: {:status => 500, :errors => JSON.parse(e.response_body)["errors"] }
      return
    end

    location = locations.find do |l|
      Array(l.capabilities).include?(SquareConnect::LocationCapability::PROCESSING)
    end

    if location.nil?
      Rails.logger.error("Can't find a location that can process payments")
      render json: {:status => 422, :errors => {locations: 'cat not process payments'} }
      return
    end

    begin
      resp = transactions_api.charge(location.id, request_body)
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
