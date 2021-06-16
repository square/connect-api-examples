/*
Copyright 2021 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

const { v4: uuidv4 } = require("uuid");

const express = require("express");
const logger = require("morgan");
const cookieParser = require("cookie-parser");
const session = require('express-session');

const routes = require("./routes/index");
const app = express();

// Set currency to be used in the app, default to USD.
const { locationsApi } = require("./util/square-client");
app.locals.currency = "USD";
app.locals.maxLimit = 200000;
locationsApi.retrieveLocation(process.env[`SQUARE_LOCATION_ID`]).then(function(response) {
  app.locals.currency = response.result.location.currency;

  // If the currency is one of the following currencies, we have to change our maximum allowed balance.
  switch (app.locals.currency) {
    case "JPY":
      app.locals.maxLimit = 50000;
      break;
    case "GBP", "EUR":
      app.locals.maxLimit = 75000;
      break;
    default:
      app.locals.maxLimit = 200000;
  }
}).catch(function(error) {
  if (error.statusCode === "401") {
    console.error("Configuration has failed. Please verify `.env` file is correct.");
  }
  process.exit(1);
});

// set the view engine to ejs
app.set('view engine', 'ejs');

app.use(logger("dev"));

app.use(express.static(__dirname + '/public'));

app.use(express.json());
app.use(express.urlencoded({
  extended: false
}));

app.use(session({
  genid: function(req) {
    return uuidv4() // use UUIDs for session IDs
  },
	secret: 'secret', // in production, use a unique and random generated string and store it in an environment variable
	resave: true,
	saveUninitialized: true
}));

app.use(function(req, res, next) {
  res.locals.customerGivenName = req.session.customerGivenName;
  res.locals.customerFamilyName = req.session.customerFamilyName;
  next();
});

app.use(cookieParser());
app.use("/", routes);

// catch 404 and forward to error handler
app.use(function (req, res, next) {
  const err = new Error("Not Found");
  err.status = 404;
  next(err);
});

// error handlers
// For simplicity, we print all error information
app.use(function (err, req, res, next) {
  res.status(err.status || 500);
  res.render("pages/error", {
    status: err.status,
    message: err.message,
    // If it is a response error then format the JSON string, if not output the error
    error: err.errors ? JSON.stringify(err.errors, null, 4) : err.stack
  });
});


module.exports = app;
