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

function validateAndSubmitDeliveryPickupDetails() {
  if (document.forms["delivery-pickup-form"].reportValidity()){
    document.forms["delivery-pickup-form"].submit();
  } else {
    const inputs = document.querySelectorAll(".panel-input");
    // Marks the required fields with a red border
    inputs.forEach( input => {
      input.classList.add("input--validity-visible");
    });
  }
}
