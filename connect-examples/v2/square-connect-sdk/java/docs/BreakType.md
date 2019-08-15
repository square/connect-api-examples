
# BreakType

### Description

A defined break template that sets an expectation for possible `Break`  instances on a `Shift`.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | UUID for this object. |  [optional]
**locationId** | **String** | The ID of the business location this type of break applies to. | 
**breakName** | **String** | A human-readable name for this type of break. Will be displayed to employees in Square products. | 
**expectedDuration** | **String** | Format: RFC-3339 P[n]Y[n]M[n]DT[n]H[n]M[n]S. The expected length of this break. Precision below minutes is truncated. | 
**isPaid** | **Boolean** | Whether this break counts towards time worked for compensation purposes. | 
**version** | **Integer** | Used for resolving concurrency issues; request will fail if version provided does not match server version at time of request. If a value is not provided, Square&#39;s servers execute a \&quot;blind\&quot; write; potentially  overwriting another writer&#39;s data. |  [optional]
**createdAt** | **String** | A read-only timestamp in RFC 3339 format. |  [optional]
**updatedAt** | **String** | A read-only timestamp in RFC 3339 format. |  [optional]



