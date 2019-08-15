
# SearchOrdersCustomerFilter

### Description

Filter based on Order `customer_id` and any Tender `customer_id` associated with the Order. Does not filter based on the [FulfillmentRecipient](#type-orderfulfillmentrecipient) `customer_id`.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**customerIds** | **List&lt;String&gt;** | List of customer IDs to filter by.  Max: 10 customer IDs. |  [optional]



