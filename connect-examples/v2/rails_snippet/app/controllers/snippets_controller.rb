class SnippetsController < ApplicationController
  before_action :require_auth
  before_action :validate_emoji, only: :create

  def create
    @emoji = create_params[:emoji]
    square_client.upsert_snippet(params[:site_id], render_to_string(partial: 'snippets/inject'))

    redirect_to site_path params[:site_id]
  end

  def destroy
    square_client.delete_snippet params[:site_id]
    redirect_to site_path params[:site_id]
  end


  private

  def validate_emoji
    render json: {error: "Invalid Emoji"}, status: 400 unless ["👉","😁", "👻", "🥕", "💯", "🚀"].include?(create_params[:emoji])
  end

  def create_params
    params.require(:snippet).permit(:emoji)
  end
end

