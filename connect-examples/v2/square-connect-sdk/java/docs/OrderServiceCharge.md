
# OrderServiceCharge

### Description

Represents a service charge applied to an order.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique ID that identifies the service charge only within this order. |  [optional]
**name** | **String** | The name of the service charge. |  [optional]
**catalogObjectId** | **String** | The catalog object ID referencing the service charge [CatalogObject](#type-catalogobject). |  [optional]
**percentage** | **String** | The service charge percentage as a string representation of a decimal number. For example, &#x60;\&quot;7.25\&quot;&#x60; indicates a service charge of 7.25%.  Exactly 1 of &#x60;percentage&#x60; or &#x60;amount_money&#x60; should be set. |  [optional]
**amountMoney** | [**Money**](Money.md) | The amount of a non-percentage based service charge.  Exactly one of &#x60;percentage&#x60; or &#x60;amount_money&#x60; should be set. |  [optional]
**appliedMoney** | [**Money**](Money.md) | The amount of money applied to the order by the service charge, including any inclusive tax amounts, as calculated by Square.  - For fixed-amount service charges, &#x60;applied_money&#x60; is equal to &#x60;amount_money&#x60;. - For percentage-based service charges, &#x60;applied_money&#x60; is the money calculated using the percentage. |  [optional]
**totalMoney** | [**Money**](Money.md) | The total amount of money to collect for the service charge.  __Note__: if an inclusive tax is applied to the service charge, &#x60;total_money&#x60; __does not__ equal &#x60;applied_money&#x60; plus &#x60;total_tax_money&#x60; since the inclusive tax amount will already be included in both &#x60;applied_money&#x60; and &#x60;total_tax_money&#x60;. |  [optional]
**totalTaxMoney** | [**Money**](Money.md) | The total amount of tax money to collect for the service charge. |  [optional]
**calculationPhase** | **String** | The calculation phase at which to apply the service charge. See [OrderServiceChargeCalculationPhase](#type-orderservicechargecalculationphase) for possible values |  [optional]
**taxable** | **Boolean** | Indicates whether the service charge can be taxed. If set to &#x60;true&#x60;, order-level taxes automatically apply to the service charge. Note that service charges calculated in the &#x60;TOTAL_PHASE&#x60; cannot be marked as taxable. |  [optional]
**taxes** | [**List&lt;OrderLineItemTax&gt;**](OrderLineItemTax.md) | A list of taxes applied to this service charge. On read or retrieve, this list includes both item-level taxes and any order-level taxes apportioned to this service charge. When creating an Order, set your service charge-level taxes in this list. By default, order-level taxes apply to service charges calculated in the &#x60;SUBTOTAL_PHASE&#x60; if &#x60;taxable&#x60; is set to &#x60;true&#x60;.  This field has been deprecated in favour of &#x60;applied_taxes&#x60;. Usage of both this field and &#x60;applied_taxes&#x60; when creating an order will result in an error. Usage of this field when sending requests to the UpdateOrder endpoint will result in an error. |  [optional]
**appliedTaxes** | [**List&lt;OrderLineItemAppliedTax&gt;**](OrderLineItemAppliedTax.md) | The list of references to taxes applied to this service charge. Each &#x60;OrderLineItemAppliedTax&#x60; has a &#x60;tax_uid&#x60; that references the &#x60;uid&#x60; of a top-level &#x60;OrderLineItemTax&#x60; that is being applied to this service charge. On reads, the amount applied is populated.  An &#x60;OrderLineItemAppliedTax&#x60; will be automatically created on every taxable service charge for all &#x60;ORDER&#x60; scoped taxes that are added to the order. &#x60;OrderLineItemAppliedTax&#x60; records for &#x60;LINE_ITEM&#x60; scoped taxes must be added in requests for the tax to apply to any taxable service charge.  Taxable service charges have the &#x60;taxable&#x60; field set to true and calculated in the &#x60;SUBTOTAL_PHASE&#x60;.  To change the amount of a tax, modify the referenced top-level tax. |  [optional]



