const express = require('express');
const router = express.Router();
const crypto = require('crypto');

const app = express();
const env = app.get('env');
const { paymentsApi, locationsApi } = require('../util/square-client');

/* GET home page. */
router.get('/', function (req, res) {
  // Set the app and location ids for sqpaymentform.js to use
  res.render('index', {
    env,
    title: 'Make Payment',
    squareApplicationId: process.env.SQUARE_APPLICATION_ID,
    squareLocationId: process.env.SQUARE_LOCATION_ID
  });
});

router.post('/process-payment', async (req, res) => {
  const { nonce } = req.body;

  // length of idempotency_key should be less than 45
  const idempotencyKey = crypto.randomBytes(22).toString('hex');

  // get the currency for the location
  const locationResponse = await locationsApi.retrieveLocation(process.env.SQUARE_LOCATION_ID);
  const currency = locationResponse.result.location.currency;

  // Charge the customer's card
  const requestBody = {
    idempotencyKey,
    sourceId: nonce,
    amountMoney: {
      amount: 100, // $1.00 charge
      currency: currency
    }
  };

  try {
    const { result: { payment } } = await paymentsApi.createPayment(requestBody);
    const result = JSON.stringify(payment, null, 4);

    res.render('process-payment', {
      result,
      'title': 'Payment Successful'
    });
  } catch (error) {
    let result = JSON.stringify(error, null, 4);
    if (error.errors) {
      result = JSON.stringify(error.errors, null, 4);
    }
    res.render('process-payment', {
      result,
      'title': 'Payment Failure'
    });
  }
});

module.exports = router;
