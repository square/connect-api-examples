async function GiftCardPay(formSelector, buttonEl) {
  const payments = await Square.payments(applicationId, locationId);
  const giftCard = await payments.giftCard();
  await giftCard.attach(formSelector);
  const giftCardPayButton = buttonEl;

  async function eventHandler(event) {
    event.preventDefault();

    try {
      const result = await giftCard.tokenize();
      if (result.status === 'OK') {
        console.log(`Payment token is ${result.token}`);

        // Submit the payment form with the nonce
        document.getElementById('card-nonce').value = result.token;
        document.getElementById('fast-checkout').submit();
      }
    } catch (e) {
      console.error(e);
    }
  };

  giftCardPayButton.addEventListener('click', eventHandler);

}