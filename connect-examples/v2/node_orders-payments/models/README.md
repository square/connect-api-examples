# Models

This directory contains classes that abstracts the data for the templates found in the [Views](../views/README.md) directory

## Index-page-data.js

Defines a class which provides the list of CatalogItems and location information for [index template](../views/index.pug)

## Checkout-page-data.js

Defines a class which provides data on a single CatalogItem, available pickup times for an order and location information for [checkout template](../views/checkout.pug)


## Confirmation-page-data.js

Defines a class provides data on a single CatalogItem, a payment that was posted and its associated order and lastly location information for [process-payment](../views/process-payment.pug)

## Catalog-item-variation.js

Defines a class that abstracts the data for both a CatalogItem and its associated [CatalogImage](https://developer.squareup.com/docs/api/connect/v2#type-catalogimage). 

## Location-info.js

Defines a class that abstracts the data for a single [Location](https://developer.squareup.com/docs/api/connect/v2#type-location) object

## Order-info.js

Defines a class that abstracts the data for a single Square [Order](https://developer.squareup.com/docs/api/connect/v2#type-order) object.

## Payment-info.js

Defines a class that abstracts the data for a single Square Payment object returned after creating a payment.

## Pickup-times.js

Defines a class that abstracts the pickup time info. All pick up times are relative to an instance of the javascript Date object which records the current time.




