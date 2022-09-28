using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Square;
using Square.Models;
using Square.Apis;
using Square.Exceptions;
using System.Threading.Tasks;

namespace csharp_checkout.Pages
{
  public class CheckoutModel : PageModel
  {
    private SquareClient client;
    private readonly string locationId;

    public CheckoutModel( Microsoft.Extensions.Configuration.IConfiguration configuration)
    {
      // Get environment
      Square.Environment environment = configuration["AppSettings:Environment"] == "sandbox" ?
        Square.Environment.Sandbox : Square.Environment.Production;

      // Build base client
      client = new SquareClient.Builder()
        .Environment(environment)
        .AccessToken(configuration["AppSettings:AccessToken"])
        .UserAgentDetail("sample_app_csharp_checkout") // Remove or replace this detail when building your own app
        .Build();

      locationId = configuration["AppSettings:LocationId"];

    }

    async public Task<IActionResult> OnPost()
    {
      ICheckoutApi checkoutApi = client.CheckoutApi;
      try
      {
        // Get the currency for the location
        RetrieveLocationResponse locationResponse = await client.LocationsApi.RetrieveLocationAsync(locationId: locationId);
        string currency = locationResponse.Location.Currency;

        // create line items for the order
        // This example assumes the order information is retrieved and hard coded
        // You can find different ways to retrieve order information and fill in the following lineItems object.
        List<OrderLineItem> lineItems = new List<OrderLineItem>();

        Money firstLineItemBasePriceMoney = new Money.Builder()
          .Amount(500L)
          .Currency(currency)
          .Build();

        OrderLineItem firstLineItem = new OrderLineItem.Builder("1")
          .Name("Test Item A")
          .BasePriceMoney(firstLineItemBasePriceMoney)
          .Build();

        lineItems.Add(firstLineItem);

        Money secondLineItemBasePriceMoney = new Money.Builder()
          .Amount(1000L)
          .Currency(currency)
          .Build();

        OrderLineItem secondLineItem = new OrderLineItem.Builder("3")
          .Name("Test Item B")
          .BasePriceMoney(secondLineItemBasePriceMoney)
          .Build();

        lineItems.Add(secondLineItem);

        // create Order object with line items
        Order order = new Order.Builder(locationId)
          .LineItems(lineItems)
          .Build();

        // create order request with order
        CreateOrderRequest orderRequest = new CreateOrderRequest.Builder()
          .Order(order)
          .Build();

        // create checkout request with the previously created order
        CreateCheckoutRequest createCheckoutRequest = new CreateCheckoutRequest.Builder(
            Guid.NewGuid().ToString(),
            orderRequest)
          .Build();

        // create checkout response, and redirect to checkout page if successful
        CreateCheckoutResponse response = checkoutApi.CreateCheckout(locationId, createCheckoutRequest);
        return Redirect(response.Checkout.CheckoutPageUrl);
      }
      catch (ApiException e)
      {
        return RedirectToPage("Error", new { error = e.Message});
      }
    }
  }
}
