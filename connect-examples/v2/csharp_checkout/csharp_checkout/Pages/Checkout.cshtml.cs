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
      this.locationId = configuration["AppSettings:LocationId"];
    }

    public string ResultMessage
    {
      get;
      set;
    }

    public IActionResult OnPost()
    {
      CheckoutApi checkoutApi = new CheckoutApi();
      int amount = (int)float.Parse(Request.Form["amount"]) * 100;

      try
      {

        CreateCheckoutRequest body = new CreateCheckoutRequest(
          IdempotencyKey: Guid.NewGuid().ToString(),
          Order: new CreateOrderRequest(
              LineItems: new List<CreateOrderRequestLineItem>()
              {
                new CreateOrderRequestLineItem(
                  Name: "Test Payment",
                  Quantity: "1",
                  BasePriceMoney: new Money(Amount: 500,
                                            Currency: Money.CurrencyEnum.USD)
                )
              }
        ));

        CreateCheckoutResponse response = checkoutApi.CreateCheckout(locationId, body);
        return Redirect(response.Checkout.CheckoutPageUrl);
      }
      catch (Square.Connect.Client.ApiException e)
      {
        this.ResultMessage = e.Message;
        return null;
      }
    }
  }
}
