using System;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Configuration;
using Square.Connect.Api;
using Square.Connect.Client;
using Square.Connect.Model;

namespace sqRazorSample.Pages
{
    public class ProcessPaymentModel : PageModel
    {
        readonly Configuration configuration;

        public string ResultMessage
        {
            get;
            set;
        }

        public ProcessPaymentModel(IConfiguration configuration) {
            // Set 'basePath' to switch between sandbox env and production env
            // sandbox: https://connect.squareupsandbox.com
            // production: https://connect.squareup.com
            string basePath = configuration["AppSettings:Environment"] == "sandbox" ?
                "https://connect.squareupsandbox.com" : "https://connect.squareup.com";
            this.configuration = new Configuration(new ApiClient(basePath));
            this.configuration.AccessToken = configuration["AppSettings:AccessToken"];
        }

        public void OnPost()
        {
            string nonce = Request.Form["nonce"];
            PaymentsApi paymentsApi = new PaymentsApi(configuration: this.configuration);
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.
            string uuid = NewIdempotencyKey();

            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00,
            // which isn't very useful.
            Money amount = new Money(100, "USD");

            // To learn more about splitting payments with additional recipients,
            // see the Payments API documentation on our [developer site]
            // (https://developer.squareup.com/docs/payments-api/overview).
            CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest(AmountMoney: amount, IdempotencyKey: uuid, SourceId: nonce);

            try
            {
                var response = paymentsApi.CreatePayment(createPaymentRequest);
                this.ResultMessage = "Payment complete! " + response.ToJson();
            }
            catch (ApiException e)
            {
                this.ResultMessage = e.Message;
            }
        }

        private static string NewIdempotencyKey()
        {
            return Guid.NewGuid().ToString();
        }
    }
}
