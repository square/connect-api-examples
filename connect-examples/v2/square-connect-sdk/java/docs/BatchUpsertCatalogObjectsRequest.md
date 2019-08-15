
# BatchUpsertCatalogObjectsRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idempotencyKey** | **String** | A value you specify that uniquely identifies this request among all your requests. A common way to create a valid idempotency key is to use a Universally unique identifier (UUID).  If you&#39;re unsure whether a particular request was successful, you can reattempt it with the same idempotency key without worrying about creating duplicate objects.  See [Idempotency](/basics/api101/idempotency) for more information. | 
**batches** | [**List&lt;CatalogObjectBatch&gt;**](CatalogObjectBatch.md) | A batch of [CatalogObject](#type-catalogobject)s to be inserted/updated atomically. The objects within a batch will be inserted in an all-or-nothing fashion, i.e., if an error occurs attempting to insert or update an object within a batch, the entire batch will be rejected. However, an error in one batch will not affect other batches within the same request.  For each object, its &#x60;updated_at&#x60; field is ignored and replaced with a current [timestamp](#workingwithdates), and its &#x60;is_deleted&#x60; field must not be set to &#x60;true&#x60;.  To modify an existing object, supply its ID. To create a new object, use an ID starting with &#x60;#&#x60;. These IDs may be used to create relationships between an object and attributes of other objects that reference it. For example, you can create a [CatalogItem](#type-catalogitem) with ID &#x60;#ABC&#x60; and a [CatalogItemVariation](#type-catalogitemvariation) with its &#x60;item_id&#x60; attribute set to &#x60;#ABC&#x60; in order to associate the [CatalogItemVariation](#type-catalogitemvariation) with its parent [CatalogItem](#type-catalogitem).  Any &#x60;#&#x60;-prefixed IDs are valid only within a single atomic batch, and will be replaced by server-generated IDs.  Each batch may contain up to 1,000 objects. The total number of objects across all batches for a single request may not exceed 10,000. If either of these limits is violated, an error will be returned and no objects will be inserted or updated. |  [optional]



