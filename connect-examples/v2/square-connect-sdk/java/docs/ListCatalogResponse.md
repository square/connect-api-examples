
# ListCatalogResponse

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | The set of [Error](#type-error)s encountered. |  [optional]
**cursor** | **String** | The pagination cursor to be used in a subsequent request. If unset, this is the final response. See [Pagination](/basics/api101/pagination) for more information. |  [optional]
**objects** | [**List&lt;CatalogObject&gt;**](CatalogObject.md) | The [CatalogObject](#type-catalogobject)s returned. |  [optional]



