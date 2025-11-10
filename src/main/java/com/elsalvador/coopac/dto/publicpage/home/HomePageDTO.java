package com.elsalvador.coopac.dto.publicpage.home;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Datos completos de la pagina Home")
public record HomePageDTO(
    @Schema(description = "Encabezado de la pagina")
    HeaderDTO header,
    @Schema(description = "Secciones principales de la pagina")
    SectionsDTO sections,
    @Schema(description = "Configuracion del sitio")
    SiteSettingsDTO siteSettings
) {
    @Schema(description = "Header de la pagina")
    public record HeaderDTO(
        @Schema(description = "Texto del badge", example = "Nuevo")
        String badgeText,
        @Schema(description = "Titulo principal", example = "Bienvenido a COOPAC")
        String titleMain,
        @Schema(description = "Parte destacada del titulo", example = "El Futuro Financiero")
        String titleHighlight,
        @Schema(description = "Subtitulo descriptivo")
        String subtitle,
        @Schema(description = "Acciones principales del header")
        ActionsDTO actions,
        @Schema(description = "Tarjetas del header")
        List<CardDTO> cards
    ) {}

    @Schema(description = "Acciones principales")
    public record ActionsDTO(
        @Schema(description = "Accion primaria")
        CtaLinkDTO primary,
        @Schema(description = "Accion secundaria")
        CtaLinkDTO secondary
    ) {}

    @Schema(description = "Tarjeta de informacion")
    public record CardDTO(
        @Schema(description = "Icono de la tarjeta")
        String icon,
        @Schema(description = "Titulo de la tarjeta")
        String title,
        @Schema(description = "Descripcion de la tarjeta")
        String description,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Secciones de contenido")
    public record SectionsDTO(
        @Schema(description = "Seccion de promociones")
        PromotionsSectionDTO promotions,
        @Schema(description = "Seccion de productos")
        ProductsSectionDTO products,
        @Schema(description = "Seccion de estadisticas")
        StatsSectionDTO stats,
        @Schema(description = "Seccion de CTAs")
        CtasSectionDTO ctas
    ) {}

    @Schema(description = "Seccion de promociones")
    public record PromotionsSectionDTO(
        @Schema(description = "Titulo principal")
        String titleMain,
        @Schema(description = "Parte destacada del titulo")
        String titleHighlight,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Items de promociones")
        List<PromotionDTO> items
    ) {}

    @Schema(description = "Item de promocion")
    public record PromotionDTO(
        @Schema(description = "Titulo de la promocion")
        String title,
        @Schema(description = "Tag o etiqueta", example = "Especial")
        String tag,
        @Schema(description = "Descripcion de la promocion")
        String description,
        @Schema(description = "Texto destacado")
        String highlightText,
        @Schema(description = "Lista de caracteristicas")
        List<String> features,
        @Schema(description = "Orden de aparicion")
        Integer order,
        @Schema(description = "Imagen en formato base64")
        String imageBase64
    ) {}

    @Schema(description = "Seccion de productos")
    public record ProductsSectionDTO(
        @Schema(description = "Titulo principal")
        String titleMain,
        @Schema(description = "Parte destacada del titulo")
        String titleHighlight,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Items de productos")
        List<ProductDTO> items
    ) {}

    @Schema(description = "Item de producto")
    public record ProductDTO(
        @Schema(description = "Slug del producto", example = "producto-1")
        String slug,
        @Schema(description = "Titulo del producto")
        String title,
        @Schema(description = "Resumen corto del producto")
        String summary,
        @Schema(description = "Icono del producto")
        String icon,
        @Schema(description = "Texto destacado")
        String highlightText,
        @Schema(description = "Categoria del producto")
        CategoryDTO category,
        @Schema(description = "Badges o etiquetas del producto")
        List<String> badges,
        @Schema(description = "Accion primaria")
        PrimaryActionDTO primaryAction,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Categoria de producto")
    public record CategoryDTO(
        @Schema(description = "Slug de la categoria")
        String slug,
        @Schema(description = "Nombre de la categoria")
        String name
    ) {}

    @Schema(description = "Accion primaria")
    public record PrimaryActionDTO(
        @Schema(description = "Etiqueta del boton")
        String label,
        @Schema(description = "Tipo de accion", example = "link")
        String type,
        @Schema(description = "Valor de la accion (URL o ID)")
        String value
    ) {}

    @Schema(description = "Seccion de estadisticas")
    public record StatsSectionDTO(
        @Schema(description = "Titulo de la seccion")
        String title,
        @Schema(description = "Subtitulo")
        String subtitle,
        @Schema(description = "Items de estadisticas")
        List<StatDTO> items
    ) {}

    @Schema(description = "Item de estadistica")
    public record StatDTO(
        @Schema(description = "Etiqueta de la estadistica", example = "Miembros Activos")
        String label,
        @Schema(description = "Valor textual", example = "15,000+")
        String value,
        @Schema(description = "Icono de la estadistica")
        String icon,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Seccion de CTAs")
    public record CtasSectionDTO(
        @Schema(description = "CTAs intermedias")
        List<CtaDTO> mid,
        @Schema(description = "CTAs finales")
        List<CtaDTO> _final
    ) {}

    @Schema(description = "Item de CTA")
    public record CtaDTO(
        @Schema(description = "Titulo del CTA")
        String title,
        @Schema(description = "Subtitulo del CTA")
        String subtitle,
        @Schema(description = "Boton de accion")
        CtaLinkDTO button,
        @Schema(description = "Orden de aparicion")
        Integer order
    ) {}

    @Schema(description = "Link de CTA")
    public record CtaLinkDTO(
        @Schema(description = "Texto del link")
        String text,
        @Schema(description = "URL del link")
        String url
    ) {}

    @Schema(description = "Configuracion del sitio")
    public record SiteSettingsDTO(
        @Schema(description = "Nombre de la empresa")
        String companyName,
        @Schema(description = "URL del logo")
        String logoUrl,
        @Schema(description = "Texto alternativo del logo")
        String logoAlt,
        @Schema(description = "Telefono principal")
        String phoneMain,
        @Schema(description = "Telefono secundario")
        String phoneSecondary,
        @Schema(description = "Numero de WhatsApp")
        String whatsappNumber,
        @Schema(description = "URL de WhatsApp")
        String whatsappUrl,
        @Schema(description = "Email principal")
        String emailMain,
        @Schema(description = "Email de soporte")
        String emailSupport,
        @Schema(description = "Primera linea de direccion")
        String addressLine1,
        @Schema(description = "Segunda linea de direccion")
        String addressLine2,
        @Schema(description = "Ciudad")
        String city,
        @Schema(description = "Estado o region")
        String state,
        @Schema(description = "Pais")
        String country,
        @Schema(description = "URL de Google Maps")
        String googleMapsUrl,
        @Schema(description = "URL de Facebook")
        String facebookUrl,
        @Schema(description = "URL de Instagram")
        String instagramUrl,
        @Schema(description = "URL de LinkedIn")
        String linkedinUrl,
        @Schema(description = "URL de Twitter/X")
        String twitterUrl,
        @Schema(description = "URL de TikTok")
        String tiktokUrl
    ) {}
}

