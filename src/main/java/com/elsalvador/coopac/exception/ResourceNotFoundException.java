package com.elsalvador.coopac.exception;

import lombok.Getter;

/**
 * Excepción personalizada para recursos no encontrados.
 * Se lanza cuando se solicita un recurso que no existe en el sistema.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceType;
    private final String resourceId;

    /**
     * Constructor con mensaje personalizado.
     */
    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceType = null;
        this.resourceId = null;
    }

    /**
     * Constructor con tipo y ID del recurso.
     */
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super("No se encontró " + resourceType + " con ID: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    /**
     * Constructor con tipo, ID y mensaje personalizado.
     */
    public ResourceNotFoundException(String resourceType, String resourceId, String message) {
        super(message);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}
