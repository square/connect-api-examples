
# OrderFulfillmentRecipient

### Description

Contains information on the recipient of a fulfillment.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**customerId** | **String** | The Customer ID of the customer associated with the fulfillment.  If &#x60;customer_id&#x60; is provided, the fulfillment recipient&#39;s &#x60;display_name&#x60;, &#x60;email_address&#x60;, and &#x60;phone_number&#x60; are automatically populated from the targeted customer profile. If these fields are set in the request, the request values will override the information from the customer profile. If the targeted customer profile does not contain the necessary information and these fields are left unset, the request will result in an error. |  [optional]
**displayName** | **String** | The display name of the fulfillment recipient.  If provided, overrides the value pulled from the customer profile indicated by &#x60;customer_id&#x60;. |  [optional]
**emailAddress** | **String** | The email address of the fulfillment recipient.  If provided, overrides the value pulled from the customer profile indicated by &#x60;customer_id&#x60;. |  [optional]
**phoneNumber** | **String** | The phone number of the fulfillment recipient.  If provided, overrides the value pulled from the customer profile indicated by &#x60;customer_id&#x60;. |  [optional]
**address** | [**Address**](Address.md) | The address of the fulfillment recipient.  If provided, overrides the value pulled from the customer profile indicated by &#x60;customer_id&#x60;. |  [optional]



