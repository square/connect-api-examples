Square Checkout Demo
=========================

This is a simple example application that utilizes Square's Checkout API. This examples does assume you are familiar with PHP development.

It takes a single payment, declared by the user, and creates an order to use in the Checkout API.
## Setup


### Requirements

* PHP >= 7.1

### Install the PHP client library

This sample already includes the `square/square` dependency in its `composer.json`
file. To install the client library:

1. Make sure you've downloaded Composer, following the instructions
[here](https://getcomposer.org/download/).

2. Run the following command from the directory containing `composer.json`:

```
php composer.phar install
```

### Specify your application credentials

In order for the example to work, you must edit the file called `.env` with your application credentials and environment configuration.

Open your [application dashboard](https://developer.squareup.com/). Now supply either production, sandbox, or both credentials. Open this file and update the following variables:
* WARNING: never upload .env with your credential/access_token

| Variable               |  Type    |   Description   |
|------------------------|:---------|-----------------|
| ENVIRONMENT (`*`)      | `string` | `production` or `sandbox` depending on what type of endpoint you want to hit. For testing purposes please use the sandbox mode (already configured in the `.env`)   |
| *_APP_ID               | `string` | `*` Application ID found on your Developer App Dashboard, Credentials tab.  |
| *_ACCESS_TOKEN         | `string` | `*` Access Token found at the Developer App Dashboard, Credentials tab. |
| *_LOCATION_ID           | `string` | `*` Location found at the Developer App Dashboard, Location tab. |

## Running the sample

From the sample's root directory, run:

    php -S localhost:8888

This will start the server on `localhost:8888`, which you can navigate to in your favorite browser.


For more information about Checkout please visit:
* https://docs.connect.squareup.com/payments/checkout/overview
* https://docs.connect.squareup.com/api/connect/v2#navsection-checkout
* https://github.com/square/square-php-sdk/blob/master/docs/Api/CheckoutApi.md
