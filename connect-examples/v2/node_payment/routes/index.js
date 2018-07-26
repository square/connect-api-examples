var express = require('express');
var router = express.Router();
var util = require('util');

var app = express();
var config = require('.././config.json')[app.get('env')];

/* GET home page. */
router.get('/', function(req, res, next) {
	res.render('index', {
		'title': 'Express Node.js Implementation',
		'square_application_id': config.squareApplicationId,
		'square_location_id': config.squareLocationId
	});
});

router.post('/process-payment', function(req,res,next){
	var request_params = req.body;

	var token = require('crypto').randomBytes(64).toString('hex');


	// To learn more about splitting transactions with additional recipients,
	// see the Transactions API documentation on our [developer site]
	// (https://docs.connect.squareup.com/payments/transactions/overview#mpt-overview).
	var transactions_api = new squareConnect.TransactionsApi();
	var request_body = {
		card_nonce: request_params.nonce,
		amount_money: {
			amount: 100,
			currency: 'USD'
		},
		idempotency_key: token
	};
	transactions_api.charge(config.squareLocationId, request_body).then(function(data) {
		console.log(util.inspect(data));
		res.render('process-payment', {
			'result': "Payment Successful (see console for transaction output)"
		});
	}, function(error) {
		console.log(util.inspect(error));
		res.render('process-payment', {
			'result': "Payment Failed (see console for error output)"
		});
	});

});

module.exports = router;
