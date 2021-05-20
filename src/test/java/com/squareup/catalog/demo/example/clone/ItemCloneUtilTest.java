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
import java.util.List;

import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogObject;

import org.junit.Before;
import org.junit.Test;

public class ItemCloneUtilTest {

  private ItemCloneUtil cloneUtil;

  @Before
  public void setUp() {
    this.cloneUtil = new ItemCloneUtil();
    Moneys.setCurrency("CAD");
  }

  @Test
  public void encodeCatalogData_withVariations() {
    List<CatalogObject> variations = new ArrayList<>();

    CatalogObject itemVariation1 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation1")
                .priceMoney(Moneys.createMoneyObject(1000))
                .build())
            .build();

    CatalogObject itemVariation2 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation2")
                .priceMoney(Moneys.createMoneyObject(2000))
                .build())
            .build();

    variations.add(itemVariation2);
    variations.add(itemVariation1);

    CatalogItem item =
        new CatalogItem.Builder().name("itemName").description("some description about the item")
            .variations(variations).build();

    CatalogObject object =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "itemName")
            .id("#id")
            .itemData(item)
            .build();

    assertThat(cloneUtil.encodeCatalogObject(object))
        .isEqualTo("itemName:::some description about the item:::itemVariation1::itemVariation2");
  }

  @Test
  public void encodeCatalogData_noVariations() {
    CatalogItem item =
        new CatalogItem.Builder()
            .name("itemName")
            .description("some description about the item")
            .variations(null)
            .build();

    CatalogObject object =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "itemName")
            .id("#id")
            .itemData(item)
            .build();

    assertThat(cloneUtil.encodeCatalogObject(object)).isEqualTo(
        "itemName:::some description about the item:::");
  }

  @Test
  public void encodeCatalogData_noDescription() {
    List<CatalogObject> variations = new ArrayList<>();

    CatalogObject itemVariation1 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation1")
                .priceMoney(Moneys.createMoneyObject(1000))
                .build())
            .build();

    CatalogObject itemVariation2 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation2")
                .priceMoney(Moneys.createMoneyObject(2000))
                .build())
            .build();

    variations.add(itemVariation1);
    variations.add(itemVariation2);

    CatalogItem item = new CatalogItem.Builder().name("itemName").variations(variations).build();

    CatalogObject object =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "itemName")
            .id("#id")
            .itemData(item)
            .build();

    assertThat(cloneUtil.encodeCatalogObject(object)).isEqualTo(
        "itemName:::null:::itemVariation1::itemVariation2");
  }

  @Test
  public void removeSourceAccountMetaData() {
    List<CatalogObject> variations = new ArrayList<>();

    CatalogObject itemVariation1 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation1")
                .priceMoney(Moneys.createMoneyObject(1000))
                .build())
            .build();

    CatalogObject itemVariation2 =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(),
            "#variationID")
            .itemVariationData(new CatalogItemVariation.Builder()
                .name("itemVariation2")
                .priceMoney(Moneys.createMoneyObject(2000))
                .build())
            .build();

    variations.add(itemVariation2);
    variations.add(itemVariation1);

    CatalogItem item =
        new CatalogItem.Builder()
            .name("itemName")
            .description("some description about the item")
            .variations(variations)
            .build();

    CatalogObject object =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "itemName")
            .id("#id")
            .itemData(item)
            .build();

    CatalogObject result = cloneUtil.removeSourceAccountMetaData(object);
    assertThat(result.getPresentAtAllLocations()).isTrue();
    assertThat(result.getPresentAtLocationIds()).isEmpty();
    assertThat(result.getItemData().getTaxIds()).isEmpty();
    assertThat(result.getId()).startsWith("#");
    assertThat(result.getItemData().getVariations()).hasSize(2);
    assertThat(result.getItemData().getVariations().get(0).getId()).startsWith("#");
    assertThat(result.getItemData().getVariations().get(1).getId()).startsWith("#");
    assertThat(result.getItemData().getVariations().get(0).getPresentAtLocationIds()).isEmpty();
    assertThat(result.getItemData().getVariations().get(1).getPresentAtLocationIds()).isEmpty();
  }
}
