using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Configuration;
using Square.Connect.Api;
using Square.Connect.Model;

namespace csharp_checkout.Pages
{
  public class CheckoutModel : PageModel
  {
    private readonly string locationId;

    public CheckoutModel(IConfiguration configuration)
    {
      locationId = configuration["AppSettings:LocationId"];
    }

    public IActionResult OnPost()
    {
      CheckoutApi checkoutApi = new CheckoutApi();
      int amount = (int)float.Parse(Request.Form["amount"]) * 100;

      try
      {
        // create line items for the order
        List<CreateOrderRequestLineItem> lineItems = new List<CreateOrderRequestLineItem>()
          {
            new CreateOrderRequestLineItem(
              Name: "Test Payment",
              Quantity: "1",
              BasePriceMoney: new Money(Amount: 500,
                                        Currency: Money.CurrencyEnum.USD)
            )
          };

        // create order with the line items
        CreateOrderRequest order = new CreateOrderRequest(
          LineItems: lineItems
        );

        // create checkout request with the previously created order
        CreateCheckoutRequest body = new CreateCheckoutRequest(
          IdempotencyKey: Guid.NewGuid().ToString(),
          Order: order
        );

        // create checkout response, and redirect to checkout page if successful
        CreateCheckoutResponse response = checkoutApi.CreateCheckout(locationId, body);
        return Redirect(response.Checkout.CheckoutPageUrl);
      }
      catch (Square.Connect.Client.ApiException e)
      {
        return RedirectToPage("Error", new { error = e.Message});
      }
    }
  }
}
