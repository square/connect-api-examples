# OrdersApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**batchRetrieveOrders**](OrdersApi.md#batchRetrieveOrders) | **POST** /v2/locations/{location_id}/orders/batch-retrieve | BatchRetrieveOrders
[**createOrder**](OrdersApi.md#createOrder) | **POST** /v2/locations/{location_id}/orders | CreateOrder
[**payOrder**](OrdersApi.md#payOrder) | **POST** /v2/orders/{order_id}/pay | PayOrder
[**searchOrders**](OrdersApi.md#searchOrders) | **POST** /v2/orders/search | SearchOrders
[**updateOrder**](OrdersApi.md#updateOrder) | **PUT** /v2/locations/{location_id}/orders/{order_id} | UpdateOrder


<a name="batchRetrieveOrders"></a>
# **batchRetrieveOrders**
> BatchRetrieveOrdersResponse batchRetrieveOrders(locationId, body)

BatchRetrieveOrders

Retrieves a set of [Order](#type-order)s by their IDs.  If a given Order ID does not exist, the ID is ignored instead of generating an error.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.OrdersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

OrdersApi apiInstance = new OrdersApi();
String locationId = "locationId_example"; // String | The ID of the orders' associated location.
BatchRetrieveOrdersRequest body = new BatchRetrieveOrdersRequest(); // BatchRetrieveOrdersRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    BatchRetrieveOrdersResponse result = apiInstance.batchRetrieveOrders(locationId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrdersApi#batchRetrieveOrders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the orders&#39; associated location. |
 **body** | [**BatchRetrieveOrdersRequest**](BatchRetrieveOrdersRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**BatchRetrieveOrdersResponse**](BatchRetrieveOrdersResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createOrder"></a>
# **createOrder**
> CreateOrderResponse createOrder(locationId, body)

CreateOrder

Creates a new [Order](#type-order) which can include information on products for purchase and settings to apply to the purchase.  To pay for a created order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  You can modify open orders using the [UpdateOrder](#endpoint-orders-updateorder) endpoint.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.OrdersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

OrdersApi apiInstance = new OrdersApi();
String locationId = "locationId_example"; // String | The ID of the business location to associate the order with.
CreateOrderRequest body = new CreateOrderRequest(); // CreateOrderRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    CreateOrderResponse result = apiInstance.createOrder(locationId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrdersApi#createOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the business location to associate the order with. |
 **body** | [**CreateOrderRequest**](CreateOrderRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**CreateOrderResponse**](CreateOrderResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="payOrder"></a>
# **payOrder**
> PayOrderResponse payOrder(orderId, body)

PayOrder

Pay for an [order](#type-order) using one or more approved [payments](#type-payment), or settle an order with a total of &#x60;0&#x60;.  The total of the &#x60;payment_ids&#x60; listed in the request must be equal to the order total. Orders with a total amount of &#x60;0&#x60; can be marked as paid by specifying an empty array of &#x60;payment_ids&#x60; in the request.  To be used with PayOrder, a payment must:  - Reference the order by specifying the &#x60;order_id&#x60; when [creating the payment](#endpoint-payments-createpayment). Any approved payments that reference the same &#x60;order_id&#x60; not specified in the &#x60;payment_ids&#x60; will be canceled. - Be approved with [delayed capture](/payments-api/take-payments#delayed-capture). Using a delayed capture payment with PayOrder will complete the approved payment.  Learn how to [pay for orders with a single payment using the Payments API](/orders-api/pay-for-orders).

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.OrdersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

OrdersApi apiInstance = new OrdersApi();
String orderId = "orderId_example"; // String | The ID of the order being paid.
PayOrderRequest body = new PayOrderRequest(); // PayOrderRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    PayOrderResponse result = apiInstance.payOrder(orderId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrdersApi#payOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **orderId** | **String**| The ID of the order being paid. |
 **body** | [**PayOrderRequest**](PayOrderRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**PayOrderResponse**](PayOrderResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="searchOrders"></a>
# **searchOrders**
> SearchOrdersResponse searchOrders(body)

SearchOrders

Search all orders for one or more locations. Orders include all sales, returns, and exchanges regardless of how or when they entered the Square Ecosystem (e.g. Point of Sale, Invoices, Connect APIs, etc).  SearchOrders requests need to specify which locations to search and define a [&#x60;SearchOrdersQuery&#x60;](#type-searchordersquery) object which controls how to sort or filter the results. Your SearchOrdersQuery can:    Set filter criteria.   Set sort order.   Determine whether to return results as complete Order objects, or as [OrderEntry](#type-orderentry) objects.  Note that details for orders processed with Square Point of Sale while in offline mode may not be transmitted to Square for up to 72 hours. Offline orders have a &#x60;created_at&#x60; value that reflects the time the order was created, not the time it was subsequently transmitted to Square.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.OrdersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

OrdersApi apiInstance = new OrdersApi();
SearchOrdersRequest body = new SearchOrdersRequest(); // SearchOrdersRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    SearchOrdersResponse result = apiInstance.searchOrders(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrdersApi#searchOrders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**SearchOrdersRequest**](SearchOrdersRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**SearchOrdersResponse**](SearchOrdersResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOrder"></a>
# **updateOrder**
> UpdateOrderResponse updateOrder(body)

UpdateOrder

Updates an open [Order](#type-order) by adding, replacing, or deleting fields. Orders with a &#x60;COMPLETED&#x60; or &#x60;CANCELED&#x60; state cannot be updated.  An UpdateOrder request requires the following:  - The &#x60;order_id&#x60; in the endpoint path, identifying the order to update. - The latest &#x60;version&#x60; of the order to update. - The [sparse order](/orders-api/manage-orders#sparse-order-objects) containing only the fields to update and the version the update is being applied to. - If deleting fields, the [dot notation paths](/orders-api/manage-orders#on-dot-notation) identifying fields to clear.  To pay for an order, please refer to the [Pay for Orders](/orders-api/pay-for-orders) guide.  To learn more about the Orders API, see the [Orders API Overview](/orders-api/what-it-does).

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.OrdersApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

OrdersApi apiInstance = new OrdersApi();
UpdateOrderRequest body = new UpdateOrderRequest(); // UpdateOrderRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    UpdateOrderResponse result = apiInstance.updateOrder(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrdersApi#updateOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateOrderRequest**](UpdateOrderRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**UpdateOrderResponse**](UpdateOrderResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

