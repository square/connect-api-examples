# Routes Directory

Routes contains the [ExpressJs routes](https://expressjs.com/en/guide/routing.html) used to match the the requests the server receives.

## index.js

The entry point that connects to all the other routes, it matches to the base of the url request.
This is also the file that serves up the home page by rendering index.pug and display a list of customers.

## management.js

This file matches any requests with `/management/*` in the base of the relative path of the url.
It serves up the subscription management page for a selected customer by providing interface to display a list of subscription plans with subscription status.

## subscription.js

This file matches any requests with `/subscription/*` in the base of the relative path of the url.
It serves up all the subscription workflow in this example, such as subscribe,unsubscribe and revert a canceling subscription to a plan.
It also shows an example how to display subscription plan phase information and map the subscription status to it.