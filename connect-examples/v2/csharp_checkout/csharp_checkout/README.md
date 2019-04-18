Square Checkout Demo
=========================

This is a simple example application that utilizes Square's Checkout API using .NET Razor. This examples does assume you are familiar with C# development.

It takes a single payment, declared by the user, and creates an order to use in the Checkout API.

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

For more information about Checkout please visit:
* https://docs.connect.squareup.com/payments/checkout/overview
* https://docs.connect.squareup.com/api/connect/v2#navsection-checkout
* https://github.com/square/connect-php-sdk/blob/master/docs/Api/CheckoutApi.md