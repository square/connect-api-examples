
# Order

### Description

Contains all information related to a single order to process with Square, including line items that specify the products to purchase. Order objects also include information on any associated tenders, refunds, and returns.  All Connect V2 Transactions have all been converted to Orders including all associated itemization data.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The order&#39;s unique ID. |  [optional]
**locationId** | **String** | The ID of the merchant location this order is associated with. | 
**referenceId** | **String** | A client specified identifier to associate an entity in another system with this order. |  [optional]
**source** | [**OrderSource**](OrderSource.md) | The origination details of the order. |  [optional]
**customerId** | **String** | The [Customer](#type-customer) ID of the customer associated with the order. |  [optional]
**lineItems** | [**List&lt;OrderLineItem&gt;**](OrderLineItem.md) | The line items included in the order. |  [optional]
**taxes** | [**List&lt;OrderLineItemTax&gt;**](OrderLineItemTax.md) | The list of all taxes associated with the order.  Taxes can be scoped to either &#x60;ORDER&#x60; or &#x60;LINE_ITEM&#x60;. For taxes with &#x60;LINE_ITEM&#x60; scope, an &#x60;OrderLineItemAppliedTax&#x60; must be added to each line item that the tax applies to. For taxes with &#x60;ORDER&#x60; scope, the server will generate an &#x60;OrderLineItemAppliedTax&#x60; for every line item.  On reads, each tax in the list will include the total amount of that tax applied to the order.  __IMPORTANT__: If &#x60;LINE_ITEM&#x60; scope is set on any taxes in this field, usage of the deprecated &#x60;line_items.taxes&#x60; field will result in an error. Please use &#x60;line_items.applied_taxes&#x60; instead. |  [optional]
**discounts** | [**List&lt;OrderLineItemDiscount&gt;**](OrderLineItemDiscount.md) | The list of all discounts associated with the order.  Discounts can be scoped to either &#x60;ORDER&#x60; or &#x60;LINE_ITEM&#x60;. For discounts scoped to &#x60;LINE_ITEM&#x60;, an &#x60;OrderLineItemAppliedDiscount&#x60; must be added to each line item that the discount applies to. For discounts with &#x60;ORDER&#x60; scope, the server will generate an &#x60;OrderLineItemAppliedDiscount&#x60; for every line item.  __IMPORTANT__: If &#x60;LINE_ITEM&#x60; scope is set on any discounts in this field, usage of the deprecated &#x60;line_items.discounts&#x60; field will result in an error. Please use &#x60;line_items.applied_discounts&#x60; instead. |  [optional]
**serviceCharges** | [**List&lt;OrderServiceCharge&gt;**](OrderServiceCharge.md) | A list of service charges applied to the order. |  [optional]
**fulfillments** | [**List&lt;OrderFulfillment&gt;**](OrderFulfillment.md) | Details on order fulfillment.  Orders can only be created with at most one fulfillment. However, orders returned by the API may contain multiple fulfillments. |  [optional]
**returns** | [**List&lt;OrderReturn&gt;**](OrderReturn.md) | Collection of items from sale Orders being returned in this one. Normally part of an Itemized Return or Exchange.  There will be exactly one &#x60;Return&#x60; object per sale Order being referenced. |  [optional]
**returnAmounts** | [**OrderMoneyAmounts**](OrderMoneyAmounts.md) | Rollup of returned money amounts. |  [optional]
**netAmounts** | [**OrderMoneyAmounts**](OrderMoneyAmounts.md) | Net money amounts (sale money - return money). |  [optional]
**roundingAdjustment** | [**OrderRoundingAdjustment**](OrderRoundingAdjustment.md) | A positive or negative rounding adjustment to the total of the order, commonly used to apply Cash Rounding when the minimum unit of account is smaller than the lowest physical denomination of currency. |  [optional]
**tenders** | [**List&lt;Tender&gt;**](Tender.md) | The Tenders which were used to pay for the Order. |  [optional]
**refunds** | [**List&lt;Refund&gt;**](Refund.md) | The Refunds that are part of this Order. |  [optional]
**createdAt** | **String** | Timestamp for when the order was created. In RFC 3339 format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**updatedAt** | **String** | Timestamp for when the order was last updated. In RFC 3339 format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**closedAt** | **String** | Timestamp for when the order was closed. In RFC 3339 format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**state** | **String** | The current state of the order. &#x60;OPEN&#x60;,&#x60;COMPLETED&#x60;,&#x60;CANCELED&#x60; See [OrderState](#type-orderstate) for possible values |  [optional]
**version** | **Integer** | Version number which is incremented each time an update is committed to the order. Orders that were not created through the API will not include a version and thus cannot be updated.  [Read more about working with versions](/orders-api/manage-orders#update-orders). |  [optional]
**totalMoney** | [**Money**](Money.md) | The total amount of money to collect for the order. |  [optional]
**totalTaxMoney** | [**Money**](Money.md) | The total tax amount of money to collect for the order. |  [optional]
**totalDiscountMoney** | [**Money**](Money.md) | The total discount amount of money to collect for the order. |  [optional]
**totalServiceChargeMoney** | [**Money**](Money.md) | The total amount of money collected in service charges for the order.  Note: &#x60;total_service_charge_money&#x60; is the sum of &#x60;applied_money&#x60; fields for each individual service charge. Therefore, &#x60;total_service_charge_money&#x60; will only include inclusive tax amounts, not additive tax amounts. |  [optional]



