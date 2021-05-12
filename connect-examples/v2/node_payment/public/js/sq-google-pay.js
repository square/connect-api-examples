async function GooglePay(buttonEl) {
  const payments = await Square.payments(applicationId, locationId);
  // Use global method from sq-payment-flow.js
  const paymentRequest = window.getPaymentRequest();
  const req = await payments.paymentRequest(paymentRequest);
  const googlePay = await payments.googlePay(req);
  const googlePayButton = buttonEl;

  async function eventHandler(event) {
    try {
      document.getElementById('message').innerHTML = '';
      const result = await googlePay.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        // Use global method from sq-payment-flow.js
        window.createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  }

  googlePayButton.addEventListener('click', eventHandler);
}

