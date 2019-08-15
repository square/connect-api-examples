
# V1Settlement

### Description

V1Settlement

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The settlement&#39;s unique identifier. |  [optional]
**status** | **String** | The settlement&#39;s current status. See [V1SettlementStatus](#type-v1settlementstatus) for possible values |  [optional]
**totalMoney** | [**V1Money**](V1Money.md) | The amount of money involved in the settlement. A positive amount indicates a deposit, and a negative amount indicates a withdrawal. This amount is never zero. |  [optional]
**initiatedAt** | **String** | The time when the settlement was submitted for deposit or withdrawal, in ISO 8601 format. |  [optional]
**bankAccountId** | **String** | The Square-issued unique identifier for the bank account associated with the settlement. |  [optional]
**entries** | [**List&lt;V1SettlementEntry&gt;**](V1SettlementEntry.md) | The entries included in this settlement. |  [optional]



