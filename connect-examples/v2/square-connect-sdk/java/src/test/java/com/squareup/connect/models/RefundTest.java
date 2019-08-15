package com.squareup.connect.models;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class RefundTest {
  /**
   * additionalRecipientsTest
   *
   * Tests that the refund object has an AdditionalRecipients field.
   */
  @Test
  public void additionalRecipientsTest() {
    AdditionalRecipient recipient = new AdditionalRecipient();
    recipient.setLocationId("location");
    recipient.setDescription("description");
    Money money = new Money();
    money.setAmount(1L);
    money.setCurrency("USD");
    recipient.setAmountMoney(money);
    new Refund().setAdditionalRecipients(new ArrayList<>(
        Arrays.asList(recipient)
    ));
  }
}
