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

const express = require("express");
const router = express.Router();
const {
  giftCardsApi,
  giftCardActivitiesApi,
  customersApi,
  ordersApi,
  paymentsApi,
  locationsApi
} = require("../util/square-client");

const dashboardRoute = require("./dashboard");
const giftCardRoute = require("./gift-card");

// Define the routes
router.use("/dashboard", dashboardRoute);
router.use("/gift-card", giftCardRoute);

router.get("/", async (req, res, next) => {
  if (req.session.loggedIn) {
    res.redirect('/dashboard');
  } else {
    res.redirect('/login');
  }
});

// Renders a fake login page that is just a list
// of existing customers to choose from.
router.get("/login", async (req, res, next) => {
  let customers;
  try {
    const response = await customersApi.listCustomers();
    customers = response.result.customers;

    res.render("pages/login", {customers})
  } catch(error) {
    console.log(error);
    next(error);
  }
});

// Mimics the login action, no actual authentication.
// In production, implement real login system.
router.post("/login", async (req, res, next) => {
  if (req.body.customer) {
    req.session.loggedIn = true;
    req.session.customerId = req.body.customer;
  }
  res.redirect("/");
})

router.get("/logout", async (req, res, next) => {
  req.session.destroy();
  res.redirect("/");
});

module.exports = router;
