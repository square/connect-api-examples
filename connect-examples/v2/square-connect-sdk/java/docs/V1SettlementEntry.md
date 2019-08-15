
# V1SettlementEntry

### Description

V1SettlementEntry

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**paymentId** | **String** | The settlement&#39;s unique identifier. |  [optional]
**type** | **String** | The settlement&#39;s current status. See [V1SettlementEntryType](#type-v1settlemententrytype) for possible values |  [optional]
**amountMoney** | [**V1Money**](V1Money.md) | The total amount of money this entry contributes to the total settlement amount. |  [optional]
**feeMoney** | [**V1Money**](V1Money.md) | The amount of all Square fees associated with this settlement entry. This value is always negative or zero. |  [optional]



