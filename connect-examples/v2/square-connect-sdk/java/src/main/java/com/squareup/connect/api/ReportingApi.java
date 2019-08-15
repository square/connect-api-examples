package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.ListAdditionalRecipientReceivableRefundsResponse;
import com.squareup.connect.models.ListAdditionalRecipientReceivablesResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReportingApi {
  private ApiClient apiClient;

  public ReportingApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ReportingApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * ListAdditionalRecipientReceivableRefunds
   * Returns a list of refunded transactions (across all possible originating locations) relating to monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list AdditionalRecipientReceivableRefunds for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return ListAdditionalRecipientReceivableRefundsResponse
   * @throws ApiException if fails to make API call
   */  public ListAdditionalRecipientReceivableRefundsResponse listAdditionalRecipientReceivableRefunds(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listAdditionalRecipientReceivableRefunds");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/additional-recipient-receivable-refunds"
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

    GenericType<ListAdditionalRecipientReceivableRefundsResponse> localVarReturnType = new GenericType<ListAdditionalRecipientReceivableRefundsResponse>() {};
    CompleteResponse<ListAdditionalRecipientReceivableRefundsResponse> completeResponse = (CompleteResponse<ListAdditionalRecipientReceivableRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListAdditionalRecipientReceivableRefunds
   * Returns a list of refunded transactions (across all possible originating locations) relating to monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list AdditionalRecipientReceivableRefunds for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<ListAdditionalRecipientReceivableRefundsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListAdditionalRecipientReceivableRefundsResponse>listAdditionalRecipientReceivableRefundsWithHttpInfo(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listAdditionalRecipientReceivableRefunds");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/additional-recipient-receivable-refunds"
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

    GenericType<ListAdditionalRecipientReceivableRefundsResponse> localVarReturnType = new GenericType<ListAdditionalRecipientReceivableRefundsResponse>() {};
    return (CompleteResponse<ListAdditionalRecipientReceivableRefundsResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListAdditionalRecipientReceivables
   * Returns a list of receivables (across all possible sending locations) representing monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list AdditionalRecipientReceivables for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return ListAdditionalRecipientReceivablesResponse
   * @throws ApiException if fails to make API call
   */  public ListAdditionalRecipientReceivablesResponse listAdditionalRecipientReceivables(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listAdditionalRecipientReceivables");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/additional-recipient-receivables"
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

    GenericType<ListAdditionalRecipientReceivablesResponse> localVarReturnType = new GenericType<ListAdditionalRecipientReceivablesResponse>() {};
    CompleteResponse<ListAdditionalRecipientReceivablesResponse> completeResponse = (CompleteResponse<ListAdditionalRecipientReceivablesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListAdditionalRecipientReceivables
   * Returns a list of receivables (across all possible sending locations) representing monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50
   * @param locationId The ID of the location to list AdditionalRecipientReceivables for. (required)
   * @param beginTime The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. (optional)
   * @param endTime The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. (optional)
   * @param sortOrder The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<ListAdditionalRecipientReceivablesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListAdditionalRecipientReceivablesResponse>listAdditionalRecipientReceivablesWithHttpInfo(String locationId, String beginTime, String endTime, String sortOrder, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listAdditionalRecipientReceivables");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/additional-recipient-receivables"
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

    GenericType<ListAdditionalRecipientReceivablesResponse> localVarReturnType = new GenericType<ListAdditionalRecipientReceivablesResponse>() {};
    return (CompleteResponse<ListAdditionalRecipientReceivablesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
