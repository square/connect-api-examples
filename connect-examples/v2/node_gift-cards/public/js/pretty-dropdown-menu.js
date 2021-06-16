/**
 * This file contains functions that handles the actions of the drop down menu
 */

function showMenuOptions() {
  document.querySelector(".pretty-dropdown-menu__list").classList.toggle("show");
}

window.onclick = function (event) {
  var parentNode = event.target.closest(".pretty-dropdown-menu");

  if (!parentNode) {
    var openDropdown = document.querySelector(".pretty-dropdown-menu__list");
    if (openDropdown.classList.contains("show")) {
      openDropdown.classList.remove("show");
    }
  }
}
