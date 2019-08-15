
# V1CashDrawerEvent

### Description

V1CashDrawerEvent

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The event&#39;s unique ID. |  [optional]
**employeeId** | **String** | The ID of the employee that created the event. |  [optional]
**eventType** | **String** | The type of event that occurred. See [V1CashDrawerEventEventType](#type-v1cashdrawereventeventtype) for possible values |  [optional]
**eventMoney** | [**V1Money**](V1Money.md) | The amount of money that was added to or removed from the cash drawer because of the event. This value can be positive (for added money) or negative (for removed money). |  [optional]
**createdAt** | **String** | The time when the event occurred, in ISO 8601 format. |  [optional]
**description** | **String** | An optional description of the event, entered by the employee that created it. |  [optional]



