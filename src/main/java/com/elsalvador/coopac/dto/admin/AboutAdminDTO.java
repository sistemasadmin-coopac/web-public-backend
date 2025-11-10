package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Respuesta completa con todos los datos de la página About")
    public static class AboutPageResponseDTO {
        @Schema(description = "Datos de misión y visión")
        private AboutMissionVisionDTO missionVision;

        @Schema(description = "Información de la sección de valores")
        private AboutValuesSectionDTO valuesSection;

        @Schema(description = "Lista de valores individuales")
        private List<AboutValueDTO> values;

        @Schema(description = "Información de la sección de historia")
        private AboutHistorySectionDTO historySection;

        @Schema(description = "Eventos del timeline de historia")
        private List<AboutTimelineEventDTO> timeline;

        @Schema(description = "Información de la sección de impacto")
        private AboutImpactSectionDTO impactSection;

        @Schema(description = "Métricas de impacto")
        private List<AboutImpactMetricDTO> impactMetrics;

        @Schema(description = "Información de la sección de junta directiva")
        private AboutBoardSectionDTO boardSection;

        @Schema(description = "Lista de miembros de junta directiva")
        private List<AboutBoardMemberDTO> boardMembers;
    }

    /**
     * DTO para misión y visión
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de misión y visión")
    public static class AboutMissionVisionDTO {
        @Schema(description = "ID único del registro")
        private UUID id;

        @NotBlank(message = "El título de misión es obligatorio")
        @Size(max = 255, message = "El título de misión no puede exceder 255 caracteres")
        @Schema(description = "Título de la misión", example = "Nuestra Misión")
        private String missionTitle;

        @NotBlank(message = "El texto de misión es obligatorio")
        @Schema(description = "Texto descriptivo de la misión")
        private String missionText;

        @NotBlank(message = "El título de visión es obligatorio")
        @Size(max = 255, message = "El título de visión no puede exceder 255 caracteres")
        @Schema(description = "Título de la visión", example = "Nuestra Visión")
        private String visionTitle;

        @NotBlank(message = "El texto de visión es obligatorio")
        @Schema(description = "Texto descriptivo de la visión")
        private String visionText;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la sección está activa", example = "true")
        private Boolean isActive;

        @Schema(description = "Fecha de última actualización")
        private LocalDateTime updatedAt;
    }

    /**
     * DTO para valores individuales
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Representa un valor individual")
    public static class AboutValueDTO {
        @Schema(description = "ID único del valor")
        private UUID id;

        @NotBlank(message = "El título del valor es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        @Schema(description = "Título del valor", example = "Integridad")
        private String title;

        @NotBlank(message = "La descripción del valor es obligatoria")
        @Schema(description = "Descripción detallada del valor")
        private String description;

        @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
        @Schema(description = "Nombre del icono (ej: material-ui icon name)", example = "check-circle")
        private String icon;

        @NotNull(message = "El orden de visualización es obligatorio")
        @Schema(description = "Orden de visualización en la página", example = "1")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si el valor está activo", example = "true")
        private Boolean isActive;
    }

    /**
     * DTO para sección de valores
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de la sección de valores")
    public static class AboutValuesSectionDTO {
        @Schema(description = "ID único de la sección")
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        @Schema(description = "Título de la sección", example = "Nuestros Valores")
        private String title;

        @Size(max = 500, message = "El subtítulo no puede exceder 500 caracteres")
        @Schema(description = "Subtítulo o descripción breve de la sección")
        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la sección está activa", example = "true")
        private Boolean isActive;

        @Schema(description = "Fecha de última actualización")
        private LocalDateTime updatedAt;
    }

    /**
     * DTO para eventos del timeline
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Evento del timeline de historia")
    public static class AboutTimelineEventDTO {
        @Schema(description = "ID único del evento")
        private UUID id;

        @NotBlank(message = "La etiqueta del año es obligatoria")
        @Size(max = 10, message = "La etiqueta del año no puede exceder 10 caracteres")
        @Schema(description = "Etiqueta del año o período", example = "2020")
        private String yearLabel;

        @NotBlank(message = "El título del evento es obligatorio")
        @Size(max = 255, message = "El título no puede exceder 255 caracteres")
        @Schema(description = "Título del evento", example = "Fundación de la organización")
        private String title;

        @NotBlank(message = "La descripción del evento es obligatoria")
        @Schema(description = "Descripción detallada del evento")
        private String description;

        @NotNull(message = "El orden de visualización es obligatorio")
        @Schema(description = "Orden de visualización en el timeline", example = "1")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si el evento está activo", example = "true")
        private Boolean isActive;
    }

    /**
     * DTO para sección de historia
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de la sección de historia")
    public static class AboutHistorySectionDTO {
        @Schema(description = "ID único de la sección")
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        @Schema(description = "Título de la sección de historia", example = "Nuestra Historia")
        private String title;

        @Schema(description = "Subtítulo de la sección")
        private String subtitle;

        @Size(max = 50, message = "El valor destacado no puede exceder 50 caracteres")
        @Schema(description = "Valor numérico o estadística destacada", example = "25+")
        private String highlightValue;

        @Size(max = 255, message = "El título destacado no puede exceder 255 caracteres")
        @Schema(description = "Título del valor destacado", example = "Años de experiencia")
        private String highlightTitle;

        @Size(max = 500, message = "La nota destacada no puede exceder 500 caracteres")
        @Schema(description = "Nota o descripción del valor destacado")
        private String highlightNote;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la sección está activa", example = "true")
        private Boolean isActive;

        @Schema(description = "Fecha de última actualización")
        private LocalDateTime updatedAt;
    }

    /**
     * DTO para métricas de impacto
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Métrica de impacto")
    public static class AboutImpactMetricDTO {
        @Schema(description = "ID único de la métrica")
        private UUID id;

        @NotBlank(message = "El label de la métrica es obligatorio")
        @Size(max = 255, message = "El label no puede exceder 255 caracteres")
        @Schema(description = "Etiqueta de la métrica", example = "Personas atendidas")
        private String label;

        @NotBlank(message = "El valor de la métrica es obligatorio")
        @Size(max = 50, message = "El valor no puede exceder 50 caracteres")
        @Schema(description = "Valor de la métrica", example = "5000+")
        private String valueText;

        @Size(max = 500, message = "La nota al pie no puede exceder 500 caracteres")
        @Schema(description = "Nota aclaratoria al pie de la métrica")
        private String footnote;

        @Size(max = 50, message = "El icono no puede exceder 50 caracteres")
        @Schema(description = "Nombre del icono asociado", example = "people")
        private String icon;

        @NotNull(message = "El orden de visualización es obligatorio")
        @Schema(description = "Orden de visualización", example = "1")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la métrica está activa", example = "true")
        private Boolean isActive;
    }

    /**
     * DTO para sección de impacto
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de la sección de impacto")
    public static class AboutImpactSectionDTO {
        @Schema(description = "ID único de la sección")
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        @Schema(description = "Título de la sección de impacto", example = "Nuestro Impacto")
        private String title;

        @Schema(description = "Subtítulo de la sección")
        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la sección está activa", example = "true")
        private Boolean isActive;

        @Schema(description = "Fecha de última actualización")
        private LocalDateTime updatedAt;
    }

    /**
     * DTO para miembros de junta directiva
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Miembro de la junta directiva")
    public static class AboutBoardMemberDTO {
        @Schema(description = "ID único del miembro")
        private UUID id;

        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
        @Schema(description = "Nombre completo del miembro", example = "Juan Pérez González")
        private String fullName;

        @NotBlank(message = "El cargo es obligatorio")
        @Size(max = 255, message = "El cargo no puede exceder 255 caracteres")
        @Schema(description = "Cargo del miembro", example = "Presidente")
        private String position;

        @Schema(description = "Biografía del miembro")
        private String bio;

        @Size(max = 1000, message = "La URL de LinkedIn no puede exceder 1000 caracteres")
        @Schema(description = "URL del perfil de LinkedIn", example = "https://linkedin.com/in/juanperez")
        private String linkedinUrl;

        @Size(max = 255, message = "El email no puede exceder 255 caracteres")
        @Schema(description = "Email del miembro", example = "juan@ejemplo.com")
        private String email;

        @Size(max = 50, message = "El teléfono no puede exceder 50 caracteres")
        @Schema(description = "Teléfono de contacto", example = "+503-2345-6789")
        private String phone;

        @NotNull(message = "El orden de visualización es obligatorio")
        @Schema(description = "Orden de visualización", example = "1")
        private Integer displayOrder;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si el miembro está activo", example = "true")
        private Boolean isActive;

        // Foto en Base64 solo para lectura/respuesta
        @Schema(description = "Foto del miembro en formato Base64")
        private String photoBase64;
    }

    /**
     * DTO para sección de junta directiva
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Información de la sección de junta directiva")
    public static class AboutBoardSectionDTO {
        @Schema(description = "ID único de la sección")
        private UUID id;

        @NotBlank(message = "El título de la sección es obligatorio")
        @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        @Schema(description = "Título de la sección de junta directiva", example = "Junta Directiva")
        private String title;

        @Schema(description = "Subtítulo de la sección")
        private String subtitle;

        @NotNull(message = "El estado activo es obligatorio")
        @Schema(description = "Indica si la sección está activa", example = "true")
        private Boolean isActive;

        @Schema(description = "Fecha de última actualización")
        private LocalDateTime updatedAt;
    }
}
