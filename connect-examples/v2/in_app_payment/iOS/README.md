# In App Payment Demo for iOS

This iOS demo app showcases an example of how to use Square's APIs to create a simple in-app payment option. The endpoints can be found in `SquareOnlineCheckoutDemo/UpgradesService.swift`. The UI is presented via two view controllers, `UpgradesTableViewController` and `PaymentWebViewController`. 

This demo was created with Xcode 13.0 and may not work with other versions. 

This demo uses no third-party libraries. 

To view the project, open `SquareOnlineCheckoutDemo.xcodeproj` and click Run to run it on the iOS Simulator. 

## Getting Started with In-App Payments

1. Add APIs (see `UpgradesService.swift` and `UpgradesModels.swift` for further details). 

 * `getUpgrades`
 * `generateUpgradeLink`
 * `checkPaymentStatus`

2. Call `UpgradesService.generateUpgradeLink(skuID:completion:)` to generate a URL to a web page where the user can pay. 

3. Open the URL in a `WKWebView` to present it to the user. You may decide instead to open the URL in Safari or `SFSafariViewController` but will then lose the ability to detect a completed or cancelled payment and return the user to the application. 

4. While the URL is open in a `WKWebView`, use `UpgradesService.generateUpgradeLink.checkPaymentStatus(id:completion:)` to poll for the payment status and once it is completed or cancelled, perform actions in your native code (e.g., present a success or failure screen). 