# Useful Links

- [Square PHP SDK](https://developer.squareup.com/docs/sdks/php)

* [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
* [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
* [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: PHP

There are two sections in this README.

- [Setup](#setup) - Provides instructions for you to download and run the app.
- [Application Flow](#application-flow) - Provides an overview of how the Square Web Payments SDK integrates in the PHP example.

## Setup

### Requirements

- 8.0 <= PHP < 8.2

### Install the PHP client library

This sample already includes the `square/square` dependency in its `composer.json`
file. To install the client library:

1. Make sure you've downloaded Composer, following the instructions
   [here](https://getcomposer.org/download/).

2. Run the following command from the directory containing `composer.json`:

   ```
   php composer.phar install
   ```

### Specify your application credentials

In order for the example to work, you must create a new file `.env` by copying the contents of the `.env.example` file. Edit this file with your application credentials and environment configuration.
<b>WARNING</b>: never save your credentials in your code.

Open your [developer dashboard](https://developer.squareup.com/apps). Now supply either production, sandbox, or both credentials. Open this file and update the following variables:

- WARNING: never upload .env with your credential/access_token

| Variable              | Type     | Description                                                                                                                                                       |
| --------------------- | :------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| ENVIRONMENT           | `string` | `production` or `sandbox` depending on what type of endpoint you want to hit. For testing purposes please use the sandbox mode (already configured in the `.env`) |
| SQUARE_APPLICATION_ID | `string` | Application ID found on your Developer App Dashboard, Credentials tab. Must match the corresponding `ENVIRONMENT`.                                                |
| SQUARE_ACCESS_TOKEN   | `string` | Access Token found at the Developer App Dashboard, Credentials tab. Must match the corresponding `ENVIRONMENT`.                                                   |
| SQUARE_LOCATION_ID    | `string` | Location found at the Developer App Dashboard, Location tab. Must match the corresponding `ENVIRONMENT`.                                                          |

## Running the sample

From the sample's root directory, run:

    php -S localhost:8888

You can then visit [`localhost:8888`](http://localhost:8888) in your browser to see the payment form.

If you're using your sandbox credentials, you can test a valid credit card
payment by providing the following card information in the form:

- Card Number `4111 1111 1111 1111`
- Card CVV `111`
- Card Expiration (Any time in the future)
- Card Postal Code (Any valid US postal code)

You can find more testing values in this [article](https://developer.squareup.com/docs/testing/test-values).

**Note that if you are _not_ using your sandbox credentials and you enter _real_
credit card information, YOU WILL CHARGE THE CARD.**

## Application Flow

This PHP web application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.

   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).

2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2
   **Payments** API, you charge the payment source using the secure payment token.

The following sections describe how the PHP sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **index.php** file. The page also downloads and executes the following scripts:

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

All the remaining actions take place in the **process-payment.php** file. This server-side component uses the [Square PHP SDK](https://developer.squareup.com/docs/sdks/php) library to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

```php
...
$square_client = new SquareClient([
  'accessToken' => $access_token,
  'environment' => getenv('ENVIRONMENT')
]);

$payments_api = $square_client->getPaymentsApi();

$money = new Money();
$money->setAmount(100);
// Set currency to the currency for the location
$money->setCurrency($location_info->getCurrency());

// Every payment you process with the SDK must have a unique idempotency key.
// If you're unsure whether a particular payment succeeded, you can reattempt
// it with the same idempotency key without worrying about double charging
// the buyer.
$create_payment_request = new CreatePaymentRequest($token, Uuid::uuid4(), $money);

$response = $payments_api->createPayment($create_payment_request);

if ($response->isSuccess()) {
  echo json_encode($response->getResult());
} else {
  echo json_encode($response->getErrors());
}
...
```

## Feedback

Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!
