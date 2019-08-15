
# CatalogTimePeriod

### Description

Represents a time period - either a single period or a repeating period.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**event** | **String** | An iCalendar (RFC5545) [event](https://tools.ietf.org/html/rfc5545#section-3.6.1), which specifies the name, timing, duration and recurrence of this time period.  Example:  &#x60;&#x60;&#x60; DTSTART:20190707T180000 DURATION:P2H RRULE:FREQ&#x3D;WEEKLY;BYDAY&#x3D;MO,WE,FR &#x60;&#x60;&#x60;  Only &#x60;SUMMARY&#x60;, &#x60;DTSTART&#x60;, &#x60;DURATION&#x60; and &#x60;RRULE&#x60; fields are supported. &#x60;DTSTART&#x60; must be in local (unzoned) time format. Note that while &#x60;BEGIN:VEVENT&#x60; and &#x60;END:VEVENT&#x60; is not required in the request. The response will always include them. |  [optional]



