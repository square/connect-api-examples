
# V1Order

### Description

V1Order

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Any errors that occurred during the request. |  [optional]
**id** | **String** | The order&#39;s unique identifier. |  [optional]
**buyerEmail** | **String** | The email address of the order&#39;s buyer. |  [optional]
**recipientName** | **String** | The name of the order&#39;s buyer. |  [optional]
**recipientPhoneNumber** | **String** | The phone number to use for the order&#39;s delivery. |  [optional]
**state** | **String** | Whether the tax is an ADDITIVE tax or an INCLUSIVE tax. See [V1OrderState](#type-v1orderstate) for possible values |  [optional]
**shippingAddress** | [**Address**](Address.md) | The address to ship the order to. |  [optional]
**subtotalMoney** | [**V1Money**](V1Money.md) | The amount of all items purchased in the order, before taxes and shipping. |  [optional]
**totalShippingMoney** | [**V1Money**](V1Money.md) | The shipping cost for the order. |  [optional]
**totalTaxMoney** | [**V1Money**](V1Money.md) | The total of all taxes applied to the order. |  [optional]
**totalPriceMoney** | [**V1Money**](V1Money.md) | The total cost of the order. |  [optional]
**totalDiscountMoney** | [**V1Money**](V1Money.md) | The total of all discounts applied to the order. |  [optional]
**createdAt** | **String** | The time when the order was created, in ISO 8601 format. |  [optional]
**updatedAt** | **String** | The time when the order was last modified, in ISO 8601 format. |  [optional]
**expiresAt** | **String** | The time when the order expires if no action is taken, in ISO 8601 format. |  [optional]
**paymentId** | **String** | The unique identifier of the payment associated with the order. |  [optional]
**buyerNote** | **String** | A note provided by the buyer when the order was created, if any. |  [optional]
**completedNote** | **String** | A note provided by the merchant when the order&#39;s state was set to COMPLETED, if any |  [optional]
**refundedNote** | **String** | A note provided by the merchant when the order&#39;s state was set to REFUNDED, if any. |  [optional]
**canceledNote** | **String** | A note provided by the merchant when the order&#39;s state was set to CANCELED, if any. |  [optional]
**tender** | [**V1Tender**](V1Tender.md) | The tender used to pay for the order. |  [optional]
**orderHistory** | [**List&lt;V1OrderHistoryEntry&gt;**](V1OrderHistoryEntry.md) | The history of actions associated with the order. |  [optional]
**promoCode** | **String** | The promo code provided by the buyer, if any. |  [optional]
**btcReceiveAddress** | **String** | For Bitcoin transactions, the address that the buyer sent Bitcoin to. |  [optional]
**btcPriceSatoshi** | [**BigDecimal**](BigDecimal.md) | For Bitcoin transactions, the price of the buyer&#39;s order in satoshi (100 million satoshi equals 1 BTC). |  [optional]



