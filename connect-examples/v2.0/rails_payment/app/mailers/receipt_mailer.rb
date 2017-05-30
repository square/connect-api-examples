class ReceiptMailer < ApplicationMailer
	default from: 'notifications@example.com'
 
  def charge_email(email, data)
  	@data = data
    mail(to: email, subject: 'Item purchase receipt')
  end

end
