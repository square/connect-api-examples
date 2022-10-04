# Connect OAuth Flow Example (Java)

This example demonstrates a bare-bones Java implementation of the Square Connect
OAuth flow. The application links merchants to the OAuth Permissions form and
handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://developer.squareup.com/docs/oauth-api/overview),
along with the comments included in `OAuthHandler.java`.

## Setup

### Square Sandbox testing

If you want to run this sample against the Square Sandbox environment:

1. Set your application dashboard to **Sandbox Settings** mode before completing
   the following steps.
1. Add a new **Sandbox Test Account**:

   a. Click **New Account** on the dashboard home page.<br>
   b. Give the account a name and pick a country. <br>
   c. Uncheck **Automatically create authorizations for all my current apps**.<br>

1. Click **Launch** on the new test account to open the sandbox seller dashboard for
   the account. The OAuth flow will create an authorization for this account.

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to
`http://localhost:8000/callback`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is
allowed for `localhost` URLs to simplify the development process.

### Specify your application credentials

In order for the sample to work, you must specify the following fields in `OAuthHandler.java`:

- Set the value of `ENVIRONMENT` to one of `Environment.SANDBOX`, `Environment.PRODUCTION` or `Environment.CUSTOM`

- For sandbox testing, set the value of `CONNECT_HOST` to `https://connect.squareupsandbox.com`.
  Otherwise, use `https://connect.squareup.com`

- Replace the value of `APPLICATION_ID` with your application's ID, available on your
  [application dashboard](https://connect.squareup.com/apps).

- Replace the value of `APPLICATION_SECRET` with the application secret, available from the OAuth tab in the Developer Dashboard

- (OPTIONAL) Change the values in the list `SCOPES` to the [permission set](../OAuthPermissions.md) you
  want to authorize the account to get authorization for. If you do not set this value,
  `MERCHANT_PROFILE_READ PAYMENTS_READ SETTLEMENTS_READ BANK_ACCOUNTS_READ` are applied.

### Compile with Maven

From this directory, run `mvn install` to compile the application.

## Running the example

If you are testing in the sandbox, be sure that your test account seller dashboard
is still open in another browser tab.

To run the example, execute the following from this directory:

    java -cp target/shaded-oauthexample-1.5-SNAPSHOT.jar com.squareup.oauthexample.OAuthHandler

You can then proceed through the OAuth flow by going to `http://localhost:8000`
in your web browser.

## Feedback

Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!
