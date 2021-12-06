# In App Payment Demo for Android

This is an Android demo showcasing Square's offering in super simple in-app payment option. The
endpoints can be found in `services/InAppPaymentService.kt` file. All of the UI code is
in `MainActivity.kt` file.

This demo usages:
* Hilt - for di.
* Compose - for rendering UI.
* ViewModel - for persisting state.
* Retrofit - for APIs.
* Moshi - for JSON serialization/deserialization.
* Coroutines - for non-blocking work.

## Getting Started with In-App Payments

1. Add APIs (see `InAppPaymentService.kt` and `ApiDataModels.kt` for further details)

```kotlin
interface InAppPaymentService {

  @GET("/get-upgrades")
  suspend fun getUpgrades(): GetUpgradesResponse

  @POST("/generate-upgrade-link")
  suspend fun generateUpgradeLinks(
    @Body request: GenerateUpgradeLinksRequest
  ): UpgradeLink

  @GET("/check-status/{id}")
  suspend fun checkStatus(@Path("id") id: String): CheckStatusResponse
}
```

2. Call `InAppPaymentService.generateUpgradeLinks()` function when user taps on Pay button of sorts.
3. Once the payment url has been obtained, we can simply open a WebView to take the user to the
   checkout page.
4. While the checkout page is open, use `InAppPaymentService.checkStatus()` function to poll the
   payment status and automatically perform certain actions (show payment success/error screen, for
   example).

... and that's it!
