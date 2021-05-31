// highlighting of rows so user know which method is selected. This can be removed when design is finalized.
$(document).on('click', '#table tr', function() {
  $(this).addClass('active-row').siblings().removeClass('active-row');
});
