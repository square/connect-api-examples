
var dropdownElement;
var data;

function initializeDropdown(elementId, defaultData) {
  dropdownElement = document.getElementById(elementId);
  data = data;

  dropdownElement.classList.add("pretty-dropdown");

  var selected = document.createElement("div");
  selected.classList.add("pretty-dropdown__selected");
  selected.onclick = showOptions;

  var selectedImageContainer = document.createElement("div");
  selectedImageContainer.classList.add("pretty-dropdown__selected-image");
  selected.appendChild(selectedImageContainer);
  var selectedImage = document.createElement("img");

  if (defaultData.img) {
    selectedImage.src = defaultData.img;
  }
  selectedImageContainer.appendChild(selectedImage);

  var selectedValues = document.createElement("div");
  selectedValues.classList.add("pretty-dropdown__selected-values");
  selected.appendChild(selectedValues);

  var defaultSelectedPrimaryValue = document.createElement("span");
  defaultSelectedPrimaryValue.classList.add("pretty-dropdown__selected-primary-display")
  if (defaultData.primary) {
    defaultSelectedPrimaryValue.textContent = defaultData.primary;
  }
  selectedValues.appendChild(defaultSelectedPrimaryValue);

  var defaultSelectedSecondaryDisplay = document.createElement("span");
  defaultSelectedSecondaryDisplay.classList.add("pretty-dropdown__selected-secondary-display")
  if (defaultData.secondary) {
    defaultSelectedSecondaryDisplay.textContent = defaultData.secondary;
  }
  selectedValues.appendChild(defaultSelectedSecondaryDisplay);

  var dropdownIcon = document.createElement("span");
  dropdownIcon.classList.add("pretty-dropdown__icon");
  selected.appendChild(dropdownIcon);

  var dropdownIconImg = document.createElement("img");
  dropdownIconImg.src = "/images/caret.svg";
  dropdownIcon.appendChild(dropdownIconImg);

  dropdownElement.insertBefore(selected, dropdownElement.firstChild);
}

function select(element) {
  var primaryDisplay = element.getAttribute("pretty-dropdown-primary-display");
  var secondaryDisplay = element.getAttribute("pretty-dropdown-secondary-display");
  var value = element.getAttribute("pretty-dropdown-value");

  // Update primary and secondary display values
  dropdownElement.querySelector(".pretty-dropdown__selected-primary-display").innerHTML = primaryDisplay;
  dropdownElement.querySelector(".pretty-dropdown__selected-secondary-display").innerHTML = secondaryDisplay;

  // If there is an image associated with an option, we want to set that as well.
  var image = element.querySelector("img");
  if (image) {
    dropdownElement.querySelector(".pretty-dropdown__selected-image").querySelector("img").src = image.src;
  }

  // Update the actual value to be submitted
  document.getElementById("pretty-dropdown__value").setAttribute("value", value);
}

function showOptions() {
  if (dropdownElement)
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
