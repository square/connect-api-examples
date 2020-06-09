/* This sample demonstrates a bare-bones implementation of the Square Connect OAuth flow in Node.js:
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


const express = require('express');
const SquareConnect = require('square-connect');
const app = express();

const port = process.env.PORT || "8000";
const messages = require('./sandbox-messages')
const config = require('./config.json')

// Configure Square defcault client
const defaultClient = SquareConnect.ApiClient.instance
defaultClient.basePath = config.SQ_SANDBOX_BASEURL

// Configure Square OAuth API instance
const oauthInstance = new SquareConnect.OAuthApi();
const scopes = [] // INCLUDE PERMISSIONS YOU WANT YOUR SELLER TO GRANT YOUR APPLICATION

// Serves the link that merchants click to authorize your application
app.get("/sandbox_request_token", (req, res) => {
    var url = config.SQ_SANDBOX_BASEURL + `/oauth2/authorize?client_id=${config.SQ_SANDBOX_APP_ID}&` + `response_type=code&` + `scope=${scopes.join('+')}`
    res.send(
        `<p>
            <a href='${url}'>Authorize this application</a>
        </p>`
    )
});

/*
    Serves requests from Square to your application's redirect URL
    Note that you need to set your application's Redirect URL to
    http://localhost:8000/sandbox_callback from your application dashboard
*/
app.get('/sandbox_callback', (req, res) => {
    // Extract the returned authorization code from the URL
    var code = req.query.code

    if (code) {
        // Provide the code in a request to the Obtain Token endpoint
        var body = {
            client_id : config.SQ_SANDBOX_APP_ID,
            client_secret : config.SQ_SANDBOX_APP_SECRET,
            code : code,
            grant_type : 'authorization_code',
        }
        oauthInstance.obtainToken(body)
            // Extract the returned access token from the ObtainTokenResponse object
            .then(data => {
                // Here, instead of printing the access token, your application server should store it securely
                // and use it in subsequent requests to the Connect API on behalf of the merchant.
                res.send(messages.writeTokensOnSuccess(data.access_token, data.refresh_token, data.expires_at, data.merchant_id))
            })
            // The response from the Obtain Token endpoint did not include an access token. Something went wrong.
            .catch(error => {
                res.send(messages.displayError('Failed to obtain access token', error))
            })
    }
    // The request to the Redirect URL did not include an authorization code. Something went wrong.
    else {
        res.send(messages.displayError('Authorization failed. No code acquired.', ''))
    }
});

app.listen(port, () => {
    console.log(`Listening to requests on http://localhost:${port}`);
});
