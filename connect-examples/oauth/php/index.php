<?php

  # This file simply serves the link that merchants click to authorize your application.
  # When authorization completes, a notification is sent to your redirect URL, which should
  # be handled in callback.php.

  $applicationId = 'REPLACE_ME';

  echo "<a href=\"https://connect.squareup.com/oauth2/authorize?client_id=$applicationId\">Click here</a> to authorize the application.";
?>
