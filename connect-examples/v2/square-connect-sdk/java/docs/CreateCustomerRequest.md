
# CreateCustomerRequest

### Description

Defines the body parameters that can be provided in a request to the CreateCustomer endpoint.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**idempotencyKey** | **String** | The idempotency key for the request. See the [Idempotency](/basics/api101/idempotency) guide for more information. |  [optional]
**givenName** | **String** | The customer&#39;s given (i.e., first) name. |  [optional]
**familyName** | **String** | The customer&#39;s family (i.e., last) name. |  [optional]
**companyName** | **String** | The name of the customer&#39;s company. |  [optional]
**nickname** | **String** | A nickname for the customer. |  [optional]
**emailAddress** | **String** | The customer&#39;s email address. |  [optional]
**address** | [**Address**](Address.md) | The customer&#39;s physical address. |  [optional]
**phoneNumber** | **String** | The customer&#39;s phone number. |  [optional]
**referenceId** | **String** | An optional second ID you can set to associate the customer with an entity in another system. |  [optional]
**note** | **String** | An optional note to associate with the customer. |  [optional]
**birthday** | **String** | The customer birthday in RFC-3339 format. Year is optional, timezone and times are not allowed. Example: &#x60;0000-09-01T00:00:00-00:00&#x60; for a birthday on September 1st. &#x60;1998-09-01T00:00:00-00:00&#x60; for a birthday on September 1st 1998. |  [optional]



