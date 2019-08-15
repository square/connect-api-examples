
# AdditionalRecipientReceivableRefund

### Description

A refund of an [AdditionalRecipientReceivable](#type-additionalrecipientreceivable). This includes the ID of the additional recipient receivable associated to this object, as well as a reference to the [Refund](#type-refund) that created this receivable refund.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The receivable refund&#39;s unique ID, issued by Square payments servers. | 
**receivableId** | **String** | The ID of the receivable that the refund was applied to. | 
**refundId** | **String** | The ID of the refund that is associated to this receivable refund. | 
**transactionLocationId** | **String** | The ID of the location that created the receivable. This is the location ID on the associated transaction. | 
**amountMoney** | [**Money**](Money.md) | The amount of the refund. This will always be non-negative. | 
**createdAt** | **String** | The time when the refund was created, in RFC 3339 format. |  [optional]



