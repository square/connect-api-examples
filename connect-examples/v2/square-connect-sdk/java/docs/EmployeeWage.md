
# EmployeeWage

### Description

The hourly wage rate that an employee will earn on a `Shift` for doing the job specified by the `title` property of this object.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** | UUID for this object. |  [optional]
**employeeId** | **String** | The &#x60;Employee&#x60; that this wage is assigned to. | 
**title** | **String** | The job title that this wage relates to. |  [optional]
**hourlyRate** | [**Money**](Money.md) | Can be a custom-set hourly wage or the calculated effective hourly wage based on annual wage and hours worked per week. |  [optional]



