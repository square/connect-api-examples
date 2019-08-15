
# V1EmployeeRole

### Description

V1EmployeeRole

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | The role&#39;s unique ID, Can only be set by Square. |  [optional]
**name** | **String** | The role&#39;s merchant-defined name. | 
**permissions** | **List&lt;String&gt;** | The role&#39;s permissions. See [V1EmployeeRolePermissions](#type-v1employeerolepermissions) for possible values | 
**isOwner** | **Boolean** | If true, employees with this role have all permissions, regardless of the values indicated in permissions. |  [optional]
**createdAt** | **String** | The time when the employee entity was created, in ISO 8601 format. Is set by Square when the Role is created. |  [optional]
**updatedAt** | **String** | The time when the employee entity was most recently updated, in ISO 8601 format. Is set by Square when the Role updated. |  [optional]



