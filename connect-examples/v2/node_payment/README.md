# Payment processing example: Node JS

* Open `config.json` and fill in values for squareApplicationId & squareAccessToken
with both your sandbox and production credentials.
<b>WARNING</b>: never upload `config.json` with your credentials/access_token.

* Ensure you have npm installed (`npm -v` in your terminal). If not please follow the instructions for your OS: https://www.npmjs.com/get-npm

* Open your terminal and type the following to install the packages:
```
npm install
```

* Then to run the server in production mode:
```
npm start
```
Or to run in sandbox mode:
```
npm test
```

* Open a browser and navigate to [localhost:3000](localhost:3000)

* [Testing using the API sandbox](https://developer.squareup.com/docs/testing/sandbox)

## Application Flow

The Node JS web application implements the Square Online payment solution to charge a payment source (debit, credit, or digital wallet payment cards).

Square Online payment solution is a 2-step process: 

1. Generate a nonce -  Using a Square Payment Form (a client-side JavaScript library 
called the **SqPaymentForm**) you accept payment source information and generate a secure payment token (nonce).

    NOTE: The SqPaymentForm library renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token (nonce). For more information, see https://docs.connect.squareup.com/payments/sqpaymentform/what-it-does.

    After embedding the Square Payment form in your web application, it will look similar to the following screenshot:

    <img src="./PaymentFormExampleNode.png" width="300"/> 

2. Charge the payment source using the nonce - Using a server-side component, that uses the Connect V2 
**Transaction** API, you charge the payment source using the nonce.

The following sections describe how the Node JS sample implements these steps.

### Step 1: Generate a Nonce

When the page loads it renders the form defined in the **views/index.pug** file. The page also downloads and executes the following scripts defined in the file:

 **Square Payment Form Javascript library** (https://js.squareup.com/v2/paymentform) It is a library that provides the SqPaymentForm object you use in the next script. For more information about the library, see [SqPaymentForm data model](https://developer.squareup.com/docs/api/paymentform#navsection-paymentform). 

**sq-payment-form.js** - This code provides two things:

* Initializes the **SqPaymentForm** object by initializing various 
[configuration fields](https://developer.squareup.com/docs/api/paymentform#paymentform-configurationfields) and providing implementation for [callback functions](https://developer.squareup.com/docs/api/paymentform#_callbackfunctions_detail). For example,

    * Maps the **SqPaymentForm.cardNumber** configuration field to corresponding form field:  

        ```javascript
        cardNumber: {
            elementId: 'sq-card-number',
            placeholder: '•••• •••• •••• ••••'
        }
        ```
    * **SqPaymentForm.cardNonceResponseReceived** is one of the callbacks the code provides implementation for. 

* Provides the **onGetCardNonce** event handler code that executes after you click **Pay $1.00 Now**.

After the buyer enters their information in the form and clicks **Pay $1.00 Now**, the application does the following: 

* The **onGetCardNonce** event handler executes. It first generates a nonce by calling the **SqPaymentForm.requestCardNonce** function.
* **SqPaymentForm.requestCardNonce** invokes **SqPaymentForm.cardNonceResponseReceived** callback. This callback  assigns the nonce to a form field and posts the form to the payment processing page:

    ```javascript
    document.getElementById('card-nonce').value = nonce;
    document.getElementById('nonce-form').submit();  
    ```

    This invokes the form action **process-payment**, described in next step.

### Step 2: Charge the Payment Source Using the Nonce 
All the remaining actions take place in the **routes/index.js**.  This server-side component uses the Square Node JS SDK library to call the Connect V2 **Payments** API to charge the payment source using the nonce as shown in the following code fragment. 
```javascript
...
router.post('/process-payment', async (req, res) => {
  const request_params = req.body;

  // length of idempotency_key should be less than 46
  const idempotency_key = crypto.randomBytes(23).toString('hex');

  // Charge the customer's card
  const payments_api = new squareConnect.PaymentsApi();
  const request_body = {
    source_id: request_params.nonce,
    amount_money: {
      amount: 100, // $1.00 charge
      currency: 'USD'
    },
    idempotency_key: idempotency_key
  };

  try {
    const respone = await payments_api.createPayment(request_body);
    const json = JSON.stringify(respone);
    res.render('process-payment', {
      'title': 'Payment Successful',
      'result': json
    });
  } catch (error) {
    res.render('process-payment', {
      'title': 'Payment Failure',
      'result': error.response.text
    });
  }
});
...
```	