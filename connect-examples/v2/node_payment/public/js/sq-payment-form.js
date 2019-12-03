
function onGetCardNonce(event) {

  // Don't submit the form until SqPaymentForm returns with a nonce
  event.preventDefault();

  // Request a nonce from the SqPaymentForm object
  paymentForm.requestCardNonce();
}

var paymentFormWallets = new SqPaymentForm( {
  // Initialize the payment form elements
  applicationId: applicationId,
  locationId: locationId,
  autoBuild: true,
  googlePay: {
    elementId: 'sq-google-pay'
  },
  applePay: {
    elementId: 'sq-apple-pay'
  },qq
  masterpass: {
    elementId: 'sq-masterpass'
  },
  // SqPaymentForm callback functions
  callbacks: {
    cardNonceResponseReceived: function(errors, nonce, cardData, billingContact, shippingContact) {
      const errorList = document.getElementById("errors");
      if (errors) {
        let error_html = "";
        for (var i = 0; i < errors.length; i++) {
          error_html += "<li> " + errors[i].message + " </li>";
        }
        errorList.innerHTML = error_html;
        errorList.style.display = 'inline-block';
        return;
      }
      errorList.style.display = 'none';
      errorList.innerHTML = "";

      // Assign the nonce value to the hidden form field
      document.getElementById('card-nonce').value = nonce;

      // POST the nonce form to the payment processing page
      document.getElementById('nonce-form').submit();
    },
    /*
     * callback function: methodsSupported
     * Triggered when: the page is loaded.
     */
    methodsSupported: function (methods) {

      console.log(paymentFormWallets.options)

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
        //Set button background image provided by MasterPass
        masterpassBtn.style.backgroundImage = 'url('
          + paymentForm.masterpassImageUrl()
          + ')';
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
        ]
      };
      return paymentRequestJson;
    },

    /*
     * callback function: validateShippingContact
     * Triggered when: a shipping address is selected/changed in a digital
     *                 wallet UI that supports address selection.
     */
    validateShippingContact: function (contact) {
      var validationErrorObj ;
      /* ADD CODE TO SET validationErrorObj IF ERRORS ARE FOUND */
      return validationErrorObj ;
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

// Initializes the SqPaymentForm object by
// initializing various configuration fields and providing implementation for callback functions.
var paymentForm = new SqPaymentForm({
  // Initialize the payment form elements
  applicationId: applicationId,
  locationId: locationId,
  autoBuild: true,
  // Initialize the credit card placeholders
  card: {
    elementId: 'sq-card',
    inputStyle: {
      fontSize: '14px',
      fontWeight: 500,
      fontFamily: 'Helvetica',
      placeholderFontWeight: 300,
      borderRadius: '8px',
      autoFillColor: '#000000',     //Card number & exp. date strings
      color: '#000000',             //CVV & Zip
      placeholderColor: '#B7B7B8',  //card field hints
      backgroundColor: '#FFFFFF',   //Card entry background color
      cardIconColor: 'linear-gradient(180deg, #29ACFF 0%, #0097F6 233.38%)',    //Card Icon color
      boxShadow: "0px 2px 6px rgba(0,0,0,.02), 0px 4px 8px rgba(0,0,0, 0.04), 0px 8px 30px rgba(0,0,0, 0.04), 0px 1px 2px rgba(0,0,0, 0.08)",
      error: {
        cardIconColor: 'linear-gradient(180deg, #29ACFF 0%, #0097F6 233.38%)',
        color: '#FF3E39',
        backgroundColor: '#FFFFFF',
        boxShadow: "0px 2px 6px rgba(228,99,90,.02), 0px 4px 8px rgba(228,99,90, 0.04), 0px 8px 30px rgba(228,99,90, 0.04), 0px 1px 2px rgba(228,99,90, 0.08)",
        fontWeight: 500,
        fontFamily: 'Helvetica' //font of the input field in error
      },
      details: {
        hidden: false,    //Shows the entry field prompt string
        color: '#B7B7B8', //Sets prompt string color
        fontSize: '12px',
        fontWeight: 500,
        fontFamily: 'Helvetica',
        error: {
          color: '#FF3E39',
          fontSize: '12px'
        },
      }
    }
  },

  // SqPaymentForm callback functions
  callbacks: {
    /*
     * callback function: cardNonceResponseReceived
     * Triggered when: SqPaymentForm completes a card nonce request
     */
    cardNonceResponseReceived: function(errors, nonce, cardData, billingContact, shippingContact) {
      const errorList = document.getElementById("errors");
      if (errors) {
        let error_html = "";
        for (var i = 0; i < errors.length; i++) {
          error_html += "<li> " + errors[i].message + " </li>";
        }
        errorList.innerHTML = error_html;
        errorList.style.display = 'inline-block';
        return;
      }
      errorList.style.display = 'none';
      errorList.innerHTML = "";
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
     * callback function: paymentFormLoaded
     * Triggered when: SqPaymentForm is fully loaded
     */
    paymentFormLoaded: function() {
      /* HANDLE AS DESIRED */
    }
  }
});

