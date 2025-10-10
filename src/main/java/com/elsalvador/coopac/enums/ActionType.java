package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum ActionType {
    navigate("navigate"),
    phone("phone"),
    whatsapp("whatsapp");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a ActionType
     */
    public static ActionType fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (ActionType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Valor de ActionType inválido: " + value);
    }
}
