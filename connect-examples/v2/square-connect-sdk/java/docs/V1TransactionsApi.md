# V1TransactionsApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createRefund**](V1TransactionsApi.md#createRefund) | **POST** /v1/{location_id}/refunds | CreateRefund
[**listBankAccounts**](V1TransactionsApi.md#listBankAccounts) | **GET** /v1/{location_id}/bank-accounts | ListBankAccounts
[**listOrders**](V1TransactionsApi.md#listOrders) | **GET** /v1/{location_id}/orders | ListOrders
[**listPayments**](V1TransactionsApi.md#listPayments) | **GET** /v1/{location_id}/payments | ListPayments
[**listRefunds**](V1TransactionsApi.md#listRefunds) | **GET** /v1/{location_id}/refunds | ListRefunds
[**listSettlements**](V1TransactionsApi.md#listSettlements) | **GET** /v1/{location_id}/settlements | ListSettlements
[**retrieveBankAccount**](V1TransactionsApi.md#retrieveBankAccount) | **GET** /v1/{location_id}/bank-accounts/{bank_account_id} | RetrieveBankAccount
[**retrieveOrder**](V1TransactionsApi.md#retrieveOrder) | **GET** /v1/{location_id}/orders/{order_id} | RetrieveOrder
[**retrievePayment**](V1TransactionsApi.md#retrievePayment) | **GET** /v1/{location_id}/payments/{payment_id} | RetrievePayment
[**retrieveSettlement**](V1TransactionsApi.md#retrieveSettlement) | **GET** /v1/{location_id}/settlements/{settlement_id} | RetrieveSettlement
[**updateOrder**](V1TransactionsApi.md#updateOrder) | **PUT** /v1/{location_id}/orders/{order_id} | UpdateOrder


<a name="createRefund"></a>
# **createRefund**
> V1Refund createRefund(locationId, body)

CreateRefund

Issues a refund for a previously processed payment. You must issue a refund within 60 days of the associated payment.  You cannot issue a partial refund for a split tender payment. You must instead issue a full or partial refund for a particular tender, by providing the applicable tender id to the V1CreateRefund endpoint. Issuing a full refund for a split tender payment refunds all tenders associated with the payment.  Issuing a refund for a card payment is not reversible. For development purposes, you can create fake cash payments in Square Point of Sale and refund them.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the original payment's associated location.
V1CreateRefundRequest body = new V1CreateRefundRequest(); // V1CreateRefundRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1Refund result = apiInstance.createRefund(locationId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#createRefund");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the original payment&#39;s associated location. |
 **body** | [**V1CreateRefundRequest**](V1CreateRefundRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1Refund**](V1Refund.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listBankAccounts"></a>
# **listBankAccounts**
> List&lt;V1BankAccount&gt; listBankAccounts(locationId)

ListBankAccounts

Provides non-confidential details for all of a location&#39;s associated bank accounts. This endpoint does not provide full bank account numbers, and there is no way to obtain a full bank account number with the Connect API.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list bank accounts for.
try {
    List<V1BankAccount> result = apiInstance.listBankAccounts(locationId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#listBankAccounts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list bank accounts for. |

### Return type

[**List&lt;V1BankAccount&gt;**](V1BankAccount.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listOrders"></a>
# **listOrders**
> List&lt;V1Order&gt; listOrders(locationId, order, limit, batchToken)

ListOrders

Provides summary information for a merchant&#39;s online store orders.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list online store orders for.
String order = "order_example"; // String | TThe order in which payments are listed in the response.
Integer limit = 56; // Integer | The maximum number of payments to return in a single response. This value cannot exceed 200.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1Order> result = apiInstance.listOrders(locationId, order, limit, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#listOrders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list online store orders for. |
 **order** | **String**| TThe order in which payments are listed in the response. | [optional]
 **limit** | **Integer**| The maximum number of payments to return in a single response. This value cannot exceed 200. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1Order&gt;**](V1Order.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listPayments"></a>
# **listPayments**
> List&lt;V1Payment&gt; listPayments(locationId, order, beginTime, endTime, limit, batchToken, includePartial)

ListPayments

Provides summary information for all payments taken for a given Square account during a date range. Date ranges cannot exceed 1 year in length. See Date ranges for details of inclusive and exclusive dates.  *Note**: Details for payments processed with Square Point of Sale while in offline mode may not be transmitted to Square for up to 72 hours. Offline payments have a &#x60;created_at&#x60; value that reflects the time the payment was originally processed, not the time it was subsequently transmitted to Square. Consequently, the ListPayments endpoint might list an offline payment chronologically between online payments that were seen in a previous request.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list payments for. If you specify me, this endpoint returns payments aggregated from all of the business's locations.
String order = "order_example"; // String | The order in which payments are listed in the response.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time.
Integer limit = 56; // Integer | The maximum number of payments to return in a single response. This value cannot exceed 200.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
Boolean includePartial = true; // Boolean | Indicates whether or not to include partial payments in the response. Partial payments will have the tenders collected so far, but the itemizations will be empty until the payment is completed.
try {
    List<V1Payment> result = apiInstance.listPayments(locationId, order, beginTime, endTime, limit, batchToken, includePartial);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#listPayments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list payments for. If you specify me, this endpoint returns payments aggregated from all of the business&#39;s locations. |
 **order** | **String**| The order in which payments are listed in the response. | [optional]
 **beginTime** | **String**| The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time. | [optional]
 **limit** | **Integer**| The maximum number of payments to return in a single response. This value cannot exceed 200. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]
 **includePartial** | **Boolean**| Indicates whether or not to include partial payments in the response. Partial payments will have the tenders collected so far, but the itemizations will be empty until the payment is completed. | [optional]

### Return type

[**List&lt;V1Payment&gt;**](V1Payment.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listRefunds"></a>
# **listRefunds**
> List&lt;V1Refund&gt; listRefunds(locationId, order, beginTime, endTime, limit, batchToken)

ListRefunds

Provides the details for all refunds initiated by a merchant or any of the merchant&#39;s mobile staff during a date range. Date ranges cannot exceed one year in length.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list refunds for.
String order = "order_example"; // String | TThe order in which payments are listed in the response.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time.
Integer limit = 56; // Integer | The approximate number of refunds to return in a single response. Default: 100. Max: 200. Response may contain more results than the prescribed limit when refunds are made simultaneously to multiple tenders in a payment or when refunds are generated in an exchange to account for the value of returned goods.
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1Refund> result = apiInstance.listRefunds(locationId, order, beginTime, endTime, limit, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#listRefunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list refunds for. |
 **order** | **String**| TThe order in which payments are listed in the response. | [optional]
 **beginTime** | **String**| The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time. | [optional]
 **limit** | **Integer**| The approximate number of refunds to return in a single response. Default: 100. Max: 200. Response may contain more results than the prescribed limit when refunds are made simultaneously to multiple tenders in a payment or when refunds are generated in an exchange to account for the value of returned goods. | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1Refund&gt;**](V1Refund.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listSettlements"></a>
# **listSettlements**
> List&lt;V1Settlement&gt; listSettlements(locationId, order, beginTime, endTime, limit, status, batchToken)

ListSettlements

Provides summary information for all deposits and withdrawals initiated by Square to a linked bank account during a date range. Date ranges cannot exceed one year in length.  *Note**: the ListSettlements endpoint does not provide entry information.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list settlements for. If you specify me, this endpoint returns payments aggregated from all of the business's locations.
String order = "order_example"; // String | TThe order in which payments are listed in the response.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time.
Integer limit = 56; // Integer | The maximum number of payments to return in a single response. This value cannot exceed 200.
String status = "status_example"; // String | Provide this parameter to retrieve only settlements with a particular status (SENT or FAILED).
String batchToken = "batchToken_example"; // String | A pagination cursor to retrieve the next set of results for your original query to the endpoint.
try {
    List<V1Settlement> result = apiInstance.listSettlements(locationId, order, beginTime, endTime, limit, status, batchToken);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#listSettlements");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list settlements for. If you specify me, this endpoint returns payments aggregated from all of the business&#39;s locations. |
 **order** | **String**| TThe order in which payments are listed in the response. | [optional]
 **beginTime** | **String**| The beginning of the requested reporting period, in ISO 8601 format. If this value is before January 1, 2013 (2013-01-01T00:00:00Z), this endpoint returns an error. Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in ISO 8601 format. If this value is more than one year greater than begin_time, this endpoint returns an error. Default value: The current time. | [optional]
 **limit** | **Integer**| The maximum number of payments to return in a single response. This value cannot exceed 200. | [optional]
 **status** | **String**| Provide this parameter to retrieve only settlements with a particular status (SENT or FAILED). | [optional]
 **batchToken** | **String**| A pagination cursor to retrieve the next set of results for your original query to the endpoint. | [optional]

### Return type

[**List&lt;V1Settlement&gt;**](V1Settlement.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveBankAccount"></a>
# **retrieveBankAccount**
> V1BankAccount retrieveBankAccount(locationId, bankAccountId)

RetrieveBankAccount

Provides non-confidential details for a merchant&#39;s associated bank account. This endpoint does not provide full bank account numbers, and there is no way to obtain a full bank account number with the Connect API.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the bank account's associated location.
String bankAccountId = "bankAccountId_example"; // String | The bank account's Square-issued ID. You obtain this value from Settlement objects returned.
try {
    V1BankAccount result = apiInstance.retrieveBankAccount(locationId, bankAccountId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#retrieveBankAccount");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the bank account&#39;s associated location. |
 **bankAccountId** | **String**| The bank account&#39;s Square-issued ID. You obtain this value from Settlement objects returned. |

### Return type

[**V1BankAccount**](V1BankAccount.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveOrder"></a>
# **retrieveOrder**
> V1Order retrieveOrder(locationId, orderId)

RetrieveOrder

Provides comprehensive information for a single online store order, including the order&#39;s history.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the order's associated location.
String orderId = "orderId_example"; // String | The order's Square-issued ID. You obtain this value from Order objects returned by the List Orders endpoint
try {
    V1Order result = apiInstance.retrieveOrder(locationId, orderId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#retrieveOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the order&#39;s associated location. |
 **orderId** | **String**| The order&#39;s Square-issued ID. You obtain this value from Order objects returned by the List Orders endpoint |

### Return type

[**V1Order**](V1Order.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrievePayment"></a>
# **retrievePayment**
> V1Payment retrievePayment(locationId, paymentId)

RetrievePayment

Provides comprehensive information for a single payment.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the payment's associated location.
String paymentId = "paymentId_example"; // String | The Square-issued payment ID. payment_id comes from Payment objects returned by the List Payments endpoint, Settlement objects returned by the List Settlements endpoint, or Refund objects returned by the List Refunds endpoint.
try {
    V1Payment result = apiInstance.retrievePayment(locationId, paymentId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#retrievePayment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the payment&#39;s associated location. |
 **paymentId** | **String**| The Square-issued payment ID. payment_id comes from Payment objects returned by the List Payments endpoint, Settlement objects returned by the List Settlements endpoint, or Refund objects returned by the List Refunds endpoint. |

### Return type

[**V1Payment**](V1Payment.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveSettlement"></a>
# **retrieveSettlement**
> V1Settlement retrieveSettlement(locationId, settlementId)

RetrieveSettlement

Provides comprehensive information for a single settlement.  The returned &#x60;Settlement&#x60; objects include an &#x60;entries&#x60; field that lists the transactions that contribute to the settlement total. Most settlement entries correspond to a payment payout, but settlement entries are also generated for less common events, like refunds, manual adjustments, or chargeback holds.  Square initiates its regular deposits as indicated in the [Deposit Options with Square](https://squareup.com/help/us/en/article/3807) help article. Details for a regular deposit are usually not available from Connect API endpoints before 10 p.m. PST the same day.  Square does not know when an initiated settlement **completes**, only whether it has failed. A completed settlement is typically reflected in a bank account within 3 business days, but in exceptional cases it may take longer.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the settlements's associated location.
String settlementId = "settlementId_example"; // String | The settlement's Square-issued ID. You obtain this value from Settlement objects returned by the List Settlements endpoint.
try {
    V1Settlement result = apiInstance.retrieveSettlement(locationId, settlementId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#retrieveSettlement");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the settlements&#39;s associated location. |
 **settlementId** | **String**| The settlement&#39;s Square-issued ID. You obtain this value from Settlement objects returned by the List Settlements endpoint. |

### Return type

[**V1Settlement**](V1Settlement.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateOrder"></a>
# **updateOrder**
> V1Order updateOrder(locationId, orderId, body)

UpdateOrder

Updates the details of an online store order. Every update you perform on an order corresponds to one of three actions:

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.V1TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1TransactionsApi apiInstance = new V1TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the order's associated location.
String orderId = "orderId_example"; // String | The order's Square-issued ID. You obtain this value from Order objects returned by the List Orders endpoint
V1UpdateOrderRequest body = new V1UpdateOrderRequest(); // V1UpdateOrderRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    V1Order result = apiInstance.updateOrder(locationId, orderId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling V1TransactionsApi#updateOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the order&#39;s associated location. |
 **orderId** | **String**| The order&#39;s Square-issued ID. You obtain this value from Order objects returned by the List Orders endpoint |
 **body** | [**V1UpdateOrderRequest**](V1UpdateOrderRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**V1Order**](V1Order.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

