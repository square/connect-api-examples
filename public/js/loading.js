/**
 * This file contains code to create the loading prompt as part of the `reset` functionality.
 */

function createLoadingPrompt(formElementId) {
  // Submit the form.
  document.getElementById(formElementId).submit();

  // Create the loading screen by exposing the prompt and shading the background.
  document.getElementsByClassName('loading__shadow-overlay')[0].style.display='flex';
}
