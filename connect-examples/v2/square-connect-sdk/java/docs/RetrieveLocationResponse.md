
# RetrieveLocationResponse

### Description

Defines the fields that are included in the response body of a request to the [RetrieveLocation](#endpoint-retrievelocation) endpoint.  One of `errors` or `location` is present in a given response (never both).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]
**location** | [**Location**](Location.md) | The requested location. |  [optional]



