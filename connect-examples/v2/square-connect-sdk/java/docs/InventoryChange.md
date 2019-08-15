
# InventoryChange

### Description

Represents a single physical count, inventory, adjustment, or transfer that is part of the history of inventory changes for a particular [CatalogObject](#type-catalogobject).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **String** | Indicates how the inventory change was applied. See [InventoryChangeType](#type-inventorychangetype) for possible values |  [optional]
**physicalCount** | [**InventoryPhysicalCount**](InventoryPhysicalCount.md) | Contains details about the physical count when &#x60;type&#x60; is &#x60;PHYSICAL_COUNT&#x60; and unset for all other types. |  [optional]
**adjustment** | [**InventoryAdjustment**](InventoryAdjustment.md) | Contains details about the inventory adjustment when &#x60;type&#x60; is &#x60;ADJUSTMENT&#x60; and unset for all other types. |  [optional]
**transfer** | [**InventoryTransfer**](InventoryTransfer.md) | Contains details about the inventory transfer when &#x60;type&#x60; is &#x60;TRANSFER&#x60; and unset for all other types. |  [optional]



