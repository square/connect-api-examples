
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
   *    displayValue: "Sailor Moon"
   * }]
   * 
   * example `options`: {
   *    disabled: false,                  // whether the dropdown is disabled
   *    addonIds: ["createCustomer"],     // additional elements added to the dropdown select list
   *    placeholder:  "Select customer"   // placeholder text to display
   *    placeholderImg: "/placeholder.svg"  // placeholder for img
   * }
   */
  constructor(elementId, data = [], options = {}) {
    this.dropdownElement = document.getElementById(elementId);
    this.data = data;
    this.options = options;

    this._buildDropdownElements();
    this._buildDropdownData();
    this._applyOptions();


    var self = this;
    window.onclick = function(event) {
      if (!self.dropdownElement.contains(event.target)) {
        var openDropdown = self.dropdownElement.querySelector(".pretty-dropdown__options-wrapper");
        if (openDropdown.classList.contains("show")) {
          openDropdown.classList.remove("show");
        }
      }
    }
  }

  _buildDropdownElements() {
    this.dropdownElement.classList.add("pretty-dropdown");
  
    // build the element that displays the selected option
    var selected = document.createElement("div");
    selected.classList.add("pretty-dropdown__selected");
    selected.onclick = this._showOptions.bind(this);
  
    // build element that displays the image associated with the selected option
    var selectedImageContainer = document.createElement("div");
    selectedImageContainer.classList.add("pretty-dropdown__selected-image");
    selected.appendChild(selectedImageContainer);
    var selectedImage = document.createElement("img");
    selectedImageContainer.appendChild(selectedImage);
  
    // build the element that displays the values of the selected option
    var selectedValues = document.createElement("div");
    selectedValues.classList.add("pretty-dropdown__selected-values");
    selected.appendChild(selectedValues);
  
    // build the element that displays the description of the selected option
    var defaultSelectedPrimaryValue = document.createElement("span");
    defaultSelectedPrimaryValue.classList.add("pretty-dropdown__selected-description")
    selectedValues.appendChild(defaultSelectedPrimaryValue);
  
    // build the element that displays the displayed value of the selected option
    var defaultSelectedSecondaryDisplay = document.createElement("span");
    defaultSelectedSecondaryDisplay.classList.add("pretty-dropdown__selected-display-value")
    selectedValues.appendChild(defaultSelectedSecondaryDisplay);
  
    // build the dropdown caret icon
    var dropdownIcon = document.createElement("span");
    dropdownIcon.classList.add("pretty-dropdown__icon");
    selected.appendChild(dropdownIcon);
  
    var dropdownIconImg = document.createElement("img");
    dropdownIconImg.src = "/images/caret.svg";
    dropdownIcon.appendChild(dropdownIconImg);

    // insert the selection box at the front of the parent element
    this.dropdownElement.insertBefore(selected, this.dropdownElement.firstChild);

    // insert the selection options list next
    var dropdownOptionsWrapper = document.createElement("div");
    dropdownOptionsWrapper.classList.add("pretty-dropdown__options-wrapper")
    this.dropdownElement.appendChild(dropdownOptionsWrapper);
  }

  _buildDropdownData() {
    // if data length is > 0, then build the options list
    if (this.data.length > 0) {
      // set the default values
      this.dropdownElement.querySelector(".pretty-dropdown__selected-description").innerText = this.data[0].description;
      this.dropdownElement.querySelector(".pretty-dropdown__selected-display-value").innerText = this.data[0].displayValue;
      document.getElementById("pretty-dropdown__value").setAttribute("value", this.data[0].value);
      if (this.data[0].img) {
        this.dropdownElement.querySelector(".pretty-dropdown__selected-image").querySelector("img").src = this.data[0].img;
      }

      // build select option items
      var dropdownOptionsWrapper = this.dropdownElement.querySelector(".pretty-dropdown__options-wrapper");

      var dropdownOptionsList = document.createElement("ul");
      dropdownOptionsList.classList.add("pretty-dropdown__options");
      dropdownOptionsWrapper.appendChild(dropdownOptionsList);

      this.data.forEach(item => {
        // create the list item
        var listItem = document.createElement("li");
        listItem.onclick = this._select.bind(this);
        dropdownOptionsList.appendChild(listItem);

        var dropdownOption = document.createElement("div");
        dropdownOption.classList.add("pretty-dropdown__option");
    
        listItem.setAttribute("pretty-dropdown-description", item.description);
        listItem.setAttribute("pretty-dropdown-value", item.value);
        listItem.appendChild(dropdownOption);

        // if an image is available for the item, create the element that displays the image
        if (item.img) {
          var listItemImage = document.createElement("img");
          listItemImage.src = item.img;
          dropdownOption.appendChild(listItemImage);
        }

        // set the displayed value
        var listItemDisplayValue = document.createElement("span");
        listItemDisplayValue.innerText = item.displayValue;
        dropdownOption.appendChild(listItemDisplayValue);
      });
    } else {
      // if no data is available, display placeholder value
      var placeholder = "No selection options available";
      if (this.options.placeholder) {
        placeholder = this.options.placeholder;
      }

      this.dropdownElement.querySelector(".pretty-dropdown__selected-display-value").innerText = placeholder;
    }
  }

  _applyOptions() {
    if (this.options.placeholderImg) {
      this.dropdownElement
      .querySelector(".pretty-dropdown__selected-image")
      .querySelector("img").src = this.options.placeholderImg;
    }

    if (this.options.disabled) {
      this.dropdownElement.classList.add("pretty-dropdown__disabled");
    }

    if (this.options.placeholder) {
      this.dropdownElement.querySelector(".pretty-dropdown__selected-description").innerText = this.options.placeholder;
      this.dropdownElement.querySelector(".pretty-dropdown__selected-display-value").innerText = null;
      document.getElementById("pretty-dropdown__value").setAttribute("value", "");
    }

    if (this.options.addonIds) {
      this.options.addonIds.forEach((id) => {
        var element = document.getElementById(id);
        if (element) {
          this.dropdownElement.querySelector(".pretty-dropdown__options-wrapper").appendChild(element);
        }
      });
    }

  }

  _select(event) {
    var currentSelected = this.dropdownElement.querySelector(".option-selected")
    if (currentSelected) {
      currentSelected.classList.remove("option-selected");
    }
    
    var listItem = event.target.closest("li");
    listItem.classList.add("option-selected");
    var description = listItem.getAttribute("pretty-dropdown-description");
    var displayValue = listItem.querySelector("span").innerText;
    var value = listItem.getAttribute("pretty-dropdown-value");
  
    // Update primary and secondary display values
    this.dropdownElement.querySelector(".pretty-dropdown__selected-description").innerText = description;
    this.dropdownElement.querySelector(".pretty-dropdown__selected-display-value").innerText = displayValue;
  
    // If there is an image associated with an option, we want to set that as well.
    var image = listItem.querySelector("img");
    if (image) {
      this.dropdownElement.querySelector(".pretty-dropdown__selected-image").querySelector("img").src = image.src;
    }
  
    // Update the actual value to be submitted
    document.getElementById("pretty-dropdown__value").setAttribute("value", value);

    this._showOptions();
  }
  
  _showOptions() {
    if (!this.dropdownElement.classList.contains("pretty-dropdown__disabled")) {
      this.dropdownElement.querySelector(".pretty-dropdown__options-wrapper").classList.toggle("show");
      this.dropdownElement.querySelector(".pretty-dropdown__icon").classList.toggle("flipped");
    }
  }
}
