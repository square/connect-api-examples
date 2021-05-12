async function SquarePaymentFlow() {
  // Create card payment object and attach to page
  window.CardPay(document.getElementById('card-container'));
}

window.createPayment = async function createPayment(token) {
  const dataJsonString = JSON.stringify({
    nonce: token,
    locationId: document.getElementById('locationId').value,
    orderId: document.getElementById('orderId').value,
    idempotencyKey: document.getElementById('idempotencyKey').value
  });

  try {
    const response = await fetch('payment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: dataJsonString
    });
    const data = await response.json();
    console.log(data);
    if (data.errors && data.errors.length > 0) {
      if (data.errors[0].detail) {
        document.getElementById('message').innerHTML = data.errors[0].detail;
      } else {
        document.getElementById('message').innerHTML = 'Payment failed.'
      }
    } else {
      window.location.href = `/order-confirmation?orderId=${document.getElementById('orderId').value}&locationId=${document.getElementById('locationId').value}`;
    }
  } catch(error) {
    console.error('Error:', error);
  };
}

SquarePaymentFlow();