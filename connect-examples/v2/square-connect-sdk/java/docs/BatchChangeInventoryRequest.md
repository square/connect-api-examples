
# BatchChangeInventoryRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idempotencyKey** | **String** | A client-supplied, universally unique identifier (UUID) for the request.  See [Idempotency](/basics/api101/idempotency) in the [API Development 101](/basics/api101/overview) section for more information. |  [optional]
**changes** | [**List&lt;InventoryChange&gt;**](InventoryChange.md) | The set of physical counts and inventory adjustments to be made. Changes are applied based on the client-supplied timestamp and may be sent out of order. Max size is 100 changes. |  [optional]
**ignoreUnchangedCounts** | **Boolean** | Indicates whether the current physical count should be ignored if the quantity is unchanged since the last physical count. Default: &#x60;true&#x60;. |  [optional]



