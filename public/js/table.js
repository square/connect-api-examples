$(document).on('click', '#table tr', function() {
  $(this).addClass('active-row').siblings().removeClass('active-row');
});
