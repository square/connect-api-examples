
# CustomerFilter

### Description

Represents a set of [`CustomerQuery`](#type-customerquery) filters used to limit the set of Customers returned by [`SearchCustomers`](#endpoint-customers-seachcustomers).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**creationSource** | [**CustomerCreationSourceFilter**](CustomerCreationSourceFilter.md) | A filter to select customers based on their creation source. |  [optional]
**createdAt** | [**TimeRange**](TimeRange.md) | A filter to select customers based on when they were created. |  [optional]
**updatedAt** | [**TimeRange**](TimeRange.md) | A filter to select customers based on when they were updated. |  [optional]



