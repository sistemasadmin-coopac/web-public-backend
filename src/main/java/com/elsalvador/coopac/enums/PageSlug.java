package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum PageSlug {
    HOME("home"),
    PRODUCTS("products"),
    FINANCIALS("financials"),
    ABOUT("about"),
    CONTACT("contact");

    private final String value;

    PageSlug(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a PageSlug
     */
    public static PageSlug fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (PageSlug slug : values()) {
            if (slug.value.equalsIgnoreCase(value)) {
                return slug;
            }
        }

        throw new IllegalArgumentException("Valor de PageSlug inválido: " + value);
    }
}
