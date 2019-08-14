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

/* eslint no-undef: 0 */

let currentTab = 0; // Keeps track of the tab that is being shown.
function showTab(num){
  document.getElementsByClassName("tab")[num].classList.remove("hidden");
}
function hideTab(num){
  document.getElementsByClassName("tab")[num].classList.add("hidden");
}
// This function adds .input--validity-visible to each input element if value is empty
function showInputValidity(elements){
  elements.forEach( element => {
    element.classList.add("input--validity-visible");
  });
}
function nextTab(){
  // All of the required inputs in the first tab
  const inputs = document.querySelectorAll(".tab:not(.hidden) .panel-input");

  // Marks the required fields with a red border
  showInputValidity(inputs);

  if (document.forms["nonce-form"].reportValidity()){
    hideTab(currentTab);
    currentTab += 1;
    showTab(currentTab);
    paymentForm.recalculateSize();
  }
}
function selectTime(event){
  if (event.target.tagName === "INPUT"){
    const pickupDate =  document.getElementById("pickup-date");
    pickupDate.innerText = event.target.getAttribute("data-pickup-date");
  }
}
window.onload = function(){
  showTab(currentTab);
};
