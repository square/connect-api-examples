
# BatchDeleteCatalogObjectsResponse

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | The set of [Error](#type-error)s encountered. |  [optional]
**deletedObjectIds** | **List&lt;String&gt;** | The IDs of all [CatalogObject](#type-catalogobject)s deleted by this request. |  [optional]
**deletedAt** | **String** | The database [timestamp](#workingwithdates) of this deletion in RFC 3339 format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]



