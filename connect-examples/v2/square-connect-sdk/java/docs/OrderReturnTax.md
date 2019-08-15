
# OrderReturnTax

### Description

Represents a tax being returned that applies to one or more return line items in an order.  Fixed-amount, order-scoped taxes are distributed across all non-zero return line item totals. The amount distributed to each return line item is relative to that item’s contribution to the order subtotal.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique ID that identifies the return tax only within this order. |  [optional]
**sourceTaxUid** | **String** | &#x60;uid&#x60; of the Tax from the Order which contains the original charge of this tax. |  [optional]
**catalogObjectId** | **String** | The catalog object id referencing [CatalogTax](#type-catalogtax). |  [optional]
**name** | **String** | The tax&#39;s name. |  [optional]
**type** | **String** | Indicates the calculation method used to apply the tax. See [OrderLineItemTaxType](#type-orderlineitemtaxtype) for possible values |  [optional]
**percentage** | **String** | The percentage of the tax, as a string representation of a decimal number. For example, a value of &#x60;\&quot;7.25\&quot;&#x60; corresponds to a percentage of 7.25%. |  [optional]
**appliedMoney** | [**Money**](Money.md) | The amount of the money applied by the tax in an order. |  [optional]
**scope** | **String** | Indicates the level at which the &#x60;OrderReturnTax&#x60; applies. For &#x60;ORDER&#x60; scoped taxes, Square generates references in &#x60;applied_taxes&#x60; on all &#x60;OrderReturnLineItem&#x60;s. For &#x60;LINE_ITEM&#x60; scoped taxes, the tax will only apply to &#x60;OrderReturnLineItem&#x60;s with references in their &#x60;applied_discounts&#x60; field. See [OrderLineItemTaxScope](#type-orderlineitemtaxscope) for possible values |  [optional]



