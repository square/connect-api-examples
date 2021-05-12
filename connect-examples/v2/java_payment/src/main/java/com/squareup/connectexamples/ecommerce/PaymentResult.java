package com.squareup.connectexamples.ecommerce;

/**
 * PaymentResult is an object representing the response back to the front end.
 */
public class PaymentResult {

  private String title;

  public PaymentResult(String t) {
    this.title = t;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }
}
