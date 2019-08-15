
# ListWorkweekConfigsResponse

### Description

The response to a request for a set of `WorkweekConfig` objects. Contains the requested `WorkweekConfig` objects. May contain a set of `Error` objects if the request resulted in errors.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**workweekConfigs** | [**List&lt;WorkweekConfig&gt;**](WorkweekConfig.md) | A page of Employee Wage results. |  [optional]
**cursor** | **String** | Value supplied in the subsequent request to fetch the next page of Employee Wage results. |  [optional]
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]



