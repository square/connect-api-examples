
# Payment

### Description

Represents a payment processed by the Square API.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | Unique ID for the payment. | 
**createdAt** | **String** | Timestamp of when the payment was created, in RFC 3339 format. |  [optional]
**updatedAt** | **String** | Timestamp of when the payment was last updated, in RFC 3339 format. |  [optional]
**amountMoney** | [**Money**](Money.md) | The amount of money processed for this payment, not including &#x60;tip_money&#x60;. Specified in the smallest denomination of the applicable currency. For example, US dollar amounts are specified in cents. For more information, see [Working with monetary amounts](/build-basics/working-with-monetary-amounts). | 
**tipMoney** | [**Money**](Money.md) | The amount designated as a tip. Specified in the smallest denomination of the applicable currency. For example, US dollar amounts are specified in cents. |  [optional]
**totalMoney** | [**Money**](Money.md) | The total money for the payment, including &#x60;amount_money&#x60; and &#x60;tip_money&#x60;. Specified in the smallest denomination of the applicable currency. For example, US dollar amounts are specified in cents. |  [optional]
**appFeeMoney** | [**Money**](Money.md) | The amount of money the developer is taking as a fee for facilitating the payment on behalf of the seller. Specified in the smallest denomination of the applicable currency. For example, US dollar amounts are specified in cents.  For more information, see [Take Payments and Collect Fees](/payments-api/take-payments-and-collect-fees).  Cannot be more than 90% of the &#x60;total_money&#x60; value. |  [optional]
**processingFee** | [**List&lt;ProcessingFee&gt;**](ProcessingFee.md) | Processing fees and fee adjustments assessed by Square on this payment. |  [optional]
**refundedMoney** | [**Money**](Money.md) | Total amount of the payment refunded to-date. Specified in the smallest denomination of the applicable currency. For example, US dollar amounts are specified in cents. |  [optional]
**status** | **String** | Indicates whether the payment is &#x60;APPROVED&#x60;, &#x60;COMPLETED&#x60;, &#x60;CANCELED&#x60;, or &#x60;FAILED&#x60;. |  [optional]
**sourceType** | **String** | The source type for this payment |  [optional]
**cardDetails** | [**CardPaymentDetails**](CardPaymentDetails.md) | Non-confidential details about the source. Only populated if the &#x60;source_type&#x60; is &#x60;CARD&#x60;. |  [optional]
**locationId** | **String** | ID of the location associated with the payment. |  [optional]
**orderId** | **String** | ID of the order associated with this payment. |  [optional]
**referenceId** | **String** | An optional ID that associates this payment with an entity in another system. |  [optional]
**customerId** | **String** | An optional customer_id to be entered by the developer when creating a payment. |  [optional]
**refundIds** | **List&lt;String&gt;** | List of &#x60;refund_id&#x60;s identifying refunds for this payment. |  [optional]
**buyerEmailAddress** | **String** | The buyer&#39;s e-mail address |  [optional]
**billingAddress** | [**Address**](Address.md) | The buyer&#39;s billing address |  [optional]
**shippingAddress** | [**Address**](Address.md) | The buyer&#39;s shipping address |  [optional]
**note** | **String** | An optional note to include when creating a payment |  [optional]



