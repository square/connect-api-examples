
# CatalogObject

### Description

The wrapper object for object types in the Catalog data model. The type of a particular `CatalogObject` is determined by the value of `type` and only the corresponding data field may be set.  - if type = `ITEM`, only `item_data` will be populated and it will contain a valid [CatalogItem](#type-catalogitem) object. - if type = `ITEM_VARIATION`, only `item_variation_data` will be populated and it will contain a valid [CatalogItemVariation](#type-catalogitemvariation) object. - if type = `MODIFIER`, only `modifier_data` will be populated and it will contain a valid [CatalogModifier](#type-catalogmodifier) object. - if type = `MODIFIER_LIST`, only `modifier_list_data` will be populated and it will contain a valid [CatalogModifierList](#type-catalogmodifierlist) object. - if type = `CATEGORY`, only `category_data` will be populated and it will contain a valid [CatalogCategory](#type-catalogcategory) object. - if type = `DISCOUNT`, only `discount_data` will be populated and it will contain a valid [CatalogDiscount](#type-catalogdiscount) object. - if type = `TAX`, only `tax_data` will be populated and it will contain a valid [CatalogTax](#type-catalogtax) object. - if type = `IMAGE`, only `image_data` will be populated and it will contain a valid [CatalogImage](#type-catalogimage) object.  For a more detailed discussion of the Catalog data model, please see the [Design a Catalog](/catalog-api/design-a-catalog) guide.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**type** | **String** | The type of this object. Each object type has expected properties expressed in a structured format within its corresponding &#x60;*_data&#x60; field below. See [CatalogObjectType](#type-catalogobjecttype) for possible values | 
**id** | **String** | An identifier to reference this object in the catalog. When a new CatalogObject is inserted, the client should set the id to a temporary identifier starting with a &#x60;&#39;#&#39;&#x60; character. Other objects being inserted or updated within the same request may use this identifier to refer to the new object.  When the server receives the new object, it will supply a unique identifier that replaces the temporary identifier for all future references. | 
**updatedAt** | **String** | Last modification [timestamp](#workingwithdates) in RFC 3339 format, e.g., &#x60;\&quot;2016-08-15T23:59:33.123Z\&quot;&#x60; would indicate the UTC time (denoted by &#x60;Z&#x60;) of August 15, 2016 at 23:59:33 and 123 milliseconds. |  [optional]
**version** | **Long** | The version of the object. When updating an object, the version supplied must match the version in the database, otherwise the write will be rejected as conflicting. |  [optional]
**isDeleted** | **Boolean** | If &#x60;true&#x60;, the object has been deleted from the database. Must be &#x60;false&#x60; for new objects being inserted. When deleted, the &#x60;updated_at&#x60; field will equal the deletion time. |  [optional]
**catalogV1Ids** | [**List&lt;CatalogV1Id&gt;**](CatalogV1Id.md) | The Connect V1 IDs for this object at each [location](#type-location) where it is present, where they differ from the object&#39;s Connect V2 ID. The field will only be present for objects that have been created or modified by legacy APIs. |  [optional]
**presentAtAllLocations** | **Boolean** | If &#x60;true&#x60;, this object is present at all locations (including future locations), except where specified in the &#x60;absent_at_location_ids&#x60; field. If &#x60;false&#x60;, this object is not present at any locations (including future locations), except where specified in the &#x60;present_at_location_ids&#x60; field. If not specified, defaults to &#x60;true&#x60;. |  [optional]
**presentAtLocationIds** | **List&lt;String&gt;** | A list of locations where the object is present, even if &#x60;present_at_all_locations&#x60; is &#x60;false&#x60;. |  [optional]
**absentAtLocationIds** | **List&lt;String&gt;** | A list of locations where the object is not present, even if &#x60;present_at_all_locations&#x60; is &#x60;true&#x60;. |  [optional]
**imageId** | **String** | Identifies the &#x60;CatalogImage&#x60; attached to this &#x60;CatalogObject&#x60;. |  [optional]
**itemData** | [**CatalogItem**](CatalogItem.md) | Structured data for a [CatalogItem](#type-catalogitem), set for CatalogObjects of type &#x60;ITEM&#x60;. |  [optional]
**categoryData** | [**CatalogCategory**](CatalogCategory.md) | Structured data for a [CatalogCategory](#type-catalogcategory), set for CatalogObjects of type &#x60;CATEGORY&#x60;. |  [optional]
**itemVariationData** | [**CatalogItemVariation**](CatalogItemVariation.md) | Structured data for a [CatalogItemVariation](#type-catalogitemvariation), set for CatalogObjects of type &#x60;ITEM_VARIATION&#x60;. |  [optional]
**taxData** | [**CatalogTax**](CatalogTax.md) | Structured data for a [CatalogTax](#type-catalogtax), set for CatalogObjects of type &#x60;TAX&#x60;. |  [optional]
**discountData** | [**CatalogDiscount**](CatalogDiscount.md) | Structured data for a [CatalogDiscount](#type-catalogdiscount), set for CatalogObjects of type &#x60;DISCOUNT&#x60;. |  [optional]
**modifierListData** | [**CatalogModifierList**](CatalogModifierList.md) | Structured data for a [CatalogModifierList](#type-catalogmodifierlist), set for CatalogObjects of type &#x60;MODIFIER_LIST&#x60;. |  [optional]
**modifierData** | [**CatalogModifier**](CatalogModifier.md) | Structured data for a [CatalogModifier](#type-catalogmodifier), set for CatalogObjects of type &#x60;MODIFIER&#x60;. |  [optional]
**timePeriodData** | [**CatalogTimePeriod**](CatalogTimePeriod.md) | Structured data for a [CatalogTimePeriod](#type-catalogtimeperiod), set for CatalogObjects of type &#x60;TIME_PERIOD&#x60;. |  [optional]
**productSetData** | [**CatalogProductSet**](CatalogProductSet.md) | Structured data for a [CatalogProductSet](#type-catalogproductset), set for CatalogObjects of type &#x60;PRODUCT_SET&#x60;. |  [optional]
**pricingRuleData** | [**CatalogPricingRule**](CatalogPricingRule.md) | Structured data for a [CatalogPricingRule](#type-catalogpricingrule), set for CatalogObjects of type &#x60;PRICING_RULE&#x60;. |  [optional]
**imageData** | [**CatalogImage**](CatalogImage.md) | Structured data for a [CatalogImage](#type-catalogimage), set for CatalogObjects of type &#x60;IMAGE&#x60;. |  [optional]
**measurementUnitData** | [**CatalogMeasurementUnit**](CatalogMeasurementUnit.md) | Structured data for a [CatalogMeasurementUnit](#type-catalogmeasurementunit), set for CatalogObjects of type &#x60;MEASUREMENT_UNIT&#x60;. |  [optional]
**itemOptionData** | [**CatalogItemOption**](CatalogItemOption.md) | Structured data for a [CatalogItemOption](#type-catalogitemoption), set for CatalogObjects of type &#x60;ITEM_OPTION&#x60;. |  [optional]
**itemOptionValueData** | [**CatalogItemOptionValue**](CatalogItemOptionValue.md) | Structured data for a [CatalogItemOptionValue](#type-catalogitemoptionvalue), set for CatalogObjects of type &#x60;ITEM_OPTION_VAL&#x60;. |  [optional]



