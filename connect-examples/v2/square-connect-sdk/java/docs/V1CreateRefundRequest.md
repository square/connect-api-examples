
# V1CreateRefundRequest

### Description

V1CreateRefundRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**paymentId** | **String** | The ID of the payment to refund. If you are creating a &#x60;PARTIAL&#x60; refund for a split tender payment, instead provide the id of the particular tender you want to refund. | 
**type** | **String** | TThe type of refund (FULL or PARTIAL). See [V1CreateRefundRequestType](#type-v1createrefundrequesttype) for possible values | 
**reason** | **String** | The reason for the refund. | 
**refundedMoney** | [**V1Money**](V1Money.md) | The amount of money to refund. Required only for PARTIAL refunds. |  [optional]
**requestIdempotenceKey** | **String** | An optional key to ensure idempotence if you issue the same PARTIAL refund request more than once. |  [optional]



