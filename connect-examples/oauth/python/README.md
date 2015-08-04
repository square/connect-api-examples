# Connect OAuth Flow Example (Python)

This example demonstrates a bare-bones Python implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/connect/v1/#oauth-overview), along with the comments included in `oauth-flow.py`.

## Setup

### Install Bottle

This application requires Python and the Bottle web framework. If you have Python
installed, you can install Bottle by following 
[these steps](http://bottlepy.org/docs/dev/tutorial.html#installation).

### Specify your application credentials

In order for the sample to work, you must specify two fields in `oauth-flow.py`:

* Replace the value of `application_id` with your application's ID, available on your
[application dashboard](https://connect.squareup.com/apps).

* Replace the value of `application_secret` with your application's secret, also available on your application dashboard.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://localhost:8080/callback`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is allowed for `localhost` URLs to simplify the development process.

## Running the example

To run the example, execute the following from the command line:

    python oauth-flow.py

You can then proceed through the OAuth flow by going to `http://localhost:8080` in your web browser.