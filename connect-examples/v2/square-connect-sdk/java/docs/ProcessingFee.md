
# ProcessingFee

### Description

Represents Square processing fee.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**effectiveAt** | **String** | Timestamp of when the fee takes effect, in RFC 3339 format. |  [optional]
**type** | **String** | The type of fee assessed or adjusted. Can be one of: &#x60;INITIAL&#x60;, &#x60;ADJUSTMENT&#x60;. |  [optional]
**amountMoney** | [**Money**](Money.md) | The fee amount assessed or adjusted by Square. May be negative.  Positive values represent funds being assessed, while negative values represent funds being returned. |  [optional]



