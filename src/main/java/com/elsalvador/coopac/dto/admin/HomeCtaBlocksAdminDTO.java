package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/**
 * DTOs para administración de bloques CTA del home
 */
public class HomeCtaBlocksAdminDTO {

    /**
     * DTO para respuesta de bloque CTA
     */
    @Schema(description = "Bloque CTA (Call To Action) del home")
    public record HomeCtaBlockResponseDTO(
            @Schema(description = "ID único del bloque CTA", example = "550e8400-e29b-41d4-a716-446655440000")
            UUID id,
            @Schema(description = "Posición del bloque", example = "top, middle, bottom")
            String position,
            @Schema(description = "Título del bloque CTA", example = "¡Únete a nosotros hoy!")
            String title,
            @Schema(description = "Subtítulo del bloque", example = "Aprovecha esta oportunidad limitada")
            String subtitle,
            @Schema(description = "Texto del botón", example = "Comenzar ahora")
            String buttonText,
            @Schema(description = "URL del botón", example = "https://ejemplo.com/registro")
            String buttonUrl,
            @Schema(description = "Estado activo del bloque", example = "true")
            Boolean isActive
    ) {}

    /**
     * DTO para actualización de bloque CTA
     */
    @Schema(description = "Datos para actualizar un bloque CTA")
    public record UpdateHomeCtaBlockDTO(
            @Schema(description = "Título del bloque CTA", example = "¡Únete a nosotros hoy!")
            String title,
            @Schema(description = "Subtítulo del bloque", example = "Aprovecha esta oportunidad limitada")
            String subtitle,
            @Schema(description = "Texto del botón", example = "Comenzar ahora")
            String buttonText,
            @Schema(description = "URL del botón", example = "https://ejemplo.com/registro")
            String buttonUrl,
            @Schema(description = "Estado activo", example = "true")
            Boolean isActive
    ) {}
}
