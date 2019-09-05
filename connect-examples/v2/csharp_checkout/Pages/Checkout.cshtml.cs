using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Configuration;
using Square.Connect.Api;
using Square.Connect.Client;
using Square.Connect.Model;

namespace csharp_checkout.Pages
{
  public class CheckoutModel : PageModel
  {
    private readonly string locationId;
    private readonly Configuration configuration;

    public CheckoutModel(IConfiguration configuration)
    {
      locationId = configuration["AppSettings:LocationId"];
      // Set 'basePath' to switch between sandbox env and production env
      // sandbox: https://connect.squareupsandbox.com
      // production: https://connect.squareup.com
      string basePath = configuration["AppSettings:Environment"] == "sandbox" ?
          "https://connect.squareupsandbox.com" : "https://connect.squareup.com";
      this.configuration = new Configuration(new ApiClient(basePath));
      this.configuration.AccessToken = configuration["AppSettings:AccessToken"];
    }

    public IActionResult OnPost()
    {
      CheckoutApi checkoutApi = new CheckoutApi(configuration: this.configuration);

      try
      {
        // create line items for the order
        // This example assumes the order information is retrieved and hard coded
        // You can find different ways to retrieved order information and fill in the following lineItems object.
        List<CreateOrderRequestLineItem> lineItems = new List<CreateOrderRequestLineItem>()
          {
            new CreateOrderRequestLineItem(
              Name: "Test Item A",
              Quantity: "1",
              BasePriceMoney: new Money(Amount: 500,
                                        Currency: Money.CurrencyEnum.USD)
            ),
            new CreateOrderRequestLineItem(
              Name: "Test Item B",
              Quantity: "3",
              BasePriceMoney: new Money(Amount: 1000,
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
