class WelcomeController < ApplicationController
  include SquareApiHelper


  def index 
    render layout: 'index'
  end
end
