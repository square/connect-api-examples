# ApplePayApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**registerDomain**](ApplePayApi.md#registerDomain) | **POST** /v2/apple-pay/domains | RegisterDomain


<a name="registerDomain"></a>
# **registerDomain**
> RegisterDomainResponse registerDomain(body)

RegisterDomain

Activates a domain for use with Web Apple Pay and Square. A validation will be performed on this domain by Apple to ensure is it properly set up as an Apple Pay enabled domain.  This endpoint provides an easy way for platform developers to bulk activate Web Apple Pay with Square for merchants using their platform.  To learn more about Apple Pay on Web see the Apple Pay section in the [Embedding the Square Payment Form](/payment-form/add-digital-wallets/apple-pay) guide.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.ApplePayApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

ApplePayApi apiInstance = new ApplePayApi();
RegisterDomainRequest body = new RegisterDomainRequest(); // RegisterDomainRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    RegisterDomainResponse result = apiInstance.registerDomain(body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApplePayApi#registerDomain");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**RegisterDomainRequest**](RegisterDomainRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**RegisterDomainResponse**](RegisterDomainResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

