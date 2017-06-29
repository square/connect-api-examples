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

import com.squareup.catalog.demo.Logger;
import com.squareup.connect.models.CatalogCategory;
import com.squareup.connect.models.CatalogDiscount;
import com.squareup.connect.models.CatalogItem;
import com.squareup.connect.models.CatalogItemModifierListInfo;
import com.squareup.connect.models.CatalogItemVariation;
import com.squareup.connect.models.CatalogModifier;
import com.squareup.connect.models.CatalogModifierList;
import com.squareup.connect.models.CatalogObject;
import com.squareup.connect.models.CatalogTax;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.squareup.catalog.demo.util.Moneys.usd;
import static com.squareup.connect.models.CatalogObject.TypeEnum.CATEGORY;
import static com.squareup.connect.models.CatalogObject.TypeEnum.DISCOUNT;
import static com.squareup.connect.models.CatalogObject.TypeEnum.ITEM;
import static com.squareup.connect.models.CatalogObject.TypeEnum.ITEM_VARIATION;
import static com.squareup.connect.models.CatalogObject.TypeEnum.MODIFIER;
import static com.squareup.connect.models.CatalogObject.TypeEnum.MODIFIER_LIST;
import static com.squareup.connect.models.CatalogObject.TypeEnum.TAX;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class RetrieveCatalogObjectExampleTest {

  @Mock Logger logger;
  private RetrieveCatalogObjectExample example;

  @Before public void setUp() {
    initMocks(this);
    this.example = new RetrieveCatalogObjectExample(logger);
  }

  @Test public void logCatalogObjectDetails_category() {
    CatalogObject catalogObject = catalogObject("id", CATEGORY)
        .categoryData(new CatalogCategory()
            .name("name"));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_discountAmount() {
    CatalogObject catalogObject = catalogObject("id", DISCOUNT)
        .discountData(new CatalogDiscount()
            .name("name")
            .amountMoney(usd(1000)));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_discountPercentage() {
    CatalogObject catalogObject = catalogObject("id", DISCOUNT)
        .discountData(new CatalogDiscount()
            .name("name")
            .percentage("10"));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_item() {
    CatalogObject catalogObject = catalogObject("id", ITEM)
        .itemData(new CatalogItem()
            .name("name")
            .categoryId("category0")
            .addTaxIdsItem("tax0")
            .addTaxIdsItem("tax1")
            .addVariationsItem(catalogObject("variation0", ITEM_VARIATION)
                .itemVariationData(new CatalogItemVariation()
                    .name("variation0_name")))
            .addVariationsItem(catalogObject("variation1", ITEM_VARIATION)
                .itemVariationData(new CatalogItemVariation()
                    .name("variation1_name")))
            .addModifierListInfoItem(new CatalogItemModifierListInfo()
                .modifierListId("modifierList0")
                .enabled(true))
            .addModifierListInfoItem(new CatalogItemModifierListInfo()
                .modifierListId("modifierList1")
                .enabled(false)));

    HashMap<String, CatalogObject> relatedObjects = new HashMap<>();
    relatedObjects.put("category0", catalogObject("category0", CATEGORY)
        .categoryData(new CatalogCategory().name("category0_name")));
    relatedObjects.put("tax0", catalogObject("tax0", TAX)
        .taxData(new CatalogTax().name("name").percentage("10")));
    relatedObjects.put("tax1", catalogObject("tax1", TAX)
        .taxData(new CatalogTax().name("name").percentage("3")));
    relatedObjects.put("modifierList0", catalogObject("tax0", MODIFIER)
        .modifierListData(new CatalogModifierList().name("name")));
    relatedObjects.put("modifierList1", catalogObject("tax1", MODIFIER)
        .modifierListData(new CatalogModifierList().name("name")));

    example.logCatalogObjectDetails(catalogObject, relatedObjects);
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_itemWithoutRelatedObjects() {
    CatalogObject catalogObject = catalogObject("id", ITEM)
        .itemData(new CatalogItem()
            .name("name")
            .addVariationsItem(catalogObject("variation0", ITEM_VARIATION)
                .itemVariationData(new CatalogItemVariation()
                    .name("variation0_name"))));

    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_itemVariation() {
    CatalogObject catalogObject = catalogObject("id", ITEM_VARIATION)
        .itemVariationData(new CatalogItemVariation()
            .name("name")
            .sku("sku")
            .priceMoney(usd(1000)));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_modifier() {
    CatalogObject catalogObject = catalogObject("id", MODIFIER)
        .modifierData(new CatalogModifier()
            .name("name"));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_modifierList() {
    CatalogObject catalogObject = catalogObject("id", MODIFIER_LIST)
        .modifierListData(new CatalogModifierList()
            .name("name")
            .selectionType(CatalogModifierList.SelectionTypeEnum.SINGLE)
            .modifiers(Arrays.asList(
                catalogObject("modifier0", MODIFIER)
                    .modifierData(new CatalogModifier()
                        .name("modifier0_name")),
                catalogObject("modifier1", MODIFIER)
                    .modifierData(new CatalogModifier()
                        .name("modifier1_name")
                        .priceMoney(usd(1000)))
            )));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  @Test public void logCatalogObjectDetails_tax() {
    CatalogObject catalogObject = catalogObject("id", TAX)
        .taxData(new CatalogTax()
            .name("name")
            .percentage("10"));
    example.logCatalogObjectDetails(catalogObject, Collections.emptyMap());
    verify(logger).info(anyString());
  }

  private CatalogObject catalogObject(String id, CatalogObject.TypeEnum type) {
    return new CatalogObject().id(id).type(type);
  }
}

