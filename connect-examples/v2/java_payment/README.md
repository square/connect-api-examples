# Java Payment Form Example

This example hosts a payment form in Java. It is a Spring Boot app and requires Java 8 or newer.

## Setting up

First, you'll need to have created a Square application. If you haven't done this yet, you can quickly
set on up in the [Square Developer Portal](https://connect.squareup.com/apps).

Once you've created an Square application, you'll need both the Application ID and the
Personal Access Token for it. These are available in the Square Developer Portal as well.

If you want to test Apple Pay, you'll need to replace the contents of
`src/main/resources/public/.well-known/apple-developer-merchantid-domain-association`.
You can get real content for this file from the
[Square Developer Portal](https://connect.squareup.com/apps), in the Apple Pay section of your
application.

Note that Apple Pay cannot be tested when running locally. You'll need to deploy the app to try it out.


## Running locally

The app can be run on a command line using Maven. The application expects two environment variables
to be set: `SQUARE_APP_ID` and `SQUARE_ACCESS_TOKEN`. Both of these can be copied from the
[Developer Dashboard](https://connect.squareup.com/apps). Keep in mind that the access token is
sensitive and must remain private.

To get up and running, first clone the repo to your local computer.
Then open a command line terminal and run the following command:

```
# The following command sets environment variables and starts the application locally:
SQUARE_APP_ID=replace_me SQUARE_ACCESS_TOKEN=replace_me SQUARE_LOCATION_ID=replace_me mvn spring-boot:run
```

After running the above command, you can open a browser and go to
[http://localhost:5000](http://localhost:5000).

The default port used is `5000`, but this can be configured in the `application.properties` file.

If the credentials are not set or are invalid, the app will fail during startup.