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
  giftCardsApi
} = require("../util/square-client");
const { checkLoginStatus } = require("../util/middleware");

/**
 * GET /dashboard
 *
 * Lists all gift cards for a user.
 */
router.get("/", checkLoginStatus, async (req, res, next) => {
  // display a list of gift cards linked to the customer's account
  const deletion = req.query.deletion;
  try {
    let { result: { giftCards } } = await giftCardsApi.listGiftCards(
      undefined,
      undefined,
      undefined,
      undefined,
      req.session.customerId);
    if (!giftCards) {
      giftCards = [];
    }

    res.render("pages/dashboard", { giftCards, deletion });
  } catch (error) {
    console.error(error);
    next(error);
  }
});

module.exports = router;
