package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

/**
 * DTO para HomePromotions en servicios de administración
 */
@Schema(description = "Promoción del home")
public record HomePromotionsAdminDTO(
    @Schema(description = "ID único de la promoción", example = "550e8400-e29b-41d4-a716-446655440000")
    UUID id,
    @Schema(description = "ID de la sección a la que pertenece", example = "550e8400-e29b-41d4-a716-446655440001")
    UUID sectionId,
    @Schema(description = "Título de la promoción", example = "Oferta Especial de Verano")
    String title,
    @Schema(description = "Etiqueta o badge de la promoción", example = "Destacado, Nuevo, Limited")
    String tag,
    @Schema(description = "Descripción de la promoción", example = "Descuento especial válido solo este mes")
    String description,
    @Schema(description = "Texto resaltado en la promoción", example = "Hasta 50% de descuento")
    String highlightText,
    @Schema(description = "Si la promoción está destacada", example = "true")
    Boolean isFeatured,
    @Schema(description = "Si la promoción está activa", example = "true")
    Boolean isActive,
    @Schema(description = "Lista de características de la promoción")
    List<HomePromotionFeaturesAdminDTO> features,
    @Schema(description = "Imagen en Base64 para lectura (solo respuesta)", example = "data:image/png;base64,iVBORw0KGg...")
    String imageBase64
) {}
