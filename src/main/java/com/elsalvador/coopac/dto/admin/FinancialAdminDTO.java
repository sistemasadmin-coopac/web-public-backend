package com.elsalvador.coopac.dto.admin;

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
    public static class FinancialReportCategoryRequest {
        @NotBlank(message = "El nombre es requerido")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        private String name;

        @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
        private String description;

        @NotNull(message = "El orden de visualización es requerido")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialReportCategoryResponse {
        private UUID id;
        private String name;
        private String slug;
        private String description;
        private Integer displayOrder;
        private Boolean isActive;
    }

    // ============= REPORT DTOs =============

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialReportRequest {
        @NotNull(message = "La categoría es requerida")
        private UUID categoryId;

        @NotBlank(message = "El título es requerido")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        private String title;

        @Size(max = 2000, message = "El resumen no puede exceder 2000 caracteres")
        private String summary;

        @Min(value = 1900, message = "El año debe ser mayor a 1900")
        @Max(value = 2100, message = "El año debe ser menor a 2100")
        private Integer year;

        @Pattern(regexp = "^(Q[1-4])?$", message = "El trimestre debe ser Q1, Q2, Q3 o Q4")
        private String quarter;

        private LocalDate publishDate;

        @NotBlank(message = "El tipo de entrega es requerido")
        @Pattern(regexp = "^(file|link|binary)$", message = "El tipo de entrega debe ser 'file', 'link' o 'binary'")
        private String deliveryType;

        @NotBlank(message = "El formato de archivo es requerido")
        @Size(max = 10, message = "El formato no puede exceder 10 caracteres")
        private String fileFormat;

        @NotBlank(message = "La URL del archivo es requerida")
        @Size(max = 1000, message = "La URL no puede exceder 1000 caracteres")
        private String fileUrl;

        private Long fileSizeBytes;

        @Size(max = 1000, message = "La URL de la miniatura no puede exceder 1000 caracteres")
        private String thumbnailUrl;

        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;

        @NotNull(message = "El orden de visualización es requerido")
        private Integer displayOrder;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialReportUpdateRequest {
        @NotNull(message = "La categoría es requerida")
        private UUID categoryId;

        @NotBlank(message = "El título es requerido")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        private String title;

        @Size(max = 2000, message = "El resumen no puede exceder 2000 caracteres")
        private String summary;

        @Min(value = 1900, message = "El año debe ser mayor a 1900")
        @Max(value = 2100, message = "El año debe ser menor a 2100")
        private Integer year;

        @Pattern(regexp = "^(Q[1-4])?$", message = "El trimestre debe ser Q1, Q2, Q3 o Q4")
        private String quarter;

        private LocalDate publishDate;

        @NotBlank(message = "El tipo de entrega es requerido")
        @Pattern(regexp = "^(file|link|binary)$", message = "El tipo de entrega debe ser 'file', 'link' o 'binary'")
        private String deliveryType;

        @Size(max = 10, message = "El formato no puede exceder 10 caracteres")
        private String fileFormat;

        @Size(max = 1000, message = "La URL no puede exceder 1000 caracteres")
        private String fileUrl;

        private Long fileSizeBytes;

        @Size(max = 1000, message = "La URL de la miniatura no puede exceder 1000 caracteres")
        private String thumbnailUrl;

        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;

        @NotNull(message = "El orden de visualización es requerido")
        private Integer displayOrder;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialReportResponse {
        private UUID id;
        private UUID categoryId;
        private String categoryName;
        private String slug;
        private String title;
        private String summary;
        private Integer year;
        private String quarter;
        private LocalDate publishDate;
        private String deliveryType;
        private String fileFormat;
        private String fileUrl;
        private Long fileSizeBytes;
        private String thumbnailUrl;
        private String[] tags;
        private Boolean isPublic;
        private Boolean isActive;
        private Integer displayOrder;
    }

    // ============= OTHER DTOs =============

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FinancialsIntroDTO {
        private UUID id;

        @NotBlank(message = "El texto de introducción es requerido")
        @Size(max = 5000, message = "El texto no puede exceder 5000 caracteres")
        private String introText;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileUploadResponse {
        private String fileUrl;
        private String fileName;
        private Long fileSizeBytes;
        private String fileFormat;
    }
}
