# Connect v1 examples: PHP

The samples in this directory demonstrate basic use of Square Connect API v1 in
PHP. Before you run any of these samples, be sure to complete the steps
described below.

## Setup

### Download Composer and Unirest

These samples require the [Unirest HTTP library for PHP](http://unirest.io/php.html),
which you install via Composer.

First, download Composer in this directory with the instructions on
[this page](https://getcomposer.org/download/).

After you've downloaded Composer, install the Unirest dependency with the following
command from this directory:

    php composer.phar install

### Specify your application credentials

In order for these samples to work, you must provide new values for variables
with the value `REPLACE_ME`. Most commonly, you must provide your personal access
token, which is available from the [application dashboard](https://connect.squareup.com/apps).

See the comments in each sample for details.

#### Run item-management
    php item-management.php
#### Run webhooks or payment-report
    php -S localhost:8000
Visit localhost:8000/payment-report.php or localhost:8000/webhooks.php
