async function ApplePay(buttonEl) {
  const paymentRequest = window.payments.paymentRequest(
    // Use global method from sq-payment-flow.js
    window.getPaymentRequest()
  );

  let applePay;
  try {
    applePay = await window.payments.applePay(paymentRequest);
  } catch (e) {
    console.error(e)
    return;
  }

  async function eventHandler(event) {
    // Clear any existing messages
    window.paymentFlowMessageEl.innerText = '';

    try {
      const result = await applePay.tokenize();
      if (result.status === 'OK') {
        // Use global method from sq-payment-flow.js
        window.createPayment(result.token);
      }
    } catch (e) {
      if (e.message) {
        window.showError(`Error: ${e.message}`);
      } else {
        window.showError('Something went wrong');
      }
    }
  }

  buttonEl.addEventListener('click', eventHandler);
}
