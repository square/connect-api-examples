using System;
using System.IO;
using System.Threading.Tasks;
using System.Collections.Generic;

using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using Square;
using Square.Models;
using Square.Apis;
using Square.Exceptions;


namespace sqRazorSample.Pages
{
  public class ProcessPaymentModel : PageModel
  {
    private SquareClient client;
    private string locationId;

    public ProcessPaymentModel(Microsoft.Extensions.Configuration.IConfiguration configuration)
    {
      var environment = configuration["AppSettings:Environment"] == "sandbox" ?
          Square.Environment.Sandbox : Square.Environment.Production;

      client = new SquareClient.Builder()
          .Environment(environment)
          .AccessToken(configuration["AppSettings:AccessToken"])
          .UserAgentDetail("sample_app_csharp_payment") // Remove or replace this detail when building your own app
          .Build();

      locationId = configuration["AppSettings:LocationId"];
    }

    public async Task<IActionResult> OnPostAsync()
    {
      var request = JObject.Parse(await new StreamReader(Request.Body).ReadToEndAsync());
      var token = (String)request["token"];
      var idempotencyKey = (String)request["idempotencyKey"];
      var PaymentsApi = client.PaymentsApi;

      // Get the currency for the location
      var retrieveLocationResponse = await client.LocationsApi.RetrieveLocationAsync(locationId: locationId);
      var currency = retrieveLocationResponse.Location.Currency;

      // Monetary amounts are specified in the smallest unit of the applicable currency.
      // This amount is in cents. It's also hard-coded for $1.00,
      // which isn't very useful.
      var amount = new Money.Builder()
          .Amount(100L)
          .Currency(currency)
          .Build();

      // To learn more about splitting payments with additional recipients,
      // see the Payments API documentation on our [developer site]
      // (https://developer.squareup.com/docs/payments-api/overview).
      var createPaymentRequest = new CreatePaymentRequest.Builder(
          sourceId: token,
          idempotencyKey: idempotencyKey,
          amountMoney: amount)
          .Build();

      try
      {
        var response = await PaymentsApi.CreatePaymentAsync(createPaymentRequest);
        return new JsonResult(new { payment = response.Payment });
      }
      catch (ApiException e)
      {
        return new JsonResult(new { errors = e.Errors });
      }
    }
  }
}
