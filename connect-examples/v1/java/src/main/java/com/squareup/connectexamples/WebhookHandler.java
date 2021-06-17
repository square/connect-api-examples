/*
  Demonstrates creating, updating, and deleting an item with the Square Connect API.
  Replace the value of the `_accessToken` variable below before running this sample.

  This sample requires the Unirest Java library. Download instructions are here:
  http://unirest.io/java.html

  This sample requires Java SE 6 or later.
 */

package com.squareup.connectexamples;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;
import org.apache.commons.codec.binary.Base64;

import org.json.JSONObject;


public class WebhookHandler {

  // Replace this value with your application's personal access token,
  // available from your application dashboard (https://connect.squareup.com/apps)
  private static final String _accessToken = "REPLACE_ME";

  // Your application's webhook signature key, available from your application dashboard
  private static final String _webhookSignatureKey = "REPLACE_ME";

  // The URL that this server is listening on (e.g., 'http://www.example.com/events')
  // Note that to receive notifications from Square, this cannot be a localhost URL
  private static final String _webhookUrl = "REPLACE_ME";

  // The base URL for every Connect API request
  private static final String _connectHost = "https://connect.squareup.com";

  static class EventsHandler implements HttpHandler {

    // Handles all incoming webhook notifications
    public void handle(HttpExchange t) throws IOException {

      System.out.println("Request received");

      // Reject non-POST requests
      if (!t.getRequestMethod().equals("POST")) {
        t.sendResponseHeaders(405, 0);
        t.getResponseBody().close();
      }

      Headers requestHeaders = t.getRequestHeaders();
      String callbackSignature = requestHeaders.get("X-square-signature").get(0);
      String callbackBody = IOUtils.toString(t.getRequestBody(), (String)null);

      if (!isValidCallback(callbackBody, callbackSignature)) {
        System.out.println("Webhook event with invalid signature detected!");
        t.sendResponseHeaders(200, 0);
        t.getResponseBody().close();
        return;
      }

      JSONObject requestBody = new JSONObject(callbackBody);
      if (requestBody.has("event_type") && requestBody.getString("event_type").equals("PAYMENT_UPDATED")) {

        // Get the ID of the updated payment
        String paymentId = requestBody.getString("entity_id");

        // Get the ID of the payment's associated location
        String locationId = requestBody.getString("location_id");
        HttpResponse<JsonNode> response;
        try {
          response = Unirest.get(_connectHost + "/v1/" + locationId + "/payments/" + paymentId).asJson();
        } catch (UnirestException e) {
          System.out.println("Failed to retrieve payment details");
          return;
        }
        System.out.println(response.getBody().getObject());
      }

      t.sendResponseHeaders(200, 0);
      t.getResponseBody().close();
    }
  }

  // Validates HMAC-SHA1 signatures included in webhook notifications
  // to ensure notifications came from Square
  public static boolean isValidCallback(String callbackBody, String callbackSignature) {

    // Combine your webhook notification URL and the JSON body of the incoming request into a single string
    String stringToSign = _webhookUrl + callbackBody;

    String result;

    // Generate the HMAC-SHA1 signature of the string, signed with your webhook signature key
    try {
      SecretKeySpec signingKey = new SecretKeySpec(_webhookSignatureKey.getBytes(), "HmacSHA1");
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signingKey);
      byte[] rawHmac = mac.doFinal(stringToSign.getBytes());
      result = Base64.encodeBase64String(rawHmac);
    } catch (Exception e){
      return false;
    }

    // Hash the two signatures a second time (to protect against timing attacks) and compare them
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-1");
      String hashedCallbackSignature = new String(digest.digest(callbackSignature.getBytes()));
      String hashedResult = new String(digest.digest(result.getBytes()));

      return hashedCallbackSignature.equals(hashedResult);
    } catch (NoSuchAlgorithmException e){
      return false;
    }
  }

  // Start up the server, listening on port 8000 at the relative path '/events'
  public static void main(String[] args) {

    // Set headers common to every Connect API request
    Unirest.setDefaultHeader("Authorization", "Bearer " + _accessToken);
    Unirest.setDefaultHeader("Content-Type", "application/json");
    Unirest.setDefaultHeader("Accept", "application/json");

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
      server.createContext("/events", new EventsHandler());
      server.setExecutor(null);
      server.start();
    } catch (IOException e) {
      System.out.println("Server startup failed. Exiting.");
      System.exit(1);
    }
  }
}
