package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CreateMobileAuthorizationCodeRequest;
import com.squareup.connect.models.CreateMobileAuthorizationCodeResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MobileAuthorizationApi {
  private ApiClient apiClient;

  public MobileAuthorizationApi() {
    this(Configuration.getDefaultApiClient());
  }

  public MobileAuthorizationApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CreateMobileAuthorizationCode
   * Generates code to authorize a mobile application to connect to a Square card reader  Authorization codes are one-time-use and expire __60 minutes__ after being issued.  __Important:__ The &#x60;Authorization&#x60; header you provide to this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Bearer ACCESS_TOKEN &#x60;&#x60;&#x60;  Replace &#x60;ACCESS_TOKEN&#x60; with a [valid production authorization credential](https://docs.connect.squareup.com/get-started#step-4-understand-the-different-application-credentials).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateMobileAuthorizationCodeResponse
   * @throws ApiException if fails to make API call
   */  public CreateMobileAuthorizationCodeResponse createMobileAuthorizationCode(CreateMobileAuthorizationCodeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createMobileAuthorizationCode");
    }

    // create path and map variables
    String localVarPath = "/mobile/authorization-code";

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

    GenericType<CreateMobileAuthorizationCodeResponse> localVarReturnType = new GenericType<CreateMobileAuthorizationCodeResponse>() {};
    CompleteResponse<CreateMobileAuthorizationCodeResponse> completeResponse = (CompleteResponse<CreateMobileAuthorizationCodeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateMobileAuthorizationCode
   * Generates code to authorize a mobile application to connect to a Square card reader  Authorization codes are one-time-use and expire __60 minutes__ after being issued.  __Important:__ The &#x60;Authorization&#x60; header you provide to this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Bearer ACCESS_TOKEN &#x60;&#x60;&#x60;  Replace &#x60;ACCESS_TOKEN&#x60; with a [valid production authorization credential](https://docs.connect.squareup.com/get-started#step-4-understand-the-different-application-credentials).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateMobileAuthorizationCodeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateMobileAuthorizationCodeResponse>createMobileAuthorizationCodeWithHttpInfo(CreateMobileAuthorizationCodeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createMobileAuthorizationCode");
    }

    // create path and map variables
    String localVarPath = "/mobile/authorization-code";

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

    GenericType<CreateMobileAuthorizationCodeResponse> localVarReturnType = new GenericType<CreateMobileAuthorizationCodeResponse>() {};
    return (CompleteResponse<CreateMobileAuthorizationCodeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
