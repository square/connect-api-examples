class AppleController < ApplicationController
  def domain_association
    send_file 'app/assets/apple-developer-merchantid-domain-association'
  end
end