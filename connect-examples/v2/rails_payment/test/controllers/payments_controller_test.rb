require 'test_helper'

class PaymentsControllerTest < ActionController::TestCase
  test "should post process" do
    post :process
    assert_response :success
  end

end
