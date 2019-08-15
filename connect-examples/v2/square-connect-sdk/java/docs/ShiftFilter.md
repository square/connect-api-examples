
# ShiftFilter

### Description

Defines a filter used in a search for `Shift` records. `AND` logic is used by Square's servers to apply each filter property specified.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**locationId** | **List&lt;String&gt;** | Fetch shifts for the specified location. |  [optional]
**employeeId** | **List&lt;String&gt;** | Fetch shifts for the specified employee. |  [optional]
**status** | **String** | Fetch a &#x60;Shift&#x60; instance by &#x60;Shift.status&#x60;. See [ShiftFilterStatus](#type-shiftfilterstatus) for possible values |  [optional]
**start** | [**TimeRange**](TimeRange.md) | Fetch &#x60;Shift&#x60;s that start in the time range - Inclusive. |  [optional]
**end** | [**TimeRange**](TimeRange.md) | Fetch the &#x60;Shift&#x60;s that end in the time range - Inclusive. |  [optional]
**workday** | [**ShiftWorkday**](ShiftWorkday.md) | Fetch the &#x60;Shift&#x60;s based on workday date range. |  [optional]



