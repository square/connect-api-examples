
# InventoryCount

### Description

Represents Square's estimated quantity of items in a particular state at a particular location based on the known history of physical counts and inventory adjustments

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**catalogObjectId** | **String** | The Square generated ID of the [CatalogObject](#type-catalogobject) being tracked. |  [optional]
**catalogObjectType** | **String** | The [CatalogObjectType](#type-catalogobjecttype) of the [CatalogObject](#type-catalogobject) being tracked. Tracking is only supported for the &#x60;ITEM_VARIATION&#x60; type. |  [optional]
**state** | **String** | The current [InventoryState](#type-inventorystate) for the related quantity of items. See [InventoryState](#type-inventorystate) for possible values |  [optional]
**locationId** | **String** | The Square ID of the [Location](#type-location) where the related quantity of items are being tracked. |  [optional]
**quantity** | **String** | The number of items in the count as a decimal string. Can support up to 5 digits after the decimal point.  _Important_: The Point of Sale app and Dashboard do not currently support decimal quantities. If a Point of Sale app or Dashboard attempts to read a decimal quantity on inventory counts or adjustments, the quantity will be rounded down to the nearest integer. For example, &#x60;2.5&#x60; will become &#x60;2&#x60;, and &#x60;-2.5&#x60; will become &#x60;-3&#x60;.  Read [Decimal Quantities (BETA)](/orders-api/what-it-does#decimal-quantities) for more information. |  [optional]
**calculatedAt** | **String** | A read-only timestamp in RFC 3339 format that indicates when Square received the most recent physical count or adjustment that had an affect on the estimated count. |  [optional]



