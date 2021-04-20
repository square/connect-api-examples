using System;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Square;
using Square.Models;
using Square.Apis;
using Square.Exceptions;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;


namespace sqRazorSample.Pages
{
    public class ProcessPaymentModel : PageModel
    {
        private SquareClient client;
        private string LocationId;
        
        public string ResultMessage { get; set; }

        public ProcessPaymentModel(Microsoft.Extensions.Configuration.IConfiguration configuration) {
            // Get environment
            Square.Environment environment = configuration["AppSettings:Environment"] == "sandbox" ?
                Square.Environment.Sandbox : Square.Environment.Production;

            // Build base client
            client = new SquareClient.Builder()
                .Environment(environment)
                .AccessToken(configuration["AppSettings:AccessToken"])
                .Build();

            LocationId = configuration["AppSettings:LocationId"];
        }

        async public Task<IActionResult> OnPost()
        {
            string nonce = Request.Form["nonce"];
            IPaymentsApi PaymentsApi = client.PaymentsApi;
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.
            string uuid = NewIdempotencyKey();

            // Get the currency for the location
            RetrieveLocationResponse location = await client.LocationsApi.RetrieveLocationAsync(locationId: LocationId);
            string currency = location.Location.Currency;

            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00,
            // which isn't very useful.
            Money amount = new Money.Builder()
                .Amount(500L)
                .Currency(currency)
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
                ResultMessage = "Payment complete! " + response.Payment.Note;
            }
            catch (ApiException e)
            {
                ResultMessage = e.Message;
            }

            return Page();
        }

        private static string NewIdempotencyKey()
        {
            return Guid.NewGuid().ToString();
        }
    }
}
