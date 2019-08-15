
# OrderQuantityUnit

### Description

Contains the measurement unit for a quantity and a precision which specifies the number of digits after the decimal point for decimal quantities.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**measurementUnit** | [**MeasurementUnit**](MeasurementUnit.md) | A [MeasurementUnit](#type-measurementunit) that represents the unit of measure for the quantity. |  [optional]
**precision** | **Integer** | For non-integer quantities, represents the number of digits after the decimal point that are recorded for this quantity.  For example, a precision of 1 allows quantities like &#x60;\&quot;1.0\&quot;&#x60; and &#x60;\&quot;1.1\&quot;&#x60;, but not &#x60;\&quot;1.01\&quot;&#x60;.  Min: 0. Max: 5. |  [optional]



