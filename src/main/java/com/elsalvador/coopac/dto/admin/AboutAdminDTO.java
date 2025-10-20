package com.elsalvador.coopac.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTOs para administración de la sección About
 */
public class AboutAdminDTO {

    /**
     * DTO para respuesta completa de la página About
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutPageResponseDTO {
        private AboutMissionVisionDTO missionVision;
        private AboutValuesSectionDTO valuesSection;
        private List<AboutValueDTO> values;
        private AboutHistorySectionDTO historySection;
        private List<AboutTimelineEventDTO> timeline;
        private AboutImpactSectionDTO impactSection;
        private List<AboutImpactMetricDTO> impactMetrics;
        private AboutBoardSectionDTO boardSection;
        private List<AboutBoardMemberDTO> boardMembers;
    }

    /**
     * DTO para misión y visión
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutMissionVisionDTO {
        private UUID id;

        @NotBlank(message = "El título de misión es obligatorio")
        @Size(max = 255, message = "El título de misión no puede exceder 255 caracteres")
        private String missionTitle;

        @NotBlank(message = "El texto de misión es obligatorio")
        private String missionText;

        @NotBlank(message = "El título de visión es obligatorio")
        @Size(max = 255, message = "El título de visión no puede exceder 255 caracteres")
        private String visionTitle;

        @NotBlank(message = "El texto de visión es obligatorio")
        private String visionText;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        private LocalDateTime updatedAt;
    }

    /**
     * DTO para valores individuales
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutValueDTO {
        private UUID id;

        @NotBlank(message = "El título del valor es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        private String title;

        @NotBlank(message = "La descripción del valor es obligatoria")
        private String description;

        @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
        private String icon;

        @NotNull(message = "El orden de visualización es obligatorio")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;
    }

    /**
     * DTO para sección de valores
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutValuesSectionDTO {
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        private String title;

        @Size(max = 500, message = "El subtítulo no puede exceder 500 caracteres")
        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        private LocalDateTime updatedAt;
    }

    /**
     * DTO para eventos del timeline
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutTimelineEventDTO {
        private UUID id;

        @NotBlank(message = "La etiqueta del año es obligatoria")
        @Size(max = 10, message = "La etiqueta del año no puede exceder 10 caracteres")
        private String yearLabel;

        @NotBlank(message = "El título del evento es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        private String title;

        @NotBlank(message = "La descripción del evento es obligatoria")
        private String description;

        @NotNull(message = "El orden de visualización es obligatorio")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;
    }

    /**
     * DTO para sección de historia
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutHistorySectionDTO {
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        private String title;

        private String subtitle;

        @Size(max = 50, message = "El valor destacado no puede exceder 50 caracteres")
        private String highlightValue;

        @Size(max = 255, message = "El título destacado no puede exceder 255 caracteres")
        private String highlightTitle;

        @Size(max = 500, message = "La nota destacada no puede exceder 500 caracteres")
        private String highlightNote;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        private LocalDateTime updatedAt;
    }

    /**
     * DTO para métricas de impacto
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutImpactMetricDTO {
        private UUID id;

        @NotBlank(message = "El label de la métrica es obligatorio")
        @Size(max = 255, message = "El label no puede exceder 255 caracteres")
        private String label;

        @NotBlank(message = "El valor de la métrica es obligatorio")
        @Size(max = 50, message = "El valor no puede exceder 50 caracteres")
        private String valueText;

        @Size(max = 500, message = "La nota al pie no puede exceder 500 caracteres")
        private String footnote;

        @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
        private String icon;

        @NotNull(message = "El orden de visualización es obligatorio")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;
    }

    /**
     * DTO para sección de impacto
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutImpactSectionDTO {
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        private String title;

        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        private LocalDateTime updatedAt;
    }

    /**
     * DTO para miembros de junta directiva
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutBoardMemberDTO {
        private UUID id;

        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        private String fullName;

        @NotBlank(message = "El cargo es obligatorio")
        @Size(max = 255, message = "El cargo no puede exceder 255 caracteres")
        private String position;

        private String bio;

        @Size(max = 1000, message = "La URL de LinkedIn no puede exceder 1000 caracteres")
        private String linkedinUrl;

        @Size(max = 255, message = "El email no puede exceder 255 caracteres")
        private String email;

        @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
        private String phone;

        @NotNull(message = "El orden de visualización es obligatorio")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        // Foto en Base64 solo para lectura/respuesta
        private String photoBase64;
    }

    /**
     * DTO para sección de junta directiva
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AboutBoardSectionDTO {
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        private String title;

        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        private Boolean isActive;

        private LocalDateTime updatedAt;
    }
}
