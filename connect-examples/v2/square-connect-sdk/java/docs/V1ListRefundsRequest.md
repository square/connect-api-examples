
# V1ListRefundsRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**order** | **String** | TThe order in which payments are listed in the response. See [SortOrder](#type-sortorder) for possible values |  [optional]
**beginTime** | **String** | The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year. |  [optional]
**endTime** | **String** | The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time. |  [optional]
**limit** | **Integer** | The approximate number of refunds to return in a single response. Default: 100. Max: 200. Response may contain more results than the prescribed limit when refunds are made simultaneously to multiple tenders in a payment or when refunds are generated in an exchange to account for the value of returned goods. |  [optional]
**batchToken** | **String** | A pagination cursor to retrieve the next set of results for your original query to the endpoint. |  [optional]



