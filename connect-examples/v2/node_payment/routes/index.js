const express = require('express');
const router = express.Router();
const crypto = require('crypto');

const app = express();
const env = app.get('env');
const config = require('.././config.json')[env];

/* GET home page. */
router.get('/', function (req, res) {
  // Set the app and location ids for sqpaymentform.js to use
  res.render('index', {
    'title': 'Make Payment',
    'square_application_id': config.squareApplicationId,
    'square_location_id': config.squareLocationId,
    'env': env,
  });
});

router.post('/process-payment', async (req, res) => {
  const request_params = req.body;

  // length of idempotency_key should be less than 45
  const idempotency_key = crypto.randomBytes(22).toString('hex');

  // Charge the customer's card
  const payments_api = new squareConnect.PaymentsApi();
  const request_body = {
    source_id: request_params.nonce,
    amount_money: {
      amount: 100, // $1.00 charge
      currency: 'USD'
    },
    idempotency_key: idempotency_key
  };

  try {
    const respone = await payments_api.createPayment(request_body);
    const json = JSON.stringify(respone);
    res.render('process-payment', {
      'title': 'Payment Successful',
      'result': json
    });
  } catch (error) {
    res.render('process-payment', {
      'title': 'Payment Failure',
      'result': error.response.text
    });
  }
});

module.exports = router;
