// Set the application ID
var applicationId = "REPLACE_ME";

// Set the location ID
var locationId = "REPLACE_ME";

/*
 * function: requestCardNonce
 *
 * requestCardNonce is triggered when the "Pay with credit card" button is
 * clicked
 *
 * Modifying this function is not required, but can be customized if you
 * wish to take additional action when the form button is clicked.
 */
function requestCardNonce(event) {

  // Don't submit the form until SqPaymentForm returns with a nonce
  event.preventDefault();

  // Request a nonce from the SqPaymentForm object
  paymentForm.requestCardNonce();
}

// Create and initialize a payment form object
var paymentForm = new SqPaymentForm({

  // Initialize the payment form elements
  applicationId: applicationId,
  locationId: locationId,
  inputClass: 'sq-input',
  autoBuild: false,

  // Customize the CSS for SqPaymentForm iframe elements
  inputStyles: [{
    fontSize: '16px',
    fontFamily: 'Helvetica Neue',
    padding: '16px',
    color: '#373F4A',
    backgroundColor: 'transparent',
    lineHeight: '24px',
    placeholderColor: '#CCC',
    _webkitFontSmoothing: 'antialiased',
    _mozOsxFontSmoothing: 'grayscale'
  }],

  // Initialize Apple Pay placeholder ID
  applePay: {
    elementId: 'sq-apple-pay'
  },

  // Initialize Masterpass placeholder ID
  masterpass: {
    elementId: 'sq-masterpass'
  },

  // Initialize Google Pay placeholder ID
  googlePay: {
    elementId: 'sq-google-pay'
  },

  // Initialize the credit card placeholders
  cardNumber: {
    elementId: 'sq-card-number',
    placeholder: '• • • •  • • • •  • • • •  • • • •'
  },
  cvv: {
    elementId: 'sq-cvv',
    placeholder: 'CVV'
  },
  expirationDate: {
    elementId: 'sq-expiration-date',
    placeholder: 'MM/YY'
  },
  postalCode: {
    elementId: 'sq-postal-code',
    placeholder: '12345'
  },

  // SqPaymentForm callback functions
  callbacks: {

    /*
     * callback function: methodsSupported
     * Triggered when: the page is loaded.
     */
    methodsSupported: function (methods) {

      var walletBox = document.getElementById('sq-walletbox');
      var applePayBtn = document.getElementById('sq-apple-pay');
      var googlePayBtn = document.getElementById('sq-google-pay');
      var masterpassBtn = document.getElementById('sq-masterpass');

      // Only show the button if Apple Pay for Web is enabled
      // Otherwise, display the wallet not enabled message.
      if (methods.applePay === true) {
        walletBox.style.display = 'block';
        applePayBtn.style.display = 'block';
      }
      // Only show the button if Masterpass is enabled
      // Otherwise, display the wallet not enabled message.
      if (methods.masterpass === true) {
        walletBox.style.display = 'block';
        masterpassBtn.style.display = 'block';
      }
      // Only show the button if Google Pay is enabled
      if (methods.googlePay === true) {
        walletBox.style.display = 'block';
        googlePayBtn.style.display = 'inline-block';
      }
    },

    /*
     * callback function: createPaymentRequest
     * Triggered when: a digital wallet payment button is clicked.
     * Note: shippingOptions is ignored by Google Pay
     */
    createPaymentRequest: function () {

      return {
        requestShippingAddress: false,
        requestBillingInfo: true,
        currencyCode: "USD",
        countryCode: "US",
        total: {
          label: "MERCHANT NAME",
          amount: "100",
          pending: false
        },
        lineItems: [
          {
            label: "Subtotal",
            amount: "100",
            pending: false
          }
        ],
        shippingOptions: [
          {
            id: "1",
            label: "SHIPPING LABEL",
            amount: "SHIPPING COST"
          }
        ]
      }
    },


    /*
     * callback function: cardNonceResponseReceived
     * Triggered when: SqPaymentForm completes a card nonce request
     */
    cardNonceResponseReceived: function (errors, nonce, cardData, billingContact, shippingContact, shippingOption) {
      if (errors) {
        // Log errors from nonce generation to the Javascript console
        console.log("Encountered errors:");
        errors.forEach(function (error) {
          console.log('  ' + error.message);
          alert(error.message);
        });

        return;
      }

      // shippingContact provides the final contact information for the payment
      if (shippingContact) {

      }

      // shippingOption privides the shipping options selected in the Apple Pay sheet
      if (shippingOption) {

      }
      // Assign the nonce value to the hidden form field
      document.getElementById('card-nonce').value = nonce;

      // POST the nonce form to the payment processing page
      document.getElementById('nonce-form').submit();

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
        case 'focusClassAdded':
          /* HANDLE AS DESIRED */
          break;
        case 'focusClassRemoved':
          /* HANDLE AS DESIRED */
          break;
        case 'errorClassAdded':
          document.getElementById("error").innerHTML = "Please fix card information errors before continuing.";
          break;
        case 'errorClassRemoved':
          /* HANDLE AS DESIRED */
          document.getElementById("error").style.display = "none";
          break;
        case 'cardBrandChanged':
          /* HANDLE AS DESIRED */
          break;
        case 'postalCodeChanged':
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
      console.log("The form loaded!");
    }
  },
  shippingOptionChanged: function(shippingOption, done) {

    //This example creates a new array of line items that includes only 1 line
    //item. The item for a shipping option.  Production code would get the
    //array of line items from the original PaymentRequest and add/update a line
    //item for the shippingOption argument of this callback.
    const newLineItems = [{
        label: shippingOption.label,
        amount: shippingOption.amount,
        pending: false
      }];
    const newTotal = {
      label: "Total",

      // TODO: total amount to be calc'd based on difference between old and new
      // amount of this shippingOption.amount if shippingOption.amount was
      // updated.
      //   -- OR --
      // Increment total amount if the line item for this shippingOption is new.
      amount: "SOME_AMOUNT + shippingOption.amount",
      pending: false
    };

    done({
      //Note: newLineItems REPLACES the set of the line items in the PaymentRequest
      // newTotal REPLACES the original payment total.
      lineItems: newLineItems,
      total: newTotal
    });
   },
   shippingContactChanged: function (shippingContact, done) {
    var valid = true;
    var errors = {};

    if (!shippingContact.postalCode) {
      errors.postalCode = "postal code is required";
      valid = false;
    }
    if (!shippingContact.addressLines) {
      errors.addressLines = "address lines are required";
      valid = false;
    }

    if (!valid) {
      done({shippingContactErrors: errors});
      return;
    }

    // Shipping address unserviceable.
    if (shippingContact.country != 'US') {
      done({error: 'Shipping to outside of the U.S. is not available.'});
      return;
    }

    // No changes to contact shipping address required
    done();
   }
});
