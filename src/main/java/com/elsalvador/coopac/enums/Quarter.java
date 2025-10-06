package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum Quarter {
    Q1("Q1"),
    Q2("Q2"),
    Q3("Q3"),
    Q4("Q4");

    private final String value;

    Quarter(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a Quarter
     */
    public static Quarter fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (Quarter quarter : values()) {
            if (quarter.value.equalsIgnoreCase(value)) {
                return quarter;
            }
        }

        throw new IllegalArgumentException("Valor de Quarter inválido: " + value);
    }
}
