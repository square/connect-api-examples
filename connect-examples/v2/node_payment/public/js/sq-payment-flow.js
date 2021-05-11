async function SquarePaymentFlow() {
  
  // Create card payment object and attach to page
  CardPay(document.getElementById('card-container'));

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

window.createPayment = async function createPayment(token) {
  const dataJsonString = JSON.stringify({
    nonce: token
  });

  fetch('process-payment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: dataJsonString
  })
  .then(response => response.json())
  .then(data => {
    document.getElementById('message').innerHTML = data.title;
  })
  .catch((error) => {
    console.error('Error:', error);
  });
}

// Hardcoded for testing purpose, only uses for Apple Pay and Google Pay
window.getPaymentRequest = function() {
  return {
    countryCode: country,
    currencyCode: currency,
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