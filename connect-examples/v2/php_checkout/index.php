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
    <div class="form-container">
      <form method="post" action="checkout.php">
        <input name="amount" type="text" value="5.00" />
        <button type="submit">Pay now!</button>
      </form>
    </div>
  </body>
</html>
