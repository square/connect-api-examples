require 'test_helper'

class ChargesControllerTest < ActionController::TestCase
  test "should get charge_card" do
    get :charge_card
    assert_response :success
  end

end
