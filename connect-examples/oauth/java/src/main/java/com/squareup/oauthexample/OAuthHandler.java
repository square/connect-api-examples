/*
   This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow:

   1. A merchant clicks the authorization link served by the root path (http://localhost:8000/)
   2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
      is already signed in to Square, and if the merchant has already authorized your application,
      the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
   3. Square sends a request to your application's Redirect URL
      (which should be set to http://localhost:8000/callback on your application dashboard)
   4. The server extracts the authorization code provided in Square's request and passes it
      along to the Obtain Token endpoint.
   5. The Obtain Token endpoint returns an access token and refresh token your
      application can use in subsequent requests to the Connect API.

   This sample requires the Unirest Java library. Download instructions are here:
   http://unirest.io/java.html

   This sample requires Java SE 6 or later.
 */

package com.squareup.oauthexample;

import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.OAuthApi;
import com.squareup.connect.ApiClient;
import com.squareup.connect.models.ObtainTokenRequest;
import com.squareup.connect.models.ObtainTokenResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import org.json.JSONObject;

public class OAuthHandler {


  // FOR SANDBOX TESTING:
  // If you are testing the OAuth flow in the sandbox, use your sandbox application
  // ID and secret. You MUST also set the CONNECT_HOST to "https://connect.squareupsandbox.com"

  // Your application's ID and secret, available from your application dashboard.
  private static final String APPLICATION_ID =  "APPLICATION_ID";
  private static final String APPLICATION_SECRET =  "APPLICATION_SECRET";
  // The base URL for every Connect API request
  private static final String CONNECT_HOST = "https://connect.squareup.com";
  private static final String SCOPES = "MERCHANT_PROFILE_READ PAYMENTS_READ SETTLEMENTS_READ BANK_ACCOUNTS_READ";
  // Serves the authorize link
  static class AuthorizeHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {

      // Reject non-GET requests
      if (!t.getRequestMethod().equals("GET")) {
        t.sendResponseHeaders(405, 0);
        t.getResponseBody().close();
      }


      String authorizeURL = String.format(
        "<a href=\"%s/oauth2/authorize?client_id=%s&scope=%s\">Click here</a> ",
        CONNECT_HOST,
        APPLICATION_ID,
        SCOPES)
        + "to authorize the application.";

      System.out.println(authorizeURL);

      final byte[] out = (authorizeURL).getBytes("UTF-8");
      t.sendResponseHeaders(200, out.length);
      t.getResponseBody().write(out);
      t.getResponseBody().close();
    }
  }

  // Serves requests from Square to your application's redirect URL
  // Note that you need to set your application's Redirect URL to
  // http://localhost:8000/callback from your application dashboard
  static class CallbackHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {

      System.out.println("Request received");

      if (!t.getRequestMethod().equals("GET")) {
        t.sendResponseHeaders(405, 0);
        t.getResponseBody().close();
      }

      // Extract the returned authorization code from the URL
      URI requestUri = t.getRequestURI();
      List<NameValuePair> queryParameters = URLEncodedUtils.parse(requestUri, "UTF-8");
      String authorizationCode = null;
      for (NameValuePair param : queryParameters) {
        if(param.getName().equals("code")) {
          authorizationCode = param.getValue();
          break;
        }
      }

      if (authorizationCode == null) {

        // The request to the Redirect URL did not include an authorization code.
        // Something went wrong.
        System.out.println("Authorization failed!");
        t.sendResponseHeaders(200, 0);
        t.getResponseBody().close();
        return;
      }

      ObtainTokenRequest body = new ObtainTokenRequest();
      body.setClientId(APPLICATION_ID);
      body.setClientSecret(APPLICATION_SECRET);
      body.setCode(authorizationCode);
      body.setGrantType("authorization_code");

      //The default base path is 'https://connect.squareup.com'
      //When you are testing OAuth in the Square Sandbox, this resets
      //the base path to the Square Sandbox domain
      ApiClient apiClient = Configuration.getDefaultApiClient();
      apiClient.setBasePath(CONNECT_HOST);

      OAuthApi oAuthApi = new OAuthApi();
      ObtainTokenResponse response = null;
      try {
        response = oAuthApi.obtainToken(body);
      } catch (ApiException e) {
        System.out.print(e.getResponseBody());
        t.sendResponseHeaders(405, 0);
        t.getResponseBody().close();
        return;
      }

      if (response != null) {
        System.out.println("Access token: " + response.getAccessToken());
        System.out.println("Refresh token: " + response.getRefreshToken());
        System.out.println("Authorization succeeded!");
        t.sendResponseHeaders(200, 0);
        t.getResponseBody().close();
      }

      t.sendResponseHeaders(200, 0);
      t.getResponseBody().close();
    }
  }

  // Start up the server, listening on port 8000
  public static void main(String[] args) {

    try {
      int portNumber = 8000;
      HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
      server.createContext("/callback", new CallbackHandler());
      server.createContext("/", new AuthorizeHandler());
      server.setExecutor(null);
      server.start();
      System.out.println("Listening on port " + portNumber);
    } catch (IOException e) {
      System.out.println("Server startup failed. Exiting.");
      System.exit(1);
    }
  }
}
