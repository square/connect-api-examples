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
const availabilityRoute = require("./availability");
const bookingRoute = require("./booking");
const contactRoute = require("./contact");
const servicesRoute = require("./services");
const staffRoute = require("./staff");

router.use("/availability", availabilityRoute);
router.use("/contact", contactRoute);
router.use("/services", servicesRoute);
router.use("/staff", staffRoute);
router.use("/booking", bookingRoute);

/**
 * GET /
 *
 * Entry point for the app. Will redirect to the /services endpoint.
 */
router.get("/", async (req, res, next) => {
  res.redirect("/services");
});

module.exports = router;
