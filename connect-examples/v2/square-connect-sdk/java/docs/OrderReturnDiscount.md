
# OrderReturnDiscount

### Description

Represents a discount being returned that applies to one or more return line items in an order.  Fixed-amount, order-scoped discounts are distributed across all non-zero return line item totals. The amount distributed to each return line item is relative to that item’s contribution to the order subtotal.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique ID that identifies the return discount only within this order. |  [optional]
**sourceDiscountUid** | **String** | &#x60;uid&#x60; of the Discount from the Order which contains the original application of this discount. |  [optional]
**catalogObjectId** | **String** | The catalog object id referencing [CatalogDiscount](#type-catalogdiscount). |  [optional]
**name** | **String** | The discount&#39;s name. |  [optional]
**type** | **String** | The type of the discount. If it is created by API, it would be either &#x60;FIXED_PERCENTAGE&#x60; or &#x60;FIXED_AMOUNT&#x60;.  VARIABLE_* is not supported in API because the order is created at the time of sale and either percentage or amount has to be specified. See [OrderLineItemDiscountType](#type-orderlineitemdiscounttype) for possible values |  [optional]
**percentage** | **String** | The percentage of the tax, as a string representation of a decimal number. A value of &#x60;7.25&#x60; corresponds to a percentage of 7.25%.  The percentage won&#39;t be set for an amount-based discount. |  [optional]
**amountMoney** | [**Money**](Money.md) | The total declared monetary amount of the discount. The amount_money won&#39;t be set for a percentage-based discount. |  [optional]
**appliedMoney** | [**Money**](Money.md) | The amount of discount actually applied to this line item. When an amount-based discount is at order-level, this value is different from &#x60;amount_money&#x60; because the discount is distributed across the line items. |  [optional]
**scope** | **String** | Indicates the level at which the &#x60;OrderReturnDiscount&#x60; applies. For &#x60;ORDER&#x60; scoped discounts, the server will generate references in &#x60;applied_discounts&#x60; on all &#x60;OrderReturnLineItem&#x60;s. For &#x60;LINE_ITEM&#x60; scoped discounts, the discount will only apply to &#x60;OrderReturnLineItem&#x60;s with references in their &#x60;applied_discounts&#x60; field. See [OrderLineItemDiscountScope](#type-orderlineitemdiscountscope) for possible values |  [optional]



