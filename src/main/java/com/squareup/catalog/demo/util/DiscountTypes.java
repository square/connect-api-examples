package com.squareup.catalog.demo.util;

import com.squareup.square.models.CatalogDiscount;

/**
 * Utility enum to capture different {@link CatalogDiscount} types.
 */
public enum DiscountTypes {
    FIXED_AMOUNT("FIXED_AMOUNT"),
    FIXED_PERCENTAGE("FIXED_PERCENTAGE"),
    VARIABLE_AMOUNT("VARIABLE_AMOUNT"),
    VARIABLE_PERCENTAGE("VARIABLE_PERCENTAGE");

    private final String type;

    private DiscountTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
