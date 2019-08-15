
# SearchCustomersRequest

### Description

Defines the fields included in the request body for the SearchCustomers endpoint.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**cursor** | **String** | Include the pagination cursor in subsequent calls to this endpoint to retrieve the next set of results associated with the original query.  See [Pagination](/basics/api101/pagination) for more information. |  [optional]
**limit** | **Long** | A limit on the number of results to be returned in a single page. The limit is advisory - the implementation may return more or fewer results. If the supplied limit is negative, zero, or is higher than the maximum limit of 1,000, it will be ignored. |  [optional]
**query** | [**CustomerQuery**](CustomerQuery.md) | Query customers based on the given conditions and sort order. Calling SearchCustomers without an explicit query parameter will return all customers ordered alphabetically based on &#x60;given_name&#x60; and &#x60;family_name&#x60;. |  [optional]



