package com.elsalvador.coopac.dto.publicpage.product;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO que contiene los datos completos de la página de detalle de un producto.
 * Incluye el encabezado de la página y todas las secciones de contenido principal.
 *
 * @param header Encabezado de la página con información principal del producto
 * @param sections Secciones de contenido del producto (financiera, características, pasos, CTA)
 */
@Schema(description = "Datos completos del detalle de un producto")
public record ProductDetailDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Secciones principales del producto")
    SectionsDTO sections
) {

    /**
     * Encabezado de la página de detalle del producto.
     * Contiene la información principal del producto a mostrar en la parte superior.
     *
     * @param back Link de navegación para regresar
     * @param category Categoría a la que pertenece el producto
     * @param title Título o nombre del producto
     * @param intro Introducción o descripción breve del producto
     * @param highlightText Texto destacado o frase resaltante
     * @param icon URL o ruta del icono que representa al producto
     */
    @Schema(description = "Header de la pagina de detalle")
    public record HeaderDTO(
        @Schema(description = "Link de regreso")
        BackLinkDTO back,
        @Schema(description = "Categoria del producto")
        CategorySummaryDTO category,
        @Schema(description = "Titulo del producto")
        String title,
        @Schema(description = "Introduccion al producto")
        String intro,
        @Schema(description = "Texto destacado")
        String highlightText,
        @Schema(description = "Icono del producto")
        String icon
    ) {}

    /**
     * Link de navegación para regresar a la página anterior.
     * Proporciona la capacidad de navegar hacia atrás en el flujo de la aplicación.
     *
     * @param label Texto visible del enlace (ej: "Volver a Productos")
     * @param url URL a la que dirigirse al hacer clic
     */
    @Schema(description = "Link de regreso")
    public record BackLinkDTO(
        @Schema(description = "Texto del enlace de regreso", example = "Volver a Productos")
        String label,
        @Schema(description = "URL del enlace")
        String url
    ) {}

    /**
     * Información resumida de la categoría a la que pertenece el producto.
     * Contiene datos básicos para identificar y mostrar la categoría.
     *
     * @param slug Identificador único en formato URL-friendly de la categoría
     * @param name Nombre legible de la categoría
     */
    @Schema(description = "Resumen de categoria")
    public record CategorySummaryDTO(
        @Schema(description = "Slug de la categoria")
        String slug,
        @Schema(description = "Nombre de la categoria")
        String name
    ) {}

    /**
     * Conjunto de secciones principales que componen el contenido del detalle del producto.
     * Agrupa toda la información relevante en categorías temáticas.
     *
     * @param financialInfo Sección con información y métricas financieras del producto
     * @param features Sección con las características principales del producto
     * @param steps Sección con los pasos necesarios para acceder al producto
     * @param ctaPanel Sección con el panel de llamada a la acción final
     */
    @Schema(description = "Secciones de contenido del producto")
    public record SectionsDTO(
        @Schema(description = "Informacion financiera del producto")
        FinancialInfoDTO financialInfo,
        @Schema(description = "Caracteristicas del producto")
        FeaturesDTO features,
        @Schema(description = "Pasos para acceder al producto")
        StepsDTO steps,
        @Schema(description = "Panel de CTA final")
        CtaPanelDTO ctaPanel
    ) {}

    /**
     * Información financiera completa del producto.
     * Combina métricas resumidas para presentación con datos financieros detallados.
     *
     * @param metrics Lista de métricas financieras principales formateadas para visualización
     * @param raw Datos financieros crudos y sin procesar con información técnica completa
     */
    @Schema(description = "Informacion financiera del producto")
    public record FinancialInfoDTO(
        @Schema(description = "Metricas financieras principales")
        List<MetricDTO> metrics,
        @Schema(description = "Datos financieros crudos")
        RawFinancialDataDTO raw
    ) {}

    /**
     * Métrica financiera individual formateada para presentación.
     * Representa un par etiqueta-valor listo para mostrar en la interfaz.
     *
     * @param label Etiqueta descriptiva de la métrica (ej: "Tasa de Interés")
     * @param value Valor formateado de la métrica (ej: "3.5%" o "12 meses")
     */
    @Schema(description = "Metrica financiera")
    public record MetricDTO(
        @Schema(description = "Etiqueta de la metrica", example = "Tasa de Interes")
        String label,
        @Schema(description = "Valor de la metrica", example = "3.5%")
        String value
    ) {}

    /**
     * Datos financieros crudos sin procesar del producto.
     * Contiene información técnica y detallada sobre los términos financieros del producto.
     *
     * @param interestRateText Descripción textual de la tasa de interés aplicable
     * @param termText Descripción del plazo o período del producto
     * @param currency Código de moneda utilizado (ej: "USD", "PEN")
     * @param notes Notas adicionales o aclaraciones sobre los términos financieros
     */
    @Schema(description = "Datos financieros crudos")
    public record RawFinancialDataDTO(
        @Schema(description = "Texto de tasa de interes", example = "Tasa anual del 3.5%")
        String interestRateText,
        @Schema(description = "Texto de plazo", example = "Plazo de 12 meses")
        String termText,
        @Schema(description = "Moneda utilizada", example = "USD")
        String currency,
        @Schema(description = "Notas adicionales sobre los datos")
        String notes
    ) {}

    /**
     * Características principales del producto.
     * Contiene una lista de características relevantes que destacan los beneficios del producto.
     *
     * @param items Lista de strings con las características principales del producto
     */
    @Schema(description = "Caracteristicas del producto")
    public record FeaturesDTO(
        @Schema(description = "Lista de caracteristicas principales")
        List<String> items
    ) {}

    /**
     * Pasos necesarios para acceder o contratar el producto.
     * Proporciona una guía estructurada del proceso que debe seguir el usuario.
     *
     * @param title Título principal de la sección de pasos
     * @param subtitle Subtítulo o descripción secundaria de la sección
     * @param items Lista detallada de pasos individuales a seguir
     */
    @Schema(description = "Pasos para acceder al producto")
    public record StepsDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo de la seccion")
        String subtitle,
        @Schema(description = "Lista de pasos")
        List<StepItemDTO> items
    ) {}

    /**
     * Detalle de un paso individual en el proceso de acceso al producto.
     * Proporciona información específica sobre cada etapa del procedimiento.
     *
     * @param title Título o nombre del paso
     * @param description Descripción detallada de qué hacer en este paso
     * @param icon URL o ruta del icono que representa este paso
     * @param estimatedTime Tiempo estimado para completar este paso (ej: "5 minutos")
     * @param order Número de orden de aparición del paso en la secuencia
     */
    @Schema(description = "Item de paso individual")
    public record StepItemDTO(
        @Schema(description = "Titulo del paso")
        String title,
        @Schema(description = "Descripcion del paso")
        String description,
        @Schema(description = "Icono del paso")
        String icon,
        @Schema(description = "Tiempo estimado", example = "5 minutos")
        String estimatedTime,
        @Schema(description = "Orden de aparicion del paso")
        Integer order
    ) {}

    /**
     * Panel de llamada a la acción (CTA) final.
     * Proporciona un mensaje motivador para que el usuario contrate el producto.
     *
     * @param title Título principal del CTA
     * @param subtitle Texto secundario o descripción adicional del CTA
     */
    @Schema(description = "Panel de CTA final")
    public record CtaPanelDTO(
        @Schema(description = "Titulo del CTA")
        String title,
        @Schema(description = "Subtitulo del CTA")
        String subtitle
    ) {}
}
