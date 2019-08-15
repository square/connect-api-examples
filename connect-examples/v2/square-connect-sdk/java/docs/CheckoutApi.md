# CheckoutApi

All URIs are relative to *https://connect.squareup.com*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCheckout**](CheckoutApi.md#createCheckout) | **POST** /v2/locations/{location_id}/checkouts | CreateCheckout


<a name="createCheckout"></a>
# **createCheckout**
> CreateCheckoutResponse createCheckout(locationId, body)

CreateCheckout

Links a &#x60;checkoutId&#x60; to a &#x60;checkout_page_url&#x60; that customers will be directed to in order to provide their payment information using a payment processing workflow hosted on connect.squareup.com.

### Example
```java
// Import classes:
//import com.squareup.connect.ApiClient;
//import com.squareup.connect.ApiException;
//import com.squareup.connect.Configuration;
//import com.squareup.connect.auth.*;
//import com.squareup.connect.api.CheckoutApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

CheckoutApi apiInstance = new CheckoutApi();
String locationId = "locationId_example"; // String | The ID of the business location to associate the checkout with.
CreateCheckoutRequest body = new CreateCheckoutRequest(); // CreateCheckoutRequest | An object containing the fields to POST for the request.  See the corresponding object definition for field details.
try {
    CreateCheckoutResponse result = apiInstance.createCheckout(locationId, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CheckoutApi#createCheckout");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **locationId** | **String**| The ID of the business location to associate the checkout with. |
 **body** | [**CreateCheckoutRequest**](CreateCheckoutRequest.md)| An object containing the fields to POST for the request.  See the corresponding object definition for field details. |

### Return type

[**CreateCheckoutResponse**](CreateCheckoutResponse.md)

### Authorization

[oauth2](../README.md#oauth2)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

