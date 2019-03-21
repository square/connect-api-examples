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
   5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
      to the Connect API.

   This sample requires the Unirest Java library. Download instructions are here:
   http://unirest.io/java.html

   This sample requires Java SE 6 or later.
 */

package com.squareup.oauthexample;

import com.squareup.connect.ApiException;
import com.squareup.connect.api.OAuthApi;
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

  // Your application's ID and secret, available from your application dashboard.
  private static final String _applicationId = "REPLACE_ME";
  private static final String _applicationSecret = "REPLACE_ME";

  // The base URL for every Connect API request
  private static final String _connectHost = "https://connect.squareup.com";

  // Serves the authorize link
  static class AuthorizeHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {

      // Reject non-GET requests
      if (!t.getRequestMethod().equals("GET")) {
        t.sendResponseHeaders(405, 0);
        t.getResponseBody().close();
      }

      final byte[] out = (String.format("<a href=\"https://connect.squareup.com/oauth2/authorize?client_id=%s\">Click here</a> ", _applicationId)
          + "to authorize the application.").getBytes("UTF-8");
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
      body.setClientId(_applicationId);
      body.setClientSecret(_applicationSecret);
      body.setCode(authorizationCode);
      body.setGrantType("authorization_code");

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
