package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.V1CashDrawerShift;
import com.squareup.connect.models.V1Employee;
import com.squareup.connect.models.V1EmployeeRole;
import com.squareup.connect.models.V1Timecard;
import com.squareup.connect.models.V1TimecardEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class V1EmployeesApi {
  private ApiClient apiClient;

  public V1EmployeesApi() {
    this(Configuration.getDefaultApiClient());
  }

  public V1EmployeesApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CreateEmployee
   *  Use the CreateEmployee endpoint to add an employee to a Square account. Employees created with the Connect API have an initial status of &#x60;INACTIVE&#x60;. Inactive employees cannot sign in to Square Point of Sale until they are activated from the Square Dashboard. Employee status cannot be changed with the Connect API.  &lt;aside class&#x3D;\&quot;important\&quot;&gt; Employee entities cannot be deleted. To disable employee profiles, set the employee&#39;s status to &lt;code&gt;INACTIVE&lt;/code&gt; &lt;/aside&gt;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Employee
   * @throws ApiException if fails to make API call
   */  public V1Employee createEmployee(V1Employee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    CompleteResponse<V1Employee> completeResponse = (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateEmployee
   *  Use the CreateEmployee endpoint to add an employee to a Square account. Employees created with the Connect API have an initial status of &#x60;INACTIVE&#x60;. Inactive employees cannot sign in to Square Point of Sale until they are activated from the Square Dashboard. Employee status cannot be changed with the Connect API.  &lt;aside class&#x3D;\&quot;important\&quot;&gt; Employee entities cannot be deleted. To disable employee profiles, set the employee&#39;s status to &lt;code&gt;INACTIVE&lt;/code&gt; &lt;/aside&gt;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Employee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Employee>createEmployeeWithHttpInfo(V1Employee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    return (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateEmployeeRole
   * Creates an employee role you can then assign to employees.  Square accounts can include any number of roles that can be assigned to employees. These roles define the actions and permissions granted to an employee with that role. For example, an employee with a \&quot;Shift Manager\&quot; role might be able to issue refunds in Square Point of Sale, whereas an employee with a \&quot;Clerk\&quot; role might not.  Roles are assigned with the [V1UpdateEmployee](#endpoint-v1updateemployee) endpoint. An employee can have only one role at a time.  If an employee has no role, they have none of the permissions associated with roles. All employees can accept payments with Square Point of Sale.
   * @param employeeRole An EmployeeRole object with a name and permissions, and an optional owner flag. (required)
   * @return V1EmployeeRole
   * @throws ApiException if fails to make API call
   */  public V1EmployeeRole createEmployeeRole(V1EmployeeRole employeeRole) throws ApiException {
    Object localVarPostBody = employeeRole;

    // verify the required parameter 'employeeRole' is set
    if (employeeRole == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeRole' when calling createEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    CompleteResponse<V1EmployeeRole> completeResponse = (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateEmployeeRole
   * Creates an employee role you can then assign to employees.  Square accounts can include any number of roles that can be assigned to employees. These roles define the actions and permissions granted to an employee with that role. For example, an employee with a \&quot;Shift Manager\&quot; role might be able to issue refunds in Square Point of Sale, whereas an employee with a \&quot;Clerk\&quot; role might not.  Roles are assigned with the [V1UpdateEmployee](#endpoint-v1updateemployee) endpoint. An employee can have only one role at a time.  If an employee has no role, they have none of the permissions associated with roles. All employees can accept payments with Square Point of Sale.
   * @param employeeRole An EmployeeRole object with a name and permissions, and an optional owner flag. (required)
   * @return CompleteResponse<V1EmployeeRole>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1EmployeeRole>createEmployeeRoleWithHttpInfo(V1EmployeeRole employeeRole) throws ApiException {
    Object localVarPostBody = employeeRole;

    // verify the required parameter 'employeeRole' is set
    if (employeeRole == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeRole' when calling createEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    return (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateTimecard
   * Creates a timecard for an employee and clocks them in with an &#x60;API_CREATE&#x60; event and a &#x60;clockin_time&#x60; set to the current time unless the request provides a different value. To import timecards from another system (rather than clocking someone in). Specify the &#x60;clockin_time&#x60; and* &#x60;clockout_time&#x60; in the request.  Timecards correspond to exactly one shift for a given employee, bounded by the &#x60;clockin_time&#x60; and &#x60;clockout_time&#x60; fields. An employee is considered clocked in if they have a timecard that doesn&#39;t have a &#x60;clockout_time&#x60; set. An employee that is currently clocked in cannot be clocked in a second time.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Timecard
   * @throws ApiException if fails to make API call
   */  public V1Timecard createTimecard(V1Timecard body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    CompleteResponse<V1Timecard> completeResponse = (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateTimecard
   * Creates a timecard for an employee and clocks them in with an &#x60;API_CREATE&#x60; event and a &#x60;clockin_time&#x60; set to the current time unless the request provides a different value. To import timecards from another system (rather than clocking someone in). Specify the &#x60;clockin_time&#x60; and* &#x60;clockout_time&#x60; in the request.  Timecards correspond to exactly one shift for a given employee, bounded by the &#x60;clockin_time&#x60; and &#x60;clockout_time&#x60; fields. An employee is considered clocked in if they have a timecard that doesn&#39;t have a &#x60;clockout_time&#x60; set. An employee that is currently clocked in cannot be clocked in a second time.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Timecard>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Timecard>createTimecardWithHttpInfo(V1Timecard body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    return (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteTimecard
   * Deletes a timecard. Timecards can also be deleted through the Square Dashboard. Deleted timecards are still accessible through Connect API endpoints, but cannot be modified. The &#x60;deleted&#x60; field of the &#x60;Timecard&#x60; object indicates whether the timecard has been deleted.  *Note**: By default, deleted timecards appear alongside valid timecards in results returned by the [ListTimecards](#endpoint-v1employees-listtimecards) endpoint. To filter deleted timecards, include the &#x60;deleted&#x60; query parameter in the list request.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The ID of the timecard to delete. (required)
   * @return Object
   * @throws ApiException if fails to make API call
   */  public Object deleteTimecard(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling deleteTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<Object> localVarReturnType = new GenericType<Object>() {};
    CompleteResponse<Object> completeResponse = (CompleteResponse<Object>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteTimecard
   * Deletes a timecard. Timecards can also be deleted through the Square Dashboard. Deleted timecards are still accessible through Connect API endpoints, but cannot be modified. The &#x60;deleted&#x60; field of the &#x60;Timecard&#x60; object indicates whether the timecard has been deleted.  *Note**: By default, deleted timecards appear alongside valid timecards in results returned by the [ListTimecards](#endpoint-v1employees-listtimecards) endpoint. To filter deleted timecards, include the &#x60;deleted&#x60; query parameter in the list request.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The ID of the timecard to delete. (required)
   * @return CompleteResponse<Object>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<Object>deleteTimecardWithHttpInfo(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling deleteTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<Object> localVarReturnType = new GenericType<Object>() {};
    return (CompleteResponse<Object>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListCashDrawerShifts
   * Provides the details for all of a location&#39;s cash drawer shifts during a date range. The date range you specify cannot exceed 90 days.
   * @param locationId The ID of the location to list cash drawer shifts for. (required)
   * @param order The order in which cash drawer shifts are listed in the response, based on their created_at field. Default value: ASC (optional)
   * @param beginTime The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time minus 90 days. (optional)
   * @param endTime The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time. (optional)
   * @return List&lt;V1CashDrawerShift&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1CashDrawerShift> listCashDrawerShifts(String locationId, String order, String beginTime, String endTime) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listCashDrawerShifts");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/cash-drawer-shifts"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1CashDrawerShift>> localVarReturnType = new GenericType<List<V1CashDrawerShift>>() {};
    CompleteResponse<List<V1CashDrawerShift>> completeResponse = (CompleteResponse<List<V1CashDrawerShift>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListCashDrawerShifts
   * Provides the details for all of a location&#39;s cash drawer shifts during a date range. The date range you specify cannot exceed 90 days.
   * @param locationId The ID of the location to list cash drawer shifts for. (required)
   * @param order The order in which cash drawer shifts are listed in the response, based on their created_at field. Default value: ASC (optional)
   * @param beginTime The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time minus 90 days. (optional)
   * @param endTime The beginning of the requested reporting period, in ISO 8601 format. Default value: The current time. (optional)
   * @return CompleteResponse<List<V1CashDrawerShift>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1CashDrawerShift>>listCashDrawerShiftsWithHttpInfo(String locationId, String order, String beginTime, String endTime) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listCashDrawerShifts");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/cash-drawer-shifts"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1CashDrawerShift>> localVarReturnType = new GenericType<List<V1CashDrawerShift>>() {};
    return (CompleteResponse<List<V1CashDrawerShift>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListEmployeeRoles
   * Provides summary information for all of a business&#39;s employee roles.
   * @param order The order in which employees are listed in the response, based on their created_at field.Default value: ASC (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return List&lt;V1EmployeeRole&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1EmployeeRole> listEmployeeRoles(String order, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/roles";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1EmployeeRole>> localVarReturnType = new GenericType<List<V1EmployeeRole>>() {};
    CompleteResponse<List<V1EmployeeRole>> completeResponse = (CompleteResponse<List<V1EmployeeRole>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListEmployeeRoles
   * Provides summary information for all of a business&#39;s employee roles.
   * @param order The order in which employees are listed in the response, based on their created_at field.Default value: ASC (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return CompleteResponse<List<V1EmployeeRole>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1EmployeeRole>>listEmployeeRolesWithHttpInfo(String order, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/roles";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1EmployeeRole>> localVarReturnType = new GenericType<List<V1EmployeeRole>>() {};
    return (CompleteResponse<List<V1EmployeeRole>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListEmployees
   * Provides summary information for all of a business&#39;s employees.
   * @param order The order in which employees are listed in the response, based on their created_at field.      Default value: ASC (optional)
   * @param beginUpdatedAt If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format (optional)
   * @param endUpdatedAt If filtering results by there updated_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginCreatedAt If filtering results by their created_at field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endCreatedAt If filtering results by their created_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param status If provided, the endpoint returns only employee entities with the specified status (ACTIVE or INACTIVE). (optional)
   * @param externalId If provided, the endpoint returns only employee entities with the specified external_id. (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return List&lt;V1Employee&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Employee> listEmployees(String order, String beginUpdatedAt, String endUpdatedAt, String beginCreatedAt, String endCreatedAt, String status, String externalId, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_updated_at", beginUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_updated_at", endUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_created_at", beginCreatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_created_at", endCreatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "external_id", externalId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Employee>> localVarReturnType = new GenericType<List<V1Employee>>() {};
    CompleteResponse<List<V1Employee>> completeResponse = (CompleteResponse<List<V1Employee>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListEmployees
   * Provides summary information for all of a business&#39;s employees.
   * @param order The order in which employees are listed in the response, based on their created_at field.      Default value: ASC (optional)
   * @param beginUpdatedAt If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format (optional)
   * @param endUpdatedAt If filtering results by there updated_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginCreatedAt If filtering results by their created_at field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endCreatedAt If filtering results by their created_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param status If provided, the endpoint returns only employee entities with the specified status (ACTIVE or INACTIVE). (optional)
   * @param externalId If provided, the endpoint returns only employee entities with the specified external_id. (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return CompleteResponse<List<V1Employee>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Employee>>listEmployeesWithHttpInfo(String order, String beginUpdatedAt, String endUpdatedAt, String beginCreatedAt, String endCreatedAt, String status, String externalId, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_updated_at", beginUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_updated_at", endUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_created_at", beginCreatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_created_at", endCreatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "external_id", externalId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Employee>> localVarReturnType = new GenericType<List<V1Employee>>() {};
    return (CompleteResponse<List<V1Employee>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListTimecardEvents
   * Provides summary information for all events associated with a particular timecard.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The ID of the timecard to list events for. (required)
   * @return List&lt;V1TimecardEvent&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1TimecardEvent> listTimecardEvents(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling listTimecardEvents");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}/events"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1TimecardEvent>> localVarReturnType = new GenericType<List<V1TimecardEvent>>() {};
    CompleteResponse<List<V1TimecardEvent>> completeResponse = (CompleteResponse<List<V1TimecardEvent>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListTimecardEvents
   * Provides summary information for all events associated with a particular timecard.  &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The ID of the timecard to list events for. (required)
   * @return CompleteResponse<List<V1TimecardEvent>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1TimecardEvent>>listTimecardEventsWithHttpInfo(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling listTimecardEvents");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}/events"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1TimecardEvent>> localVarReturnType = new GenericType<List<V1TimecardEvent>>() {};
    return (CompleteResponse<List<V1TimecardEvent>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListTimecards
   * Provides summary information for all of a business&#39;s employee timecards.
   * @param order The order in which timecards are listed in the response, based on their created_at field. (optional)
   * @param employeeId If provided, the endpoint returns only timecards for the employee with the specified ID. (optional)
   * @param beginClockinTime If filtering results by their clockin_time field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endClockinTime If filtering results by their clockin_time field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginClockoutTime If filtering results by their clockout_time field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endClockoutTime If filtering results by their clockout_time field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginUpdatedAt If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endUpdatedAt If filtering results by their updated_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param deleted If true, only deleted timecards are returned. If false, only valid timecards are returned.If you don&#39;t provide this parameter, both valid and deleted timecards are returned. (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return List&lt;V1Timecard&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Timecard> listTimecards(String order, String employeeId, String beginClockinTime, String endClockinTime, String beginClockoutTime, String endClockoutTime, String beginUpdatedAt, String endUpdatedAt, Boolean deleted, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/timecards";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "employee_id", employeeId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_clockin_time", beginClockinTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_clockin_time", endClockinTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_clockout_time", beginClockoutTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_clockout_time", endClockoutTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_updated_at", beginUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_updated_at", endUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "deleted", deleted));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Timecard>> localVarReturnType = new GenericType<List<V1Timecard>>() {};
    CompleteResponse<List<V1Timecard>> completeResponse = (CompleteResponse<List<V1Timecard>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListTimecards
   * Provides summary information for all of a business&#39;s employee timecards.
   * @param order The order in which timecards are listed in the response, based on their created_at field. (optional)
   * @param employeeId If provided, the endpoint returns only timecards for the employee with the specified ID. (optional)
   * @param beginClockinTime If filtering results by their clockin_time field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endClockinTime If filtering results by their clockin_time field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginClockoutTime If filtering results by their clockout_time field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endClockoutTime If filtering results by their clockout_time field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param beginUpdatedAt If filtering results by their updated_at field, the beginning of the requested reporting period, in ISO 8601 format. (optional)
   * @param endUpdatedAt If filtering results by their updated_at field, the end of the requested reporting period, in ISO 8601 format. (optional)
   * @param deleted If true, only deleted timecards are returned. If false, only valid timecards are returned.If you don&#39;t provide this parameter, both valid and deleted timecards are returned. (optional)
   * @param limit The maximum integer number of employee entities to return in a single response. Default 100, maximum 200. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return CompleteResponse<List<V1Timecard>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Timecard>>listTimecardsWithHttpInfo(String order, String employeeId, String beginClockinTime, String endClockinTime, String beginClockoutTime, String endClockoutTime, String beginUpdatedAt, String endUpdatedAt, Boolean deleted, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v1/me/timecards";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "order", order));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "employee_id", employeeId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_clockin_time", beginClockinTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_clockin_time", endClockinTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_clockout_time", beginClockoutTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_clockout_time", endClockoutTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_updated_at", beginUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_updated_at", endUpdatedAt));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "deleted", deleted));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Timecard>> localVarReturnType = new GenericType<List<V1Timecard>>() {};
    return (CompleteResponse<List<V1Timecard>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveCashDrawerShift
   * Provides the details for a single cash drawer shift, including all events that occurred during the shift.
   * @param locationId The ID of the location to list cash drawer shifts for. (required)
   * @param shiftId The shift&#39;s ID. (required)
   * @return V1CashDrawerShift
   * @throws ApiException if fails to make API call
   */  public V1CashDrawerShift retrieveCashDrawerShift(String locationId, String shiftId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveCashDrawerShift");
    }

    // verify the required parameter 'shiftId' is set
    if (shiftId == null) {
      throw new ApiException(400, "Missing the required parameter 'shiftId' when calling retrieveCashDrawerShift");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/cash-drawer-shifts/{shift_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "shift_id" + "\\}", apiClient.escapeString(shiftId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1CashDrawerShift> localVarReturnType = new GenericType<V1CashDrawerShift>() {};
    CompleteResponse<V1CashDrawerShift> completeResponse = (CompleteResponse<V1CashDrawerShift>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveCashDrawerShift
   * Provides the details for a single cash drawer shift, including all events that occurred during the shift.
   * @param locationId The ID of the location to list cash drawer shifts for. (required)
   * @param shiftId The shift&#39;s ID. (required)
   * @return CompleteResponse<V1CashDrawerShift>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1CashDrawerShift>retrieveCashDrawerShiftWithHttpInfo(String locationId, String shiftId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveCashDrawerShift");
    }

    // verify the required parameter 'shiftId' is set
    if (shiftId == null) {
      throw new ApiException(400, "Missing the required parameter 'shiftId' when calling retrieveCashDrawerShift");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/cash-drawer-shifts/{shift_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "shift_id" + "\\}", apiClient.escapeString(shiftId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1CashDrawerShift> localVarReturnType = new GenericType<V1CashDrawerShift>() {};
    return (CompleteResponse<V1CashDrawerShift>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveEmployee
   * Provides the details for a single employee.
   * @param employeeId The employee&#39;s ID. (required)
   * @return V1Employee
   * @throws ApiException if fails to make API call
   */  public V1Employee retrieveEmployee(String employeeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeId' when calling retrieveEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees/{employee_id}"
      .replaceAll("\\{" + "employee_id" + "\\}", apiClient.escapeString(employeeId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    CompleteResponse<V1Employee> completeResponse = (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveEmployee
   * Provides the details for a single employee.
   * @param employeeId The employee&#39;s ID. (required)
   * @return CompleteResponse<V1Employee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Employee>retrieveEmployeeWithHttpInfo(String employeeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeId' when calling retrieveEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees/{employee_id}"
      .replaceAll("\\{" + "employee_id" + "\\}", apiClient.escapeString(employeeId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    return (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveEmployeeRole
   * Provides the details for a single employee role.
   * @param roleId The role&#39;s ID. (required)
   * @return V1EmployeeRole
   * @throws ApiException if fails to make API call
   */  public V1EmployeeRole retrieveEmployeeRole(String roleId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'roleId' is set
    if (roleId == null) {
      throw new ApiException(400, "Missing the required parameter 'roleId' when calling retrieveEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles/{role_id}"
      .replaceAll("\\{" + "role_id" + "\\}", apiClient.escapeString(roleId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    CompleteResponse<V1EmployeeRole> completeResponse = (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveEmployeeRole
   * Provides the details for a single employee role.
   * @param roleId The role&#39;s ID. (required)
   * @return CompleteResponse<V1EmployeeRole>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1EmployeeRole>retrieveEmployeeRoleWithHttpInfo(String roleId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'roleId' is set
    if (roleId == null) {
      throw new ApiException(400, "Missing the required parameter 'roleId' when calling retrieveEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles/{role_id}"
      .replaceAll("\\{" + "role_id" + "\\}", apiClient.escapeString(roleId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    return (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveTimecard
   * Provides the details for a single timecard. &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The timecard&#39;s ID. (required)
   * @return V1Timecard
   * @throws ApiException if fails to make API call
   */  public V1Timecard retrieveTimecard(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling retrieveTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    CompleteResponse<V1Timecard> completeResponse = (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveTimecard
   * Provides the details for a single timecard. &lt;aside&gt; Only approved accounts can manage their employees with Square. Unapproved accounts cannot use employee management features with the API. &lt;/aside&gt;
   * @param timecardId The timecard&#39;s ID. (required)
   * @return CompleteResponse<V1Timecard>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Timecard>retrieveTimecardWithHttpInfo(String timecardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling retrieveTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    return (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateEmployee
   *
   * @param employeeId The ID of the role to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Employee
   * @throws ApiException if fails to make API call
   */  public V1Employee updateEmployee(String employeeId, V1Employee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeId' when calling updateEmployee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees/{employee_id}"
      .replaceAll("\\{" + "employee_id" + "\\}", apiClient.escapeString(employeeId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    CompleteResponse<V1Employee> completeResponse = (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateEmployee
   *
   * @param employeeId The ID of the role to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Employee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Employee>updateEmployeeWithHttpInfo(String employeeId, V1Employee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'employeeId' is set
    if (employeeId == null) {
      throw new ApiException(400, "Missing the required parameter 'employeeId' when calling updateEmployee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateEmployee");
    }

    // create path and map variables
    String localVarPath = "/v1/me/employees/{employee_id}"
      .replaceAll("\\{" + "employee_id" + "\\}", apiClient.escapeString(employeeId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Employee> localVarReturnType = new GenericType<V1Employee>() {};
    return (CompleteResponse<V1Employee>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateEmployeeRole
   * Modifies the details of an employee role.
   * @param roleId The ID of the role to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1EmployeeRole
   * @throws ApiException if fails to make API call
   */  public V1EmployeeRole updateEmployeeRole(String roleId, V1EmployeeRole body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'roleId' is set
    if (roleId == null) {
      throw new ApiException(400, "Missing the required parameter 'roleId' when calling updateEmployeeRole");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles/{role_id}"
      .replaceAll("\\{" + "role_id" + "\\}", apiClient.escapeString(roleId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    CompleteResponse<V1EmployeeRole> completeResponse = (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateEmployeeRole
   * Modifies the details of an employee role.
   * @param roleId The ID of the role to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1EmployeeRole>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1EmployeeRole>updateEmployeeRoleWithHttpInfo(String roleId, V1EmployeeRole body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'roleId' is set
    if (roleId == null) {
      throw new ApiException(400, "Missing the required parameter 'roleId' when calling updateEmployeeRole");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateEmployeeRole");
    }

    // create path and map variables
    String localVarPath = "/v1/me/roles/{role_id}"
      .replaceAll("\\{" + "role_id" + "\\}", apiClient.escapeString(roleId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1EmployeeRole> localVarReturnType = new GenericType<V1EmployeeRole>() {};
    return (CompleteResponse<V1EmployeeRole>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateTimecard
   * Modifies the details of a timecard with an &#x60;API_EDIT&#x60; event for the timecard. Updating an active timecard with a &#x60;clockout_time&#x60; clocks the employee out.
   * @param timecardId TThe ID of the timecard to modify. (required)
   * @param body An object containing the fields to POST for the request. See the corresponding object definition for field details. (required)
   * @return V1Timecard
   * @throws ApiException if fails to make API call
   */  public V1Timecard updateTimecard(String timecardId, V1Timecard body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling updateTimecard");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    CompleteResponse<V1Timecard> completeResponse = (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateTimecard
   * Modifies the details of a timecard with an &#x60;API_EDIT&#x60; event for the timecard. Updating an active timecard with a &#x60;clockout_time&#x60; clocks the employee out.
   * @param timecardId TThe ID of the timecard to modify. (required)
   * @param body An object containing the fields to POST for the request. See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Timecard>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Timecard>updateTimecardWithHttpInfo(String timecardId, V1Timecard body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'timecardId' is set
    if (timecardId == null) {
      throw new ApiException(400, "Missing the required parameter 'timecardId' when calling updateTimecard");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateTimecard");
    }

    // create path and map variables
    String localVarPath = "/v1/me/timecards/{timecard_id}"
      .replaceAll("\\{" + "timecard_id" + "\\}", apiClient.escapeString(timecardId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Timecard> localVarReturnType = new GenericType<V1Timecard>() {};
    return (CompleteResponse<V1Timecard>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
