package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

/**
 * DTO para PageHeader en servicios de administración
 */
@Schema(description = "Datos del header de una página")
public record PageHeaderAdminDTO(
    @Schema(description = "ID único del header", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,

    @Schema(description = "Texto del badge/etiqueta", example = "Nuevo")
    String badgeText,

    @Schema(description = "Título principal del header", example = "Bienvenido a nuestra plataforma")
    String titleMain,

    @Schema(description = "Parte del título que debe resaltarse", example = "plataforma")
    String titleHighlight,

    @Schema(description = "Subtítulo del header", example = "Descubre las mejores soluciones")
    String subtitle,

    @Schema(description = "Descripción detallada", example = "Ofrecemos servicios de alta calidad para tu negocio")
    String description,

    @Schema(description = "Texto del botón de acción principal", example = "Comenzar ahora")
    String primaryCtaText,

    @Schema(description = "URL del botón de acción principal", example = "https://ejemplo.com/comenzar")
    String primaryCtaUrl,

    @Schema(description = "Texto del botón de acción secundario", example = "Más información")
    String secondaryCtaText,

    @Schema(description = "URL del botón de acción secundario", example = "https://ejemplo.com/info")
    String secondaryCtaUrl
) {}
