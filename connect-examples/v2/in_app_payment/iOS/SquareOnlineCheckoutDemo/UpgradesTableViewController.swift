import UIKit

final class UpgradesTableViewController: UITableViewController, PaymentWebViewControllerDelegate {
    
    /// The available upgrades retrieved from the server.
    private var upgrades = [Upgrade]()
    
    /// A service for fetching and interacting with upgrades. 
    private let upgradesService = UpgradesService()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getUpgrades()
    }
    
    /// Gets upgrades from the server and populates the data source.
    private func getUpgrades() {
        upgradesService.getUpgrades { [weak self] result in
            switch result {
            case .success(let response):
                DispatchQueue.main.async {
                    self?.upgrades = response.upgrades
                    self?.tableView.reloadData()
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
    /// Genrates upgrade link and opens it in web view controller.
    private func showPaymentWebView(for upgrade: Upgrade) {
        upgradesService.generateUpgradeLink(skuID: upgrade.sku) { [weak self] result in
            switch result {
            case .success(let response):
                DispatchQueue.main.async {
                    self?.performSegue(withIdentifier: "ShowPaymentWebView", sender: response)
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard
            segue.identifier == "ShowPaymentWebView",
            let destination = segue.destination as? UINavigationController,
            let webViewController = destination.viewControllers.first as? PaymentWebViewController,
            let response = sender as? GenerateLinkRequestResponse,
            let url = URL(string: response.url)
        else {
            return
        }
        webViewController.url = url
        webViewController.paymentID = response.id
        webViewController.delegate = self
    }
    
    // MARK: PaymentWebViewControllerDelegate
    
    func paymentWebViewController(_ controller: PaymentWebViewController, didCompletePaymentWithID paymentID: String) {
        guard let selectedIndexPath = tableView.indexPathForSelectedRow else { return }
        let upgrade = upgrades[selectedIndexPath.row]
        tableView.deselectRow(at: selectedIndexPath, animated: true)
        let alert = UIAlertController(title: "Paid for \(upgrade.name)", message: nil, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: { [weak alert] _ in
            alert?.dismiss(animated: true, completion: nil)
        }))
        present(alert, animated: true, completion: nil)
    }
    
    // MARK: UITableViewDelegate
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        showPaymentWebView(for: upgrades[indexPath.row])
    }
    
    // MARK: UITableViewDataSource
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        var content = cell.defaultContentConfiguration()
        content.text = upgrades[indexPath.row].name
        cell.contentConfiguration = content
        return cell
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        upgrades.count
    }
    
}
