/*
Copyright 2020 Square Inc.

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
const crypto = require("crypto");
const {
  customersApi,
  invoicesApi,
} = require("../util/square-client");

const router = express.Router();

/**
 * Matches: GET /management/:locationId/:customerId
 *
 * Description:
 *  Renders the invoice management page that:
 *  * display a list of items that the customer can purchase and receive an invoice.
 *  * display a list of all invoices for the selected customer.
 *
 * Query Parameters:
 *  customerId: Id of the selected customer
 *  locationId: Id of the location that the invoices belongs to
 */
router.get("/:locationId/:customerId", async (req, res, next) => {
  // Post request body contains id of item that is going to be purchased
  const {
    customerId,
    locationId,
  } = req.params;
  try {
    const { result : { customer } } = await customersApi.retrieveCustomer(customerId);

    // Initialize a list of services that can create invoice for.
    // We hard code these items for simplicity of the example, you can use
    // Catalog API to retrieve the catalog items from your Square account.
    // For more information, please check:
    // https://developer.squareup.com/docs/catalog-api/what-it-does
    const serviceItems = [
      {
        name: "Roof Inspection",
        priceAmount: 7500,
      },
      {
        name: "Roof Cleanning (2000~2500 sqft)",
        priceAmount: 37500,
      },
      {
        name: "Gutter Cleaning",
        priceAmount: 12000,
      }
    ];

    // Get all the invoices for this customer.
    // The API support pagination, for simplicity, we retrieve all invoices.
    const { result: { invoices } } = await invoicesApi.searchInvoices({
      query: {
        filter: {
          locationIds: [locationId],
          customerIds: [customerId],
        },
        sort: {
          field: "INVOICE_SORT_DATE",
        }
      }
    });

    // Render the invoice management page
    res.render("management", {
      locationId,
      serviceItems,
      customer,
      invoices: invoices || [],
      idempotencyKey: crypto.randomUUID(),
    });
  } catch (error) {
    next(error);
  }
});

module.exports = router;
