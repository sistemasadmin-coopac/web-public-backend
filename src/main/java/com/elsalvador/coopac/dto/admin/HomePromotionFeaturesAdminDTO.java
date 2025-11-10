package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

/**
 * DTO para HomePromotionFeatures en servicios de administración
 */
@Schema(description = "Característica/feature de una promoción del home")
public record HomePromotionFeaturesAdminDTO(
    @Schema(description = "ID único de la característica", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(description = "ID de la promoción a la que pertenece", example = "550e8400-e29b-41d4-a716-446655440001")
    UUID promotionId,
    @Schema(description = "Texto de la característica", example = "Acceso prioritario a nuevos productos")
    String featureText
) {}
