# Routes Directory

Routes contains the [ExpressJs routes](https://expressjs.com/en/guide/routing.html) used to match the the requests the server receives.

## index.js

The entry point that connects to all the other routes, it matches to the base of the url request.
This is also the file that serves up the home page by rendering index.pug and take `create-order` request
to kick off a checkout workflow.

## checkout.js

This file matches any requests with `./checkout/*` in the base of the relative path of the url.
It serves up all the checkout workflow by taking POST data from client and rendering the pages for
each checkout steps.

## order-confirmation.js

This file matches any requests with `./order-confirmation/` in the base of the relative path of the url.
It serves up the confirmation page from a successful paid order and display the order's latest status.
