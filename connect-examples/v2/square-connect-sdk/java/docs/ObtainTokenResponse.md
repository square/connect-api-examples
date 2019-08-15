
# ObtainTokenResponse

### Description



## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**accessToken** | **String** | A valid OAuth access token. OAuth access tokens are 64 bytes long. Provide the access token in a header with every request to Connect API endpoints. See the [Build with OAuth](/authz/oauth/build-with-the-api) guide for more information. |  [optional]
**tokenType** | **String** | This value is always _bearer_. |  [optional]
**expiresAt** | **String** | The date when access_token expires, in [ISO 8601](http://www.iso.org/iso/home/standards/iso8601.htm) format. |  [optional]
**merchantId** | **String** | The ID of the authorizing merchant&#39;s business. |  [optional]
**subscriptionId** | **String** | __LEGACY FIELD__. The ID of a subscription plan the merchant signed up for. Only present if the merchant signed up for a subscription during authorization. |  [optional]
**planId** | **String** | __LEGACY FIELD__. The ID of the subscription plan the merchant signed up for. Only present if the merchant signed up for a subscription during authorization. |  [optional]
**idToken** | **String** | Then OpenID token belonging to this this person. Only present if the OPENID scope is included in the authorize request. |  [optional]
**refreshToken** | **String** | A refresh token. For more information, see [OAuth access token management](/authz/oauth/how-it-works#oauth-access-token-management). |  [optional]



