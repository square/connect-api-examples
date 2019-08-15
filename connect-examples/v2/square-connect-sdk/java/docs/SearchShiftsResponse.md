
# SearchShiftsResponse

### Description

The response to a request for `Shift` objects. Contains the requested `Shift` objects. May contain a set of `Error` objects if the request resulted in errors.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**shifts** | [**List&lt;Shift&gt;**](Shift.md) | Shifts |  [optional]
**cursor** | **String** | Opaque cursor for fetching the next page. |  [optional]
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]



