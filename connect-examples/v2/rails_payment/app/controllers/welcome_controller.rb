class WelcomeController < ApplicationController
  include SquareApiHelper

  def index
    location = square_api_client
      .locations
      .retrieve_location(location_id: ENV['SQUARE_LOCATION_ID'])
      .data[:location]

    @country = location[:country]
    @currency = location[:currency]
    @idempotencyKey = SecureRandom.uuid

    render layout: 'index'
  end
end
