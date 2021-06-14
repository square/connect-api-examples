function createLoadingPrompt(formElementId) {
  // Submit the form.
  document.getElementById(formElementId).submit();

  // Create the loading screen by exposing the prompt and shading the background.
  document.getElementsByClassName('shadow-overlay')[0].style.display='flex';
  document.getElementsByClassName('loading_prompt-container')[0].style.display='flex';
}
