# Java Payment Form Example

This example hosts a payment form in a Java. It is a Spring Boot app and requires Java 8 or newer.
It can run locally and is structured to be easily deployable on
[Heroku](https://devcenter.heroku.com/articles/getting-started-with-java) as well.


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

To get up and running, open a command line terminal and run the following commands.

```
git clone https://github.com/square/connect-api-examples.git
cd connect-api-examples/connect-examples/v2/java_payment

# The following command sets environment variables and starts the application locally:
SQUARE_APP_ID=replace_me SQUARE_ACCESS_TOKEN=replace_me mvn spring-boot:run
```

After running the above command, you can open a browser and go to
[http://localhost:5000](http://localhost:5000).

The default port used is `5000`, but this can be configured in the `application.properties` file.

If the credentials are not set or are invalid, the app will fail during startup.


## Running on Heroku

Heroku is a cloud platform provider that supports easily deploying applications and has a free tier.
This sample app can be deployed to Heroku without any code changes.

### Prerequisites

* A Heroku account. Sign up at the [Heroku website](https://www.heroku.com/).
* Git command line tools installed, and basic familiarity git.
  See [installation instructions](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).
* The Heroku CLI tool installed.
  See [installation instructions](https://devcenter.heroku.com/articles/heroku-cli).


### Deployment Steps

You can run this sample app with just a few commands. For the commands with `replace_me`, you'll
need to copy the values from the [Square Developer Portal](https://connect.squareup.com/apps).

To get started, open up a command line terminal.

```
# Create a directory that will contain the code that will be deployed. Any directory name is fine.
mkdir connect-v2-payment-example
cd connect-v2-payment-example

# Copy the sample code from GitHub
curl -L https://github.com/square/connect-api-examples/tarball/master | tar -xz --strip=4 '*/connect-examples/v2/java_payment'

# Initialize the directory as a new git repository.
git init
git add .
git commit -m 'Initial commit'

# Create a new Heroku app.
# If this command fails, you may need to run this first: heroku login
heroku create

# Set the environment variables required by the application.
# If you don't do this, or set them to invalid values, the application will not start.
# IMPORTANT: Don't forget to replace `replace_me` with real values.
heroku config:set SQUARE_APP_ID=replace_me
heroku config:set SQUARE_ACCESS_TOKEN=replace_me

# Push the code to Heroku, which causes Heroku to build and run the application.
git push heroku master

# Open up the application in your browser.
# There may be a small delay when your app starts up for the first time.
heroku open
```

You can manage or delete your newly created app in the
[Heroku Dashboard](https://dashboard.heroku.com/apps).

If the application is not starting, you may want to check the logs:

```
heroku logs
```
