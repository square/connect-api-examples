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

import com.squareup.connect.models.CatalogModifier;
import com.squareup.connect.models.CatalogModifierList;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.Money;
import org.junit.Before;
import org.junit.Test;

import static com.squareup.catalog.demo.util.Moneys.usd;
import static com.squareup.connect.models.CatalogModifierList.SelectionTypeEnum.MULTIPLE;
import static org.fest.assertions.Assertions.assertThat;

public class ModifierListCloneUtilTest {

  private ModifierListCloneUtil cloneUtil;

  @Before public void setUp() {
    this.cloneUtil = new ModifierListCloneUtil();
  }

  @Test public void encodeCatalogData() {
    CatalogModifierList modifierList = new CatalogModifierList()
        .name("name")
        .selectionType(MULTIPLE);
    assertThat(cloneUtil.encodeCatalogData(modifierList)).isEqualTo("name:::MULTIPLE");
  }

  @Test public void removeSourceAccountMetaData_modifiersMatchModifierList() {
    CatalogObject modifier0 = createModifier("modifier0", null)
        .presentAtAllLocations(false) //
        .addPresentAtLocationIdsItem("sourceLocation1");
    CatalogObject modifier1 = createModifier("modifier1", null)
        .presentAtAllLocations(false) //
        .addPresentAtLocationIdsItem("sourceLocation1");
    CatalogObject modifierList = new CatalogObject()
        .presentAtAllLocations(false) //
        .addPresentAtLocationIdsItem("sourceLocation1")
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(modifier0)
            .addModifiersItem(modifier1));
    cloneUtil.removeSourceAccountMetaData(modifierList);
    assertThat(modifierList.getPresentAtAllLocations()).isTrue();
    assertThat(modifierList.getPresentAtLocationIds()).isEmpty();
    assertThat(modifier0.getPresentAtAllLocations()).isTrue();
    assertThat(modifier0.getPresentAtLocationIds()).isEmpty();
    assertThat(modifier1.getPresentAtAllLocations()).isTrue();
    assertThat(modifier1.getPresentAtLocationIds()).isEmpty();
  }

  @Test public void mergeSourceCatalogObjectIntoTarget_appendsNewModifiers() {
    CatalogObject sourceModifierList = new CatalogObject()
        .presentAtAllLocations(false) //
        .addPresentAtLocationIdsItem("sourceLocation1")
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("foo", null)
                .presentAtAllLocations(false) //
                .addPresentAtLocationIdsItem("sourceLocation1"))
            .addModifiersItem(createModifier("bar", null)
                .presentAtAllLocations(false) //
                .addPresentAtLocationIdsItem("sourceLocation1")));
    CatalogObject targetModifierList = new CatalogObject()
        .presentAtAllLocations(true) //
        .addPresentAtLocationIdsItem("targetLocation1")
        .addAbsentAtLocationIdsItem("targetLocation2")
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("bar", null)
                .presentAtAllLocations(true) //
                .addPresentAtLocationIdsItem("targetLocation1")
                .addAbsentAtLocationIdsItem("targetLocation2"))
            .addModifiersItem(createModifier("baz", null)
                .presentAtAllLocations(true) //
                .addPresentAtLocationIdsItem("targetLocation1")
                .addAbsentAtLocationIdsItem("targetLocation2")));

    CatalogObject resultObject =
        cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceModifierList, targetModifierList);
    assertThat(resultObject.getPresentAtAllLocations()).isTrue();
    assertThat(resultObject.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultObject.getAbsentAtLocationIds()).containsExactly("targetLocation2");

    CatalogModifierList resultModifierList = resultObject.getModifierListData();
    assertThat(resultModifierList.getModifiers()).hasSize(3);

    CatalogObject resultModifier0 = resultModifierList.getModifiers().get(0);
    assertThat(resultModifier0.getPresentAtAllLocations()).isTrue();
    assertThat(resultModifier0.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultModifier0.getAbsentAtLocationIds()).containsExactly("targetLocation2");
    assertThat(resultModifier0.getModifierData().getName()).isEqualTo("bar");
    CatalogObject resultModifier1 = resultModifierList.getModifiers().get(1);
    assertThat(resultModifier1.getPresentAtAllLocations()).isTrue();
    assertThat(resultModifier1.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultModifier1.getAbsentAtLocationIds()).containsExactly("targetLocation2");
    assertThat(resultModifier1.getModifierData().getName()).isEqualTo("baz");
    CatalogObject resultModifier2 = resultModifierList.getModifiers().get(2);
    assertThat(resultModifier2.getPresentAtAllLocations()).isTrue();
    assertThat(resultModifier2.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultModifier2.getAbsentAtLocationIds()).containsExactly("targetLocation2");
    assertThat(resultModifier2.getModifierData().getName()).isEqualTo("foo");
  }

  @Test public void mergeSourceCatalogObjectIntoTarget_coercesNullAmountToZero() {
    CatalogObject sourceModifierList = new CatalogObject()
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("name", null)));
    CatalogObject targetModifierList = new CatalogObject()
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("name", 0L)));

    CatalogObject resultObject =
        cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceModifierList, targetModifierList);
    assertThat(resultObject).isNull();
  }

  @Test public void mergeSourceCatalogObjectIntoTarget_addsModifierIfPriceDifferent() {
    CatalogObject sourceModifierList = new CatalogObject()
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("name", 0L)));
    CatalogObject targetModifierList = new CatalogObject()
        .modifierListData(new CatalogModifierList()
            .addModifiersItem(createModifier("name", 100L)));

    CatalogObject resultObject =
        cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceModifierList, targetModifierList);
    CatalogModifierList resultModifierList = resultObject.getModifierListData();
    assertThat(resultModifierList.getModifiers()).hasSize(2);

    CatalogObject resultModifier0 = resultModifierList.getModifiers().get(0);
    assertThat(resultModifier0.getModifierData().getName()).isEqualTo("name");
    assertThat(resultModifier0.getModifierData().getPriceMoney().getAmount()).isEqualTo(100L);
    CatalogObject resultModifier1 = resultModifierList.getModifiers().get(1);
    assertThat(resultModifier1.getModifierData().getName()).isEqualTo("name");
    assertThat(resultModifier1.getModifierData().getPriceMoney().getAmount()).isEqualTo(0L);
  }

  private CatalogObject createModifier(String name, Long priceAmount) {
    Money priceMoney = (priceAmount == null) ? null : usd(priceAmount);
    return new CatalogObject()
        .modifierData(new CatalogModifier()
            .name(name)
            .priceMoney(priceMoney));
  }
}
