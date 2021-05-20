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

import static com.squareup.catalog.demo.util.Moneys.createMoneyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.squareup.catalog.demo.Logger;
import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.catalog.demo.util.Moneys;
import com.squareup.square.models.CatalogCategory;
import com.squareup.square.models.CatalogDiscount;
import com.squareup.square.models.CatalogImage;
import com.squareup.square.models.CatalogItem;
import com.squareup.square.models.CatalogItemModifierListInfo;
import com.squareup.square.models.CatalogItemVariation;
import com.squareup.square.models.CatalogModifier;
import com.squareup.square.models.CatalogModifierList;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.CatalogTax;

import org.junit.Before;
import org.junit.Test;

public class RetrieveCatalogObjectExampleTest {

  private Logger logger;
  private RetrieveCatalogObjectExample example;

  @Before
  public void setUp() {
    initMocks(this);
    this.logger = mock(Logger.class);
    this.example = new RetrieveCatalogObjectExample(logger);
    Moneys.setCurrency("CAD");
  }

  @Test
  public void logCatalogObjectDetails_category() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.CATEGORY.toString(), "id")
            .categoryData(new CatalogCategory.Builder().name("name").build()).build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_discountAmount() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.DISCOUNT.toString(), "id")
            .discountData(new CatalogDiscount.Builder().name("name")
                .amountMoney(createMoneyObject(1000))
                .build()).build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_discountPercentage() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.DISCOUNT.toString(), "id")
            .discountData(new CatalogDiscount.Builder().name("name").percentage("10").build())
            .build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_item() {
    List<CatalogObject> variations = new ArrayList<>();
    variations.add(new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(), "var0")
        .itemVariationData(new CatalogItemVariation.Builder().name("var0name").build()).build());

    variations.add(new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(), "var1")
        .itemVariationData(new CatalogItemVariation.Builder().name("var1name").build()).build());

    List<CatalogItemModifierListInfo> modifiers = new ArrayList<>();
    modifiers.add(new CatalogItemModifierListInfo.Builder("modlist0").enabled(true).build());

    modifiers.add(new CatalogItemModifierListInfo.Builder("modlist1").enabled(false).build());

    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "id")
            .itemData(new CatalogItem.Builder().name("name")
                .categoryId("category0")
                .taxIds(Arrays.asList("tax0", "tax1"))
                .variations(variations)
                .modifierListInfo(modifiers)
                .build())
            .build();

    HashMap<String, CatalogObject> relatedObjects = new HashMap<>();
    relatedObjects.put("category0",
        new CatalogObject.Builder(CatalogObjectTypes.CATEGORY.toString(), "category0")
            .categoryData(new CatalogCategory.Builder().name("category0_name").build()).build());
    relatedObjects.put("tax0", new CatalogObject.Builder(CatalogObjectTypes.TAX.toString(), "tax0")
        .taxData(new CatalogTax.Builder().name("tax0").percentage("10").build()).build());
    relatedObjects.put("tax1", new CatalogObject.Builder(CatalogObjectTypes.TAX.toString(), "tax1")
        .taxData(new CatalogTax.Builder().name("tax1").percentage("3").build()).build());

    relatedObjects.put("modlist0",
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(), "modlist0")
            .modifierListData(new CatalogModifierList.Builder().name("name").build()).build());
    relatedObjects.put("modlist1",
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(), "modlist1")
            .modifierListData(new CatalogModifierList.Builder().name("name").build()).build());

    example.logCatalogObjectDetails(catalogObject, relatedObjects);
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_itemWithoutRelatedObjects() {
    List<CatalogObject> variations = new ArrayList<>();
    variations.add(
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(), "variation0")
            .itemVariationData(new CatalogItemVariation.Builder().name("variation0_name").build())
            .build());

    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "id")
            .itemData(new CatalogItem.Builder().name("name").variations(variations).build())
            .build();

    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_itemVariation() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.ITEM_VARIATION.toString(), "id")
            .itemVariationData(
                new CatalogItemVariation.Builder().itemId("itemId").name("name").sku("sku")
                    .priceMoney(createMoneyObject(1000)).build())
            .build();

    // Add the item as a related object.
    HashMap<String, CatalogObject> relatedObjects = new HashMap<>();
    relatedObjects.put("itemId",
        new CatalogObject.Builder(CatalogObjectTypes.ITEM.toString(), "itemId")
            .itemData(new CatalogItem.Builder().name("item_name").build()).build());

    example.logCatalogObjectDetails(catalogObject, relatedObjects);
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_modifier() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(), "id")
            .modifierData(new CatalogModifier.Builder().name("name").build()).build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_modifierList() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.MODIFIER_LIST.toString(), "id")
            .modifierListData(new CatalogModifierList.Builder().name("name").selectionType("SINGLE")
                .modifiers(Arrays.asList(
                    new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(), "modifier0")
                        .modifierData(new CatalogModifier.Builder().name("modifier0_name").build())
                        .build(),
                    new CatalogObject.Builder(CatalogObjectTypes.MODIFIER.toString(),
                        "modifier1").modifierData(
                        new CatalogModifier.Builder().name("modifier1_name")
                            .priceMoney(createMoneyObject(1000))
                            .build())
                        .build()))
                .build())
            .build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_tax() {
    CatalogObject catalogObject = new CatalogObject.Builder(CatalogObjectTypes.TAX.toString(), "id")
        .taxData(new CatalogTax.Builder().name("name").percentage("10").build()).build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test
  public void logCatalogObjectDetails_image() {
    CatalogObject catalogObject =
        new CatalogObject.Builder(CatalogObjectTypes.IMAGE.toString(), "id")
            .imageData(new CatalogImage.Builder().name("name")
                .url("www.example.com")
                .caption("caption")
                .build()).build();
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }
}
