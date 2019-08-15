
# SearchOrdersRequest

### Description

The request does not have any required fields. When given no query criteria, SearchOrders will return all results for all of the merchant’s locations. When fetching additional pages using a `cursor`, the `query` must be equal to the `query` used to fetch the first page of results.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**locationIds** | **List&lt;String&gt;** | The location IDs for the orders to query. All locations must belong to the same merchant.  Min: 1 location IDs.  Max: 10 location IDs. |  [optional]
**cursor** | **String** | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query. See [Pagination](/basics/api101/pagination) for more information. |  [optional]
**query** | [**SearchOrdersQuery**](SearchOrdersQuery.md) | Query conditions used to filter or sort the results. Note that when fetching additional pages using a cursor, the query must be equal to the query used to fetch the first page of results. |  [optional]
**limit** | **Integer** | Maximum number of results to be returned in a single page. It is possible to receive fewer results than the specified limit on a given page.  Default: &#x60;500&#x60; |  [optional]
**returnEntries** | **Boolean** | Boolean that controls the format of the search results. If &#x60;true&#x60;, SearchOrders will return [&#x60;OrderEntry&#x60;](#type-orderentry) objects. If &#x60;false&#x60;, SearchOrders will return complete Order objects.  Default: &#x60;false&#x60;. |  [optional]



