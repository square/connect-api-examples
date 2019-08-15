
# OrderEntry

### Description

A lightweight description of an [Order](#type-order) that is returned when `returned_entries` is true on a [SearchOrderRequest](#type-searchorderrequest)

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**orderId** | **String** | The id of the Order |  [optional]
**version** | **Integer** | Version number which is incremented each time an update is committed to the order. Orders that were not created through the API will not include a version and thus cannot be updated.  [Read more about working with versions](/orders-api/manage-orders#update-orders). |  [optional]
**locationId** | **String** | The location id the Order belongs to. |  [optional]



