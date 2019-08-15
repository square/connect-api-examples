package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CreateCheckoutRequest;
import com.squareup.connect.models.CreateCheckoutResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutApi {
  private ApiClient apiClient;

  public CheckoutApi() {
    this(Configuration.getDefaultApiClient());
  }

  public CheckoutApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CreateCheckout
   * Links a &#x60;checkoutId&#x60; to a &#x60;checkout_page_url&#x60; that customers will be directed to in order to provide their payment information using a payment processing workflow hosted on connect.squareup.com.
   * @param locationId The ID of the business location to associate the checkout with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateCheckoutResponse
   * @throws ApiException if fails to make API call
   */  public CreateCheckoutResponse createCheckout(String locationId, CreateCheckoutRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createCheckout");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCheckout");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/checkouts"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

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

    GenericType<CreateCheckoutResponse> localVarReturnType = new GenericType<CreateCheckoutResponse>() {};
    CompleteResponse<CreateCheckoutResponse> completeResponse = (CompleteResponse<CreateCheckoutResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateCheckout
   * Links a &#x60;checkoutId&#x60; to a &#x60;checkout_page_url&#x60; that customers will be directed to in order to provide their payment information using a payment processing workflow hosted on connect.squareup.com.
   * @param locationId The ID of the business location to associate the checkout with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateCheckoutResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateCheckoutResponse>createCheckoutWithHttpInfo(String locationId, CreateCheckoutRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createCheckout");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCheckout");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/checkouts"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

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

    GenericType<CreateCheckoutResponse> localVarReturnType = new GenericType<CreateCheckoutResponse>() {};
    return (CompleteResponse<CreateCheckoutResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
