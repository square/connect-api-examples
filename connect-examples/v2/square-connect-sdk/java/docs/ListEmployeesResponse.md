
# ListEmployeesResponse

### Description

Defines the fields that are included in the response body of a request to the ListEmployees endpoint.  One of `errors` or `employees` is present in a given response (never both).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**employees** | [**List&lt;Employee&gt;**](Employee.md) | List of employees returned from the request. |  [optional]
**cursor** | **String** | The token to be used to retrieve the next page of results. |  [optional]
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]



