
var currency;
var formattingFunction;

/**
 * Function to initialize our amount bar.
 * The first parameter is the formatting function used.
 * The second parameter is the currency to be used.
 */
function initializeAmountBar(formattingFunction, currency) {
  this.currency = currency;
  this.formattingFunction = formattingFunction;
  const buttons = document.getElementById("amount-bar").children;
  for (var i = 0; i < buttons.length; i++) {
    if (buttons[i].classList.contains("custom")) {
      continue;
    }
    // For each button that is not "custom", set the text to be formatted based on currency.
    buttons[i].textContent = this.formattingFunction(buttons[i].getAttribute("amount-bar-value"), this.currency);
  }

  // Set initial value for `pay` button and data.
  setAmountChosen(buttons[0].getAttribute("amount-bar-value"));
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
  document.getElementById("custom-amount__div").classList.add("show");
}

function hideCustomTextField() {
  document.getElementById("custom-amount-text").value = "";
  document.getElementById("custom-amount__div").classList.remove("show");
}

// This function will run every time a character is typed in our "custom" text field.
// We will update both the `pay` button text and the actual data to send to our backend
// accordingly.
function updatePayButtonText(element) {
  setAmountChosen(100 * element.value);
}

function setAmountChosen(amount) {
  document.getElementById("pay-button").innerHTML = "Pay " + this.formattingFunction(amount, this.currency);
  document.getElementById("amount-bar__value").value = amount;
}
