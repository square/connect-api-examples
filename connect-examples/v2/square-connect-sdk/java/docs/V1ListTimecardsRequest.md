
# V1ListTimecardsRequest

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**order** | **String** | The order in which timecards are listed in the response, based on their created_at field. See [SortOrder](#type-sortorder) for possible values |  [optional]
**employeeId** | **String** | If provided, the endpoint returns only timecards for the employee with the specified ID. |  [optional]
**beginClockinTime** | **String** | If filtering results by their clockin_time field, the beginning of the requested reporting period, in ISO 8601 format. |  [optional]
**endClockinTime** | **String** | If filtering results by their clockin_time field, the end of the requested reporting period, in ISO 8601 format. |  [optional]
**beginClockoutTime** | **String** | If filtering results by their clockout_time field, the beginning of the requested reporting period, in ISO 8601 format. |  [optional]
**endClockoutTime** | **String** | If filtering results by their clockout_time field, the end of the requested reporting period, in ISO 8601 format. |  [optional]
**beginUpdatedAt** | **String** | If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format. |  [optional]
**endUpdatedAt** | **String** | If filtering results by their updated_at field, the end of the requested reporting period, in ISO 8601 format. |  [optional]
**deleted** | **Boolean** | If true, only deleted timecards are returned. If false, only valid timecards are returned.If you don&#39;t provide this parameter, both valid and deleted timecards are returned. |  [optional]
**limit** | **Integer** | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. |  [optional]
**batchToken** | **String** | A pagination cursor to retrieve the next set of results for your original query to the endpoint. |  [optional]



