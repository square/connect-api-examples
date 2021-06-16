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
  customersApi
} = require("../util/square-client");

const dashboardRoute = require("./dashboard");
const giftCardRoute = require("./gift-card");
const seedRoute = require("./seed");

// Define the routes
router.use("/dashboard", dashboardRoute);
router.use("/gift-card", giftCardRoute);
router.use("/seed", seedRoute);

router.get("/", async (req, res, next) => {
  if (req.session.loggedIn) {
    res.redirect('/dashboard');
  } else {
    res.redirect('/login');
  }
});

/**
 *  Renders a fake login page that shows a list of existing
 *  customers to choose from.
 */
router.get("/login", async (req, res, next) => {
  const customerCreated = req.query.customerCreated;
  const customerCreatedGivenName = req.query.givenName;
  const customerCreatedFamilyName = req.query.familyName;
  const reset = req.query.reset;
  try {
    let { result: { customers } } = await customersApi.listCustomers();
    if (!customers) {
      customers = [];
    }

    const customerIds = customers.map(customer => customer.id);

    // Check if there are any missing customers that need to be added to the list of available customers.
    if (req.session.missingCustomers) {
      req.session.missingCustomers = req.session.missingCustomers
        .filter(missingCustomer => !customerIds.includes(missingCustomer.id));

      // Those who aren't available yet, should be appended to our retrieved list of customers.
      customers = customers.concat(req.session.missingCustomers);
    }

    res.render("pages/login",
      { customers, customerCreated, customerCreatedGivenName, customerCreatedFamilyName, reset });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

/**
 * Mimics the login action, no actual authentication is implemented.
 * ** Do not copy this code in production. **
*/
router.post("/login", async (req, res, next) => {
  if (req.body.customer) {
    req.session.loggedIn = true;
    req.session.customerId = req.body.customer;

    try {
      const { result: { customer } } = await customersApi.retrieveCustomer(req.session.customerId);
      req.session.customerGivenName = customer.givenName;
      req.session.customerFamilyName = customer.familyName;
    } catch (error) {
      console.error(error);
      next(error);
    }
  }

  res.redirect("/");
});

router.get("/logout", async (req, res, next) => {
  req.session.customerId = null;
  req.session.customerGivenName = null;
  req.session.customerFamilyName = null;
  req.session.loggedIn = false;

  res.redirect("/");
});

module.exports = router;
