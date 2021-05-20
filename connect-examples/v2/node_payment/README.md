# Useful Links

- [Square Node.js SDK](https://developer.squareup.com/docs/sdks/nodejs)
- [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
- [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
- [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: Node.js

There are two sections in this README.

- [Setup](#setup) - Provides instructions for you to download and run the app.
- [Application Flow](#application-flow) - Provides an overview of how the Square Web Payments SDK integrates in the Node.js example.

## Setup

1. Create a file named `.env` in the root directory of this example. `.env.example` is an example of what the `.env` file should look like. Fill in values for `SQUARE_APPLICATION_ID`, `SQUARE_ACCESS_TOKEN`, and `SQUARE_LOCATION_ID` with your sandbox or production credentials.
   <b>WARNING</b>: never save your credentials in your code.

1. Ensure you have npm installed (`npm -v` in your terminal). If not please follow the instructions for your OS: https://www.npmjs.com/get-npm

1. Open your terminal and type the following to install the packages:

   ```
   npm install
   ```

1. Then to run the server in _production_ mode:

   ```
   npm start
   ```

   Or to run in _sandbox_ mode:

   ```
   npm test
   ```

1. Open a browser and navigate to [localhost:3000](localhost:3000)

1. Test with different payment options. For more information on testing in sandbox mode, follow the guide: [Testing using the API sandbox](https://developer.squareup.com/docs/testing/sandbox). You can find more testing values in this [article](https://developer.squareup.com/docs/testing/test-values).

    **Note that if you are _not_ using your sandbox credentials and you enter _real_
    credit card information, YOU WILL CHARGE THE CARD.**

## Application Flow

This Node.js web application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.

   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).

2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2
   **Payments** API, you charge the payment source using the secure payment token.

The following sections describe how the Node JS sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **views/index.pug** file. The page also downloads and executes the following scripts:

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

All the remaining actions take place in the **routes/index.js** file. This server-side component uses the [Square Node.js SDK](https://developer.squareup.com/docs/sdks/nodejs) library to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

```javascript
...
router.post('/process-payment', async (req, res) => {
  const token = req.body.token;

  // length of idempotency_key should be less than 45
  const idempotencyKey = uuidv4();

  // get the currency for the location
  const locationResponse = await locationsApi.retrieveLocation(process.env.SQUARE_LOCATION_ID);
  const currency = locationResponse.result.location.currency;

  // Charge the customer's card
  const requestBody = {
    idempotencyKey,
    sourceId: token,
    amountMoney: {
      amount: 100, // $1.00 charge
      currency
    }
  };

  try {
    const { result: { payment } } = await paymentsApi.createPayment(requestBody);

    const result = JSON.stringify(payment, (key, value) => {
      return typeof value === "bigint" ? parseInt(value) : value;
    }, 4);

    res.json({
      result
    });
  } catch (error) {
    res.json(error.result);
  }
});
...
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
