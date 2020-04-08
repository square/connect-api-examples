/*
Copyright 2019 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

function openModal(element) {
  // Changes hash which triggers which item's modal is on display
  showModal("modal-" + element.parentElement.id);
  window.history.pushState(null, null, `#${element.parentElement.id}`);
}
function showModal(id) {
  const modal = document.getElementById(id);
  if (modal) {
    // Only show modal if element exists
    document.body.classList.add("modal-open"); // add class to help prevent scrolling when overlay is open
    const veil = document.getElementById("veil");

    veil.classList.add("visible");
    modal.classList.add("is-open");
  } else {
    closeModal(); // Closes already open modal when invalid hash is used.
  }
}
function closeModal() {
  const modal = document.querySelector(".modal.is-open"); // Finds open modal element

  if (modal) {
    // If open modal exist it closes it
    modal.classList.remove("is-open");

    document.body.classList.remove("modal-open"); // remove class to resume scrolling
    document.getElementById("veil").classList.remove("visible");
  }
}
function onCloseModalClick() {
  // Closes modal
  closeModal();
  // Changes url after closing modal
  window.history.pushState(null, null, "/");
}
function onUrlChange() {
  // function is called whenever there is a change to URL
  const itemId = location.hash.slice(1);
  if (itemId !== "") {
    // If hash exists show modal
    showModal("modal-" + itemId);
  } else {
    closeModal(); // If no hash exists close the modal
  }
}
window.onpopstate = onUrlChange;
window.onload = onUrlChange;
window.onclick = function (element) {
  if (element.srcElement.id === "veil" && location.hash.length > 1) {
    closeModal();
  }
};
