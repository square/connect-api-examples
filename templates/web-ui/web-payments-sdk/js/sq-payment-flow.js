async function SquarePaymentFlow() {

  // Create card payment object and attach to page
  CardPay(document.getElementById('card-container'), document.getElementById('card-button'));

  // Create Apple pay instance
  ApplePay(document.getElementById('apple-pay-button'));

  // Create Google pay instance
  GooglePay(document.getElementById('google-pay-button'));

  // Create ACH payment
  ACHPay(document.getElementById('ach-button'));
}

window.payments = Square.payments(window.applicationId, window.locationId);

window.paymentFlowMessageEl = document.getElementById('payment-flow-message');

window.showSuccess = function(message) {
  window.paymentFlowMessageEl.classList.add('success');
  window.paymentFlowMessageEl.classList.remove('error');
  window.paymentFlowMessageEl.innerText = message;
}

window.showError = function(message) {
  window.paymentFlowMessageEl.classList.add('error');
  window.paymentFlowMessageEl.classList.remove('success');
  window.paymentFlowMessageEl.innerText = message;
}

window.createPayment = async function(token) {
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

    if (data.errors && data.errors.length > 0) {
      if (data.errors[0].detail) {
        window.showError(data.errors[0].detail);
      } else {
        window.showError('Payment Failed.');
      }
    } else {
      window.showSuccess('Payment Successful!');
    }
  } catch (error) {
    console.error('Error:', error);
  }
}

// Hardcoded for testing purpose, only used for Apple Pay and Google Pay
window.getPaymentRequest = function() {
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
