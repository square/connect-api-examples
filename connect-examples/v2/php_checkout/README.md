Square Checkout Demo
=========================

This is a simple example application that utilizes Square's Checkout API. This examples does assume you are familiar with PHP development.

It takes a single payment, declared by the user, and creates an order to use in the Checkout API.

To get it running:

* Clone/download to your local computer.
* Place your credentials in `.env`
  * <b>WARNING</b>: never upload .env with your credential/access_token
* In `.env` there's a variable, `USE_PROD` to switch between production and sandbox; change it based on your needs.
* Downloaded composer (https://getcomposer.org/download/)
* Run the following command in your terminal to download the required packages (in the directory you copied the project to):
```
php composer.phar install
```
* Run the following command in your terminal to start your server:
```
php -S localhost:8888
```

This will start the server on `localhost:8888`, which you can navigate to in your favorite browser.

For more information about Checkout please visit:
* https://docs.connect.squareup.com/payments/checkout/overview
* https://docs.connect.squareup.com/api/connect/v2#navsection-checkout
* https://github.com/square/connect-php-sdk/blob/master/docs/Api/CheckoutApi.md

## Application flow

This is a PHP application. The web application implements the 
Checkout API to take payments.   

The application works as follows:

1. Buyer places an order using the application frontend. 

   In this application we hardcode an order with couple of line items 
   and show a **Pay now!** button. See checkout.php. 

2. Buyer clicks **Pay now!** when ready to checkout. This submits
the  **Checkout** event to the handler ( **checkout.php**). The event handler does the following:

    1. Send a `CreateCheckout` request to Square as follows:
        * Create an instance of `Api_Client` (`api_client`).
        * Create an instance of `CreateCheckoutRequest` with the order line items.
        * Call the `CreateCheckout` method with`CreateCheckoutRequest` object and the  `locationId` parameters.
          ```php
          $response = $checkout_api->createCheckout($location_id, $request_body);
          ```

    1. Square does necessary configuration to create a prebuilt checkout page and returns response that includes a URL to the checkout page.

    1. The application responds by redirecting the buyer's browser to the `CheckoutPageUrl`. 
        ```php
        header("Location: ".$response->getCheckout()->getCheckoutPageUrl());
        ```
      
        The buyer sees following page hosted on Square.

        <img src="./checkout.png" width="650"/>

        This page hosted on Square shows all the order information  and also the 
        **Payment Information** section.
       
    1. Buyer provides card information and clicks **Place Order**. 

    1. Square processes the payment and redirects the buyer to a confirmation  page. If you provided **redirect_url** in your initial `CreateCheckoutRequest`, Square returns the buyer to that page. Otherwise, Square redirects the buyer to Square provided confirmation page. An example screenshot is shown:

        <img src="./confirmation.png" width="650"/>



