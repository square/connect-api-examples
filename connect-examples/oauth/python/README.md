# Connect OAuth Flow Example (Python)

This example demonstrates a bare-bones Python implementation of the Square Connect OAuth flow. The application links merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to the application's Redirect URL.

For more information, see [OAuth Overview](https://developer.squareup.com/docs/oauth-api/what-it-does), along with the comments included in `oauth-flow.py`.

## Setup

### Install Required Modules
Open your terminal at this directory and type:
```
pip install -r requirements.txt
```

### Uses Flask

This application requires Python and the Flask web framework.

### Get your credentials and set the redirect URL:

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).
1. Choose **Open** on the card for an application.
1. At the top of the page, set the dashboard mode to the environment that you want to work with by choosing **Sandbox** or **Production**.
1. Choose **OAuth** in the left navigation pane. The OAuth page is shown.
1. In the **Redirect URL** box, enter the URL for the callback you will implement to complete the OAuth flow:
    `http://127.0.0.1:5000/callback`

    You can use HTTP for localhost but an actual web server implementation must use HTTPS.
1. In the **Application ID** box, copy the application ID.
1. In the **Application Secret** box, choose **Show**, and then copy the application secret.
1. Click **Save**.
1. In your project directory, create a copy of the `.env.example` file and name it `.env`
1. In the newly created .env file, replace the `your-environment` with either `sandbox` or `production`
1. Replace the `your-application-id` and `your-application-secret` placeholders with the Sandbox or Production application ID and application secret, respectively.

    Note that OAuth Sandbox credentials begin with a sandbox prefix and that the base URL for calling Sandbox endpoints is https://connect.squareupsandbox.com. When you implement for production, you need production credentials and use https://connect.squareup.com as the base URL.

   **WARNING**: Never check your credentials/access_token into your version control system. We've added `.env` to the `.gitignore` file to help prevent uploading confidential information.

## Running the example

To run the example, execute the following from the command line:

    FLASK_APP=oauth-flow.py flask run

You can then proceed through the OAuth flow by going to `http://127.0.0.1:5000/` in your web browser.

## Feedback
Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!