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

import java.util.Collections;
import java.util.UUID;

import com.squareup.catalog.demo.util.CatalogObjectTypes;
import com.squareup.square.models.CatalogObject;
import com.squareup.square.models.Money;

/**
 * Defines utility methods used when cloning a CatalogObject from one merchant account to another.
 *
 * @param <T> The catalog object data type
 */
abstract class CatalogObjectCloneUtil<T> {

  final CatalogObjectTypes type;

  CatalogObjectCloneUtil(CatalogObjectTypes type) {
    this.type = type;
  }

  /**
   * Returns the type specific data from a catalog object.
   */
  abstract T getCatalogData(CatalogObject catalogObject);

  /**
   * Encodes a subset of fields from the {@link CatalogObject} into a string, allowing
   * CatalogObjects to be compared between merchant accounts for likeness. If the encoded string of
   * a {@link CatalogObject} in the source account matches the encoded string of a {@link
   * CatalogObject} in the target account, then the object is not cloned.
   */
  final String encodeCatalogObject(CatalogObject catalogObject) {
    T catalogData = getCatalogData(catalogObject);
    return encodeCatalogData(catalogData);
  }

  /**
   * Encodes a subset of fields from the {@link CatalogObject} into a string, allowing
   * CatalogObjects to be compared between merchant accounts for likeness. If the encoded string of
   * a {@link CatalogObject} in the source account matches the encoded string of a {@link
   * CatalogObject} in the target account, then the object is not cloned.
   *
   * @param catalogData the data from the {@link CatalogObject}.
   */
  abstract String encodeCatalogData(T catalogData);

  /**
   * Encodes a {@link Money} object to a string.
   *
   * @param money the {@link Money} object to encode
   * @return the amount as a string, or null if the {@link Money} object is null
   */
  String amountOrNull(Money money) {
    return (money == null) ? "null" : money.getAmount().toString();
  }

  /**
   * Removes meta data from the {@link CatalogObject} that only applies to the source account, such
   * as location IDs and version.
   *
   * @param catalogObject the {@link CatalogObject} to modify
   */
  CatalogObject removeSourceAccountMetaData(CatalogObject catalogObject) {
    return catalogObject.toBuilder()
        // We need to set a temporary client generated ID.
        .id("#" + UUID.randomUUID())
        // The V1 IDs from the source account do not apply to the target account.
        .catalogV1Ids(Collections.emptyList())
        // The location IDs from the source account do not apply to the target account.
        .presentAtLocationIds(Collections.emptyList()).absentAtLocationIds(Collections.emptyList())
        // The server will assign a version.
        .version(null)
        // The server will update the timestamp.
        .updatedAt(null).build();
  }

  /**
   * Merges data from a {@link CatalogObject} from the source account into a {@link CatalogObject}
   * from the target account when they have the same encoded string. This method can be used to
   * merge two objects that have the same name, but slightly different attributes. For example, if a
   * modifier list in the source account has the same name as a modifier list in the target account,
   * this method is used to copy the modifiers from the source account to the target account.
   *
   * @param sourceCatalogObject the {@link CatalogObject} from the source account
   * @param targetCatalogObject the {@link CatalogObject} from the target account
   * @return the modifier target {@link CatalogObject}, or null if nothing changed
   */
  CatalogObject mergeSourceCatalogObjectIntoTarget(CatalogObject sourceCatalogObject,
      CatalogObject targetCatalogObject) {
    // Default implementation assumes that the objects do not need to be merged.
    return null;
  }
}
