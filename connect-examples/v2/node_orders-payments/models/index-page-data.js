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

const CatalogItemVariation = require("./catalog-item-variation");
const LocationInfo = require("./location-info");

/**
 * Description:
 *  An instance of this IndexPageData  provides the information necessary to render
 *  views/index.pug
 *
 * Parameters:
 *  catalogList:  Array of Catalog objects returned from ListCatalog api
 *  locationObj:  First location in returned from ListLocations api
 *
 * Learn more about the ListCatalog api here: https://developer.squareup.com/docs/api/connect/v2#endpoint-catalog-listcatalog
 *
 */
class IndexPageData {
  constructor(catalogList, locationObj){
    // Array of items, we are using the default variation as our only choice for the item
    this.items = [];
    this.populateItems(catalogList);
    this.location = new LocationInfo(locationObj);
  }

  populateItems(catalogList){
    if (catalogList.objects){
      // Separate out the CatalogImages and the CatalogItems
      const catalogItemObjects = catalogList.objects.filter( obj => obj.type === "ITEM");
      const catalogImageObjects = catalogList.objects.filter( obj => obj.type === "IMAGE");

      // For a shorter look time, we will convert the array of CatalogImageObjects, into a map
      // where the keys are the CatalogImageObjects ids and the value are the CatalogImageObjects
      const catalogImageObjectsMap = catalogImageObjects.reduce((map, imageObject)=>{
        map[imageObject.id] = imageObject;
        return map;
      }, {});

      // Reassigns this.items to be an array of CatalogItemVariation instances
      this.items = catalogItemObjects.map( item => {
        const imageObject = catalogImageObjectsMap[item.image_id];
        return new CatalogItemVariation(item, imageObject);
      });
    }
  }
}



module.exports = IndexPageData;
