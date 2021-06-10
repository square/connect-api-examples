
class PrettyDropdownSelect {
  /**
   * 
   * @param {*} elementId - id of the element to build the dropdown in
   * @param {*} data      - data for options in the dropdown select
   * @param {*} options   - options for additonal configuration
   * 
   * example `data`:
   * [ { 
   *    img: "/example1.png",
   *    description: "Customer",
   *    value: "123456",
   *    displayed-value: "Emma Chen"
   * }, {
   *    img: "/example2.png",
   *    description: "Customer",
   *    value: "123457",
   *    displayed-value: "Sailor Moon"
   * }]
   * 
   * example `options`: {
   *    disabled: false,                  // whether the dropdown is disabled
   *    addonIds: ["createCustomer"],     // additional elements added to the dropdown select list
   *    placeholder:  "Select customer"   // placeholder text to display
   * }
   */
  constructor(elementId, data, options) {
    this.dropdownElement = document.getElementById(elementId);
    this.data = data;
    this.options = options;

    this._buildDropdownElements();
    this._buildDropdownData();
  }

  _buildDropdownElements() {
    dropdownElement.classList.add("pretty-dropdown");
  
    var selected = document.createElement("div");
    selected.classList.add("pretty-dropdown__selected");
    selected.onclick = _showOptions;
  
    var selectedImageContainer = document.createElement("div");
    selectedImageContainer.classList.add("pretty-dropdown__selected-image");
    selected.appendChild(selectedImageContainer);
    var selectedImage = document.createElement("img");
  
    selectedImageContainer.appendChild(selectedImage);
  
    var selectedValues = document.createElement("div");
    selectedValues.classList.add("pretty-dropdown__selected-values");
    selected.appendChild(selectedValues);
  
    var defaultSelectedPrimaryValue = document.createElement("span");
    defaultSelectedPrimaryValue.classList.add("pretty-dropdown__selected-primary-display")
    selectedValues.appendChild(defaultSelectedPrimaryValue);
  
    var defaultSelectedSecondaryDisplay = document.createElement("span");
    defaultSelectedSecondaryDisplay.classList.add("pretty-dropdown__selected-secondary-display")
    selectedValues.appendChild(defaultSelectedSecondaryDisplay);
  
    var dropdownIcon = document.createElement("span");
    dropdownIcon.classList.add("pretty-dropdown__icon");
    selected.appendChild(dropdownIcon);
  
    var dropdownIconImg = document.createElement("img");
    dropdownIconImg.src = "/images/caret.svg";
    dropdownIcon.appendChild(dropdownIconImg);

    // insert the selection box at the front of the parent element
    dropdownElement.insertBefore(selected, dropdownElement.firstChild);

    // insert the selection options list next
    var dropdownOptionsWrapper = document.createElement("div");
    var dropdownOptionsList = document.createElement("ul");
    dropdownOptionsList.classList.add("pretty-dropdown-options");
    dropdownOptionsWrapper.appendChild(dropdownOptionsList);
  }

  _buildDropdownData() {
    if (this.data.length > 0) {
      
    }
  }

  _applyOptions() {

  }

  _select(element) {
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
  
  _showOptions() {
    if (dropdownElement) {
      dropdownElement.querySelector(".pretty-dropdown__options").classList.toggle("show");
    }
  }
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
