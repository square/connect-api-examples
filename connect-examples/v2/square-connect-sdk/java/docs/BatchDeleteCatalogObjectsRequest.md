
# BatchDeleteCatalogObjectsRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**objectIds** | **List&lt;String&gt;** | The IDs of the [CatalogObject](#type-catalogobject)s to be deleted. When an object is deleted, other objects in the graph that depend on that object will be deleted as well (for example, deleting a [CatalogItem](#type-catalogitem) will delete its [CatalogItemVariation](#type-catalogitemvariation)s). |  [optional]



