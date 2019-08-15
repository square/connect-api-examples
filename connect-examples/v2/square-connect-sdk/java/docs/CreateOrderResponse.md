
# CreateOrderResponse

### Description

Defines the fields that are included in the response body of a request to the CreateOrder endpoint.  One of `errors` or `order` is present in a given response (never both).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**order** | [**Order**](Order.md) | The newly created order. |  [optional]
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]



