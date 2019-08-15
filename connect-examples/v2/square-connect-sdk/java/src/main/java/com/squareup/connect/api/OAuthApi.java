package com.squareup.connect.api;

import com.squareup.connect.ApiException;
import com.squareup.connect.ApiClient;
import com.squareup.connect.Configuration;
import com.squareup.connect.Pair;
import com.squareup.connect.CompleteResponse;

import javax.ws.rs.core.GenericType;

import com.squareup.connect.models.ObtainTokenRequest;
import com.squareup.connect.models.ObtainTokenResponse;
import com.squareup.connect.models.RenewTokenRequest;
import com.squareup.connect.models.RenewTokenResponse;
import com.squareup.connect.models.RevokeTokenRequest;
import com.squareup.connect.models.RevokeTokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OAuthApi {
  private ApiClient apiClient;

  public OAuthApi() {
    this(Configuration.getDefaultApiClient());
  }

  public OAuthApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * ObtainToken
   * Returns an OAuth access token.   The endpoint supports distinct methods of obtaining OAuth access tokens.  Applications specify a method by adding the &#x60;grant_type&#x60; parameter  in the request and also provide relevant information.  For more information, see [OAuth access token management](/authz/oauth/how-it-works#oauth-access-token-management).   __Note:__ Regardless of the method application specified, the endpoint always returns two items; an OAuth access token and  a refresh token in the response.   __OAuth tokens should only live on secure servers. Application clients should never interact directly with OAuth tokens__.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return ObtainTokenResponse
   * @throws ApiException if fails to make API call
   */  public ObtainTokenResponse obtainToken(ObtainTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling obtainToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/token";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ObtainTokenResponse> localVarReturnType = new GenericType<ObtainTokenResponse>() {};
    CompleteResponse<ObtainTokenResponse> completeResponse = (CompleteResponse<ObtainTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * ObtainToken
   * Returns an OAuth access token.   The endpoint supports distinct methods of obtaining OAuth access tokens.  Applications specify a method by adding the &#x60;grant_type&#x60; parameter  in the request and also provide relevant information.  For more information, see [OAuth access token management](/authz/oauth/how-it-works#oauth-access-token-management).   __Note:__ Regardless of the method application specified, the endpoint always returns two items; an OAuth access token and  a refresh token in the response.   __OAuth tokens should only live on secure servers. Application clients should never interact directly with OAuth tokens__.
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<ObtainTokenResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<ObtainTokenResponse>obtainTokenWithHttpInfo(ObtainTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling obtainToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/token";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<ObtainTokenResponse> localVarReturnType = new GenericType<ObtainTokenResponse>() {};
    return (CompleteResponse<ObtainTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RenewToken
   * &#x60;RenewToken&#x60; is deprecated. For information about refreshing OAuth access tokens, see  [Renew OAuth Token](https://developer.squareup.com/docs/oauth-api/cookbook/renew-oauth-tokens).   Renews an OAuth access token before it expires.  OAuth access tokens besides your application&#39;s personal access token expire after __30 days__. You can also renew expired tokens within __15 days__ of their expiration. You cannot renew an access token that has been expired for more than 15 days. Instead, the associated user must re-complete the OAuth flow from the beginning.  __Important:__ The &#x60;Authorization&#x60; header for this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Client APPLICATION_SECRET &#x60;&#x60;&#x60;  Replace &#x60;APPLICATION_SECRET&#x60; with the application secret on the Credentials page in the [application dashboard](https://connect.squareup.com/apps).
   * @param clientId Your application&#39;s ID, available from the [application dashboard](https://connect.squareup.com/apps). (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return RenewTokenResponse
   * @throws ApiException if fails to make API call
   */
  @Deprecated
  public RenewTokenResponse renewToken(String clientId, RenewTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'clientId' is set
    if (clientId == null) {
      throw new ApiException(400, "Missing the required parameter 'clientId' when calling renewToken");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling renewToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/clients/{client_id}/access-token/renew"
      .replaceAll("\\{" + "client_id" + "\\}", apiClient.escapeString(clientId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2ClientSecret" };

    GenericType<RenewTokenResponse> localVarReturnType = new GenericType<RenewTokenResponse>() {};
    CompleteResponse<RenewTokenResponse> completeResponse = (CompleteResponse<RenewTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RenewToken
   * &#x60;RenewToken&#x60; is deprecated. For information about refreshing OAuth access tokens, see  [Renew OAuth Token](https://developer.squareup.com/docs/oauth-api/cookbook/renew-oauth-tokens).   Renews an OAuth access token before it expires.  OAuth access tokens besides your application&#39;s personal access token expire after __30 days__. You can also renew expired tokens within __15 days__ of their expiration. You cannot renew an access token that has been expired for more than 15 days. Instead, the associated user must re-complete the OAuth flow from the beginning.  __Important:__ The &#x60;Authorization&#x60; header for this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Client APPLICATION_SECRET &#x60;&#x60;&#x60;  Replace &#x60;APPLICATION_SECRET&#x60; with the application secret on the Credentials page in the [application dashboard](https://connect.squareup.com/apps).
   * @param clientId Your application&#39;s ID, available from the [application dashboard](https://connect.squareup.com/apps). (required)
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<RenewTokenResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RenewTokenResponse>renewTokenWithHttpInfo(String clientId, RenewTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'clientId' is set
    if (clientId == null) {
      throw new ApiException(400, "Missing the required parameter 'clientId' when calling renewToken");
    }

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling renewToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/clients/{client_id}/access-token/renew"
      .replaceAll("\\{" + "client_id" + "\\}", apiClient.escapeString(clientId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2ClientSecret" };

    GenericType<RenewTokenResponse> localVarReturnType = new GenericType<RenewTokenResponse>() {};
    return (CompleteResponse<RenewTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * RevokeToken
   * Revokes an access token generated with the OAuth flow.  If an account has more than one OAuth access token for your application, this endpoint revokes all of them, regardless of which token you specify. When an OAuth access token is revoked, all of the active subscriptions associated with that OAuth token are canceled immediately.  __Important:__ The &#x60;Authorization&#x60; header for this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Client APPLICATION_SECRET &#x60;&#x60;&#x60;  Replace &#x60;APPLICATION_SECRET&#x60; with the application secret on the Credentials page in the [application dashboard](https://connect.squareup.com/apps).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return RevokeTokenResponse
   * @throws ApiException if fails to make API call
   */  public RevokeTokenResponse revokeToken(RevokeTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling revokeToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/revoke";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();
    localVarHeaderParams.put("Square-Version", "2019-08-14");



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2ClientSecret" };

    GenericType<RevokeTokenResponse> localVarReturnType = new GenericType<RevokeTokenResponse>() {};
    CompleteResponse<RevokeTokenResponse> completeResponse = (CompleteResponse<RevokeTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    return completeResponse.getData();
      }

  /**
   * RevokeToken
   * Revokes an access token generated with the OAuth flow.  If an account has more than one OAuth access token for your application, this endpoint revokes all of them, regardless of which token you specify. When an OAuth access token is revoked, all of the active subscriptions associated with that OAuth token are canceled immediately.  __Important:__ The &#x60;Authorization&#x60; header for this endpoint must have the following format:  &#x60;&#x60;&#x60; Authorization: Client APPLICATION_SECRET &#x60;&#x60;&#x60;  Replace &#x60;APPLICATION_SECRET&#x60; with the application secret on the Credentials page in the [application dashboard](https://connect.squareup.com/apps).
   * @param body An object containing the fields to POST for the request.  See the corresponding object definition for field details. (required)
   * @return CompleteResponse<RevokeTokenResponse>
   * @throws ApiException if fails to make API call
   */
  public CompleteResponse<RevokeTokenResponse>revokeTokenWithHttpInfo(RevokeTokenRequest body) throws ApiException {
    Object localVarPostBody = body;

    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling revokeToken");
    }

    // create path and map variables
    String localVarPath = "/oauth2/revoke";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();




    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "oauth2ClientSecret" };

    GenericType<RevokeTokenResponse> localVarReturnType = new GenericType<RevokeTokenResponse>() {};
    return (CompleteResponse<RevokeTokenResponse>)apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
}
