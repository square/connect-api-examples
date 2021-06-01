const express = require("express");
const app = express();
const {
  giftCardsApi,
  giftCardActivitiesApi,
  customersApi,
  ordersApi,
  paymentsApi,
  locationsApi
} = require("../util/square-client");

function setCurrency() {
  locationsApi.retrieveLocation("main").then( function (result) {
    app.locals.currency = result.location.currency;
  });
}

module.exports = {
  setCurrency
}