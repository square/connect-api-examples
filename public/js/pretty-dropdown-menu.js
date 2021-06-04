function showMenuOptions() {
  document.querySelector(".pretty-dropdown-menu__list").classList.toggle("show");
}

window.onclick = function(event) {
  var parentNode = dropdownElement.querySelector(".pretty-dropdown-menu");

  if (!parentNode.contains(event.target)) {
    var openDropdown = dropdownElement.querySelector(".pretty-dropdown-menu__list");
    if (openDropdown.classList.contains("show")) {
      openDropdown.classList.remove("show");
    }
  }
}