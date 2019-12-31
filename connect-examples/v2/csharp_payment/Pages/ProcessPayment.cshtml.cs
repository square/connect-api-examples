using System;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Square;
using Square.Models;
using Square.Apis;
using Square.Exceptions;

namespace sqRazorSample.Pages
{
    public class ProcessPaymentModel : PageModel
    {
        private SquareClient client;
        public string ResultMessage
        {
            get;
            set;
        }

        public ProcessPaymentModel(Microsoft.Extensions.Configuration.IConfiguration configuration) {
            // Get environment
            Square.Environment environment = configuration["AppSettings:Environment"] == "sandbox" ?
                Square.Environment.Sandbox : Square.Environment.Production;

            // Build base client
            client = new SquareClient.Builder()
                .Environment(environment)
                .AccessToken(configuration["AppSettings:AccessToken"])
                .Build();
        }

        public void OnPost()
        {
            string nonce = Request.Form["nonce"];
            IPaymentsApi PaymentsApi = client.PaymentsApi;
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.
            string uuid = NewIdempotencyKey();

            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00,
            // which isn't very useful.
            Money amount = new Money.Builder()
                .Amount(500L)
                .Currency("USD")
                .Build();

            // To learn more about splitting payments with additional recipients,
            // see the Payments API documentation on our [developer site]
            // (https://developer.squareup.com/docs/payments-api/overview).
            CreatePaymentRequest createPaymentRequest  = new CreatePaymentRequest.Builder(nonce,uuid,amount)
                .Note("From Square Sample Csharp App")
                .Build();

            try
            {
                CreatePaymentResponse response = PaymentsApi.CreatePayment(createPaymentRequest);
                this.ResultMessage = "Payment complete! " + response.Payment.Note;
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
