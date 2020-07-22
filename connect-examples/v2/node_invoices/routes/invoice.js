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
const {
  randomBytes
} = require("crypto");
const {
  orderApi,
  invoiceApi,
  customerApi,
} = require("../util/square-connect-client");

const router = express.Router();


/**
 * Matches: GET /invoice/view/:location_id/:customer_id/:invoice_id
 *
 * Description:
 *  Renders the invoice detail view page that with buttons
 *  that can update the status of the invoice.
 *
 * Query Parameters:
 *  location_id: Id of the location that the invoice belongs to
 *  customer_id: Id of the selected customer
 *  invoice_id: Id of the selected invoice
 */
router.get("/view/:location_id/:customer_id/:invoice_id", async (req, res, next) => {
  const {
    location_id,
    customer_id,
    invoice_id,
  } = req.params;
  try {
    // Get the invoice by invoice id
    const { invoice } = await invoiceApi.getInvoice(invoice_id);

    // Render the invoice detail view page
    res.render("invoice", {
      idempotency_key: randomBytes(45).toString("hex"),
      location_id,
      customer_id,
      invoice,
    });
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /invoice/create
 *
 * Description:
 *  Take the order item information and create an invoice.
 *  In this example, the invoice is created and scheduled to be sent
 *  at 10 minutes after the creation and payment due date is 7 days
 *  after the creation date.
 *
 *  The invoice is created to charge customer's card on file by default
 *  if there is an card on file. Otherwise, the invoice will be sent and
 *  paid by customer through the invoice's public url.
 *
 * Request Body:
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the invoice belongs to
 *  idempotency_key: Unique identifier for request from client
 *  price_amount: The amount of price for the order item
 *  name: The name of the order item
 */
router.post("/create", async (req, res, next) => {
  const {
    customer_id,
    location_id,
    idempotency_key,
    price_amount,
    name,
  } = req.body;
  try {
    const { customer } = await customerApi.retrieveCustomer(customer_id);

    // Create an order to be attached to invoice
    const { order } = await orderApi.createOrder(location_id, {
      idempotency_key, // Unique identifier for request
      order: {
        customer_id,
        line_items: [{
          name,
          quantity: "1",
          base_price_money: {
            amount: parseInt(price_amount),
            currency: "USD",
          }
        }]
      }
    });

    // We set two important time below, scheduled_at and due_date.
    // scheduled_at is when the invoice will be delivered to the buyer
    // and due_date is when the invoice will be charged.
    // If scheduled_at is before the due date, it will send an email with an explanation that
    // the card on file will be charged on the due date
    // if the scheduled_at is the same date as the due date (in the location timezone)
    // it will charge at the scheduled_at time and send a receipt after, instead of sending the upcoming charge notification.
    // scheduled_at should be never after due_date.

    // Set the due date to 7 days from today
    const due_date = new Date();
    due_date.setDate(due_date.getDate() + 7);
    const due_date_string = due_date.toISOString().split("T")[0];
    // Set the scheduled_at to next 10 minutes
    const scheduled_at = new Date(Date.now() + 10 * 60 * 1000);
    const scheduled_at_string = scheduled_at.toISOString();

    // Set the payment request based on the customer's card on file status
    let payment_request = null;
    if (customer.cards && customer.cards.length > 0) {
      // the current customer has a card on file
      // creating invoice with the payment request method CHARGE_CARD_ON_FILE
      // the invoice will be charged with the card on file on the due date
      payment_request = {
        request_type: "BALANCE",
        request_method: "CHARGE_CARD_ON_FILE",
        due_date: due_date_string,
        card_id: customer.cards[0].id // Take the first card
      };
    } else {
      // the current customer doesn't have a card on file
      // creating invoice with the payment request method EMAIL and set a reminder
      // the invoice will be sent and paid by customer
      payment_request = {
        request_type: "BALANCE",
        request_method: "EMAIL",
        due_date: due_date_string,
        reminders: [
          {
            message: "Your invoice is due tomorrow",
            relative_scheduled_days: -1
          }
        ]
      };
    }

    const requestBody = {
      idempotency_key,
      invoice: {
        order_id: order.id,
        location_id: location_id,
        title: name,
        scheduled_at: scheduled_at_string,
        primary_recipient: {
          customer_id,
        },
        payment_requests: [
          payment_request
        ]
      }
    };
    const result = await invoiceApi.createInvoice(requestBody);

    res.redirect(`view/${location_id}/${customer_id}/${result.invoice.id}`);
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /invoice/publish
 *
 * Description:
 *  Publish the invoice.
 *
 * Request Body:
 *  idempotency_key: Unique identifier for request from client
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the invoice belongs to
 *  invoice_id: Id of the invoice
 *  invoice_version: The version of the invoice
 */
router.post("/publish", async (req, res, next) => {
  const {
    idempotency_key,
    location_id,
    customer_id,
    invoice_id,
    invoice_version,
  } = req.body;
  try {
    // publish invoice
    const result = await invoiceApi.publishInvoice(invoice_id, {
      version: parseInt(invoice_version),
      idempotency_key,
    });

    // redirect to the invoice detail view page
    res.redirect(`view/${location_id}/${customer_id}/${result.invoice.id}`);
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /invoice/cancel
 *
 * Description:
 *  Cancel the invoice.
 *
 * Request Body:
 *  customer_id: Id of the selected customer
 *  location_id: Id of the location that the invoice belongs to
 *  invoice_id: Id of the invoice
 *  invoice_version: The version of the invoice
 */
router.post("/cancel", async (req, res, next) => {
  const {
    location_id,
    customer_id,
    invoice_id,
    invoice_version,
  } = req.body;
  try {
    // cancel invoice
    await invoiceApi.cancelInvoice(invoice_id, {
      version: parseInt(invoice_version),
    });

    // redirect to invoice detail view page
    res.redirect(`view/${location_id}/${customer_id}/${invoice_id}`);
  } catch (error) {
    next(error);
  }
});


/**
 * Matches: POST /invoice/delete
 *
 * Description:
 *  Delete the invoice.
 *
 * Request Body:
 *  location_id: Id of the location that the invoice belongs to
 *  customer_id: Id of the selected customer
 *  invoice_id: Id of the invoice
 *  invoice_version: The version of the invoice
 */
router.post("/delete", async (req, res, next) => {
  const {
    location_id,
    customer_id,
    invoice_id,
    invoice_version,
  } = req.body;
  try {
    // delete the invoice
    await invoiceApi.deleteInvoice(invoice_id, {
      version: parseInt(invoice_version),
    });

    // invoice doesn't exist anymore, return to the invoice management page after delete the invoice
    res.redirect(`/management/${location_id}/${customer_id}`);
  } catch (error) {
    next(error);
  }
});

module.exports = router;
