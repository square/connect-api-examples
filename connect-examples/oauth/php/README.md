# Connect OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/connect/v1/#oauth-overview), along with the comments included in `callback.php`.

## Setup

### Download Unirest 

This application requires the Unirest HTTP library for PHP. You can download it at
[unirest.io/php.html](http://unirest.io/php.html).

### Specify your application credentials

In order for the sample to work, you must specify three fields in `callback.php`, and one field
in `index.php`:

* In `callback.php`, replace `path/to/Unirest.php` with the path to the Unirest PHP library.

* In both files, replace the value of `$application_id` with your application's ID, available on your
[application dashboard](https://connect.squareup.com/apps).

* In `callback.php`, replace the value of `$applicationSecret` with your application's secret, also available on your application dashboard.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://localhost:8000/callback.php`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is allowed for `localhost` URLs to simplify the development process.

## Running the example

To run the example, execute the following from the directory that containss these files:

    php -S localhost:8000

You can then proceed through the OAuth flow by going to `http://localhost:8000` in your web browser.