using System;
using Microsoft.AspNetCore.Mvc.RazorPages;
using Microsoft.Extensions.Configuration;
using Square.Connect.Api;
using Square.Connect.Model;

namespace sqRazorSample.Pages
{
    public class IndexModel : PageModel
    {
        public string PaymentFormUrl { get; set; }
        public string ApplicationId { get; set; }
        public string LocationId { get; set; }


        public IndexModel(IConfiguration configuration)
        {
            this.ApplicationId = configuration["AppSettings:ApplicationId"];
            this.LocationId = configuration["AppSettings:LocationId"];
            this.PaymentFormUrl = configuration["AppSettings:Environment"] == "sandbox" ? 
                "https://js.squareupsandbox.com/v2/paymentform" : "https://js.squareup.com/v2/paymentform" ;
        }
    }
}
