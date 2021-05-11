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

import static com.squareup.catalog.demo.util.Moneys.createMoneyObject;
import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogModifier;
import com.squareup.square.models.CatalogModifierList;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.Money;

import org.junit.Before;
import org.junit.Test;

public class ModifierListCloneUtilTest {

  private ModifierListCloneUtil cloneUtil;

  @Before
  public void setUp() {
    this.cloneUtil = new ModifierListCloneUtil();
    Moneys.setCurrency("CAD");
  }

  @Test
  public void encodeCatalogData() {
    CatalogModifierList modifierList =
        new CatalogModifierList.Builder().name("name").selectionType("MULTIPLE").build();
    assertThat(cloneUtil.encodeCatalogData(modifierList)).isEqualTo("name:::MULTIPLE");
  }

  @Test
  public void removeSourceAccountMetaData_modifiersMatchModifierList() {
    CatalogObject modifier0 = createModifier("modifier0", null);
    modifier0 = modifier0.toBuilder().presentAtAllLocations(false)
        .presentAtLocationIds(Arrays.asList("sourceLocation1")).build();

    CatalogObject modifier1 = createModifier("modifier1", null);
    modifier1 = modifier1.toBuilder().presentAtAllLocations(false)
        .presentAtLocationIds(Arrays.asList("sourceLocation1")).build();

    CatalogObject modifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id").build();

    List<CatalogObject> modifiers = Arrays.asList(modifier0, modifier1);

    modifierList = modifierList.toBuilder().presentAtAllLocations(false) //
        .presentAtLocationIds(Arrays.asList("sourceLocation1"))
        .modifierListData(new CatalogModifierList.Builder().modifiers(modifiers).build()).build();

    CatalogObject result = cloneUtil.removeSourceAccountMetaData(modifierList);
    assertThat(result.getPresentAtAllLocations()).isTrue();
    assertThat(result.getPresentAtLocationIds()).isEmpty();
    assertThat(
        result.getModifierListData().getModifiers().get(0).getPresentAtAllLocations()).isTrue();
    assertThat(
        result.getModifierListData().getModifiers().get(0).getPresentAtLocationIds()).isEmpty();
    assertThat(
        result.getModifierListData().getModifiers().get(1).getPresentAtAllLocations()).isTrue();
    assertThat(
        result.getModifierListData().getModifiers().get(1).getPresentAtLocationIds()).isEmpty();
  }

  @Test
  public void mergeSourceCatalogObjectIntoTarget_appendsNewModifiers() {
    CatalogObject sourceModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id")
            .presentAtAllLocations(false).presentAtLocationIds(Arrays.asList("sourceLocation1"))
            .modifierListData(new CatalogModifierList.Builder().modifiers(Arrays.asList(
                createModifier("foo", null).toBuilder().presentAtAllLocations(false)
                    .presentAtLocationIds(Arrays.asList("sourceLocation1")).build(),
                createModifier("bar", null).toBuilder().presentAtAllLocations(false)
                    .presentAtLocationIds(Arrays.asList("sourceLocation1")).build()))
                .build())
            .build();

    CatalogObject targetModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id")
            .presentAtAllLocations(true).presentAtLocationIds(Arrays.asList("targetLocation1"))
            .absentAtLocationIds(Arrays.asList("targetLocation2"))
            .modifierListData(new CatalogModifierList.Builder().modifiers(Arrays.asList(
                createModifier("bar", null).toBuilder().presentAtAllLocations(true)
                    .presentAtLocationIds(Arrays.asList("targetLocation1"))
                    .absentAtLocationIds(Arrays.asList("targetLocation2")).build(),
                createModifier("baz", null).toBuilder().presentAtAllLocations(true)
                    .presentAtLocationIds(Arrays.asList("targetLocation1"))
                    .absentAtLocationIds(Arrays.asList("targetLocation2")).build()))
                .build())
            .build();

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
    assertThat(resultModifier0.getModifierData().getName()).isEqualTo("foo");
    CatalogObject resultModifier1 = resultModifierList.getModifiers().get(1);
    assertThat(resultModifier1.getPresentAtAllLocations()).isTrue();
    assertThat(resultModifier1.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultModifier1.getAbsentAtLocationIds()).containsExactly("targetLocation2");
    assertThat(resultModifier1.getModifierData().getName()).isEqualTo("bar");
    CatalogObject resultModifier2 = resultModifierList.getModifiers().get(2);
    assertThat(resultModifier2.getPresentAtAllLocations()).isTrue();
    assertThat(resultModifier2.getPresentAtLocationIds()).containsExactly("targetLocation1");
    assertThat(resultModifier2.getAbsentAtLocationIds()).containsExactly("targetLocation2");
    assertThat(resultModifier2.getModifierData().getName()).isEqualTo("baz");
  }

  @Test
  public void mergeSourceCatalogObjectIntoTarget_coercesNullAmountToZero() {
    CatalogObject sourceModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id1")
            .modifierListData(
                new CatalogModifierList.Builder().modifiers(
                    Arrays.asList(createModifier("name", null))).build())
            .build();

    CatalogObject targetModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id2")
            .modifierListData(
                new CatalogModifierList.Builder().modifiers(
                    Arrays.asList(createModifier("name", 0L))).build())
            .build();

    CatalogObject resultObject =
        cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceModifierList, targetModifierList);
    assertThat(resultObject).isNull();
  }

  @Test
  public void mergeSourceCatalogObjectIntoTarget_addsModifierIfPriceDifferent() {
    CatalogObject sourceModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id")
            .modifierListData(
                new CatalogModifierList.Builder().modifiers(
                    Arrays.asList(createModifier("name", 0L))).build())
            .build();

    CatalogObject targetModifierList =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id")
            .modifierListData(
                new CatalogModifierList.Builder().modifiers(
                    Arrays.asList(createModifier("name", 100L))).build())
            .build();

    CatalogObject resultObject =
        cloneUtil.mergeSourceCatalogObjectIntoTarget(sourceModifierList, targetModifierList);
    CatalogModifierList resultModifierList = resultObject.getModifierListData();
    assertThat(resultModifierList.getModifiers()).hasSize(2);

    CatalogObject resultModifier0 = resultModifierList.getModifiers().get(1);
    assertThat(resultModifier0.getModifierData().getName()).isEqualTo("name");
    assertThat(resultModifier0.getModifierData().getPriceMoney().getAmount()).isEqualTo(100L);
    CatalogObject resultModifier1 = resultModifierList.getModifiers().get(0);
    assertThat(resultModifier1.getModifierData().getName()).isEqualTo("name");
    assertThat(resultModifier1.getModifierData().getPriceMoney().getAmount()).isEqualTo(0L);
  }

  private CatalogObject createModifier(String name, Long priceAmount) {
    Money priceMoney = (priceAmount == null) ? null : createMoneyObject(priceAmount);
    return new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(), "id")
        .modifierData(new CatalogModifier.Builder().name(name).priceMoney(priceMoney).build())
        .build();
  }
}
