# Connect OAuth Flow Example (Java)

This example demonstrates a bare-bones Java implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://docs.connect.squareup.com/api/connect/v1/#oauth-overview), along with the comments included in `OAuthHandler.java`.

## Setup

### Set your application's Redirect URL

On your application dashboard, set your application's Redirect URL to `http://localhost:8000/callback`.

Note that applications that don't use a `localhost` URL must use HTTPS. HTTP is allowed for `localhost` URLs to simplify the development process.

### Specify your application credentials

In order for the sample to work, you must specify two fields in `OAuthHandler.java`:

* Replace the value of `_applicationId` with your application's ID, available on your
[application dashboard](https://connect.squareup.com/apps).

* Replace the value of `_applicationSecret` with your application's secret, also available on your application dashboard.

### Compile with Maven

From this directory, run `mvn install` to compile the application.

## Running the example

To run the example, execute the following from this directory:

    java -cp target/shaded-oauthexample-1.0-SNAPSHOT.jar com.squareup.oauthexample.OAuthHandler

You can then proceed through the OAuth flow by going to `http://localhost:8000` in your web browser.