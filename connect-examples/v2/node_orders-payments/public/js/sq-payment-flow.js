async function SquarePaymentFlow() {
  // Create card payment object and attach to page
  window.CardPay(document.getElementById('card-container'), document.getElementById('sq-creditcard'));
}

window.createPayment = async function createPayment(token) {
  const locationId = document.getElementById('locationId').value;
  const orderId = document.getElementById('orderId').value;
  const idempotencyKey = document.getElementById('idempotencyKey').value;

  const dataJsonString = JSON.stringify({
    token,
    locationId,
    orderId,
    idempotencyKey,
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
    if (data.errors && data.errors.length > 0) {
      if (data.errors[0].detail) {
        document.getElementById('message').innerHTML = data.errors[0].detail;
      } else {
        document.getElementById('message').innerHTML = 'Payment failed.'
      }
    } else {
      window.location.href = `/order-confirmation?orderId=${orderId}&locationId=${locationId}`;
    }
  } catch (error) {
    console.error('Error:', error);
  };
}

SquarePaymentFlow();