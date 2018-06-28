Square Checkout Demo
=========================

This is a simple example application that utilizes Square's Checkout API.

It takes a single payment, declared by the user, and creates an order to use in the Checkout API.

To get it running:

* Clone/download to your local computer.
* Rename `.envsample` to `.env` and fill in the values.
* In `checkout.php` there's a variable, `IS_PROD` to switch between production and sandbox; change it based on your needs.
* Downloaded composer (https://getcomposer.org/download/)
* Run the following command in your terminal to download the required packages (in the directory you copied the project to):
```
php composer.phar install
```
* Run the following command in your terminal to start your server:
```
php -S localhost:8888
```

This will start the server on `localhost:8888`, which you can navigate to in your favorite browser.
