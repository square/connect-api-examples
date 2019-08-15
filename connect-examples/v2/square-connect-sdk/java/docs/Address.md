
# Address

### Description

Represents a physical address.

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**addressLine1** | **String** | The first line of the address.  Fields that start with &#x60;address_line&#x60; provide the address&#39;s most specific details, like street number, street name, and building name. They do *not* provide less specific details like city, state/province, or country (these details are provided in other fields). |  [optional]
**addressLine2** | **String** | The second line of the address, if any. |  [optional]
**addressLine3** | **String** | The third line of the address, if any. |  [optional]
**locality** | **String** | The city or town of the address. |  [optional]
**sublocality** | **String** | A civil region within the address&#39;s &#x60;locality&#x60;, if any. |  [optional]
**sublocality2** | **String** | A civil region within the address&#39;s &#x60;sublocality&#x60;, if any. |  [optional]
**sublocality3** | **String** | A civil region within the address&#39;s &#x60;sublocality_2&#x60;, if any. |  [optional]
**administrativeDistrictLevel1** | **String** | A civil entity within the address&#39;s country. In the US, this is the state. |  [optional]
**administrativeDistrictLevel2** | **String** | A civil entity within the address&#39;s &#x60;administrative_district_level_1&#x60;. In the US, this is the county. |  [optional]
**administrativeDistrictLevel3** | **String** | A civil entity within the address&#39;s &#x60;administrative_district_level_2&#x60;, if any. |  [optional]
**postalCode** | **String** | The address&#39;s postal code. |  [optional]
**country** | **String** | The address&#39;s country, in ISO 3166-1-alpha-2 format. See [Country](#type-country) for possible values |  [optional]
**firstName** | **String** | Optional first name when it&#39;s representing recipient. |  [optional]
**lastName** | **String** | Optional last name when it&#39;s representing recipient. |  [optional]
**organization** | **String** | Optional organization name when it&#39;s representing recipient. |  [optional]



