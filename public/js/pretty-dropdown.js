
var dropdownElement;

function initializeDropdown(elementId) {
  dropdownElement = document.getElementById(elementId);
}

function select(element) {
  var primaryValue = element.getAttribute("pretty-dropdown-primary-value");
  var secondaryValue = element.getAttribute("pretty-dropdown-secondary-value");

  // Update primary and secondary display values
  dropdownElement.querySelector(".pretty-dropdown__selected-primary").innerHTML = primaryValue;
  dropdownElement.querySelector(".pretty-dropdown__selected-secondary").innerHTML = secondaryValue;

  // Update the actual value to be submitted
  dropdownElement.querySelector("#pretty-dropdown__value").setAttribute("value", element.getAttribute("pretty-dropdown-value"));
}

function showOptions() {
  dropdownElement.querySelector(".pretty-dropdown__options").classList.toggle("show");
}

window.onclick = function(event) {
  var parentNode = dropdownElement.querySelector(".pretty-dropdown__selected");

  if (!parentNode.contains(event.target)) {
    var openDropdown = dropdownElement.querySelector(".pretty-dropdown__options");
    if (openDropdown.classList.contains("show")) {
      openDropdown.classList.remove("show");
    }
  }
}