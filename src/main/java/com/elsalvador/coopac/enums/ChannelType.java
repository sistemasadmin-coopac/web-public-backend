package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
    PHONE("phone"),
    WHATSAPP("whatsapp"),
    EMAIL("email"),
    LOCATION("location");

    private final String value;

    ChannelType(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a ChannelType
     */
    public static ChannelType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (ChannelType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Valor de ChannelType inválido: " + value);
    }
}
