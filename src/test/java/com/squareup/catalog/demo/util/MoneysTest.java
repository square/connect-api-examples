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
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.squareup.catalog.demo.util.Moneys.format;
import static com.squareup.connect.models.Money.CurrencyEnum.EUR;
import static com.squareup.connect.models.Money.CurrencyEnum.JPY;
import static com.squareup.connect.models.Money.CurrencyEnum.USD;
import static org.fest.assertions.Assertions.assertThat;

public class MoneysTest {

  private Locale defaultLocale;

  @Before public void setUp() {
    // Remember the default locale so we can restore it when the test finishes.
    defaultLocale = Locale.getDefault();
    Locale.setDefault(Locale.US);
  }

  @After public void tearDown() {
    Locale.setDefault(defaultLocale);
  }

  @Test public void format_us_dollars() {
    assertThat(format($(1_23, USD))).isEqualTo("$1.23");
    assertThat(format($(1234_56, USD))).isEqualTo("$1,234.56");

    Locale.setDefault(Locale.JAPAN);
    assertThat(format($(1_23, USD))).isEqualTo("USD1.23");
    assertThat(format($(1234_56, USD))).isEqualTo("USD1,234.56");
  }

  @Test public void format_euro() {
    assertThat(format($(1_23, EUR))).isEqualTo("EUR1.23");
    assertThat(format($(1234_56, EUR))).isEqualTo("EUR1,234.56");

    Locale.setDefault(Locale.FRANCE);
    assertThat(format($(1_23, EUR))).isEqualTo("1,23 €");
    assertThat(format($(1234_56, EUR))).isEqualTo("1 234,56 €");
  }

  @Test public void format_japanese_yen() {
    assertThat(format($(123, JPY))).isEqualTo("JPY123");
    assertThat(format($(123456, JPY))).isEqualTo("JPY123,456");

    Locale.setDefault(Locale.JAPAN);
    assertThat(format($(123, JPY))).isEqualTo("￥123");
    assertThat(format($(123456, JPY))).isEqualTo("￥123,456");
  }

  private Money $(long amount, Money.CurrencyEnum currency) {
    return new Money().amount(amount).currency(currency);
  }
}
