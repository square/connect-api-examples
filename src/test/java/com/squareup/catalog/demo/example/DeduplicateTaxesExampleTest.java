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
package com.squareup.catalog.demo.example;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.example.DeduplicateTaxesExample.DuplicateTaxInfo;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class DeduplicateTaxesExampleTest {

  @Mock
  Logger logger;

  @Before
  public void setUp() {
    initMocks(this);
    Moneys.setCurrency("CAD");
  }

  @Test
  public void mergeDuplicate_bothPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectAbsentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectAbsentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup, logger);

    CatalogObject result = taxInfo.getMasterTax();
    assertThat(result.getPresentAtAllLocations()).isTrue();
    assertThat(result.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(result.getAbsentAtLocationIds()).containsExactly("L2");
  }

  @Test
  public void mergeDuplicate_onlyDuplicatePresentAtAllLocations() {
    CatalogObject master = createCatalogObjectPresentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectAbsentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup, logger);

    CatalogObject result = taxInfo.getMasterTax();
    assertThat(result.getPresentAtAllLocations()).isTrue();
    assertThat(result.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(result.getAbsentAtLocationIds()).containsExactly("L3");
  }

  @Test
  public void mergeDuplicate_onlyMasterPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectAbsentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectPresentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup, logger);

    CatalogObject result = taxInfo.getMasterTax();
    assertThat(result.getPresentAtAllLocations()).isTrue();
    assertThat(result.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(result.getAbsentAtLocationIds()).containsExactly("L1");
  }

  @Test
  public void mergeDuplicate_neitherPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectPresentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectPresentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup, logger);

    CatalogObject result = taxInfo.getMasterTax();
    assertThat(result.getPresentAtAllLocations()).isFalse();
    assertThat(result.getPresentAtLocationIds()).containsExactly("L1", "L2", "L3");
    assertThat(result.getAbsentAtLocationIds()).isNullOrEmpty();
  }

  private static CatalogObject createCatalogObjectPresentAtLocations(String... locationIds) {
    return new CatalogObject.Builder(CatalogObjectTypes.TAX.toString(), "id")
        .presentAtAllLocations(false)
        .presentAtLocationIds(Arrays.asList(locationIds))
        .build();
  }

  private static CatalogObject createCatalogObjectAbsentAtLocations(String... locationIds) {
    return new CatalogObject.Builder(CatalogObjectTypes.TAX.toString(), "id")
        .presentAtAllLocations(true)
        .absentAtLocationIds(Arrays.asList(locationIds))
        .build();
  }
}
