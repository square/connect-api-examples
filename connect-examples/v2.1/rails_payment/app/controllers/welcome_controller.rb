class WelcomeController < ApplicationController

 
  def index
  end

  def js
    render layout: "js"
  end

  def jquery
    render layout: "js"
  end

  def js_bootstrap
    render layout: "js_bootstrap"
  end

  def angular
    render layout: "angular"
  end

  def react
    render layout: "react"
  end

  def ember
    render layout: "ember"
  end

end
