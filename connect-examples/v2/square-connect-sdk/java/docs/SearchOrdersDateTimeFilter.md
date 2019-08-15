
# SearchOrdersDateTimeFilter

### Description

Filter for `Order` objects based on whether their `CREATED_AT`, `CLOSED_AT` or `UPDATED_AT` timestamps fall within a specified time range. You can specify the time range and which timestamp to filter for. You can filter for only one time range at a time.  For each time range, the start time and end time are inclusive. If the end time is absent, it defaults to the time of the first request for the cursor.  __Important:__ If you use the DateTimeFilter in a SearchOrders query, you must also set the `sort_field` in [OrdersSort](#type-searchorderordersort) to the same field you filter for. For example, if you set the `CLOSED_AT` field in DateTimeFilter, you must also set the `sort_field` in SearchOrdersSort to `CLOSED_AT`. Otherwise, SearchOrders will throw an error. [Learn more about filtering orders by time range](/orders-api/manage-orders#important-note-on-filtering-orders-by-time-range).

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**createdAt** | [**TimeRange**](TimeRange.md) | Time range for filtering on the &#x60;created_at&#x60; timestamp. If you use this value, you must also set the &#x60;sort_field&#x60; in the OrdersSearchSort object to &#x60;CREATED_AT&#x60;. |  [optional]
**updatedAt** | [**TimeRange**](TimeRange.md) | Time range for filtering on the &#x60;updated_at&#x60; timestamp. If you use this value, you must also set the &#x60;sort_field&#x60; in the OrdersSearchSort object to &#x60;UPDATED_AT&#x60;. |  [optional]
**closedAt** | [**TimeRange**](TimeRange.md) | Time range for filtering on the &#x60;closed_at&#x60; timestamp. If you use this value, you must also set the &#x60;sort_field&#x60; in the OrdersSearchSort object to &#x60;CLOSED_AT&#x60;. |  [optional]



