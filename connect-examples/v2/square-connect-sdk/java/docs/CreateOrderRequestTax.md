
# CreateOrderRequestTax

### Description

__Deprecated__: Please use the [OrderLineItemTax](#type-orderlineitemtax) type in the order field of [CreateOrderRequest](#type-createorderrequest) instead.  Represents a tax that can apply to either a single line item or an entire order.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**catalogObjectId** | **String** | Only used for catalog taxes. The catalog object ID of an existing [CatalogTax](#type-catalogtax).  Do not provide a value for this field if you provide values in other fields for an ad hoc tax. |  [optional]
**name** | **String** | Only used for ad hoc taxes. The tax&#39;s name.  Do not provide a value for this field if you set &#x60;catalog_object_id&#x60;. |  [optional]
**type** | **String** | Only used for ad hoc taxes. Indicates the calculation method used to apply the line item tax.  Default: &#x60;ADDITIVE&#x60;; See [OrderLineItemTaxType](#type-orderlineitemtaxtype) for possible values. See [OrderLineItemTaxType](#type-orderlineitemtaxtype) for possible values |  [optional]
**percentage** | **String** | Only used for ad hoc taxes. The percentage of the tax, as a string representation of a decimal number.  A value of &#x60;7.25&#x60; corresponds to a percentage of 7.25%. This value range between 0.0 up to 100.0 |  [optional]



