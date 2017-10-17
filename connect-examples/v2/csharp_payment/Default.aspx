<%@ Page Language="C#" Inherits="CSharpPaymentExample.Default" %>
  <!DOCTYPE html>
  <html>

  <head runat="server">
    <title>My Payment Form</title>
    <script type="text/javascript" src="https://js.squareup.com/v2/paymentform"></script>
    <script type="text/javascript">
      // Set the application ID
      var applicationId = '<%=ApplicationId%>';

      // Set the location ID
      var locationId = '<%=LocationId%>';

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
            fontSize: '.9em'
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
          placeholder: '0000 0000 0000 0000'
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
              applePayLabel.style.display = 'none' ;
            }
            // Only show the button if Masterpass is enabled
            // Otherwise, display the wallet not enabled message.
            if (methods.masterpass === true) {
              masterpassBtn.style.display = 'inline-block';
              masterpassLabel.style.display = 'none';
            }
          },

          /*
          * callback function: createPaymentRequest
          * Triggered when: a digital wallet payment button is clicked.
          */
          createPaymentRequest: function () {

            var paymentRequestJson = {
              requestShippingAddress: true,
              currencyCode: "USD",
              countryCode: "US",
              total: {
                label: "MERCHANT NAME",
                amount: "10.00",
                pending: false
              }
			};
            return paymentRequestJson ;
          },

          /*
          * callback function: cardNonceResponseReceived
          * Triggered when: SqPaymentForm completes a card nonce request
          */
          cardNonceResponseReceived: function(errors, nonce, cardData) {
            if (errors) {
              // Log errors from nonce generation to the Javascript console
              console.log("Encountered errors:");
              errors.forEach(function(error) {
                console.log('  ' + error.message);
              });

              return;
            }

            alert('Nonce received: ' + nonce); /* FOR TESTING ONLY */

            // Assign the nonce value to the hidden form field
            document.getElementById('card-nonce').value = nonce;

            // Invoke the Charge Method in Default.aspx.cs
            PageMethods.Charge(nonce, function(response, userContext, methodName) {
              alert(response);
            });

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
    </script>

    <style type="text/css" runat="server">
      /* Define how SqPaymentForm iframes should look */
      .sq-input {
        border: 1px solid rgb(223, 223, 223);
        outline-offset: -2px;
        margin-bottom: 5px;
        display: inline-block;
      }

      /* Define how SqPaymentForm iframes should look when they have focus */
      .sq-input--focus {
        outline: 5px auto rgb(59, 153, 252);
      }

      /* Define how SqPaymentForm iframes should look when they contain invalid values */
      .sq-input--error {
        outline: 5px auto rgb(255, 97, 97);
      }

      /* Customize the "Pay with Credit Card" button */
      .button-credit-card {
        min-width: 200px;
        min-height: 20px;
        padding: 0;
        margin: 5px;
        line-height: 20px;
        box-shadow: 2px 2px 1px rgb(200, 200, 200);
        background: rgb(255, 255, 255);
        border-radius: 5px;
        border: 1px solid rgb(200, 200, 200);
        font-weight: bold;
        cursor:pointer;
      }


      /* Customize the "{{Wallet}} not enabled" message */
      .wallet-not-enabled {
        min-width: 200px;
        min-height: 40px;
        max-height: 64px;
        padding: 0;
        margin: 10px;
        line-height: 40px;
        background: #eee;
        border-radius: 5px;
        font-weight: lighter;
        font-style: italic;
        font-family: inherit;
        display: block;
      }

      /* Customize the Apple Pay on the Web button */
      .button-apple-pay {
        min-width: 200px;
        min-height: 40px;
        max-height: 64px;
        padding: 0;
        margin: 10px;
        background-image: -webkit-named-image(apple-pay-logo-white);
        background-color: black;
        background-size: 100% 60%;
        background-repeat: no-repeat;
        background-position: 50% 50%;
        border-radius: 5px;
        cursor:pointer;
        display: none;
      }

      /* Customize the Masterpass button */
      .button-masterpass {
        min-width: 200px;
        min-height: 40px;
        max-height: 40px;
        padding: 0;
        margin: 10px;
        background-image: url(https://static.masterpass.com/dyn/img/btn/global/mp_chk_btn_147x034px.svg);
        background-color: black;
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-position: 50% 50%;
        border-radius: 5px;
        border-color: rgb(255, 255, 255);
        cursor:pointer;
        display: none;
      }

      #sq-walletbox {
        float:left;
        margin:5px;
        padding:10px;
        text-align: center;
        vertical-align: top;
        font-weight: bold;
      }

      #sq-ccbox {
        float:left;
        margin:5px;
        padding:10px;
        text-align: center;
        vertical-align: top;
        font-weight: bold;
      }
    </style>
  </head>

  <body>
    <div id="sq-ccbox">
      <!--
        You should replace the action attribute of the form with the path of
        the URL you want to POST the nonce to (for example, "/process-card")
      -->
      <form id="form" runat="server" novalidate="novalidate">
        Pay with a Credit Card
        <asp:ScriptManager ID="ScriptManager" runat="server" EnablePageMethods="true">
        </asp:ScriptManager>
        <table>
        <tbody>
          <tr>
            <td>Card Number:</td>
            <td><div id="sq-card-number"></div></td>
          </tr>
          <tr>
            <td>CVV:</td>
            <td><div id="sq-cvv"></div></td>
          </tr>
          <tr>
            <td>Expiration Date: </td>
            <td><div id="sq-expiration-date"></div></td>
          </tr>
          <tr>
            <td>Postal Code:</td>
            <td><div id="sq-postal-code"></div></td>
          </tr>
          <tr>
            <td colspan="2">
              <button id="sq-creditcard" class="button-credit-card" onclick="requestCardNonce(event)">
                Pay with card
              </button>
            </td>
          </tr>
        </tbody>
        </table>
    
        <!--
          After a nonce is generated it will be assigned to this hidden input field.
        -->
        <input type="hidden" id="card-nonce" name="nonce">
      </form>
    </div>
    
    <div id="sq-walletbox">
      Pay with a Digital Wallet
      <div id="sq-apple-pay-label" class="wallet-not-enabled">Apple Pay for Web not enabled</div>
      <!-- Placholder for Apple Pay for Web button -->
      <button id="sq-apple-pay" class="button-apple-pay"></button>
    
      <div id="sq-masterpass-label" class="wallet-not-enabled">Masterpass not enabled</div>
      <!-- Placholder for Masterpass button -->
      <button id="sq-masterpass" class="button-masterpass"></button>
    </div>

  </body>

  </html>