# Connect OAuth Flow Example (Python)

This example demonstrates a bare-bones Python implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/oauth#oauth-overview), along with the comments included in `oauth-flow.py`.

## Setup

### Install SquareConnect SDK
Open your terminal at this directory and type:
```
pip install git+https://github.com/square/connect-python-sdk.git
```

### Install Flask

This application requires Python and the Flask web framework. If you have Python
installed, you can install Flask by following
[these steps](https://flask.palletsprojects.com/en/1.1.x/installation/).

### Specify your application credentials

In order for the sample to work, you must specify two fields in `oauth-flow.py`:

* Replace the value of `application_id` with your application's ID, available on your
[application dashboard](https://developer.squareup.com/apps).

* Replace the value of `application_secret` with your application's secret, also available on your application dashboard.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://127.0.0.1:5000/callback`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is allowed for `localhost` URLs to simplify the development process.

## Running the example

To run the example, execute the following from the command line:

    FLASK_APP=oauth-flow.py flask run

You can then proceed through the OAuth flow by going to `http://127.0.0.1:5000/` in your web browser.
