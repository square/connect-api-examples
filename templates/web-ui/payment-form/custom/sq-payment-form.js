
function requestCardNonce(event) {

  // Don't submit the form until SqPaymentForm returns with a nonce
  event.preventDefault();

  // Request a nonce from the SqPaymentForm object
  paymentForm.requestCardNonce();
}
var paymentForm = new SqPaymentForm({
  // Initialize the payment form elements
  applicationId: applicationId,
  locationId: locationId,
  inputClass: 'sq-input',

  // Customize the CSS for SqPaymentForm iframe elements
  inputStyles: [{
    backgroundColor: 'transparent',
    color: '#333333',
    fontFamily: '"Helvetica Neue", "Helvetica", sans-serif',
    fontSize: '16px',
    fontWeight: '400',
    placeholderColor: '#8594A7',
    placeholderFontWeight: '400',
    padding: '16px',
    _webkitFontSmoothing: 'antialiased',
    _mozOsxFontSmoothing: 'grayscale'
  }],

  // Initialize Google Pay button ID
  googlePay: {
    elementId: 'sq-google-pay'
  },

  // Initialize Apple Pay placeholder ID
  applePay: {
    elementId: 'sq-apple-pay'
  },

  // Initialize Masterpass placeholder ID
  masterpass: {
    elementId: 'sq-masterpass'
  },

  // Initialize the credit card placeholders
  cardNumber: {
    elementId: 'sq-card-number',
    placeholder: '•••• •••• •••• ••••'
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
    elementId: 'sq-postal-code'
  },

  // SqPaymentForm callback functions
  callbacks: {

    /*
     * callback function: methodsSupported
     * Triggered when: the page is loaded.
     */
    methodsSupported: function (methods) {
      if (!methods.masterpass && !methods.applePay && !methods.googlePay) {
        var walletBox = document.getElementById('sq-walletbox');
        walletBox.style.display = 'none';
      } else {
        var walletBox = document.getElementById('sq-walletbox');
        walletBox.style.display = 'block';
      }

      // Only show the button if Google Pay is enabled
      if (methods.googlePay === true) {
        var googlePayBtn = document.getElementById('sq-google-pay');
        googlePayBtn.style.display = 'inline-block';
      }

      // Only show the button if Apple Pay for Web is enabled
      if (methods.applePay === true) {
        var applePayBtn = document.getElementById('sq-apple-pay');
        applePayBtn.style.display = 'inline-block';
      }

      // Only show the button if Masterpass is enabled
      if (methods.masterpass === true) {
        var masterpassBtn = document.getElementById('sq-masterpass');
        masterpassBtn.style.display = 'inline-block';
      }
    },

    /*
     * callback function: createPaymentRequest
     * Triggered when: a digital wallet payment button is clicked.
     */
    createPaymentRequest: function () {

      var paymentRequestJson = {
        requestShippingAddress: false,
        requestBillingInfo: true,
        shippingContact: {
          familyName: "CUSTOMER LAST NAME",
          givenName: "CUSTOMER FIRST NAME",
          email: "mycustomer@example.com",
          country: "USA",
          region: "CA",
          city: "San Francisco",
          addressLines: [
            "1455 Market St #600"
          ],
          postalCode: "94103",
          phone:"14255551212"
        },
        currencyCode: "USD",
        countryCode: "US",
        total: {
          label: "MERCHANT NAME",
          amount: "1.00",
          pending: false
        },
        lineItems: [
          {
            label: "Subtotal",
            amount: "1.00",
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
      };

      return paymentRequestJson;
    },

    /*
    * callback function: shippingOptionChanged
    * Triggered when: Apple Pay sheet shipping option changed
    */
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

      /*
      * callback function: shippingContactChanged
      * Triggered when: Apple Pay sheet shipping contact changed
      */
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
    },
    /*
     * callback function: cardNonceResponseReceived
     * Triggered when: SqPaymentForm completes a card nonce request
     */
    cardNonceResponseReceived: function(errors, nonce, cardData, billingContact, shippingContact, shippingOption) {
      if (errors){
        var error_html = "";
        for (var i =0; i < errors.length; i++){
          error_html += "<li> " + errors[i].message + " </li>";
        }
        document.getElementById("error").innerHTML = error_html;
        document.getElementById('sq-creditcard').disabled = false;

        return;
      }else{
        document.getElementById("error").innerHTML = "";
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
    unsupportedBrowserDetected: function() {
      /* PROVIDE FEEDBACK TO SITE VISITORS */
    },

    /*
     * callback function: inputEventReceived
     * Triggered when: visitors interact with SqPaymentForm iframe elements.
     */
    inputEventReceived: function(inputEvent) {
      switch (inputEvent.eventType) {
        case 'focusClassAdded':
          /* HANDLE AS DESIRED */
          break;
        case 'focusClassRemoved':
          /* HANDLE AS DESIRED */
          break;
        case 'errorClassAdded':
          /* HANDLE AS DESIRED */
          break;
        case 'errorClassRemoved':
          /* HANDLE AS DESIRED */
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
    paymentFormLoaded: function() {
      /* HANDLE AS DESIRED */
    }
  }
});
