
# CreatePaymentResponse

### Description

Defines the fields that are included in the response body of a request to the [CreatePayment](#endpoint-payments-createpayment) endpoint.  Note: if there are errors processing the request, the payment field may not be present, or it may be present with a status of `FAILED`.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**errors** | [**List&lt;Error&gt;**](Error.md) | Information on errors encountered during the request. |  [optional]
**payment** | [**Payment**](Payment.md) | The newly created payment. |  [optional]



