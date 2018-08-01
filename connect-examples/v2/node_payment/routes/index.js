var express = require('express');
var router = express.Router();
var util = require('util');

var app = express();
var config = require('.././config.json')[app.get('env')];

/* GET home page. */
router.get('/', function(req, res, next) {
	// Set the app and location ids for sqpaymentform.js to use
	res.render('index', {
		'title': 'Make Payment',
		'square_application_id': config.squareApplicationId,
		'square_location_id': config.squareLocationId
	});
});

router.post('/process-payment', function(req,res,next){
	var request_params = req.body;

	var idempotency_key = require('crypto').randomBytes(64).toString('hex');

	// Charge the customer's card
	var transactions_api = new squareConnect.TransactionsApi();
	var request_body = {
		card_nonce: request_params.nonce,
		amount_money: {
			amount: 100, // $1.00 charge
			currency: 'USD'
		},
		idempotency_key: idempotency_key
	};
	transactions_api.charge(config.squareLocationId, request_body).then(function(data) {
		console.log(util.inspect(data, false, null));
		res.render('process-payment', {
			'title': 'Payment Successful',
			'result': "Payment Successful (see console for transaction output)"
		});
	}, function(error) {
		console.log(util.inspect(error.status, false, null));
		res.render('process-payment', {
			'title': 'Payment Failure',
			'result': "Payment Failed (see console for error output)"
		});
	});

});

module.exports = router;
