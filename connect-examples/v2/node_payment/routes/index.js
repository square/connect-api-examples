var express = require('express');
var router = express.Router();

var app = express();
var config = require('.././config.json')[app.get('env')];

/* GET home page. */
router.get('/', function(req, res, next) {
	res.render('index', {
		'title': 'Express Node.js Implementation',
		'square_application_id': config.squareApplicationId,
		'square_location_id': config.locationId,
	});
});

router.post('/charges/charge_card', function(req,res,next){
	var request_params = req.body;

	var token = require('crypto').randomBytes(64).toString('hex');

	// Check if product exists
	if (!product_cost.hasOwnProperty(request_params.product_id)) {
		return res.json({status: 400, errors: [{"detail": "Product Unavailable"}] })
	}

	// Make sure amount is a valid integer
	var amount = product_cost[request_params.product_id]

	// To learn more about splitting transactions with additional recipients,
	// see the Transactions API documentation on our [developer site]
	// (https://docs.connect.squareup.com/payments/transactions/overview#mpt-overview).
	request_body = {
		card_nonce: request_params.nonce,
		amount_money: {
			amount: amount,
			currency: 'USD'
		},
		idempotency_key: token
	}

	locationId = request_params.location_id;

	unirest.post(base_url + '/locations/' + locationId + "/transactions")
	.headers({
		'Authorization': 'Bearer ' + config.squareAccessToken,
		'Accept': 'application/json',
		'Content-Type': 'application/json'
	})
	.send(request_body)
	.end(function(response){
		if (response.body.errors){
			res.json({status: 400, errors: response.body.errors})
		}else{
			res.json({status: 200})
		}
	})

});

module.exports = router;
