/**
 * This file contains code to create the loading screen as part of the `reset` functionality.
 */

function createLoadingScreen(formElementId) {
  // Submit the form.
  document.getElementById(formElementId).submit();

  // Create the loading screen by exposing the screen and shading the background.
  document.getElementsByClassName('loading__shadow-overlay')[0].style.display='flex';
}
