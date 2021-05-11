async function ApplePay(buttonEl, showApplePayElements) {
  const payments = await Square.payments(applicationId, locationId);
  const paymentRequest = getPaymentRequest();
  const req = await payments.paymentRequest(paymentRequest);
  const applePayButton = buttonEl;
  const applePay = await payments.applePay(req);
  showApplePayElements();

  async function eventHandler(event) {
    event.preventDefault(result.token);

    try {
      const result = await applePay.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  }

  applePayButton.addEventListener('click', eventHandler);
}

