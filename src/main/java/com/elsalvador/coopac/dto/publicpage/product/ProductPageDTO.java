package com.elsalvador.coopac.dto.publicpage.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos completos de la pagina de productos")
public record ProductPageDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Secciones principales de la pagina")
    SectionsDTO sections
) {

    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Texto del badge", example = "Nuevos Productos")
        String badgeText,
        @Schema(description = "Titulo principal", example = "Nuestros Productos")
        String titleMain,
        @Schema(description = "Parte destacada del titulo")
        String titleHighlight,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Acciones del header")
        ActionsDTO actions
    ) {}

    @Schema(description = "Acciones del header")
    public record ActionsDTO(
        @Schema(description = "Accion primaria")
        ActionLinkDTO primary
    ) {}

    @Schema(description = "Link de accion")
    public record ActionLinkDTO(
        @Schema(description = "Texto del enlace")
        String text,
        @Schema(description = "URL del enlace")
        String url
    ) {}

    @Schema(description = "Secciones de contenido")
    public record SectionsDTO(
        @Schema(description = "Seccion de filtros y busqueda")
        FiltersDTO filters,
        @Schema(description = "Grid de productos")
        GridDTO grid
    ) {}

    @Schema(description = "Filtros de productos")
    public record FiltersDTO(
        @Schema(description = "Placeholder del input de busqueda", example = "Buscar productos...")
        String searchPlaceholder,
        @Schema(description = "Categorias disponibles para filtrar")
        List<CategoryDTO> categories
    ) {}

    @Schema(description = "Categoria de producto")
    public record CategoryDTO(
        @Schema(description = "Slug de la categoria")
        String slug,
        @Schema(description = "Nombre de la categoria")
        String name,
        @Schema(description = "Icono de la categoria")
        String icon,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Grid de productos")
    public record GridDTO(
        @Schema(description = "Items de productos")
        List<ProductItemDTO> items,
        @Schema(description = "Informacion de paginacion")
        PaginationDTO pagination
    ) {}

    @Schema(description = "Item de producto en la lista")
    public record ProductItemDTO(
        @Schema(description = "Slug del producto", example = "ahorros-programados")
        String slug,
        @Schema(description = "Titulo del producto")
        String title,
        @Schema(description = "Resumen corto del producto")
        String summary,
        @Schema(description = "Icono del producto")
        String icon,
        @Schema(description = "Categoria a la que pertenece")
        CategorySummaryDTO category,
        @Schema(description = "Texto destacado del producto")
        String highlightText,
        @Schema(description = "Tags o etiquetas del producto")
        List<String> tags,
        @Schema(description = "Resumen de informacion financiera")
        FinanceBriefDTO financeBrief,
        @Schema(description = "URL para ver detalles del producto")
        String detailsUrl,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Resumen de categoria")
    public record CategorySummaryDTO(
        @Schema(description = "Slug de la categoria")
        String slug,
        @Schema(description = "Nombre de la categoria")
        String name
    ) {}

    @Schema(description = "Informacion financiera resumida")
    public record FinanceBriefDTO(
        @Schema(description = "Texto de tasa de interes", example = "3.5% anual")
        String interestRateText,
        @Schema(description = "Texto de plazo", example = "12 meses")
        String termText,
        @Schema(description = "Moneda utilizada", example = "USD")
        String currency,
        @Schema(description = "Notas adicionales")
        String notes
    ) {}

    @Schema(description = "Informacion de paginacion")
    public record PaginationDTO(
        @Schema(description = "Numero de pagina actual", example = "1")
        Integer page,
        @Schema(description = "Cantidad de items por pagina", example = "12")
        Integer pageSize,
        @Schema(description = "Total de items", example = "45")
        Integer total
    ) {}
}
