package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.V1AdjustInventoryRequest;
import com.squareup.connect.models.V1Category;
import com.squareup.connect.models.V1Discount;
import com.squareup.connect.models.V1Fee;
import com.squareup.connect.models.V1InventoryEntry;
import com.squareup.connect.models.V1Item;
import com.squareup.connect.models.V1ModifierList;
import com.squareup.connect.models.V1ModifierOption;
import com.squareup.connect.models.V1Page;
import com.squareup.connect.models.V1PageCell;
import com.squareup.connect.models.V1UpdateModifierListRequest;
import com.squareup.connect.models.V1Variation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class V1ItemsApi {
  private ApiClient apiClient;

  public V1ItemsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public V1ItemsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * AdjustInventory
   * Adjusts an item variation&#39;s current available inventory.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param variationId The ID of the variation to adjust inventory information for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1InventoryEntry
   * @throws ApiException if fails to make API call
   */  public V1InventoryEntry adjustInventory(String locationId, String variationId, V1AdjustInventoryRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling adjustInventory");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling adjustInventory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling adjustInventory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/inventory/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1InventoryEntry> localVarReturnType = new GenericType<V1InventoryEntry>() {};
    CompleteResponse<V1InventoryEntry> completeResponse = (CompleteResponse<V1InventoryEntry>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * AdjustInventory
   * Adjusts an item variation&#39;s current available inventory.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param variationId The ID of the variation to adjust inventory information for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1InventoryEntry>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1InventoryEntry>adjustInventoryWithHttpInfo(String locationId, String variationId, V1AdjustInventoryRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling adjustInventory");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling adjustInventory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling adjustInventory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/inventory/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1InventoryEntry> localVarReturnType = new GenericType<V1InventoryEntry>() {};
    return (CompleteResponse<V1InventoryEntry>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ApplyFee
   * Associates a fee with an item, meaning the fee is automatically applied to the item in Square Register.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param itemId The ID of the item to add the fee to. (required)
   * @param feeId The ID of the fee to apply. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item applyFee(String locationId, String itemId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling applyFee");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling applyFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling applyFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ApplyFee
   * Associates a fee with an item, meaning the fee is automatically applied to the item in Square Register.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param itemId The ID of the item to add the fee to. (required)
   * @param feeId The ID of the fee to apply. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>applyFeeWithHttpInfo(String locationId, String itemId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling applyFee");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling applyFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling applyFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ApplyModifierList
   * Associates a modifier list with an item, meaning modifier options from the list can be applied to the item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to apply. (required)
   * @param itemId The ID of the item to add the modifier list to. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item applyModifierList(String locationId, String modifierListId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling applyModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling applyModifierList");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling applyModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ApplyModifierList
   * Associates a modifier list with an item, meaning modifier options from the list can be applied to the item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to apply. (required)
   * @param itemId The ID of the item to add the modifier list to. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>applyModifierListWithHttpInfo(String locationId, String modifierListId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling applyModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling applyModifierList");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling applyModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateCategory
   * Creates an item category.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Category
   * @throws ApiException if fails to make API call
   */  public V1Category createCategory(String locationId, V1Category body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createCategory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories"
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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    CompleteResponse<V1Category> completeResponse = (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateCategory
   * Creates an item category.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Category>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Category>createCategoryWithHttpInfo(String locationId, V1Category body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createCategory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories"
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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    return (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateDiscount
   * Creates a discount.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Discount
   * @throws ApiException if fails to make API call
   */  public V1Discount createDiscount(String locationId, V1Discount body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createDiscount");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts"
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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    CompleteResponse<V1Discount> completeResponse = (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateDiscount
   * Creates a discount.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Discount>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Discount>createDiscountWithHttpInfo(String locationId, V1Discount body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createDiscount");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts"
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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    return (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateFee
   * Creates a fee (tax).
   * @param locationId The ID of the location to create a fee for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Fee
   * @throws ApiException if fails to make API call
   */  public V1Fee createFee(String locationId, V1Fee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createFee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees"
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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    CompleteResponse<V1Fee> completeResponse = (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateFee
   * Creates a fee (tax).
   * @param locationId The ID of the location to create a fee for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Fee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Fee>createFeeWithHttpInfo(String locationId, V1Fee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createFee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees"
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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    return (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateItem
   * Creates an item and at least one variation for it. Item-related entities include fields you can use to associate them with entities in a non-Square system.  When you create an item-related entity, you can optionally specify its &#x60;id&#x60;. This value must be unique among all IDs ever specified for the account, including those specified by other applications. You can never reuse an entity ID. If you do not specify an ID, Square generates one for the entity.  Item variations have a &#x60;user_data&#x60; string that lets you associate arbitrary metadata with the variation. The string cannot exceed 255 characters.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item createItem(String locationId, V1Item body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createItem");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items"
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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateItem
   * Creates an item and at least one variation for it. Item-related entities include fields you can use to associate them with entities in a non-Square system.  When you create an item-related entity, you can optionally specify its &#x60;id&#x60;. This value must be unique among all IDs ever specified for the account, including those specified by other applications. You can never reuse an entity ID. If you do not specify an ID, Square generates one for the entity.  Item variations have a &#x60;user_data&#x60; string that lets you associate arbitrary metadata with the variation. The string cannot exceed 255 characters.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>createItemWithHttpInfo(String locationId, V1Item body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createItem");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items"
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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateModifierList
   * Creates an item modifier list and at least one modifier option for it.
   * @param locationId The ID of the location to create a modifier list for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1ModifierList
   * @throws ApiException if fails to make API call
   */  public V1ModifierList createModifierList(String locationId, V1ModifierList body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createModifierList");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists"
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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    CompleteResponse<V1ModifierList> completeResponse = (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateModifierList
   * Creates an item modifier list and at least one modifier option for it.
   * @param locationId The ID of the location to create a modifier list for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1ModifierList>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierList>createModifierListWithHttpInfo(String locationId, V1ModifierList body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createModifierList");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists"
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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    return (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateModifierOption
   * Creates an item modifier option and adds it to a modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1ModifierOption
   * @throws ApiException if fails to make API call
   */  public V1ModifierOption createModifierOption(String locationId, String modifierListId, V1ModifierOption body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling createModifierOption");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    CompleteResponse<V1ModifierOption> completeResponse = (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateModifierOption
   * Creates an item modifier option and adds it to a modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1ModifierOption>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierOption>createModifierOptionWithHttpInfo(String locationId, String modifierListId, V1ModifierOption body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling createModifierOption");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    return (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreatePage
   * Creates a Favorites page in Square Register.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Page
   * @throws ApiException if fails to make API call
   */  public V1Page createPage(String locationId, V1Page body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createPage");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createPage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages"
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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    CompleteResponse<V1Page> completeResponse = (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreatePage
   * Creates a Favorites page in Square Register.
   * @param locationId The ID of the location to create an item for. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Page>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Page>createPageWithHttpInfo(String locationId, V1Page body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createPage");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createPage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages"
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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    return (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateVariation
   * Creates an item variation for an existing item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The item&#39;s ID. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Variation
   * @throws ApiException if fails to make API call
   */  public V1Variation createVariation(String locationId, String itemId, V1Variation body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling createVariation");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    CompleteResponse<V1Variation> completeResponse = (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateVariation
   * Creates an item variation for an existing item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The item&#39;s ID. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Variation>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Variation>createVariationWithHttpInfo(String locationId, String itemId, V1Variation body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling createVariation");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    return (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteCategory
   * Deletes an existing item category. *Note**: DeleteCategory returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteCategoryRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param categoryId The ID of the category to delete. (required)
   * @return V1Category
   * @throws ApiException if fails to make API call
   */  public V1Category deleteCategory(String locationId, String categoryId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteCategory");
    }

    // verify the required parameter 'categoryId' is set
    if (categoryId == null) {
      throw new ApiException(400, "Missing the required parameter 'categoryId' when calling deleteCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories/{category_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "category_id" + "\\}", apiClient.escapeString(categoryId.toString()));

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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    CompleteResponse<V1Category> completeResponse = (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteCategory
   * Deletes an existing item category. *Note**: DeleteCategory returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteCategoryRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param categoryId The ID of the category to delete. (required)
   * @return CompleteResponse<V1Category>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Category>deleteCategoryWithHttpInfo(String locationId, String categoryId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteCategory");
    }

    // verify the required parameter 'categoryId' is set
    if (categoryId == null) {
      throw new ApiException(400, "Missing the required parameter 'categoryId' when calling deleteCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories/{category_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "category_id" + "\\}", apiClient.escapeString(categoryId.toString()));

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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    return (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteDiscount
   * Deletes an existing discount. *Note**: DeleteDiscount returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteDiscountRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param discountId The ID of the discount to delete. (required)
   * @return V1Discount
   * @throws ApiException if fails to make API call
   */  public V1Discount deleteDiscount(String locationId, String discountId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteDiscount");
    }

    // verify the required parameter 'discountId' is set
    if (discountId == null) {
      throw new ApiException(400, "Missing the required parameter 'discountId' when calling deleteDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts/{discount_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "discount_id" + "\\}", apiClient.escapeString(discountId.toString()));

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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    CompleteResponse<V1Discount> completeResponse = (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteDiscount
   * Deletes an existing discount. *Note**: DeleteDiscount returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteDiscountRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param discountId The ID of the discount to delete. (required)
   * @return CompleteResponse<V1Discount>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Discount>deleteDiscountWithHttpInfo(String locationId, String discountId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteDiscount");
    }

    // verify the required parameter 'discountId' is set
    if (discountId == null) {
      throw new ApiException(400, "Missing the required parameter 'discountId' when calling deleteDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts/{discount_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "discount_id" + "\\}", apiClient.escapeString(discountId.toString()));

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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    return (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteFee
   * Deletes an existing fee (tax). *Note**: DeleteFee returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteFeeRequest&#x60; object as documented below.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param feeId The ID of the fee to delete. (required)
   * @return V1Fee
   * @throws ApiException if fails to make API call
   */  public V1Fee deleteFee(String locationId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling deleteFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    CompleteResponse<V1Fee> completeResponse = (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteFee
   * Deletes an existing fee (tax). *Note**: DeleteFee returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteFeeRequest&#x60; object as documented below.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param feeId The ID of the fee to delete. (required)
   * @return CompleteResponse<V1Fee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Fee>deleteFeeWithHttpInfo(String locationId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling deleteFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    return (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteItem
   * Deletes an existing item and all item variations associated with it. *Note**: DeleteItem returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteItemRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item deleteItem(String locationId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling deleteItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteItem
   * Deletes an existing item and all item variations associated with it. *Note**: DeleteItem returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteItemRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>deleteItemWithHttpInfo(String locationId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling deleteItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteModifierList
   * Deletes an existing item modifier list and all modifier options associated with it. *Note**: DeleteModifierList returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteModifierListRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to delete. (required)
   * @return V1ModifierList
   * @throws ApiException if fails to make API call
   */  public V1ModifierList deleteModifierList(String locationId, String modifierListId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling deleteModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    CompleteResponse<V1ModifierList> completeResponse = (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteModifierList
   * Deletes an existing item modifier list and all modifier options associated with it. *Note**: DeleteModifierList returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteModifierListRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to delete. (required)
   * @return CompleteResponse<V1ModifierList>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierList>deleteModifierListWithHttpInfo(String locationId, String modifierListId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling deleteModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    return (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteModifierOption
   * Deletes an existing item modifier option from a modifier list. *Note**: DeleteModifierOption returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteModifierOptionRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to delete. (required)
   * @param modifierOptionId The ID of the modifier list to edit. (required)
   * @return V1ModifierOption
   * @throws ApiException if fails to make API call
   */  public V1ModifierOption deleteModifierOption(String locationId, String modifierListId, String modifierOptionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling deleteModifierOption");
    }

    // verify the required parameter 'modifierOptionId' is set
    if (modifierOptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierOptionId' when calling deleteModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "modifier_option_id" + "\\}", apiClient.escapeString(modifierOptionId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    CompleteResponse<V1ModifierOption> completeResponse = (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteModifierOption
   * Deletes an existing item modifier option from a modifier list. *Note**: DeleteModifierOption returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteModifierOptionRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to delete. (required)
   * @param modifierOptionId The ID of the modifier list to edit. (required)
   * @return CompleteResponse<V1ModifierOption>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierOption>deleteModifierOptionWithHttpInfo(String locationId, String modifierListId, String modifierOptionId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling deleteModifierOption");
    }

    // verify the required parameter 'modifierOptionId' is set
    if (modifierOptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierOptionId' when calling deleteModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "modifier_option_id" + "\\}", apiClient.escapeString(modifierOptionId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    return (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeletePage
   * Deletes an existing Favorites page and all of its cells. *Note**: DeletePage returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeletePageRequest&#x60; object as documented below.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page to delete. (required)
   * @return V1Page
   * @throws ApiException if fails to make API call
   */  public V1Page deletePage(String locationId, String pageId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deletePage");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling deletePage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    CompleteResponse<V1Page> completeResponse = (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeletePage
   * Deletes an existing Favorites page and all of its cells. *Note**: DeletePage returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeletePageRequest&#x60; object as documented below.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page to delete. (required)
   * @return CompleteResponse<V1Page>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Page>deletePageWithHttpInfo(String locationId, String pageId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deletePage");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling deletePage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    return (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeletePageCell
   * Deletes a cell from a Favorites page in Square Register. *Note**: DeletePageCell returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeletePageCellRequest&#x60; object as documented below.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page to delete. (required)
   * @param row The row of the cell to clear. Always an integer between 0 and 4, inclusive. Row 0 is the top row. (optional)
   * @param column The column of the cell to clear. Always an integer between 0 and 4, inclusive. Column 0 is the leftmost column. (optional)
   * @return V1Page
   * @throws ApiException if fails to make API call
   */  public V1Page deletePageCell(String locationId, String pageId, String row, String column) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deletePageCell");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling deletePageCell");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}/cells"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "row", row));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "column", column));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    CompleteResponse<V1Page> completeResponse = (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeletePageCell
   * Deletes a cell from a Favorites page in Square Register. *Note**: DeletePageCell returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeletePageCellRequest&#x60; object as documented below.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page to delete. (required)
   * @param row The row of the cell to clear. Always an integer between 0 and 4, inclusive. Row 0 is the top row. (optional)
   * @param column The column of the cell to clear. Always an integer between 0 and 4, inclusive. Column 0 is the leftmost column. (optional)
   * @return CompleteResponse<V1Page>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Page>deletePageCellWithHttpInfo(String locationId, String pageId, String row, String column) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deletePageCell");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling deletePageCell");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}/cells"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "row", row));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "column", column));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    return (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * DeleteVariation
   * Deletes an existing item variation from an item. *Note**: DeleteVariation returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteVariationRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to delete. (required)
   * @param variationId The ID of the variation to delete. (required)
   * @return V1Variation
   * @throws ApiException if fails to make API call
   */  public V1Variation deleteVariation(String locationId, String itemId, String variationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling deleteVariation");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling deleteVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    CompleteResponse<V1Variation> completeResponse = (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * DeleteVariation
   * Deletes an existing item variation from an item. *Note**: DeleteVariation returns nothing on success but Connect SDKs map the empty response to an empty &#x60;V1DeleteVariationRequest&#x60; object as documented below.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to delete. (required)
   * @param variationId The ID of the variation to delete. (required)
   * @return CompleteResponse<V1Variation>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Variation>deleteVariationWithHttpInfo(String locationId, String itemId, String variationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling deleteVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling deleteVariation");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling deleteVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    return (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListCategories
   * Lists all of a location&#39;s item categories.
   * @param locationId The ID of the location to list categories for. (required)
   * @return List&lt;V1Category&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Category> listCategories(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listCategories");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories"
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

    GenericType<List<V1Category>> localVarReturnType = new GenericType<List<V1Category>>() {};
    CompleteResponse<List<V1Category>> completeResponse = (CompleteResponse<List<V1Category>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListCategories
   * Lists all of a location&#39;s item categories.
   * @param locationId The ID of the location to list categories for. (required)
   * @return CompleteResponse<List<V1Category>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Category>>listCategoriesWithHttpInfo(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listCategories");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories"
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

    GenericType<List<V1Category>> localVarReturnType = new GenericType<List<V1Category>>() {};
    return (CompleteResponse<List<V1Category>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListDiscounts
   * Lists all of a location&#39;s discounts.
   * @param locationId The ID of the location to list categories for. (required)
   * @return List&lt;V1Discount&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Discount> listDiscounts(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listDiscounts");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts"
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

    GenericType<List<V1Discount>> localVarReturnType = new GenericType<List<V1Discount>>() {};
    CompleteResponse<List<V1Discount>> completeResponse = (CompleteResponse<List<V1Discount>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListDiscounts
   * Lists all of a location&#39;s discounts.
   * @param locationId The ID of the location to list categories for. (required)
   * @return CompleteResponse<List<V1Discount>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Discount>>listDiscountsWithHttpInfo(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listDiscounts");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts"
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

    GenericType<List<V1Discount>> localVarReturnType = new GenericType<List<V1Discount>>() {};
    return (CompleteResponse<List<V1Discount>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListFees
   * Lists all of a location&#39;s fees (taxes).
   * @param locationId The ID of the location to list fees for. (required)
   * @return List&lt;V1Fee&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Fee> listFees(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listFees");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees"
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

    GenericType<List<V1Fee>> localVarReturnType = new GenericType<List<V1Fee>>() {};
    CompleteResponse<List<V1Fee>> completeResponse = (CompleteResponse<List<V1Fee>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListFees
   * Lists all of a location&#39;s fees (taxes).
   * @param locationId The ID of the location to list fees for. (required)
   * @return CompleteResponse<List<V1Fee>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Fee>>listFeesWithHttpInfo(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listFees");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees"
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

    GenericType<List<V1Fee>> localVarReturnType = new GenericType<List<V1Fee>>() {};
    return (CompleteResponse<List<V1Fee>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListInventory
   * Provides inventory information for all of a merchant&#39;s inventory-enabled item variations.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param limit The maximum number of inventory entries to return in a single response. This value cannot exceed 1000. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return List&lt;V1InventoryEntry&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1InventoryEntry> listInventory(String locationId, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listInventory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/inventory"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1InventoryEntry>> localVarReturnType = new GenericType<List<V1InventoryEntry>>() {};
    CompleteResponse<List<V1InventoryEntry>> completeResponse = (CompleteResponse<List<V1InventoryEntry>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListInventory
   * Provides inventory information for all of a merchant&#39;s inventory-enabled item variations.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param limit The maximum number of inventory entries to return in a single response. This value cannot exceed 1000. (optional)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return CompleteResponse<List<V1InventoryEntry>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1InventoryEntry>>listInventoryWithHttpInfo(String locationId, Integer limit, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listInventory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/inventory"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1InventoryEntry>> localVarReturnType = new GenericType<List<V1InventoryEntry>>() {};
    return (CompleteResponse<List<V1InventoryEntry>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListItems
   * Provides summary information for all of a location&#39;s items.
   * @param locationId The ID of the location to list items for. (required)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return List&lt;V1Item&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Item> listItems(String locationId, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listItems");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Item>> localVarReturnType = new GenericType<List<V1Item>>() {};
    CompleteResponse<List<V1Item>> completeResponse = (CompleteResponse<List<V1Item>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListItems
   * Provides summary information for all of a location&#39;s items.
   * @param locationId The ID of the location to list items for. (required)
   * @param batchToken A pagination cursor to retrieve the next set of results for your original query to the endpoint. (optional)
   * @return CompleteResponse<List<V1Item>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Item>>listItemsWithHttpInfo(String locationId, String batchToken) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listItems");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "batch_token", batchToken));



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2" };

    GenericType<List<V1Item>> localVarReturnType = new GenericType<List<V1Item>>() {};
    return (CompleteResponse<List<V1Item>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListModifierLists
   * Lists all of a location&#39;s modifier lists.
   * @param locationId The ID of the location to list modifier lists for. (required)
   * @return List&lt;V1ModifierList&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1ModifierList> listModifierLists(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listModifierLists");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists"
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

    GenericType<List<V1ModifierList>> localVarReturnType = new GenericType<List<V1ModifierList>>() {};
    CompleteResponse<List<V1ModifierList>> completeResponse = (CompleteResponse<List<V1ModifierList>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListModifierLists
   * Lists all of a location&#39;s modifier lists.
   * @param locationId The ID of the location to list modifier lists for. (required)
   * @return CompleteResponse<List<V1ModifierList>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1ModifierList>>listModifierListsWithHttpInfo(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listModifierLists");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists"
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

    GenericType<List<V1ModifierList>> localVarReturnType = new GenericType<List<V1ModifierList>>() {};
    return (CompleteResponse<List<V1ModifierList>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * ListPages
   * Lists all of a location&#39;s Favorites pages in Square Register.
   * @param locationId The ID of the location to list Favorites pages for. (required)
   * @return List&lt;V1Page&gt;
   * @throws ApiException if fails to make API call
   */  public List<V1Page> listPages(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listPages");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages"
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

    GenericType<List<V1Page>> localVarReturnType = new GenericType<List<V1Page>>() {};
    CompleteResponse<List<V1Page>> completeResponse = (CompleteResponse<List<V1Page>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ListPages
   * Lists all of a location&#39;s Favorites pages in Square Register.
   * @param locationId The ID of the location to list Favorites pages for. (required)
   * @return CompleteResponse<List<V1Page>>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<List<V1Page>>listPagesWithHttpInfo(String locationId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling listPages");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages"
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

    GenericType<List<V1Page>> localVarReturnType = new GenericType<List<V1Page>>() {};
    return (CompleteResponse<List<V1Page>>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RemoveFee
   * Removes a fee assocation from an item, meaning the fee is no longer automatically applied to the item in Square Register.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param itemId The ID of the item to add the fee to. (required)
   * @param feeId The ID of the fee to apply. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item removeFee(String locationId, String itemId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling removeFee");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling removeFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling removeFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RemoveFee
   * Removes a fee assocation from an item, meaning the fee is no longer automatically applied to the item in Square Register.
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param itemId The ID of the item to add the fee to. (required)
   * @param feeId The ID of the fee to apply. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>removeFeeWithHttpInfo(String locationId, String itemId, String feeId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling removeFee");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling removeFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling removeFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RemoveModifierList
   * Removes a modifier list association from an item, meaning modifier options from the list can no longer be applied to the item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to remove. (required)
   * @param itemId The ID of the item to remove the modifier list from. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item removeModifierList(String locationId, String modifierListId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling removeModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling removeModifierList");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling removeModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RemoveModifierList
   * Removes a modifier list association from an item, meaning modifier options from the list can no longer be applied to the item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to remove. (required)
   * @param itemId The ID of the item to remove the modifier list from. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>removeModifierListWithHttpInfo(String locationId, String modifierListId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling removeModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling removeModifierList");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling removeModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveItem
   * Provides the details for a single item, including associated modifier lists and fees.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The item&#39;s ID. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item retrieveItem(String locationId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling retrieveItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveItem
   * Provides the details for a single item, including associated modifier lists and fees.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The item&#39;s ID. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>retrieveItemWithHttpInfo(String locationId, String itemId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling retrieveItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RetrieveModifierList
   * Provides the details for a single modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The modifier list&#39;s ID. (required)
   * @return V1ModifierList
   * @throws ApiException if fails to make API call
   */  public V1ModifierList retrieveModifierList(String locationId, String modifierListId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling retrieveModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    CompleteResponse<V1ModifierList> completeResponse = (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RetrieveModifierList
   * Provides the details for a single modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The modifier list&#39;s ID. (required)
   * @return CompleteResponse<V1ModifierList>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierList>retrieveModifierListWithHttpInfo(String locationId, String modifierListId) throws ApiException {
    Object localVarPostBody = null;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling retrieveModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling retrieveModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    return (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateCategory
   * Modifies the details of an existing item category.
   * @param locationId The ID of the category&#39;s associated location. (required)
   * @param categoryId The ID of the category to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Category
   * @throws ApiException if fails to make API call
   */  public V1Category updateCategory(String locationId, String categoryId, V1Category body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateCategory");
    }

    // verify the required parameter 'categoryId' is set
    if (categoryId == null) {
      throw new ApiException(400, "Missing the required parameter 'categoryId' when calling updateCategory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories/{category_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "category_id" + "\\}", apiClient.escapeString(categoryId.toString()));

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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    CompleteResponse<V1Category> completeResponse = (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateCategory
   * Modifies the details of an existing item category.
   * @param locationId The ID of the category&#39;s associated location. (required)
   * @param categoryId The ID of the category to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Category>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Category>updateCategoryWithHttpInfo(String locationId, String categoryId, V1Category body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateCategory");
    }

    // verify the required parameter 'categoryId' is set
    if (categoryId == null) {
      throw new ApiException(400, "Missing the required parameter 'categoryId' when calling updateCategory");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateCategory");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/categories/{category_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "category_id" + "\\}", apiClient.escapeString(categoryId.toString()));

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

    GenericType<V1Category> localVarReturnType = new GenericType<V1Category>() {};
    return (CompleteResponse<V1Category>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateDiscount
   * Modifies the details of an existing discount.
   * @param locationId The ID of the category&#39;s associated location. (required)
   * @param discountId The ID of the discount to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Discount
   * @throws ApiException if fails to make API call
   */  public V1Discount updateDiscount(String locationId, String discountId, V1Discount body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateDiscount");
    }

    // verify the required parameter 'discountId' is set
    if (discountId == null) {
      throw new ApiException(400, "Missing the required parameter 'discountId' when calling updateDiscount");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts/{discount_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "discount_id" + "\\}", apiClient.escapeString(discountId.toString()));

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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    CompleteResponse<V1Discount> completeResponse = (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateDiscount
   * Modifies the details of an existing discount.
   * @param locationId The ID of the category&#39;s associated location. (required)
   * @param discountId The ID of the discount to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Discount>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Discount>updateDiscountWithHttpInfo(String locationId, String discountId, V1Discount body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateDiscount");
    }

    // verify the required parameter 'discountId' is set
    if (discountId == null) {
      throw new ApiException(400, "Missing the required parameter 'discountId' when calling updateDiscount");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateDiscount");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/discounts/{discount_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "discount_id" + "\\}", apiClient.escapeString(discountId.toString()));

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

    GenericType<V1Discount> localVarReturnType = new GenericType<V1Discount>() {};
    return (CompleteResponse<V1Discount>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateFee
   * Modifies the details of an existing fee (tax).
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param feeId The ID of the fee to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Fee
   * @throws ApiException if fails to make API call
   */  public V1Fee updateFee(String locationId, String feeId, V1Fee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling updateFee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    CompleteResponse<V1Fee> completeResponse = (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateFee
   * Modifies the details of an existing fee (tax).
   * @param locationId The ID of the fee&#39;s associated location. (required)
   * @param feeId The ID of the fee to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Fee>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Fee>updateFeeWithHttpInfo(String locationId, String feeId, V1Fee body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateFee");
    }

    // verify the required parameter 'feeId' is set
    if (feeId == null) {
      throw new ApiException(400, "Missing the required parameter 'feeId' when calling updateFee");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateFee");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/fees/{fee_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "fee_id" + "\\}", apiClient.escapeString(feeId.toString()));

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

    GenericType<V1Fee> localVarReturnType = new GenericType<V1Fee>() {};
    return (CompleteResponse<V1Fee>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateItem
   * Modifies the core details of an existing item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Item
   * @throws ApiException if fails to make API call
   */  public V1Item updateItem(String locationId, String itemId, V1Item body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling updateItem");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    CompleteResponse<V1Item> completeResponse = (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateItem
   * Modifies the core details of an existing item.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Item>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Item>updateItemWithHttpInfo(String locationId, String itemId, V1Item body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateItem");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling updateItem");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateItem");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()));

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

    GenericType<V1Item> localVarReturnType = new GenericType<V1Item>() {};
    return (CompleteResponse<V1Item>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateModifierList
   * Modifies the details of an existing item modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1ModifierList
   * @throws ApiException if fails to make API call
   */  public V1ModifierList updateModifierList(String locationId, String modifierListId, V1UpdateModifierListRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling updateModifierList");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    CompleteResponse<V1ModifierList> completeResponse = (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateModifierList
   * Modifies the details of an existing item modifier list.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1ModifierList>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierList>updateModifierListWithHttpInfo(String locationId, String modifierListId, V1UpdateModifierListRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateModifierList");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling updateModifierList");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateModifierList");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()));

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

    GenericType<V1ModifierList> localVarReturnType = new GenericType<V1ModifierList>() {};
    return (CompleteResponse<V1ModifierList>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateModifierOption
   * Modifies the details of an existing item modifier option.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param modifierOptionId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1ModifierOption
   * @throws ApiException if fails to make API call
   */  public V1ModifierOption updateModifierOption(String locationId, String modifierListId, String modifierOptionId, V1ModifierOption body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling updateModifierOption");
    }

    // verify the required parameter 'modifierOptionId' is set
    if (modifierOptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierOptionId' when calling updateModifierOption");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "modifier_option_id" + "\\}", apiClient.escapeString(modifierOptionId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    CompleteResponse<V1ModifierOption> completeResponse = (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateModifierOption
   * Modifies the details of an existing item modifier option.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param modifierListId The ID of the modifier list to edit. (required)
   * @param modifierOptionId The ID of the modifier list to edit. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1ModifierOption>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1ModifierOption>updateModifierOptionWithHttpInfo(String locationId, String modifierListId, String modifierOptionId, V1ModifierOption body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateModifierOption");
    }

    // verify the required parameter 'modifierListId' is set
    if (modifierListId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierListId' when calling updateModifierOption");
    }

    // verify the required parameter 'modifierOptionId' is set
    if (modifierOptionId == null) {
      throw new ApiException(400, "Missing the required parameter 'modifierOptionId' when calling updateModifierOption");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateModifierOption");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "modifier_list_id" + "\\}", apiClient.escapeString(modifierListId.toString()))
      .replaceAll("\\{" + "modifier_option_id" + "\\}", apiClient.escapeString(modifierOptionId.toString()));

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

    GenericType<V1ModifierOption> localVarReturnType = new GenericType<V1ModifierOption>() {};
    return (CompleteResponse<V1ModifierOption>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdatePage
   * Modifies the details of a Favorites page in Square Register.
   * @param locationId The ID of the Favorites page&#39;s associated location (required)
   * @param pageId The ID of the page to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Page
   * @throws ApiException if fails to make API call
   */  public V1Page updatePage(String locationId, String pageId, V1Page body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updatePage");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling updatePage");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updatePage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    CompleteResponse<V1Page> completeResponse = (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdatePage
   * Modifies the details of a Favorites page in Square Register.
   * @param locationId The ID of the Favorites page&#39;s associated location (required)
   * @param pageId The ID of the page to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Page>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Page>updatePageWithHttpInfo(String locationId, String pageId, V1Page body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updatePage");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling updatePage");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updatePage");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    return (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdatePageCell
   * Modifies a cell of a Favorites page in Square Register.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page the cell belongs to. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Page
   * @throws ApiException if fails to make API call
   */  public V1Page updatePageCell(String locationId, String pageId, V1PageCell body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updatePageCell");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling updatePageCell");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updatePageCell");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}/cells"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    CompleteResponse<V1Page> completeResponse = (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdatePageCell
   * Modifies a cell of a Favorites page in Square Register.
   * @param locationId The ID of the Favorites page&#39;s associated location. (required)
   * @param pageId The ID of the page the cell belongs to. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Page>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Page>updatePageCellWithHttpInfo(String locationId, String pageId, V1PageCell body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updatePageCell");
    }

    // verify the required parameter 'pageId' is set
    if (pageId == null) {
      throw new ApiException(400, "Missing the required parameter 'pageId' when calling updatePageCell");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updatePageCell");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/pages/{page_id}/cells"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "page_id" + "\\}", apiClient.escapeString(pageId.toString()));

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

    GenericType<V1Page> localVarReturnType = new GenericType<V1Page>() {};
    return (CompleteResponse<V1Page>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateVariation
   * Modifies the details of an existing item variation.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @param variationId The ID of the variation to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return V1Variation
   * @throws ApiException if fails to make API call
   */  public V1Variation updateVariation(String locationId, String itemId, String variationId, V1Variation body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling updateVariation");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling updateVariation");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    CompleteResponse<V1Variation> completeResponse = (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateVariation
   * Modifies the details of an existing item variation.
   * @param locationId The ID of the item&#39;s associated location. (required)
   * @param itemId The ID of the item to modify. (required)
   * @param variationId The ID of the variation to modify. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<V1Variation>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<V1Variation>updateVariationWithHttpInfo(String locationId, String itemId, String variationId, V1Variation body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling updateVariation");
    }

    // verify the required parameter 'itemId' is set
    if (itemId == null) {
      throw new ApiException(400, "Missing the required parameter 'itemId' when calling updateVariation");
    }

    // verify the required parameter 'variationId' is set
    if (variationId == null) {
      throw new ApiException(400, "Missing the required parameter 'variationId' when calling updateVariation");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateVariation");
    }

    // create path and map variables
    String localVarPath = "/v1/{location_id}/items/{item_id}/variations/{variation_id}"
      .replaceAll("\\{" + "location_id" + "\\}", apiClient.escapeString(locationId.toString()))
      .replaceAll("\\{" + "item_id" + "\\}", apiClient.escapeString(itemId.toString()))
      .replaceAll("\\{" + "variation_id" + "\\}", apiClient.escapeString(variationId.toString()));

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

    GenericType<V1Variation> localVarReturnType = new GenericType<V1Variation>() {};
    return (CompleteResponse<V1Variation>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
