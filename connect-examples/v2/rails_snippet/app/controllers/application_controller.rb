class ApplicationController < ActionController::Base

  def require_auth
    redirect_to welcome_path unless session[:auth]
  end

  def square_client
    square_api_client = Square::SquareApiClient.new
    square_api_client.access_token = session[:auth]['credentials']['token']

    return square_api_client
  end

end
