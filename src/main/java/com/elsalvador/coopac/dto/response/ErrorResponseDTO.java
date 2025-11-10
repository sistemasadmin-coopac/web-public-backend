package com.elsalvador.coopac.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO estándar para respuestas de error en la API
 */
@Schema(
    description = "Respuesta de error estándar de la API",
    example = """
        {
          "code": "RESOURCE_NOT_FOUND",
          "message": "El recurso solicitado no fue encontrado",
          "details": null
        }
        """
)
public record ErrorResponseDTO(
        @Schema(
            description = "Código de error interno",
            example = "RESOURCE_NOT_FOUND"
        )
        String code,

        @Schema(
            description = "Mensaje de error legible",
            example = "El recurso solicitado no fue encontrado"
        )
        String message,

        @Schema(
            description = "Detalles adicionales del error (opcional)",
            example = "null"
        )
        String details
) {
    public ErrorResponseDTO(String code, String message) {
        this(code, message, null);
    }
}

