
# OrderRoundingAdjustment

### Description

A rounding adjustment of the money being returned. Commonly used to apply Cash Rounding when the minimum unit of account is smaller than the lowest physical denomination of currency.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique ID that identifies the rounding adjustment only within this order. |  [optional]
**name** | **String** | The name of the rounding adjustment from the original sale Order. |  [optional]
**amountMoney** | [**Money**](Money.md) | Actual rounding adjustment amount. |  [optional]



