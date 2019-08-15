# V1EmployeesApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createEmployee**](V1EmployeesApi.md#createEmployee) | **POST** /v1/me/employees | CreateEmployee
[**createEmployeeRole**](V1EmployeesApi.md#createEmployeeRole) | **POST** /v1/me/roles | CreateEmployeeRole
[**createTimecard**](V1EmployeesApi.md#createTimecard) | **POST** /v1/me/timecards | CreateTimecard
[**deleteTimecard**](V1EmployeesApi.md#deleteTimecard) | **DELETE** /v1/me/timecards/{timecard_id} | DeleteTimecard
[**listCashDrawerShifts**](V1EmployeesApi.md#listCashDrawerShifts) | **GET** /v1/{location_id}/cash-drawer-shifts | ListCashDrawerShifts
[**listEmployeeRoles**](V1EmployeesApi.md#listEmployeeRoles) | **GET** /v1/me/roles | ListEmployeeRoles
[**listEmployees**](V1EmployeesApi.md#listEmployees) | **GET** /v1/me/employees | ListEmployees
[**listTimecardEvents**](V1EmployeesApi.md#listTimecardEvents) | **GET** /v1/me/timecards/{timecard_id}/events | ListTimecardEvents
[**listTimecards**](V1EmployeesApi.md#listTimecards) | **GET** /v1/me/timecards | ListTimecards
[**retrieveCashDrawerShift**](V1EmployeesApi.md#retrieveCashDrawerShift) | **GET** /v1/{location_id}/cash-drawer-shifts/{shift_id} | RetrieveCashDrawerShift
[**retrieveEmployee**](V1EmployeesApi.md#retrieveEmployee) | **GET** /v1/me/employees/{employee_id} | RetrieveEmployee
[**retrieveEmployeeRole**](V1EmployeesApi.md#retrieveEmployeeRole) | **GET** /v1/me/roles/{role_id} | RetrieveEmployeeRole
[**retrieveTimecard**](V1EmployeesApi.md#retrieveTimecard) | **GET** /v1/me/timecards/{timecard_id} | RetrieveTimecard
[**updateEmployee**](V1EmployeesApi.md#updateEmployee) | **PUT** /v1/me/employees/{employee_id} | UpdateEmployee
[**updateEmployeeRole**](V1EmployeesApi.md#updateEmployeeRole) | **PUT** /v1/me/roles/{role_id} | UpdateEmployeeRole
[**updateTimecard**](V1EmployeesApi.md#updateTimecard) | **PUT** /v1/me/timecards/{timecard_id} | UpdateTimecard


<a name="createEmployee"></a>
# **createEmployee**
> V1Employee createEmployee(body)

CreateEmployee

 Use the CreateEmployee endpoint to add an employee to a Square account. Employees created with the Connect API have an initial status of &#x60;INACTIVE&#x60;. Inactive employees cannot sign in to Square Point of Sale until they are activated from the Square Dashboard. Employee status cannot be changed with the Connect API.  &lt;aside class&#x3D;\&quot;important\&quot;&gt; Employee entities cannot be deleted. To disable employee profiles, set the employee&#39;s status to &lt;code&gt;INACTIVE&lt;/code&gt; &lt;/aside&gt;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
V1Employee body = new V1Employee(); // V1Employee | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1Employee result = apiInstance.createEmployee(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#createEmployee");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**V1Employee**](V1Employee.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1Employee**](V1Employee.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createEmployeeRole"></a>
# **createEmployeeRole**
> V1EmployeeRole createEmployeeRole(employeeRole)

CreateEmployeeRole

Creates an employee role you can then assign to employees.  Square accounts can include any number of roles that can be assigned to employees. These roles define the actions and permissions granted to an employee with that role. For example, an employee with a \&quot;Shift Manager\&quot; role might be able to issue refunds in Square Point of Sale, whereas an employee with a \&quot;Clerk\&quot; role might not.  Roles are assigned with the [V1UpdateEmployee](#endpoint-v1updateemployee) endpoint. An employee can have only one role at a time.  If an employee has no role, they have none of the permissions associated with roles. All employees can accept payments with Square Point of Sale.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
V1EmployeeRole employeeRole = new V1EmployeeRole(); // V1EmployeeRole | An EmployeeRole object with a name and permissions, and an optional owner flag.
try {
    V1EmployeeRole result = apiInstance.createEmployeeRole(employeeRole);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#createEmployeeRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **employeeRole** | [**V1EmployeeRole**](V1EmployeeRole.md)| An EmployeeRole object with a name and permissions, and an optional owner flag. |

### Return type

[**V1EmployeeRole**](V1EmployeeRole.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createTimecard"></a>
# **createTimecard**
> V1Timecard createTimecard(body)

CreateTimecard

Creates a timecard for an employee and clocks them in with an &#x60;API_CREATE&#x60; event and a &#x60;clockin_time&#x60; set to the current time unless the request provides a different value. To import timecards from another system (rather than clocking someone in). Specify the &#x60;clockin_time&#x60; and* &#x60;clockout_time&#x60; in the request.  Timecards correspond to exactly one shift for a given employee, bounded by the &#x60;clockin_time&#x60; and &#x60;clockout_time&#x60; fields. An employee is considered clocked in if they have a timecard that doesn&#39;t have a &#x60;clockout_time&#x60; set. An employee that is currently clocked in cannot be clocked in a second time.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
V1Timecard body = new V1Timecard(); // V1Timecard | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1Timecard result = apiInstance.createTimecard(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#createTimecard");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**V1Timecard**](V1Timecard.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1Timecard**](V1Timecard.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="deleteTimecard"></a>
# **deleteTimecard**
> Object deleteTimecard(timecardId)

DeleteTimecard

Deletes a timecard. Timecards can also be deleted through the Square Dashboard. Deleted timecards are still accessible through Connect API endpoints, but cannot be modified. The &#x60;deleted&#x60; field of the &#x60;Timecard&#x60; object indicates whether the timecard has been deleted.  *Note**: By default, deleted timecards appear alongside valid timecards in results returned by the [ListTimecards](#endpoint-v1employees-listtimecards) endpoint. To filter deleted timecards, include the &#x60;deleted&#x60; query parameter in the list request.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String timecardId = "timecardId_example"; // String | The ID of the timecard to delete.
try {
    Object result = apiInstance.deleteTimecard(timecardId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#deleteTimecard");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **timecardId** | **String**| The ID of the timecard to delete. |

### Return type

**Object**

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listCashDrawerShifts"></a>
# **listCashDrawerShifts**
> List&lt;V1CashDrawerShift&gt; listCashDrawerShifts(locationId, order, beginTime, endTime)

ListCashDrawerShifts

Provides the details for all of a location&#39;s cash drawer shifts during a date range. The date range you specify cannot exceed 90 days.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String locationId = "locationId_example"; // String | The ID of the location to list cash drawer shifts for.
String order = "order_example"; // String | The order in which cash drawer shifts are listed in the response, based on their created_at field. Default value: ASC
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time minus 90 days.
String endTime = "endTime_example"; // String | The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time.
try {
    List<V1CashDrawerShift> result = apiInstance.listCashDrawerShifts(locationId, order, beginTime, endTime);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listCashDrawerShifts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list cash drawer shifts for. |
 **order** | **String**| The order in which cash drawer shifts are listed in the response, based on their created_at field. Default value: ASC | [optional]
 **beginTime** | **String**| The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time minus 90 days. | [optional]
 **endTime** | **String**| The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time. | [optional]

### Return type

[**List&lt;V1CashDrawerShift&gt;**](V1CashDrawerShift.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listEmployeeRoles"></a>
# **listEmployeeRoles**
> List&lt;V1EmployeeRole&gt; listEmployeeRoles(order, limit, batchToken)

ListEmployeeRoles

Provides summary information for all of a business&#39;s employee roles.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String order = "order_example"; // String | The order in which employees are listed in the response, based on their created_at field.Default value: ASC
Integer limit = 56; // Integer | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1EmployeeRole> result = apiInstance.listEmployeeRoles(order, limit, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listEmployeeRoles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **order** | **String**| The order in which employees are listed in the response, based on their created_at field.Default value: ASC | [optional]
 **limit** | **Integer**| The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1EmployeeRole&gt;**](V1EmployeeRole.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listEmployees"></a>
# **listEmployees**
> List&lt;V1Employee&gt; listEmployees(order, beginUpdatedAt, endUpdatedAt, beginCreatedAt, endCreatedAt, status, externalId, limit, batchToken)

ListEmployees

Provides summary information for all of a business&#39;s employees.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String order = "order_example"; // String | The order in which employees are listed in the response, based on their created_at field.      Default value: ASC
String beginUpdatedAt = "beginUpdatedAt_example"; // String | If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format
String endUpdatedAt = "endUpdatedAt_example"; // String | If filtering results by there updated_at field, the end of the requested reporting period, in ISO 8601 format.
String beginCreatedAt = "beginCreatedAt_example"; // String | If filtering results by their created_at field, the beginning of the requested reporting period, in ISO 8601 format.
String endCreatedAt = "endCreatedAt_example"; // String | If filtering results by their created_at field, the end of the requested reporting period, in ISO 8601 format.
String status = "status_example"; // String | If provided, the endpoint returns only employee entities with the specified status (ACTIVE or INACTIVE).
String externalId = "externalId_example"; // String | If provided, the endpoint returns only employee entities with the specified external_id.
Integer limit = 56; // Integer | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1Employee> result = apiInstance.listEmployees(order, beginUpdatedAt, endUpdatedAt, beginCreatedAt, endCreatedAt, status, externalId, limit, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listEmployees");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **order** | **String**| The order in which employees are listed in the response, based on their created_at field.      Default value: ASC | [optional]
 **beginUpdatedAt** | **String**| If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format | [optional]
 **endUpdatedAt** | **String**| If filtering results by there updated_at field, the end of the requested reporting period, in ISO 8601 format. | [optional]
 **beginCreatedAt** | **String**| If filtering results by their created_at field, the beginning of the requested reporting period, in ISO 8601 format. | [optional]
 **endCreatedAt** | **String**| If filtering results by their created_at field, the end of the requested reporting period, in ISO 8601 format. | [optional]
 **status** | **String**| If provided, the endpoint returns only employee entities with the specified status (ACTIVE or INACTIVE). | [optional]
 **externalId** | **String**| If provided, the endpoint returns only employee entities with the specified external_id. | [optional]
 **limit** | **Integer**| The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1Employee&gt;**](V1Employee.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listTimecardEvents"></a>
# **listTimecardEvents**
> List&lt;V1TimecardEvent&gt; listTimecardEvents(timecardId)

ListTimecardEvents

Provides summary information for all events associated with a particular timecard.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String timecardId = "timecardId_example"; // String | The ID of the timecard to list events for.
try {
    List<V1TimecardEvent> result = apiInstance.listTimecardEvents(timecardId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listTimecardEvents");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **timecardId** | **String**| The ID of the timecard to list events for. |

### Return type

[**List&lt;V1TimecardEvent&gt;**](V1TimecardEvent.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listTimecards"></a>
# **listTimecards**
> List&lt;V1Timecard&gt; listTimecards(order, employeeId, beginClockinTime, endClockinTime, beginClockoutTime, endClockoutTime, beginUpdatedAt, endUpdatedAt, deleted, limit, batchToken)

ListTimecards

Provides summary information for all of a business&#39;s employee timecards.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String order = "order_example"; // String | The order in which timecards are listed in the response, based on their created_at field.
String employeeId = "employeeId_example"; // String | If provided, the endpoint returns only timecards for the employee with the specified ID.
String beginClockinTime = "beginClockinTime_example"; // String | If filtering results by their clockin_time field, the beginning of the requested reporting period, in ISO 8601 format.
String endClockinTime = "endClockinTime_example"; // String | If filtering results by their clockin_time field, the end of the requested reporting period, in ISO 8601 format.
String beginClockoutTime = "beginClockoutTime_example"; // String | If filtering results by their clockout_time field, the beginning of the requested reporting period, in ISO 8601 format.
String endClockoutTime = "endClockoutTime_example"; // String | If filtering results by their clockout_time field, the end of the requested reporting period, in ISO 8601 format.
String beginUpdatedAt = "beginUpdatedAt_example"; // String | If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format.
String endUpdatedAt = "endUpdatedAt_example"; // String | If filtering results by their updated_at field, the end of the requested reporting period, in ISO 8601 format.
Boolean deleted = true; // Boolean | If true, only deleted timecards are returned. If false, only valid timecards are returned.If you don't provide this parameter, both valid and deleted timecards are returned.
Integer limit = 56; // Integer | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1Timecard> result = apiInstance.listTimecards(order, employeeId, beginClockinTime, endClockinTime, beginClockoutTime, endClockoutTime, beginUpdatedAt, endUpdatedAt, deleted, limit, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listTimecards");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **order** | **String**| The order in which timecards are listed in the response, based on their created_at field. | [optional]
 **employeeId** | **String**| If provided, the endpoint returns only timecards for the employee with the specified ID. | [optional]
 **beginClockinTime** | **String**| If filtering results by their clockin_time field, the beginning of the requested reporting period, in ISO 8601 format. | [optional]
 **endClockinTime** | **String**| If filtering results by their clockin_time field, the end of the requested reporting period, in ISO 8601 format. | [optional]
 **beginClockoutTime** | **String**| If filtering results by their clockout_time field, the beginning of the requested reporting period, in ISO 8601 format. | [optional]
 **endClockoutTime** | **String**| If filtering results by their clockout_time field, the end of the requested reporting period, in ISO 8601 format. | [optional]
 **beginUpdatedAt** | **String**| If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format. | [optional]
 **endUpdatedAt** | **String**| If filtering results by their updated_at field, the end of the requested reporting period, in ISO 8601 format. | [optional]
 **deleted** | **Boolean**| If true, only deleted timecards are returned. If false, only valid timecards are returned.If you don&#39;t provide this parameter, both valid and deleted timecards are returned. | [optional]
 **limit** | **Integer**| The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1Timecard&gt;**](V1Timecard.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveCashDrawerShift"></a>
# **retrieveCashDrawerShift**
> V1CashDrawerShift retrieveCashDrawerShift(locationId, shiftId)

RetrieveCashDrawerShift

Provides the details for a single cash drawer shift, including all events that occurred during the shift.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String locationId = "locationId_example"; // String | The ID of the location to list cash drawer shifts for.
String shiftId = "shiftId_example"; // String | The shift's ID.
try {
    V1CashDrawerShift result = apiInstance.retrieveCashDrawerShift(locationId, shiftId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#retrieveCashDrawerShift");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list cash drawer shifts for. |
 **shiftId** | **String**| The shift&#39;s ID. |

### Return type

[**V1CashDrawerShift**](V1CashDrawerShift.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveEmployee"></a>
# **retrieveEmployee**
> V1Employee retrieveEmployee(employeeId)

RetrieveEmployee

Provides the details for a single employee.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String employeeId = "employeeId_example"; // String | The employee's ID.
try {
    V1Employee result = apiInstance.retrieveEmployee(employeeId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#retrieveEmployee");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **employeeId** | **String**| The employee&#39;s ID. |

### Return type

[**V1Employee**](V1Employee.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveEmployeeRole"></a>
# **retrieveEmployeeRole**
> V1EmployeeRole retrieveEmployeeRole(roleId)

RetrieveEmployeeRole

Provides the details for a single employee role.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String roleId = "roleId_example"; // String | The role's ID.
try {
    V1EmployeeRole result = apiInstance.retrieveEmployeeRole(roleId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#retrieveEmployeeRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **roleId** | **String**| The role&#39;s ID. |

### Return type

[**V1EmployeeRole**](V1EmployeeRole.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveTimecard"></a>
# **retrieveTimecard**
> V1Timecard retrieveTimecard(timecardId)

RetrieveTimecard

Provides the details for a single timecard. &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String timecardId = "timecardId_example"; // String | The timecard's ID.
try {
    V1Timecard result = apiInstance.retrieveTimecard(timecardId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#retrieveTimecard");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **timecardId** | **String**| The timecard&#39;s ID. |

### Return type

[**V1Timecard**](V1Timecard.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateEmployee"></a>
# **updateEmployee**
> V1Employee updateEmployee(employeeId, body)

UpdateEmployee



### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String employeeId = "employeeId_example"; // String | The ID of the role to modify.
V1Employee body = new V1Employee(); // V1Employee | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1Employee result = apiInstance.updateEmployee(employeeId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#updateEmployee");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **employeeId** | **String**| The ID of the role to modify. |
 **body** | [**V1Employee**](V1Employee.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1Employee**](V1Employee.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateEmployeeRole"></a>
# **updateEmployeeRole**
> V1EmployeeRole updateEmployeeRole(roleId, body)

UpdateEmployeeRole

Modifies the details of an employee role.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String roleId = "roleId_example"; // String | The ID of the role to modify.
V1EmployeeRole body = new V1EmployeeRole(); // V1EmployeeRole | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1EmployeeRole result = apiInstance.updateEmployeeRole(roleId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#updateEmployeeRole");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **roleId** | **String**| The ID of the role to modify. |
 **body** | [**V1EmployeeRole**](V1EmployeeRole.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1EmployeeRole**](V1EmployeeRole.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateTimecard"></a>
# **updateTimecard**
> V1Timecard updateTimecard(timecardId, body)

UpdateTimecard

Modifies the details of a timecard with an &#x60;API_EDIT&#x60; event for the timecard. Updating an active timecard with a &#x60;clockout_time&#x60; clocks the employee out.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String timecardId = "timecardId_example"; // String | TThe ID of the timecard to modify.
V1Timecard body = new V1Timecard(); // V1Timecard | An object containing the fields to POST for the request. See the corresponding object definition for field details.
try {
    V1Timecard result = apiInstance.updateTimecard(timecardId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#updateTimecard");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **timecardId** | **String**| TThe ID of the timecard to modify. |
 **body** | [**V1Timecard**](V1Timecard.md)| An object containing the fields to POST for the request. See the corresponding object definition for field details. |

### Return type

[**V1Timecard**](V1Timecard.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

