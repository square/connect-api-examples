
# CatalogProductSet

### Description

Represents a collection of catalog objects for the purpose of applying a [PricingRule](#type-pricingrule). Including a catalog object will include all of its subtypes. For example, including a category in a product set will include all of its items and associated item variations in the product set. Including an item in a product set will also include its item variations.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  User-defined name for the product set. For example, \&quot;Clearance Items\&quot; or \&quot;Winter Sale Items\&quot;. |  [optional]
**productIdsAny** | **List&lt;String&gt;** | Unique IDs for any [CatalogObjects](#type-catalogobject)s to include in this product set. Any number of these catalog objects can be in an order for a pricing rule to apply.  This can be used with &#x60;product_ids_all&#x60; in a parent [CatalogProductSet](#type-catalogproductset) to match groups of products for a bulk discount, such as a discount for an entree and side combo.  Only one of &#x60;product_ids_all&#x60;, &#x60;product_ids_any&#x60;, or &#x60;all_products&#x60; can be set.  Max: 500 catalog object IDs. |  [optional]
**productIdsAll** | **List&lt;String&gt;** | Unique IDs for [CatalogObjects](#type-catalogobject) to include in this product set. All objects in this set must be included in an order for a pricing rule to apply.  Only one of &#x60;product_ids_all&#x60;, &#x60;product_ids_any&#x60;, or &#x60;all_products&#x60; can be set.  Max: 500 catalog object IDs. |  [optional]
**quantityExact** | **Long** | If set, there must be exactly this many items from &#x60;products_any&#x60; or &#x60;products_all&#x60; in the cart for the discount to apply.  Cannot be combined with either &#x60;quantity_min&#x60; or &#x60;quantity_max&#x60;. |  [optional]
**quantityMin** | **Long** | If set, there must be at least this many items from &#x60;products_any&#x60; or &#x60;products_all&#x60; in a cart for the discount to apply. See &#x60;quantity_exact&#x60;. Defaults to 0 if &#x60;quantity_exact&#x60;, &#x60;quantity_min&#x60; and &#x60;quantity_max&#x60; are all unspecified. |  [optional]
**quantityMax** | **Long** | If set, the pricing rule will apply to a maximum of this many items from &#x60;products_any&#x60; or &#x60;products_all&#x60;. |  [optional]
**allProducts** | **Boolean** | If set to &#x60;true&#x60;, the product set will include every item in the catalog.  Only one of &#x60;product_ids_all&#x60;, &#x60;product_ids_any&#x60;, or &#x60;all_products&#x60; can be set. |  [optional]



