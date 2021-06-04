
var dropdownElement;

function initializeDropdown(elementId, options) {
  dropdownElement = document.getElementById(elementId);
  var selected = dropdownElement.getElementsByClassName("option-selected")[0];
  selected.querySelector("#selected-secondary").innerHTML = options[0].givenName + " " + options[0].familyName;
}

function select(element) {
  console.log(element);
  var value = element.querySelector(".option").innerHTML;
  var selected = dropdownElement.getElementsByClassName("option-selected")[0];
  selected.querySelector("#selected-secondary").innerHTML = value;

  dropdownElement.querySelector("#login-customer").setAttribute("value", element.getAttribute("customer-id"));
}

function showOptions() {
  document.getElementById("dropdown-options").classList.toggle("show");
}

window.onclick = function(event) {
  var parentNode = document.getElementById("dropdown-selected");

  if (!parentNode.contains(event.target)) {
    var openDropdown = document.getElementById("dropdown-options");
    if (openDropdown.classList.contains("show")) {
      openDropdown.classList.remove("show");
    }
  }
}