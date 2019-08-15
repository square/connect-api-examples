package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CaptureTransactionResponse;
import com.squareup.connect.models.ChargeRequest;
import com.squareup.connect.models.ChargeResponse;
import com.squareup.connect.models.CreateRefundRequest;
import com.squareup.connect.models.CreateRefundResponse;
import com.squareup.connect.models.ListRefundsResponse;
import com.squareup.connect.models.ListTransactionsResponse;
import com.squareup.connect.models.RetrieveTransactionResponse;
import com.squareup.connect.models.VoidTransactionResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionsApi {
  private ApiClient apiClient;

  public TransactionsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public TransactionsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CaptureTransaction
   * Captures a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.
   * @param locationId  (required)
   * @param transactionId  (required)
   * @return CaptureTransactionResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public CaptureTransactionResponse captureTransaction(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling captureTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling captureTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/capture"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<CaptureTransactionResponse> localVarReturnType = new GenericType<CaptureTransactionResponse>() {};
    CompleteResponse<CaptureTransactionResponse> completeResponse = (CompleteResponse<CaptureTransactionResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CaptureTransaction
   * Captures a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.
   * @param locationId  (required)
   * @param transactionId  (required)
   * @return CompleteResponse<CaptureTransactionResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CaptureTransactionResponse>captureTransactionWithHttpInfo(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling captureTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling captureTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/capture"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<CaptureTransactionResponse> localVarReturnType = new GenericType<CaptureTransactionResponse>() {};
    return (CompleteResponse<CaptureTransactionResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * Charge
   * Charges a card represented by a card nonce or a customer&#39;s card on file.  Deprecated - recommend using [CreatePayment](#endpoint-payments-createpayment)  Your request to this endpoint must include _either_:  - A value for the &#x60;card_nonce&#x60; parameter (to charge a card nonce generated with the &#x60;SqPaymentForm&#x60;) - Values for the &#x60;customer_card_id&#x60; and &#x60;customer_id&#x60; parameters (to charge a customer&#39;s card on file)  When this response is returned, the amount of Square&#39;s processing fee might not yet be calculated. To obtain the processing fee, wait about ten seconds and call [RetrieveTransaction](#endpoint-transactions-retrievetransaction). See the &#x60;processing_fee_money&#x60; field of each [Tender included](#type-tender) in the transaction.
   * @param locationId The ID of the location to associate the created transaction with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return ChargeResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public ChargeResponse charge(String locationId, ChargeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling charge");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling charge");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions"
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

    GenericType<ChargeResponse> localVarReturnType = new GenericType<ChargeResponse>() {};
    CompleteResponse<ChargeResponse> completeResponse = (CompleteResponse<ChargeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * Charge
   * Charges a card represented by a card nonce or a customer&#39;s card on file.  Deprecated - recommend using [CreatePayment](#endpoint-payments-createpayment)  Your request to this endpoint must include _either_:  - A value for the &#x60;card_nonce&#x60; parameter (to charge a card nonce generated with the &#x60;SqPaymentForm&#x60;) - Values for the &#x60;customer_card_id&#x60; and &#x60;customer_id&#x60; parameters (to charge a customer&#39;s card on file)  When this response is returned, the amount of Square&#39;s processing fee might not yet be calculated. To obtain the processing fee, wait about ten seconds and call [RetrieveTransaction](#endpoint-transactions-retrievetransaction). See the &#x60;processing_fee_money&#x60; field of each [Tender included](#type-tender) in the transaction.
   * @param locationId The ID of the location to associate the created transaction with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<ChargeResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ChargeResponse>chargeWithHttpInfo(String locationId, ChargeRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling charge");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling charge");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions"
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

    GenericType<ChargeResponse> localVarReturnType = new GenericType<ChargeResponse>() {};
    return (CompleteResponse<ChargeResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateRefund
   * Initiates a refund for a previously charged tender.  Deprecated - recommend using [RefundPayment](#endpoint-refunds-refundpayment)  You must issue a refund within 120 days of the associated payment. See [this article](https://squareup.com/help/us/en/article/5060) for more information on refund behavior.  NOTE: Card-present transactions with Interac credit cards **cannot be refunded using the Connect API**. Interac transactions must refunded in-person (e.g., dipping the card using POS app).
   * @param locationId The ID of the original transaction&#39;s associated location. (required)
   * @param transactionId The ID of the original transaction that includes the tender to refund. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateRefundResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public CreateRefundResponse createRefund(String locationId, String transactionId, CreateRefundRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createRefund");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling createRefund");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createRefund");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/refund"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<CreateRefundResponse> localVarReturnType = new GenericType<CreateRefundResponse>() {};
    CompleteResponse<CreateRefundResponse> completeResponse = (CompleteResponse<CreateRefundResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateRefund
   * Initiates a refund for a previously charged tender.  Deprecated - recommend using [RefundPayment](#endpoint-refunds-refundpayment)  You must issue a refund within 120 days of the associated payment. See [this article](https://squareup.com/help/us/en/article/5060) for more information on refund behavior.  NOTE: Card-present transactions with Interac credit cards **cannot be refunded using the Connect API**. Interac transactions must refunded in-person (e.g., dipping the card using POS app).
   * @param locationId The ID of the original transaction&#39;s associated location. (required)
   * @param transactionId The ID of the original transaction that includes the tender to refund. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateRefundResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateRefundResponse>createRefundWithHttpInfo(String locationId, String transactionId, CreateRefundRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createRefund");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling createRefund");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createRefund");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/refund"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<CreateRefundResponse> localVarReturnType = new GenericType<CreateRefundResponse>() {};
    return (CompleteResponse<CreateRefundResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListRefunds
   * Lists refunds for one of a business&#39;s locations.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  In addition to full or partial tender refunds processed through Square APIs, refunds may result from itemized returns or exchanges through Square&#39;s Point of Sale applications.  Refunds with a &#x60;status&#x60; of &#x60;PENDING&#x60; are not currently included in this endpoint&#39;s response.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list refunds for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return ListRefundsResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public ListRefundsResponse listRefunds(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listRefunds");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/refunds"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
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

    GenericType<ListRefundsResponse> localVarReturnType = new GenericType<ListRefundsResponse>() {};
    CompleteResponse<ListRefundsResponse> completeResponse = (CompleteResponse<ListRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListRefunds
   * Lists refunds for one of a business&#39;s locations.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  In addition to full or partial tender refunds processed through Square APIs, refunds may result from itemized returns or exchanges through Square&#39;s Point of Sale applications.  Refunds with a &#x60;status&#x60; of &#x60;PENDING&#x60; are not currently included in this endpoint&#39;s response.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list refunds for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<ListRefundsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListRefundsResponse>listRefundsWithHttpInfo(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listRefunds");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/refunds"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
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

    GenericType<ListRefundsResponse> localVarReturnType = new GenericType<ListRefundsResponse>() {};
    return (CompleteResponse<ListRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListTransactions
   * Lists transactions for a particular location.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  Transactions include payment information from sales and exchanges and refund information from returns and exchanges.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list transactions for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return ListTransactionsResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public ListTransactionsResponse listTransactions(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listTransactions");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
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

    GenericType<ListTransactionsResponse> localVarReturnType = new GenericType<ListTransactionsResponse>() {};
    CompleteResponse<ListTransactionsResponse> completeResponse = (CompleteResponse<ListTransactionsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListTransactions
   * Lists transactions for a particular location.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  Transactions include payment information from sales and exchanges and refund information from returns and exchanges.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list transactions for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<ListTransactionsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListTransactionsResponse>listTransactionsWithHttpInfo(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listTransactions");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "begin_time", beginTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "end_time", endTime));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));
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

    GenericType<ListTransactionsResponse> localVarReturnType = new GenericType<ListTransactionsResponse>() {};
    return (CompleteResponse<ListTransactionsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveTransaction
   * Retrieves details for a single transaction.  Deprecated - recommend using [BatchRetrieveOrders](#endpoint-batchretrieveorders)
   * @param locationId The ID of the transaction&#39;s associated location. (required)
   * @param transactionId The ID of the transaction to retrieve. (required)
   * @return RetrieveTransactionResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public RetrieveTransactionResponse retrieveTransaction(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling retrieveTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<RetrieveTransactionResponse> localVarReturnType = new GenericType<RetrieveTransactionResponse>() {};
    CompleteResponse<RetrieveTransactionResponse> completeResponse = (CompleteResponse<RetrieveTransactionResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveTransaction
   * Retrieves details for a single transaction.  Deprecated - recommend using [BatchRetrieveOrders](#endpoint-batchretrieveorders)
   * @param locationId The ID of the transaction&#39;s associated location. (required)
   * @param transactionId The ID of the transaction to retrieve. (required)
   * @return CompleteResponse<RetrieveTransactionResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveTransactionResponse>retrieveTransactionWithHttpInfo(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling retrieveTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<RetrieveTransactionResponse> localVarReturnType = new GenericType<RetrieveTransactionResponse>() {};
    return (CompleteResponse<RetrieveTransactionResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * VoidTransaction
   * Cancels a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.
   * @param locationId  (required)
   * @param transactionId  (required)
   * @return VoidTransactionResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public VoidTransactionResponse voidTransaction(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling voidTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling voidTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/void"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<VoidTransactionResponse> localVarReturnType = new GenericType<VoidTransactionResponse>() {};
    CompleteResponse<VoidTransactionResponse> completeResponse = (CompleteResponse<VoidTransactionResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * VoidTransaction
   * Cancels a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.
   * @param locationId  (required)
   * @param transactionId  (required)
   * @return CompleteResponse<VoidTransactionResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<VoidTransactionResponse>voidTransactionWithHttpInfo(String locationId, String transactionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling voidTransaction");
    }

    // verify the required parameter 'transactionId' is set
    if (transactionId == null) {
      throw new ApiException(400, "Missing the required parameter 'transactionId' when calling voidTransaction");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/transactions/{transaction_id}/void"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "transaction_id" + "\\}", apiClient.escapeString(transactionId.toString()));

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

    GenericType<VoidTransactionResponse> localVarReturnType = new GenericType<VoidTransactionResponse>() {};
    return (CompleteResponse<VoidTransactionResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
