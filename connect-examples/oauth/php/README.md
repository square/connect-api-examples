# Square OAuth Flow Example (PHP)

This example demonstrates a bare-bones PHP implementation of the OAuth flow for
Square APIs. It serves a link that directs merchants to the OAuth Permissions form
and handles the result of the authorization, which is sent to your application's
Redirect URL (specified on the application dashboard).

For a detailed walkthrough of this example, see [OAuth Walkthrough](https://developer.squareup.com/docs/oauth-api/walkthrough),
along with the comments included in `sandbox_callback.php`.

## Getting started

### Requirements

- 8.0 <= PHP

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
1. At the top of the page, set the dashboard mode to the environment that you want to work with by choosing **Sandbox** or **Production**.
1. Choose **OAuth** in the left navigation pane. The OAuth page is shown.
1. In the **Redirect URL** box, enter the URL for the callback you will implement to complete the OAuth flow:
   `http://localhost:8000/callback.php`

   You can use HTTP for localhost but an actual web server implementation must use HTTPS.

1. in the **Application ID** box, copy the application ID.
1. In the **Application Secret** box, choose **Show**, and then copy the application secret.
1. Click **Save**.
1. In your project directory, create a copy of the `.env.example` file and name it `.env`
1. In the newly created .env file, replace the `your-environment` with either `sandbox` or `production`
1. Replace the `your-application-id` and `your-application-secret` placeholders with the Sandbox or Production application ID and application secret, respectively.

   Note that OAuth Sandbox credentials begin with a sandbox prefix and that the base URL for calling Sandbox endpoints is https://connect.squareupsandbox.com. When you implement for production, you need production credentials and use https://connect.squareup.com as the base URL.

   **WARNING**: Never check your credentials/access_token into your version control system. We've added `.env` to the `.gitignore` file to help prevent uploading confidential information.

### Step 3: Running the example

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).

1. For testing in sandbox mode, in the Sandbox Test Accounts section, find one test acount and choose Open. For production mode, open the seller dashboard at https://squareup.com/dashboard/

1. Start the PHP server, if it is not running:

   ```
   php -S localhost:8000
   ```

1. Open http://localhost:8000/request_token.php to start.

## Feedback

Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!
