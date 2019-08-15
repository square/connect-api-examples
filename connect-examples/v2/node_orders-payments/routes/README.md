# Routes Directory

Routes contains the [ExpressJs routes](https://expressjs.com/en/guide/routing.html) used to match the the requests the server receives.

## Index.js

The entry point that connects to all the other routes, it matches to the base of the url request.
This is also the file that serves up the home page by rendering index.pug.

## Checkout.js

This file matches any requests with `./checkout/` in the base of the relative path of the url.
It serves up the checkout page from the checkout.pug, upon receiving a POST request.

## Process-payment.js

This file matches any requests with `./process-payment/` in the base of the relative path of the url.
It serves up the confirmation page from the process-payment.pug template, upon receiving a POST request.
