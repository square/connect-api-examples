# ReportingApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**listAdditionalRecipientReceivableRefunds**](ReportingApi.md#listAdditionalRecipientReceivableRefunds) | **GET** /v2/locations/{location_id}/additional-recipient-receivable-refunds | ListAdditionalRecipientReceivableRefunds
[**listAdditionalRecipientReceivables**](ReportingApi.md#listAdditionalRecipientReceivables) | **GET** /v2/locations/{location_id}/additional-recipient-receivables | ListAdditionalRecipientReceivables


<a name="listAdditionalRecipientReceivableRefunds"></a>
# **listAdditionalRecipientReceivableRefunds**
> ListAdditionalRecipientReceivableRefundsResponse listAdditionalRecipientReceivableRefunds(locationId, beginTime, endTime, sortOrder, cursor)

ListAdditionalRecipientReceivableRefunds

Returns a list of refunded transactions (across all possible originating locations) relating to monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.ReportingApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

ReportingApi apiInstance = new ReportingApi();
String locationId = "locationId_example"; // String | The ID of the location to list AdditionalRecipientReceivableRefunds for.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time.
String sortOrder = "sortOrder_example"; // String | The order in which results are listed in the response (`ASC` for oldest first, `DESC` for newest first).  Default value: `DESC`
String cursor = "cursor_example"; // String | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information.
try {
    ListAdditionalRecipientReceivableRefundsResponse result = apiInstance.listAdditionalRecipientReceivableRefunds(locationId, beginTime, endTime, sortOrder, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReportingApi#listAdditionalRecipientReceivableRefunds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list AdditionalRecipientReceivableRefunds for. |
 **beginTime** | **String**| The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. | [optional]
 **sortOrder** | **String**| The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; | [optional]
 **cursor** | **String**| A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. | [optional]

### Return type

[**ListAdditionalRecipientReceivableRefundsResponse**](ListAdditionalRecipientReceivableRefundsResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="listAdditionalRecipientReceivables"></a>
# **listAdditionalRecipientReceivables**
> ListAdditionalRecipientReceivablesResponse listAdditionalRecipientReceivables(locationId, beginTime, endTime, sortOrder, cursor)

ListAdditionalRecipientReceivables

Returns a list of receivables (across all possible sending locations) representing monies credited to the provided location ID by another Square account using the &#x60;additional_recipients&#x60; field in a transaction.  Max results per [page](#paginatingresults): 50

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.ReportingApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

ReportingApi apiInstance = new ReportingApi();
String locationId = "locationId_example"; // String | The ID of the location to list AdditionalRecipientReceivables for.
String beginTime = "beginTime_example"; // String | The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year.
String endTime = "endTime_example"; // String | The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time.
String sortOrder = "sortOrder_example"; // String | The order in which results are listed in the response (`ASC` for oldest first, `DESC` for newest first).  Default value: `DESC`
String cursor = "cursor_example"; // String | A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information.
try {
    ListAdditionalRecipientReceivablesResponse result = apiInstance.listAdditionalRecipientReceivables(locationId, beginTime, endTime, sortOrder, cursor);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ReportingApi#listAdditionalRecipientReceivables");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the location to list AdditionalRecipientReceivables for. |
 **beginTime** | **String**| The beginning of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time minus one year. | [optional]
 **endTime** | **String**| The end of the requested reporting period, in RFC 3339 format.  See [Date ranges](#dateranges) for details on date inclusivity/exclusivity.  Default value: The current time. | [optional]
 **sortOrder** | **String**| The order in which results are listed in the response (&#x60;ASC&#x60; for oldest first, &#x60;DESC&#x60; for newest first).  Default value: &#x60;DESC&#x60; | [optional]
 **cursor** | **String**| A pagination cursor returned by a previous call to this endpoint. Provide this to retrieve the next set of results for your original query.  See [Pagination](/basics/api101/pagination) for more information. | [optional]

### Return type

[**ListAdditionalRecipientReceivablesResponse**](ListAdditionalRecipientReceivablesResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

