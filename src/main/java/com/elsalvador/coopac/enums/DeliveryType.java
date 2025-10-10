package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum DeliveryType {
    BINARY("binary"),
    URL("url");

    private final String value;

    DeliveryType(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a DeliveryType
     */
    public static DeliveryType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (DeliveryType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Valor de DeliveryType inválido: " + value);
    }
}
