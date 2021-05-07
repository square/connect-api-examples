<link rel="stylesheet" href="public/style.css" type="text/css">
<meta name="viewport" content="width=device-width">
<?php
  // The following functions are display helpers and should only be used in Sandbox applications.

  // Write token information to the page.
  // Do not do this in production. In production, replace this function with one that stores the OAuth tokens securely.
  function writeTokensOnSuccess($accessToken, $refreshToken, $expiresAt, $merchantId) {
?> 
    <div class="wrapper">
      <div class="messages">
        <h1>Authorization Succeeded</h1>
          <div style='color:rgba(204, 0, 35, 1)'><strong>Caution:</strong> NEVER store or share OAuth access tokens or refresh tokens in clear text.
                Use a strong encryption standard such as AES to encrypt OAuth tokens. Ensure the production encryption key is not
                accessible to anyone who does not need it.
          </div>
          <br/>
          <div><strong>OAuth access token:</strong> <?= $accessToken ?></div>
          <div><strong>OAuth access token expires at:</strong> <?= $expiresAt ?></div>
          <div><strong>OAuth refresh token:</strong> <?= $refreshToken ?></div>
          <div><strong>Merchant Id:</strong> <?= $merchantId ?></div>
          <div><p>You can use this OAuth access token to call Create Payment and other APIs that were authorized by this seller.</p>
          <p>Try it out with <a href='https://developer.squareup.com/explorer/square/payments-api/create-payment' target='_blank'>API Explorer</a>.</p>
        </div>
    </div>
  </div>

<?php
  }

  // Display error message if the state doesn't match the state originally passed to the authorization page.
  function displayStateError() {
?>  
  <div class="wrapper">
    <div class="messages">
      <h1>Authorization failed</h1>
      <div>Invalid state parameter.</div>
    </div>
  </div>
<?php
  }

  // Disply error message.
  function displayError($error, $error_description) {
?>
  <div class="wrapper">
    <div class="messages">
      <h1>Authorization failed</h1>
      <div>Error: <?= $error ?></div>
      <div>Error Details: <?= $error_description ?></div>
    </div>
  </div>
<?php
  }

?>