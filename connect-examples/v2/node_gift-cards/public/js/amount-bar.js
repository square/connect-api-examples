class AmountBar {

  /**
   * Constructor to initialize our amount bar.
   * @constructor
   * @param {*} elementId - The ID associated with the amount-bar element in the HTML.
   * @param {*} formatMoneyFunction - A formatting function used to format money based on different parameters.
   * @param {*} currency - The currency to be used.
   * @param {*} maxAllowedToAdd - The maximum allowed money value to be added to the gift card.
   */
  constructor(elementId, formatMoneyFunction, currency, maxAllowedToAdd) {
    this.currency = currency;
    this.formatMoneyFunction = formatMoneyFunction;
    this.maxAllowedToAdd = maxAllowedToAdd;

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
   *
   * @param {*} amount - The amount to display on the button and to send to our backend.
   * Note that this parameter is expected to be in the lowest denomination possible for the currency provided.
   */
  setAmountChosen(amount = 0) {
    // We dont want rounding on our pay button, pass in false to our formatting function.
    let payButton = document.getElementById("pay-button")
    if (amount > this.maxAllowedToAdd) {
      payButton.innerHTML = "Amount exceeds maximum balance allowed";

      payButton.setAttribute("disabled-by-amount", "true");
    } else {
      payButton.innerHTML = "Pay " + this.formatMoneyFunction(amount, this.currency, false);
      document.getElementById("amount-bar__value").value = amount;

      payButton.setAttribute("disabled-by-amount", "false");
    }

    // NOTE: The following logic is used specifically for coordinating logic between the dropdown and the pay button
    payButton.disabled = payButton.getAttribute("disabled-by-amount") === "true" || payButton.getAttribute("disabled-by-dropdown") === "true";
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

      // If the maximum amount we can add is greater than the current button amount, disable it.
      if (buttons[i].getAttribute("amount-bar-value") > this.maxAllowedToAdd) {
        buttons[i].disabled = true;
        buttons[i].style.cursor = "not-allowed";
      }
    }

    // Set initial value for `pay` button and data. If the first button is disabled
    // (i.e. adding that amount would exceed the maximum gift card balance allowed),
    // set the default selected button to 'custom', and show the text field. Otherwise,
    // use the first button value.
    if (buttons[0].disabled) {
      buttons[0].classList.remove("active");
      buttons[buttons.length - 1].classList.add("active");
      this.showCustomTextField();
      this.setAmountChosen();
    } else {
      this.setAmountChosen(buttons[0].getAttribute("amount-bar-value"));
    }
  }
}
