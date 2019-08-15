
# RefundPaymentResponse

### Description

Defines the fields that are included in the response body of a request to the [RefundPayment](#endpoint-refunds-refundpayment) endpoint.  Note: if there are errors processing the request, the refund field may not be present, or it may be present in a FAILED state.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Information on errors encountered during the request. |  [optional]
**refund** | [**PaymentRefund**](PaymentRefund.md) | The successfully created &#x60;PaymentRefund&#x60;. |  [optional]



