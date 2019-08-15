
# V1PageCell

### Description

V1PageCell

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**pageId** | **String** | The unique identifier of the page the cell is included on. |  [optional]
**row** | **Integer** | The row of the cell. Always an integer between 0 and 4, inclusive. |  [optional]
**column** | **Integer** | The column of the cell. Always an integer between 0 and 4, inclusive. |  [optional]
**objectType** | **String** | The type of entity represented in the cell (ITEM, DISCOUNT, CATEGORY, or PLACEHOLDER). See [V1PageCellObjectType](#type-v1pagecellobjecttype) for possible values |  [optional]
**objectId** | **String** | The unique identifier of the entity represented in the cell. Not present for cells with an object_type of PLACEHOLDER. |  [optional]
**placeholderType** | **String** | For a cell with an object_type of PLACEHOLDER, this value indicates the cell&#39;s special behavior. See [V1PageCellPlaceholderType](#type-v1pagecellplaceholdertype) for possible values |  [optional]



