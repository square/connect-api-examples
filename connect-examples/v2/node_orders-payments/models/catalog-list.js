/*
Copyright 2019 Square Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

const CatalogItem = require("./catalog-item");

/**
 * Description:
 *  An instance of this CatalogList provides the catalog list data to render
 *  views/index.pug
 *
 * Parameters:
 *  objects:  Array of Catalog objects returned from ListCatalog api
 *
 * Learn more about the ListCatalog api here: https://developer.squareup.com/reference/square/catalog-api/list-catalog
 *
 */
class CatalogList {
  constructor(catalogList) {
    // Array of items, we are using the default variation as our only choice for the item
    this.items = [];
    this.populateItems(catalogList);
  }

  populateItems(objects) {
    if (!objects || objects.length === 0) return;

    // Separate out the CatalogImages and the CatalogItems
    const catalogItemObjects = objects.filter(
      // In this example, we assume no item has more than one variation and we don't display any item that has no variation
      obj => obj.type === "ITEM" && obj.itemData.variations && obj.itemData.variations.length > 0);
    const catalogImageObjects = objects.filter(obj => obj.type === "IMAGE");

    // For a shorter look time, we will convert the array of CatalogImageObjects, into a map
    // where the keys are the CatalogImageObjects ids and the value are the CatalogImageObjects
    const catalogImageObjectsMap = catalogImageObjects.reduce((map, imageObject) => {
      map[imageObject.id] = imageObject;
      return map;
    }, {});

    // Reassigns this.items to be an array of CatalogItem instances
    this.items = catalogItemObjects.map(item => {
      let imageObject = null;
      if (item.itemData.imageIds) {
        imageObject = catalogImageObjectsMap[item.itemData.imageIds[0]];
      }
      return new CatalogItem(item, imageObject);
    });
  }
}

module.exports = CatalogList;
