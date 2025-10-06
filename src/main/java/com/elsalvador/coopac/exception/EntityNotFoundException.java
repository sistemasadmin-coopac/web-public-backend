package com.elsalvador.coopac.exception;

/**
 * Excepción lanzada cuando una entidad no es encontrada
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
