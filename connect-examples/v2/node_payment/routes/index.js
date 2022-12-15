const express = require('express');
const router = express.Router();
const { v4: uuidv4 } = require("uuid");

const app = express();
const env = app.get('env');
const { paymentsApi, locationsApi } = require('../util/square-client');

/* GET home page. */
router.get('/', async function (req, res) {
  const locationResponse = await locationsApi.retrieveLocation(process.env.SQUARE_LOCATION_ID);
  const currency = locationResponse.result.location.currency;
  const country = locationResponse.result.location.country;
  const idempotencyKey = uuidv4();

  // Set the app and location ids for Payment Web SDK to use
  res.render('index', {
    env,
    title: 'Make Payment',
    squareApplicationId: process.env.SQUARE_APPLICATION_ID,
    squareLocationId: process.env.SQUARE_LOCATION_ID,
    squareAccountCountry: country,
    squareAccountCurrency: currency,
    idempotencyKey
  });
});

router.post('/process-payment', async (req, res) => {
  const token = req.body.token;
  const idempotencyKey = req.body.idempotencyKey;

  // get the currency for the location
  const locationResponse = await locationsApi.retrieveLocation(process.env.SQUARE_LOCATION_ID);
  const currency = locationResponse.result.location.currency;

  // Charge the customer's card
  const requestBody = {
    idempotencyKey,
    sourceId: token,
    amountMoney: {
      amount: 100, // $1.00 charge
      currency
    }
  };

  try {
    const { result: { payment } } = await paymentsApi.createPayment(requestBody);

    const result = JSON.stringify(payment, (key, value) => {
      return typeof value === "bigint" ? parseInt(value) : value;
    }, 4);

    res.json({
      result
    });
  } catch (error) {
    res.json(error.result);
  }
});

module.exports = router;
