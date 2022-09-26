const { Client } = require('square');

require('dotenv').config()

// Square client config
const config = {
  environment: process.env.NODE_ENV,
  accessToken: process.env.SQUARE_ACCESS_TOKEN,
  userAgentDetail: "sample_app_node_payment"
}

// Configure instance of Square client
const defaultClient = new Client(config)

module.exports = defaultClient
