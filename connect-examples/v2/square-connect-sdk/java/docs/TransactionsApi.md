# TransactionsApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**captureTransaction**](TransactionsApi.md#captureTransaction) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/capture | CaptureTransaction
[**charge**](TransactionsApi.md#charge) | **POST** /v2/locations/{location_id}/transactions | Charge
[**createRefund**](TransactionsApi.md#createRefund) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/refund | CreateRefund
[**listRefunds**](TransactionsApi.md#listRefunds) | **GET** /v2/locations/{location_id}/refunds | ListRefunds
[**listTransactions**](TransactionsApi.md#listTransactions) | **GET** /v2/locations/{location_id}/transactions | ListTransactions
[**retrieveTransaction**](TransactionsApi.md#retrieveTransaction) | **GET** /v2/locations/{location_id}/transactions/{transaction_id} | RetrieveTransaction
[**voidTransaction**](TransactionsApi.md#voidTransaction) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/void | VoidTransaction


<a name="captureTransaction"></a>
# **captureTransaction**
> CaptureTransactionResponse captureTransaction(locationId, transactionId)

CaptureTransaction

Captures a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | 
String transactionId = "transactionId_example"; // String | 
try {
    CaptureTransactionResponse result = apiInstance.captureTransaction(locationId, transactionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#captureTransaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**|  |
 **transactionId** | **String**|  |

### Return type

[**CaptureTransactionResponse**](CaptureTransactionResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="charge"></a>
# **charge**
> ChargeResponse charge(locationId, body)

Charge

Charges a card represented by a card nonce or a customer&#39;s card on file.  Deprecated - recommend using [CreatePayment](#endpoint-payments-createpayment)  Your request to this endpoint must include _either_:  - A value for the &#x60;card_nonce&#x60; parameter (to charge a card nonce generated with the &#x60;SqPaymentForm&#x60;) - Values for the &#x60;customer_card_id&#x60; and &#x60;customer_id&#x60; parameters (to charge a customer&#39;s card on file)  When this response is returned, the amount of Square&#39;s processing fee might not yet be calculated. To obtain the processing fee, wait about ten seconds and call [RetrieveTransaction](#endpoint-transactions-retrievetransaction). See the &#x60;processing_fee_money&#x60; field of each [Tender included](#type-tender) in the transaction.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to associate the created transaction with.
ChargeRequest body = new ChargeRequest(); // ChargeRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    ChargeResponse result = apiInstance.charge(locationId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#charge");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to associate the created transaction with. |
 **body** | [**ChargeRequest**](ChargeRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**ChargeResponse**](ChargeResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="createRefund"></a>
# **createRefund**
> CreateRefundResponse createRefund(locationId, transactionId, body)

CreateRefund

Initiates a refund for a previously charged tender.  Deprecated - recommend using [RefundPayment](#endpoint-refunds-refundpayment)  You must issue a refund within 120 days of the associated payment. See [this article](https://squareup.com/help/us/en/article/5060) for more information on refund behavior.  NOTE: Card-present transactions with Interac credit cards **cannot be refunded using the Connect API**. Interac transactions must refunded in-person (e.g., dipping the card using POS app).

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the original transaction's associated location.
String transactionId = "transactionId_example"; // String | The ID of the original transaction that includes the tender to refund.
CreateRefundRequest body = new CreateRefundRequest(); // CreateRefundRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    CreateRefundResponse result = apiInstance.createRefund(locationId, transactionId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#createRefund");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the original transaction&#39;s associated location. |
 **transactionId** | **String**| The ID of the original transaction that includes the tender to refund. |
 **body** | [**CreateRefundRequest**](CreateRefundRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**CreateRefundResponse**](CreateRefundResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listRefunds"></a>
# **listRefunds**
> ListRefundsResponse listRefunds(locationId, beginTime, endTime, sortOrder, cursor)

ListRefunds

Lists refunds for one of a business&#39;s locations.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  In addition to full or partial tender refunds processed through Square APIs, refunds may result from itemized returns or exchanges through Square&#39;s Point of Sale applications.  Refunds with a &#x60;status&#x60; of &#x60;PENDING&#x60; are not currently included in this endpoint&#39;s response.  Max results per [page](#paginatingresults): 50

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list refunds for.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time.
String sortOrder = "sortOrder_example"; // String | The order in which results are listed in the response (`ASC` for oldest first, `DESC` for newest first).  Default value: `DESC`
String cursor = "cursor_example"; // String | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information.
try {
    ListRefundsResponse result = apiInstance.listRefunds(locationId, beginTime, endTime, sortOrder, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#listRefunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list refunds for. |
 **beginTime** | **String**| The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. | [optional]
 **sortOrder** | **String**| The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; | [optional]
 **cursor** | **String**| A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. | [optional]

### Return type

[**ListRefundsResponse**](ListRefundsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listTransactions"></a>
# **listTransactions**
> ListTransactionsResponse listTransactions(locationId, beginTime, endTime, sortOrder, cursor)

ListTransactions

Lists transactions for a particular location.  Deprecated - recommend using [SearchOrders](#endpoint-orders-searchorders)  Transactions include payment information from sales and exchanges and refund information from returns and exchanges.  Max results per [page](#paginatingresults): 50

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the location to list transactions for.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time.
String sortOrder = "sortOrder_example"; // String | The order in which results are listed in the response (`ASC` for oldest first, `DESC` for newest first).  Default value: `DESC`
String cursor = "cursor_example"; // String | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information.
try {
    ListTransactionsResponse result = apiInstance.listTransactions(locationId, beginTime, endTime, sortOrder, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#listTransactions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list transactions for. |
 **beginTime** | **String**| The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. | [optional]
 **sortOrder** | **String**| The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; | [optional]
 **cursor** | **String**| A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. | [optional]

### Return type

[**ListTransactionsResponse**](ListTransactionsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="retrieveTransaction"></a>
# **retrieveTransaction**
> RetrieveTransactionResponse retrieveTransaction(locationId, transactionId)

RetrieveTransaction

Retrieves details for a single transaction.  Deprecated - recommend using [BatchRetrieveOrders](#endpoint-batchretrieveorders)

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | The ID of the transaction's associated location.
String transactionId = "transactionId_example"; // String | The ID of the transaction to retrieve.
try {
    RetrieveTransactionResponse result = apiInstance.retrieveTransaction(locationId, transactionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#retrieveTransaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the transaction&#39;s associated location. |
 **transactionId** | **String**| The ID of the transaction to retrieve. |

### Return type

[**RetrieveTransactionResponse**](RetrieveTransactionResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="voidTransaction"></a>
# **voidTransaction**
> VoidTransactionResponse voidTransaction(locationId, transactionId)

VoidTransaction

Cancels a transaction that was created with the [Charge](#endpoint-transactions-charge) endpoint with a &#x60;delay_capture&#x60; value of &#x60;true&#x60;.  See the [Delay Capture of Funds](/transactions-api/cookbook/delay-capture) recipe for more information.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.TransactionsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

TransactionsApi apiInstance = new TransactionsApi();
String locationId = "locationId_example"; // String | 
String transactionId = "transactionId_example"; // String | 
try {
    VoidTransactionResponse result = apiInstance.voidTransaction(locationId, transactionId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling TransactionsApi#voidTransaction");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**|  |
 **transactionId** | **String**|  |

### Return type

[**VoidTransactionResponse**](VoidTransactionResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

