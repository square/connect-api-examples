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

import java.util.ArrayList;

import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogCategory;

import org.junit.Before;
import org.junit.Test;

public class CategoryCloneUtilTest {

  private CategoryCloneUtil cloneUtil;

  @Before
  public void setUp() {
    this.cloneUtil = new CategoryCloneUtil();
    Moneys.setCurrency("CAD");
  }

  @Test
  public void encodeCatalogData() {
    CatalogCategory category = new CatalogCategory("TestCategory", new ArrayList<String>());
    assertThat(cloneUtil.encodeCatalogData(category)).isEqualTo("TestCategory");
  }
}
