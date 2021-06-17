class AmountBar {

  /**
   * Constructor to initialize our amount bar.
   * @constructor
   * @param {*} elementId - The ID associated with the amount-bar element in the HTML.
   * @param {*} formatMoneyFunction - A formatting function used to format money based on different parameters.
   * @param {*} currency - The currency to be used.
   * @param {*} maxAllowedToAdd - The maximum allowed money value to be added to the gift card.
   */
  constructor(elementId, formatMoneyFunction, currency, maxAllowedToAdd, dropdown) {
    this.currency = currency;
    this.formatMoneyFunction = formatMoneyFunction;
    this.maxAllowedToAdd = maxAllowedToAdd;
    this.dropdown = dropdown;

    const buttons = document.getElementById(elementId).children;
    this.configureButtons(buttons);
  }

  /**
   * Function for handling amount-bar button selection.
   * It will update other buttons on the bar to not be selected.
   * If the button selected is `Custom`, we will show a text field.
   *
   * @param {*} element - The button element that was selected.
   */
  selectButton(element) {
    element.parentNode.getElementsByClassName("active")[0].classList.remove("active");
    if (element.classList.contains("custom")) {
      this.showCustomTextField();
    } else {
      this.hideCustomTextField();
    }
    element.classList.add("active");

    // Set the actual value, in both the `pay` button text, and the value to send to the back end.
    this.setAmountChosen(element.getAttribute("amount-bar-value"));
  }

  /**
   * A helper function to show the custom text field.
   */
  showCustomTextField() {
    document.getElementById("custom-amount").classList.add("show");
    document.getElementById("custom-amount-text").required = true;
  }

  /**
   * A helper function to hide the custom text field.
   */
  hideCustomTextField() {
    document.getElementById("custom-amount-text").value = "";
    document.getElementById("custom-amount").classList.remove("show");
    document.getElementById("custom-amount-text").required = false;
  }

  /**
   * A function that will run every time a character is typed in our `Custom` text field.
   * We will update both the `pay button` text, as well as the actual data to be sent to the backend.
   *
   * @param {*} element - The element that was used (i.e. the pay button).
   */
  updatePayButtonText(element) {
    let valueInLowestDenomination;

    if (element.value < 0) {
      valueInLowestDenomination = 0
    } else if (this.currency === "JPY") {
      // already in lowest denomination
      valueInLowestDenomination = element.value;
    } else {
      // greater than 0 and not JPY
      valueInLowestDenomination = Math.round(100 * element.value);
    }

    this.setAmountChosen(valueInLowestDenomination);
  }

  /**
   * A function to set the money amount selected in two places:
   * 1. The 'pay button' text, in which the amount should be based on the most commonly used denomination.
   * 2. The `amount-bar__value` value, which is the value that will be sent to the backend in case of a submit, and
   *    should be in the lowest denomination.
   * This function also coordinates with the dropdown on what the global state of the pay button should be (i.e. should it be disabled or enabled)
   * based on the current value we are about to send to the backend.
   *
   * @param {*} amount - The amount to display on the button and to send to our backend.
   * Note that this parameter is expected to be in the lowest denomination possible for the currency provided.
   */
  setAmountChosen(amount = 0) {
    const valueInput = document.getElementById("pretty-dropdown__value");
    let disableFlag = true;

    // Check the current state. The button text will change based on that.
    if (this.maxAllowedToAdd == 0) {
      document.getElementById("pay-button").innerHTML = "Maximum gift card balance reached";
    } else if (amount > this.maxAllowedToAdd) {
      document.getElementById("pay-button").innerHTML = "Funds would exceed maximum balance allowed";
    } else {
      // We dont want rounding on our pay button, pass in false to our formatting function.
      document.getElementById("pay-button").innerHTML = "Pay " + this.formatMoneyFunction(amount, this.currency, false);
      disableFlag = false;
    }

    // If a credit card on file was selected, we apply the disable/enable flag to the state of the `pay button`.
    if (valueInput.value !== "") {
      document.getElementById("pay-button").disabled = disableFlag;
    }

    // Update data to send to backend
    document.getElementById("amount-bar__value").value = amount;

    // Remove and re-add an event listener with the new disable override. If this flag is true, the button will not be enabled.
    this.dropdown.removeInputEventListener();
    this.dropdown.addInputEventListener(disableFlag);
  }

  /**
   * A function to configure the amount bar buttons with default values.
   *
   * @param {*} buttons - The list of the amount bar buttons.
   */
  configureButtons(buttons) {
    for (var i = 0; i < buttons.length; i++) {
      buttons[i].onclick = this.selectButton.bind(this, buttons[i]);
      if (buttons[i].classList.contains("custom")) {
        continue;
      }

      // For each button that is not "custom", set the text to be formatted based on currency.
      // Pass in true in order to achieve rounding, so text fits nicely. The actual function can be
      // found in `functions.ejs`.
      buttons[i].textContent = this.formatMoneyFunction(buttons[i].getAttribute("amount-bar-value"), this.currency, true);
    }

    // set Initial amount chosen.
    this.setAmountChosen(buttons[0].getAttribute("amount-bar-value"));
  }
}
