async function CardPay(formSelector, buttonEl) {
  const payments = Square.payments(applicationId, locationId);

  // Create a card payment object and attach to page
  const card = await payments.card({
    style: {
      '.input-container.is-focus': {
        borderColor: '#006AFF',
      },
      '.message-text.is-error': {
        color: '#BF0020'
      }
    }
  });
  await card.attach(formSelector);

  async function eventHandler(event) {
    event.preventDefault();
    try {
      const result = await card.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);
        createPayment(result.token);
      }
    } catch (e) {
      console.error(e);
    }
  };
  const cardButton = buttonEl;

  cardButton.addEventListener('click', eventHandler);
}