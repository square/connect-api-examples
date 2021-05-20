/*
 * Copyright 2017 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.catalog.demo.util;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import com.squareup.square.models.Money;

public class Moneys {

  /**
   * Variable that holds the currency to be used across the application.
   */
  private static String currency;

  /**
   * Creates a Money object for the specified amount of cents in the currency provided earlier.
   */
  public static Money createMoneyObject(long cents) {
    return new Money.Builder().amount(cents).currency(currency).build();
  }

  /**
   * Formats Money into a human readable currency string.
   *
   * @param moneyOrNull the money to format, or null
   * @return a formatted money string, or null if moneyOrNull is null
   */
  public static String format(Money moneyOrNull) {
    return format(moneyOrNull, Locale.getDefault());
  }

  /**
   * Formats Money into a human readable currency string.
   *
   * @param moneyOrNull the money to format, or null
   * @param locale      the {@link Locale} to format for
   * @return a formatted money string, or null if moneyOrNull is null
   */
  public static String format(Money moneyOrNull, Locale locale) {
    if (moneyOrNull == null) {
      return null;
    }

    // Convert the currency specified in the proto to a Currency object.
    Currency currency = Currency.getInstance(moneyOrNull.getCurrency().toString());

    // Create a formatter that uses the currency.
    NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
    formatter.setCurrency(currency);
    formatter.setMaximumFractionDigits(currency.getDefaultFractionDigits());

    if (currency.getDefaultFractionDigits() == 0) {
      // For locales that do not support fractional amounts, use the amount as is.
      // For example, Japan has no concept of "cents". The base unit of currency is 1 Yen,
      // and currency is always measured in Yen.
      return formatter.format(moneyOrNull.getAmount());
    } else {
      // For locales that support fractional amounts, divide by 100 to get the local equivalent of
      // "dollars". For example, the amount in USD is represented as cents. We divide by 100 to get
      // US dollars.
      return formatter.format(moneyOrNull.getAmount() / 100.0);
    }
  }

  /**
   * Sets the appropriate currency for the app.
   *
   * @param c the currency to be used.
   */
  public static void setCurrency(String c) {
    currency = c;
  }

  /**
   * Gets the appropriate currency for the app.
   *
   * @return the currency used across the app.
   */
  public static String getCurrency() {
    return currency;
  }

  private Moneys() {
  }
}
