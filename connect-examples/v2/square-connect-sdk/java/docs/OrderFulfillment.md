
# OrderFulfillment

### Description

Contains details on how to fulfill this order.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**uid** | **String** | Unique ID that identifies the fulfillment only within this order. |  [optional]
**type** | **String** | The type of the fulfillment. See [OrderFulfillmentType](#type-orderfulfillmenttype) for possible values |  [optional]
**state** | **String** | The state of the fulfillment. See [OrderFulfillmentState](#type-orderfulfillmentstate) for possible values |  [optional]
**pickupDetails** | [**OrderFulfillmentPickupDetails**](OrderFulfillmentPickupDetails.md) | Contains details for a pickup fulfillment. Required when fulfillment type is &#x60;PICKUP&#x60;. |  [optional]
**shipmentDetails** | [**OrderFulfillmentShipmentDetails**](OrderFulfillmentShipmentDetails.md) | Contains details for a shipment fulfillment. Required when fulfillment type is &#x60;SHIPMENT&#x60;.  A shipment fulfillment&#39;s relationship to fulfillment &#x60;state&#x60;: &#x60;PROPOSED&#x60;: A shipment is requested. &#x60;RESERVED&#x60;: Fulfillment accepted. Shipment processing. &#x60;PREPARED&#x60;: Shipment packaged. Shipping label created. &#x60;COMPLETED&#x60;: Package has been shipped. &#x60;CANCELED&#x60;: Shipment has been canceled. &#x60;FAILED&#x60;: Shipment has failed. |  [optional]



