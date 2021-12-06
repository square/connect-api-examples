import Foundation

/// A watcher that repeatedly retrieves the payment status.
final class PaymentStatusWatcher {
    
    /// The ID of the payment of which to retrieve the status.
    private let paymentID: String
        
    /// The delay between each request for the payment status.
    private let pollingDelayInSeconds = 2
    
    /// A service for fetching and interacting with upgrades.
    private let ugpradesService = UpgradesService()
    
    init(paymentID: String) {
        self.paymentID = paymentID
    }
    
    /// Fetches the payment status repeatedly until the desired status is received.
    func waitForPaymentStatus(_ status: CheckPaymentStatusResponse.PaymentStatus, completion: @escaping (Result<Void, Error>) -> Void) {
        DispatchQueue.global().asyncAfter(deadline: .now() + .seconds(pollingDelayInSeconds)) { [weak self] in
            guard let self = self else { return }
            self.ugpradesService.checkPaymentStatus(with: self.paymentID) { [weak self] result in
                switch result {
                case .success(let response):
                    if response.status == status {
                        completion(.success(()))
                    } else {
                        self?.waitForPaymentStatus(status, completion: completion)
                    }
                case .failure(let error):
                    completion(.failure(error))
                }
            }
        }
    }
    
}
