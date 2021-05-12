async function ApplePay(htmlEl, showApplePayElements) {  
  const paymentRequest = await payments.paymentRequest(
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

  htmlEl.addEventListener('click', eventHandler);
}

