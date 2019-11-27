# Payment processing example: Csharp

This sample demonstrates processing card payments with Square Connect API, using the
Square Connect C# client library and dotnet core. There are two sections in this ReadMe.
* [Setup](#setup) - Provides instructions for you to download and run the app.
* [Application Flow](#application-flow) - Provides an overview of how the Square Payment form integrates in the ASP.NET app.

## Setup

### Requirements

* Download and install [.net core 2.0](https://www.microsoft.com/net/download/macos)
* Signup a Square account from [Square website](https://squareup.com/signup)
* You have learned the basics from [Square Developer Docs](https://docs.connect.squareup.com/)

### Setup your square account

1. Login to [Square Dashboard](https://squareup.com/dashboard/)
2. Create some items from [Items Tab](https://squareup.com/dashboard/items/library)
3. Go to [Square Developer Portal](https://connect.squareup.com/apps) and create a new application.

### Build the project

After cloning this sample project to local, open command line tool, and from the project root directory run:

    dotnet build

### Provide required credentials

Open `./appsettings.json`, specify "Environment" and replace "AccessToken", "LocationId" and "ApplicationId" with the ids you get from your square application created in [Square Developer Portal](https://connect.squareup.com/apps).
<b>WARNING</b>: never upload `appsettings.json` with your credentials/access_token.

If you're just testing things out, it's recommended that you use your _sandbox_
credentials for now. See
[Test Square APIs With Sandbox](https://developer.squareup.com/docs/testing/sandbox/)
for more information on the API sandbox.

## Running the sample

Run the command from the project root directory:

    dotnet run

Then you can visit:

    http://localhost:5000

* You'll see a simple payment form that will charge $1.00.
* You can test a valid credit card transaction by providing the following card information in the form:

    * Card Number 4111 1111 1111 1111
    * Card CVV 111
    * Card Expiration (Any time in the future)
    * Card Postal Code (Any valid US postal code)

You can find more testing values in [Sandbox Test Values](https://developer.squareup.com/docs/testing/test-values)

**Note that if you are _not_ using your sandbox credentials and you enter _real_
credit card information, YOU WILL CHARGE THE CARD.**

## Application Flow

This is an ASP.NET Core Razor application. The web application implements the Square Online payment solution to charge a payment source (debit, credit, or digital wallet payment cards).

Square Online payment solution is a 2-step process:

1. Generate a nonce -  Using a Square Payment Form (a client-side JavaScript library
called the **SqPaymentForm**) you accept payment source information and generate a secure payment token (nonce).

    NOTE: The SqPaymentForm library renders the card inputs and digital wallet buttons that make up the payment form and returns a secure payment token (nonce). For more information, see [Square Payment Form - What It Does](https://developer.squareup.com/docs/payment-form/what-it-does).

    After embeded the Square Payment form in your web application, it will look similar to the following screenshot:

    <img src="./PaymentFormExample.png" width="300"/>

2. Charge the payment source using the nonce - Using a server-side component, that uses the Connect V2 Payments API, you charge the payment source using the nonce.

### Step 1: Generate a Nonce

When the webpage loads it renders the Square Payment form defined in the **index.cshtml**  file. The page also downloads and executes the following scripts defined in the view file:

The **Square Payment Form Javascript library** (https://js.squareup.com/v2/paymentform)  is a library that provides the SqPaymentForm object you use in the next script. For more information about the library, see [SqPaymentForm data model](https://docs.connect.squareup.com/api/paymentform#navsection-paymentform).

**sq-payment-form.js** - This code provides provides the following:

* Initializes a **SqPaymentForm** object (`paymentFormWallets`) for the digital wallets by initializing various
[configuration fields](https://docs.connect.squareup.com/api/paymentform#paymentform-configurationfields) and providing implementation for [callback functions](https://docs.connect.squareup.com/api/paymentform#_callbackfunctions_detail).
* Initializes a **SqPaymentForm**  (beta) object (`paymentForm`) for the single-element payment form by initializing various
[configuration fields](https://docs.connect.squareup.com/api/paymentform#paymentform-configurationfields) and providing implementation for [callback functions](https://docs.connect.squareup.com/api/paymentform#_callbackfunctions_detail). For example,


    * Maps the **SqPaymentForm.card** configuration field to corresponding form field:

        ```javascript
            card: {
                elementId: 'sq-card',
                inputStyle: {
                    fontSize: '14px',
                    fontWeight: 500,
                    fontFamily: 'tahoma',
                    placeholderFontWeight: 300,
                    borderRadius: '10px',
                    autoFillColor: '#FFFFFF',     //Card number & exp. date strings
                    color: '#FFFFFF',             //CVV & Zip
                    ...
                }
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



