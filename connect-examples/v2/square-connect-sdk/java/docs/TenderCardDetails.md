
# TenderCardDetails

### Description

Represents additional details of a tender with `type` `CARD` or `SQUARE_GIFT_CARD`

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | **String** | The credit card payment&#39;s current state (such as &#x60;AUTHORIZED&#x60; or &#x60;CAPTURED&#x60;). See [TenderCardDetailsStatus](#type-tendercarddetailsstatus) for possible values |  [optional]
**card** | [**Card**](Card.md) | The credit card&#39;s non-confidential details. |  [optional]
**entryMethod** | **String** | The method used to enter the card&#39;s details for the transaction. See [TenderCardDetailsEntryMethod](#type-tendercarddetailsentrymethod) for possible values |  [optional]



