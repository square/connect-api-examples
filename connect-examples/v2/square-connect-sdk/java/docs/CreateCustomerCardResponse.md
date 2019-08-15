
# CreateCustomerCardResponse

### Description

Defines the fields that are included in the response body of a request to the CreateCustomerCard endpoint.  One of `errors` or `card` is present in a given response (never both).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]
**card** | [**Card**](Card.md) | The created card on file. |  [optional]



