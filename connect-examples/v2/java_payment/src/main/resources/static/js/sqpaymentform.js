// var applicationId = '[[${appId}]]'; // <-- This is set to a real value when it's rendered by the template in Main.java
// var locationId = '[[${locationId}]]'; // <-- This is set to a real value when it's rendered by the template in Main.java

// Make sure the application ID is set before continuing.
if (applicationId == '' || locationId == '') {
    alert('You need to provide a value for applicationId and locationId.');
}

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
    // Customize the CSS for SqPaymentForm iframe elements
    inputStyles: [{
        fontSize: '18px',
        fontFamily: 'Helvetica Neue',
        padding: '15px',
        color: '#373F4A',
        lineHeight: '24px',
        placeholderColor: '#BDBFBF',
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

            var applePayBtn = document.getElementById('sq-apple-pay');
            var applePayLabel = document.getElementById('sq-apple-pay-label');
            var masterpassBtn = document.getElementById('sq-masterpass');
            var masterpassLabel = document.getElementById('sq-masterpass-label');

            // Only show the button if Apple Pay for Web is enabled
            // Otherwise, display the wallet not enabled message.
            if (methods.applePay === true) {
                applePayBtn.style.display = 'inline-block';
                applePayLabel.style.display = 'none';
            }

            // Only show the button if Masterpass is enabled
            // Otherwise, display the wallet not enabled message.
            if (methods.masterpass === true) {
                masterpassBtn.style.display = 'block';
                masterpassLabel.style.display = 'none';
            }
        },

        /*
         * callback function: createPaymentRequest
         * Triggered when: a digital wallet payment button is clicked.
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
                ]
            }
        },

        /*
         * callback function: validateShippingContact
         * Triggered when: a shipping address is selected/changed in a digital
         *                 wallet UI that supports address selection.
         */
        validateShippingContact: function (contact) {

            var validationErrorObj;
            /* ADD CODE TO SET validationErrorObj IF ERRORS ARE FOUND */
            return validationErrorObj;
        },

        /*
         * callback function: cardNonceResponseReceived
         * Triggered when: SqPaymentForm completes a card nonce request
         */
        cardNonceResponseReceived: function (errors, nonce, cardData) {
            if (errors) {
                // Log errors from nonce generation to the Javascript console
                console.log("Encountered errors:");
                errors.forEach(function (error) {
                    console.log('  ' + error.message);
                    alert(error.message);
                });

                return;
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
                    document.getElementById("error").innerHTML = "Please fix all errors.";
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
    }
});
