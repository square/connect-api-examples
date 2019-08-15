
# Employee

### Description

An employee created in the **Square Dashboard** account of a business.  Used by the Labor API.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | UUID for this &#x60;Employee&#x60;. |  [optional]
**firstName** | **String** | Given (first) name of the employee. |  [optional]
**lastName** | **String** | Family (last) name of the employee |  [optional]
**email** | **String** | Email of the employee |  [optional]
**phoneNumber** | **String** | Phone number of the employee in E.164 format, i.e. \&quot;+12125554250\&quot; |  [optional]
**locationIds** | **List&lt;String&gt;** | A list of location IDs where this employee has access. |  [optional]
**status** | **String** | Specifies the status of the employee being fetched. See [EmployeeStatus](#type-employeestatus) for possible values |  [optional]
**createdAt** | **String** | A read-only timestamp in RFC 3339 format. |  [optional]
**updatedAt** | **String** | A read-only timestamp in RFC 3339 format. |  [optional]



