# Useful Links

* [Square .NET SDK](https://developer.squareup.com/docs/sdks/dotnet)
- [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
- [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
- [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: .NET

There are two sections in this ReadMe.
* [Setup](#setup) - Provides instructions for you to download and run the app.
* [Application Flow](#application-flow) - Provides an overview of how the Square Payment form integrates in the ASP.NET app.

## Setup

1. create a file `./appsettings.json` by copying `./appsettings.json.example`. Specify `Environment`, and replace `AccessToken`, `LocationId`, and `ApplicationId` with your sandbox or production credentials. You can get your credentials from your Square application created at [Square Developer Portal](https://developer.squareup.com/apps).
<b>WARNING</b>: never upload `appsettings.json` with your credentials or access token.

1. Esure you have `dotnet` instealled (`dotnet --version` in your terminal). If not, please download [.net core 2.0+](https://www.microsoft.com/net/download/)

1. Open your terminal, and run the following to build the app:

    ```
    dotnet build
    ```

1. Then, to run the server:

    ```
    dotnet run
    ```

1. Open a browser and navigate to [localhost:5000](http://localhost:5000)

1. Test with different payment options. For more information on testing in sandbox mode, follow the guide: [Testing using the API sandbox](https://developer.squareup.com/docs/testing/sandbox)


## Application Flow

This ASP.NET Core Razor application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.


   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).


2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2
   **Payments** API, you charge the payment source using the secure payment token.

The following sections describe how the C# sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **Pages/index.cshtml** file. The page also downloads and executes the following scripts:

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

All the remaining actions take place in the **Pages/ProcessPayment.cshtml.cs** file. This server-side component uses the Square .NET SDK library to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

### Step 2: Charge the Payment Source Using the Nonce
All the remaining actions take place in the **ProcessPayment.cshtml.cs**.  This server-side component uses the Square .NET SDK C# wrapper library to call the Connect V2 **Payments** API to charge the payment source using the nonce.
```csharp
public async Task<IActionResult> OnPostAsync()
{
    var request = JObject.Parse(await new StreamReader(Request.Body).ReadToEndAsync());
    var token = (String)request["token"];
    var PaymentsApi = client.PaymentsApi;
    // Every payment you process with the SDK must have a unique idempotency key.
    // If you're unsure whether a particular payment succeeded, you can reattempt
    // it with the same idempotency key without worrying about double charging
    // the buyer.
    string uuid = NewIdempotencyKey();

    // Get the currency for the location
    var retrieveLocationResponse = await client.LocationsApi.RetrieveLocationAsync(locationId: locationId);
    var currency = retrieveLocationResponse.Location.Currency;

    // Monetary amounts are specified in the smallest unit of the applicable currency.
    // This amount is in cents. It's also hard-coded for $1.00,
    // which isn't very useful.
    var amount = new Money.Builder()
        .Amount(100L)
        .Currency(currency)
        .Build();

    // To learn more about splitting payments with additional recipients,
    // see the Payments API documentation on our [developer site]
    // (https://developer.squareup.com/docs/payments-api/overview).
    var createPaymentRequest = new CreatePaymentRequest.Builder(
        sourceId: token,
        idempotencyKey: uuid,
        amountMoney: amount)
        .Build();

    try
    {
        var response = await PaymentsApi.CreatePaymentAsync(createPaymentRequest);
        return new JsonResult(new { payment = response.Payment });
    }
    catch (ApiException e)
    {
        return new JsonResult(new { errors = e.Errors });
    }
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