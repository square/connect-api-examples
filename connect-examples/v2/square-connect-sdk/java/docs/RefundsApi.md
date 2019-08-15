# RefundsApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPaymentRefund**](RefundsApi.md#getPaymentRefund) | **GET** /v2/refunds/{refund_id} | GetPaymentRefund
[**listPaymentRefunds**](RefundsApi.md#listPaymentRefunds) | **GET** /v2/refunds | ListPaymentRefunds
[**refundPayment**](RefundsApi.md#refundPayment) | **POST** /v2/refunds | RefundPayment


<a name="getPaymentRefund"></a>
# **getPaymentRefund**
> GetPaymentRefundResponse getPaymentRefund(refundId)

GetPaymentRefund

Retrieves a specific &#x60;Refund&#x60; using the &#x60;refund_id&#x60;.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.RefundsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

RefundsApi apiInstance = new RefundsApi();
String refundId = "refundId_example"; // String | Unique ID for the desired `PaymentRefund`.
try {
    GetPaymentRefundResponse result = apiInstance.getPaymentRefund(refundId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RefundsApi#getPaymentRefund");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **refundId** | **String**| Unique ID for the desired &#x60;PaymentRefund&#x60;. |

### Return type

[**GetPaymentRefundResponse**](GetPaymentRefundResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listPaymentRefunds"></a>
# **listPaymentRefunds**
> ListPaymentRefundsResponse listPaymentRefunds(beginTime, endTime, sortOrder, cursor, locationId, status, sourceType)

ListPaymentRefunds

Retrieves a list of refunds for the account making the request.  Max results per page: 100

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.RefundsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

RefundsApi apiInstance = new RefundsApi();
String beginTime = "beginTime_example"; // String | Timestamp for the beginning of the requested reporting period, in RFC 3339 format.  Default: The current time minus one year.
String endTime = "endTime_example"; // String | Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time.
String sortOrder = "sortOrder_example"; // String | The order in which results are listed. - `ASC` - oldest to newest - `DESC` - newest to oldest (default).
String cursor = "cursor_example"; // String | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information.
String locationId = "locationId_example"; // String | ID of location associated with payment.
String status = "status_example"; // String | If provided, only refunds with the given status are returned.  For a list of refund status values, see [PaymentRefund](#type-paymentrefund).  Default: If omitted refunds are returned regardless of status.
String sourceType = "sourceType_example"; // String | If provided, only refunds with the given source type are returned.  - `CARD` - List refunds only for payments where card was specified as payment  source.  Default: If omitted refunds are returned regardless of source type.
try {
    ListPaymentRefundsResponse result = apiInstance.listPaymentRefunds(beginTime, endTime, sortOrder, cursor, locationId, status, sourceType);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RefundsApi#listPaymentRefunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **beginTime** | **String**| Timestamp for the beginning of the requested reporting period, in RFC 3339 format.  Default: The current time minus one year. | [optional]
 **endTime** | **String**| Timestamp for the end of the requested reporting period, in RFC 3339 format.  Default: The current time. | [optional]
 **sortOrder** | **String**| The order in which results are listed. - &#x60;ASC&#x60; - oldest to newest - &#x60;DESC&#x60; - newest to oldest (default). | [optional]
 **cursor** | **String**| A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for the original query.  See [Pagination](/basics/api101/pagination) for more information. | [optional]
 **locationId** | **String**| ID of location associated with payment. | [optional]
 **status** | **String**| If provided, only refunds with the given status are returned.  For a list of refund status values, see [PaymentRefund](#type-paymentrefund).  Default: If omitted refunds are returned regardless of status. | [optional]
 **sourceType** | **String**| If provided, only refunds with the given source type are returned.  - &#x60;CARD&#x60; - List refunds only for payments where card was specified as payment  source.  Default: If omitted refunds are returned regardless of source type. | [optional]

### Return type

[**ListPaymentRefundsResponse**](ListPaymentRefundsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="refundPayment"></a>
# **refundPayment**
> RefundPaymentResponse refundPayment(body)

RefundPayment

Refunds a payment. You can refund the entire payment amount or a  portion of it. For more information, see  [Payments and Refunds Overview](/payments-api/overview).

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.RefundsApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

RefundsApi apiInstance = new RefundsApi();
RefundPaymentRequest body = new RefundPaymentRequest(); // RefundPaymentRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    RefundPaymentResponse result = apiInstance.refundPayment(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RefundsApi#refundPayment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**RefundPaymentRequest**](RefundPaymentRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**RefundPaymentResponse**](RefundPaymentResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

