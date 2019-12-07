# Connect OAuth Flow Example (Python)

This example demonstrates a bare-bones Python implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://developer.squareup.com/docs/oauth-api/what-it-does), along with the comments included in `oauth-flow.py`.

## Setup

### Install Square Python SDK
Open your terminal at this directory and type:
```
pip install squareup
```

### Install Bottle

This application requires Python and the Bottle web framework. If you have Python
installed, you can install Bottle by following [these steps](http://bottlepy.org/docs/dev/tutorial.html#installation).

### Square Sandbox testing
If you want to run this sample against the Square Sandbox environment:

1. Set your application dashboard to **Sandbox Settings** mode before completing
the following steps.
1. Add a new **Sandbox Test Account**:

   a. Click **New Account** on the dashboard home page.<br>
   b. Give the account a name and pick a country. <br>
   c. Uncheck **Automatically create authorizations for all my current apps**.<br>

1. Click **Launch** on the new test account to open the sandbox seller dashboard
for the account. The OAuth flow will create an authorization for this account.


### Specify your application credentials

In order for the sample to work, you must specify two fields in `oauth-flow.py`:

* Replace the value of `application_id` with your application's ID, available on your
[application dashboard](https://connect.squareup.com/apps).

* Replace the value of `application_secret` with your application's secret, also
available on your application dashboard.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://localhost:8080/callback`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is
allowed for `localhost` URLs to simplify the development process.

### (OPTIONAL) Set the value of authorization `scopes`
Chose scopes from the [permission set](../OAuthPermissions.md) you
want to authorize the account to get authorization for. If you do not set this value,
`MERCHANT_PROFILE_READ PAYMENTS_READ SETTLEMENTS_READ BANK_ACCOUNTS_READ` are applied.

## Running the example

To run the example, execute the following from the command line:

    python oauth-flow.py

You can then proceed through the OAuth flow by going to `http://localhost:8080`
in your web browser.
