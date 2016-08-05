# Payment processing example: Csharp

This sample demonstrates processing card payments with Square Connect API, using the
Square Connect C# client library.

## Setup

### Install the C# client library

package.config under PaymentExample contains package dependency

1. Import the SquareConnect.sln to your Visual Studio

2. If there is no warning asking you to restore packages, run the following command in the [Package Manager Console](https://docs.nuget.org/docs/start-here/using-the-package-manager-console):

        Install-Package Square.Connect

### Provide required credentials

Both `PaymentExample/Default.aspx` and `PaymentExample/Default.aspx.cs` have values near the top of the file
that you need to replace with various credentials associated with your application.
If you're just testing things out, it's recommended that you use your _sandbox_
credentials for now. See
[this article](https://docs.connect.squareup.com/articles/using-sandbox/)
for more information on the API sandbox.

You can `grep` for `REPLACE_ME` to find all of the fields to replace.


## Running the sample

After building your project, the localhost browser would pop up and 
you could see the card form.

If you're using your sandbox credentials, you can test a valid credit card
transaction by providing the following card information in the form:

* Card Number 4532 7597 3454 5858
* Card CVV 111
* Card Expiration (Any time in the future)
* Card Postal Code (Any valid US postal code)

**Note that if you are _not_ using your sandbox credentials and you enter _real_
credit card information, YOU WILL CHARGE THE CARD.**

After entering the card information, you could click `submit` button to get a 
nonce. Then click `charge` button to send the nonce and you would 
get the transaction repsonse body in an alert window.