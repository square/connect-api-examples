# Routes Directory

Routes contains the [ExpressJs routes](https://expressjs.com/en/guide/routing.html) used to match the the requests the server receives.

## index.js

The entry point that connects to all the other routes, it matches to the base of the url request.
This is also the file that serves up the home page by rendering index.pug and display a list of customers.

## management.js

This file matches any requests with `/management/*` in the base of the relative path of the url.
It serves up the invoice management page for a selected customer by providing interface to create invoic and
display a list of invoices.

## invoice.js

This file matches any requests with `/invoice/*` in the base of the relative path of the url.
It serves up all the invoice workflow by taking POST data from client and renders the page for
an invoice.
