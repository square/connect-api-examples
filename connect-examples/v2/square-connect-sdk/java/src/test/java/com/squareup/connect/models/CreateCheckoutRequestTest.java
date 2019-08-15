package com.squareup.connect.models;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class CreateCheckoutRequestTest {
  /**
   * additionalRecipientsTest
   *
   * Tests that the CreateCheckoutRequest object has an AdditionalRecipients field.
   */
  @Test
  public void additionalRecipientsTest() {
    ChargeRequestAdditionalRecipient recipient = new ChargeRequestAdditionalRecipient();
    recipient.setLocationId("location");
    recipient.setDescription("description");
    Money money = new Money();
    money.setAmount(1L);
    money.setCurrency("USD");
    recipient.setAmountMoney(money);
    new CreateCheckoutRequest().setAdditionalRecipients(new ArrayList<>(
        Arrays.asList(recipient)
    ));
  }
}
