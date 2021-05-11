class SitesController < ApplicationController
  before_action :require_auth

  def index
    sites = square_client.list_sites
    
    redirect_to site_path sites.first.id
  end

  def show
    @sites = square_client.list_sites
    Rails.logger.debug(@sites)
    @site = @sites.select {|site| site.id === params[:id]}.first
    @snippet = square_client.get_snippet(@site['id'])
    @emoji = @snippet['content'][187] unless @snippet.nil?
  end

end