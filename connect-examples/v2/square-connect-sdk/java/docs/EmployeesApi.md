# EmployeesApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**listEmployees**](EmployeesApi.md#listEmployees) | **GET** /v2/employees | ListEmployees
[**retrieveEmployee**](EmployeesApi.md#retrieveEmployee) | **GET** /v2/employees/{id} | RetrieveEmployee


<a name="listEmployees"></a>
# **listEmployees**
> ListEmployeesResponse listEmployees(locationId, status, limit, cursor)

ListEmployees

Gets a list of &#x60;Employee&#x60; objects for a business.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

EmployeesApi apiInstance = new EmployeesApi();
String locationId = "locationId_example"; // String | Filter employees returned to only those that are associated with the specified location.
String status = "status_example"; // String | Specifies the EmployeeStatus to filter the employee by.
Integer limit = 56; // Integer | The number of employees to be returned on each page.
String cursor = "cursor_example"; // String | The token required to retrieve the specified page of results.
try {
    ListEmployeesResponse result = apiInstance.listEmployees(locationId, status, limit, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmployeesApi#listEmployees");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| Filter employees returned to only those that are associated with the specified location. | [optional]
 **status** | **String**| Specifies the EmployeeStatus to filter the employee by. | [optional]
 **limit** | **Integer**| The number of employees to be returned on each page. | [optional]
 **cursor** | **String**| The token required to retrieve the specified page of results. | [optional]

### Return type

[**ListEmployeesResponse**](ListEmployeesResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveEmployee"></a>
# **retrieveEmployee**
> RetrieveEmployeeResponse retrieveEmployee(id)

RetrieveEmployee

Gets an &#x60;Employee&#x60; by Square-assigned employee &#x60;ID&#x60; (UUID)

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

EmployeesApi apiInstance = new EmployeesApi();
String id = "id_example"; // String | UUID for the employee that was requested.
try {
    RetrieveEmployeeResponse result = apiInstance.retrieveEmployee(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EmployeesApi#retrieveEmployee");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**| UUID for the employee that was requested. |

### Return type

[**RetrieveEmployeeResponse**](RetrieveEmployeeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

