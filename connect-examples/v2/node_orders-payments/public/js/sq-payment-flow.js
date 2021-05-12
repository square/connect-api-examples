async function SquarePaymentFlow() {
  // Create card payment object and attach to page
  console.log(document.getElementById('card-container'));
  window.CardPay(document.getElementById('card-container'));
}

window.createPayment = async function createPayment(token) {
  const dataJsonString = JSON.stringify({
    nonce: token,
    locationId: document.getElementById('locationId').value,
    orderId: document.getElementById('orderId').value,
    idempotencyKey: document.getElementById('idempotencyKey').value
  });

  fetch('payment', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: dataJsonString
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    if (data.errors) {
      document.getElementById('message').innerHTML = data.errors[0].detail;
    } else {
      window.location.href = `/order-confirmation?orderId=${document.getElementById('orderId').value}&locationId=${document.getElementById('locationId').value}`;
    }
  })
  .catch((error) => {
    console.error('Error:', error);
  });
}

SquarePaymentFlow();