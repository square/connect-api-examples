async function ACHPay(buttonEl) {
  const payments = await Square.payments(applicationId, locationId);

  try {
    const ach = await payments.ach();
  } catch (e) {
    // If the ACH payment method is not supported by your account then 
    // do not show the ACH components
    if (e.name === 'PaymentMethodUnsupportedError') {
      document.getElementById('ach-wrapper').style.display = 'none';
    }
  }
  
  async function eventHandler(event) {
    event.preventDefault();

    try {
      const result = ach.tokenize({ accountHolderName: "John Wick" });
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  }

  const achButton = buttonEl;
  achButton.addEventListener('click', eventHandler);
}