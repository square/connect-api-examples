package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.BatchRetrieveOrdersRequest;
import com.squareup.connect.models.BatchRetrieveOrdersResponse;
import com.squareup.connect.models.CreateOrderRequest;
import com.squareup.connect.models.CreateOrderResponse;
import com.squareup.connect.models.PayOrderRequest;
import com.squareup.connect.models.PayOrderResponse;
import com.squareup.connect.models.SearchOrdersRequest;
import com.squareup.connect.models.SearchOrdersResponse;
import com.squareup.connect.models.UpdateOrderRequest;
import com.squareup.connect.models.UpdateOrderResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrdersApi {
  private ApiClient apiClient;

  public OrdersApi() {
    this(Configuration.getDefaultApiClient());
  }

  public OrdersApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * BatchRetrieveOrders
   * Retrieves a set of [Order](#type-order)s by their IDs.  If a given Order ID does not exist, the ID is ignored instead of generating an error.
   * @param locationId The ID of the orders&#39; associated location. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return BatchRetrieveOrdersResponse
   * @throws ApiException if fails to make API call
   */  public BatchRetrieveOrdersResponse batchRetrieveOrders(String locationId, BatchRetrieveOrdersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling batchRetrieveOrders");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveOrders");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders/batch-retrieve"
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

    GenericType<BatchRetrieveOrdersResponse> localVarReturnType = new GenericType<BatchRetrieveOrdersResponse>() {};
    CompleteResponse<BatchRetrieveOrdersResponse> completeResponse = (CompleteResponse<BatchRetrieveOrdersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * BatchRetrieveOrders
   * Retrieves a set of [Order](#type-order)s by their IDs.  If a given Order ID does not exist, the ID is ignored instead of generating an error.
   * @param locationId The ID of the orders&#39; associated location. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<BatchRetrieveOrdersResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<BatchRetrieveOrdersResponse>batchRetrieveOrdersWithHttpInfo(String locationId, BatchRetrieveOrdersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling batchRetrieveOrders");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling batchRetrieveOrders");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders/batch-retrieve"
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

    GenericType<BatchRetrieveOrdersResponse> localVarReturnType = new GenericType<BatchRetrieveOrdersResponse>() {};
    return (CompleteResponse<BatchRetrieveOrdersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * CreateOrder
   * Creates a new [Order](#type-order) which can include information on products for purchase and settings to apply to the purchase.  To pay for a created order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  You can modify open orders using the [UpdateOrder](#endpoint-orders-updateorder) endpoint.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).
   * @param locationId The ID of the business location to associate the order with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CreateOrderResponse
   * @throws ApiException if fails to make API call
   */  public CreateOrderResponse createOrder(String locationId, CreateOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createOrder");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders"
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

    GenericType<CreateOrderResponse> localVarReturnType = new GenericType<CreateOrderResponse>() {};
    CompleteResponse<CreateOrderResponse> completeResponse = (CompleteResponse<CreateOrderResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * CreateOrder
   * Creates a new [Order](#type-order) which can include information on products for purchase and settings to apply to the purchase.  To pay for a created order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  You can modify open orders using the [UpdateOrder](#endpoint-orders-updateorder) endpoint.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).
   * @param locationId The ID of the business location to associate the order with. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<CreateOrderResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<CreateOrderResponse>createOrderWithHttpInfo(String locationId, CreateOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'locationId' is set
    if (locationId == null) {
      throw new ApiException(400, "Missing the required parameter 'locationId' when calling createOrder");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling createOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders"
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

    GenericType<CreateOrderResponse> localVarReturnType = new GenericType<CreateOrderResponse>() {};
    return (CompleteResponse<CreateOrderResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * PayOrder
   * Pay for an [order](#type-order) using one or more approved [payments](#type-payment), or settle an order with a total of &#x60;0&#x60;.  The total of the &#x60;payment_ids&#x60; listed in the request must be equal to the order total. Orders with a total amount of &#x60;0&#x60; can be marked as paid by specifying an empty array of &#x60;payment_ids&#x60; in the request.  To be used with PayOrder, a payment must:  - Reference the order by specifying the &#x60;order_id&#x60; when [creating the payment](#endpoint-payments-createpayment). Any approved payments that reference the same &#x60;order_id&#x60; not specified in the &#x60;payment_ids&#x60; will be canceled. - Be approved with [delayed capture](/payments-api/take-payments#delayed-capture). Using a delayed capture payment with PayOrder will complete the approved payment.  Learn how to [pay for orders with a single payment using the Payments API](/orders-api/pay-for-orders).
   * @param orderId The ID of the order being paid. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return PayOrderResponse
   * @throws ApiException if fails to make API call
   */  public PayOrderResponse payOrder(String orderId, PayOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling payOrder");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling payOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/orders/{order_id}/pay"
      .replaceAll("\\{" + "order_id" + "\\}", apiClient.escapeString(orderId.toString()));

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

    GenericType<PayOrderResponse> localVarReturnType = new GenericType<PayOrderResponse>() {};
    CompleteResponse<PayOrderResponse> completeResponse = (CompleteResponse<PayOrderResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * PayOrder
   * Pay for an [order](#type-order) using one or more approved [payments](#type-payment), or settle an order with a total of &#x60;0&#x60;.  The total of the &#x60;payment_ids&#x60; listed in the request must be equal to the order total. Orders with a total amount of &#x60;0&#x60; can be marked as paid by specifying an empty array of &#x60;payment_ids&#x60; in the request.  To be used with PayOrder, a payment must:  - Reference the order by specifying the &#x60;order_id&#x60; when [creating the payment](#endpoint-payments-createpayment). Any approved payments that reference the same &#x60;order_id&#x60; not specified in the &#x60;payment_ids&#x60; will be canceled. - Be approved with [delayed capture](/payments-api/take-payments#delayed-capture). Using a delayed capture payment with PayOrder will complete the approved payment.  Learn how to [pay for orders with a single payment using the Payments API](/orders-api/pay-for-orders).
   * @param orderId The ID of the order being paid. (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<PayOrderResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<PayOrderResponse>payOrderWithHttpInfo(String orderId, PayOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling payOrder");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling payOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/orders/{order_id}/pay"
      .replaceAll("\\{" + "order_id" + "\\}", apiClient.escapeString(orderId.toString()));

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

    GenericType<PayOrderResponse> localVarReturnType = new GenericType<PayOrderResponse>() {};
    return (CompleteResponse<PayOrderResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * SearchOrders
   * Search all orders for one or more locations. Orders include all sales, returns, and exchanges regardless of how or when they entered the Square Ecosystem (e.g. Point of Sale, Invoices, Connect APIs, etc).  SearchOrders requests need to specify which locations to search and define a [&#x60;SearchOrdersQuery&#x60;](#type-searchordersquery) object which controls how to sort or filter the results. Your SearchOrdersQuery can:    Set filter criteria.   Set sort order.   Determine whether to return results as complete Order objects, or as [OrderEntry](#type-orderentry) objects.  Note that details for orders processed with Square Point of Sale while in offline mode may not be transmitted to Square for up to 72 hours. Offline orders have a &#x60;created_at&#x60; value that reflects the time the order was created, not the time it was subsequently transmitted to Square.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return SearchOrdersResponse
   * @throws ApiException if fails to make API call
   */  public SearchOrdersResponse searchOrders(SearchOrdersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchOrders");
    }

    // create path and map variables
    String localVarPath = "/v2/orders/search";

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

    GenericType<SearchOrdersResponse> localVarReturnType = new GenericType<SearchOrdersResponse>() {};
    CompleteResponse<SearchOrdersResponse> completeResponse = (CompleteResponse<SearchOrdersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * SearchOrders
   * Search all orders for one or more locations. Orders include all sales, returns, and exchanges regardless of how or when they entered the Square Ecosystem (e.g. Point of Sale, Invoices, Connect APIs, etc).  SearchOrders requests need to specify which locations to search and define a [&#x60;SearchOrdersQuery&#x60;](#type-searchordersquery) object which controls how to sort or filter the results. Your SearchOrdersQuery can:    Set filter criteria.   Set sort order.   Determine whether to return results as complete Order objects, or as [OrderEntry](#type-orderentry) objects.  Note that details for orders processed with Square Point of Sale while in offline mode may not be transmitted to Square for up to 72 hours. Offline orders have a &#x60;created_at&#x60; value that reflects the time the order was created, not the time it was subsequently transmitted to Square.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<SearchOrdersResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<SearchOrdersResponse>searchOrdersWithHttpInfo(SearchOrdersRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling searchOrders");
    }

    // create path and map variables
    String localVarPath = "/v2/orders/search";

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

    GenericType<SearchOrdersResponse> localVarReturnType = new GenericType<SearchOrdersResponse>() {};
    return (CompleteResponse<SearchOrdersResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * UpdateOrder
   * Updates an open [Order](#type-order) by adding, replacing, or deleting fields. Orders with a &#x60;COMPLETED&#x60; or &#x60;CANCELED&#x60; state cannot be updated.  An UpdateOrder request requires the following:  - The &#x60;order_id&#x60; in the endpoint path, identifying the order to update. - The latest &#x60;version&#x60; of the order to update. - The [sparse order](/orders-api/manage-orders#sparse-order-objects) containing only the fields to update and the version the update is being applied to. - If deleting fields, the [dot notation paths](/orders-api/manage-orders#on-dot-notation) identifying fields to clear.  To pay for an order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return UpdateOrderResponse
   * @throws ApiException if fails to make API call
   */  public UpdateOrderResponse updateOrder(UpdateOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders/{order_id}";

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

    GenericType<UpdateOrderResponse> localVarReturnType = new GenericType<UpdateOrderResponse>() {};
    CompleteResponse<UpdateOrderResponse> completeResponse = (CompleteResponse<UpdateOrderResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * UpdateOrder
   * Updates an open [Order](#type-order) by adding, replacing, or deleting fields. Orders with a &#x60;COMPLETED&#x60; or &#x60;CANCELED&#x60; state cannot be updated.  An UpdateOrder request requires the following:  - The &#x60;order_id&#x60; in the endpoint path, identifying the order to update. - The latest &#x60;version&#x60; of the order to update. - The [sparse order](/orders-api/manage-orders#sparse-order-objects) containing only the fields to update and the version the update is being applied to. - If deleting fields, the [dot notation paths](/orders-api/manage-orders#on-dot-notation) identifying fields to clear.  To pay for an order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<UpdateOrderResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<UpdateOrderResponse>updateOrderWithHttpInfo(UpdateOrderRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateOrder");
    }

    // create path and map variables
    String localVarPath = "/v2/locations/{location_id}/orders/{order_id}";

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

    GenericType<UpdateOrderResponse> localVarReturnType = new GenericType<UpdateOrderResponse>() {};
    return (CompleteResponse<UpdateOrderResponse>)apiClient.invokeAPI(localVarPath, "PUT", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
