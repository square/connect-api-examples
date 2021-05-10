async function SquarePaymentForm() {
  
  // Create card payment object and attach to page
  CardPay('#card-container', document.getElementById('card-button'));

  // Create Apple pay instance
  const ApplePayButton = document.querySelector('#apple-pay-button');
  ApplePay(ApplePayButton, () => {
    ApplePayButton.style.display = 'flex';
  });

  // Create Google pay instance
  GooglePay(document.querySelector('#google-pay-button'));

  // Create ACH payment
  ACHPay(document.querySelector('#ach-button'));

  // Create gift card object and attach to page
  GiftCardPay('#gift-card-container', document.getElementById('gift-card-button'));

}

// Hardcoded for testing purpose, only uses for Apple Pay and Google Pay
function getPaymentRequest() {
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

SquarePaymentForm();