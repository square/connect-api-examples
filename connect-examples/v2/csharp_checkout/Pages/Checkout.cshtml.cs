using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Square;
using Square.Models;
using Square.Apis;
using Square.Exceptions;

namespace csharp_checkout.Pages
{
  public class CheckoutModel : PageModel
  {
    private SquareClient client;
    private readonly string locationId;
    // private readonly Configuration configuration;

    public CheckoutModel( Microsoft.Extensions.Configuration.IConfiguration configuration)
    {
      // Get environment
      Square.Environment environment = configuration["AppSettings:Environment"] == "sandbox" ?
        Square.Environment.Sandbox : Square.Environment.Production;

      // Build base client
      client = new SquareClient.Builder()
        .Environment(environment)
        .AccessToken(configuration["AppSettings:AccessToken"])
        .Build();

      locationId = configuration["AppSettings:LocationId"];

    }

    public IActionResult OnPost()
    {
      ICheckoutApi checkoutApi = client.CheckoutApi;
      try
      {
        // create line items for the order
        // This example assumes the order information is retrieved and hard coded
        // You can find different ways to retrieve order information and fill in the following lineItems object.
        List<CreateOrderRequestLineItem> lineItems = new List<CreateOrderRequestLineItem>();

        Money firstLineItemBasePriceMoney = new Money.Builder()
          .Amount(500L)
          .Currency("USD")
          .Build();

        CreateOrderRequestLineItem firstLineItem = new CreateOrderRequestLineItem.Builder("1")
          .Name("Test Item A")
          .BasePriceMoney(firstLineItemBasePriceMoney)
          .Build();

        lineItems.Add(firstLineItem);

        Money secondLineItemBasePriceMoney = new Money.Builder()
          .Amount(1000L)
          .Currency("USD")
          .Build();

        CreateOrderRequestLineItem secondLineItem = new CreateOrderRequestLineItem.Builder("3")
          .Name("Test Item B")
          .BasePriceMoney(secondLineItemBasePriceMoney)
          .Build();

        lineItems.Add(secondLineItem);

        // create order with the line items
        CreateOrderRequest order = new CreateOrderRequest.Builder()
          .LineItems(lineItems)
          .Build();

        // create checkout request with the previously created order
        CreateCheckoutRequest createCheckoutRequest = new CreateCheckoutRequest.Builder(
            Guid.NewGuid().ToString(),
            order)
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
