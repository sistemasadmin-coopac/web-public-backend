package com.elsalvador.coopac.enums;

import lombok.Getter;

@Getter
public enum FileFormat {
    PDF("PDF"),
    JPG("JPG"),
    PNG("PNG"),
    JPEG("JPEG");

    private final String value;

    FileFormat(String value) {
        this.value = value;
    }

    /**
     * Convierte un string a FileFormat
     */
    public static FileFormat fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (FileFormat format : values()) {
            if (format.value.equalsIgnoreCase(value)) {
                return format;
            }
        }

        throw new IllegalArgumentException("Valor de FileFormat inválido: " + value);
    }
}
