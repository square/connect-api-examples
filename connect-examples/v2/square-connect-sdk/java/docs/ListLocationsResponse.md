
# ListLocationsResponse

### Description

Defines the fields that are included in the response body of a request to the ListLocations endpoint.  One of `errors` or `locations` is present in a given response (never both).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]
**locations** | [**List&lt;Location&gt;**](Location.md) | The business&#39;s locations. |  [optional]



