
# Error

### Description

Represents an error encountered during a request to the Connect API.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**category** | **String** | The high-level category for the error. See [ErrorCategory](#type-errorcategory) See [ErrorCategory](#type-errorcategory) for possible values | 
**code** | **String** | The specific code of the error. See [ErrorCode](#type-errorcode) for possible See [ErrorCode](#type-errorcode) for possible values | 
**detail** | **String** | A human-readable description of the error for debugging purposes. |  [optional]
**field** | **String** | The name of the field provided in the original request (if any) that the error pertains to. |  [optional]



