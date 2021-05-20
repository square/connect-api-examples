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
package com.squareup.catalog.demo.example.clone;

import static org.fest.assertions.Assertions.assertThat;

import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogDiscount;

import org.junit.Before;
import org.junit.Test;

public class DiscountCloneUtilTest {

  private DiscountCloneUtil cloneUtil;

  @Before
  public void setUp() {
    this.cloneUtil = new DiscountCloneUtil(false);
    Moneys.setCurrency("CAD");
  }

  @Test
  public void encodeCatalogData_fixedAmount() {
    CatalogDiscount discount =
        new CatalogDiscount.Builder()
            .name("name")
            .discountType("FIXED_AMOUNT")
            .percentage(null)
            .amountMoney(Moneys.createMoneyObject(1000))
            .build();
    assertThat(cloneUtil.encodeCatalogData(discount)).isEqualTo(
        "name:::FIXED_AMOUNT:::null:::1000");
  }

  @Test
  public void encodeCatalogData_fixedPercentage() {
    CatalogDiscount discount =
        new CatalogDiscount.Builder()
            .name("name")
            .discountType("FIXED_PERCENTAGE")
            .percentage("12.34")
            .amountMoney(null)
            .build();
    assertThat(cloneUtil.encodeCatalogData(discount)).isEqualTo(
        "name:::FIXED_PERCENTAGE:::12.34:::null");
  }

  @Test
  public void encodeCatalogData_variableAmount() {
    CatalogDiscount discount =
        new CatalogDiscount.Builder()
            .name("name")
            .discountType("VARIABLE_AMOUNT")
            .percentage(null)
            .amountMoney(null)
            .build();
    assertThat(cloneUtil.encodeCatalogData(discount)).isEqualTo(
        "name:::VARIABLE_AMOUNT:::null:::null");
  }

  @Test
  public void encodeCatalogData_variablePercentage() {
    CatalogDiscount discount =
        new CatalogDiscount.Builder()
            .name("name")
            .discountType("VARIABLE_PERCENTAGE")
            .percentage(null)
            .amountMoney(null)
            .build();
    assertThat(cloneUtil.encodeCatalogData(discount)).isEqualTo(
        "name:::VARIABLE_PERCENTAGE:::null:::null");
  }
}
