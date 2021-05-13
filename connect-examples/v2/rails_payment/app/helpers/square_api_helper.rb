module SquareApiHelper
  def square_api_client
    # Create an instance of the API Client and initialize it with the credentials
    # for the Square account whose assets you want to manage.
    @api_client ||= Square::Client.new(
      access_token: Rails.application.secrets.square_access_token,
      environment: ENV['ENVIRONMENT']
    )
  end
end
