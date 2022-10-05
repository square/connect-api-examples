# Square OAuth Flow Example (Node.js)

This example demonstrates a bare-bones Node.js implementation of the OAuth flow for Square APIs. It serves a link that directs merchants to the OAuth Permissions form and handles the result of the authorization, which is sent to your application's Redirect URL (specified on the application dashboard).

## Getting started

### Step 1: Download dependencies

This application requires the Node.js Square SDK and Express, which you install via npm. Open your terminal in this directory and type:

```
npm install
```

### Step 2: Get your credentials and set the redirect URL:

1. Open the [Developer Dashboard](https://developer.squareup.com/apps).
1. Choose **Open** on the card for an application.
1. At the top of the page, set the dashboard mode to the environment that you want to work with by choosing **Sandbox** or **Production**.
1. Choose **OAuth** in the left navigation pane. The OAuth page is shown.
1. In the **Redirect URL** box, enter the URL for the callback you will implement to complete the OAuth flow:
   `http://localhost:8000/callback`

   You can use HTTP for localhost but an actual web server implementation must use HTTPS.

1. In the **Application ID** box, copy the application ID.
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

1. Start the Node server, if it is not running:

   ```
   npm start
   ```

1. Open http://localhost:8000/request_token to start.

# License

Copyright 2020 Square, Inc.
​

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
​
   http://www.apache.org/licenses/LICENSE-2.0
​
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Feedback

Rate this sample app [here](https://delighted.com/t/Z1xmKSqy)!
