
# OrderReturnLineItem

### Description

The line item being returned in an Order.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique identifier for this return line item entry. |  [optional]
**sourceLineItemUid** | **String** | &#x60;uid&#x60; of the LineItem in the original sale Order. |  [optional]
**name** | **String** | The name of the line item. |  [optional]
**quantity** | **String** | The quantity returned, formatted as a decimal number. For example: &#x60;\&quot;3\&quot;&#x60;.  Line items with a &#x60;quantity_unit&#x60; can have non-integer quantities. For example: &#x60;\&quot;1.70000\&quot;&#x60;. | 
**quantityUnit** | [**OrderQuantityUnit**](OrderQuantityUnit.md) | The unit and precision that this return line item&#39;s quantity is measured in. |  [optional]
**note** | **String** | The note of the returned line item. |  [optional]
**catalogObjectId** | **String** | The [CatalogItemVariation](#type-catalogitemvariation) id applied to this returned line item. |  [optional]
**variationName** | **String** | The name of the variation applied to this returned line item. |  [optional]
**returnModifiers** | [**List&lt;OrderReturnLineItemModifier&gt;**](OrderReturnLineItemModifier.md) | The [CatalogModifier](#type-catalogmodifier)s applied to this line item. |  [optional]
**returnTaxes** | [**List&lt;OrderReturnTax&gt;**](OrderReturnTax.md) | A list of taxes applied to this line item. On read or retrieve, this list includes both item-level taxes and any return-level taxes apportioned to this item.  This field has been deprecated in favour of &#x60;applied_taxes&#x60;. |  [optional]
**returnDiscounts** | [**List&lt;OrderReturnDiscount&gt;**](OrderReturnDiscount.md) | A list of discounts applied to this line item. On read or retrieve, this list includes both item-level discounts and any return-level discounts apportioned to this item.  This field has been deprecated in favour of &#x60;applied_discounts&#x60;. |  [optional]
**appliedTaxes** | [**List&lt;OrderLineItemAppliedTax&gt;**](OrderLineItemAppliedTax.md) | The list of references to &#x60;OrderReturnTax&#x60; entities applied to the returned line item. Each &#x60;OrderLineItemAppliedTax&#x60; has a &#x60;tax_uid&#x60; that references the &#x60;uid&#x60; of a top-level &#x60;OrderReturnTax&#x60; applied to the returned line item. On reads, the amount applied is populated. |  [optional]
**appliedDiscounts** | [**List&lt;OrderLineItemAppliedDiscount&gt;**](OrderLineItemAppliedDiscount.md) | The list of references to &#x60;OrderReturnDiscount&#x60; entities applied to the returned line item. Each &#x60;OrderLineItemAppliedDiscount&#x60; has a &#x60;discount_uid&#x60; that references the &#x60;uid&#x60; of a top-level &#x60;OrderReturnDiscount&#x60; applied to the returned line item. On reads, the amount applied is populated. |  [optional]
**basePriceMoney** | [**Money**](Money.md) | The base price for a single unit of the line item. |  [optional]
**variationTotalPriceMoney** | [**Money**](Money.md) | The total price of all item variations returned in this line item. Calculated as &#x60;base_price_money&#x60; multiplied by &#x60;quantity&#x60;. Does not include modifiers. |  [optional]
**grossReturnMoney** | [**Money**](Money.md) | The gross return amount of money calculated as (item base price + modifiers price) * quantity. |  [optional]
**totalTaxMoney** | [**Money**](Money.md) | The total tax amount of money to return for the line item. |  [optional]
**totalDiscountMoney** | [**Money**](Money.md) | The total discount amount of money to return for the line item. |  [optional]
**totalMoney** | [**Money**](Money.md) | The total amount of money to return for this line item. |  [optional]



