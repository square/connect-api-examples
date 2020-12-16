/*
Copyright 2020 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow in Node.js:
    1. A merchant clicks the authorization link served by the root path (http://localhost:8000/sandbox_request_token)
    2. The merchant signs in to Square and submits the Permissions form. Note that if the merchant
        is already signed in to Square, and if the merchant has already authorized your application,
        the OAuth flow automatically proceeds to the next step without presenting the Permissions form.
    3. Square sends a request to your application's Redirect URL
        (which should be set to http://localhost:8000/sandbox_callback on your application dashboard)
    4. The server extracts the authorization code provided in Square's request and passes it
        along to the Obtain Token endpoint.
    5. The Obtain Token endpoint returns an access token your application can use in subsequent requests
        to the Connect API.
This sample requires the following dependencies:
    express (https://expressjs.com/)
*/


const dotenv = require('dotenv').config(); // Loads .env file
const express = require('express');
const cookieParser = require('cookie-parser');
const md5 = require('md5');
const { ApiError, Client, Environment } = require('square');
const app = express();
app.use(cookieParser());

const {PORT, SQ_SANDBOX_APP_ID, SQ_SANDBOX_APP_SECRET } = process.env;

// Check if example secrets were set
if (!SQ_SANDBOX_APP_ID || !SQ_SANDBOX_APP_SECRET) {
    console.warn('\x1b[33m%s\x1b[0m','Missing secrets! Configure set values for SQ_SANDBOX_APP_ID and SQ_SANDBOX_APP_SECRET in a .env file.');
    process.exit(1);
}

const port = PORT || "8000";
const messages = require('./sandbox-messages');

// The default environment for this example is sandbox
let basePath = `https://connect.squareupsandbox.com`;

// Configure Square defcault client
const squareClient = new Client({
    environment: Environment.Sandbox
});

// Configure Square OAuth API instance
const oauthInstance = squareClient.oAuthApi;

// INCLUDE PERMISSIONS YOU WANT YOUR SELLER TO GRANT YOUR APPLICATION
const scopes = [
    "ITEMS_READ",
    "MERCHANT_PROFILE_READ",
    "PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS",
    "PAYMENTS_WRITE",
    "PAYMENTS_READ"
];

/**
 * Description:
 *  Serves the link that merchants click to authorize your application
 */
app.get("/sandbox_request_token", (req, res) => {
    // Set the Auth_State cookie with a random md5 string to protect against cross-site request forgery.
    // Auth_State will expire in 300 seconds (5 mins) after the page is loaded.
    var state = md5(Date.now())
    var url = basePath + `/oauth2/authorize?client_id=${process.env.SQ_SANDBOX_APP_ID}&` + `response_type=code&` + `scope=${scopes.join('+')}` + `&state=` + state
    res.cookie("Auth_State", state, {expire: Date.now() + 300000}).send(
        `<p>
            <a href='${url}'> SANDBOX: Authorize this application</a>
        </p>`
    )
});

/**
 * Description:
 *  Serves requests from Square to your application's redirect URL
 *  Note that you need to set your application's Redirect URL to
 *  http://localhost:8000/sandbox_callback from your application dashboard
 *
 * Query Parameters:
 *  state: the Auth State set in request_token
 *  response_type: the type of the response; should be "code"
 *  code: the authorization code
 */
app.get('/sandbox_callback', async (req, res) => {
    console.log(req.query);
    // Verify the state to protect against cross-site request forgery.
    if (req.cookies["Auth_State"] !== req.query['state']) {
        res.send(messages.displayStateError());
    }

    else if (req.query['error']) {
        // Check to see if the seller clicked the Deny button and handle it as a special case.
        if(("access_denied" === req.query['error']) && ("user_denied" === req.query["error_description"])) {
            res.send(messages.displayError("Authorization denied", "You chose to deny access to the app."));
        }
        // Display the error and description for all other errors.
        else {
            res.send(messages.displayError(req.query["error"], req.query["error_description"]));
        }
    }
    // When the response_type is "code", the seller clicked Allow
    // and the authorization page returned the auth tokens.
    else if ("code" === req.query["response_type"]) {
        // Extract the returned authorization code from the URL
        var { code } = req.query;

        try {
            let { result } = await oauthInstance.obtainToken({
                // Provide the code in a request to the Obtain Token endpoint
                code,
                clientId: process.env.SQ_SANDBOX_APP_ID,
                clientSecret: process.env.SQ_SANDBOX_APP_SECRET,
                grantType: 'authorization_code'
            });

            let {
                // Extract the returned access token from the ObtainTokenResponse object
                accessToken,
                refreshToken,
                expiresAt,
                merchantId
            } = result;

            // Because we want to keep things simple and we're using Sandbox,
            // we call a function that writes the tokens to the page so we can easily copy and use them directly.
            // In production, you should never write tokens to the page. You should encrypt the tokens and handle them securely.
            res.send(messages.writeTokensOnSuccess(accessToken, refreshToken, expiresAt, merchantId));
        } catch (error) {
            // The response from the Obtain Token endpoint did not include an access token. Something went wrong.
            if (error instanceof ApiError) {
                res.send(messages.displayError('Exception', JSON.stringify(error.result)));
            } else {
                res.send(messages.displayError('Exception', JSON.stringify(error)));
            }
        }
    }
    else {
        // No recognizable parameters were returned.
        res.send(messages.displayError("Unknown parameters", "Expected parameters were not returned"));
    }
});

app.listen(port, () => {
    console.log(`Listening to requests on http://localhost:${port}`);
});
