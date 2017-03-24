/*
 * Copyright 2017 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.catalog.demo.api;

import com.google.gson.JsonSyntaxException;
import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.GsonProvider;
import com.squareup.catalog.demo.util.Utils;
import com.squareup.resources.Error;
import com.squareup.resources.ErrorResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Base class used to implement APIs.
 */
public class BaseApi {

  public final String baseUrl;
  public final String accessToken;
  private final Logger logger;

  protected BaseApi(String baseUrl, String accessToken, Logger logger) {
    this.baseUrl = baseUrl;
    this.accessToken = accessToken;
    this.logger = logger;
  }

  /**
   * Sends a GET request to the API.
   *
   * @param action the action to perform
   * @param responseClass the expected response type from the server
   * @return the response from the server
   */
  protected <RES> RES get(String action, Class<RES> responseClass) throws IOException {
    String url = baseUrl + action;
    HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Authorization", "Bearer " + accessToken);
    logger.info("GET " + url);

    // Read the response.
    return parseResponse(connection, responseClass);
  }

  /**
   * Sends a POST request to the API.
   *
   * @param action the action to perform
   * @param request the request body
   * @param responseClass the expected response type from the server
   * @return the response from the server
   */
  protected <REQ, RES> RES post(String action, REQ request, Class<RES> responseClass)
      throws IOException {
    String url = baseUrl + action;
    HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Authorization", "Bearer " + accessToken);

    // Create the request.
    logger.info("POST " + url);
    connection.setDoOutput(true);
    OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
    output.write(GsonProvider.gson().toJson(request));
    output.flush();
    output.close();

    // Read the response.
    return parseResponse(connection, responseClass);
  }

  /**
   * Parse the response from the server into the specified response class. If the response contains
   * errors, they'll be logged and null is returned.
   *
   * @param connection the connection that contains the response to parse
   * @param responseClass the expected response type from the server
   * @return the response, or null if an error occurs
   */
  private <RES> RES parseResponse(HttpsURLConnection connection, Class<RES> responseClass)
      throws IOException {
    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      InputStreamReader isr = new InputStreamReader(connection.getInputStream());
      RES response = GsonProvider.gson().fromJson(isr, responseClass);
      return response;
    } else {
      logHttpError(connection);
      return null;
    }
  }

  /**
   * Logs errors received from the Catalog API.
   *
   * @param connection the connection that returned the error
   * @throws IOException if there is an error reading the response
   */
  void logHttpError(HttpURLConnection connection)
      throws IOException {
    int responseCode = connection.getResponseCode();
    String responseMessage = connection.getResponseMessage();
    InputStream errorStream = connection.getErrorStream();
    if (errorStream != null) {
      String responseBody = Utils.toString(errorStream);
      try {
        // If the response includes a body, it means that he server returned some error message.
        ErrorResponse response = GsonProvider.gson().fromJson(responseBody, ErrorResponse.class);
        for (Error error : response.errors) {
          logger.error("[" + error.code.toString() + "] " + error.detail);
        }
      } catch (JsonSyntaxException e) {
        // If the error message isn't in JSON format, log it directly.
        logger.error("[" + responseCode + " " + responseMessage + "] " + responseBody);
      }
    } else {
      // If the server did not return a body, we probably ran into some kind of connection error.
      logger.error(responseCode + " (" + responseMessage + ")");
    }
  }
}
