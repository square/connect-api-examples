async function SquarePaymentFlow() {

  // Create card payment object and attach to page
  CardPay(document.getElementById('card-container'), document.getElementById('card-button'));

  // Create Apple pay instance
  const ApplePayButton = document.getElementById('apple-pay-button');
  ApplePay(ApplePayButton, () => {
    ApplePayButton.style.display = 'flex';
  });

  // Create Google pay instance
  GooglePay(document.getElementById('google-pay-button'));

  // Create ACH payment
  ACHPay(document.getElementById('ach-button'));

}

window.payments = Square.payments(window.applicationId, window.locationId);

window.createPayment = async function createPayment(token) {
  const dataJsonString = JSON.stringify({
    token
  });

  try {
    const response = await fetch('process-payment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: dataJsonString
    });

    const data = await response.json();
    const messageEl = document.getElementById('message');
    if (data.errors && data.error.length > 0) {
      if (data.errors[0].detail) {
        messageEl.innerHTML = data.errors[0].detail;
      } else {
        messageEl.innerHTML = 'Payment Failed.'
      }
    } else {
      messageEl.innerHTML = 'Payment Successful!';
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

// Hardcoded for testing purpose, only uses for Apple Pay and Google Pay
window.getPaymentRequest = function () {
  return {
    countryCode: window.country,
    currencyCode: window.currency,
    lineItems: [
      { amount: '1.23', label: 'Cat', pending: false },
      { amount: '4.56', label: 'Dog', pending: false },
    ],
    requestBillingContact: false,
    requestShippingContact: true,
    shippingContact: {
      addressLines: ['123 Test St', ''],
      city: 'San Francisco',
      countryCode: 'US',
      email: 'test@test.com',
      familyName: 'Last Name',
      givenName: 'First Name',
      phone: '1111111111',
      postalCode: '94109',
      state: 'CA',
    },
    shippingOptions: [
      { amount: '0.00', id: 'FREE', label: 'Free' },
      { amount: '9.99', id: 'XP', label: 'Express' },
    ],
    total: { amount: '1.00', label: 'Total', pending: false },
  };
};

SquarePaymentFlow();