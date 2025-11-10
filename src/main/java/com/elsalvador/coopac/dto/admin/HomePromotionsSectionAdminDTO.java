package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/**
 * DTO para HomePromotionsSection en servicios de administración
 */
@Schema(description = "Sección de promociones del home")
public record HomePromotionsSectionAdminDTO(
    @Schema(description = "ID único de la sección", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(description = "Título principal de la sección", example = "Promociones Especiales")
    String titleMain,
    @Schema(description = "Parte del título a resaltar", example = "Especiales")
    String titleHighlight,
    @Schema(description = "Subtítulo de la sección", example = "Descubre nuestras ofertas exclusivas")
    String subtitle,
    @Schema(description = "Si la sección está activa", example = "true")
    Boolean isActive
) {}
