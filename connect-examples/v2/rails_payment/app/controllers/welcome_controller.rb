class WelcomeController < ApplicationController
  def index
  end

  def js
    render layout: "js"
  end
end
