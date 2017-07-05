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

import com.squareup.connect.models.Money;
import com.squareup.connect.models.Money.CurrencyEnum;
import java.text.NumberFormat;

public class Moneys {

  /**
   * Creates a Money object for the specified amount of cents in USD.
   */
  public static Money usd(long cents) {
    return new Money()
        .amount(cents)
        .currency(CurrencyEnum.USD);
  }

  /**
   * Formats Money into a human readable currency string.
   *
   * @param moneyOrNull the money to format, or null
   * @return a formatted money string, or null if moneyOrNull is null
   */
  public static String format(Money moneyOrNull) {
    if (moneyOrNull == null) {
      return null;
    }

    // Note that this assumes the currency is in USD.
    return NumberFormat.getCurrencyInstance().format(moneyOrNull.getAmount() / 100.0);
  }

  private Moneys() {
  }
}
