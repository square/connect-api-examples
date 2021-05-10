async function GooglePay(buttonEl) {
  const payments = await Square.payments(applicationId, locationId);
  const paymentRequest = getPaymentRequest();
  const req = await payments.paymentRequest(paymentRequest);
  const googlePay = await payments.googlePay(req);
  const googlePayButton = buttonEl;

  async function eventHandler(event) {
    event.preventDefault();

    try {
      const result = await googlePay.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
      }
    } catch (e) {
      console.error(e);
    }
  }

  googlePayButton.addEventListener('click', eventHandler);
}

