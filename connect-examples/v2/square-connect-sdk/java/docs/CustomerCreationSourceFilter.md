
# CustomerCreationSourceFilter

### Description

Creation source filter.  If one or more creation sources are set, customer profiles are included in, or excluded from, the result if they match at least one of the filter criteria.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**values** | **List&lt;String&gt;** | The list of creation sources used as filtering criteria. See [CustomerCreationSource](#type-customercreationsource) for possible values |  [optional]
**rule** | **String** | Indicates whether a customer profile matching the filter criteria should be included in the result or excluded from the result. Default: &#x60;INCLUDE&#x60;. See [CustomerInclusionExclusion](#type-customerinclusionexclusion) for possible values |  [optional]



