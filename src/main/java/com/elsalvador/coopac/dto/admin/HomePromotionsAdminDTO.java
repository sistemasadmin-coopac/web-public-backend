package com.elsalvador.coopac.dto.admin;

import java.util.List;
import java.util.UUID;

/**
 * DTO para HomePromotions en servicios de administración
 */
public record HomePromotionsAdminDTO(
    UUID id,
    UUID sectionId,
    String title,
    String tag,
    String description,
    String highlightText,
    Boolean isFeatured,
    Boolean isActive,
    List<HomePromotionFeaturesAdminDTO> features,
    String imageBase64  // Imagen en Base64 solo para lectura/respuesta
) {}
