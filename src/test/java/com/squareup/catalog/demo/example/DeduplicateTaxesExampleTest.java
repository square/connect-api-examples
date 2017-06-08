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

import com.squareup.catalog.demo.example.DeduplicateTaxesExample.DuplicateTaxInfo;
import com.squareup.connect.models.CatalogObject;
import java.util.Arrays;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DeduplicateTaxesExampleTest {

  @Test public void mergeDuplicate_bothPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectAbsentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectAbsentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup);
    assertThat(master.getPresentAtAllLocations()).isTrue();
    assertThat(master.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(master.getAbsentAtLocationIds()).containsExactly("L2");
  }

  @Test public void mergeDuplicate_onlyDuplicatePresentAtAllLocations() {
    CatalogObject master = createCatalogObjectPresentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectAbsentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup);
    assertThat(master.getPresentAtAllLocations()).isTrue();
    assertThat(master.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(master.getAbsentAtLocationIds()).containsExactly("L3");
  }

  @Test public void mergeDuplicate_onlyMasterPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectAbsentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectPresentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup);
    assertThat(master.getPresentAtAllLocations()).isTrue();
    assertThat(master.getPresentAtLocationIds()).isNullOrEmpty();
    assertThat(master.getAbsentAtLocationIds()).containsExactly("L1");
  }

  @Test public void mergeDuplicate_neitherPresentAtAllLocations() {
    CatalogObject master = createCatalogObjectPresentAtLocations("L1", "L2");
    CatalogObject dup = createCatalogObjectPresentAtLocations("L2", "L3");

    DuplicateTaxInfo taxInfo = new DuplicateTaxInfo(master);
    taxInfo.mergeDuplicate(dup);
    assertThat(master.getPresentAtAllLocations()).isFalse();
    assertThat(master.getPresentAtLocationIds()).containsExactly("L1", "L2", "L3");
    assertThat(master.getAbsentAtLocationIds()).isNullOrEmpty();
  }

  private static CatalogObject createCatalogObjectPresentAtLocations(String... locationIds) {
    return new CatalogObject().presentAtAllLocations(false)
        .presentAtLocationIds(Arrays.asList(locationIds));
  }

  private static CatalogObject createCatalogObjectAbsentAtLocations(String... locationIds) {
    return new CatalogObject().presentAtAllLocations(true)
        .absentAtLocationIds(Arrays.asList(locationIds));
  }
}
