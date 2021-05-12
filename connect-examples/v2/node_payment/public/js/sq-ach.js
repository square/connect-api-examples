async function ACHPay(buttonEl) {
  const accountHolderName = document.getElementById('ach-account-holder-name').value.trim();
  let ach;
  let supported = true;
  try {
    ach = await window.payments.ach();
  } catch (e) {
    // If the ACH payment method is not supported by your account then 
    // do not show the ACH components
    if (e.name === 'PaymentMethodUnsupportedError') {
      document.getElementById("ach-message").innerHTML = "ACH payment is not supported by your account";
      supported = false;
      accountHolderName.disabled = true;
      return;
    }
  }
  
  async function eventHandler(event) {
    if (accountHolderName === '') {
      document.getElementById("ach-message").innerHTML = "Please input full name";
      return;
    }

    try {
      document.getElementById('message').innerHTML = '';
      const result = ach.tokenize({ 
        accountHolderName,
      });
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