<%@ Page Language="C#" Inherits="CSharpPaymentExample.Default" %>
  <!DOCTYPE html>
  <html>

  <head runat="server">
    <title>My Payment Form</title>
    <script type="text/javascript" src="https://js.squareup.com/v2/paymentform"></script>
    <script type="text/javascript">
      var sqPaymentForm = new SqPaymentForm({
        applicationId: '<%=ApplicationId%>',
        locationId: '<%=LocationId%>',
        inputClass: 'sq-input',
        cardNumber: {
          elementId: 'sq-card-number',
          placeholder: "0000 0000 0000 0000"
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
          placeholder: 'Postal Code'
        },
        applePay: {
          elementId: 'sq-apple-pay'
        },
        inputStyles: [
          // Because this object provides no value for mediaMaxWidth or mediaMinWidth,
          // these styles apply for screens of all sizes, unless overridden by another
          // input style below.
          {
            fontSize: '14px',
            padding: '3px'
          },
          // These styles are applied to inputs ONLY when the screen width is 400px
          // or smaller. Note that because it doesn't specify a value for padding,
          // the padding value in the previous object is preserved.
          {
            mediaMaxWidth: '400px',
            fontSize: '18px',
          }
        ],
        callbacks: {
          methodsSupported: function (methods) {
            // Show the Apple Pay button if Apple Pay is supported.
            if (methods.applePay === true) {
              var element = document.getElementById('sq-apple-pay');
              element.style.display = 'inline-block';
            }
          },
          createPaymentRequest: function () {
            return {
              requestShippingAddress: false,
              currencyCode: "USD",
              countryCode: "US",

              total: {
                label: "Blue Glass Cafe",
                amount: "1.01",
                pending: false,
              },

              lineItems: [
                {
                  label: "Subtotal",
                  amount: "1.00",
                  pending: false,
                },
                {
                  label: "Tax",
                  amount: "0.01",
                  pending: false,
                }
              ]
            };
          },
          cardNonceResponseReceived: function (errors, nonce, cardData) {
            if (errors) {
              var errorDiv = document.getElementById('errors');
              errorDiv.innerHTML = "";
              errors.forEach(function (error) {
                var p = document.createElement('p');
                p.innerHTML = error.message;
                errorDiv.appendChild(p);
              });
            } else {
              // This alert is for debugging purposes only.
              alert('Nonce received! ' + nonce + ' ' + JSON.stringify(cardData));
              // Assign the value of the nonce to a hidden form element
              var nonceField = document.getElementById('cardNonce');
              nonceField.value = nonce;
            }
          },
          unsupportedBrowserDetected: function () {
            // Alert the buyer that their browser is not supported
          }
        }
      });
      function submitButtonClick(event) {
        event.preventDefault();
        sqPaymentForm.requestCardNonce();
      }
      function SendNonce() {
        PageMethods.Charge(document.getElementById('cardNonce').value, OnSuccess);
      }
      function Clear() {
        // Submit the form
        document.getElementById('form').submit();
      }
      function OnSuccess(response, userContext, methodName) {
        alert(response);
      }
    </script>
    <style type="text/css" runat="server">
      .sq-input {
        border: 1px solid #CCCCCC;
        margin-bottom: 10px;
        padding: 1px;
      }

      .sq-input--focus {
        outline-width: 5px;
        outline-color: #70ACE9;
        outline-offset: -1px;
        outline-style: auto;
      }

      .sq-input--error {
        outline-width: 5px;
        outline-color: #FF9393;
        outline-offset: 0px;
        outline-style: auto;
      }

      .apple-pay-button {
        display: inline-block;
        background-size: 100% 60%;
        background-repeat: no-repeat;
        background-position: 50% 50%;
        border-radius: 5px;
        border-width: 2px;
        padding: 0px;
        box-sizing: border-box;
        min-width: 200px;
        min-height: 32px;
        max-height: 64px;
      }

      .apple-pay-button-white {
        background-image: -webkit-named-image(apple-pay-logo-black);
        background-color: white;
      }

      #sq-apple-pay {
        display: none;
      }
    </style>
  </head>

  <body>
    <h1>My Payment Form</h1>
    <form id="form" runat="server" novalidate="novalidate">
      <asp:ScriptManager ID="ScriptManager" runat="server" EnablePageMethods="true">
      </asp:ScriptManager>
      <label>Credit Card</label>
      <div id="sq-card-number"></div>
      <label>CVV</label>
      <div id="sq-cvv"></div>
      <label>Expiration Date</label>
      <div id="sq-expiration-date"></div>
      <label>Postal Code</label>
      <div id="sq-postal-code"></div>
      <input type="hidden" id="cardNonce" name="nonce" />
      <input type="submit" onclick="submitButtonClick(event)" id="btnSubmit" />
      <input id="btnCharge" type="button" value="Charge" onclick="SendNonce()" />
      <input id="btnClear" type="button" value="Clear" onclick="Clear()" />

      <p>
        <button id="sq-apple-pay" class="apple-pay-button apple-pay-button-white"></button>
      </p>
    </form>

    <div id="errors"></div>
  </body>

  </html>