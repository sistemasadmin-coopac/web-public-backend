package com.elsalvador.coopac.dto.admin;

import java.util.UUID;

/**
 * DTO para PageHeaderCards en servicios de administración
 */
public record PageHeaderCardsAdminDTO(
    UUID id,
    String icon,
    String title,
    String description
) {}
