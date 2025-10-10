package com.elsalvador.coopac.exception;

import lombok.Getter;

/**
 * Excepción personalizada para errores de validación de reglas de negocio.
 * Se lanza cuando los datos no cumplen con las reglas específicas del dominio.
 */
@Getter
public class BusinessValidationException extends RuntimeException {

    private final String field;
    private final Object rejectedValue;

    /**
     * Constructor con mensaje personalizado.
     */
    public BusinessValidationException(String message) {
        super(message);
        this.field = null;
        this.rejectedValue = null;
    }

    /**
     * Constructor con campo específico y valor rechazado.
     */
    public BusinessValidationException(String field, Object rejectedValue, String message) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
