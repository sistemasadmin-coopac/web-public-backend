package com.elsalvador.coopac.dto.admin;

import java.util.UUID;

/**
 * DTO para PageHeader en servicios de administración
 */
public record PageHeaderAdminDTO(
    UUID id,
    String badgeText,
    String titleMain,
    String titleHighlight,
    String subtitle,
    String description,
    String primaryCtaText,
    String primaryCtaUrl,
    String secondaryCtaText,
    String secondaryCtaUrl
) {}
