package com.elsalvador.coopac.dto.publicpage.financial;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Schema(description = "Datos completos de la pagina de Financials")
public record FinancialPageDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Secciones principales de la pagina")
    SectionsDTO sections
) {

    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Titulo principal", example = "Reportes Financieros")
        String titleMain,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle
    ) {}

    @Schema(description = "Secciones de contenido")
    public record SectionsDTO(
        @Schema(description = "Introduccion a reportes financieros")
        IntroDTO intro,
        @Schema(description = "Lista de categorias de reportes")
        List<CategoryDTO> categories
    ) {}

    @Schema(description = "Introduccion de reportes")
    public record IntroDTO(
        @Schema(description = "Texto de introduccion")
        String text
    ) {}

    @Schema(description = "Categoria de reportes")
    public record CategoryDTO(
        @Schema(description = "Nombre de la categoria", example = "Estados Financieros")
        String name,
        @Schema(description = "Slug de la categoria", example = "estados-financieros")
        String slug,
        @Schema(description = "Descripcion de la categoria")
        String description,
        @Schema(description = "Cantidad de reportes en la categoria")
        Integer count,
        @Schema(description = "Lista de reportes en esta categoria")
        List<ReportDTO> items
    ) {}

    @Schema(description = "Reporte financiero")
    public record ReportDTO(
        @Schema(description = "ID unico del reporte")
        UUID id,
        @Schema(description = "Slug del reporte", example = "reporte-2024")
        String slug,
        @Schema(description = "Titulo del reporte", example = "Reporte Anual 2024")
        String title,
        @Schema(description = "Resumen corto del reporte")
        String summary,
        @Schema(description = "Fecha de publicacion del reporte")
        LocalDate publishDate,
        @Schema(description = "Informacion del archivo del reporte")
        FileDTO file,
        @Schema(description = "URL de imagen miniatura del reporte")
        String thumbnailUrl,
        @Schema(description = "Etiquetas del reporte", example = "[\"auditorado\", \"2024\"]")
        List<String> tags,
        @Schema(description = "Indica si el reporte es publico")
        Boolean isPublic,
        @Schema(description = "Orden de aparicion del reporte")
        Integer order
    ) {}

    @Schema(description = "Informacion del archivo")
    public record FileDTO(
        @Schema(description = "Formato del archivo", example = "pdf")
        String format,
        @Schema(description = "URL de descarga del archivo")
        String url,
        @Schema(description = "Tamanio del archivo en bytes")
        Long sizeBytes
    ) {}
}


