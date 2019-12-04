The Square Connect v2 API requires OAuth scopes with every call to a Connect endpoint. The following tables provided the needed permissions (scopes) for each endpoint in a Connect v2 service.

## CheckoutService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|CreateCheckout|ORDERS_WRITE, PAYMENTS_WRITE|

## CustomersService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|CreateCustomer|CUSTOMERS_WRITE|
|UpdateCustomer|CUSTOMERS_WRITE|
|ListCustomers|CUSTOMERS_READ|
|SearchCustomers|CUSTOMERS_READ|
|RetrieveCustomer|CUSTOMERS_READ|
|DeleteCustomer|CUSTOMERS_WRITE|
|CreateCustomerCard|CUSTOMERS_WRITE|
|DeleteCustomerCard|CUSTOMERS_WRITE|

## EmployeesService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|RetrieveEmployee|EMPLOYEES_READ|
|ListEmployees|EMPLOYEES_READ|

## LaborService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|CreateBreakType|TIMECARDS_SETTINGS_WRITE|
|GetBreakType|TIMECARDS_SETTINGS_READ|
|ListBreakTypes|TIMECARDS_SETTINGS_READ|
|UpdateBreakType|TIMECARDS_SETTINGS_READ, TIMECARDS_SETTINGS_WRITE|
|DeleteBreakType|TIMECARDS_SETTINGS_WRITE|
|GetEmployeeWage|EMPLOYEES_READ|
|ListEmployeeWages|EMPLOYEES_READ|
|SearchShifts|TIMECARDS_READ|
|GetShift|TIMECARDS_READ|
|CreateShift|TIMECARDS_WRITE|
|UpdateShift|TIMECARDS_WRITE, TIMECARDS_READ|
|DeleteShift|TIMECARDS_SETTINGS_WRITE|
|UpdateWorkweekConfig|TIMECARDS_SETTINGS_READ, TIMECARDS_SETTINGS_WRITE|
|ListWorkweekConfigs|TIMECARDS_SETTINGS_READ|

## LocationsService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|ListLocations|MERCHANT_PROFILE_READ|
|RetrieveLocation|MERCHANT_PROFILE_READ|
|UpdateLocation|MERCHANT_PROFILE_WRITE|
|CreateLocation|MERCHANT_PROFILE_WRITE|

## MerchantsService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|ListMerchants|MERCHANT_PROFILE_READ|
|RetrieveMerchant|MERCHANT_PROFILE_READ|

## MobileAuthorizationService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|CreateMobileAuthorizationCode|PAYMENTS_WRITE_IN_PERSON|

## OrderService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|SearchOrders|ORDERS_READ|
|CreateOrder|ORDERS_WRITE|
|UpdateOrder|ORDERS_WRITE|
|BatchRetrieveOrders|ORDERS_READ|
|PayOrder|ORDERS_WRITE, PAYMENTS_WRITE|

## PaymentsService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|CreatePayment|PAYMENTS_WRITE|
|GetPayment|PAYMENTS_READ|
|CancelPayment|PAYMENTS_WRITE|
|CancelPaymentByIdempotencyKey|PAYMENTS_WRITE|
|CompletePayment|PAYMENTS_WRITE|
|ListPayments|PAYMENTS_READ|

## RefundsService
|Endpoint|Permissions|
|:-------------|:-------------------------------------------------------------|
|RefundPayment|PAYMENTS_WRITE|
|GetPaymentRefund|PAYMENTS_READ|
|ListPaymentRefunds|PAYMENTS_READ|
