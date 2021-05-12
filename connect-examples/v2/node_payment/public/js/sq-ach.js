async function ACHPay(buttonEl) {
  const payments = await Square.payments(applicationId, locationId);
  const firstName = document.getElementById('ach-first-name');
  const lastName = document.getElementById('ach-last-name');
  let ach;
  let supported = true;
  try {
    ach = await payments.ach();
  } catch (e) {
    // If the ACH payment method is not supported by your account then 
    // do not show the ACH components
    if (e.name === 'PaymentMethodUnsupportedError') {
      document.getElementById("ach-message").innerHTML = "ACH payment is not supported by your account";
      supported = false;
      firstName.disabled = true;
      lastName.disabled = true;
    }
  }
  
  async function eventHandler(event) {
    if (!supported) {
      return;
    }

    if (firstName.value == "" || lastName.value == "") {
      document.getElementById("ach-message").innerHTML = "Please input full name";
      return;
    }
    
    let accountHolderName = `${firstName.value} ${lastName.value}`;

    try {
      document.getElementById('message').innerHTML = '';
      const result = ach.tokenize({ accountHolderName: accountHolderName });
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        // Use global method from sq-payment-flow.js
        window.createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  }

  const achButton = buttonEl;
  achButton.addEventListener('click', eventHandler);
}