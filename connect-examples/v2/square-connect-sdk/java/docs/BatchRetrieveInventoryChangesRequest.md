
# BatchRetrieveInventoryChangesRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**catalogObjectIds** | **List&lt;String&gt;** | Filters results by [CatalogObject](#type-catalogobject) ID. Only applied when set. Default: unset. |  [optional]
**locationIds** | **List&lt;String&gt;** | Filters results by [Location](#type-location) ID. Only applied when set. Default: unset. |  [optional]
**types** | **List&lt;String&gt;** | Filters results by [InventoryChangeType](#type-inventorychangetype). Default: [&#x60;PHYSICAL_COUNT&#x60;, &#x60;ADJUSTMENT&#x60;]. &#x60;TRANSFER&#x60; is not supported as a filter. See [InventoryChangeType](#type-inventorychangetype) for possible values |  [optional]
**states** | **List&lt;String&gt;** | Filters &#x60;ADJUSTMENT&#x60; query results by [InventoryState](#type-inventorystate). Only applied when set. Default: unset. See [InventoryState](#type-inventorystate) for possible values |  [optional]
**updatedAfter** | **String** | Provided as an RFC 3339 timestamp. Returns results whose &#x60;created_at&#x60; or &#x60;calculated_at&#x60; value is after the given time. Default: UNIX epoch (&#x60;1970-01-01T00:00:00Z&#x60;). |  [optional]
**updatedBefore** | **String** | Provided as an RFC 3339 timestamp. Returns results whose &#x60;created_at&#x60; or &#x60;calculated_at&#x60; value is strictly before the given time. Default: UNIX epoch (&#x60;1970-01-01T00:00:00Z&#x60;). |  [optional]
**cursor** | **String** | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. |  [optional]



