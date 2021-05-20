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

import static com.squareup.catalog.demo.util.Moneys.format;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Locale;

import com.squareup.square.models.Money;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MoneysTest {

  private Locale defaultLocale;

  @Before
  public void setUp() {
    // Remember the default locale so we can restore it when the test finishes.
    defaultLocale = Locale.getDefault();
    Locale.setDefault(Locale.US);
  }

  @After
  public void tearDown() {
    Locale.setDefault(defaultLocale);
  }

  @Test
  public void format_us_dollars() {
    Locale.setDefault(Locale.US);
    assertThat(format($(1_23, "USD"))).isEqualTo("$1.23");
    assertThat(format($(1234_56, "USD"))).isEqualTo("$1,234.56");
  }

  @Test
  public void format_japanese_yen() {
    Locale.setDefault(Locale.JAPAN);
    assertThat(format($(123, "JPY"))).isEqualTo("￥123");
    assertThat(format($(123456, "JPY"))).isEqualTo("￥123,456");
  }

  @Test
  public void format_canadian_dollars() {
    Locale.setDefault(Locale.CANADA);
    assertThat(format($(123, "CAD"))).isEqualTo("$1.23");
    assertThat(format($(123456, "CAD"))).isEqualTo("$1,234.56");
  }

  private Money $(long amount, String currency) {
    return new Money.Builder().amount(amount).currency(currency).build();
  }
}
