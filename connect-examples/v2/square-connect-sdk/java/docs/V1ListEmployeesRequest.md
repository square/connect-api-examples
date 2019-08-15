
# V1ListEmployeesRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**order** | **String** | The order in which employees are listed in the response, based on their created_at field.      Default value: ASC See [SortOrder](#type-sortorder) for possible values |  [optional]
**beginUpdatedAt** | **String** | If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format |  [optional]
**endUpdatedAt** | **String** | If filtering results by there updated_at field, the end of the requested reporting period, in ISO 8601 format. |  [optional]
**beginCreatedAt** | **String** | If filtering results by their created_at field, the beginning of the requested reporting period, in ISO 8601 format. |  [optional]
**endCreatedAt** | **String** | If filtering results by their created_at field, the end of the requested reporting period, in ISO 8601 format. |  [optional]
**status** | **String** | If provided, the endpoint returns only employee entities with the specified status (ACTIVE or INACTIVE). See [V1ListEmployeesRequestStatus](#type-v1listemployeesrequeststatus) for possible values |  [optional]
**externalId** | **String** | If provided, the endpoint returns only employee entities with the specified external_id. |  [optional]
**limit** | **Integer** | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. |  [optional]
**batchToken** | **String** | A pagination cursor to retrieve the next set of results for your original query to the endpoint. |  [optional]



