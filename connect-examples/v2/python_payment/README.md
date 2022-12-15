# Useful Links

- [Square Python SDK](https://developer.squareup.com/docs/sdks/python)
- [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview)
- [Web Payments SDK Reference](https://developer.squareup.com/reference/sdks/web/payments)
- [Payments API Overview](https://developer.squareup.com/docs/payments)
- [Payments API Reference](https://developer.squareup.com/reference/square/payments-api)

# Payment processing example: Python

This sample demonstrates processing card payments with Square Connect API, using the Square Connect Python client library.

## Requirements

Make sure you have Python >= 3.4

## Setup

### Install the Python client library

1. Make sure you have Python >= 3.4 installed from [python.org](https://www.python.org/).

2. Run the following command to install `squareup` package and other dependencies:

   `pip install -r requirements.txt` or `pip3 install -r requirements.txt`

### Provide required credentials

Create a `config.ini` file at the root directory by copying the contents of the `config.ini.example` file and populate it. Note that there's sandbox and production credentials. Use `is_prod` (true/false) to choose between them.
Do not use quotes around the strings in the `config.ini` file.
(**WARNING**: never upload `config.ini` with your credentials/access_token.)

If you're just testing things out, it's recommended that you use your _sandbox_ credentials for now. See
[this article](https://developer.squareup.com/docs/testing/sandbox)
for more information on the API sandbox.

## Running the sample

From the sample's root directory, run:

    uvicorn main:app --reload

You can then visit `localhost:8000` in your browser to see the card form.

If you're using your sandbox credentials, you can test a valid credit card
transaction by providing the following card information in the form:

- Card Number: `4111 1111 1111 1111`
- Card CVV: `111`
- Card Expiration: `12/24` (Any time in the future)
- Card Postal Code: `12345` (Any valid US postal code)

You can find more testing values in this [article](https://developer.squareup.com/docs/testing/test-values)

**Note that if you are _not_ using your sandbox credentials and you enter _real_ credit card information, YOU WILL CHARGE THE CARD.**

## Application Flow

This Python application implements the Square Online payment solution to charge a payment source (debit card, credit card, ACH transfers, and digital wallet payment methods).

Square Online payment solution is a 2-step process:

1. Generate a token - Use the [Square Web Payments SDK](https://developer.squareup.com/reference/sdks/web/payments) to accept payment source information and generate a secure payment token.

   NOTE: The Web Payments SDK renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token. For more information, see the [Web Payments SDK Overview](https://developer.squareup.com/docs/web-payments/overview).

2. Charge the payment source using the token - Using a server-side component, that uses the Connect V2 **Payments** API, you charge the payment source using the secure payment token.

The following sections describe how the Python sample implements these steps.

### Step 1: Generate a Token

When the page loads it renders the form defined in the **main.py** file:

```python
@app.get("/", response_class=HTMLResponse)
def read_root():
    return generate_index_html()
```

which downloads and executes the following scripts:

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

All the remaining actions take place in the **main.py** file. This server-side component uses the [Square Python SDK](https://developer.squareup.com/docs/sdks/python) to call the Connect V2 **Payments** API to charge the payment source using the token as shown in the following code fragment.

```python
...
@app.post("/process-payment")
def create_payment(payment: Payment):
    logging.info("Creating payment")
    # Charge the customer's card
    create_payment_response = client.payments.create_payment(
        body={
            "source_id": payment.token,
            "idempotency_key": str(uuid.uuid4()),
            "amount_money": {
                "amount": 100,  # $1.00 charge
                "currency": ACCOUNT_CURRENCY,
            },
        }
    )

    logging.info("Payment created")
    if create_payment_response.is_success():
        return create_payment_response.body
    elif create_payment_response.is_error():
        return create_payment_response
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

## Feedback

Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!
