/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squareup.connectexamples.ecommerce;

import com.squareup.square.Environment;
import com.squareup.square.api.PaymentsApi;
import com.squareup.square.models.*;
import com.squareup.square.SquareClient;
import com.squareup.square.exceptions.ApiException;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class Main {
  // The environment variable containing a Square Personal Access Token.
  // This must be set in order for the application to start.
  private static final String SQUARE_ACCESS_TOKEN_ENV_VAR = "SQUARE_ACCESS_TOKEN";

  // The environment variable containing a Square application ID.
  // This must be set in order for the application to start.
  private static final String SQUARE_APP_ID_ENV_VAR = "SQUARE_APPLICATION_ID";

  // The environment variable containing a Square location ID.
  // This must be set in order for the application to start.
  private static final String SQUARE_LOCATION_ID_ENV_VAR = "SQUARE_LOCATION_ID";

  // The environment variable indicate the square environment - sandbox or
  // production.
  // This must be set in order for the application to start.
  private static final String SQUARE_ENV_ENV_VAR = "ENVIRONMENT";

  private final SquareClient squareClient;
  private final String squareLocationId;
  private final String squareAppId;
  private final String squareEnvironment;

  public Main() throws ApiException {
    squareEnvironment = mustLoadEnvironmentVariable(SQUARE_ENV_ENV_VAR);
    squareAppId = mustLoadEnvironmentVariable(SQUARE_APP_ID_ENV_VAR);
    squareLocationId = mustLoadEnvironmentVariable(SQUARE_LOCATION_ID_ENV_VAR);

    squareClient = new SquareClient.Builder()
        .environment(Environment.fromString(squareEnvironment))
        .accessToken(mustLoadEnvironmentVariable(SQUARE_ACCESS_TOKEN_ENV_VAR))
        .userAgentDetail("sample_app_java_payment") // Remove or replace this detail when building your own app
        .build();
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  private String mustLoadEnvironmentVariable(String name) {
    String value = System.getenv(name);
    if (value == null || value.length() == 0) {
      throw new IllegalStateException(
          String.format("The %s environment variable must be set", name));
    }

    return value;
  }

  @RequestMapping("/")
  String index(Map<String, Object> model) throws InterruptedException, ExecutionException {

    // Get currency and country for location
    RetrieveLocationResponse locationResponse = getLocationInformation(squareClient).get();
    model.put("paymentFormUrl",
        squareEnvironment.equals("sandbox") ? "https://sandbox.web.squarecdn.com/v1/square.js"
            : "https://web.squarecdn.com/v1/square.js");
    model.put("locationId", squareLocationId);
    model.put("appId", squareAppId);
    model.put("currency", locationResponse.getLocation().getCurrency());
    model.put("country", locationResponse.getLocation().getCountry());
    model.put("idempotencyKey", UUID.randomUUID().toString());

    return "index";
  }

  @PostMapping("/process-payment")
  @ResponseBody
  PaymentResult processPayment(@RequestBody TokenWrapper tokenObject)
      throws InterruptedException, ExecutionException {
    // To learn more about splitting payments with additional recipients,
    // see the Payments API documentation on our [developer site]
    // (https://developer.squareup.com/docs/payments-api/overview).

    // Get currency for location
    RetrieveLocationResponse locationResponse = getLocationInformation(squareClient).get();
    String currency = locationResponse.getLocation().getCurrency();

    Money bodyAmountMoney = new Money.Builder()
        .amount(100L)
        .currency(currency)
        .build();

    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest.Builder(
        tokenObject.getToken(),
        tokenObject.getIdempotencyKey(),
        bodyAmountMoney)
        .build();

    PaymentsApi paymentsApi = squareClient.getPaymentsApi();
    return paymentsApi.createPaymentAsync(createPaymentRequest).thenApply(result -> {
      return new PaymentResult("SUCCESS", null);
    }).exceptionally(exception -> {
      ApiException e = (ApiException) exception.getCause();
      System.out.println("Failed to make the request");
      System.out.printf("Exception: %s%n", e.getMessage());
      return new PaymentResult("FAILURE", e.getErrors());
    }).join();
  }

  /**
   * Helper method that makes a retrieveLocation API call using the configured
   * locationId and returns the future containing the response
   *
   * @param squareClient the API client
   * @return a future that holds the retrieveLocation response
   */
  private CompletableFuture<RetrieveLocationResponse> getLocationInformation(
      SquareClient squareClient) {
    return squareClient.getLocationsApi().retrieveLocationAsync(squareLocationId)
        .thenApply(result -> {
          return result;
        })
        .exceptionally(exception -> {
          System.out.println("Failed to make the request");
          System.out.printf("Exception: %s%n", exception.getMessage());
          return null;
        });
  }
}
