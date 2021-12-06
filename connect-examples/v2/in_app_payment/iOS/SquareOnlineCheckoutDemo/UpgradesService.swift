import Foundation

/// A service for fetching and interacting with upgrades.
final class UpgradesService {
    
    /// Specific errors for the upgrades service.
    enum UpgradesServiceError: Error {
        
        /// Data was missing in the response.
        case missingData
    }
    
    /// The base URL for network requests.
    private let baseURL = URL(string: "http://localhost:3000")!
    
    /// Requests available upgrades and returns the result into a completion handler.
    func getUpgrades(completion: @escaping (Result<GetUpgradesRequestResponse, Error>) -> Void) {
        var request = URLRequest(url: baseURL.appendingPathComponent("get-upgrades"))
        request.httpMethod = "GET"
        send(request, completion: completion)
    }
    
    /// Requests a web link to pay for an upgrade and returns the result into a completion handler.
    func generateUpgradeLink(skuID: String, completion: @escaping (Result<GenerateLinkRequestResponse, Error>) -> Void) {
        var request = URLRequest(url: baseURL.appendingPathComponent("generate-upgrade-link"))
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        do {
            request.httpBody = try JSONEncoder().encode(GenerateLinkRequestBody(sku: skuID))
            send(request, completion: completion)
        } catch {
            completion(.failure(error))
        }
    }
    
    /// Requests the status of a payment and returns the result into a completion handler.
    func checkPaymentStatus(with id: String, completion: @escaping (Result<CheckPaymentStatusResponse, Error>) -> Void) {
        var request = URLRequest(url: baseURL.appendingPathComponent("check-status/\(id)"))
        request.httpMethod = "GET"
        send(request, completion: completion)
    }
    
    /// Sends a network requesst, decodes the anticipated type, and returns the result into a completion handler.
    private func send<T: Decodable>(_ request: URLRequest, completion: @escaping (Result<T, Error>) -> Void) {
        URLSession.shared.dataTask(with: request) { data, _, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            guard let data = data else {
                completion(.failure(UpgradesServiceError.missingData))
                return
            }
            do {
                completion(.success(try JSONDecoder().decode(T.self, from: data)))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
}
