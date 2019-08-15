package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.BatchDeleteCatalogObjectsRequest;
import com.squareup.connect.models.BatchDeleteCatalogObjectsResponse;
import com.squareup.connect.models.BatchRetrieveCatalogObjectsRequest;
import com.squareup.connect.models.BatchRetrieveCatalogObjectsResponse;
import com.squareup.connect.models.BatchUpsertCatalogObjectsRequest;
import com.squareup.connect.models.BatchUpsertCatalogObjectsResponse;
import com.squareup.connect.models.CatalogInfoResponse;
import com.squareup.connect.models.DeleteCatalogObjectResponse;
import com.squareup.connect.models.ListCatalogResponse;
import com.squareup.connect.models.RetrieveCatalogObjectResponse;
import com.squareup.connect.models.SearchCatalogObjectsRequest;
import com.squareup.connect.models.SearchCatalogObjectsResponse;
import com.squareup.connect.models.UpdateItemModifierListsRequest;
import com.squareup.connect.models.UpdateItemModifierListsResponse;
import com.squareup.connect.models.UpdateItemTaxesRequest;
import com.squareup.connect.models.UpdateItemTaxesResponse;
import com.squareup.connect.models.UpsertCatalogObjectRequest;
import com.squareup.connect.models.UpsertCatalogObjectResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CatalogApi {
  private ApiClient apiClient;

  public CatalogApi() {
    this(Configuration.getDefaultApiClient());
  }

  public CatalogApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * BatchDeleteCatalogObjects
   * Deletes a set of [CatalogItem](#type-catalogitem)s based on the provided list of target IDs and returns a set of successfully deleted IDs in the response. Deletion is a cascading event such that all children of the targeted object are also deleted. For example, deleting a CatalogItem will also delete all of its [CatalogItemVariation](#type-catalogitemvariation) children.  &#x60;BatchDeleteCatalogObjects&#x60; succeeds even if only a portion of the targeted IDs can be deleted. The response will only include IDs that were actually deleted.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchDeleteCatalogObjectsResponse
   * @throws ApiException if fails to make API call
   */  public BatchDeleteCatalogObjectsResponse batchDeleteCatalogObjects(BatchDeleteCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchDeleteCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-delete";

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

    GenericType<BatchDeleteCatalogObjectsResponse> localVarReturnType = new GenericType<BatchDeleteCatalogObjectsResponse>() {};
    CompleteResponse<BatchDeleteCatalogObjectsResponse> completeResponse = (CompleteResponse<BatchDeleteCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchDeleteCatalogObjects
   * Deletes a set of [CatalogItem](#type-catalogitem)s based on the provided list of target IDs and returns a set of successfully deleted IDs in the response. Deletion is a cascading event such that all children of the targeted object are also deleted. For example, deleting a CatalogItem will also delete all of its [CatalogItemVariation](#type-catalogitemvariation) children.  &#x60;BatchDeleteCatalogObjects&#x60; succeeds even if only a portion of the targeted IDs can be deleted. The response will only include IDs that were actually deleted.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchDeleteCatalogObjectsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchDeleteCatalogObjectsResponse>batchDeleteCatalogObjectsWithHttpInfo(BatchDeleteCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchDeleteCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-delete";

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

    GenericType<BatchDeleteCatalogObjectsResponse> localVarReturnType = new GenericType<BatchDeleteCatalogObjectsResponse>() {};
    return (CompleteResponse<BatchDeleteCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * BatchRetrieveCatalogObjects
   * Returns a set of objects based on the provided ID. Each [CatalogItem](#type-catalogitem) returned in the set includes all of its child information including: all of its [CatalogItemVariation](#type-catalogitemvariation) objects, references to its [CatalogModifierList](#type-catalogmodifierlist) objects, and the ids of any [CatalogTax](#type-catalogtax) objects that apply to it.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchRetrieveCatalogObjectsResponse
   * @throws ApiException if fails to make API call
   */  public BatchRetrieveCatalogObjectsResponse batchRetrieveCatalogObjects(BatchRetrieveCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-retrieve";

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

    GenericType<BatchRetrieveCatalogObjectsResponse> localVarReturnType = new GenericType<BatchRetrieveCatalogObjectsResponse>() {};
    CompleteResponse<BatchRetrieveCatalogObjectsResponse> completeResponse = (CompleteResponse<BatchRetrieveCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchRetrieveCatalogObjects
   * Returns a set of objects based on the provided ID. Each [CatalogItem](#type-catalogitem) returned in the set includes all of its child information including: all of its [CatalogItemVariation](#type-catalogitemvariation) objects, references to its [CatalogModifierList](#type-catalogmodifierlist) objects, and the ids of any [CatalogTax](#type-catalogtax) objects that apply to it.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchRetrieveCatalogObjectsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchRetrieveCatalogObjectsResponse>batchRetrieveCatalogObjectsWithHttpInfo(BatchRetrieveCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-retrieve";

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

    GenericType<BatchRetrieveCatalogObjectsResponse> localVarReturnType = new GenericType<BatchRetrieveCatalogObjectsResponse>() {};
    return (CompleteResponse<BatchRetrieveCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * BatchUpsertCatalogObjects
   * Creates or updates up to 10,000 target objects based on the provided list of objects. The target objects are grouped into batches and each batch is inserted/updated in an all-or-nothing manner. If an object within a batch is malformed in some way, or violates a database constraint, the entire batch containing that item will be disregarded. However, other batches in the same request may still succeed. Each batch may contain up to 1,000 objects, and batches will be processed in order as long as the total object count for the request (items, variations, modifier lists, discounts, and taxes) is no more than 10,000.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchUpsertCatalogObjectsResponse
   * @throws ApiException if fails to make API call
   */  public BatchUpsertCatalogObjectsResponse batchUpsertCatalogObjects(BatchUpsertCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchUpsertCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-upsert";

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

    GenericType<BatchUpsertCatalogObjectsResponse> localVarReturnType = new GenericType<BatchUpsertCatalogObjectsResponse>() {};
    CompleteResponse<BatchUpsertCatalogObjectsResponse> completeResponse = (CompleteResponse<BatchUpsertCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchUpsertCatalogObjects
   * Creates or updates up to 10,000 target objects based on the provided list of objects. The target objects are grouped into batches and each batch is inserted/updated in an all-or-nothing manner. If an object within a batch is malformed in some way, or violates a database constraint, the entire batch containing that item will be disregarded. However, other batches in the same request may still succeed. Each batch may contain up to 1,000 objects, and batches will be processed in order as long as the total object count for the request (items, variations, modifier lists, discounts, and taxes) is no more than 10,000.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchUpsertCatalogObjectsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchUpsertCatalogObjectsResponse>batchUpsertCatalogObjectsWithHttpInfo(BatchUpsertCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchUpsertCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/batch-upsert";

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

    GenericType<BatchUpsertCatalogObjectsResponse> localVarReturnType = new GenericType<BatchUpsertCatalogObjectsResponse>() {};
    return (CompleteResponse<BatchUpsertCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CatalogInfo
   * Returns information about the Square Catalog API, such as batch size limits for &#x60;BatchUpsertCatalogObjects&#x60;.
   * @return CatalogInfoResponse
   * @throws ApiException if fails to make API call
   */  public CatalogInfoResponse catalogInfo() throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/catalog/info";

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

    GenericType<CatalogInfoResponse> localVarReturnType = new GenericType<CatalogInfoResponse>() {};
    CompleteResponse<CatalogInfoResponse> completeResponse = (CompleteResponse<CatalogInfoResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CatalogInfo
   * Returns information about the Square Catalog API, such as batch size limits for &#x60;BatchUpsertCatalogObjects&#x60;.
   * @return CompleteResponse<CatalogInfoResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CatalogInfoResponse>catalogInfoWithHttpInfo() throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/catalog/info";

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

    GenericType<CatalogInfoResponse> localVarReturnType = new GenericType<CatalogInfoResponse>() {};
    return (CompleteResponse<CatalogInfoResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteCatalogObject
   * Deletes a single [CatalogObject](#type-catalogobject) based on the provided ID and returns the set of successfully deleted IDs in the response. Deletion is a cascading event such that all children of the targeted object are also deleted. For example, deleting a [CatalogItem](#type-catalogitem) will also delete all of its [CatalogItemVariation](#type-catalogitemvariation) children.
   * @param objectId The ID of the [CatalogObject](#type-catalogobject) to be deleted. When an object is deleted, other objects in the graph that depend on that object will be deleted as well (for example, deleting a [CatalogItem](#type-catalogitem) will delete its [CatalogItemVariation](#type-catalogitemvariation)s). (required)
   * @return DeleteCatalogObjectResponse
   * @throws ApiException if fails to make API call
   */  public DeleteCatalogObjectResponse deleteCatalogObject(String objectId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'objectId' is set
    if (objectId == null) {
      throw new ApiException(400, "Missing the required parameter 'objectId' when calling deleteCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object/{object_id}"
      .replaceAll("\\{" + "object_id" + "\\}", apiClient.escapeString(objectId.toString()));

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

    GenericType<DeleteCatalogObjectResponse> localVarReturnType = new GenericType<DeleteCatalogObjectResponse>() {};
    CompleteResponse<DeleteCatalogObjectResponse> completeResponse = (CompleteResponse<DeleteCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteCatalogObject
   * Deletes a single [CatalogObject](#type-catalogobject) based on the provided ID and returns the set of successfully deleted IDs in the response. Deletion is a cascading event such that all children of the targeted object are also deleted. For example, deleting a [CatalogItem](#type-catalogitem) will also delete all of its [CatalogItemVariation](#type-catalogitemvariation) children.
   * @param objectId The ID of the [CatalogObject](#type-catalogobject) to be deleted. When an object is deleted, other objects in the graph that depend on that object will be deleted as well (for example, deleting a [CatalogItem](#type-catalogitem) will delete its [CatalogItemVariation](#type-catalogitemvariation)s). (required)
   * @return CompleteResponse<DeleteCatalogObjectResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<DeleteCatalogObjectResponse>deleteCatalogObjectWithHttpInfo(String objectId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'objectId' is set
    if (objectId == null) {
      throw new ApiException(400, "Missing the required parameter 'objectId' when calling deleteCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object/{object_id}"
      .replaceAll("\\{" + "object_id" + "\\}", apiClient.escapeString(objectId.toString()));

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

    GenericType<DeleteCatalogObjectResponse> localVarReturnType = new GenericType<DeleteCatalogObjectResponse>() {};
    return (CompleteResponse<DeleteCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListCatalog
   * Returns a list of [CatalogObject](#type-catalogobject)s that includes all objects of a set of desired types (for example, all [CatalogItem](#type-catalogitem) and [CatalogTax](#type-catalogtax) objects) in the catalog. The &#x60;types&#x60; parameter is specified as a comma-separated list of valid [CatalogObject](#type-catalogobject) types: &#x60;ITEM&#x60;, &#x60;ITEM_VARIATION&#x60;, &#x60;MODIFIER&#x60;, &#x60;MODIFIER_LIST&#x60;, &#x60;CATEGORY&#x60;, &#x60;DISCOUNT&#x60;, &#x60;TAX&#x60;.  __Important:__ ListCatalog does not return deleted catalog items. To retrieve deleted catalog items, use SearchCatalogObjects and set &#x60;include_deleted_objects&#x60; to &#x60;true&#x60;.
   * @param cursor The pagination cursor returned in the previous response. Leave unset for an initial request. See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param types An optional case-insensitive, comma-separated list of object types to retrieve, for example &#x60;ITEM,ITEM_VARIATION,CATEGORY,IMAGE&#x60;.  The legal values are taken from the [CatalogObjectType](#type-catalogobjecttype) enumeration, namely &#x60;ITEM&#x60;, &#x60;ITEM_VARIATION&#x60;, &#x60;CATEGORY&#x60;, &#x60;DISCOUNT&#x60;, &#x60;TAX&#x60;, &#x60;MODIFIER&#x60;, &#x60;MODIFIER_LIST&#x60;, or &#x60;IMAGE&#x60;. (optional)
   * @return ListCatalogResponse
   * @throws ApiException if fails to make API call
   */  public ListCatalogResponse listCatalog(String cursor, String types) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/catalog/list";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "types", types));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListCatalogResponse> localVarReturnType = new GenericType<ListCatalogResponse>() {};
    CompleteResponse<ListCatalogResponse> completeResponse = (CompleteResponse<ListCatalogResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListCatalog
   * Returns a list of [CatalogObject](#type-catalogobject)s that includes all objects of a set of desired types (for example, all [CatalogItem](#type-catalogitem) and [CatalogTax](#type-catalogtax) objects) in the catalog. The &#x60;types&#x60; parameter is specified as a comma-separated list of valid [CatalogObject](#type-catalogobject) types: &#x60;ITEM&#x60;, &#x60;ITEM_VARIATION&#x60;, &#x60;MODIFIER&#x60;, &#x60;MODIFIER_LIST&#x60;, &#x60;CATEGORY&#x60;, &#x60;DISCOUNT&#x60;, &#x60;TAX&#x60;.  __Important:__ ListCatalog does not return deleted catalog items. To retrieve deleted catalog items, use SearchCatalogObjects and set &#x60;include_deleted_objects&#x60; to &#x60;true&#x60;.
   * @param cursor The pagination cursor returned in the previous response. Leave unset for an initial request. See [Pagination](/basics/api101/pagination) for more information. (optional)
   * @param types An optional case-insensitive, comma-separated list of object types to retrieve, for example &#x60;ITEM,ITEM_VARIATION,CATEGORY,IMAGE&#x60;.  The legal values are taken from the [CatalogObjectType](#type-catalogobjecttype) enumeration, namely &#x60;ITEM&#x60;, &#x60;ITEM_VARIATION&#x60;, &#x60;CATEGORY&#x60;, &#x60;DISCOUNT&#x60;, &#x60;TAX&#x60;, &#x60;MODIFIER&#x60;, &#x60;MODIFIER_LIST&#x60;, or &#x60;IMAGE&#x60;. (optional)
   * @return CompleteResponse<ListCatalogResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ListCatalogResponse>listCatalogWithHttpInfo(String cursor, String types) throws ApiException {
    Object localVarPostBody = null;

    // create path and map variables
    String localVarPath = "/v2/catalog/list";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "cursor", cursor));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "types", types));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<ListCatalogResponse> localVarReturnType = new GenericType<ListCatalogResponse>() {};
    return (CompleteResponse<ListCatalogResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveCatalogObject
   * Returns a single [CatalogItem](#type-catalogitem) as a [CatalogObject](#type-catalogobject) based on the provided ID. The returned object includes all of the relevant [CatalogItem](#type-catalogitem) information including: [CatalogItemVariation](#type-catalogitemvariation) children, references to its [CatalogModifierList](#type-catalogmodifierlist) objects, and the ids of any [CatalogTax](#type-catalogtax) objects that apply to it.
   * @param objectId The object ID of any type of [CatalogObject](#type-catalogobject)s to be retrieved. (required)
   * @param includeRelatedObjects If &#x60;true&#x60;, the response will include additional objects that are related to the requested object, as follows:  If the &#x60;object&#x60; field of the response contains a [CatalogItem](#type-catalogitem), its associated [CatalogCategory](#type-catalogcategory), [CatalogTax](#type-catalogtax)es, [CatalogImage](#type-catalogimage)s and [CatalogModifierList](#type-catalogmodifierlist)s will be returned in the &#x60;related_objects&#x60; field of the response. If the &#x60;object&#x60; field of the response contains a [CatalogItemVariation](#type-catalogitemvariation), its parent [CatalogItem](#type-catalogitem) will be returned in the &#x60;related_objects&#x60; field of  the response.  Default value: &#x60;false&#x60; (optional)
   * @return RetrieveCatalogObjectResponse
   * @throws ApiException if fails to make API call
   */  public RetrieveCatalogObjectResponse retrieveCatalogObject(String objectId, Boolean includeRelatedObjects) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'objectId' is set
    if (objectId == null) {
      throw new ApiException(400, "Missing the required parameter 'objectId' when calling retrieveCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object/{object_id}"
      .replaceAll("\\{" + "object_id" + "\\}", apiClient.escapeString(objectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "include_related_objects", includeRelatedObjects));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<RetrieveCatalogObjectResponse> localVarReturnType = new GenericType<RetrieveCatalogObjectResponse>() {};
    CompleteResponse<RetrieveCatalogObjectResponse> completeResponse = (CompleteResponse<RetrieveCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveCatalogObject
   * Returns a single [CatalogItem](#type-catalogitem) as a [CatalogObject](#type-catalogobject) based on the provided ID. The returned object includes all of the relevant [CatalogItem](#type-catalogitem) information including: [CatalogItemVariation](#type-catalogitemvariation) children, references to its [CatalogModifierList](#type-catalogmodifierlist) objects, and the ids of any [CatalogTax](#type-catalogtax) objects that apply to it.
   * @param objectId The object ID of any type of [CatalogObject](#type-catalogobject)s to be retrieved. (required)
   * @param includeRelatedObjects If &#x60;true&#x60;, the response will include additional objects that are related to the requested object, as follows:  If the &#x60;object&#x60; field of the response contains a [CatalogItem](#type-catalogitem), its associated [CatalogCategory](#type-catalogcategory), [CatalogTax](#type-catalogtax)es, [CatalogImage](#type-catalogimage)s and [CatalogModifierList](#type-catalogmodifierlist)s will be returned in the &#x60;related_objects&#x60; field of the response. If the &#x60;object&#x60; field of the response contains a [CatalogItemVariation](#type-catalogitemvariation), its parent [CatalogItem](#type-catalogitem) will be returned in the &#x60;related_objects&#x60; field of  the response.  Default value: &#x60;false&#x60; (optional)
   * @return CompleteResponse<RetrieveCatalogObjectResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RetrieveCatalogObjectResponse>retrieveCatalogObjectWithHttpInfo(String objectId, Boolean includeRelatedObjects) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'objectId' is set
    if (objectId == null) {
      throw new ApiException(400, "Missing the required parameter 'objectId' when calling retrieveCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object/{object_id}"
      .replaceAll("\\{" + "object_id" + "\\}", apiClient.escapeString(objectId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "include_related_objects", includeRelatedObjects));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<RetrieveCatalogObjectResponse> localVarReturnType = new GenericType<RetrieveCatalogObjectResponse>() {};
    return (CompleteResponse<RetrieveCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * SearchCatalogObjects
   * Queries the targeted catalog using a variety of query types: [CatalogQuerySortedAttribute](#type-catalogquerysortedattribute), [CatalogQueryExact](#type-catalogqueryexact), [CatalogQueryRange](#type-catalogqueryrange), [CatalogQueryText](#type-catalogquerytext), [CatalogQueryItemsForTax](#type-catalogqueryitemsfortax), and [CatalogQueryItemsForModifierList](#type-catalogqueryitemsformodifierlist).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return SearchCatalogObjectsResponse
   * @throws ApiException if fails to make API call
   */  public SearchCatalogObjectsResponse searchCatalogObjects(SearchCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/search";

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

    GenericType<SearchCatalogObjectsResponse> localVarReturnType = new GenericType<SearchCatalogObjectsResponse>() {};
    CompleteResponse<SearchCatalogObjectsResponse> completeResponse = (CompleteResponse<SearchCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * SearchCatalogObjects
   * Queries the targeted catalog using a variety of query types: [CatalogQuerySortedAttribute](#type-catalogquerysortedattribute), [CatalogQueryExact](#type-catalogqueryexact), [CatalogQueryRange](#type-catalogqueryrange), [CatalogQueryText](#type-catalogquerytext), [CatalogQueryItemsForTax](#type-catalogqueryitemsfortax), and [CatalogQueryItemsForModifierList](#type-catalogqueryitemsformodifierlist).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<SearchCatalogObjectsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<SearchCatalogObjectsResponse>searchCatalogObjectsWithHttpInfo(SearchCatalogObjectsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchCatalogObjects");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/search";

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

    GenericType<SearchCatalogObjectsResponse> localVarReturnType = new GenericType<SearchCatalogObjectsResponse>() {};
    return (CompleteResponse<SearchCatalogObjectsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateItemModifierLists
   * Updates the [CatalogModifierList](#type-catalogmodifierlist) objects that apply to the targeted [CatalogItem](#type-catalogitem) without having to perform an upsert on the entire item.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateItemModifierListsResponse
   * @throws ApiException if fails to make API call
   */  public UpdateItemModifierListsResponse updateItemModifierLists(UpdateItemModifierListsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItemModifierLists");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/update-item-modifier-lists";

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

    GenericType<UpdateItemModifierListsResponse> localVarReturnType = new GenericType<UpdateItemModifierListsResponse>() {};
    CompleteResponse<UpdateItemModifierListsResponse> completeResponse = (CompleteResponse<UpdateItemModifierListsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateItemModifierLists
   * Updates the [CatalogModifierList](#type-catalogmodifierlist) objects that apply to the targeted [CatalogItem](#type-catalogitem) without having to perform an upsert on the entire item.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateItemModifierListsResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateItemModifierListsResponse>updateItemModifierListsWithHttpInfo(UpdateItemModifierListsRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItemModifierLists");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/update-item-modifier-lists";

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

    GenericType<UpdateItemModifierListsResponse> localVarReturnType = new GenericType<UpdateItemModifierListsResponse>() {};
    return (CompleteResponse<UpdateItemModifierListsResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateItemTaxes
   * Updates the [CatalogTax](#type-catalogtax) objects that apply to the targeted [CatalogItem](#type-catalogitem) without having to perform an upsert on the entire item.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateItemTaxesResponse
   * @throws ApiException if fails to make API call
   */  public UpdateItemTaxesResponse updateItemTaxes(UpdateItemTaxesRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItemTaxes");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/update-item-taxes";

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

    GenericType<UpdateItemTaxesResponse> localVarReturnType = new GenericType<UpdateItemTaxesResponse>() {};
    CompleteResponse<UpdateItemTaxesResponse> completeResponse = (CompleteResponse<UpdateItemTaxesResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateItemTaxes
   * Updates the [CatalogTax](#type-catalogtax) objects that apply to the targeted [CatalogItem](#type-catalogitem) without having to perform an upsert on the entire item.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateItemTaxesResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateItemTaxesResponse>updateItemTaxesWithHttpInfo(UpdateItemTaxesRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItemTaxes");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/update-item-taxes";

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

    GenericType<UpdateItemTaxesResponse> localVarReturnType = new GenericType<UpdateItemTaxesResponse>() {};
    return (CompleteResponse<UpdateItemTaxesResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpsertCatalogObject
   * Creates or updates the target [CatalogObject](#type-catalogobject).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpsertCatalogObjectResponse
   * @throws ApiException if fails to make API call
   */  public UpsertCatalogObjectResponse upsertCatalogObject(UpsertCatalogObjectRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling upsertCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object";

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

    GenericType<UpsertCatalogObjectResponse> localVarReturnType = new GenericType<UpsertCatalogObjectResponse>() {};
    CompleteResponse<UpsertCatalogObjectResponse> completeResponse = (CompleteResponse<UpsertCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpsertCatalogObject
   * Creates or updates the target [CatalogObject](#type-catalogobject).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpsertCatalogObjectResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpsertCatalogObjectResponse>upsertCatalogObjectWithHttpInfo(UpsertCatalogObjectRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling upsertCatalogObject");
    }

    // create path and map variables
    String localVarPath = "/v2/catalog/object";

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

    GenericType<UpsertCatalogObjectResponse> localVarReturnType = new GenericType<UpsertCatalogObjectResponse>() {};
    return (CompleteResponse<UpsertCatalogObjectResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
