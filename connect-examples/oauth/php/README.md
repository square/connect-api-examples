# Connect OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the OAuth flow for
Square APIs. It serves a link that directs merchants to the OAuth Permissions form
and handles the result of the authorization, which is sent to your application's
Redirect URL (specified on the application dashboard).

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/oauth#oauth-overview), along with the comments included in `callback.php`.

The Square open source code [LICENSE](LICENSE) details any restrictions
and warranties for this example project.

## Setup

### Download Composer and dependencies

This application requires the PHP Square SDK as well as DotEnv for reading environment variables, which you install via
Composer.

First, download Composer in this directory with the instructions on
[this page](https://getcomposer.org/download/).

After you've downloaded Composer, install the dependencies with the following
command from this directory:

    php composer.phar install

### Specify your application credentials

In order for the sample to work, you must create a file called `.env`:

* In this file, supply either production, sandbox, or both credentials.
* Be sure to put `true` or `false` for `USE_PROD` as it will change the domain being used.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `https://localhost:8000/callback.php`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is
allowed for `localhost` URLs to simplify the development process.

## Running the example

To run the example, execute the following from the directory that contains these files:

    php -S localhost:8000

You can then proceed through the OAuth flow by visiting `http://localhost:8000`
in your web browser.
