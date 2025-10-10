package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum Position {
    MID("mid"),
    FINAL("final");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a Position
     */
    public static Position fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (Position position : values()) {
            if (position.value.equalsIgnoreCase(value)) {
                return position;
            }
        }

        throw new IllegalArgumentException("Valor de Position inválido: " + value);
    }
}
