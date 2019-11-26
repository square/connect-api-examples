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
  },
  masterpass: {
    elementId: 'sq-masterpass'
  },
  // SqPaymentForm callback functions
  callbacks: {
    cardNonceResponseReceived: function(errors, nonce, cardData, billingContact, shippingContact) {
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

  }
);

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
      fontFamily: 'tahoma',
      placeholderFontWeight: 300,
      borderRadius: '10px',
      autoFillColor: '#FFFFFF',     //Card number & exp. date strings
      color: '#FFFFFF',             //CVV & Zip
      placeholderColor: '#A5A5A5',  //card field hints
      backgroundColor: '#1F1F1F',   //Card entry background color
      cardIconColor: '#A5A5A5',    //Card Icon color
      boxShadow: "10px 20px 20px #3d3d5c",
      error: {
        cardIconColor: '#cc0000',
        color: '#cccccc',
        backgroundColor: '#1F1F1F',
        boxShadow: "10px 20px 20px #800000",
        fontWeight: 500,
        fontFamily: 'tahoma' //font of the input field in error
      },
      details: {
        hidden: false,    //Shows the entry field prompt string
        color: '#A5A5A5', //Sets prompt string color
        fontSize: '14px',
        fontWeight: 500,
        fontFamily: 'tahoma',
        error: {
          color: '#ffcccc',
          fontSize: '14px'
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

