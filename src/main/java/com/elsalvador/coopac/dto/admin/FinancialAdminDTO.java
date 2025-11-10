package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTOs para administración de reportes financieros
 */
public class FinancialAdminDTO {

    // ============= CATEGORY DTOs =============

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Solicitud para crear o actualizar una categoría financiera")
    public static class FinancialReportCategoryRequest {
        @NotBlank(message = "El nombre es requerido")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        @Schema(description = "Nombre de la categoría", example = "Reportes Anuales 2024")
        private String name;

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        @Schema(description = "Descripción de la categoría", example = "Reportes financieros correspondientes al año 2024")
        private String description;

        @NotNull(message = "El estado activo es requerido")
        @Schema(description = "Indica si la categoría está activa", example = "true")
        private Boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Respuesta con datos de una categoría financiera")
    public static class FinancialReportCategoryResponse {
        @Schema(description = "ID único de la categoría", example = "550e8400-e29b-41d4-a716-446655440000")
        private UUID id;

        @Schema(description = "Nombre de la categoría", example = "Reportes Anuales 2024")
        private String name;

        @Schema(description = "Slug único de la categoría para URLs", example = "reportes-anuales-2024")
        private String slug;

        @Schema(description = "Descripción de la categoría")
        private String description;

        @Schema(description = "Orden de visualización", example = "1")
        private Integer displayOrder;

        @Schema(description = "Estado activo de la categoría", example = "true")
        private Boolean isActive;
    }

    // ============= REPORT DTOs =============

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Solicitud para crear un reporte financiero")
    public static class FinancialReportRequest {
        @NotNull(message = "La categoría es requerida")
        @Schema(description = "ID de la categoría del reporte", example = "550e8400-e29b-41d4-a716-446655440000")
        private UUID categoryId;

        @NotBlank(message = "El título es requerido")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        @Schema(description = "Título del reporte", example = "Balance General Anual 2024")
        private String title;

        @Size(max = 2000, message = "El resumen no puede exceder 2000 caracteres")
        @Schema(description = "Resumen o descripción breve del reporte")
        private String summary;

        @Schema(description = "Etiquetas del reporte para categorización adicional", example = "[\"balance\", \"2024\", \"oficial\"]")
        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        @Schema(description = "Indica si el reporte es visible públicamente", example = "true")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        @Schema(description = "Indica si el reporte está activo", example = "true")
        private Boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Solicitud para actualizar un reporte financiero existente")
    public static class FinancialReportUpdateRequest {
        @NotNull(message = "La categoría es requerida")
        @Schema(description = "ID de la categoría del reporte", example = "550e8400-e29b-41d4-a716-446655440000")
        private UUID categoryId;

        @NotBlank(message = "El título es requerido")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        @Schema(description = "Título del reporte", example = "Balance General Anual 2024")
        private String title;

        @Size(max = 2000, message = "El resumen no puede exceder 2000 caracteres")
        @Schema(description = "Resumen del reporte")
        private String summary;

        @Schema(description = "Etiquetas del reporte")
        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        @Schema(description = "Visible públicamente", example = "true")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        @Schema(description = "Activo", example = "true")
        private Boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Respuesta con datos completos de un reporte financiero")
    public static class FinancialReportResponse {
        @Schema(description = "ID único del reporte", example = "550e8400-e29b-41d4-a716-446655440000")
        private UUID id;

        @Schema(description = "ID de la categoría", example = "550e8400-e29b-41d4-a716-446655440000")
        private UUID categoryId;

        @Schema(description = "Nombre de la categoría", example = "Reportes Anuales 2024")
        private String categoryName;

        @Schema(description = "Slug del reporte para URLs", example = "balance-general-anual-2024")
        private String slug;

        @Schema(description = "Título del reporte", example = "Balance General Anual 2024")
        private String title;

        @Schema(description = "Resumen del reporte")
        private String summary;

        @Schema(description = "Fecha de publicación", example = "2024-11-09")
        private LocalDate publishDate;

        @Schema(description = "Formato del archivo", example = "pdf")
        private String fileFormat;

        @Schema(description = "URL del archivo descargable")
        private String fileUrl;

        @Schema(description = "Tamaño del archivo en bytes", example = "2097152")
        private Long fileSizeBytes;

        @Schema(description = "URL de la imagen miniatura")
        private String thumbnailUrl;

        @Schema(description = "Etiquetas del reporte")
        private String[] tags;

        @Schema(description = "Visible públicamente", example = "true")
        private Boolean isPublic;

        @Schema(description = "Estado activo", example = "true")
        private Boolean isActive;

        @Schema(description = "Orden de visualización", example = "1")
        private Integer displayOrder;
    }
}
