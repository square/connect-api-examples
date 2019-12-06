<?php

  # This file simply serves the link that merchants click to authorize your application.
  # When authorization completes, a notification is sent to your redirect URL, which should
  # be handled in callback.php.

  /**
  * Square domain for REST API calls
  */
  if (!defined(_SQ_DOMAIN)) {
    define('_SQ_DOMAIN', "https://connect.squareup.com") ;
  }

  /**
  * Square sandbox domain for REST API calls
  */
  if (!defined(_SQ_SANDBOX_DOMAIN)) {
  define('_SQ_SANDBOX_DOMAIN', "https://connect.squareupsandbox.com") ;
  }


  $applicationId = 'REPLACE_ME';

  echo "<a href=\"https://connect.squareupsandbox.com/oauth2/authorize?client_id=$applicationId\">Click here</a> to authorize the application.";
?>
