# Useful Links

- [Square Ruby SDK](https://developer.squareup.com/docs/sdks/ruby)
- [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
- [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
- [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: Ruby on Rails

There are two sections in this ReadMe.

- [Setup](#setup) - Provides instructions for you to download and run the app.
- [Application Flow](#application-flow) - Provides an overview of how the Web Payments SDK integrates in the Ruby on Rails example.

## Setup

1. Create a file named `.env` in the root directory of this example. `.env.example` is an example of what the `.env` file should look like. Fill in values for `SQUARE_APPLICATION_ID`, `SQUARE_APPLICATION_ID`, `SQUARE_ACCESS_TOKEN`, and `SQUARE_LOCATION_ID` with your sandbox or production credentials.
   <b>WARNING</b>: never save your credentials in your code.

1. Ensure you have Ruby `2.7.2` installed (`ruby -v` in your terminal). If not please follow the instructions for your OS: https://www.ruby-lang.org/en/downloads/

1. Ensure you have Bundler installed (`bundle -v`) in your terminal). If not, install it: `gem install bundler`.

1. Open your terminal and type the following to install the packages:

   ```
   bundle install
   ```

1. Then to run the server:

   ```
   bin/rails server
   ```

1. Open a browser and navigate to [localhost:3000](http://localhost:3000)

1. Test with different payment options. For more information on testing in sandbox mode, follow the guide: [Testing using the API sandbox](https://developer.squareup.com/docs/testing/sandbox)

## Application Flow

This Ruby on Rails web application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.

   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).

2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2
   **Payments** API, you charge the payment source using the sure payment token.

The following sections describe how the Ruby on Rails sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **app/views/welcome/index.html.erb** file. The page also downloads and executes the following scripts:

**Square Web Payments SDK** - It is a library that provides the Payment objects you use in sq-payment-flow.js. For more information about the library, see [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments).

**sq-payment-flow.js** - This code provides two things:

- Initializes objects for various supported payment methods including card payments, bank payments, and digital wallet payments. Each of the following files handles unique client logic for a specific payment method to generate a payment token:

  - `sq-card-pay.js`
  - `sq-ach.js`
  - `sq-google-pay.js`
  - `sq-apple-pay.js`

- Provides the global method that fires a `fetch` request to the server after receiving the payment token.

  ```javascript
  window.createPayment = async function (token) {
    const dataJsonString = JSON.stringify({
      token,
    });

    try {
      const response = await fetch("process-payment", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: dataJsonString,
      });

      const data = await response.json();

      if (data.errors && data.errors.length > 0) {
        if (data.errors[0].detail) {
          window.showError(data.errors[0].detail);
        } else {
          window.showError("Payment Failed.");
        }
      } else {
        window.showSuccess("Payment Successful!");
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };
  ```

### Step 2: Charge the Payment Source Using the Token

All the remaining actions take place in the **app/controllers/payments_controller.rb** file. This server-side component uses the [Square Ruby SDK](https://developer.squareup.com/docs/sdks/ruby) to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

```ruby
def process_payment
  # To learn more about splitting payments with additional recipients,
  # see the Payments API documentation on our [developer site]
  # (https://developer.squareup.com/docs/payments-api/overview).
  # Charge 1 dollar (100 cent)
  currency = square_api_client
    .locations
    .retrieve_location(location_id: ENV['SQUARE_LOCATION_ID'])
    .data
    .location[:currency]

  request_body = {
    :source_id => params[:token],
    :amount_money => {
      :amount => 100,
      :currency => currency
    },
    :idempotency_key => SecureRandom.uuid
  }

  resp = square_api_client.payments.create_payment(body: request_body)
  render json: resp.data.payment
end
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