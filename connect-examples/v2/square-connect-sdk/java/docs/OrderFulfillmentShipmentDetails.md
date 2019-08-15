
# OrderFulfillmentShipmentDetails

### Description

Contains details necessary to fulfill a shipment order.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**recipient** | [**OrderFulfillmentRecipient**](OrderFulfillmentRecipient.md) | Information on the person meant to receive this shipment fulfillment. |  [optional]
**carrier** | **String** | The shipping carrier being used to ship this fulfillment e.g. UPS, FedEx, USPS, etc. |  [optional]
**shippingNote** | **String** | A note with additional information for the shipping carrier. |  [optional]
**shippingType** | **String** | A description of the type of shipping product purchased from the carrier. e.g. First Class, Priority, Express |  [optional]
**trackingNumber** | **String** | The reference number provided by the carrier to track the shipment&#39;s progress. |  [optional]
**trackingUrl** | **String** | A link to the tracking webpage on the carrier&#39;s website. |  [optional]
**placedAt** | **String** | The [timestamp](#workingwithdates) indicating when the shipment was requested. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**inProgressAt** | **String** | The [timestamp](#workingwithdates) indicating when this fulfillment was moved to the &#x60;RESERVED&#x60; state. Indicates that preparation of this shipment has begun. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**packagedAt** | **String** | The [timestamp](#workingwithdates) indicating when this fulfillment was moved to the &#x60;PREPARED&#x60; state. Indicates that the fulfillment is packaged. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**expectedShippedAt** | **String** | The [timestamp](#workingwithdates) indicating when the shipment is expected to be delivered to the shipping carrier. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**shippedAt** | **String** | The [timestamp](#workingwithdates) indicating when this fulfillment was moved to the &#x60;COMPLETED&#x60;state. Indicates that the fulfillment has been given to the shipping carrier. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**canceledAt** | **String** | The [timestamp](#workingwithdates) indicating the shipment was canceled. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**cancelReason** | **String** | A description of why the shipment was canceled. |  [optional]
**failedAt** | **String** | The [timestamp](#workingwithdates) indicating when the shipment failed to be completed. Must be in RFC3339 timestamp format, e.g., \&quot;2016-09-04T23:59:33.123Z\&quot;. |  [optional]
**failureReason** | **String** | A description of why the shipment failed to be completed. |  [optional]



