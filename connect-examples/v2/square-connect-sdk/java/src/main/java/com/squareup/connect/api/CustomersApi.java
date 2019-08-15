package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.CreateCustomerCardRequest;
import com.squareup.connect.models.CreateCustomerCardResponse;
import com.squareup.connect.models.CreateCustomerRequest;
import com.squareup.connect.models.CreateCustomerResponse;
import com.squareup.connect.models.DeleteCustomerCardResponse;
import com.squareup.connect.models.DeleteCustomerResponse;
import com.squareup.connect.models.ListCustomersResponse;
import com.squareup.connect.models.RetrieveCustomerResponse;
import com.squareup.connect.models.SearchCustomersRequest;
import com.squareup.connect.models.SearchCustomersResponse;
import com.squareup.connect.models.UpdateCustomerRequest;
import com.squareup.connect.models.UpdateCustomerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomersApi {
  private ApiClient apiClient;

  public CustomersApi() {
    this(Configuration.getDefaultApiClient());
  }

  public CustomersApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * CreateCustomer
   * Creates a new customer for a business, which can have associated cards on file.  You must provide __at least one__ of the following values in your request to this endpoint:  - &#x60;given_name&#x60; - &#x60;family_name&#x60; - &#x60;company_name&#x60; - &#x60;email_address&#x60; - &#x60;phone_number&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateCustomerResponse
   * @throws ApiException if fails to make API call
   */  public CreateCustomerResponse createCustomer(CreateCustomerRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers";

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

    GenericType<CreateCustomerResponse> localVarReturnType = new GenericType<CreateCustomerResponse>() {};
    CompleteResponse<CreateCustomerResponse> completeResponse = (CompleteResponse<CreateCustomerResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateCustomer
   * Creates a new customer for a business, which can have associated cards on file.  You must provide __at least one__ of the following values in your request to this endpoint:  - &#x60;given_name&#x60; - &#x60;family_name&#x60; - &#x60;company_name&#x60; - &#x60;email_address&#x60; - &#x60;phone_number&#x60;
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateCustomerResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateCustomerResponse>createCustomerWithHttpInfo(CreateCustomerRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers";

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

    GenericType<CreateCustomerResponse> localVarReturnType = new GenericType<CreateCustomerResponse>() {};
    return (CompleteResponse<CreateCustomerResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateCustomerCard
   * Adds a card on file to an existing customer.  As with charges, calls to &#x60;CreateCustomerCard&#x60; are idempotent. Multiple calls with the same card nonce return the same card record that was created with the provided nonce during the _first_ call.  Cards on file are automatically updated on a monthly basis to confirm they are still valid and can be charged.
   * @param customerId The Square ID of the customer profile the card is linked to. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateCustomerCardResponse
   * @throws ApiException if fails to make API call
   */  public CreateCustomerCardResponse createCustomerCard(String customerId, CreateCustomerCardRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling createCustomerCard");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCustomerCard");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}/cards"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<CreateCustomerCardResponse> localVarReturnType = new GenericType<CreateCustomerCardResponse>() {};
    CompleteResponse<CreateCustomerCardResponse> completeResponse = (CompleteResponse<CreateCustomerCardResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateCustomerCard
   * Adds a card on file to an existing customer.  As with charges, calls to &#x60;CreateCustomerCard&#x60; are idempotent. Multiple calls with the same card nonce return the same card record that was created with the provided nonce during the _first_ call.  Cards on file are automatically updated on a monthly basis to confirm they are still valid and can be charged.
   * @param customerId The Square ID of the customer profile the card is linked to. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateCustomerCardResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateCustomerCardResponse>createCustomerCardWithHttpInfo(String customerId, CreateCustomerCardRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling createCustomerCard");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCustomerCard");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}/cards"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<CreateCustomerCardResponse> localVarReturnType = new GenericType<CreateCustomerCardResponse>() {};
    return (CompleteResponse<CreateCustomerCardResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteCustomer
   * Deletes a customer from a business, along with any linked cards on file. When two profiles are merged into a single profile, that profile is assigned a new &#x60;customer_id&#x60;. You must use the new &#x60;customer_id&#x60; to delete merged profiles.
   * @param customerId The ID of the customer to delete. (required)
   * @return DeleteCustomerResponse
   * @throws ApiException if fails to make API call
   */  public DeleteCustomerResponse deleteCustomer(String customerId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling deleteCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<DeleteCustomerResponse> localVarReturnType = new GenericType<DeleteCustomerResponse>() {};
    CompleteResponse<DeleteCustomerResponse> completeResponse = (CompleteResponse<DeleteCustomerResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteCustomer
   * Deletes a customer from a business, along with any linked cards on file. When two profiles are merged into a single profile, that profile is assigned a new &#x60;customer_id&#x60;. You must use the new &#x60;customer_id&#x60; to delete merged profiles.
   * @param customerId The ID of the customer to delete. (required)
   * @return CompleteResponse<DeleteCustomerResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<DeleteCustomerResponse>deleteCustomerWithHttpInfo(String customerId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling deleteCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<DeleteCustomerResponse> localVarReturnType = new GenericType<DeleteCustomerResponse>() {};
    return (CompleteResponse<DeleteCustomerResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteCustomerCard
   * Removes a card on file from a customer.
   * @param customerId The ID of the customer that the card on file belongs to. (required)
   * @param cardId The ID of the card on file to delete. (required)
   * @return DeleteCustomerCardResponse
   * @throws ApiException if fails to make API call
   */  public DeleteCustomerCardResponse deleteCustomerCard(String customerId, String cardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling deleteCustomerCard");
    }

    // verify the required parameter 'cardId' is set
    if (cardId == null) {
      throw new ApiException(400, "Missing the required parameter 'cardId' when calling deleteCustomerCard");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}/cards/{card_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()))
      .replaceAll("\\{" + "card_id" + "\\}", apiClient.escapeString(cardId.toString()));

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

    GenericType<DeleteCustomerCardResponse> localVarReturnType = new GenericType<DeleteCustomerCardResponse>() {};
    CompleteResponse<DeleteCustomerCardResponse> completeResponse = (CompleteResponse<DeleteCustomerCardResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteCustomerCard
   * Removes a card on file from a customer.
   * @param customerId The ID of the customer that the card on file belongs to. (required)
   * @param cardId The ID of the card on file to delete. (required)
   * @return CompleteResponse<DeleteCustomerCardResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<DeleteCustomerCardResponse>deleteCustomerCardWithHttpInfo(String customerId, String cardId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling deleteCustomerCard");
    }

    // verify the required parameter 'cardId' is set
    if (cardId == null) {
      throw new ApiException(400, "Missing the required parameter 'cardId' when calling deleteCustomerCard");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}/cards/{card_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()))
      .replaceAll("\\{" + "card_id" + "\\}", apiClient.escapeString(cardId.toString()));

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

    GenericType<DeleteCustomerCardResponse> localVarReturnType = new GenericType<DeleteCustomerCardResponse>() {};
    return (CompleteResponse<DeleteCustomerCardResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListCustomers
   * Lists a business&#39;s customers.
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param sortField Indicates how Customers should be sorted. Default: &#x60;DEFAULT&#x60;. (optional)
   * @param sortOrder Indicates whether Customers should be sorted in ascending (&#x60;ASC&#x60;) or descending (&#x60;DESC&#x60;) order. Default: &#x60;ASC&#x60;. (optional)
   * @return ListCustomersResponse
   * @throws ApiException if fails to make API call
   */  public ListCustomersResponse listCustomers(String cursor, String sortField, String sortOrder) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/customers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_field", sortField));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListCustomersResponse> localVarReturnType = new GenericType<ListCustomersResponse>() {};
    CompleteResponse<ListCustomersResponse> completeResponse = (CompleteResponse<ListCustomersResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListCustomers
   * Lists a business&#39;s customers.
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param sortField Indicates how Customers should be sorted. Default: &#x60;DEFAULT&#x60;. (optional)
   * @param sortOrder Indicates whether Customers should be sorted in ascending (&#x60;ASC&#x60;) or descending (&#x60;DESC&#x60;) order. Default: &#x60;ASC&#x60;. (optional)
   * @return CompleteResponse<ListCustomersResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListCustomersResponse>listCustomersWithHttpInfo(String cursor, String sortField, String sortOrder) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/customers";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_field", sortField));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "sort_order", sortOrder));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListCustomersResponse> localVarReturnType = new GenericType<ListCustomersResponse>() {};
    return (CompleteResponse<ListCustomersResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveCustomer
   * Returns details for a single customer.
   * @param customerId The ID of the customer to retrieve. (required)
   * @return RetrieveCustomerResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveCustomerResponse retrieveCustomer(String customerId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling retrieveCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<RetrieveCustomerResponse> localVarReturnType = new GenericType<RetrieveCustomerResponse>() {};
    CompleteResponse<RetrieveCustomerResponse> completeResponse = (CompleteResponse<RetrieveCustomerResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveCustomer
   * Returns details for a single customer.
   * @param customerId The ID of the customer to retrieve. (required)
   * @return CompleteResponse<RetrieveCustomerResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveCustomerResponse>retrieveCustomerWithHttpInfo(String customerId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling retrieveCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<RetrieveCustomerResponse> localVarReturnType = new GenericType<RetrieveCustomerResponse>() {};
    return (CompleteResponse<RetrieveCustomerResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * SearchCustomers
   * Searches the customer profiles associated with a Square account. Calling SearchCustomers without an explicit query parameter returns all customer profiles ordered alphabetically based on &#x60;given_name&#x60; and &#x60;family_name&#x60;.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return SearchCustomersResponse
   * @throws ApiException if fails to make API call
   */  public SearchCustomersResponse searchCustomers(SearchCustomersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchCustomers");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/search";

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

    GenericType<SearchCustomersResponse> localVarReturnType = new GenericType<SearchCustomersResponse>() {};
    CompleteResponse<SearchCustomersResponse> completeResponse = (CompleteResponse<SearchCustomersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * SearchCustomers
   * Searches the customer profiles associated with a Square account. Calling SearchCustomers without an explicit query parameter returns all customer profiles ordered alphabetically based on &#x60;given_name&#x60; and &#x60;family_name&#x60;.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<SearchCustomersResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<SearchCustomersResponse>searchCustomersWithHttpInfo(SearchCustomersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchCustomers");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/search";

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

    GenericType<SearchCustomersResponse> localVarReturnType = new GenericType<SearchCustomersResponse>() {};
    return (CompleteResponse<SearchCustomersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateCustomer
   * Updates the details of an existing customer. When two profiles are merged into a single profile, that profile is assigned a new &#x60;customer_id&#x60;. You must use the new &#x60;customer_id&#x60; to update merged profiles.  You cannot edit a customer&#39;s cards on file with this endpoint. To make changes to a card on file, you must delete the existing card on file with the [DeleteCustomerCard](#endpoint-customers-deletecustomercard) endpoint, then create a new one with the [CreateCustomerCard](#endpoint-customers-createcustomercard) endpoint.
   * @param customerId The ID of the customer to update. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateCustomerResponse
   * @throws ApiException if fails to make API call
   */  public UpdateCustomerResponse updateCustomer(String customerId, UpdateCustomerRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling updateCustomer");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<UpdateCustomerResponse> localVarReturnType = new GenericType<UpdateCustomerResponse>() {};
    CompleteResponse<UpdateCustomerResponse> completeResponse = (CompleteResponse<UpdateCustomerResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateCustomer
   * Updates the details of an existing customer. When two profiles are merged into a single profile, that profile is assigned a new &#x60;customer_id&#x60;. You must use the new &#x60;customer_id&#x60; to update merged profiles.  You cannot edit a customer&#39;s cards on file with this endpoint. To make changes to a card on file, you must delete the existing card on file with the [DeleteCustomerCard](#endpoint-customers-deletecustomercard) endpoint, then create a new one with the [CreateCustomerCard](#endpoint-customers-createcustomercard) endpoint.
   * @param customerId The ID of the customer to update. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateCustomerResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateCustomerResponse>updateCustomerWithHttpInfo(String customerId, UpdateCustomerRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'customerId' is set
    if (customerId == null) {
      throw new ApiException(400, "Missing the required parameter 'customerId' when calling updateCustomer");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateCustomer");
    }

    // create path and map variables
    String localVarPath = "/v2/customers/{customer_id}"
      .replaceAll("\\{" + "customer_id" + "\\}", apiClient.escapeString(customerId.toString()));

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

    GenericType<UpdateCustomerResponse> localVarReturnType = new GenericType<UpdateCustomerResponse>() {};
    return (CompleteResponse<UpdateCustomerResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
