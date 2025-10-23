package com.elsalvador.coopac.dto.admin;

import java.util.UUID;

/**
 * DTO para HomePromotionFeatures en servicios de administración
 */
public record HomePromotionFeaturesAdminDTO(
    UUID id,
    UUID promotionId,
    String featureText
) {}
