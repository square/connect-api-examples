function selectButton(element) {
  element.parentNode.getElementsByClassName("active")[0].classList.remove("active");
  if (element.classList.contains("custom")) {
    showCustomTextField();
  } else {
    hideCustomTextField();
  }
  element.classList.add("active");
  document.getElementById("pay-button").innerHTML = "Pay $" + element.getAttribute("name");
  document.getElementById("pretty-dropdown__value-2").value = element.getAttribute("name")
}

function showCustomTextField() {
  document.getElementById("custom-amount__div").classList.add("show");
}

function hideCustomTextField() {
  document.getElementById("custom-amount-text").value = "";
  document.getElementById("custom-amount__div").classList.remove("show");
}

function updatePayButtonText(element) {
  document.getElementById("pay-button").innerHTML = "Pay $" + element.value;
  document.getElementById("pretty-dropdown__value-2").value = element.value;
}
