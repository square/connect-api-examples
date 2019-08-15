
# CreateCheckoutResponse

### Description

Defines the fields that are included in the response body of a request to the CreateCheckout endpoint.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**checkout** | [**Checkout**](Checkout.md) | The newly created checkout. If the same request was made with the same idempotency_key, this will be the checkout created with the idempotency_key. |  [optional]
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]



