
# BatchRetrieveInventoryChangesResponse

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]
**changes** | [**List&lt;InventoryChange&gt;**](InventoryChange.md) | The current calculated inventory changes for the requested objects and locations. |  [optional]
**cursor** | **String** | The pagination cursor to be used in a subsequent request. If unset, this is the final response. See [Pagination](/basics/api101/pagination) for more information. |  [optional]



