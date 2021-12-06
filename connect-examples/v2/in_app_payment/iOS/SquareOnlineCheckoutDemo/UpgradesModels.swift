import Foundation

/// The response from the get upgrades request.
struct GetUpgradesRequestResponse: Decodable {
    
    let upgrades: [Upgrade]
}

/// A single upgrade available for payment.
struct Upgrade: Decodable {
    
    /// Price data for an upgrade.
    struct Price: Decodable {
        let amount: Int
        let currency: String
    }
    
    /// A unique ID representing this upgrade.
    let sku: String
    
    /// A user-facing name for the upgrade.
    let name: String
    
    /// Price data for the upgrade.
    let price: Price
}

/// The body for the generate link request.
struct GenerateLinkRequestBody: Encodable {
    
    let sku: String
}

/// The response from the generate link request.
struct GenerateLinkRequestResponse: Decodable {
    
    /// The URL where the payment can be made.
    let url: String
    
    /// The ID of the payemnt that can be made at the URL.
    let id: String
}

/// The response from the check payment status request.
struct CheckPaymentStatusResponse: Decodable {
    
    /// The possible payment statuses.
    enum PaymentStatus: String, Decodable {
        
        /// The payment has been paid.
        /// This is a terminal state.
        case paid = "PAID"
        
        /// The payment is still pending.
        case pending = "PENDING"
        
        /// The payment has been cancelled.
        /// This is a terminal state.
        case cancelled = "CANCELED"
    }

    /// The status of the payment.
    let status: PaymentStatus
}
