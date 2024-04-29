<!DOCTYPE html>
<html>
  <head>
    <title>Square Checkout</title>
    <meta name="description" content="An example of Square Checkout on Glitch">
    <link rel='stylesheet' href='/main.css'>
    <link rel='stylesheet' href='/normalize.css'>
    <link id="favicon" rel="icon" href="https://cdn.glitch.com/4c9bc573-ca4c-48de-8afe-501eddad0b79%2Fsquare-logo.svg?1521834224783" type="image/x-icon">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name='viewport' content='width=device-width, initial-scale=1'>
  </head>
  <body>
    <header class="container">
      <div id="square-logo"></div>
      <h1 class="header">Simple Checkout</h1>
    </header>
    <div class="container">
      <form class="form-container" method="post" action="checkout.php">
        <div class="item-line">
          <div class="item-label">Test Item A x 1</div>
          <div class="item-amount">$5.00</div>
        </div>
        <div class="item-line">
          <div class="item-label">Test Item B x 3</div>
          <div class="item-amount">$30.00</div>
        </div>
        <div class="item-line total-line">
          <div class="item-label">Total</div>
          <div class="item-amount">$35.00</div>
        </div>
        <button type="submit">Pay now!</button>
      </form>
    </div>
  </body>
</html>
