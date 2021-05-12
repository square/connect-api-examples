window.CardPay = async function(fieldEl, buttonEl) {
  const payments = Square.payments(window.applicationId, window.locationId);

  // Create a card payment object and attach to page
  const card = await payments.card({
    style: {
      '.message-text.is-error': {
        color: '#BF0020'
      }
    }
  });
  await card.attach(fieldEl);

  async function eventHandler(event) {
    try {
      const result = await card.tokenize();
      if (result.status === 'OK') {
        // Use global method from sq-payment-flow.js
        window.createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  };

  buttonEl.addEventListener('click', eventHandler);
}