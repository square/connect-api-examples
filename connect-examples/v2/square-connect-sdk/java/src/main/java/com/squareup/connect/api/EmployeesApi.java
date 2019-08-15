package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.ListEmployeesResponse;
import com.squareup.connect.models.RetrieveEmployeeResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeesApi {
  private ApiClient apiClient;

  public EmployeesApi() {
    this(Configuration.getDefaultApiClient());
  }

  public EmployeesApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * ListEmployees
   * Gets a list of &#x60;Employee&#x60; objects for a business.
   * @param locationId Filter employees returned to only those that are associated with the specified location. (optional)
   * @param status Specifies the EmployeeStatus to filter the employee by. (optional)
   * @param limit The number of employees to be returned on each page. (optional)
   * @param cursor The token required to retrieve the specified page of results. (optional)
   * @return ListEmployeesResponse
   * @throws ApiException if fails to make API call
   */  public ListEmployeesResponse listEmployees(String locationId, String status, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
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

    GenericType<ListEmployeesResponse> localVarReturnType = new GenericType<ListEmployeesResponse>() {};
    CompleteResponse<ListEmployeesResponse> completeResponse = (CompleteResponse<ListEmployeesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListEmployees
   * Gets a list of &#x60;Employee&#x60; objects for a business.
   * @param locationId Filter employees returned to only those that are associated with the specified location. (optional)
   * @param status Specifies the EmployeeStatus to filter the employee by. (optional)
   * @param limit The number of employees to be returned on each page. (optional)
   * @param cursor The token required to retrieve the specified page of results. (optional)
   * @return CompleteResponse<ListEmployeesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListEmployeesResponse>listEmployeesWithHttpInfo(String locationId, String status, Integer limit, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/employees";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
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

    GenericType<ListEmployeesResponse> localVarReturnType = new GenericType<ListEmployeesResponse>() {};
    return (CompleteResponse<ListEmployeesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveEmployee
   * Gets an &#x60;Employee&#x60; by Square-assigned employee &#x60;ID&#x60; (UUID)
   * @param id UUID for the employee that was requested. (required)
   * @return RetrieveEmployeeResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveEmployeeResponse retrieveEmployee(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling retrieveEmployee");
    }

    // create path and map variables
    String localVarPath = "/v2/employees/{id}"
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

    GenericType<RetrieveEmployeeResponse> localVarReturnType = new GenericType<RetrieveEmployeeResponse>() {};
    CompleteResponse<RetrieveEmployeeResponse> completeResponse = (CompleteResponse<RetrieveEmployeeResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveEmployee
   * Gets an &#x60;Employee&#x60; by Square-assigned employee &#x60;ID&#x60; (UUID)
   * @param id UUID for the employee that was requested. (required)
   * @return CompleteResponse<RetrieveEmployeeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveEmployeeResponse>retrieveEmployeeWithHttpInfo(String id) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new ApiException(400, "Missing the required parameter 'id' when calling retrieveEmployee");
    }

    // create path and map variables
    String localVarPath = "/v2/employees/{id}"
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

    GenericType<RetrieveEmployeeResponse> localVarReturnType = new GenericType<RetrieveEmployeeResponse>() {};
    return (CompleteResponse<RetrieveEmployeeResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
