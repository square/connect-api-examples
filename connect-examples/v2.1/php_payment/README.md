# Payment processing example: PHP

This sample demonstrates processing card payments with Square Connect API, using the
Square Connect PHP client library.

## Setup

### Requirements

* PHP >= 5.4.0

### Install the PHP client library

This sample already includes the `square/connect` dependency in its `composer.json`
file. To install the client library:

1. Make sure you've downloaded Composer, following the instructions
[here](https://getcomposer.org/download/).

2. Run the following command from the directory containing `composer.json`:

```
php composer.phar install
```

### Provide required credentials

Both `process-card.php` and `index.html` have values near the top of the file
that you need to replace with various credentials associated with your application.
If you're just testing things out, it's recommended that you use your _sandbox_
credentials for now. See
[this article](https://docs.connect.squareup.com/articles/using-sandbox/)
for more information on the API sandbox.

You can `grep` for `REPLACE_ME` to find all of the fields to replace.

## Running the sample

From the sample's root directory, run:

    php -S localhost:8000

You can then visit `localhost:8000` in your browser to see the card form.

If you're using your sandbox credentials, you can test a valid credit card
transaction by providing the following card information in the form:

* Card Number `4532 7597 3454 5858`
* Card CVV `111`
* Card Expiration (Any time in the future)
* Card Postal Code (Any valid US postal code)

You can find more testing values in this [article](https://docs.connect.squareup.com/articles/using-sandbox)

**Note that if you are _not_ using your sandbox credentials and you enter _real_
credit card information, YOU WILL CHARGE THE CARD.**
