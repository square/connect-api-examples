package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.BatchChangeInventoryRequest;
import com.squareup.connect.models.BatchChangeInventoryResponse;
import com.squareup.connect.models.BatchRetrieveInventoryChangesRequest;
import com.squareup.connect.models.BatchRetrieveInventoryChangesResponse;
import com.squareup.connect.models.BatchRetrieveInventoryCountsRequest;
import com.squareup.connect.models.BatchRetrieveInventoryCountsResponse;
import com.squareup.connect.models.RetrieveInventoryAdjustmentResponse;
import com.squareup.connect.models.RetrieveInventoryChangesResponse;
import com.squareup.connect.models.RetrieveInventoryCountResponse;
import com.squareup.connect.models.RetrieveInventoryPhysicalCountResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InventoryApi {
  private ApiClient apiClient;

  public InventoryApi() {
    this(Configuration.getDefaultApiClient());
  }

  public InventoryApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * BatchChangeInventory
   * Applies adjustments and counts to the provided item quantities.  On success: returns the current calculated counts for all objects referenced in the request. On failure: returns a list of related errors.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchChangeInventoryResponse
   * @throws ApiException if fails to make API call
   */  public BatchChangeInventoryResponse batchChangeInventory(BatchChangeInventoryRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchChangeInventory");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-change";

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

    GenericType<BatchChangeInventoryResponse> localVarReturnType = new GenericType<BatchChangeInventoryResponse>() {};
    CompleteResponse<BatchChangeInventoryResponse> completeResponse = (CompleteResponse<BatchChangeInventoryResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchChangeInventory
   * Applies adjustments and counts to the provided item quantities.  On success: returns the current calculated counts for all objects referenced in the request. On failure: returns a list of related errors.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchChangeInventoryResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchChangeInventoryResponse>batchChangeInventoryWithHttpInfo(BatchChangeInventoryRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchChangeInventory");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-change";

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

    GenericType<BatchChangeInventoryResponse> localVarReturnType = new GenericType<BatchChangeInventoryResponse>() {};
    return (CompleteResponse<BatchChangeInventoryResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * BatchRetrieveInventoryChanges
   * Returns historical physical counts and adjustments based on the provided filter criteria.  Results are paginated and sorted in ascending order according their &#x60;occurred_at&#x60; timestamp (oldest first).  BatchRetrieveInventoryChanges is a catch-all query endpoint for queries that cannot be handled by other, simpler endpoints.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchRetrieveInventoryChangesResponse
   * @throws ApiException if fails to make API call
   */  public BatchRetrieveInventoryChangesResponse batchRetrieveInventoryChanges(BatchRetrieveInventoryChangesRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveInventoryChanges");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-retrieve-changes";

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

    GenericType<BatchRetrieveInventoryChangesResponse> localVarReturnType = new GenericType<BatchRetrieveInventoryChangesResponse>() {};
    CompleteResponse<BatchRetrieveInventoryChangesResponse> completeResponse = (CompleteResponse<BatchRetrieveInventoryChangesResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchRetrieveInventoryChanges
   * Returns historical physical counts and adjustments based on the provided filter criteria.  Results are paginated and sorted in ascending order according their &#x60;occurred_at&#x60; timestamp (oldest first).  BatchRetrieveInventoryChanges is a catch-all query endpoint for queries that cannot be handled by other, simpler endpoints.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchRetrieveInventoryChangesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchRetrieveInventoryChangesResponse>batchRetrieveInventoryChangesWithHttpInfo(BatchRetrieveInventoryChangesRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveInventoryChanges");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-retrieve-changes";

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

    GenericType<BatchRetrieveInventoryChangesResponse> localVarReturnType = new GenericType<BatchRetrieveInventoryChangesResponse>() {};
    return (CompleteResponse<BatchRetrieveInventoryChangesResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * BatchRetrieveInventoryCounts
   * Returns current counts for the provided [CatalogObject](#type-catalogobject)s at the requested [Location](#type-location)s.  Results are paginated and sorted in descending order according to their &#x60;calculated_at&#x60; timestamp (newest first).  When &#x60;updated_after&#x60; is specified, only counts that have changed since that time (based on the server timestamp for the most recent change) are returned. This allows clients to perform a \&quot;sync\&quot; operation, for example in response to receiving a Webhook notification.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchRetrieveInventoryCountsResponse
   * @throws ApiException if fails to make API call
   */  public BatchRetrieveInventoryCountsResponse batchRetrieveInventoryCounts(BatchRetrieveInventoryCountsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveInventoryCounts");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-retrieve-counts";

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

    GenericType<BatchRetrieveInventoryCountsResponse> localVarReturnType = new GenericType<BatchRetrieveInventoryCountsResponse>() {};
    CompleteResponse<BatchRetrieveInventoryCountsResponse> completeResponse = (CompleteResponse<BatchRetrieveInventoryCountsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchRetrieveInventoryCounts
   * Returns current counts for the provided [CatalogObject](#type-catalogobject)s at the requested [Location](#type-location)s.  Results are paginated and sorted in descending order according to their &#x60;calculated_at&#x60; timestamp (newest first).  When &#x60;updated_after&#x60; is specified, only counts that have changed since that time (based on the server timestamp for the most recent change) are returned. This allows clients to perform a \&quot;sync\&quot; operation, for example in response to receiving a Webhook notification.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchRetrieveInventoryCountsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchRetrieveInventoryCountsResponse>batchRetrieveInventoryCountsWithHttpInfo(BatchRetrieveInventoryCountsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveInventoryCounts");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/batch-retrieve-counts";

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

    GenericType<BatchRetrieveInventoryCountsResponse> localVarReturnType = new GenericType<BatchRetrieveInventoryCountsResponse>() {};
    return (CompleteResponse<BatchRetrieveInventoryCountsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveInventoryAdjustment
   * Returns the [InventoryAdjustment](#type-inventoryadjustment) object with the provided &#x60;adjustment_id&#x60;.
   * @param adjustmentId ID of the [InventoryAdjustment](#type-inventoryadjustment) to retrieve. (required)
   * @return RetrieveInventoryAdjustmentResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveInventoryAdjustmentResponse retrieveInventoryAdjustment(String adjustmentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'adjustmentId' is set
    if (adjustmentId == null) {
      throw new ApiException(400, "Missing the required parameter 'adjustmentId' when calling retrieveInventoryAdjustment");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/adjustment/{adjustment_id}"
      .replaceAll("\\{" + "adjustment_id" + "\\}", apiClient.escapeString(adjustmentId.toString()));

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

    GenericType<RetrieveInventoryAdjustmentResponse> localVarReturnType = new GenericType<RetrieveInventoryAdjustmentResponse>() {};
    CompleteResponse<RetrieveInventoryAdjustmentResponse> completeResponse = (CompleteResponse<RetrieveInventoryAdjustmentResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveInventoryAdjustment
   * Returns the [InventoryAdjustment](#type-inventoryadjustment) object with the provided &#x60;adjustment_id&#x60;.
   * @param adjustmentId ID of the [InventoryAdjustment](#type-inventoryadjustment) to retrieve. (required)
   * @return CompleteResponse<RetrieveInventoryAdjustmentResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveInventoryAdjustmentResponse>retrieveInventoryAdjustmentWithHttpInfo(String adjustmentId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'adjustmentId' is set
    if (adjustmentId == null) {
      throw new ApiException(400, "Missing the required parameter 'adjustmentId' when calling retrieveInventoryAdjustment");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/adjustment/{adjustment_id}"
      .replaceAll("\\{" + "adjustment_id" + "\\}", apiClient.escapeString(adjustmentId.toString()));

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

    GenericType<RetrieveInventoryAdjustmentResponse> localVarReturnType = new GenericType<RetrieveInventoryAdjustmentResponse>() {};
    return (CompleteResponse<RetrieveInventoryAdjustmentResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveInventoryChanges
   * Returns a set of physical counts and inventory adjustments for the provided [CatalogObject](#type-catalogobject) at the requested [Location](#type-location)s.  Results are paginated and sorted in descending order according to their &#x60;occurred_at&#x60; timestamp (newest first).  There are no limits on how far back the caller can page. This endpoint is useful when displaying recent changes for a specific item. For more sophisticated queries, use a batch endpoint.
   * @param catalogObjectId ID of the [CatalogObject](#type-catalogobject) to retrieve. (required)
   * @param locationIds The [Location](#type-location) IDs to look up as a comma-separated list. An empty list queries all locations. (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return RetrieveInventoryChangesResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveInventoryChangesResponse retrieveInventoryChanges(String catalogObjectId, String locationIds, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalogObjectId' is set
    if (catalogObjectId == null) {
      throw new ApiException(400, "Missing the required parameter 'catalogObjectId' when calling retrieveInventoryChanges");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/{catalog_object_id}/changes"
      .replaceAll("\\{" + "catalog_object_id" + "\\}", apiClient.escapeString(catalogObjectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_ids", locationIds));
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

    GenericType<RetrieveInventoryChangesResponse> localVarReturnType = new GenericType<RetrieveInventoryChangesResponse>() {};
    CompleteResponse<RetrieveInventoryChangesResponse> completeResponse = (CompleteResponse<RetrieveInventoryChangesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveInventoryChanges
   * Returns a set of physical counts and inventory adjustments for the provided [CatalogObject](#type-catalogobject) at the requested [Location](#type-location)s.  Results are paginated and sorted in descending order according to their &#x60;occurred_at&#x60; timestamp (newest first).  There are no limits on how far back the caller can page. This endpoint is useful when displaying recent changes for a specific item. For more sophisticated queries, use a batch endpoint.
   * @param catalogObjectId ID of the [CatalogObject](#type-catalogobject) to retrieve. (required)
   * @param locationIds The [Location](#type-location) IDs to look up as a comma-separated list. An empty list queries all locations. (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<RetrieveInventoryChangesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveInventoryChangesResponse>retrieveInventoryChangesWithHttpInfo(String catalogObjectId, String locationIds, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalogObjectId' is set
    if (catalogObjectId == null) {
      throw new ApiException(400, "Missing the required parameter 'catalogObjectId' when calling retrieveInventoryChanges");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/{catalog_object_id}/changes"
      .replaceAll("\\{" + "catalog_object_id" + "\\}", apiClient.escapeString(catalogObjectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_ids", locationIds));
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

    GenericType<RetrieveInventoryChangesResponse> localVarReturnType = new GenericType<RetrieveInventoryChangesResponse>() {};
    return (CompleteResponse<RetrieveInventoryChangesResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveInventoryCount
   * Retrieves the current calculated stock count for a given [CatalogObject](#type-catalogobject) at a given set of [Location](#type-location)s. Responses are paginated and unsorted. For more sophisticated queries, use a batch endpoint.
   * @param catalogObjectId ID of the [CatalogObject](#type-catalogobject) to retrieve. (required)
   * @param locationIds The [Location](#type-location) IDs to look up as a comma-separated list. An empty list queries all locations. (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return RetrieveInventoryCountResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveInventoryCountResponse retrieveInventoryCount(String catalogObjectId, String locationIds, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalogObjectId' is set
    if (catalogObjectId == null) {
      throw new ApiException(400, "Missing the required parameter 'catalogObjectId' when calling retrieveInventoryCount");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/{catalog_object_id}"
      .replaceAll("\\{" + "catalog_object_id" + "\\}", apiClient.escapeString(catalogObjectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_ids", locationIds));
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

    GenericType<RetrieveInventoryCountResponse> localVarReturnType = new GenericType<RetrieveInventoryCountResponse>() {};
    CompleteResponse<RetrieveInventoryCountResponse> completeResponse = (CompleteResponse<RetrieveInventoryCountResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveInventoryCount
   * Retrieves the current calculated stock count for a given [CatalogObject](#type-catalogobject) at a given set of [Location](#type-location)s. Responses are paginated and unsorted. For more sophisticated queries, use a batch endpoint.
   * @param catalogObjectId ID of the [CatalogObject](#type-catalogobject) to retrieve. (required)
   * @param locationIds The [Location](#type-location) IDs to look up as a comma-separated list. An empty list queries all locations. (optional)
   * @param cursor A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @return CompleteResponse<RetrieveInventoryCountResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveInventoryCountResponse>retrieveInventoryCountWithHttpInfo(String catalogObjectId, String locationIds, String cursor) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'catalogObjectId' is set
    if (catalogObjectId == null) {
      throw new ApiException(400, "Missing the required parameter 'catalogObjectId' when calling retrieveInventoryCount");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/{catalog_object_id}"
      .replaceAll("\\{" + "catalog_object_id" + "\\}", apiClient.escapeString(catalogObjectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "location_ids", locationIds));
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

    GenericType<RetrieveInventoryCountResponse> localVarReturnType = new GenericType<RetrieveInventoryCountResponse>() {};
    return (CompleteResponse<RetrieveInventoryCountResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveInventoryPhysicalCount
   * Returns the [InventoryPhysicalCount](#type-inventoryphysicalcount) object with the provided &#x60;physical_count_id&#x60;.
   * @param physicalCountId ID of the [InventoryPhysicalCount](#type-inventoryphysicalcount) to retrieve. (required)
   * @return RetrieveInventoryPhysicalCountResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveInventoryPhysicalCountResponse retrieveInventoryPhysicalCount(String physicalCountId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'physicalCountId' is set
    if (physicalCountId == null) {
      throw new ApiException(400, "Missing the required parameter 'physicalCountId' when calling retrieveInventoryPhysicalCount");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/physical-count/{physical_count_id}"
      .replaceAll("\\{" + "physical_count_id" + "\\}", apiClient.escapeString(physicalCountId.toString()));

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

    GenericType<RetrieveInventoryPhysicalCountResponse> localVarReturnType = new GenericType<RetrieveInventoryPhysicalCountResponse>() {};
    CompleteResponse<RetrieveInventoryPhysicalCountResponse> completeResponse = (CompleteResponse<RetrieveInventoryPhysicalCountResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveInventoryPhysicalCount
   * Returns the [InventoryPhysicalCount](#type-inventoryphysicalcount) object with the provided &#x60;physical_count_id&#x60;.
   * @param physicalCountId ID of the [InventoryPhysicalCount](#type-inventoryphysicalcount) to retrieve. (required)
   * @return CompleteResponse<RetrieveInventoryPhysicalCountResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveInventoryPhysicalCountResponse>retrieveInventoryPhysicalCountWithHttpInfo(String physicalCountId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'physicalCountId' is set
    if (physicalCountId == null) {
      throw new ApiException(400, "Missing the required parameter 'physicalCountId' when calling retrieveInventoryPhysicalCount");
    }

    // create path and map variables
    String localVarPath = "/v2/inventory/physical-count/{physical_count_id}"
      .replaceAll("\\{" + "physical_count_id" + "\\}", apiClient.escapeString(physicalCountId.toString()));

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

    GenericType<RetrieveInventoryPhysicalCountResponse> localVarReturnType = new GenericType<RetrieveInventoryPhysicalCountResponse>() {};
    return (CompleteResponse<RetrieveInventoryPhysicalCountResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
