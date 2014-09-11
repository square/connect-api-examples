# Terminal API

Square's *Terminal API* enables third-party applications to accept payments through Square's iOS and Android apps. To request a payment through Square, a third-party app constructs a [URL](http://www.ietf.org/rfc/rfc3986.txt) following this template:

    square://terminal/2.0/pay?{query}

Query parameters are encoded using this [syntax](http://www.ietf.org/rfc/rfc2234.txt): 

    query     = parameter *( "&" parameter )
    parameter = name "=" value

Valid parameter names are specified below. Parameter values _must_ be converted to UTF-8 bytes and then percent-encoded.

For example, an app that requests $0.02 for "Advice" might use the following URL:

    square://terminal/1.0/pay?amount=2&currency_code=USD&description=Advice&app_id=2HhoPus5
    
How an application invokes this URL depends on the platform. iOS applications call [`openURL:`][docs-ios]. Android applications use [`startActivity()`][docs-android]. A web-based app might set [`window.location`][docs-web].

[docs-ios]: http://developer.apple.com/library/ios/#DOCUMENTATION/UIKit/Reference/UIApplication_Class/Reference/Reference.html#//apple_ref/occ/instm/UIApplication/openURL:
[docs-android]: http://developer.android.com/reference/android/content/Context.html#startActivity(android.content.Intent)
[docs-web]: http://www.whatwg.org/specs/web-apps/current-work/multipage/history.html#dom-location


## Parameters

- `amount`
    - Type: string (of an integer number)
    - Required: no
    - Description: Amount of money to pay; always specify an integer representing the money amount scaled by the default number of decimal places for the currency. For example, 100 would be 100 cents in USD, or $1.
    - Example: Since U.S. Dollars (`USD`) have fractional components for cents, pass `199` to indicate $1.99. Yen (`JPY`) have no fractional component; pass `199` to indicate 199 yen.

- `currency_code`
    - Type: string
    - Required: if amount is specified
    - Description: The currency of `amount`. Three letter ISO 4217 currency code.
    - Currently supported:
        - `USD`: U.S. Dollar; specify `amount` in cents.

- `app_id`
    - Type: string
    - Required: yes
    - Description: Unique identifier provided by Square.

- `callback`
    - Type: string
    - Required: yes
    - Description: URL Square will redirect back to when the payment completes. Square inserts query parameters into the callback URL. Query parameter names starting with `square_` are reserved for usage by Square. 
    - Parameters Square inserts:
        - `square_errors`
            - Description: Comma-separated list of error codes, see [Error Codes](#error-codes) for the possible values. Present when `square_status` is `error`.
        - `square_payment_id`
            - Description: Uniquely identifies a successful payment. Present when `square_status` is `successful`
        - `square_reference_id`
            - Description: Reference ID provided by the API client in the original URL. Present when `reference_id` is set. 
        - `square_status`
            - Possible values: `successful`, `cancelled`, or `error`
            - The `cancelled` status is returned when the payment is cleared, or when the the transaction is voided from the signature screen.
        - `square_amount`
           - Actual amount attempted. May be different from the amount passed, if the user edits the amount, or if adjustments such as taxes or tips are applied.
        - `square_currency_code`
           - ISO 4217 currency code for the transaction
    - Example: If an application intends to pass `http://website.com/return_from_square.php` as the `callback`, it should make sure to percent encode the value as `http%3A%2F%2Fwebsite.com%2Freturn_from_square.php`. Square might redirect back to  `http://website.com/return_from_square.php?square_status=successful&square_payment_id=XoVqtBmv&square_amount=1525&square_currency_code=USD`.

- `default_email`
    - Type: string
    - Required: no
    - Description: Specifies a default email address to send a receipt to. If the user chooses to email themselves a receipt Square will pre-fill the email field with this value. The value should conform to [RFC 2822](http://tools.ietf.org/html/rfc2822#section-3.4.1).
    - Example: `jack@my.domain`

- `default_phone`
    - Type: string
    - Required: no
    - Description: Specifies a default phone number to text message a receipt to. If the user chooses to text message themselves a receipt Square will pre-fill the phone number field with this value. Includes the area code. U.S. numbers only at this time.
    - Example: `4155551234`

- `description`
    - Type: string
    - Required: no
    - Description: Describes the payment. 140 characters or fewer. 
    - Example: `Charitable donation`

- `reference_id`
    - Type: string (256 characters or fewer)
    - Required: no
    - Description: Used by the API client to uniquely identify a payment. Inserted into `callback` URL.


## <span id="error-codes">Error Codes</span>

All error codes are passed back in the `callback` as specified above.

### Incomplete Request Errors
These errors are returned when a request is missing a required field.

- `app_id_missing`
- `currency_code_missing`

### Application Not Ready
These errors are returned when a request is well formed, but the Square application cannot process a transaction

- `not_logged_in`
    - The Square application is installed, but the device user has not logged in.
- `user_not_active`
    - The user is logged in, but is not approved for credit card processing.
- `currency_code_mismatch`
    - The currency for the current Square user does not match the currency of the requested transaction.


### Invalid Value Errors
These errors are returned when a given field is populated with an invalid value.

- `amount_invalid_format`
	- Indicates that the value specified in `amount` had an invalid format. This can occur when missing the required number of decimal places. This will depend on the `currency_code`; for example, passing in `15.00` and `USD` would generate an `amount_invalid_format` error because `USD` should be cent in integer cents.

- `amount_too_large`
	- Indicates that the value specified in `amount` is above Square's maximum transactable amount. This will depend on the `currency_code`; for `USD`, the maximum value is `999999999`.

- `amount_too_small`
	- Indicates that the value specified in `amount` is below Square's minimum transactable amount. This will depend on the `currency_code`; for `USD`, the minimum value is `100`.

- `app_id_invalid`
    - Indicates that the `app_id` supplied is not valid. Ensure that this is the correct identifier from Square.

- `currency_not_supported`
	- Indicates that the currency is not currently accepted by Square. Currently, only `USD` is supported.

These errors are returned when a given field's length exceeds its limit.

- `description_too_long`
- `reference_id_too_long`
