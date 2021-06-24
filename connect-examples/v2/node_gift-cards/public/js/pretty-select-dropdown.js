
class PrettySelectDropdown {
  /**
   * Class for handling the drop down selection elements
   *
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
    this._configureElements();


    var self = this;
    window.onclick = function (event) {
      if (!self.dropdownElement.contains(event.target)) {
        var openDropdown = self.dropdownElement.querySelector(".pretty-dropdown__options-wrapper");
        if (openDropdown.classList.contains("show")) {
          openDropdown.classList.remove("show");
        }
      }
    }
  }

  /**
   * Build the select dropdown elements
   */
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

  /**
   * Create the select options using this.data
   */
  _buildDropdownData() {
    // if data length is > 0, then build the options list
    if (this.data.length > 0) {
      // set the default values
      this.dropdownElement.querySelector(".pretty-dropdown__selected-description").innerText = this.data[0].description;
      this.dropdownElement.querySelector(".pretty-dropdown__selected-display-value").innerText = this.data[0].displayValue;
      var valueInput = document.getElementById("pretty-dropdown__value")
      valueInput.setAttribute("value", this.data[0].value);
      var inputEvent = new Event("input");
      valueInput.dispatchEvent(inputEvent);

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

  /**
   * Apply any options specified
   */
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
      var valueInput = document.getElementById("pretty-dropdown__value")
      valueInput.setAttribute("value", "");
      
      var inputEvent = new Event("input");
      valueInput.dispatchEvent(inputEvent);
    }

    // Add elements with certain IDs to the pretty-dropdown__options-wrapper
    if (this.options.addonIds) {
      this.options.addonIds.forEach(id => {
        var element = document.getElementById(id);
        if (element) {
          this.dropdownElement.querySelector(".pretty-dropdown__options-wrapper").appendChild(element);
        }
      });
    }
  }

  /**
   * Selects an option and sets the value of #pretty-dropdown__value
   * to that of the selected option
   *
   * @param {*} event
   */
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
    var valueInput = document.getElementById("pretty-dropdown__value")
    valueInput.setAttribute("value", value);
    var inputEvent = new Event("input");
    valueInput.dispatchEvent(inputEvent);

    this._showOptions();
  }

  /**
   * Toggles open the select options
   */
  _showOptions() {
    if (!this.dropdownElement.classList.contains("pretty-dropdown__disabled")) {
      this.dropdownElement.querySelector(".pretty-dropdown__options-wrapper").classList.toggle("show");
      this.dropdownElement.querySelector(".pretty-dropdown__icon").classList.toggle("flipped");
    }
  }

  /**
   * configure the input listener for #pretty-dropdown__value and disable the
   * submit button if the value of #pretty-dropdown__value is empty
   */
  _configureElements() {
    var valueInput = document.getElementById("pretty-dropdown__value");
    var submitButton = valueInput.closest("form").querySelector(":scope > button[type=submit]");
    submitButton.setAttribute("disabled-by-dropdown", submitButton.disabled.toString());

    valueInput.addEventListener('input', function () {
      if (valueInput.value !== "") {
        submitButton.setAttribute("disabled-by-dropdown", "false");
      } else {
        submitButton.setAttribute("disabled-by-dropdown", "true");
      }

      // NOTE: The following logic is used specifically for coordinating logic between the dropdown and the pay button
      if (submitButton.getAttribute("disabled-by-amount")) {
        submitButton.disabled = submitButton.getAttribute("disabled-by-amount") === "true" || submitButton.getAttribute("disabled-by-dropdown") === "true";
      } else {
        submitButton.disabled = submitButton.getAttribute("disabled-by-dropdown") === "true";
      }
      
    });

    var inputEvent = new Event("input");
    valueInput.dispatchEvent(inputEvent);
  }
}
