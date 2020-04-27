/*
Copyright 2019 Square Inc.

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

/* eslint no-undef: 0 */

/**
 * Define callback function for "sq-button"
 * @param {*} event
 */
function onGetCardNonce(event) {
  // Don't submit the form until SqPaymentForm returns with a nonce
  event.preventDefault();

  // Request a nonce from the SqPaymentForm object
  paymentForm.requestCardNonce();
}

/**
 * Initializes the SqPaymentForm object by
 * initializing various configuration fields and providing implementation for callback functions.
 *
 * Learn more about the SqPaymentForm here: https://developer.squareup.com/docs/payment-form/payment-form-walkthrough
 */
const paymentForm = new SqPaymentForm({
  // Initialize the payment form elements
  applicationId: applicationId,
  locationId: locationId,
  inputClass: "sq-input",
  autoBuild: false,

  // Customize the CSS for SqPaymentForm iframe elements
  inputStyles: [
    {
      backgroundColor: "transparent",
      color: "#333333",
      fontFamily: "\"Helvetica Neue\", \"Helvetica\", sans-serif",
      fontSize: "16px",
      fontWeight: "400",
      placeholderColor: "#8594A7",
      placeholderFontWeight: "400",
      padding: "16px",
      _webkitFontSmoothing: "antialiased",
      _mozOsxFontSmoothing: "grayscale",
    },
  ],

  // Initialize the credit card placeholders
  cardNumber: {
    elementId: "sq-card-number",
    placeholder: "Card Number",
  },
  cvv: {
    elementId: "sq-cvv",
    placeholder: "CVV",
  },
  expirationDate: {
    elementId: "sq-expiration-date",
    placeholder: "MM/YY",
  },
  postalCode: {
    elementId: "sq-postal-code",
    placeholder: "ZIP",
  },

  // SqPaymentForm callback functions
  callbacks: {
    /*
     * callback function: methodsSupported
     * Triggered when: the page is loaded.
     */
    methodsSupported: function (methods) {},

    /*
     * callback function: createPaymentRequest
     * Triggered when: a digital wallet payment button is clicked.
     */
    createPaymentRequest: function () {},

    /*
     * callback function: validateShippingContact
     * Triggered when: a shipping address is selected/changed in a digital
     *                 wallet UI that supports address selection.
     */
    validateShippingContact: function (contact) {
      let validationErrorObj;
      /* ADD CODE TO SET validationErrorObj IF ERRORS ARE FOUND */
      return validationErrorObj;
    },

    /*
     * callback function: cardNonceResponseReceived
     * Triggered when: SqPaymentForm completes a card nonce request
     */
    cardNonceResponseReceived: function (
      errors,
      nonce,
      cardData,
      billingContact,
      shippingContact
    ) {
      if (errors) {
        let error_html = "";
        for (let i = 0; i < errors.length; i++) {
          error_html += "<li> " + errors[i].message + " </li>";
        }
        document.getElementById("error").innerHTML = error_html;
        document.getElementById("sq-creditcard").disabled = false;

        return;
      } else {
        document.getElementById("error").innerHTML = "";
      }

      // Assign the nonce value to the hidden form field
      document.getElementById("card-nonce").value = nonce;

      // POST the nonce form to the payment processing page
      document.getElementById("payment-form").submit();
    },

    /*
     * callback function: unsupportedBrowserDetected
     * Triggered when: the page loads and an unsupported browser is detected
     */
    unsupportedBrowserDetected: function () {
      /* PROVIDE FEEDBACK TO SITE VISITORS */
    },

    /*
     * callback function: inputEventReceived
     * Triggered when: visitors interact with SqPaymentForm iframe elements.
     */
    inputEventReceived: function (inputEvent) {
      switch (inputEvent.eventType) {
      case "focusClassAdded":
        /* HANDLE AS DESIRED */
        break;
      case "focusClassRemoved":
        /* HANDLE AS DESIRED */
        break;
      case "errorClassAdded":
        /* HANDLE AS DESIRED */
        break;
      case "errorClassRemoved":
        /* HANDLE AS DESIRED */
        break;
      case "cardBrandChanged":
        /* HANDLE AS DESIRED */
        break;
      case "postalCodeChanged":
        /* HANDLE AS DESIRED */
        break;
      }
    },

    /*
     * callback function: paymentFormLoaded
     * Triggered when: SqPaymentForm is fully loaded
     */
    paymentFormLoaded: function () {
      /* HANDLE AS DESIRED */
    },
  },
});

window.onload = function () {
  // build the Square Payment Form only when dom is loaded.
  paymentForm.build();
};
