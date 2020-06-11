# Square OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the OAuth flow for
Square APIs. It serves a link that directs merchants to the OAuth Permissions form
and handles the result of the authorization, which is sent to your application's
Redirect URL (specified on the application dashboard).

For a detailed walkthrough of this example, see [OAuth Walkthrough](https://developer.squareup.com/docs/oauth-api/walkthrough),
along with the comments included in `sandbox_callback.php`.

## Getting started

### Step 1: Download Composer and dependencies

This application requires the PHP Square SDK as well as DotEnv for reading environment variables, which you install via
Composer.

First, download Composer in this directory with the instructions on
[this page](https://getcomposer.org/download/).

After you've downloaded Composer, install the dependencies with the following
command from this directory:

```
php composer.phar install
```

### Step 2: Get your credentials and set the redirect URL:

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).
1. Choose **Open** on the card for an application.
1. At the top of the page, set the dashboard mode to **Sandbox**.
1. Choose **OAuth** in the left navigation pane. The OAuth page is shown.
1. In the **Sandbox Redirect URL** box, enter the URL for the callback you will implement to complete the OAuth flow:
    `http://localhost:8000/sandbox_callback.php`

    This example uses localhost in the Square Sandbox. You can use HTTP for localhost but an actual web server implementation must use HTTPS.
1. in the **Sandbox Application ID** box, copy the application ID.
1. In the **Sandbox Application Secret** box, choose **Show**, and then copy the application secret.
1. Click **Save**.
1. Create the `sandbox_config.php` file and add the following code. Follow the instructions in the file to replace the appropriate `REPLACE_ME` placeholders with the Sandbox application ID and Sandbox application secret. The file should look like the following:

    ```php
    <?php
    require 'vendor/autoload.php';

    // To keep it simple and because we are using sandbox, 
    // this example uses a named constant to store the application secret.
    // In production, you should encrypt sensitive data such as the application secret and OAuth tokens.
    // For more information on best practices, see the Square API OAuth documentation.

    // Sandbox application ID is used for the following:
    // --The client_id query parameters for the Square Authorization Page URL
    // --The client_id value in the POST body when calling Obtain Token
    // REPLACE_ME = the sandbox application ID from the application's OAuth tab in the Developer Dashboard.
    define('_SQ_SANDBOX_APP_ID', "REPLACE_ME");

    // Sandbox application secret is the client secret required to call obtain token.
    // REPLACE_ME = the sandbox application secret from the application's OAuth tab.
    define('_SQ_SANDBOX_APP_SECRET', "REPLACE_ME");
    ?>
    ```

    Note that OAuth Sandbox credentials begin with a sandbox prefix and that the base URL for calling Sandbox endpoints is https://connect.squareupsandbox.com. When you implement for production, you need production credentials and use the production environment.

    In addition, you should also avoid putting the application secret directly in your source code, encrypt the secret, and use other methods such as environment variables to reference the secret in your application.

### Step 3: Running the example

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).

1. In the Sandbox Test Accounts section, find one test acount and choose Open.

1. Start the PHP server, if it is not running:

    ```
    php -S localhost:8000
    ```

1. Open http://localhost:8000/sandbox_request_token.php to start.
