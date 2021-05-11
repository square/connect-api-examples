module Square
  Site = Struct.new(:id, :site_title, :domain, :is_published, :created_at, :updated_at, keyword_init: true)
  Snippet = Struct.new(:id, :site_id, :content, :created_at, :updated_at, keyword_init: true)

  class SquareApiClient
    attr_writer :access_token
    CONNECT_BASE_URL =  'https://connect.squareup.com'
    
    # GET /v2/sites
    def list_sites
      response = RestClient.get(build_path('/v2/sites'), get_headers)
      JSON.parse(response)["sites"].map {|site| Site.new(site)}
    end

    # GET /v2/sites/:site_id/snippet 
    def get_snippet(site_id)
      # rescue nil is not recommended for a production application 
      # this is a quick and dirty way of handling 404 responses for the sample
      response = RestClient.get(build_path("/v2/sites/#{site_id}/snippet"), get_headers) rescue nil
      Snippet.new(JSON.parse(response)["snippet"]) unless response.nil?
    end

    # POST /v2/sites/:site_id/snippet
    def upsert_snippet(site_id, content)
      request_body = JSON.generate({"snippet": {"content": content}})
      response = RestClient.post(build_path("/v2/sites/#{site_id}/snippet"), request_body, get_headers)
      Snippet.new(JSON.parse(response)["snippet"])
    end

    # DELETE /v2/sites/:site_id/snippet
    def delete_snippet(site_id)
      response = RestClient.delete(build_path("/v2/sites/#{site_id}/snippet"), get_headers)
    end

    private 

    def build_path(path)
      "#{CONNECT_BASE_URL}#{path}"
    end

    def get_headers
      {
        'Content-Type': 'application/json', 
        'Authorization': "Bearer #{@access_token}"
      }
    end
  end
end