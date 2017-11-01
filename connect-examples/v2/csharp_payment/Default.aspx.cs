using System;

using Square.Connect.Api;
using Square.Connect.Model;
using Square.Connect.Client;

namespace CSharpPaymentExample
{

    public partial class Default : System.Web.UI.Page
    {
        // The access token to use in all Connect API requests.
        // Use your *sandbox* accesstoken if you're just testing things out.
        private static string accessToken = "REPLACE_ME";

        // The ID of the business location to associate processed payments with.
        // See [Retrieve your business's locations]
        // (https://docs.connect.squareup.com/articles/getting-started/#retrievemerchantprofile)
        // for an easy way to get your business's location IDs.
        // If you're testing things out, use a sandbox location ID.
        public static string LocationId = "REPLACE_ME";
        public static string ApplicationId = "REPLACE_ME";

        static Default()
        {
            Configuration.Default.AccessToken = accessToken;
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        [System.Web.Services.WebMethod]
        public static string Charge(string nonce)
        {

            TransactionsApi transactionsApi = new TransactionsApi();
            // Every payment you process with the SDK must have a unique idempotency key.
            // If you're unsure whether a particular payment succeeded, you can reattempt
            // it with the same idempotency key without worrying about double charging
            // the buyer.
            string uuid = NewIdempotencyKey();

            // Monetary amounts are specified in the smallest unit of the applicable currency.
            // This amount is in cents. It's also hard-coded for $1.00,
            // which isn't very useful.
            Money amount = new Money(100, Money.CurrencyEnum.USD);

            // To learn more about splitting transactions with additional recipients,
            // see the Transactions API documentation on our [developer site]
            // (https://docs.connect.squareup.com/payments/transactions/overview#mpt-overview).
            ChargeRequest body = new ChargeRequest(AmountMoney: amount, IdempotencyKey: uuid, CardNonce: nonce);

            try
            {
                var response = transactionsApi.Charge(LocationId, body);
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
