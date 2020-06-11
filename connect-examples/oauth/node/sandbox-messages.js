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
*/

// Write tokens to page on success
// WARNING: NEVER store or share OAuth access tokens or refresh tokens in clear text.
function writeTokensOnSuccess(access_token, refresh_token, expires_at, merchant_id) {
    return (
        `
        <h1>Authorization succeeded</h1>
        <div style='color:red;'>
            <strong>Caution:</strong> NEVER store or share OAuth access tokens or refresh tokens in clear text.
                Use a strong encryption standard such as AES to encrypt OAuth tokens. Ensure the production encryption key is not
                accessible to anyone who does not need it.
        </div><br/>
        <div><strong>OAuth access token: </strong> ${access_token} </div><br/>
        <div><strong>OAuth access token expires at: </strong> ${expires_at} </div><br/>
        <div><strong>OAuth refresh token: </strong>  ${refresh_token} </div><br/>
        <div><strong>Merchant Id: </strong>  ${merchant_id} </div><br/>
        <div><p>You can this OAuth access token in Sandbox to call Create Payment and other APIs that were authorized by this seller.</p>
          <p>Try it out with <a href='https://developer.squareup.com/explorer/square/payments-api/create-payment' target='_blank'>API Explorer</a>.</p>
        </div><br/>`
    )
}

// Display error message if the state doesn't match the state originally passed to the authorization page.
function displayStateError() {
    return (
        `<h1>Authorization failed</h1>
        <div>Invalid state parameter.</div>`
    )
}


// Display error if access token not acquired
function displayError(error, error_description) {
    return (
        `
        <h1>Authorization failed</h1>
        <div>Error: ${error} </div>
        <div>Error Details: ${error_description} </div>
        `
    )
}

module.exports = {
    writeTokensOnSuccess : writeTokensOnSuccess,
    displayStateError : displayStateError,
    displayError : displayError
}
