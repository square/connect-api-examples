package com.squareup.connectexamples.ecommerce;

import java.util.List;

/**
 * PaymentResult is an object representing the response back to the front end.
 */
public class PaymentResult {

  private String title;

  private List<String> errors;

  public PaymentResult(String t, List<String> errorMessages) {
    this.title = t;
    this.errors = errorMessages;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }

  public List<String> getErrors() {
    return this.errors;
  }
}
