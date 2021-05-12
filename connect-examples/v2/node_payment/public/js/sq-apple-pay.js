async function ApplePay(buttonEl, showApplePayElements) {
  const payments = Square.payments(window.applicationId, window.locationId);
  // Use global method from sq-payment-flow.js
  const paymentRequest = window.getPaymentRequest();
  const req = await payments.paymentRequest(paymentRequest);
  const applePayButton = buttonEl;
  let applePay;
  try {
    applePay = await payments.applePay(req);
    await applePay.attach('#apple-pay-button');
  } catch (error) {
    if (error.name === 'PaymentMethodUnsupportedError') {
      document.getElementById('apple-pay-button').style.display = 'none';
    }
    return;
  }

  showApplePayElements();
  async function eventHandler(event) {
    try {
      document.getElementById('message').innerHTML = '';
      const result = await applePay.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        // Use global method from sq-payment-flow.js
        window.createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  }

  applePayButton.addEventListener('click', eventHandler);
}

