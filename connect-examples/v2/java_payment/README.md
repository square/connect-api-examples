# Useful Links

- [Square Java SDK](https://developer.squareup.com/docs/sdks/java)
- [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
- [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
- [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: Java

This example hosts a payment form in Java. It is a Spring Boot app and requires Java 8 or newer. There are two sections in this ReadMe.

- [Setup](#setup) - Provides instructions for you to download and run the app.
- [Application Flow](#application-flow) - Provides an overview of how the Web Payments SDK integrates in the Java example.

## Setup

1. Create a Square application. If you haven't done this yet, you can quickly set one up in the [Developer Dashboard](https://developer.squareup.com/apps).

1. This app runs from the command line using Maven. The application expects four environment variables
to be set: `ENVIRONMENT`, `SQUARE_APPLICATION_ID`, `SQUARE_LOCATION_ID` and `SQUARE_ACCESS_TOKEN`, 
which correspond to either your sandbox or production credentials.
        <b>WARNING</b>: never save your credentials in your code.

1. Open the command line, and run the following command to start the app:

    ```bash
    ENVIRONMENT=<sandbox or production> SQUARE_APPLICATION_ID=replace_me SQUARE_ACCESS_TOKEN=replace_me SQUARE_LOCATION_ID=replace_me mvn spring-boot:run
    ```

    NOTE: If the credentials are not set or are invalid, the app will fail during startup.
    
1. Open a browser and navigate to [localhost:3000](http://localhost:3000). The default port used is `3000`, but this can be configured in the `application.properties` file.

1. Test with different payment options. For more information on testing in sandbox mode, follow the guide: [Testing using the API sandbox](https://developer.squareup.com/docs/testing/sandbox).


## Application Flow

This Java web application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.

   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).

2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2
   **Payments** API, you charge the payment source using the secure payment token.

The following sections describe how the Java sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **src/main/resources/templates/index.html** file. The page also downloads and executes the following scripts:

**Square Web Payments SDK** - It is a library that provides the Payment objects you use in sq-payment-flow.js. For more information about the library, see [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments).

**sq-payment-flow.js** - This code provides two things:

- Initializes objects for various supported payment methods including card payments, bank payments, and digital wallet payments. Each of the following files handles unique client logic for a specific payment method to generate a payment token:

  - `sq-card-pay.js`
  - `sq-ach.js`
  - `sq-google-pay.js`
  - `sq-apple-pay.js`

- Provides the global method that fires a `fetch` request to the server after receiving the payment token.
  ```javascript
  window.createPayment = async function(token) {
    const dataJsonString = JSON.stringify({
      token
    });

    try {
      const response = await fetch('process-payment', {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
        },
        body: dataJsonString
      });

      const data = await response.json();

      if (data.errors && data.errors.length > 0) {
        if (data.errors[0].detail) {
          window.showError(data.errors[0].detail);
        } else {
          window.showError('Payment Failed.');
        }
      } else {
        window.showSuccess('Payment Successful!');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  }
  ```

### Step 2: Charge the Payment Source Using the Token

All the remaining actions take place in the **src/main/java/com/squareup/connectexamples/ecommerce/Main.java** file. 
This server-side component uses the [Square Java SDK](https://developer.squareup.com/docs/sdks/java) 
to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

```java
@PostMapping("/process-payment")
@ResponseBody PaymentResult processPayment(@RequestBody TokenWrapper tokenObject)
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
        UUID.randomUUID().toString(),
        bodyAmountMoney)
        .build();

  PaymentsApi paymentsApi = squareClient.getPaymentsApi();
    return paymentsApi.createPaymentAsync(createPaymentRequest).thenApply(result -> {
      return new PaymentResult("SUCCESS", null);
    }).exceptionally(exception -> {
      System.out.println("Failed to make the request");
      System.out.printf("Exception: %s%n", exception.getMessage());
      // NOTE: Can pass in error messages to be presented in the front end. For simplicity,
      // we just hardcoded a simple string for now.
      return new PaymentResult("FAILURE", Collections.singletonList("errorMessage"));
    }).join();
}
```
  

# License

Copyright 2021 Square, Inc.
​
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
​
   http://www.apache.org/licenses/LICENSE-2.0
​
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Feedback
Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!