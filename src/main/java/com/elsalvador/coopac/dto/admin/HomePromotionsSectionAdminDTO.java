package com.elsalvador.coopac.dto.admin;

import java.util.UUID;

/**
 * DTO para HomePromotionsSection en servicios de administración
 */
public record HomePromotionsSectionAdminDTO(
    UUID id,
    String titleMain,
    String titleHighlight,
    String subtitle,
    Boolean isActive
) {}
