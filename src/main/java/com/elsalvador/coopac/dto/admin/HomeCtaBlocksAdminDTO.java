package com.elsalvador.coopac.dto.admin;

import java.util.UUID;

/**
 * DTOs para administración de bloques CTA del home
 */
public class HomeCtaBlocksAdminDTO {

    /**
     * DTO para respuesta de bloque CTA
     */
    public record HomeCtaBlockResponseDTO(
            UUID id,
            String position,
            String title,
            String subtitle,
            String buttonText,
            String buttonUrl,
            Integer displayOrder,
            Boolean isActive
    ) {}

    /**
     * DTO para actualización de bloque CTA
     */
    public record UpdateHomeCtaBlockDTO(
            String title,
            String subtitle,
            String buttonText,
            String buttonUrl,
            Integer displayOrder,
            Boolean isActive
    ) {}
}
