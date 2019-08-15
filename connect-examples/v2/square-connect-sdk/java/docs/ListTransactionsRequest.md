
# ListTransactionsRequest

### Description

Defines the query parameters that can be included in a request to the [ListTransactions](#endpoint-listtransactions) endpoint.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**beginTime** | **String** | The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. |  [optional]
**endTime** | **String** | The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. |  [optional]
**sortOrder** | **String** | The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; See [SortOrder](#type-sortorder) for possible values |  [optional]
**cursor** | **String** | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. |  [optional]



