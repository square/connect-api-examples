async function GooglePay(htmlEl) {
  const paymentRequest = await window.payments.paymentRequest(
    // Use global method from sq-payment-flow.js
    window.getPaymentRequest()
  );
  const googlePay = await payments.googlePay(paymentRequest);

  await googlePay.attach(htmlEl);

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

  htmlEl.addEventListener('click', eventHandler);
}

