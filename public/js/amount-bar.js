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

function updatePayButtonText(element) {
  // In the case we use a custom amount, the amount to send to the backend and the text amount are the same.
  setAmountChosen(element.value, element.value);
}

function setAmountChosen(amount) {
  document.getElementById("pay-button").innerHTML = "Pay $" + amount;
  document.getElementById("amount-bar__value").value = amount;
}
