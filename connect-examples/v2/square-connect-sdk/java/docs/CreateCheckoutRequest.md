
# CreateCheckoutRequest

### Description

Defines the parameters that can be included in the body of a request to the CreateCheckout endpoint.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idempotencyKey** | **String** | A unique string that identifies this checkout among others you&#39;ve created. It can be any valid string but must be unique for every order sent to Square Checkout for a given location ID.  The idempotency key is used to avoid processing the same order more than once. If you&#39;re unsure whether a particular checkout was created successfully, you can reattempt it with the same idempotency key and all the same other parameters without worrying about creating duplicates.  We recommend using a random number/string generator native to the language you are working in to generate strings for your idempotency keys.  See [Idempotency](/basics/api101/idempotency) for more information. | 
**order** | [**CreateOrderRequest**](CreateOrderRequest.md) | The order including line items to be checked out. | 
**askForShippingAddress** | **Boolean** | If &#x60;true&#x60;, Square Checkout will collect shipping information on your behalf and store that information with the transaction information in your Square Dashboard.  Default: &#x60;false&#x60;. |  [optional]
**merchantSupportEmail** | **String** | The email address to display on the Square Checkout confirmation page and confirmation email that the buyer can use to contact the merchant.  If this value is not set, the confirmation page and email will display the primary email address associated with the merchant&#39;s Square account.  Default: none; only exists if explicitly set. |  [optional]
**prePopulateBuyerEmail** | **String** | If provided, the buyer&#39;s email is pre-populated on the checkout page as an editable text field.  Default: none; only exists if explicitly set. |  [optional]
**prePopulateShippingAddress** | [**Address**](Address.md) | If provided, the buyer&#39;s shipping info is pre-populated on the checkout page as editable text fields.  Default: none; only exists if explicitly set. |  [optional]
**redirectUrl** | **String** | The URL to redirect to after checkout is completed with &#x60;checkoutId&#x60;, Square&#39;s &#x60;orderId&#x60;, &#x60;transactionId&#x60;, and &#x60;referenceId&#x60; appended as URL parameters. For example, if the provided redirect_url is &#x60;http://www.example.com/order-complete&#x60;, a successful transaction redirects the customer to:  &#x60;http://www.example.com/order-complete?checkoutId&#x3D;xxxxxx&amp;orderId&#x3D;xxxxxx&amp;referenceId&#x3D;xxxxxx&amp;transactionId&#x3D;xxxxxx&#x60;  If you do not provide a redirect URL, Square Checkout will display an order confirmation page on your behalf; however Square strongly recommends that you provide a redirect URL so you can verify the transaction results and finalize the order through your existing/normal confirmation workflow.  Default: none; only exists if explicitly set. |  [optional]
**additionalRecipients** | [**List&lt;ChargeRequestAdditionalRecipient&gt;**](ChargeRequestAdditionalRecipient.md) | The basic primitive of multi-party transaction. The value is optional. The transaction facilitated by you can be split from here.  If you provide this value, the &#x60;amount_money&#x60; value in your additional_recipients must not be more than 90% of the &#x60;total_money&#x60; calculated by Square for your order. The &#x60;location_id&#x60; must be the valid location of the app owner merchant.  This field requires &#x60;PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS&#x60; OAuth permission.  This field is currently not supported in sandbox. |  [optional]
**note** | **String** | An optional note to associate with the checkout object.  This value cannot exceed 60 characters. |  [optional]



