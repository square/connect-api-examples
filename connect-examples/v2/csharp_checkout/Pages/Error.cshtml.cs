using Microsoft.AspNetCore.Mvc.RazorPages;

namespace csharp_checkout.Pages
{
  public class ErrorModel : PageModel
  {
    public string ErrorMessage { get; set; }

    public void OnGet(string error)
    {
      ErrorMessage = error;
    }
  }
}
