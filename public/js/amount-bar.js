
var currency;
var formatMoneyFunction;

/**
 * Function to initialize our amount bar.
 * The first parameter is the formatting function used.
 * The second parameter is the currency to be used.
 */
function initializeAmountBar(elementId, formatMoneyFunction, currency, maxAllowedToAdd) {
  console.log(maxAllowedToAdd);
  this.currency = currency;
  this.formatMoneyFunction = formatMoneyFunction;
  const buttons = document.getElementById(elementId).children;
  for (var i = 0; i < buttons.length; i++) {
    if (buttons[i].classList.contains("custom")) {
      continue;
    }
    // For each button that is not "custom", set the text to be formatted based on currency
    // Pass in true in order to achieve rounding, so text fits nicely. The actual function can be
    // found in functions.ejs.
    buttons[i].textContent = this.formatMoneyFunction(buttons[i].getAttribute("amount-bar-value"), this.currency, true, true);

    if (buttons[i].getAttribute("amount-bar-value") > maxAllowedToAdd) {
      buttons[i].disabled = true;
      buttons[i].style.cursor = "not-allowed";
    }
  }

  // Set initial value for `pay` button and data. If the first button is disabled
  // (i.e. adding that amount would exceed the maximum gift card balance allowed),
  // set the default to custom.
  if (buttons[0].disabled) {
    buttons[0].classList.remove("active");
    buttons[buttons.length - 1].classList.add("active");
    showCustomTextField();
    setAmountChosen();
  } else {
    setAmountChosen(buttons[0].getAttribute("amount-bar-value"));
  }
}

/**
 * Function to select a button from the amount bar.
 * It will update other "buttons" on the bar to not be selected.
 * If the "button" selected is `Custom`, we want to show a text field.
 */
function selectButton(element) {
  element.parentNode.getElementsByClassName("active")[0].classList.remove("active");
  if (element.classList.contains("custom")) {
    showCustomTextField();
  } else {
    hideCustomTextField();
  }
  element.classList.add("active");

  // Set the actual value, in both the `pay` button text, and the value to send to the back end.
  setAmountChosen(element.getAttribute("amount-bar-value"));
}

function showCustomTextField() {
  document.getElementById("custom-amount").classList.add("show");
}

function hideCustomTextField() {
  document.getElementById("custom-amount-text").value = "";
  document.getElementById("custom-amount").classList.remove("show");
}

// This function will run every time a character is typed in our "custom" text field.
// We will update both the `pay` button text and the actual data to send to our backend
// accordingly.
function updatePayButtonText(element) {
  setAmountChosen((element.value > 0 ? element.value : 0), false);
}

function setAmountChosen(amount = 0, isLowestDenomination = true) {
  // We dont want rounding on our pay button, pass in false to our formatting function.
  document.getElementById("pay-button").innerHTML = "Pay " + this.formatMoneyFunction(amount, this.currency, false, isLowestDenomination);
  document.getElementById("amount-bar__value").value = (!isLowestDenomination) ? amount * 100 : amount;
}
