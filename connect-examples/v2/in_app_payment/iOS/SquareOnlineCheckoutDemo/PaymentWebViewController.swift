import UIKit
import WebKit

/// A delegate to be informed when the payment has been made.
protocol PaymentWebViewControllerDelegate: AnyObject {
    
    func paymentWebViewController(_ controller: PaymentWebViewController, didCompletePaymentWithID paymentID: String)
}

final class PaymentWebViewController: UIViewController {
    
    @IBOutlet weak var webView: WKWebView!
    
    /// The URL to load into the web view.
    var url: URL!
    
    /// The ID of the payment.
    var paymentID: String!
    
    /// A delegate to be informed when the payment has been made.
    weak var delegate: PaymentWebViewControllerDelegate?
    
    /// A watcher that repeatedly retrieves the payment status.
    private lazy var paymentStatusWatcher = PaymentStatusWatcher(paymentID: paymentID)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.title = "Online Checkout"
        navigationItem.leftBarButtonItem = .init(
            barButtonSystemItem: .close,
            target: self,
            action: #selector(close)
        )
        webView.navigationDelegate = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        loadURL()
        watchPaymentStatus()
    }
    
    private func loadURL() {
        webView.load(URLRequest(url: url))
    }
    
    /// Continue to ask for the payment status until we receive the desired result.
    private func watchPaymentStatus() {
        paymentStatusWatcher.waitForPaymentStatus(.paid) { result in
            switch result {
            case .success:
                // The status is paid. We can now dismiss this view controller and inform the delegate.
                DispatchQueue.main.async { [weak self] in
                    guard let self = self else { return }
                    self.close() {
                        self.delegate?.paymentWebViewController(self, didCompletePaymentWithID: self.paymentID)
                    }
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
    @objc
    private func close(completion: @escaping () -> Void) {
        dismiss(animated: true, completion: completion)
    }
    
}

extension PaymentWebViewController: WKNavigationDelegate {
    
    /// Ensure that the web view will still load with unverified SSL certificates.
    func webView(_ webView: WKWebView, didReceive challenge: URLAuthenticationChallenge, completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        let cred = URLCredential(trust: challenge.protectionSpace.serverTrust!)
        completionHandler(.useCredential, cred)
    }
    
}
    
