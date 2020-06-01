# Connect OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the OAuth flow for Square APIs. It serves a link that directs merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to your application's Redirect URL (specified on the [application dashboard](https://developer.squareup.com/)).

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/oauth#oauth-overview), along with the comments included in `callback.php`.

## Running the example

### Requirements

This application requires the PHP Square SDK as well as DotEnv for reading environment variables, which you install via Composer.

> To install Composer follow the instructions on [this page](https://getcomposer.org/download/).


### Install dependencies using [Composer](https://getcomposer.org/)

    composer install

Or if you have a local `composer.phar` file you can run the following:
```
php composer.phar install
```

### Specify your application credentials

In order for the sample to work, you must create a file called `.env` that will contain your application credentials and environment configuration. To get you started create a copy of the `.env.example` file:

```
cp .env.example .env
```

Open your [application dashboard](https://developer.squareup.com/). Now supply either production, sandbox, or both credentials. Open this file and update the following variables:
* WARNING: never upload .env with your credential/access_token

| Variable               |  Type    |   Description   |
|------------------------|:---------|-----------------|
| ENVIRONMENT (`*`)      | `string` | `production` or `sandbox` depending on what type of endpoint you want to hit. For testing purposes please use the sandbox mode (already configured in the `.env.example`)   |
| *_APP_ID               | `string` | `*` Application ID found on your Developer App Dashboard, Credentials tab.  |
| *_ACCESS_TOKEN         | `string` | `*` Access Token found at the Developer App Dashboard, Credentials tab. |
| *_APP_SECRET           | `string` | `*` Application Secret found at the Developer App Dashboard, OAuth tab. |


### Set your application's Redirect URL

On your [application dashboard](https://developer.squareup.com/), set your application's Redirect URL to:

```
http://localhost:8000/callback.php
```

> Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is allowed for `localhost` URLs to simplify the development process.

## Start a development server

To run the example, execute the following from the directory that contains these files:

    php -S localhost:8000

You can then proceed through the OAuth flow by visiting `http://localhost:8000` in your web browser.