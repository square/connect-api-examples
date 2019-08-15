package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CreateBreakTypeRequest;
import com.squareup.connect.models.CreateBreakTypeResponse;
import com.squareup.connect.models.CreateShiftRequest;
import com.squareup.connect.models.CreateShiftResponse;
import com.squareup.connect.models.DeleteBreakTypeResponse;
import com.squareup.connect.models.DeleteShiftResponse;
import com.squareup.connect.models.GetBreakTypeResponse;
import com.squareup.connect.models.GetEmployeeWageResponse;
import com.squareup.connect.models.GetShiftResponse;
import com.squareup.connect.models.ListBreakTypesResponse;
import com.squareup.connect.models.ListEmployeeWagesResponse;
import com.squareup.connect.models.ListWorkweekConfigsResponse;
import com.squareup.connect.models.SearchShiftsRequest;
import com.squareup.connect.models.SearchShiftsResponse;
import com.squareup.connect.models.UpdateBreakTypeRequest;
import com.squareup.connect.models.UpdateBreakTypeResponse;
import com.squareup.connect.models.UpdateShiftRequest;
import com.squareup.connect.models.UpdateShiftResponse;
import com.squareup.connect.models.UpdateWorkweekConfigRequest;
import com.squareup.connect.models.UpdateWorkweekConfigResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LaborApi {
  private ApiClient apiClient;

  public LaborApi() {
    this(Configuration.getDefaultApiClient());
  }

  public LaborApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CreateBreakType
   * Creates a new &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; is a template for creating &#x60;Break&#x60; objects.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;break_name&#x60; - &#x60;expected_duration&#x60; - &#x60;is_paid&#x60;  You can only have 3 &#x60;BreakType&#x60; instances per location. If you attempt to add a 4th &#x60;BreakType&#x60; for a location, an &#x60;INVALID_REQUEST_ERROR&#x60; \&quot;Exceeded limit of 3 breaks per location.\&quot; is returned.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateBreakTypeResponse
   * @throws ApiException if fails to make API call
   */  public CreateBreakTypeResponse createBreakType(CreateBreakTypeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types";

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

    GenericType<CreateBreakTypeResponse> localVarReturnType = new GenericType<CreateBreakTypeResponse>() {};
    CompleteResponse<CreateBreakTypeResponse> completeResponse = (CompleteResponse<CreateBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateBreakType
   * Creates a new &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; is a template for creating &#x60;Break&#x60; objects.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;break_name&#x60; - &#x60;expected_duration&#x60; - &#x60;is_paid&#x60;  You can only have 3 &#x60;BreakType&#x60; instances per location. If you attempt to add a 4th &#x60;BreakType&#x60; for a location, an &#x60;INVALID_REQUEST_ERROR&#x60; \&quot;Exceeded limit of 3 breaks per location.\&quot; is returned.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateBreakTypeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateBreakTypeResponse>createBreakTypeWithHttpInfo(CreateBreakTypeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types";

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

    GenericType<CreateBreakTypeResponse> localVarReturnType = new GenericType<CreateBreakTypeResponse>() {};
    return (CompleteResponse<CreateBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateShift
   * Creates a new &#x60;Shift&#x60;.   A &#x60;Shift&#x60; represents a complete work day for a single employee.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;employee_id&#x60; - &#x60;start_at&#x60;  An attempt to create a new &#x60;Shift&#x60; can result in a &#x60;BAD_REQUEST&#x60; error when: - The &#x60;status&#x60; of the new &#x60;Shift&#x60; is &#x60;OPEN&#x60; and the employee has another  shift with an &#x60;OPEN&#x60; status.  - The &#x60;start_at&#x60; date is in the future - the &#x60;start_at&#x60; or &#x60;end_at&#x60; overlaps another shift for the same employee - If &#x60;Break&#x60;s are set in the request, a break &#x60;start_at&#x60; must not be before the &#x60;Shift.start_at&#x60;. A break &#x60;end_at&#x60; must not be after the &#x60;Shift.end_at&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateShiftResponse
   * @throws ApiException if fails to make API call
   */  public CreateShiftResponse createShift(CreateShiftRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts";

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

    GenericType<CreateShiftResponse> localVarReturnType = new GenericType<CreateShiftResponse>() {};
    CompleteResponse<CreateShiftResponse> completeResponse = (CompleteResponse<CreateShiftResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateShift
   * Creates a new &#x60;Shift&#x60;.   A &#x60;Shift&#x60; represents a complete work day for a single employee.  You must provide the following values in your request to this endpoint:  - &#x60;location_id&#x60; - &#x60;employee_id&#x60; - &#x60;start_at&#x60;  An attempt to create a new &#x60;Shift&#x60; can result in a &#x60;BAD_REQUEST&#x60; error when: - The &#x60;status&#x60; of the new &#x60;Shift&#x60; is &#x60;OPEN&#x60; and the employee has another  shift with an &#x60;OPEN&#x60; status.  - The &#x60;start_at&#x60; date is in the future - the &#x60;start_at&#x60; or &#x60;end_at&#x60; overlaps another shift for the same employee - If &#x60;Break&#x60;s are set in the request, a break &#x60;start_at&#x60; must not be before the &#x60;Shift.start_at&#x60;. A break &#x60;end_at&#x60; must not be after the &#x60;Shift.end_at&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateShiftResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateShiftResponse>createShiftWithHttpInfo(CreateShiftRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts";

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

    GenericType<CreateShiftResponse> localVarReturnType = new GenericType<CreateShiftResponse>() {};
    return (CompleteResponse<CreateShiftResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteBreakType
   * Deletes an existing &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; can be deleted even if it is referenced from a &#x60;Shift&#x60;.
   * @param id UUID for the &#x60;BreakType&#x60; being deleted. (required)
   * @return DeleteBreakTypeResponse
   * @throws ApiException if fails to make API call
   */  public DeleteBreakTypeResponse deleteBreakType(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<DeleteBreakTypeResponse> localVarReturnType = new GenericType<DeleteBreakTypeResponse>() {};
    CompleteResponse<DeleteBreakTypeResponse> completeResponse = (CompleteResponse<DeleteBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteBreakType
   * Deletes an existing &#x60;BreakType&#x60;.   A &#x60;BreakType&#x60; can be deleted even if it is referenced from a &#x60;Shift&#x60;.
   * @param id UUID for the &#x60;BreakType&#x60; being deleted. (required)
   * @return CompleteResponse<DeleteBreakTypeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<DeleteBreakTypeResponse>deleteBreakTypeWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<DeleteBreakTypeResponse> localVarReturnType = new GenericType<DeleteBreakTypeResponse>() {};
    return (CompleteResponse<DeleteBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteShift
   * Deletes a &#x60;Shift&#x60;.
   * @param id UUID for the &#x60;Shift&#x60; being deleted. (required)
   * @return DeleteShiftResponse
   * @throws ApiException if fails to make API call
   */  public DeleteShiftResponse deleteShift(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<DeleteShiftResponse> localVarReturnType = new GenericType<DeleteShiftResponse>() {};
    CompleteResponse<DeleteShiftResponse> completeResponse = (CompleteResponse<DeleteShiftResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteShift
   * Deletes a &#x60;Shift&#x60;.
   * @param id UUID for the &#x60;Shift&#x60; being deleted. (required)
   * @return CompleteResponse<DeleteShiftResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<DeleteShiftResponse>deleteShiftWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling deleteShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<DeleteShiftResponse> localVarReturnType = new GenericType<DeleteShiftResponse>() {};
    return (CompleteResponse<DeleteShiftResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * GetBreakType
   * Returns a single &#x60;BreakType&#x60; specified by id.
   * @param id UUID for the &#x60;BreakType&#x60; being retrieved. (required)
   * @return GetBreakTypeResponse
   * @throws ApiException if fails to make API call
   */  public GetBreakTypeResponse getBreakType(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetBreakTypeResponse> localVarReturnType = new GenericType<GetBreakTypeResponse>() {};
    CompleteResponse<GetBreakTypeResponse> completeResponse = (CompleteResponse<GetBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * GetBreakType
   * Returns a single &#x60;BreakType&#x60; specified by id.
   * @param id UUID for the &#x60;BreakType&#x60; being retrieved. (required)
   * @return CompleteResponse<GetBreakTypeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<GetBreakTypeResponse>getBreakTypeWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetBreakTypeResponse> localVarReturnType = new GenericType<GetBreakTypeResponse>() {};
    return (CompleteResponse<GetBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * GetEmployeeWage
   * Returns a single &#x60;EmployeeWage&#x60; specified by id.
   * @param id UUID for the &#x60;EmployeeWage&#x60; being retrieved. (required)
   * @return GetEmployeeWageResponse
   * @throws ApiException if fails to make API call
   */  public GetEmployeeWageResponse getEmployeeWage(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getEmployeeWage");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/employee-wages/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetEmployeeWageResponse> localVarReturnType = new GenericType<GetEmployeeWageResponse>() {};
    CompleteResponse<GetEmployeeWageResponse> completeResponse = (CompleteResponse<GetEmployeeWageResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * GetEmployeeWage
   * Returns a single &#x60;EmployeeWage&#x60; specified by id.
   * @param id UUID for the &#x60;EmployeeWage&#x60; being retrieved. (required)
   * @return CompleteResponse<GetEmployeeWageResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<GetEmployeeWageResponse>getEmployeeWageWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getEmployeeWage");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/employee-wages/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetEmployeeWageResponse> localVarReturnType = new GenericType<GetEmployeeWageResponse>() {};
    return (CompleteResponse<GetEmployeeWageResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * GetShift
   * Returns a single &#x60;Shift&#x60; specified by id.
   * @param id UUID for the &#x60;Shift&#x60; being retrieved. (required)
   * @return GetShiftResponse
   * @throws ApiException if fails to make API call
   */  public GetShiftResponse getShift(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetShiftResponse> localVarReturnType = new GenericType<GetShiftResponse>() {};
    CompleteResponse<GetShiftResponse> completeResponse = (CompleteResponse<GetShiftResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * GetShift
   * Returns a single &#x60;Shift&#x60; specified by id.
   * @param id UUID for the &#x60;Shift&#x60; being retrieved. (required)
   * @return CompleteResponse<GetShiftResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<GetShiftResponse>getShiftWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling getShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<GetShiftResponse> localVarReturnType = new GenericType<GetShiftResponse>() {};
    return (CompleteResponse<GetShiftResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListBreakTypes
   * Returns a paginated list of &#x60;BreakType&#x60; instances for a business.
   * @param locationId Filter Break Types returned to only those that are associated with the specified location. (optional)
   * @param limit Maximum number of Break Types to return per page. Can range between 1 and 200. The default is the maximum at 200. (optional)
   * @param cursor Pointer to the next page of Break Type results to fetch. (optional)
   * @return ListBreakTypesResponse
   * @throws ApiException if fails to make API call
   */  public ListBreakTypesResponse listBreakTypes(String locationId, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/break-types";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListBreakTypesResponse> localVarReturnType = new GenericType<ListBreakTypesResponse>() {};
    CompleteResponse<ListBreakTypesResponse> completeResponse = (CompleteResponse<ListBreakTypesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListBreakTypes
   * Returns a paginated list of &#x60;BreakType&#x60; instances for a business.
   * @param locationId Filter Break Types returned to only those that are associated with the specified location. (optional)
   * @param limit Maximum number of Break Types to return per page. Can range between 1 and 200. The default is the maximum at 200. (optional)
   * @param cursor Pointer to the next page of Break Type results to fetch. (optional)
   * @return CompleteResponse<ListBreakTypesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListBreakTypesResponse>listBreakTypesWithHttpInfo(String locationId, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/break-types";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListBreakTypesResponse> localVarReturnType = new GenericType<ListBreakTypesResponse>() {};
    return (CompleteResponse<ListBreakTypesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListEmployeeWages
   * Returns a paginated list of &#x60;EmployeeWage&#x60; instances for a business.
   * @param employeeId Filter wages returned to only those that are associated with the specified employee. (optional)
   * @param limit Maximum number of Employee Wages to return per page. Can range between 1 and 200. The default is the maximum at 200. (optional)
   * @param cursor Pointer to the next page of Employee Wage results to fetch. (optional)
   * @return ListEmployeeWagesResponse
   * @throws ApiException if fails to make API call
   */  public ListEmployeeWagesResponse listEmployeeWages(String employeeId, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/employee-wages";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "employee_id", employeeId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListEmployeeWagesResponse> localVarReturnType = new GenericType<ListEmployeeWagesResponse>() {};
    CompleteResponse<ListEmployeeWagesResponse> completeResponse = (CompleteResponse<ListEmployeeWagesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListEmployeeWages
   * Returns a paginated list of &#x60;EmployeeWage&#x60; instances for a business.
   * @param employeeId Filter wages returned to only those that are associated with the specified employee. (optional)
   * @param limit Maximum number of Employee Wages to return per page. Can range between 1 and 200. The default is the maximum at 200. (optional)
   * @param cursor Pointer to the next page of Employee Wage results to fetch. (optional)
   * @return CompleteResponse<ListEmployeeWagesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListEmployeeWagesResponse>listEmployeeWagesWithHttpInfo(String employeeId, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/employee-wages";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "employee_id", employeeId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListEmployeeWagesResponse> localVarReturnType = new GenericType<ListEmployeeWagesResponse>() {};
    return (CompleteResponse<ListEmployeeWagesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListWorkweekConfigs
   * Returns a list of &#x60;WorkweekConfig&#x60; instances for a business.
   * @param limit Maximum number of Workweek Configs to return per page. (optional)
   * @param cursor Pointer to the next page of Workweek Config results to fetch. (optional)
   * @return ListWorkweekConfigsResponse
   * @throws ApiException if fails to make API call
   */  public ListWorkweekConfigsResponse listWorkweekConfigs(Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/workweek-configs";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListWorkweekConfigsResponse> localVarReturnType = new GenericType<ListWorkweekConfigsResponse>() {};
    CompleteResponse<ListWorkweekConfigsResponse> completeResponse = (CompleteResponse<ListWorkweekConfigsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListWorkweekConfigs
   * Returns a list of &#x60;WorkweekConfig&#x60; instances for a business.
   * @param limit Maximum number of Workweek Configs to return per page. (optional)
   * @param cursor Pointer to the next page of Workweek Config results to fetch. (optional)
   * @return CompleteResponse<ListWorkweekConfigsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListWorkweekConfigsResponse>listWorkweekConfigsWithHttpInfo(Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/labor/workweek-configs";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListWorkweekConfigsResponse> localVarReturnType = new GenericType<ListWorkweekConfigsResponse>() {};
    return (CompleteResponse<ListWorkweekConfigsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * SearchShifts
   * Returns a paginated list of &#x60;Shift&#x60; records for a business.  The list to be returned can be filtered by: - Location IDs **and** - employee IDs **and** - shift status (&#x60;OPEN&#x60;, &#x60;CLOSED&#x60;) **and** - shift start **and** - shift end **and** - work day details  The list can be sorted by: - &#x60;start_at&#x60; - &#x60;end_at&#x60; - &#x60;created_at&#x60; - &#x60;updated_at&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return SearchShiftsResponse
   * @throws ApiException if fails to make API call
   */  public SearchShiftsResponse searchShifts(SearchShiftsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchShifts");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/search";

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

    GenericType<SearchShiftsResponse> localVarReturnType = new GenericType<SearchShiftsResponse>() {};
    CompleteResponse<SearchShiftsResponse> completeResponse = (CompleteResponse<SearchShiftsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * SearchShifts
   * Returns a paginated list of &#x60;Shift&#x60; records for a business.  The list to be returned can be filtered by: - Location IDs **and** - employee IDs **and** - shift status (&#x60;OPEN&#x60;, &#x60;CLOSED&#x60;) **and** - shift start **and** - shift end **and** - work day details  The list can be sorted by: - &#x60;start_at&#x60; - &#x60;end_at&#x60; - &#x60;created_at&#x60; - &#x60;updated_at&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<SearchShiftsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<SearchShiftsResponse>searchShiftsWithHttpInfo(SearchShiftsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchShifts");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/search";

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

    GenericType<SearchShiftsResponse> localVarReturnType = new GenericType<SearchShiftsResponse>() {};
    return (CompleteResponse<SearchShiftsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateBreakType
   * Updates an existing &#x60;BreakType&#x60;.
   * @param id UUID for the &#x60;BreakType&#x60; being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateBreakTypeResponse
   * @throws ApiException if fails to make API call
   */  public UpdateBreakTypeResponse updateBreakType(String id, UpdateBreakTypeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateBreakType");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateBreakTypeResponse> localVarReturnType = new GenericType<UpdateBreakTypeResponse>() {};
    CompleteResponse<UpdateBreakTypeResponse> completeResponse = (CompleteResponse<UpdateBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateBreakType
   * Updates an existing &#x60;BreakType&#x60;.
   * @param id UUID for the &#x60;BreakType&#x60; being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateBreakTypeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateBreakTypeResponse>updateBreakTypeWithHttpInfo(String id, UpdateBreakTypeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateBreakType");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateBreakType");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/break-types/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateBreakTypeResponse> localVarReturnType = new GenericType<UpdateBreakTypeResponse>() {};
    return (CompleteResponse<UpdateBreakTypeResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateShift
   * Updates an existing &#x60;Shift&#x60;.   When adding a &#x60;Break&#x60; to a &#x60;Shift&#x60;, any earlier &#x60;Breaks&#x60; in the &#x60;Shift&#x60; have  the &#x60;end_at&#x60; property set to a valid RFC-3339 datetime string.   When closing a &#x60;Shift&#x60;, all &#x60;Break&#x60; instances in the shift must be complete with &#x60;end_at&#x60; set on each &#x60;Break&#x60;.
   * @param id ID of the object being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateShiftResponse
   * @throws ApiException if fails to make API call
   */  public UpdateShiftResponse updateShift(String id, UpdateShiftRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateShift");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateShiftResponse> localVarReturnType = new GenericType<UpdateShiftResponse>() {};
    CompleteResponse<UpdateShiftResponse> completeResponse = (CompleteResponse<UpdateShiftResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateShift
   * Updates an existing &#x60;Shift&#x60;.   When adding a &#x60;Break&#x60; to a &#x60;Shift&#x60;, any earlier &#x60;Breaks&#x60; in the &#x60;Shift&#x60; have  the &#x60;end_at&#x60; property set to a valid RFC-3339 datetime string.   When closing a &#x60;Shift&#x60;, all &#x60;Break&#x60; instances in the shift must be complete with &#x60;end_at&#x60; set on each &#x60;Break&#x60;.
   * @param id ID of the object being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateShiftResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateShiftResponse>updateShiftWithHttpInfo(String id, UpdateShiftRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateShift");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateShift");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/shifts/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateShiftResponse> localVarReturnType = new GenericType<UpdateShiftResponse>() {};
    return (CompleteResponse<UpdateShiftResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateWorkweekConfig
   * Updates a &#x60;WorkweekConfig&#x60;.
   * @param id UUID for the &#x60;WorkweekConfig&#x60; object being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateWorkweekConfigResponse
   * @throws ApiException if fails to make API call
   */  public UpdateWorkweekConfigResponse updateWorkweekConfig(String id, UpdateWorkweekConfigRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateWorkweekConfig");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateWorkweekConfig");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/workweek-configs/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateWorkweekConfigResponse> localVarReturnType = new GenericType<UpdateWorkweekConfigResponse>() {};
    CompleteResponse<UpdateWorkweekConfigResponse> completeResponse = (CompleteResponse<UpdateWorkweekConfigResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateWorkweekConfig
   * Updates a &#x60;WorkweekConfig&#x60;.
   * @param id UUID for the &#x60;WorkweekConfig&#x60; object being updated. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateWorkweekConfigResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateWorkweekConfigResponse>updateWorkweekConfigWithHttpInfo(String id, UpdateWorkweekConfigRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling updateWorkweekConfig");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateWorkweekConfig");
    }

    // create path and map variables
    String localVarPath = "/v2/labor/workweek-configs/{id}"
      .replaceAll("\\{" + "id" + "\\}", apiClient.escapeString(id.toString()));

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

    GenericType<UpdateWorkweekConfigResponse> localVarReturnType = new GenericType<UpdateWorkweekConfigResponse>() {};
    return (CompleteResponse<UpdateWorkweekConfigResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
