class SessionsController < ApplicationController
  # If you're using a strategy that POSTs during callback, you'll need to skip the authenticity token check for the callback action only. 
  skip_before_action :verify_authenticity_token, only: :create

  def create
    session[:auth] = auth_hash

    redirect_to sites_url
  end

  protected

  def auth_hash
    request.env['omniauth.auth']
  end
end