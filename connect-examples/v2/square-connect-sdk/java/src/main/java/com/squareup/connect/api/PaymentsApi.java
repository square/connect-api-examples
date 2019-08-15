package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CancelPaymentByIdempotencyKeyRequest;
import com.squareup.connect.models.CancelPaymentByIdempotencyKeyResponse;
import com.squareup.connect.models.CancelPaymentResponse;
import com.squareup.connect.models.CompletePaymentResponse;
import com.squareup.connect.models.CreatePaymentRequest;
import com.squareup.connect.models.CreatePaymentResponse;
import com.squareup.connect.models.GetPaymentResponse;
import com.squareup.connect.models.ListPaymentsResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaymentsApi {
  private ApiClient apiClient;

  public PaymentsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public PaymentsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CancelPayment
   * Cancels a payment. If you set &#x60;autocomplete&#x60; to false when creating a payment,  you can cancel the payment using this endpoint. For more information, see [Delayed Payments](/payments-api/take-payments#delayed-payments).
   * @param paymentId &#x60;payment_id&#x60; identifying the payment to be canceled. (required)
   * @return CancelPaymentResponse
   * @throws ApiException if fails to make API call
   */  public CancelPaymentResponse cancelPayment(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling cancelPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}/cancel"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<CancelPaymentResponse> localVarReturnType = new GenericType<CancelPaymentResponse>() {};
    CompleteResponse<CancelPaymentResponse> completeResponse = (CompleteResponse<CancelPaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CancelPayment
   * Cancels a payment. If you set &#x60;autocomplete&#x60; to false when creating a payment,  you can cancel the payment using this endpoint. For more information, see [Delayed Payments](/payments-api/take-payments#delayed-payments).
   * @param paymentId &#x60;payment_id&#x60; identifying the payment to be canceled. (required)
   * @return CompleteResponse<CancelPaymentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CancelPaymentResponse>cancelPaymentWithHttpInfo(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling cancelPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}/cancel"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<CancelPaymentResponse> localVarReturnType = new GenericType<CancelPaymentResponse>() {};
    return (CompleteResponse<CancelPaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CancelPaymentByIdempotencyKey
   * Cancels a payment identified by the idenpotency key that is specified the request.  Use this method when status of a CreatePayment request is unknown. For example, after you send a CreatePayment request a network error occurs and you don&#39;t get a response. In this case, you can direct Square to cancel the payment using this endpoint. In the request, you provide the same idempotency key that you provided in your CreatePayment request you want  to cancel. After cancelling the payment, you can submit your CreatePayment request again. Note that if no payment with the specified idempotency key is found, no action is taken, the end  point returns successfully.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CancelPaymentByIdempotencyKeyResponse
   * @throws ApiException if fails to make API call
   */  public CancelPaymentByIdempotencyKeyResponse cancelPaymentByIdempotencyKey(CancelPaymentByIdempotencyKeyRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling cancelPaymentByIdempotencyKey");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/cancel";

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

    GenericType<CancelPaymentByIdempotencyKeyResponse> localVarReturnType = new GenericType<CancelPaymentByIdempotencyKeyResponse>() {};
    CompleteResponse<CancelPaymentByIdempotencyKeyResponse> completeResponse = (CompleteResponse<CancelPaymentByIdempotencyKeyResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CancelPaymentByIdempotencyKey
   * Cancels a payment identified by the idenpotency key that is specified the request.  Use this method when status of a CreatePayment request is unknown. For example, after you send a CreatePayment request a network error occurs and you don&#39;t get a response. In this case, you can direct Square to cancel the payment using this endpoint. In the request, you provide the same idempotency key that you provided in your CreatePayment request you want  to cancel. After cancelling the payment, you can submit your CreatePayment request again. Note that if no payment with the specified idempotency key is found, no action is taken, the end  point returns successfully.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CancelPaymentByIdempotencyKeyResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CancelPaymentByIdempotencyKeyResponse>cancelPaymentByIdempotencyKeyWithHttpInfo(CancelPaymentByIdempotencyKeyRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling cancelPaymentByIdempotencyKey");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/cancel";

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

    GenericType<CancelPaymentByIdempotencyKeyResponse> localVarReturnType = new GenericType<CancelPaymentByIdempotencyKeyResponse>() {};
    return (CompleteResponse<CancelPaymentByIdempotencyKeyResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CompletePayment
   * Completes a payment.  By default, payments are set to complete immediately after they are created.  If you set autocomplete to false when creating a payment,  you can complete the payment using this endpoint. For more information, see [Delayed Payments](/payments-api/take-payments#delayed-payments).
   * @param paymentId Unique ID identifying the payment to be completed. (required)
   * @return CompletePaymentResponse
   * @throws ApiException if fails to make API call
   */  public CompletePaymentResponse completePayment(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling completePayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}/complete"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<CompletePaymentResponse> localVarReturnType = new GenericType<CompletePaymentResponse>() {};
    CompleteResponse<CompletePaymentResponse> completeResponse = (CompleteResponse<CompletePaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CompletePayment
   * Completes a payment.  By default, payments are set to complete immediately after they are created.  If you set autocomplete to false when creating a payment,  you can complete the payment using this endpoint. For more information, see [Delayed Payments](/payments-api/take-payments#delayed-payments).
   * @param paymentId Unique ID identifying the payment to be completed. (required)
   * @return CompleteResponse<CompletePaymentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CompletePaymentResponse>completePaymentWithHttpInfo(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling completePayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}/complete"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<CompletePaymentResponse> localVarReturnType = new GenericType<CompletePaymentResponse>() {};
    return (CompleteResponse<CompletePaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreatePayment
   * Charges a payment source, for example, a card  represented by customer&#39;s card on file or a card nonce. In addition  to the payment source, the request must also include the  amount to accept for the payment.  There are several optional parameters that you can include in the request.  For example, tip money, whether to autocomplete the payment, or a reference ID to correlate this payment with another system.  For more information about these  payment options, see [Take Payments](/payments-api/take-payments).  The &#x60;PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS&#x60; OAuth permission is required to enable application fees.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreatePaymentResponse
   * @throws ApiException if fails to make API call
   */  public CreatePaymentResponse createPayment(CreatePaymentRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments";

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

    GenericType<CreatePaymentResponse> localVarReturnType = new GenericType<CreatePaymentResponse>() {};
    CompleteResponse<CreatePaymentResponse> completeResponse = (CompleteResponse<CreatePaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreatePayment
   * Charges a payment source, for example, a card  represented by customer&#39;s card on file or a card nonce. In addition  to the payment source, the request must also include the  amount to accept for the payment.  There are several optional parameters that you can include in the request.  For example, tip money, whether to autocomplete the payment, or a reference ID to correlate this payment with another system.  For more information about these  payment options, see [Take Payments](/payments-api/take-payments).  The &#x60;PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS&#x60; OAuth permission is required to enable application fees.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreatePaymentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreatePaymentResponse>createPaymentWithHttpInfo(CreatePaymentRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments";

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

    GenericType<CreatePaymentResponse> localVarReturnType = new GenericType<CreatePaymentResponse>() {};
    return (CompleteResponse<CreatePaymentResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * GetPayment
   * Retrieves details for a specific Payment.
   * @param paymentId Unique ID for the desired &#x60;Payment&#x60;. (required)
   * @return GetPaymentResponse
   * @throws ApiException if fails to make API call
   */  public GetPaymentResponse getPayment(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling getPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<GetPaymentResponse> localVarReturnType = new GenericType<GetPaymentResponse>() {};
    CompleteResponse<GetPaymentResponse> completeResponse = (CompleteResponse<GetPaymentResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * GetPayment
   * Retrieves details for a specific Payment.
   * @param paymentId Unique ID for the desired &#x60;Payment&#x60;. (required)
   * @return CompleteResponse<GetPaymentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<GetPaymentResponse>getPaymentWithHttpInfo(String paymentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'paymentId' is set
    if (paymentId == null) {
      throw new ApiException(400, "Missing the required parameter 'paymentId' when calling getPayment");
    }

    // create path and map variables
    String localVarPath = "/v2/payments/{payment_id}"
      .replaceAll("\\{" + "payment_id" + "\\}", apiClient.escapeString(paymentId.toString()));

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

    GenericType<GetPaymentResponse> localVarReturnType = new GenericType<GetPaymentResponse>() {};
    return (CompleteResponse<GetPaymentResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListPayments
   * Retrieves a list of payments taken by the account making the request.  Max results per page: 100
   * @param beginTime Timestamp for the beginning of the reporting period, in RFC 3339 format. Inclusive. Default: The current time minus one year. (optional)
   * @param endTime Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time. (optional)
   * @param sortOrder The order in which results are listed. - &#x60;ASC&#x60; - oldest to newest - &#x60;DESC&#x60; - newest to oldest (default). (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param locationId ID of location associated with payment (optional)
   * @param total The exact amount in the total_money for a &#x60;Payment&#x60;. (optional)
   * @param last4 The last 4 digits of &#x60;Payment&#x60; card. (optional)
   * @param cardBrand The brand of &#x60;Payment&#x60; card. For example, &#x60;VISA&#x60; (optional)
   * @return ListPaymentsResponse
   * @throws ApiException if fails to make API call
   */  public ListPaymentsResponse listPayments(String beginTime, String endTime, String sortOrder, String cursor, String locationId, Long total, String last4, String cardBrand) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/payments";

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
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "total", total));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "last_4", last4));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "card_brand", cardBrand));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListPaymentsResponse> localVarReturnType = new GenericType<ListPaymentsResponse>() {};
    CompleteResponse<ListPaymentsResponse> completeResponse = (CompleteResponse<ListPaymentsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListPayments
   * Retrieves a list of payments taken by the account making the request.  Max results per page: 100
   * @param beginTime Timestamp for the beginning of the reporting period, in RFC 3339 format. Inclusive. Default: The current time minus one year. (optional)
   * @param endTime Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time. (optional)
   * @param sortOrder The order in which results are listed. - &#x60;ASC&#x60; - oldest to newest - &#x60;DESC&#x60; - newest to oldest (default). (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param locationId ID of location associated with payment (optional)
   * @param total The exact amount in the total_money for a &#x60;Payment&#x60;. (optional)
   * @param last4 The last 4 digits of &#x60;Payment&#x60; card. (optional)
   * @param cardBrand The brand of &#x60;Payment&#x60; card. For example, &#x60;VISA&#x60; (optional)
   * @return CompleteResponse<ListPaymentsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListPaymentsResponse>listPaymentsWithHttpInfo(String beginTime, String endTime, String sortOrder, String cursor, String locationId, Long total, String last4, String cardBrand) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/payments";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_id", locationId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "total", total));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "last_4", last4));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "card_brand", cardBrand));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListPaymentsResponse> localVarReturnType = new GenericType<ListPaymentsResponse>() {};
    return (CompleteResponse<ListPaymentsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
