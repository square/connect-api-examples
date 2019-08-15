package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.GetPaymentRefundResponse;
import com.squareup.connect.models.ListPaymentRefundsResponse;
import com.squareup.connect.models.RefundPaymentRequest;
import com.squareup.connect.models.RefundPaymentResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RefundsApi {
  private ApiClient apiClient;

  public RefundsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public RefundsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * GetPaymentRefund
   * Retrieves a specific &#x60;Refund&#x60; using the &#x60;refund_id&#x60;.
   * @param refundId Unique ID for the desired &#x60;PaymentRefund&#x60;. (required)
   * @return GetPaymentRefundResponse
   * @throws ApiException if fails to make API call
   */  public GetPaymentRefundResponse getPaymentRefund(String refundId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'refundId' is set
    if (refundId == null) {
      throw new ApiException(400, "Missing the required parameter 'refundId' when calling getPaymentRefund");
    }

    // create path and map variables
    String localVarPath = "/v2/refunds/{refund_id}"
      .replaceAll("\\{" + "refund_id" + "\\}", apiClient.escapeString(refundId.toString()));

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

    GenericType<GetPaymentRefundResponse> localVarReturnType = new GenericType<GetPaymentRefundResponse>() {};
    CompleteResponse<GetPaymentRefundResponse> completeResponse = (CompleteResponse<GetPaymentRefundResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * GetPaymentRefund
   * Retrieves a specific &#x60;Refund&#x60; using the &#x60;refund_id&#x60;.
   * @param refundId Unique ID for the desired &#x60;PaymentRefund&#x60;. (required)
   * @return CompleteResponse<GetPaymentRefundResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<GetPaymentRefundResponse>getPaymentRefundWithHttpInfo(String refundId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'refundId' is set
    if (refundId == null) {
      throw new ApiException(400, "Missing the required parameter 'refundId' when calling getPaymentRefund");
    }

    // create path and map variables
    String localVarPath = "/v2/refunds/{refund_id}"
      .replaceAll("\\{" + "refund_id" + "\\}", apiClient.escapeString(refundId.toString()));

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

    GenericType<GetPaymentRefundResponse> localVarReturnType = new GenericType<GetPaymentRefundResponse>() {};
    return (CompleteResponse<GetPaymentRefundResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListPaymentRefunds
   * Retrieves a list of refunds for the account making the request.  Max results per page: 100
   * @param beginTime Timestamp for the beginning of the requested reporting period, in RFC 3339 format.  Default: The current time minus one year. (optional)
   * @param endTime Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time. (optional)
   * @param sortOrder The order in which results are listed. - &#x60;ASC&#x60; - oldest to newest - &#x60;DESC&#x60; - newest to oldest (default). (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param locationId ID of location associated with payment. (optional)
   * @param status If provided, only refunds with the given status are returned.  For a list of refund status values, see [PaymentRefund](#type-paymentrefund).  Default: If omitted refunds are returned regardless of status. (optional)
   * @param sourceType If provided, only refunds with the given source type are returned.  - &#x60;CARD&#x60; - List refunds only for payments where card was specified as payment  source.  Default: If omitted refunds are returned regardless of source type. (optional)
   * @return ListPaymentRefundsResponse
   * @throws ApiException if fails to make API call
   */  public ListPaymentRefundsResponse listPaymentRefunds(String beginTime, String endTime, String sortOrder, String cursor, String locationId, String status, String sourceType) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/refunds";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "source_type", sourceType));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListPaymentRefundsResponse> localVarReturnType = new GenericType<ListPaymentRefundsResponse>() {};
    CompleteResponse<ListPaymentRefundsResponse> completeResponse = (CompleteResponse<ListPaymentRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListPaymentRefunds
   * Retrieves a list of refunds for the account making the request.  Max results per page: 100
   * @param beginTime Timestamp for the beginning of the requested reporting period, in RFC 3339 format.  Default: The current time minus one year. (optional)
   * @param endTime Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time. (optional)
   * @param sortOrder The order in which results are listed. - &#x60;ASC&#x60; - oldest to newest - &#x60;DESC&#x60; - newest to oldest (default). (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param locationId ID of location associated with payment. (optional)
   * @param status If provided, only refunds with the given status are returned.  For a list of refund status values, see [PaymentRefund](#type-paymentrefund).  Default: If omitted refunds are returned regardless of status. (optional)
   * @param sourceType If provided, only refunds with the given source type are returned.  - &#x60;CARD&#x60; - List refunds only for payments where card was specified as payment  source.  Default: If omitted refunds are returned regardless of source type. (optional)
   * @return CompleteResponse<ListPaymentRefundsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListPaymentRefundsResponse>listPaymentRefundsWithHttpInfo(String beginTime, String endTime, String sortOrder, String cursor, String locationId, String status, String sourceType) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/refunds";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "status", status));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "source_type", sourceType));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListPaymentRefundsResponse> localVarReturnType = new GenericType<ListPaymentRefundsResponse>() {};
    return (CompleteResponse<ListPaymentRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RefundPayment
   * Refunds a payment. You can refund the entire payment amount or a  portion of it. For more information, see  [Payments and Refunds Overview](/payments-api/overview).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return RefundPaymentResponse
   * @throws ApiException if fails to make API call
   */  public RefundPaymentResponse refundPayment(RefundPaymentRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling refundPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/refunds";

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

    GenericType<RefundPaymentResponse> localVarReturnType = new GenericType<RefundPaymentResponse>() {};
    CompleteResponse<RefundPaymentResponse> completeResponse = (CompleteResponse<RefundPaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RefundPayment
   * Refunds a payment. You can refund the entire payment amount or a  portion of it. For more information, see  [Payments and Refunds Overview](/payments-api/overview).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<RefundPaymentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RefundPaymentResponse>refundPaymentWithHttpInfo(RefundPaymentRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling refundPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/refunds";

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

    GenericType<RefundPaymentResponse> localVarReturnType = new GenericType<RefundPaymentResponse>() {};
    return (CompleteResponse<RefundPaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
