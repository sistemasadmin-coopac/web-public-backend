package com.elsalvador.coopac.dto.publicpage.about;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos completos de la pagina About")
public record AboutPageDTO(
    @Schema(description = "Informacion del header/encabezado")
    HeaderDTO header,
    @Schema(description = "Secciones principales de la pagina")
    SectionsDTO sections
) {

    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Titulo principal", example = "Sobre Nosotros")
        String titleMain,
        @Schema(description = "Subtitulo", example = "Conoce mas sobre nuestra cooperativa")
        String subtitle
    ) {}

    @Schema(description = "Secciones de contenido")
    public record SectionsDTO(
        @Schema(description = "Seccion de historia")
        HistoryDTO history,
        @Schema(description = "Seccion de mision y vision")
        MissionVisionDTO missionVision,
        @Schema(description = "Seccion de valores")
        ValuesDTO values,
        @Schema(description = "Seccion de impacto")
        ImpactDTO impact,
        @Schema(description = "Seccion de junta directiva")
        BoardDTO board
    ) {}

    @Schema(description = "Informacion de historia")
    public record HistoryDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Dato destacado")
        HighlightDTO highlight,
        @Schema(description = "Eventos de la linea de tiempo")
        List<TimelineEventDTO> events
    ) {}

    @Schema(description = "Dato destacado")
    public record HighlightDTO(
        @Schema(description = "Valor numerico o textual", example = "25 anos")
        String value,
        @Schema(description = "Titulo del valor destacado")
        String title,
        @Schema(description = "Nota adicional")
        String note
    ) {}

    @Schema(description = "Evento de la linea de tiempo")
    public record TimelineEventDTO(
        @Schema(description = "Etiqueta del ano", example = "1998")
        String yearLabel,
        @Schema(description = "Titulo del evento")
        String title,
        @Schema(description = "Descripcion del evento")
        String description,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Mision y vision")
    public record MissionVisionDTO(
        @Schema(description = "Mision de la organizacion")
        MissionDTO mission,
        @Schema(description = "Vision de la organizacion")
        VisionDTO vision
    ) {}

    @Schema(description = "Mision")
    public record MissionDTO(
        @Schema(description = "Titulo")
        String title,
        @Schema(description = "Texto de la mision")
        String text,
        @Schema(description = "Icono")
        String icon
    ) {}

    @Schema(description = "Vision")
    public record VisionDTO(
        @Schema(description = "Titulo")
        String title,
        @Schema(description = "Texto de la vision")
        String text,
        @Schema(description = "Icono")
        String icon
    ) {}

    @Schema(description = "Lista de valores")
    public record ValuesDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Items de valores")
        List<ValueItemDTO> items
    ) {}

    @Schema(description = "Item de valor")
    public record ValueItemDTO(
        @Schema(description = "Icono del valor")
        String icon,
        @Schema(description = "Nombre del valor", example = "Integridad")
        String title,
        @Schema(description = "Descripcion del valor")
        String description,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Metricas de impacto")
    public record ImpactDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Items de metricas")
        List<MetricDTO> items
    ) {}

    @Schema(description = "Metrica de impacto")
    public record MetricDTO(
        @Schema(description = "Etiqueta de la metrica", example = "Miembros activos")
        String label,
        @Schema(description = "Valor textual", example = "15,000")
        String valueText,
        @Schema(description = "Icono")
        String icon,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Junta directiva")
    public record BoardDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Miembros de la junta directiva")
        List<BoardMemberDTO> members
    ) {}

    @Schema(description = "Miembro de la junta directiva")
    public record BoardMemberDTO(
        @Schema(description = "Nombre completo del miembro")
        String fullName,
        @Schema(description = "Posicion o cargo", example = "Presidente")
        String position,
        @Schema(description = "Foto en formato base64")
        String photoBase64,
        @Schema(description = "Biografia corta del miembro")
        String bio,
        @Schema(description = "URL de perfil LinkedIn")
        String linkedinUrl,
        @Schema(description = "Email de contacto")
        String email,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}
}


