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

        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;
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

        private String[] tags;

        @NotNull(message = "El estado público es requerido")
        private Boolean isPublic;

        @NotNull(message = "El estado activo es requerido")
        private Boolean isActive;
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
        private LocalDate publishDate;
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
