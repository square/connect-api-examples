# Square OAuth Flow Example (Node.js)

This example demonstrates a bare-bones Node.js implementation of the OAuth flow for
Square APIs. It serves a link that directs merchants to the OAuth Permissions form
and handles the result of the authorization, which is sent to your application's
Redirect URL (specified on the application dashboard).

## Getting started

### Step 1: Download dependencies

This application requires the Node.js Square SDK and Express, which you install via
npm. Open your terminal in this directory and type:

```
npm install
```

### Step 2: Get your credentials and set the redirect URL:

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).
1. Choose **Open** on the card for an application.
1. At the top of the page, set the dashboard mode to **Sandbox**.
1. Choose **OAuth** in the left navigation pane. The OAuth page is shown.
1. In the **Sandbox Redirect URL** box, enter the URL for the callback you will implement to complete the OAuth flow:
    `http://localhost:8000/sandbox_callback`

    This example uses localhost in the Square Sandbox. You can use HTTP for localhost but an actual web server implementation must use HTTPS.
1. In the **Sandbox Application ID** box, copy the application ID.
1. In the **Sandbox Application Secret** box, choose **Show**, and then copy the application secret.
1. Click **Save**.
1. Navigate to the `config.json` file in your project directory and replace the appropriate `REPLACE_ME` placeholders with the Sandbox application ID and Sandbox application secret. 

    Note that OAuth Sandbox credentials begin with a sandbox prefix and that the base URL for calling Sandbox endpoints is https://connect.squareupsandbox.com. When you implement for production, you need production credentials and use https://connect.squareup.com as the base URL.

    In addition, you should also avoid putting the application secret directly in your source code, encrypt the secret, and use other methods such as environment variables to reference the secret in your application.

### Step 3: Running the example

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).

1. In the Sandbox Test Accounts section, find one test acount and choose Open.

1. Start the Node server, if it is not running:

    ```
    node index.js
    ```

1. Open http://localhost:8000/sandbox_request_token to start.