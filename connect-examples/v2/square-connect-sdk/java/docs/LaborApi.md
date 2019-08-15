# LaborApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createBreakType**](LaborApi.md#createBreakType) | **POST** /v2/labor/break-types | CreateBreakType
[**createShift**](LaborApi.md#createShift) | **POST** /v2/labor/shifts | CreateShift
[**deleteBreakType**](LaborApi.md#deleteBreakType) | **DELETE** /v2/labor/break-types/{id} | DeleteBreakType
[**deleteShift**](LaborApi.md#deleteShift) | **DELETE** /v2/labor/shifts/{id} | DeleteShift
[**getBreakType**](LaborApi.md#getBreakType) | **GET** /v2/labor/break-types/{id} | GetBreakType
[**getEmployeeWage**](LaborApi.md#getEmployeeWage) | **GET** /v2/labor/employee-wages/{id} | GetEmployeeWage
[**getShift**](LaborApi.md#getShift) | **GET** /v2/labor/shifts/{id} | GetShift
[**listBreakTypes**](LaborApi.md#listBreakTypes) | **GET** /v2/labor/break-types | ListBreakTypes
[**listEmployeeWages**](LaborApi.md#listEmployeeWages) | **GET** /v2/labor/employee-wages | ListEmployeeWages
[**listWorkweekConfigs**](LaborApi.md#listWorkweekConfigs) | **GET** /v2/labor/workweek-configs | ListWorkweekConfigs
[**searchShifts**](LaborApi.md#searchShifts) | **POST** /v2/labor/shifts/search | SearchShifts
[**updateBreakType**](LaborApi.md#updateBreakType) | **PUT** /v2/labor/break-types/{id} | UpdateBreakType
[**updateShift**](LaborApi.md#updateShift) | **PUT** /v2/labor/shifts/{id} | UpdateShift
[**updateWorkweekConfig**](LaborApi.md#updateWorkweekConfig) | **PUT** /v2/labor/workweek-configs/{id} | UpdateWorkweekConfig


<a name="createBreakType"></a>
# **createBreakType**
> CreateBreakTypeResponse createBreakType(body)

CreateBreakType

Creates a new &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; is a template for creating &#x60;Break&#x60; objects.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;break_name&#x60; - &#x60;expected_duration&#x60; - &#x60;is_paid&#x60;  You can only have 3 &#x60;BreakType&#x60; instances per location. If you attempt to add a 4th &#x60;BreakType&#x60; for a location, an &#x60;INVALID_REQUEST_ERROR&#x60; \&quot;Exceeded limit of 3 breaks per location.\&quot; is returned.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
CreateBreakTypeRequest body = new CreateBreakTypeRequest(); // CreateBreakTypeRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    CreateBreakTypeResponse result = apiInstance.createBreakType(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#createBreakType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateBreakTypeRequest**](CreateBreakTypeRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**CreateBreakTypeResponse**](CreateBreakTypeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createShift"></a>
# **createShift**
> CreateShiftResponse createShift(body)

CreateShift

Creates a new &#x60;Shift&#x60;.   A &#x60;Shift&#x60; represents a complete work day for a single employee.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;employee_id&#x60; - &#x60;start_at&#x60;  An attempt to create a new &#x60;Shift&#x60; can result in a &#x60;BAD_REQUEST&#x60; error when: - The &#x60;status&#x60; of the new &#x60;Shift&#x60; is &#x60;OPEN&#x60; and the employee has another  shift with an &#x60;OPEN&#x60; status.  - The &#x60;start_at&#x60; date is in the future - the &#x60;start_at&#x60; or &#x60;end_at&#x60; overlaps another shift for the same employee - If &#x60;Break&#x60;s are set in the request, a break &#x60;start_at&#x60; must not be before the &#x60;Shift.start_at&#x60;. A break &#x60;end_at&#x60; must not be after the &#x60;Shift.end_at&#x60;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
CreateShiftRequest body = new CreateShiftRequest(); // CreateShiftRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    CreateShiftResponse result = apiInstance.createShift(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#createShift");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**CreateShiftRequest**](CreateShiftRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**CreateShiftResponse**](CreateShiftResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteBreakType"></a>
# **deleteBreakType**
> DeleteBreakTypeResponse deleteBreakType(id)

DeleteBreakType

Deletes an existing &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; can be deleted even if it is referenced from a &#x60;Shift&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `BreakType` being deleted.
try {
    DeleteBreakTypeResponse result = apiInstance.deleteBreakType(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#deleteBreakType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;BreakType&#x60; being deleted. |

### Return type

[**DeleteBreakTypeResponse**](DeleteBreakTypeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteShift"></a>
# **deleteShift**
> DeleteShiftResponse deleteShift(id)

DeleteShift

Deletes a &#x60;Shift&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `Shift` being deleted.
try {
    DeleteShiftResponse result = apiInstance.deleteShift(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#deleteShift");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;Shift&#x60; being deleted. |

### Return type

[**DeleteShiftResponse**](DeleteShiftResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getBreakType"></a>
# **getBreakType**
> GetBreakTypeResponse getBreakType(id)

GetBreakType

Returns a single &#x60;BreakType&#x60; specified by id.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `BreakType` being retrieved.
try {
    GetBreakTypeResponse result = apiInstance.getBreakType(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#getBreakType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;BreakType&#x60; being retrieved. |

### Return type

[**GetBreakTypeResponse**](GetBreakTypeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getEmployeeWage"></a>
# **getEmployeeWage**
> GetEmployeeWageResponse getEmployeeWage(id)

GetEmployeeWage

Returns a single &#x60;EmployeeWage&#x60; specified by id.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `EmployeeWage` being retrieved.
try {
    GetEmployeeWageResponse result = apiInstance.getEmployeeWage(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#getEmployeeWage");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;EmployeeWage&#x60; being retrieved. |

### Return type

[**GetEmployeeWageResponse**](GetEmployeeWageResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getShift"></a>
# **getShift**
> GetShiftResponse getShift(id)

GetShift

Returns a single &#x60;Shift&#x60; specified by id.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `Shift` being retrieved.
try {
    GetShiftResponse result = apiInstance.getShift(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#getShift");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;Shift&#x60; being retrieved. |

### Return type

[**GetShiftResponse**](GetShiftResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listBreakTypes"></a>
# **listBreakTypes**
> ListBreakTypesResponse listBreakTypes(locationId, limit, cursor)

ListBreakTypes

Returns a paginated list of &#x60;BreakType&#x60; instances for a business.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String locationId = "locationId_example"; // String | Filter Break Types returned to only those that are associated with the specified location.
Integer limit = 56; // Integer | Maximum number of Break Types to return per page. Can range between 1 and 200. The default is the maximum at 200.
String cursor = "cursor_example"; // String | Pointer to the next page of Break Type results to fetch.
try {
    ListBreakTypesResponse result = apiInstance.listBreakTypes(locationId, limit, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#listBreakTypes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| Filter Break Types returned to only those that are associated with the specified location. | [optional]
 **limit** | **Integer**| Maximum number of Break Types to return per page. Can range between 1 and 200. The default is the maximum at 200. | [optional]
 **cursor** | **String**| Pointer to the next page of Break Type results to fetch. | [optional]

### Return type

[**ListBreakTypesResponse**](ListBreakTypesResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listEmployeeWages"></a>
# **listEmployeeWages**
> ListEmployeeWagesResponse listEmployeeWages(employeeId, limit, cursor)

ListEmployeeWages

Returns a paginated list of &#x60;EmployeeWage&#x60; instances for a business.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String employeeId = "employeeId_example"; // String | Filter wages returned to only those that are associated with the specified employee.
Integer limit = 56; // Integer | Maximum number of Employee Wages to return per page. Can range between 1 and 200. The default is the maximum at 200.
String cursor = "cursor_example"; // String | Pointer to the next page of Employee Wage results to fetch.
try {
    ListEmployeeWagesResponse result = apiInstance.listEmployeeWages(employeeId, limit, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#listEmployeeWages");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **employeeId** | **String**| Filter wages returned to only those that are associated with the specified employee. | [optional]
 **limit** | **Integer**| Maximum number of Employee Wages to return per page. Can range between 1 and 200. The default is the maximum at 200. | [optional]
 **cursor** | **String**| Pointer to the next page of Employee Wage results to fetch. | [optional]

### Return type

[**ListEmployeeWagesResponse**](ListEmployeeWagesResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listWorkweekConfigs"></a>
# **listWorkweekConfigs**
> ListWorkweekConfigsResponse listWorkweekConfigs(limit, cursor)

ListWorkweekConfigs

Returns a list of &#x60;WorkweekConfig&#x60; instances for a business.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
Integer limit = 56; // Integer | Maximum number of Workweek Configs to return per page.
String cursor = "cursor_example"; // String | Pointer to the next page of Workweek Config results to fetch.
try {
    ListWorkweekConfigsResponse result = apiInstance.listWorkweekConfigs(limit, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#listWorkweekConfigs");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Maximum number of Workweek Configs to return per page. | [optional]
 **cursor** | **String**| Pointer to the next page of Workweek Config results to fetch. | [optional]

### Return type

[**ListWorkweekConfigsResponse**](ListWorkweekConfigsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchShifts"></a>
# **searchShifts**
> SearchShiftsResponse searchShifts(body)

SearchShifts

Returns a paginated list of &#x60;Shift&#x60; records for a business.  The list to be returned can be filtered by: - Location IDs **and** - employee IDs **and** - shift status (&#x60;OPEN&#x60;, &#x60;CLOSED&#x60;) **and** - shift start **and** - shift end **and** - work day details  The list can be sorted by: - &#x60;start_at&#x60; - &#x60;end_at&#x60; - &#x60;created_at&#x60; - &#x60;updated_at&#x60;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
SearchShiftsRequest body = new SearchShiftsRequest(); // SearchShiftsRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    SearchShiftsResponse result = apiInstance.searchShifts(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#searchShifts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchShiftsRequest**](SearchShiftsRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**SearchShiftsResponse**](SearchShiftsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateBreakType"></a>
# **updateBreakType**
> UpdateBreakTypeResponse updateBreakType(id, body)

UpdateBreakType

Updates an existing &#x60;BreakType&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `BreakType` being updated.
UpdateBreakTypeRequest body = new UpdateBreakTypeRequest(); // UpdateBreakTypeRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    UpdateBreakTypeResponse result = apiInstance.updateBreakType(id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#updateBreakType");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;BreakType&#x60; being updated. |
 **body** | [**UpdateBreakTypeRequest**](UpdateBreakTypeRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**UpdateBreakTypeResponse**](UpdateBreakTypeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateShift"></a>
# **updateShift**
> UpdateShiftResponse updateShift(id, body)

UpdateShift

Updates an existing &#x60;Shift&#x60;.   When adding a &#x60;Break&#x60; to a &#x60;Shift&#x60;, any earlier &#x60;Breaks&#x60; in the &#x60;Shift&#x60; have  the &#x60;end_at&#x60; property set to a valid RFC-3339 datetime string.   When closing a &#x60;Shift&#x60;, all &#x60;Break&#x60; instances in the shift must be complete with &#x60;end_at&#x60; set on each &#x60;Break&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | ID of the object being updated.
UpdateShiftRequest body = new UpdateShiftRequest(); // UpdateShiftRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    UpdateShiftResponse result = apiInstance.updateShift(id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#updateShift");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| ID of the object being updated. |
 **body** | [**UpdateShiftRequest**](UpdateShiftRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**UpdateShiftResponse**](UpdateShiftResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateWorkweekConfig"></a>
# **updateWorkweekConfig**
> UpdateWorkweekConfigResponse updateWorkweekConfig(id, body)

UpdateWorkweekConfig

Updates a &#x60;WorkweekConfig&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.LaborApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

LaborApi apiInstance = new LaborApi();
String id = "id_example"; // String | UUID for the `WorkweekConfig` object being updated.
UpdateWorkweekConfigRequest body = new UpdateWorkweekConfigRequest(); // UpdateWorkweekConfigRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    UpdateWorkweekConfigResponse result = apiInstance.updateWorkweekConfig(id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling LaborApi#updateWorkweekConfig");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the &#x60;WorkweekConfig&#x60; object being updated. |
 **body** | [**UpdateWorkweekConfigRequest**](UpdateWorkweekConfigRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**UpdateWorkweekConfigResponse**](UpdateWorkweekConfigResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

