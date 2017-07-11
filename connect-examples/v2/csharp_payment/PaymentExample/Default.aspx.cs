using System;

using Square.Connect.Api;
using Square.Connect.Model;
using Square.Connect.Client;

namespace PaymentExample
{

    public partial class Default : System.Web.UI.Page
    {
        private static TransactionsApi _transactionsApi;

        // The ID of the business location to associate processed payments with.
        // See [Retrieve your business's locations]
        // (https://docs.connect.squareup.com/articles/getting-started/#retrievemerchantprofile)
        // for an easy way to get your business's location IDs.
        // If you're testing things out, use a sandbox location ID.
        private static string _locationId = "REPLACE_ME";
        
        static Default() {
            // The access token to use in all Connect API requests.
            // Use your *sandbox* accesstoken if you're just testing things out.
            Configuration.Default.AccessToken = "REPLACE_ME";
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            _transactionsApi = new TransactionsApi();
        }

        [System.Web.Services.WebMethod]
        public static string Charge(string nonce)
        {
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.
            string uuid = NewIdempotencyKey();

            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00,
            // which isn't very useful.
            Money amount = new Money(100, Money.CurrencyEnum.USD);

            ChargeRequest body = new ChargeRequest(AmountMoney: amount, IdempotencyKey: uuid, CardNonce: nonce);

            try
            {
                var response = _transactionsApi.Charge(_locationId, body);
                return "Transaction complete\n" + response.ToJson();
            }
            catch (ApiException e)
            {
                return e.Message;
            }
        }

        public static string NewIdempotencyKey()
        {
            return Guid.NewGuid().ToString();
        }
    }

}
