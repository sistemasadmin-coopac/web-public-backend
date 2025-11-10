package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO para PageHeaderCards en servicios de administración
 */
@Schema(description = "Tarjeta/Card del header de una página")
public record PageHeaderCardsAdminDTO(
    @Schema(description = "ID único de la tarjeta", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,

    @Schema(description = "Icono de la tarjeta", example = "star")
    String icon,

    @Schema(description = "Título de la tarjeta", example = "Calidad Premium")
    String title,

    @Schema(description = "Descripción de la tarjeta", example = "Acceso a todas las características premium")
    String description
) {}
