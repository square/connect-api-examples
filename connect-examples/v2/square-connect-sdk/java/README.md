![Square logo]

# Square Connect Java SDK

---

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.squareup/connect/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.squareup/connect)
[![Apache-2 license](https://img.shields.io/badge/license-Apache2-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0)
==================

**If you have feedback about the new SDKs, or just want to talk to other Square Developers, request an invite to the new [slack community for Square Developers](https://squ.re/2GB8GHk)**

## ENUM to String Migration
The Java SDK no longer treats enums as explicit types. Instead, all enums are handled as static strings.
Previously, you would use an enum constant to represent the related string value. For example:
```java
Money money = new Money();
money.setCurrency(Money.CurrencyEnum.USD);
```

As of version 2.20190710.0, you would work with the static string value directly. For example:
```java
Money money = new Money();
money.setCurrency("USD");
```

But, as a best practice, we recommend representing enum strings as constants for easier reuse. For example:
```java
String MONEY_USD = "USD";
Money money = new Money();
money.setCurrency(MONEY_USD);
```

## Requirements

Java 8

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

### Option 1: Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>com.squareup</groupId>
    <artifactId>connect</artifactId>
    <version>2.20190814.0</version>
    <scope>compile</scope>
</dependency>
```

### Option 2: Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.squareup:connect:2.20190814.0"
```

### Option 3: Build and Install locally

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install -DskipTests
```

To run tests, you would need to create below **accounts.json** under **travis-ci** folder.

```json
{
  "US-Prod": {
    "email": "your_email",
    "access_token": "your_access_token",
    "location_id": "your_location_id",
    "application_id": "your_application_id"
  },
  "US-Prod-Sandbox": {
    "email": "your_email",
    "access_token": "your_sandbox_access_token",
    "location_id": "your_sandbox_location_id",
    "application_id": "your_sandbox_application_id"
  }
}
```

```shell
mvn install
```

### Option 4: Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/connect-2.20190814.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java
import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.Location;

import java.io.File;
import java.util.*;

public class Example {

    public static void main(String[] args) {
        ApiClient apiClient = Configuration.getDefaultApiClient();

        // Configure OAuth2 access token for authorization: oauth2
        OAuth oauth2 = (OAuth) apiClient.getAuthentication("oauth2");
        oauth2.setAccessToken("YOUR_ACCESS_TOKEN");

        // List all locations
        LocationsApi locationsApi = new LocationsApi();
        locationsApi.setApiClient(apiClient);

        try {
            List<Location> locations = locationsApi.listLocations().getLocations();
            System.out.println(locations);
        } catch (ApiException e) {
            System.err.println("Exception when calling API");
            e.printStackTrace();
        }
    }
}
```

### How to configure sandbox environment
```java

import com.squareup.connect.ApiClient;
import com.squareup.connect.ApiException;
import com.squareup.connect.Configuration;
import com.squareup.connect.api.LocationsApi;
import com.squareup.connect.auth.OAuth;
import com.squareup.connect.models.Location;

import java.io.File;
import java.util.*;

public class Example {

    public static void main(String[] args) {
        ApiClient apiClient = Configuration.getDefaultApiClient();
        // Set sandbox url
        apiClient.setBasePath('https://connect.squareupsandbox.com');

        // Configure OAuth2 access token for authorization: oauth2
        OAuth oauth2 = (OAuth) apiClient.getAuthentication("oauth2");
        // Set your sandbox token
        oauth2.setAccessToken("YOUR_SANDBOX_ACCESS_TOKEN");

        LocationsApi locationsApi = new LocationsApi();
    }
}
```

## Documentation for API Endpoints

All URIs are relative to *https://connect.squareup.com*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*ApplePayApi* | [**registerDomain**](docs/ApplePayApi.md#registerDomain) | **POST** /v2/apple-pay/domains | RegisterDomain
*CatalogApi* | [**batchDeleteCatalogObjects**](docs/CatalogApi.md#batchDeleteCatalogObjects) | **POST** /v2/catalog/batch-delete | BatchDeleteCatalogObjects
*CatalogApi* | [**batchRetrieveCatalogObjects**](docs/CatalogApi.md#batchRetrieveCatalogObjects) | **POST** /v2/catalog/batch-retrieve | BatchRetrieveCatalogObjects
*CatalogApi* | [**batchUpsertCatalogObjects**](docs/CatalogApi.md#batchUpsertCatalogObjects) | **POST** /v2/catalog/batch-upsert | BatchUpsertCatalogObjects
*CatalogApi* | [**catalogInfo**](docs/CatalogApi.md#catalogInfo) | **GET** /v2/catalog/info | CatalogInfo
*CatalogApi* | [**deleteCatalogObject**](docs/CatalogApi.md#deleteCatalogObject) | **DELETE** /v2/catalog/object/{object_id} | DeleteCatalogObject
*CatalogApi* | [**listCatalog**](docs/CatalogApi.md#listCatalog) | **GET** /v2/catalog/list | ListCatalog
*CatalogApi* | [**retrieveCatalogObject**](docs/CatalogApi.md#retrieveCatalogObject) | **GET** /v2/catalog/object/{object_id} | RetrieveCatalogObject
*CatalogApi* | [**searchCatalogObjects**](docs/CatalogApi.md#searchCatalogObjects) | **POST** /v2/catalog/search | SearchCatalogObjects
*CatalogApi* | [**updateItemModifierLists**](docs/CatalogApi.md#updateItemModifierLists) | **POST** /v2/catalog/update-item-modifier-lists | UpdateItemModifierLists
*CatalogApi* | [**updateItemTaxes**](docs/CatalogApi.md#updateItemTaxes) | **POST** /v2/catalog/update-item-taxes | UpdateItemTaxes
*CatalogApi* | [**upsertCatalogObject**](docs/CatalogApi.md#upsertCatalogObject) | **POST** /v2/catalog/object | UpsertCatalogObject
*CheckoutApi* | [**createCheckout**](docs/CheckoutApi.md#createCheckout) | **POST** /v2/locations/{location_id}/checkouts | CreateCheckout
*CustomersApi* | [**createCustomer**](docs/CustomersApi.md#createCustomer) | **POST** /v2/customers | CreateCustomer
*CustomersApi* | [**createCustomerCard**](docs/CustomersApi.md#createCustomerCard) | **POST** /v2/customers/{customer_id}/cards | CreateCustomerCard
*CustomersApi* | [**deleteCustomer**](docs/CustomersApi.md#deleteCustomer) | **DELETE** /v2/customers/{customer_id} | DeleteCustomer
*CustomersApi* | [**deleteCustomerCard**](docs/CustomersApi.md#deleteCustomerCard) | **DELETE** /v2/customers/{customer_id}/cards/{card_id} | DeleteCustomerCard
*CustomersApi* | [**listCustomers**](docs/CustomersApi.md#listCustomers) | **GET** /v2/customers | ListCustomers
*CustomersApi* | [**retrieveCustomer**](docs/CustomersApi.md#retrieveCustomer) | **GET** /v2/customers/{customer_id} | RetrieveCustomer
*CustomersApi* | [**searchCustomers**](docs/CustomersApi.md#searchCustomers) | **POST** /v2/customers/search | SearchCustomers
*CustomersApi* | [**updateCustomer**](docs/CustomersApi.md#updateCustomer) | **PUT** /v2/customers/{customer_id} | UpdateCustomer
*EmployeesApi* | [**listEmployees**](docs/EmployeesApi.md#listEmployees) | **GET** /v2/employees | ListEmployees
*EmployeesApi* | [**retrieveEmployee**](docs/EmployeesApi.md#retrieveEmployee) | **GET** /v2/employees/{id} | RetrieveEmployee
*InventoryApi* | [**batchChangeInventory**](docs/InventoryApi.md#batchChangeInventory) | **POST** /v2/inventory/batch-change | BatchChangeInventory
*InventoryApi* | [**batchRetrieveInventoryChanges**](docs/InventoryApi.md#batchRetrieveInventoryChanges) | **POST** /v2/inventory/batch-retrieve-changes | BatchRetrieveInventoryChanges
*InventoryApi* | [**batchRetrieveInventoryCounts**](docs/InventoryApi.md#batchRetrieveInventoryCounts) | **POST** /v2/inventory/batch-retrieve-counts | BatchRetrieveInventoryCounts
*InventoryApi* | [**retrieveInventoryAdjustment**](docs/InventoryApi.md#retrieveInventoryAdjustment) | **GET** /v2/inventory/adjustment/{adjustment_id} | RetrieveInventoryAdjustment
*InventoryApi* | [**retrieveInventoryChanges**](docs/InventoryApi.md#retrieveInventoryChanges) | **GET** /v2/inventory/{catalog_object_id}/changes | RetrieveInventoryChanges
*InventoryApi* | [**retrieveInventoryCount**](docs/InventoryApi.md#retrieveInventoryCount) | **GET** /v2/inventory/{catalog_object_id} | RetrieveInventoryCount
*InventoryApi* | [**retrieveInventoryPhysicalCount**](docs/InventoryApi.md#retrieveInventoryPhysicalCount) | **GET** /v2/inventory/physical-count/{physical_count_id} | RetrieveInventoryPhysicalCount
*LaborApi* | [**createBreakType**](docs/LaborApi.md#createBreakType) | **POST** /v2/labor/break-types | CreateBreakType
*LaborApi* | [**createShift**](docs/LaborApi.md#createShift) | **POST** /v2/labor/shifts | CreateShift
*LaborApi* | [**deleteBreakType**](docs/LaborApi.md#deleteBreakType) | **DELETE** /v2/labor/break-types/{id} | DeleteBreakType
*LaborApi* | [**deleteShift**](docs/LaborApi.md#deleteShift) | **DELETE** /v2/labor/shifts/{id} | DeleteShift
*LaborApi* | [**getBreakType**](docs/LaborApi.md#getBreakType) | **GET** /v2/labor/break-types/{id} | GetBreakType
*LaborApi* | [**getEmployeeWage**](docs/LaborApi.md#getEmployeeWage) | **GET** /v2/labor/employee-wages/{id} | GetEmployeeWage
*LaborApi* | [**getShift**](docs/LaborApi.md#getShift) | **GET** /v2/labor/shifts/{id} | GetShift
*LaborApi* | [**listBreakTypes**](docs/LaborApi.md#listBreakTypes) | **GET** /v2/labor/break-types | ListBreakTypes
*LaborApi* | [**listEmployeeWages**](docs/LaborApi.md#listEmployeeWages) | **GET** /v2/labor/employee-wages | ListEmployeeWages
*LaborApi* | [**listWorkweekConfigs**](docs/LaborApi.md#listWorkweekConfigs) | **GET** /v2/labor/workweek-configs | ListWorkweekConfigs
*LaborApi* | [**searchShifts**](docs/LaborApi.md#searchShifts) | **POST** /v2/labor/shifts/search | SearchShifts
*LaborApi* | [**updateBreakType**](docs/LaborApi.md#updateBreakType) | **PUT** /v2/labor/break-types/{id} | UpdateBreakType
*LaborApi* | [**updateShift**](docs/LaborApi.md#updateShift) | **PUT** /v2/labor/shifts/{id} | UpdateShift
*LaborApi* | [**updateWorkweekConfig**](docs/LaborApi.md#updateWorkweekConfig) | **PUT** /v2/labor/workweek-configs/{id} | UpdateWorkweekConfig
*LocationsApi* | [**listLocations**](docs/LocationsApi.md#listLocations) | **GET** /v2/locations | ListLocations
*MobileAuthorizationApi* | [**createMobileAuthorizationCode**](docs/MobileAuthorizationApi.md#createMobileAuthorizationCode) | **POST** /mobile/authorization-code | CreateMobileAuthorizationCode
*OAuthApi* | [**obtainToken**](docs/OAuthApi.md#obtainToken) | **POST** /oauth2/token | ObtainToken
*OAuthApi* | [**renewToken**](docs/OAuthApi.md#renewToken) | **POST** /oauth2/clients/{client_id}/access-token/renew | RenewToken
*OAuthApi* | [**revokeToken**](docs/OAuthApi.md#revokeToken) | **POST** /oauth2/revoke | RevokeToken
*OrdersApi* | [**batchRetrieveOrders**](docs/OrdersApi.md#batchRetrieveOrders) | **POST** /v2/locations/{location_id}/orders/batch-retrieve | BatchRetrieveOrders
*OrdersApi* | [**createOrder**](docs/OrdersApi.md#createOrder) | **POST** /v2/locations/{location_id}/orders | CreateOrder
*OrdersApi* | [**payOrder**](docs/OrdersApi.md#payOrder) | **POST** /v2/orders/{order_id}/pay | PayOrder
*OrdersApi* | [**searchOrders**](docs/OrdersApi.md#searchOrders) | **POST** /v2/orders/search | SearchOrders
*OrdersApi* | [**updateOrder**](docs/OrdersApi.md#updateOrder) | **PUT** /v2/locations/{location_id}/orders/{order_id} | UpdateOrder
*PaymentsApi* | [**cancelPayment**](docs/PaymentsApi.md#cancelPayment) | **POST** /v2/payments/{payment_id}/cancel | CancelPayment
*PaymentsApi* | [**cancelPaymentByIdempotencyKey**](docs/PaymentsApi.md#cancelPaymentByIdempotencyKey) | **POST** /v2/payments/cancel | CancelPaymentByIdempotencyKey
*PaymentsApi* | [**completePayment**](docs/PaymentsApi.md#completePayment) | **POST** /v2/payments/{payment_id}/complete | CompletePayment
*PaymentsApi* | [**createPayment**](docs/PaymentsApi.md#createPayment) | **POST** /v2/payments | CreatePayment
*PaymentsApi* | [**getPayment**](docs/PaymentsApi.md#getPayment) | **GET** /v2/payments/{payment_id} | GetPayment
*PaymentsApi* | [**listPayments**](docs/PaymentsApi.md#listPayments) | **GET** /v2/payments | ListPayments
*RefundsApi* | [**getPaymentRefund**](docs/RefundsApi.md#getPaymentRefund) | **GET** /v2/refunds/{refund_id} | GetPaymentRefund
*RefundsApi* | [**listPaymentRefunds**](docs/RefundsApi.md#listPaymentRefunds) | **GET** /v2/refunds | ListPaymentRefunds
*RefundsApi* | [**refundPayment**](docs/RefundsApi.md#refundPayment) | **POST** /v2/refunds | RefundPayment
*ReportingApi* | [**listAdditionalRecipientReceivableRefunds**](docs/ReportingApi.md#listAdditionalRecipientReceivableRefunds) | **GET** /v2/locations/{location_id}/additional-recipient-receivable-refunds | ListAdditionalRecipientReceivableRefunds
*ReportingApi* | [**listAdditionalRecipientReceivables**](docs/ReportingApi.md#listAdditionalRecipientReceivables) | **GET** /v2/locations/{location_id}/additional-recipient-receivables | ListAdditionalRecipientReceivables
*TransactionsApi* | [**captureTransaction**](docs/TransactionsApi.md#captureTransaction) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/capture | CaptureTransaction
*TransactionsApi* | [**charge**](docs/TransactionsApi.md#charge) | **POST** /v2/locations/{location_id}/transactions | Charge
*TransactionsApi* | [**createRefund**](docs/TransactionsApi.md#createRefund) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/refund | CreateRefund
*TransactionsApi* | [**listRefunds**](docs/TransactionsApi.md#listRefunds) | **GET** /v2/locations/{location_id}/refunds | ListRefunds
*TransactionsApi* | [**listTransactions**](docs/TransactionsApi.md#listTransactions) | **GET** /v2/locations/{location_id}/transactions | ListTransactions
*TransactionsApi* | [**retrieveTransaction**](docs/TransactionsApi.md#retrieveTransaction) | **GET** /v2/locations/{location_id}/transactions/{transaction_id} | RetrieveTransaction
*TransactionsApi* | [**voidTransaction**](docs/TransactionsApi.md#voidTransaction) | **POST** /v2/locations/{location_id}/transactions/{transaction_id}/void | VoidTransaction
*V1EmployeesApi* | [**createEmployee**](docs/V1EmployeesApi.md#createEmployee) | **POST** /v1/me/employees | CreateEmployee
*V1EmployeesApi* | [**createEmployeeRole**](docs/V1EmployeesApi.md#createEmployeeRole) | **POST** /v1/me/roles | CreateEmployeeRole
*V1EmployeesApi* | [**createTimecard**](docs/V1EmployeesApi.md#createTimecard) | **POST** /v1/me/timecards | CreateTimecard
*V1EmployeesApi* | [**deleteTimecard**](docs/V1EmployeesApi.md#deleteTimecard) | **DELETE** /v1/me/timecards/{timecard_id} | DeleteTimecard
*V1EmployeesApi* | [**listCashDrawerShifts**](docs/V1EmployeesApi.md#listCashDrawerShifts) | **GET** /v1/{location_id}/cash-drawer-shifts | ListCashDrawerShifts
*V1EmployeesApi* | [**listEmployeeRoles**](docs/V1EmployeesApi.md#listEmployeeRoles) | **GET** /v1/me/roles | ListEmployeeRoles
*V1EmployeesApi* | [**listEmployees**](docs/V1EmployeesApi.md#listEmployees) | **GET** /v1/me/employees | ListEmployees
*V1EmployeesApi* | [**listTimecardEvents**](docs/V1EmployeesApi.md#listTimecardEvents) | **GET** /v1/me/timecards/{timecard_id}/events | ListTimecardEvents
*V1EmployeesApi* | [**listTimecards**](docs/V1EmployeesApi.md#listTimecards) | **GET** /v1/me/timecards | ListTimecards
*V1EmployeesApi* | [**retrieveCashDrawerShift**](docs/V1EmployeesApi.md#retrieveCashDrawerShift) | **GET** /v1/{location_id}/cash-drawer-shifts/{shift_id} | RetrieveCashDrawerShift
*V1EmployeesApi* | [**retrieveEmployee**](docs/V1EmployeesApi.md#retrieveEmployee) | **GET** /v1/me/employees/{employee_id} | RetrieveEmployee
*V1EmployeesApi* | [**retrieveEmployeeRole**](docs/V1EmployeesApi.md#retrieveEmployeeRole) | **GET** /v1/me/roles/{role_id} | RetrieveEmployeeRole
*V1EmployeesApi* | [**retrieveTimecard**](docs/V1EmployeesApi.md#retrieveTimecard) | **GET** /v1/me/timecards/{timecard_id} | RetrieveTimecard
*V1EmployeesApi* | [**updateEmployee**](docs/V1EmployeesApi.md#updateEmployee) | **PUT** /v1/me/employees/{employee_id} | UpdateEmployee
*V1EmployeesApi* | [**updateEmployeeRole**](docs/V1EmployeesApi.md#updateEmployeeRole) | **PUT** /v1/me/roles/{role_id} | UpdateEmployeeRole
*V1EmployeesApi* | [**updateTimecard**](docs/V1EmployeesApi.md#updateTimecard) | **PUT** /v1/me/timecards/{timecard_id} | UpdateTimecard
*V1ItemsApi* | [**adjustInventory**](docs/V1ItemsApi.md#adjustInventory) | **POST** /v1/{location_id}/inventory/{variation_id} | AdjustInventory
*V1ItemsApi* | [**applyFee**](docs/V1ItemsApi.md#applyFee) | **PUT** /v1/{location_id}/items/{item_id}/fees/{fee_id} | ApplyFee
*V1ItemsApi* | [**applyModifierList**](docs/V1ItemsApi.md#applyModifierList) | **PUT** /v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id} | ApplyModifierList
*V1ItemsApi* | [**createCategory**](docs/V1ItemsApi.md#createCategory) | **POST** /v1/{location_id}/categories | CreateCategory
*V1ItemsApi* | [**createDiscount**](docs/V1ItemsApi.md#createDiscount) | **POST** /v1/{location_id}/discounts | CreateDiscount
*V1ItemsApi* | [**createFee**](docs/V1ItemsApi.md#createFee) | **POST** /v1/{location_id}/fees | CreateFee
*V1ItemsApi* | [**createItem**](docs/V1ItemsApi.md#createItem) | **POST** /v1/{location_id}/items | CreateItem
*V1ItemsApi* | [**createModifierList**](docs/V1ItemsApi.md#createModifierList) | **POST** /v1/{location_id}/modifier-lists | CreateModifierList
*V1ItemsApi* | [**createModifierOption**](docs/V1ItemsApi.md#createModifierOption) | **POST** /v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options | CreateModifierOption
*V1ItemsApi* | [**createPage**](docs/V1ItemsApi.md#createPage) | **POST** /v1/{location_id}/pages | CreatePage
*V1ItemsApi* | [**createVariation**](docs/V1ItemsApi.md#createVariation) | **POST** /v1/{location_id}/items/{item_id}/variations | CreateVariation
*V1ItemsApi* | [**deleteCategory**](docs/V1ItemsApi.md#deleteCategory) | **DELETE** /v1/{location_id}/categories/{category_id} | DeleteCategory
*V1ItemsApi* | [**deleteDiscount**](docs/V1ItemsApi.md#deleteDiscount) | **DELETE** /v1/{location_id}/discounts/{discount_id} | DeleteDiscount
*V1ItemsApi* | [**deleteFee**](docs/V1ItemsApi.md#deleteFee) | **DELETE** /v1/{location_id}/fees/{fee_id} | DeleteFee
*V1ItemsApi* | [**deleteItem**](docs/V1ItemsApi.md#deleteItem) | **DELETE** /v1/{location_id}/items/{item_id} | DeleteItem
*V1ItemsApi* | [**deleteModifierList**](docs/V1ItemsApi.md#deleteModifierList) | **DELETE** /v1/{location_id}/modifier-lists/{modifier_list_id} | DeleteModifierList
*V1ItemsApi* | [**deleteModifierOption**](docs/V1ItemsApi.md#deleteModifierOption) | **DELETE** /v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id} | DeleteModifierOption
*V1ItemsApi* | [**deletePage**](docs/V1ItemsApi.md#deletePage) | **DELETE** /v1/{location_id}/pages/{page_id} | DeletePage
*V1ItemsApi* | [**deletePageCell**](docs/V1ItemsApi.md#deletePageCell) | **DELETE** /v1/{location_id}/pages/{page_id}/cells | DeletePageCell
*V1ItemsApi* | [**deleteVariation**](docs/V1ItemsApi.md#deleteVariation) | **DELETE** /v1/{location_id}/items/{item_id}/variations/{variation_id} | DeleteVariation
*V1ItemsApi* | [**listCategories**](docs/V1ItemsApi.md#listCategories) | **GET** /v1/{location_id}/categories | ListCategories
*V1ItemsApi* | [**listDiscounts**](docs/V1ItemsApi.md#listDiscounts) | **GET** /v1/{location_id}/discounts | ListDiscounts
*V1ItemsApi* | [**listFees**](docs/V1ItemsApi.md#listFees) | **GET** /v1/{location_id}/fees | ListFees
*V1ItemsApi* | [**listInventory**](docs/V1ItemsApi.md#listInventory) | **GET** /v1/{location_id}/inventory | ListInventory
*V1ItemsApi* | [**listItems**](docs/V1ItemsApi.md#listItems) | **GET** /v1/{location_id}/items | ListItems
*V1ItemsApi* | [**listModifierLists**](docs/V1ItemsApi.md#listModifierLists) | **GET** /v1/{location_id}/modifier-lists | ListModifierLists
*V1ItemsApi* | [**listPages**](docs/V1ItemsApi.md#listPages) | **GET** /v1/{location_id}/pages | ListPages
*V1ItemsApi* | [**removeFee**](docs/V1ItemsApi.md#removeFee) | **DELETE** /v1/{location_id}/items/{item_id}/fees/{fee_id} | RemoveFee
*V1ItemsApi* | [**removeModifierList**](docs/V1ItemsApi.md#removeModifierList) | **DELETE** /v1/{location_id}/items/{item_id}/modifier-lists/{modifier_list_id} | RemoveModifierList
*V1ItemsApi* | [**retrieveItem**](docs/V1ItemsApi.md#retrieveItem) | **GET** /v1/{location_id}/items/{item_id} | RetrieveItem
*V1ItemsApi* | [**retrieveModifierList**](docs/V1ItemsApi.md#retrieveModifierList) | **GET** /v1/{location_id}/modifier-lists/{modifier_list_id} | RetrieveModifierList
*V1ItemsApi* | [**updateCategory**](docs/V1ItemsApi.md#updateCategory) | **PUT** /v1/{location_id}/categories/{category_id} | UpdateCategory
*V1ItemsApi* | [**updateDiscount**](docs/V1ItemsApi.md#updateDiscount) | **PUT** /v1/{location_id}/discounts/{discount_id} | UpdateDiscount
*V1ItemsApi* | [**updateFee**](docs/V1ItemsApi.md#updateFee) | **PUT** /v1/{location_id}/fees/{fee_id} | UpdateFee
*V1ItemsApi* | [**updateItem**](docs/V1ItemsApi.md#updateItem) | **PUT** /v1/{location_id}/items/{item_id} | UpdateItem
*V1ItemsApi* | [**updateModifierList**](docs/V1ItemsApi.md#updateModifierList) | **PUT** /v1/{location_id}/modifier-lists/{modifier_list_id} | UpdateModifierList
*V1ItemsApi* | [**updateModifierOption**](docs/V1ItemsApi.md#updateModifierOption) | **PUT** /v1/{location_id}/modifier-lists/{modifier_list_id}/modifier-options/{modifier_option_id} | UpdateModifierOption
*V1ItemsApi* | [**updatePage**](docs/V1ItemsApi.md#updatePage) | **PUT** /v1/{location_id}/pages/{page_id} | UpdatePage
*V1ItemsApi* | [**updatePageCell**](docs/V1ItemsApi.md#updatePageCell) | **PUT** /v1/{location_id}/pages/{page_id}/cells | UpdatePageCell
*V1ItemsApi* | [**updateVariation**](docs/V1ItemsApi.md#updateVariation) | **PUT** /v1/{location_id}/items/{item_id}/variations/{variation_id} | UpdateVariation
*V1LocationsApi* | [**listLocations**](docs/V1LocationsApi.md#listLocations) | **GET** /v1/me/locations | ListLocations
*V1LocationsApi* | [**retrieveBusiness**](docs/V1LocationsApi.md#retrieveBusiness) | **GET** /v1/me | RetrieveBusiness
*V1TransactionsApi* | [**createRefund**](docs/V1TransactionsApi.md#createRefund) | **POST** /v1/{location_id}/refunds | CreateRefund
*V1TransactionsApi* | [**listBankAccounts**](docs/V1TransactionsApi.md#listBankAccounts) | **GET** /v1/{location_id}/bank-accounts | ListBankAccounts
*V1TransactionsApi* | [**listOrders**](docs/V1TransactionsApi.md#listOrders) | **GET** /v1/{location_id}/orders | ListOrders
*V1TransactionsApi* | [**listPayments**](docs/V1TransactionsApi.md#listPayments) | **GET** /v1/{location_id}/payments | ListPayments
*V1TransactionsApi* | [**listRefunds**](docs/V1TransactionsApi.md#listRefunds) | **GET** /v1/{location_id}/refunds | ListRefunds
*V1TransactionsApi* | [**listSettlements**](docs/V1TransactionsApi.md#listSettlements) | **GET** /v1/{location_id}/settlements | ListSettlements
*V1TransactionsApi* | [**retrieveBankAccount**](docs/V1TransactionsApi.md#retrieveBankAccount) | **GET** /v1/{location_id}/bank-accounts/{bank_account_id} | RetrieveBankAccount
*V1TransactionsApi* | [**retrieveOrder**](docs/V1TransactionsApi.md#retrieveOrder) | **GET** /v1/{location_id}/orders/{order_id} | RetrieveOrder
*V1TransactionsApi* | [**retrievePayment**](docs/V1TransactionsApi.md#retrievePayment) | **GET** /v1/{location_id}/payments/{payment_id} | RetrievePayment
*V1TransactionsApi* | [**retrieveSettlement**](docs/V1TransactionsApi.md#retrieveSettlement) | **GET** /v1/{location_id}/settlements/{settlement_id} | RetrieveSettlement
*V1TransactionsApi* | [**updateOrder**](docs/V1TransactionsApi.md#updateOrder) | **PUT** /v1/{location_id}/orders/{order_id} | UpdateOrder


## Documentation for Models

 - [AdditionalRecipient](docs/AdditionalRecipient.md)
 - [AdditionalRecipientReceivable](docs/AdditionalRecipientReceivable.md)
 - [AdditionalRecipientReceivableRefund](docs/AdditionalRecipientReceivableRefund.md)
 - [Address](docs/Address.md)
 - [BalancePaymentDetails](docs/BalancePaymentDetails.md)
 - [BatchChangeInventoryRequest](docs/BatchChangeInventoryRequest.md)
 - [BatchChangeInventoryResponse](docs/BatchChangeInventoryResponse.md)
 - [BatchDeleteCatalogObjectsRequest](docs/BatchDeleteCatalogObjectsRequest.md)
 - [BatchDeleteCatalogObjectsResponse](docs/BatchDeleteCatalogObjectsResponse.md)
 - [BatchRetrieveCatalogObjectsRequest](docs/BatchRetrieveCatalogObjectsRequest.md)
 - [BatchRetrieveCatalogObjectsResponse](docs/BatchRetrieveCatalogObjectsResponse.md)
 - [BatchRetrieveInventoryChangesRequest](docs/BatchRetrieveInventoryChangesRequest.md)
 - [BatchRetrieveInventoryChangesResponse](docs/BatchRetrieveInventoryChangesResponse.md)
 - [BatchRetrieveInventoryCountsRequest](docs/BatchRetrieveInventoryCountsRequest.md)
 - [BatchRetrieveInventoryCountsResponse](docs/BatchRetrieveInventoryCountsResponse.md)
 - [BatchRetrieveOrdersRequest](docs/BatchRetrieveOrdersRequest.md)
 - [BatchRetrieveOrdersResponse](docs/BatchRetrieveOrdersResponse.md)
 - [BatchUpsertCatalogObjectsRequest](docs/BatchUpsertCatalogObjectsRequest.md)
 - [BatchUpsertCatalogObjectsResponse](docs/BatchUpsertCatalogObjectsResponse.md)
 - [BreakType](docs/BreakType.md)
 - [BusinessHours](docs/BusinessHours.md)
 - [BusinessHoursPeriod](docs/BusinessHoursPeriod.md)
 - [CancelPaymentByIdempotencyKeyRequest](docs/CancelPaymentByIdempotencyKeyRequest.md)
 - [CancelPaymentByIdempotencyKeyResponse](docs/CancelPaymentByIdempotencyKeyResponse.md)
 - [CancelPaymentRequest](docs/CancelPaymentRequest.md)
 - [CancelPaymentResponse](docs/CancelPaymentResponse.md)
 - [CaptureTransactionRequest](docs/CaptureTransactionRequest.md)
 - [CaptureTransactionResponse](docs/CaptureTransactionResponse.md)
 - [Card](docs/Card.md)
 - [CardBrand](docs/CardBrand.md)
 - [CardPaymentDetails](docs/CardPaymentDetails.md)
 - [CatalogCategory](docs/CatalogCategory.md)
 - [CatalogDiscount](docs/CatalogDiscount.md)
 - [CatalogDiscountType](docs/CatalogDiscountType.md)
 - [CatalogIdMapping](docs/CatalogIdMapping.md)
 - [CatalogImage](docs/CatalogImage.md)
 - [CatalogInfoRequest](docs/CatalogInfoRequest.md)
 - [CatalogInfoResponse](docs/CatalogInfoResponse.md)
 - [CatalogInfoResponseLimits](docs/CatalogInfoResponseLimits.md)
 - [CatalogItem](docs/CatalogItem.md)
 - [CatalogItemModifierListInfo](docs/CatalogItemModifierListInfo.md)
 - [CatalogItemOption](docs/CatalogItemOption.md)
 - [CatalogItemOptionForItem](docs/CatalogItemOptionForItem.md)
 - [CatalogItemOptionValue](docs/CatalogItemOptionValue.md)
 - [CatalogItemOptionValueForItemVariation](docs/CatalogItemOptionValueForItemVariation.md)
 - [CatalogItemProductType](docs/CatalogItemProductType.md)
 - [CatalogItemVariation](docs/CatalogItemVariation.md)
 - [CatalogMeasurementUnit](docs/CatalogMeasurementUnit.md)
 - [CatalogModifier](docs/CatalogModifier.md)
 - [CatalogModifierList](docs/CatalogModifierList.md)
 - [CatalogModifierListSelectionType](docs/CatalogModifierListSelectionType.md)
 - [CatalogModifierOverride](docs/CatalogModifierOverride.md)
 - [CatalogObject](docs/CatalogObject.md)
 - [CatalogObjectBatch](docs/CatalogObjectBatch.md)
 - [CatalogObjectType](docs/CatalogObjectType.md)
 - [CatalogPricingRule](docs/CatalogPricingRule.md)
 - [CatalogPricingType](docs/CatalogPricingType.md)
 - [CatalogProductSet](docs/CatalogProductSet.md)
 - [CatalogQuery](docs/CatalogQuery.md)
 - [CatalogQueryExact](docs/CatalogQueryExact.md)
 - [CatalogQueryItemVariationsForItemOptionValues](docs/CatalogQueryItemVariationsForItemOptionValues.md)
 - [CatalogQueryItemsForItemOptions](docs/CatalogQueryItemsForItemOptions.md)
 - [CatalogQueryItemsForModifierList](docs/CatalogQueryItemsForModifierList.md)
 - [CatalogQueryItemsForTax](docs/CatalogQueryItemsForTax.md)
 - [CatalogQueryPrefix](docs/CatalogQueryPrefix.md)
 - [CatalogQueryRange](docs/CatalogQueryRange.md)
 - [CatalogQuerySortedAttribute](docs/CatalogQuerySortedAttribute.md)
 - [CatalogQueryText](docs/CatalogQueryText.md)
 - [CatalogTax](docs/CatalogTax.md)
 - [CatalogTimePeriod](docs/CatalogTimePeriod.md)
 - [CatalogV1Id](docs/CatalogV1Id.md)
 - [ChargeRequest](docs/ChargeRequest.md)
 - [ChargeRequestAdditionalRecipient](docs/ChargeRequestAdditionalRecipient.md)
 - [ChargeResponse](docs/ChargeResponse.md)
 - [Checkout](docs/Checkout.md)
 - [CompletePaymentRequest](docs/CompletePaymentRequest.md)
 - [CompletePaymentResponse](docs/CompletePaymentResponse.md)
 - [Coordinates](docs/Coordinates.md)
 - [Country](docs/Country.md)
 - [CreateBreakTypeRequest](docs/CreateBreakTypeRequest.md)
 - [CreateBreakTypeResponse](docs/CreateBreakTypeResponse.md)
 - [CreateCheckoutRequest](docs/CreateCheckoutRequest.md)
 - [CreateCheckoutResponse](docs/CreateCheckoutResponse.md)
 - [CreateCustomerCardRequest](docs/CreateCustomerCardRequest.md)
 - [CreateCustomerCardResponse](docs/CreateCustomerCardResponse.md)
 - [CreateCustomerRequest](docs/CreateCustomerRequest.md)
 - [CreateCustomerResponse](docs/CreateCustomerResponse.md)
 - [CreateMobileAuthorizationCodeRequest](docs/CreateMobileAuthorizationCodeRequest.md)
 - [CreateMobileAuthorizationCodeResponse](docs/CreateMobileAuthorizationCodeResponse.md)
 - [CreateOrderRequest](docs/CreateOrderRequest.md)
 - [CreateOrderRequestDiscount](docs/CreateOrderRequestDiscount.md)
 - [CreateOrderRequestLineItem](docs/CreateOrderRequestLineItem.md)
 - [CreateOrderRequestModifier](docs/CreateOrderRequestModifier.md)
 - [CreateOrderRequestTax](docs/CreateOrderRequestTax.md)
 - [CreateOrderResponse](docs/CreateOrderResponse.md)
 - [CreatePaymentRequest](docs/CreatePaymentRequest.md)
 - [CreatePaymentResponse](docs/CreatePaymentResponse.md)
 - [CreateRefundRequest](docs/CreateRefundRequest.md)
 - [CreateRefundResponse](docs/CreateRefundResponse.md)
 - [CreateShiftRequest](docs/CreateShiftRequest.md)
 - [CreateShiftResponse](docs/CreateShiftResponse.md)
 - [Currency](docs/Currency.md)
 - [Customer](docs/Customer.md)
 - [CustomerCreationSource](docs/CustomerCreationSource.md)
 - [CustomerCreationSourceFilter](docs/CustomerCreationSourceFilter.md)
 - [CustomerFilter](docs/CustomerFilter.md)
 - [CustomerGroupInfo](docs/CustomerGroupInfo.md)
 - [CustomerInclusionExclusion](docs/CustomerInclusionExclusion.md)
 - [CustomerPreferences](docs/CustomerPreferences.md)
 - [CustomerQuery](docs/CustomerQuery.md)
 - [CustomerSort](docs/CustomerSort.md)
 - [CustomerSortField](docs/CustomerSortField.md)
 - [DateRange](docs/DateRange.md)
 - [DayOfWeek](docs/DayOfWeek.md)
 - [DeleteBreakTypeRequest](docs/DeleteBreakTypeRequest.md)
 - [DeleteBreakTypeResponse](docs/DeleteBreakTypeResponse.md)
 - [DeleteCatalogObjectRequest](docs/DeleteCatalogObjectRequest.md)
 - [DeleteCatalogObjectResponse](docs/DeleteCatalogObjectResponse.md)
 - [DeleteCustomerCardRequest](docs/DeleteCustomerCardRequest.md)
 - [DeleteCustomerCardResponse](docs/DeleteCustomerCardResponse.md)
 - [DeleteCustomerRequest](docs/DeleteCustomerRequest.md)
 - [DeleteCustomerResponse](docs/DeleteCustomerResponse.md)
 - [DeleteShiftRequest](docs/DeleteShiftRequest.md)
 - [DeleteShiftResponse](docs/DeleteShiftResponse.md)
 - [Device](docs/Device.md)
 - [Employee](docs/Employee.md)
 - [EmployeeStatus](docs/EmployeeStatus.md)
 - [EmployeeWage](docs/EmployeeWage.md)
 - [Error](docs/Error.md)
 - [ErrorCategory](docs/ErrorCategory.md)
 - [ErrorCode](docs/ErrorCode.md)
 - [GetBreakTypeRequest](docs/GetBreakTypeRequest.md)
 - [GetBreakTypeResponse](docs/GetBreakTypeResponse.md)
 - [GetEmployeeWageRequest](docs/GetEmployeeWageRequest.md)
 - [GetEmployeeWageResponse](docs/GetEmployeeWageResponse.md)
 - [GetPaymentRefundRequest](docs/GetPaymentRefundRequest.md)
 - [GetPaymentRefundResponse](docs/GetPaymentRefundResponse.md)
 - [GetPaymentRequest](docs/GetPaymentRequest.md)
 - [GetPaymentResponse](docs/GetPaymentResponse.md)
 - [GetShiftRequest](docs/GetShiftRequest.md)
 - [GetShiftResponse](docs/GetShiftResponse.md)
 - [InventoryAdjustment](docs/InventoryAdjustment.md)
 - [InventoryAlertType](docs/InventoryAlertType.md)
 - [InventoryChange](docs/InventoryChange.md)
 - [InventoryChangeType](docs/InventoryChangeType.md)
 - [InventoryCount](docs/InventoryCount.md)
 - [InventoryPhysicalCount](docs/InventoryPhysicalCount.md)
 - [InventoryState](docs/InventoryState.md)
 - [InventoryTransfer](docs/InventoryTransfer.md)
 - [ItemVariationLocationOverrides](docs/ItemVariationLocationOverrides.md)
 - [ListAdditionalRecipientReceivableRefundsRequest](docs/ListAdditionalRecipientReceivableRefundsRequest.md)
 - [ListAdditionalRecipientReceivableRefundsResponse](docs/ListAdditionalRecipientReceivableRefundsResponse.md)
 - [ListAdditionalRecipientReceivablesRequest](docs/ListAdditionalRecipientReceivablesRequest.md)
 - [ListAdditionalRecipientReceivablesResponse](docs/ListAdditionalRecipientReceivablesResponse.md)
 - [ListBreakTypesRequest](docs/ListBreakTypesRequest.md)
 - [ListBreakTypesResponse](docs/ListBreakTypesResponse.md)
 - [ListCatalogRequest](docs/ListCatalogRequest.md)
 - [ListCatalogResponse](docs/ListCatalogResponse.md)
 - [ListCustomersRequest](docs/ListCustomersRequest.md)
 - [ListCustomersResponse](docs/ListCustomersResponse.md)
 - [ListEmployeeWagesRequest](docs/ListEmployeeWagesRequest.md)
 - [ListEmployeeWagesResponse](docs/ListEmployeeWagesResponse.md)
 - [ListEmployeesRequest](docs/ListEmployeesRequest.md)
 - [ListEmployeesResponse](docs/ListEmployeesResponse.md)
 - [ListLocationsRequest](docs/ListLocationsRequest.md)
 - [ListLocationsResponse](docs/ListLocationsResponse.md)
 - [ListPaymentRefundsRequest](docs/ListPaymentRefundsRequest.md)
 - [ListPaymentRefundsResponse](docs/ListPaymentRefundsResponse.md)
 - [ListPaymentsRequest](docs/ListPaymentsRequest.md)
 - [ListPaymentsResponse](docs/ListPaymentsResponse.md)
 - [ListRefundsRequest](docs/ListRefundsRequest.md)
 - [ListRefundsResponse](docs/ListRefundsResponse.md)
 - [ListTransactionsRequest](docs/ListTransactionsRequest.md)
 - [ListTransactionsResponse](docs/ListTransactionsResponse.md)
 - [ListWorkweekConfigsRequest](docs/ListWorkweekConfigsRequest.md)
 - [ListWorkweekConfigsResponse](docs/ListWorkweekConfigsResponse.md)
 - [Location](docs/Location.md)
 - [LocationCapability](docs/LocationCapability.md)
 - [LocationStatus](docs/LocationStatus.md)
 - [LocationType](docs/LocationType.md)
 - [MeasurementUnit](docs/MeasurementUnit.md)
 - [MeasurementUnitArea](docs/MeasurementUnitArea.md)
 - [MeasurementUnitCustom](docs/MeasurementUnitCustom.md)
 - [MeasurementUnitGeneric](docs/MeasurementUnitGeneric.md)
 - [MeasurementUnitLength](docs/MeasurementUnitLength.md)
 - [MeasurementUnitTime](docs/MeasurementUnitTime.md)
 - [MeasurementUnitUnitType](docs/MeasurementUnitUnitType.md)
 - [MeasurementUnitVolume](docs/MeasurementUnitVolume.md)
 - [MeasurementUnitWeight](docs/MeasurementUnitWeight.md)
 - [ModelBreak](docs/ModelBreak.md)
 - [Money](docs/Money.md)
 - [ObtainTokenRequest](docs/ObtainTokenRequest.md)
 - [ObtainTokenResponse](docs/ObtainTokenResponse.md)
 - [Order](docs/Order.md)
 - [OrderEntry](docs/OrderEntry.md)
 - [OrderFulfillment](docs/OrderFulfillment.md)
 - [OrderFulfillmentPickupDetails](docs/OrderFulfillmentPickupDetails.md)
 - [OrderFulfillmentPickupDetailsScheduleType](docs/OrderFulfillmentPickupDetailsScheduleType.md)
 - [OrderFulfillmentRecipient](docs/OrderFulfillmentRecipient.md)
 - [OrderFulfillmentShipmentDetails](docs/OrderFulfillmentShipmentDetails.md)
 - [OrderFulfillmentState](docs/OrderFulfillmentState.md)
 - [OrderFulfillmentType](docs/OrderFulfillmentType.md)
 - [OrderLineItem](docs/OrderLineItem.md)
 - [OrderLineItemAppliedDiscount](docs/OrderLineItemAppliedDiscount.md)
 - [OrderLineItemAppliedTax](docs/OrderLineItemAppliedTax.md)
 - [OrderLineItemDiscount](docs/OrderLineItemDiscount.md)
 - [OrderLineItemDiscountScope](docs/OrderLineItemDiscountScope.md)
 - [OrderLineItemDiscountType](docs/OrderLineItemDiscountType.md)
 - [OrderLineItemModifier](docs/OrderLineItemModifier.md)
 - [OrderLineItemTax](docs/OrderLineItemTax.md)
 - [OrderLineItemTaxScope](docs/OrderLineItemTaxScope.md)
 - [OrderLineItemTaxType](docs/OrderLineItemTaxType.md)
 - [OrderMoneyAmounts](docs/OrderMoneyAmounts.md)
 - [OrderQuantityUnit](docs/OrderQuantityUnit.md)
 - [OrderReturn](docs/OrderReturn.md)
 - [OrderReturnDiscount](docs/OrderReturnDiscount.md)
 - [OrderReturnLineItem](docs/OrderReturnLineItem.md)
 - [OrderReturnLineItemModifier](docs/OrderReturnLineItemModifier.md)
 - [OrderReturnServiceCharge](docs/OrderReturnServiceCharge.md)
 - [OrderReturnTax](docs/OrderReturnTax.md)
 - [OrderRoundingAdjustment](docs/OrderRoundingAdjustment.md)
 - [OrderServiceCharge](docs/OrderServiceCharge.md)
 - [OrderServiceChargeCalculationPhase](docs/OrderServiceChargeCalculationPhase.md)
 - [OrderSource](docs/OrderSource.md)
 - [OrderState](docs/OrderState.md)
 - [PayOrderRequest](docs/PayOrderRequest.md)
 - [PayOrderResponse](docs/PayOrderResponse.md)
 - [Payment](docs/Payment.md)
 - [PaymentRefund](docs/PaymentRefund.md)
 - [ProcessingFee](docs/ProcessingFee.md)
 - [Product](docs/Product.md)
 - [Refund](docs/Refund.md)
 - [RefundPaymentRequest](docs/RefundPaymentRequest.md)
 - [RefundPaymentResponse](docs/RefundPaymentResponse.md)
 - [RefundStatus](docs/RefundStatus.md)
 - [RegisterDomainRequest](docs/RegisterDomainRequest.md)
 - [RegisterDomainResponse](docs/RegisterDomainResponse.md)
 - [RegisterDomainResponseStatus](docs/RegisterDomainResponseStatus.md)
 - [RenewTokenRequest](docs/RenewTokenRequest.md)
 - [RenewTokenResponse](docs/RenewTokenResponse.md)
 - [RetrieveCatalogObjectRequest](docs/RetrieveCatalogObjectRequest.md)
 - [RetrieveCatalogObjectResponse](docs/RetrieveCatalogObjectResponse.md)
 - [RetrieveCustomerRequest](docs/RetrieveCustomerRequest.md)
 - [RetrieveCustomerResponse](docs/RetrieveCustomerResponse.md)
 - [RetrieveEmployeeRequest](docs/RetrieveEmployeeRequest.md)
 - [RetrieveEmployeeResponse](docs/RetrieveEmployeeResponse.md)
 - [RetrieveInventoryAdjustmentRequest](docs/RetrieveInventoryAdjustmentRequest.md)
 - [RetrieveInventoryAdjustmentResponse](docs/RetrieveInventoryAdjustmentResponse.md)
 - [RetrieveInventoryChangesRequest](docs/RetrieveInventoryChangesRequest.md)
 - [RetrieveInventoryChangesResponse](docs/RetrieveInventoryChangesResponse.md)
 - [RetrieveInventoryCountRequest](docs/RetrieveInventoryCountRequest.md)
 - [RetrieveInventoryCountResponse](docs/RetrieveInventoryCountResponse.md)
 - [RetrieveInventoryPhysicalCountRequest](docs/RetrieveInventoryPhysicalCountRequest.md)
 - [RetrieveInventoryPhysicalCountResponse](docs/RetrieveInventoryPhysicalCountResponse.md)
 - [RetrieveLocationRequest](docs/RetrieveLocationRequest.md)
 - [RetrieveLocationResponse](docs/RetrieveLocationResponse.md)
 - [RetrieveTransactionRequest](docs/RetrieveTransactionRequest.md)
 - [RetrieveTransactionResponse](docs/RetrieveTransactionResponse.md)
 - [RevokeTokenRequest](docs/RevokeTokenRequest.md)
 - [RevokeTokenResponse](docs/RevokeTokenResponse.md)
 - [SearchCatalogObjectsRequest](docs/SearchCatalogObjectsRequest.md)
 - [SearchCatalogObjectsResponse](docs/SearchCatalogObjectsResponse.md)
 - [SearchCustomersRequest](docs/SearchCustomersRequest.md)
 - [SearchCustomersResponse](docs/SearchCustomersResponse.md)
 - [SearchOrdersCustomerFilter](docs/SearchOrdersCustomerFilter.md)
 - [SearchOrdersDateTimeFilter](docs/SearchOrdersDateTimeFilter.md)
 - [SearchOrdersFilter](docs/SearchOrdersFilter.md)
 - [SearchOrdersFulfillmentFilter](docs/SearchOrdersFulfillmentFilter.md)
 - [SearchOrdersQuery](docs/SearchOrdersQuery.md)
 - [SearchOrdersRequest](docs/SearchOrdersRequest.md)
 - [SearchOrdersResponse](docs/SearchOrdersResponse.md)
 - [SearchOrdersSort](docs/SearchOrdersSort.md)
 - [SearchOrdersSortField](docs/SearchOrdersSortField.md)
 - [SearchOrdersSourceFilter](docs/SearchOrdersSourceFilter.md)
 - [SearchOrdersStateFilter](docs/SearchOrdersStateFilter.md)
 - [SearchShiftsRequest](docs/SearchShiftsRequest.md)
 - [SearchShiftsResponse](docs/SearchShiftsResponse.md)
 - [Shift](docs/Shift.md)
 - [ShiftFilter](docs/ShiftFilter.md)
 - [ShiftFilterStatus](docs/ShiftFilterStatus.md)
 - [ShiftQuery](docs/ShiftQuery.md)
 - [ShiftSort](docs/ShiftSort.md)
 - [ShiftSortField](docs/ShiftSortField.md)
 - [ShiftStatus](docs/ShiftStatus.md)
 - [ShiftWage](docs/ShiftWage.md)
 - [ShiftWorkday](docs/ShiftWorkday.md)
 - [ShiftWorkdayMatcher](docs/ShiftWorkdayMatcher.md)
 - [SortOrder](docs/SortOrder.md)
 - [SourceApplication](docs/SourceApplication.md)
 - [StandardUnitDescription](docs/StandardUnitDescription.md)
 - [StandardUnitDescriptionGroup](docs/StandardUnitDescriptionGroup.md)
 - [TaxCalculationPhase](docs/TaxCalculationPhase.md)
 - [TaxInclusionType](docs/TaxInclusionType.md)
 - [Tender](docs/Tender.md)
 - [TenderCardDetails](docs/TenderCardDetails.md)
 - [TenderCardDetailsEntryMethod](docs/TenderCardDetailsEntryMethod.md)
 - [TenderCardDetailsStatus](docs/TenderCardDetailsStatus.md)
 - [TenderCashDetails](docs/TenderCashDetails.md)
 - [TenderType](docs/TenderType.md)
 - [TimeRange](docs/TimeRange.md)
 - [Transaction](docs/Transaction.md)
 - [TransactionProduct](docs/TransactionProduct.md)
 - [UpdateBreakTypeRequest](docs/UpdateBreakTypeRequest.md)
 - [UpdateBreakTypeResponse](docs/UpdateBreakTypeResponse.md)
 - [UpdateCustomerRequest](docs/UpdateCustomerRequest.md)
 - [UpdateCustomerResponse](docs/UpdateCustomerResponse.md)
 - [UpdateItemModifierListsRequest](docs/UpdateItemModifierListsRequest.md)
 - [UpdateItemModifierListsResponse](docs/UpdateItemModifierListsResponse.md)
 - [UpdateItemTaxesRequest](docs/UpdateItemTaxesRequest.md)
 - [UpdateItemTaxesResponse](docs/UpdateItemTaxesResponse.md)
 - [UpdateOrderRequest](docs/UpdateOrderRequest.md)
 - [UpdateOrderResponse](docs/UpdateOrderResponse.md)
 - [UpdateShiftRequest](docs/UpdateShiftRequest.md)
 - [UpdateShiftResponse](docs/UpdateShiftResponse.md)
 - [UpdateWorkweekConfigRequest](docs/UpdateWorkweekConfigRequest.md)
 - [UpdateWorkweekConfigResponse](docs/UpdateWorkweekConfigResponse.md)
 - [UpsertCatalogObjectRequest](docs/UpsertCatalogObjectRequest.md)
 - [UpsertCatalogObjectResponse](docs/UpsertCatalogObjectResponse.md)
 - [V1AdjustInventoryRequest](docs/V1AdjustInventoryRequest.md)
 - [V1AdjustInventoryRequestAdjustmentType](docs/V1AdjustInventoryRequestAdjustmentType.md)
 - [V1ApplyFeeRequest](docs/V1ApplyFeeRequest.md)
 - [V1ApplyModifierListRequest](docs/V1ApplyModifierListRequest.md)
 - [V1BankAccount](docs/V1BankAccount.md)
 - [V1BankAccountType](docs/V1BankAccountType.md)
 - [V1CashDrawerEvent](docs/V1CashDrawerEvent.md)
 - [V1CashDrawerEventEventType](docs/V1CashDrawerEventEventType.md)
 - [V1CashDrawerShift](docs/V1CashDrawerShift.md)
 - [V1CashDrawerShiftEventType](docs/V1CashDrawerShiftEventType.md)
 - [V1Category](docs/V1Category.md)
 - [V1CreateCategoryRequest](docs/V1CreateCategoryRequest.md)
 - [V1CreateDiscountRequest](docs/V1CreateDiscountRequest.md)
 - [V1CreateEmployeeRoleRequest](docs/V1CreateEmployeeRoleRequest.md)
 - [V1CreateFeeRequest](docs/V1CreateFeeRequest.md)
 - [V1CreateItemRequest](docs/V1CreateItemRequest.md)
 - [V1CreateModifierListRequest](docs/V1CreateModifierListRequest.md)
 - [V1CreateModifierOptionRequest](docs/V1CreateModifierOptionRequest.md)
 - [V1CreatePageRequest](docs/V1CreatePageRequest.md)
 - [V1CreateRefundRequest](docs/V1CreateRefundRequest.md)
 - [V1CreateRefundRequestType](docs/V1CreateRefundRequestType.md)
 - [V1CreateVariationRequest](docs/V1CreateVariationRequest.md)
 - [V1DeleteCategoryRequest](docs/V1DeleteCategoryRequest.md)
 - [V1DeleteDiscountRequest](docs/V1DeleteDiscountRequest.md)
 - [V1DeleteFeeRequest](docs/V1DeleteFeeRequest.md)
 - [V1DeleteItemRequest](docs/V1DeleteItemRequest.md)
 - [V1DeleteModifierListRequest](docs/V1DeleteModifierListRequest.md)
 - [V1DeleteModifierOptionRequest](docs/V1DeleteModifierOptionRequest.md)
 - [V1DeletePageCellRequest](docs/V1DeletePageCellRequest.md)
 - [V1DeletePageRequest](docs/V1DeletePageRequest.md)
 - [V1DeleteTimecardRequest](docs/V1DeleteTimecardRequest.md)
 - [V1DeleteTimecardResponse](docs/V1DeleteTimecardResponse.md)
 - [V1DeleteVariationRequest](docs/V1DeleteVariationRequest.md)
 - [V1Discount](docs/V1Discount.md)
 - [V1DiscountColor](docs/V1DiscountColor.md)
 - [V1DiscountDiscountType](docs/V1DiscountDiscountType.md)
 - [V1Employee](docs/V1Employee.md)
 - [V1EmployeeRole](docs/V1EmployeeRole.md)
 - [V1EmployeeRolePermissions](docs/V1EmployeeRolePermissions.md)
 - [V1EmployeeStatus](docs/V1EmployeeStatus.md)
 - [V1Fee](docs/V1Fee.md)
 - [V1FeeAdjustmentType](docs/V1FeeAdjustmentType.md)
 - [V1FeeCalculationPhase](docs/V1FeeCalculationPhase.md)
 - [V1FeeInclusionType](docs/V1FeeInclusionType.md)
 - [V1FeeType](docs/V1FeeType.md)
 - [V1InventoryEntry](docs/V1InventoryEntry.md)
 - [V1Item](docs/V1Item.md)
 - [V1ItemColor](docs/V1ItemColor.md)
 - [V1ItemImage](docs/V1ItemImage.md)
 - [V1ItemType](docs/V1ItemType.md)
 - [V1ItemVisibility](docs/V1ItemVisibility.md)
 - [V1ListBankAccountsRequest](docs/V1ListBankAccountsRequest.md)
 - [V1ListBankAccountsResponse](docs/V1ListBankAccountsResponse.md)
 - [V1ListCashDrawerShiftsRequest](docs/V1ListCashDrawerShiftsRequest.md)
 - [V1ListCashDrawerShiftsResponse](docs/V1ListCashDrawerShiftsResponse.md)
 - [V1ListCategoriesRequest](docs/V1ListCategoriesRequest.md)
 - [V1ListCategoriesResponse](docs/V1ListCategoriesResponse.md)
 - [V1ListDiscountsRequest](docs/V1ListDiscountsRequest.md)
 - [V1ListDiscountsResponse](docs/V1ListDiscountsResponse.md)
 - [V1ListEmployeeRolesRequest](docs/V1ListEmployeeRolesRequest.md)
 - [V1ListEmployeeRolesResponse](docs/V1ListEmployeeRolesResponse.md)
 - [V1ListEmployeesRequest](docs/V1ListEmployeesRequest.md)
 - [V1ListEmployeesRequestStatus](docs/V1ListEmployeesRequestStatus.md)
 - [V1ListEmployeesResponse](docs/V1ListEmployeesResponse.md)
 - [V1ListFeesRequest](docs/V1ListFeesRequest.md)
 - [V1ListFeesResponse](docs/V1ListFeesResponse.md)
 - [V1ListInventoryRequest](docs/V1ListInventoryRequest.md)
 - [V1ListInventoryResponse](docs/V1ListInventoryResponse.md)
 - [V1ListItemsRequest](docs/V1ListItemsRequest.md)
 - [V1ListItemsResponse](docs/V1ListItemsResponse.md)
 - [V1ListLocationsRequest](docs/V1ListLocationsRequest.md)
 - [V1ListLocationsResponse](docs/V1ListLocationsResponse.md)
 - [V1ListModifierListsRequest](docs/V1ListModifierListsRequest.md)
 - [V1ListModifierListsResponse](docs/V1ListModifierListsResponse.md)
 - [V1ListOrdersRequest](docs/V1ListOrdersRequest.md)
 - [V1ListOrdersResponse](docs/V1ListOrdersResponse.md)
 - [V1ListPagesRequest](docs/V1ListPagesRequest.md)
 - [V1ListPagesResponse](docs/V1ListPagesResponse.md)
 - [V1ListPaymentsRequest](docs/V1ListPaymentsRequest.md)
 - [V1ListPaymentsResponse](docs/V1ListPaymentsResponse.md)
 - [V1ListRefundsRequest](docs/V1ListRefundsRequest.md)
 - [V1ListRefundsResponse](docs/V1ListRefundsResponse.md)
 - [V1ListSettlementsRequest](docs/V1ListSettlementsRequest.md)
 - [V1ListSettlementsRequestStatus](docs/V1ListSettlementsRequestStatus.md)
 - [V1ListSettlementsResponse](docs/V1ListSettlementsResponse.md)
 - [V1ListTimecardEventsRequest](docs/V1ListTimecardEventsRequest.md)
 - [V1ListTimecardEventsResponse](docs/V1ListTimecardEventsResponse.md)
 - [V1ListTimecardsRequest](docs/V1ListTimecardsRequest.md)
 - [V1ListTimecardsResponse](docs/V1ListTimecardsResponse.md)
 - [V1Merchant](docs/V1Merchant.md)
 - [V1MerchantAccountType](docs/V1MerchantAccountType.md)
 - [V1MerchantBusinessType](docs/V1MerchantBusinessType.md)
 - [V1MerchantLocationDetails](docs/V1MerchantLocationDetails.md)
 - [V1ModifierList](docs/V1ModifierList.md)
 - [V1ModifierListSelectionType](docs/V1ModifierListSelectionType.md)
 - [V1ModifierOption](docs/V1ModifierOption.md)
 - [V1Money](docs/V1Money.md)
 - [V1Order](docs/V1Order.md)
 - [V1OrderHistoryEntry](docs/V1OrderHistoryEntry.md)
 - [V1OrderHistoryEntryAction](docs/V1OrderHistoryEntryAction.md)
 - [V1OrderState](docs/V1OrderState.md)
 - [V1Page](docs/V1Page.md)
 - [V1PageCell](docs/V1PageCell.md)
 - [V1PageCellObjectType](docs/V1PageCellObjectType.md)
 - [V1PageCellPlaceholderType](docs/V1PageCellPlaceholderType.md)
 - [V1Payment](docs/V1Payment.md)
 - [V1PaymentDiscount](docs/V1PaymentDiscount.md)
 - [V1PaymentItemDetail](docs/V1PaymentItemDetail.md)
 - [V1PaymentItemization](docs/V1PaymentItemization.md)
 - [V1PaymentItemizationItemizationType](docs/V1PaymentItemizationItemizationType.md)
 - [V1PaymentModifier](docs/V1PaymentModifier.md)
 - [V1PaymentSurcharge](docs/V1PaymentSurcharge.md)
 - [V1PaymentSurchargeType](docs/V1PaymentSurchargeType.md)
 - [V1PaymentTax](docs/V1PaymentTax.md)
 - [V1PaymentTaxInclusionType](docs/V1PaymentTaxInclusionType.md)
 - [V1PhoneNumber](docs/V1PhoneNumber.md)
 - [V1Refund](docs/V1Refund.md)
 - [V1RefundType](docs/V1RefundType.md)
 - [V1RemoveFeeRequest](docs/V1RemoveFeeRequest.md)
 - [V1RemoveModifierListRequest](docs/V1RemoveModifierListRequest.md)
 - [V1RetrieveBankAccountRequest](docs/V1RetrieveBankAccountRequest.md)
 - [V1RetrieveBusinessRequest](docs/V1RetrieveBusinessRequest.md)
 - [V1RetrieveCashDrawerShiftRequest](docs/V1RetrieveCashDrawerShiftRequest.md)
 - [V1RetrieveEmployeeRequest](docs/V1RetrieveEmployeeRequest.md)
 - [V1RetrieveEmployeeRoleRequest](docs/V1RetrieveEmployeeRoleRequest.md)
 - [V1RetrieveItemRequest](docs/V1RetrieveItemRequest.md)
 - [V1RetrieveModifierListRequest](docs/V1RetrieveModifierListRequest.md)
 - [V1RetrieveOrderRequest](docs/V1RetrieveOrderRequest.md)
 - [V1RetrievePaymentRequest](docs/V1RetrievePaymentRequest.md)
 - [V1RetrieveSettlementRequest](docs/V1RetrieveSettlementRequest.md)
 - [V1RetrieveTimecardRequest](docs/V1RetrieveTimecardRequest.md)
 - [V1Settlement](docs/V1Settlement.md)
 - [V1SettlementEntry](docs/V1SettlementEntry.md)
 - [V1SettlementEntryType](docs/V1SettlementEntryType.md)
 - [V1SettlementStatus](docs/V1SettlementStatus.md)
 - [V1Tender](docs/V1Tender.md)
 - [V1TenderCardBrand](docs/V1TenderCardBrand.md)
 - [V1TenderEntryMethod](docs/V1TenderEntryMethod.md)
 - [V1TenderType](docs/V1TenderType.md)
 - [V1Timecard](docs/V1Timecard.md)
 - [V1TimecardEvent](docs/V1TimecardEvent.md)
 - [V1TimecardEventEventType](docs/V1TimecardEventEventType.md)
 - [V1UpdateCategoryRequest](docs/V1UpdateCategoryRequest.md)
 - [V1UpdateDiscountRequest](docs/V1UpdateDiscountRequest.md)
 - [V1UpdateEmployeeRequest](docs/V1UpdateEmployeeRequest.md)
 - [V1UpdateEmployeeRoleRequest](docs/V1UpdateEmployeeRoleRequest.md)
 - [V1UpdateFeeRequest](docs/V1UpdateFeeRequest.md)
 - [V1UpdateItemRequest](docs/V1UpdateItemRequest.md)
 - [V1UpdateModifierListRequest](docs/V1UpdateModifierListRequest.md)
 - [V1UpdateModifierListRequestSelectionType](docs/V1UpdateModifierListRequestSelectionType.md)
 - [V1UpdateModifierOptionRequest](docs/V1UpdateModifierOptionRequest.md)
 - [V1UpdateOrderRequest](docs/V1UpdateOrderRequest.md)
 - [V1UpdateOrderRequestAction](docs/V1UpdateOrderRequestAction.md)
 - [V1UpdatePageCellRequest](docs/V1UpdatePageCellRequest.md)
 - [V1UpdatePageRequest](docs/V1UpdatePageRequest.md)
 - [V1UpdateTimecardRequest](docs/V1UpdateTimecardRequest.md)
 - [V1UpdateVariationRequest](docs/V1UpdateVariationRequest.md)
 - [V1Variation](docs/V1Variation.md)
 - [V1VariationInventoryAlertType](docs/V1VariationInventoryAlertType.md)
 - [V1VariationPricingType](docs/V1VariationPricingType.md)
 - [VoidTransactionRequest](docs/VoidTransactionRequest.md)
 - [VoidTransactionResponse](docs/VoidTransactionResponse.md)
 - [WebhookEvents](docs/WebhookEvents.md)
 - [Weekday](docs/Weekday.md)
 - [WorkweekConfig](docs/WorkweekConfig.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### oauth2

- **Type**: OAuth
- **Flow**: accessCode
- **Authorization URL**: https://connect.squareup.com/oauth2/authorize
- **Scopes**: 
  - BANK_ACCOUNTS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to bank account information associated with the targeted Square account. For example, to call the Connect v1 ListBankAccounts endpoint.
  - CUSTOMERS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to customer information. For example, to call the ListCustomers endpoint.
  - CUSTOMERS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to customer information. For example, to create and update customer profiles.
  - EMPLOYEES_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to employee profile information. For example, to call the Connect v1 Employees API.
  - EMPLOYEES_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to employee profile information. For example, to create and modify employee profiles.
  - INVENTORY_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to inventory information. For example, to call the RetrieveInventoryCount endpoint.
  - INVENTORY_WRITE: __HTTP Method__:  &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to inventory information. For example, to call the BatchChangeInventory endpoint.
  - ITEMS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to product catalog information. For example, to get an  item or a list of items.
  - ITEMS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to product catalog information. For example, to modify or add to a product catalog.
  - MERCHANT_PROFILE_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to business and location information. For example, to obtain a location ID for subsequent activity.
  - ORDERS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to order information. For example, to call the BatchRetrieveOrders endpoint.
  - ORDERS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to order information. For example, to call the CreateCheckout endpoint.
  - PAYMENTS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to transaction and refund information. For example, to call the RetrieveTransaction endpoint.
  - PAYMENTS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to transaction and refunds information. For example, to process payments with the Transactions or Checkout API.
  - PAYMENTS_WRITE_ADDITIONAL_RECIPIENTS: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Allow third party applications to deduct a portion of each transaction amount. __Required__ to use multiparty transaction functionality with the Transactions API.
  - PAYMENTS_WRITE_IN_PERSON: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to transaction and refunds information. For example, to process in-person payments.
  - SETTLEMENTS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to settlement (deposit) information. For example, to call the Connect v1 ListSettlements endpoint.
  - TIMECARDS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to employee timecard information. For example, to call the Connect v1 ListTimecards endpoint.
  - TIMECARDS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to employee timecard information. For example, to create and modify timecards.
  - TIMECARDS_SETTINGS_READ: __HTTP Method__: &#x60;GET&#x60;  Grants read access to employee timecard settings information. For example, to call the GetBreakType endpoint.
  - TIMECARDS_SETTINGS_WRITE: __HTTP Method__: &#x60;POST&#x60;, &#x60;PUT&#x60;, &#x60;DELETE&#x60;  Grants write access to employee timecard settings information. For example, to call the UpdateBreakType endpoint.

### oauth2ClientSecret

- **Type**: API key
- **API key parameter name**: Authorization
- **Location**: HTTP header


## Pagination of V1 Endpoints

V1 Endpoints return pagination information via HTTP headers. In order to obtain
response headers and extract the `batch_token` parameter you will need to follow
the following steps:

1. Use the full information endpoint methods of each API to get the response HTTP
Headers. They are named as their simple counterpart with a `WithHttpInfo` suffix.
Hence `listEmployeeRoles` would be called `listEmployeeRolesWithHttpInfo`. This
method returns a `CompleteResponse` object with the response data deserialized along
with a helper to retrieve the token if present.

2. Use `String batchToken = completeResponse.getBatchToken();`
to extract the token and proceed to get the following page if a token is present.

### Example

```java
// Import classes:
//import .ApiClient;
//import .ApiException;
//import .Configuration;
//import .CompleteResponse;
//import .auth.*;
//import .api.V1EmployeesApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: oauth2
OAuth oauth2 = (OAuth) defaultClient.getAuthentication("oauth2");
oauth2.setAccessToken("YOUR ACCESS TOKEN");

V1EmployeesApi apiInstance = new V1EmployeesApi();
String order = "order_example"; // String | The order in which employees are listed in the response, based on their created_at field.Default value: ASC
Integer limit = 56; // Integer | The maximum integer number of employee entities to return in a single response. Default 100, maximum 200.
String batchToken = null;
try {
    do {
        CompleteResponse<List<V1EmployeeRole>> completeResponse = apiInstance.listEmployeeRoles(order, limit, batchToken);
        System.out.println(completeResponse.getData());

        batchToken = completeResponse.getBatchToken();
    } while (batchToken != null);
} catch (ApiException e) {
    System.err.println("Exception when calling V1EmployeesApi#listEmployeeRoles");
    e.printStackTrace();
}
```

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

developers@squareup.com




[//]: # "Link anchor definitions"
[Square Logo]: https://docs.connect.squareup.com/images/github/github-square-logo.png
