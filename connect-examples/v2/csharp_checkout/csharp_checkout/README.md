# Website Payment Processing Using the Square Checkout API: Csharp

This is a simple example application that utilizes Square's Checkout API 
using .NET Razor. This examples assumes you are familiar with C# development. 

For more information about Checkout please visit:
* https://docs.connect.squareup.com/payments/checkout/overview
* https://docs.connect.squareup.com/api/connect/v2#navsection-checkout
* https://github.com/square/connect-php-sdk/blob/master/docs/Api/CheckoutApi.md

There are two sections in this ReadMe.
* [Setup](#setup) - Provides instructions for you to download and run the app.
* [Application Flow](#application-flow) - Provides an overview of how the Square Payment form integrates in the ASP.NET app.

## Setup
To get it running:

* Clone/download to your local computer.
* Place your production credentials in `appsettings.Production.json`
  * <b>WARNING</b>: never upload .env with your credential/access_token
* Place your sandbox credentials in `appsettings.Sandbox.json`
  * <b>WARNING</b>: never upload .env with your credential/access_token
* Run the following command in your terminal, while inside the "csharp_checkout" (this) directory to start your server:
```
dotnet run --launch-profile Sandbox
```
  * Note: replace "Sandbox" with "Production" to use your production credentials.

This will start the server on `localhost:5000`, which you can navigate to in your favorite browser.



## Application flow

This is an ASP.NET Core Razor application (???). The web application implements the 
Checkout API to charge a payment source (a credit card). The Checkout API is one 
of the Square's solution for taking online payments,  

The application works as follows:

1. Application frontend takes an order from the buyer. 

   In this application, we hardcode an order with couple of line items and show a **Pay now!** 
   button. See index.cshtml. 

2. After the buyer is ready to checkout, they click **Pay now!** causing the  **Checkout** 
event handler to execute the **CheckoutMode.OnPost** in Checkout.cshtml.cs. 
The `OnPost` method does the following:

   a. Send a `CreateCheckout` request to Square as follows:
      * Create a `CheckoutApi` client.
      * Create an instance of `CreateOrderRequest` and packge order information to send to Square.
      * Send a `CreateCheckout` request to Square. In the request, include the order object in 
      body.
      
      ```
              CreateCheckoutResponse response = checkoutApi.CreateCheckout(locationId, body);
      ```

   b. Square does necessary orchestration to provide a prebuilt payment form and returns 
   a `Checkout` object that includes the `Order` to be checked out and a `CheckoutPageUrl`.

   c. The application code redirects the buyer's browser to the `CheckoutPageUrl`. 

      ```
      return Redirect(response.Checkout.CheckoutPageUrl);

      ```
   
      The buyer see  following page hosted on Square.

      <img src="./checkout.png" width="300"/>

      This page hosted on Square shows all the order information  and also a
       **Payment Information** section.

After the buyer provides card  information and clicks **Place Order**, Square processes the 
payment and returns the buyer to a confirmation  page. In your `CreateCheckout` request, if you 
included a **redirect_url** parameter, Squre  returns the buyer to that page. Otherwise, 
Square Checkout displays an order confirmation page to the buyer on your behalf.  An example 
screenshot is shown:

<img src="./confirmation.png" width="300"/>


**************************IGNORE THE FOLLOWING - should be removed ***********************

1. Generate a nonce -  Using a Square Payment Form (a client-side JavaScript library 
called the **SqPaymentForm**) you accept payment source information and generate a secure payment token (nonce).

    NOTE: The SqPaymentForm library renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token (nonce). For more information, see https://docs.connect.squareup.com/payments/sqpaymentform/what-it-does.

    After embeded the Square Payment form in your web application, it will look similar to the following screenshot:

    <img src="./PaymentFormExample.png" width="300"/>

2. Charge the payment source using the nonce - Using a server-side component, that uses the Connect V2 Payments API, you charge the payment source using the nonce.

### Step 1: Generate a Nonce

When the webpage loads it renders the Square Payment form defined in the **index.cshtml**  file. The page also downloads and executes the following scripts defined in the view file:

The **Square Payment Form Javascript library** (https://js.squareup.com/v2/paymentform)  is a library that provides the SqPaymentForm object you use in the next script. For more information about the library, see [SqPaymentForm data model](https://docs.connect.squareup.com/api/paymentform#navsection-paymentform). 

**sq-payment-form.js** - This code provides two things:

* Initializes the **SqPaymentForm** object by initializing various 
[configuration fields](https://docs.connect.squareup.com/api/paymentform#paymentform-configurationfields) and providing implementation for [callback functions](https://docs.connect.squareup.com/api/paymentform#_callbackfunctions_detail). For example,

    * Maps the **SqPaymentForm.cardNumber** configuration field to corresponding form field:  

        ```javascript
        cardNumber: {
            elementId: 'sq-card-number',               
            placeholder: '•••• •••• •••• ••••'
        }
        ```
    * **SqPaymentForm.cardNonceResponseReceived** is one of the callbacks the code provides implementation for. 

* Provides the **onGetCardNonce** event handler code that executes after you choose click **Pay $1.00 Now**.

After the buyer enters their information in the form and clicks **Pay $1 Now**, the application does the following: 

* The **onGetCardNonce** event handler executes. It first generates a nonce by calling the **SqPaymentForm.requestCardNonce** function.
* **SqPaymentForm.requestCardNonce** invokes **SqPaymentForm.cardNonceResponseReceived** callback. This callback  assigns the nonce to a form field and posts the form to the payment processing page:

    ```javascript
    document.getElementById('card-nonce').value = nonce;
    document.getElementById('nonce-form').submit();  
    ```

    This invokes the form action **ProcessPayment**, described in next step.

### Step 2: Charge the Payment Source Using the Nonce 
All the remaining actions take place in the **ProcessPayment.cshtml.cs**.  This server-side component uses the Square .NET SDK C# wrapper library to call the Connect V2 **Payments** API to charge the payment source using the nonce.
```csharp
public void OnPost()
{
    string nonce = Request.Form["nonce"];
    IPaymentsApi paymentsApi = new PaymentsApi(this.BasePath);
    paymentsApi.Configuration.AccessToken = this.AccessToken;

    string uuid = NewIdempotencyKey();
    Money amount = new Money(100, "USD");

    CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(AmountMoney: amount, IdempotencyKey: uuid, SourceId: nonce);
    try
    {
        var response = paymentsApi.CreatePayment(createPaymentRequest);
        this.ResultMessage = "Payment complete! " + response.ToJson();
    }
    catch (ApiException e)
    {
        this.ResultMessage = e.Message;
    }
}
```