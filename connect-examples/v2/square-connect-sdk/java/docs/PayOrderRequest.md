
# PayOrderRequest

### Description

Defines the fields that are included in requests to the [PayOrder](#endpoint-payorder) endpoint.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idempotencyKey** | **String** | A value you specify that uniquely identifies this request among requests you&#39;ve sent. If you&#39;re unsure whether a particular payment request was completed successfully, you can reattempt it with the same idempotency key without worrying about duplicate payments.  See [Idempotency](/working-with-apis/idempotency) for more information. | 
**orderVersion** | **Integer** | The version of the order being paid. If not supplied, the latest version will be paid. |  [optional]
**paymentIds** | **List&lt;String&gt;** |  |  [optional]



