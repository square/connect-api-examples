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

import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.api.PaymentsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.*;

import java.util.Currency;
import java.util.Map;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class Main {
    // The environment variable containing a Square Personal Access Token.
    // This must be set in order for the application to start.
    private static final String SQUARE_ACCESS_TOKEN_ENV_VAR = "SQUARE_ACCESS_TOKEN";

    // The environment variable containing a Square application ID.
    // This must be set in order for the application to start.
    private static final String SQUARE_APP_ID_ENV_VAR = "SQUARE_APP_ID";

    // The environment variable containing a Square location ID.
    // This must be set in order for the application to start.
    private static final String SQUARE_LOCATION_ID_ENV_VAR = "SQUARE_LOCATION_ID";

    // The environment variable indicate the square environment - sandbox or production.
    // This must be set in order for the application to start.
    private static final String SQUARE_ENV_ENV_VAR = "SQUARE_ENV";

    private final ApiClient squareClient;
    private final String squareLocationId;
    private final String squareAppId;
    private final String squareEnvironment;

    public Main() throws ApiException {
        squareEnvironment = mustLoadEnvironmentVariable(SQUARE_ENV_ENV_VAR);
        squareAppId = mustLoadEnvironmentVariable(SQUARE_APP_ID_ENV_VAR);
        squareLocationId = mustLoadEnvironmentVariable(SQUARE_LOCATION_ID_ENV_VAR);

        squareClient = new ApiClient();
        // Configure OAuth2 access token for authorization: oauth2
        OAuth oauth2 = (OAuth) squareClient.getAuthentication("oauth2");
        oauth2.setAccessToken(mustLoadEnvironmentVariable(SQUARE_ACCESS_TOKEN_ENV_VAR));
        // Set 'basePath' to switch between sandbox env and production env
        // sandbox: https://connect.squareupsandbox.com
        // production: https://connect.squareup.com
        squareClient.setBasePath(squareEnvironment.equals("sandbox") ? "https://connect.squareupsandbox.com" : "https://connect.squareup.com");
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
    String index(Map<String, Object> model) throws ApiException {
        model.put("paymentFormUrl", squareEnvironment.equals("sandbox") ?
            "https://js.squareupsandbox.com/v2/paymentform" : "https://js.squareup.com/v2/paymentform");
        model.put("locationId", squareLocationId);
        model.put("appId", squareAppId);

        return "index";
    }

    @PostMapping("/charge")
    String charge(@ModelAttribute NonceForm form, Map<String, Object> model) throws ApiException {
        // To learn more about splitting payments with additional recipients,
        // see the Payments API documentation on our [developer site]
        // (https://developer.squareup.com/docs/payments-api/overview).
        CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest()
            .idempotencyKey(UUID.randomUUID().toString())
            .amountMoney(new Money().amount(1_00L).currency("USD"))
            .sourceId(form.getNonce())
            .note("From a Square sample Java app");

        PaymentsApi paymentsApi = new PaymentsApi(squareClient);

        CreatePaymentResponse response = paymentsApi.createPayment(createPaymentRequest);

        model.put("payment", response.getPayment());

        return "charge";
    }
}
