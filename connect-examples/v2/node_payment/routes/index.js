var express = require('express');
var router = express.Router();

var app = express();
var config = require('.././config.json')[app.get('env')];

var unirest = require('unirest');
var base_url = "https://connect.squareup.com/v2";

// data store for product cost
var product_cost = {"001": 100, "002": 4900, "003": 500000} 

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express Node.js Implementation', 'square_application_id': config.squareApplicationId });
});

router.post('/charges/charge_card', function(req,res,next){
	var location;
	var request_params = req.body;
	unirest.get(base_url + '/locations')
	.headers({
		'Authorization': 'Bearer ' + config.squareAccessToken,
		'Accept': 'application/json'
	})
	.end(function (response) {
	  location = response.body.locations[0];
	  
	  var token = require('crypto').randomBytes(64).toString('hex');
	  
	  //Check if product exists
	  if (!product_cost.hasOwnProperty(request_params.product_id)) {
	  	return res.json({status: 400, errors: [{"detail": "Product Unavailable"}] })
	  }

	  //Make sure amount is a valid integer
	  var amount = product_cost[request_params.product_id]
	  
	  request_body = {
	  	card_nonce: request_params.nonce,
	  	amount_money: {
	  		amount: amount,
	  		currency: 'USD'
	  	},
	  	idempotency_key: token
	  }
	  unirest.post(base_url + '/locations/' + location.id + "/transactions")
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
});

module.exports = router;
