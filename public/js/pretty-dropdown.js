
var dropdownElement;

function initializeDropdown(elementId) {
  dropdownElement = document.getElementById(elementId);
}

function select(element) {
  var value = element.querySelector(".pretty-dropdown__option").innerHTML;
  
  dropdownElement.querySelector("#pretty-dropdown__selected-secondary").innerHTML = value;
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