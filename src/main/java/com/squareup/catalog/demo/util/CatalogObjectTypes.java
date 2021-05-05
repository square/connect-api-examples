package com.squareup.catalog.demo.util;

import com.squareup.square.models.CatalogObject;

/**
 * Utility enum to capture different {@link CatalogObject} types.
 */
public enum CatalogObjectTypes {
    ITEM("ITEM"),
    ITEM_VARIATION("ITEM_VARIATION"),
    CATEGORY("CATEGORY"),
    TAX("TAX"),
    DISCOUNT("DISCOUNT"),
    MODIFIER("MODIFIER"),
    MODIFIER_LIST("MODIFIER_LIST"),
    IMAGE("IMAGE");

    private final String type;

    private CatalogObjectTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
