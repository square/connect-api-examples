# Connect OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the OAuth flow for
Square APIs. It serves a link that directs merchants to the OAuth Permissions form
and handles the result of the authorization, which is sent to your application's
Redirect URL (specified on the application dashboard).

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/connect/v1/#oauth-overview), along with the comments included in `callback.php`.

## Setup

### Download Composer and Unirest

This application requires the Unirest HTTP library for PHP, which you install via
Composer.

First, download Composer in this directory with the instructions on
[this page](https://getcomposer.org/download/).

After you've downloaded Composer, install the Unirest dependency with the following
command from this directory:

    php composer.phar install

### Specify your application credentials

In order for the sample to work, you must specify fields in `callback.php` and `index.php`:

* In both files, replace the value of `$application_id` with your application's ID,
available on your [application dashboard](https://connect.squareup.com/apps).

* In `callback.php`, replace the value of `$applicationSecret` with your
application's secret, also available on your application dashboard.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://localhost:8000/callback.php`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is
allowed for `localhost` URLs to simplify the development process.

## Running the example

To run the example, execute the following from the directory that contains these files:

    php -S localhost:8000

You can then proceed through the OAuth flow by visiting `http://localhost:8000`
in your web browser.
