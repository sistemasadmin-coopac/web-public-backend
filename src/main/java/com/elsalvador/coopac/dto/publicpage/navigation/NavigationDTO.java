package com.elsalvador.coopac.dto.publicpage.navigation;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * DTO que contiene la estructura completa de navegación del sitio.
 * Incluye el menú, marca, items de navegación y accesos directos.
 *
 * @param menu Información del menú de navegación
 * @param brand Información de marca y logo
 * @param items Lista de items de navegación principales
 * @param shortcuts Accesos directos como WhatsApp y teléfono
 */
@Schema(description = "Estructura completa de navegación del sitio web")
public record NavigationDTO(
    @Schema(description = "Información del menú de navegación")
    MenuDTO menu,
    @Schema(description = "Información de marca y logo")
    BrandDTO brand,
    @Schema(description = "Lista de items de navegación principales")
    List<NavigationItemDTO> items,
    @Schema(description = "Accesos directos rápidos (WhatsApp, teléfono)")
    ShortcutsDTO shortcuts
) {

    /**
     * Información del menú de navegación.
     */
    @Schema(description = "Información del menú de navegación")
    public record MenuDTO(
        @Schema(description = "Identificador único del menú en formato URL-friendly", example = "main-menu")
        String slug,
        @Schema(description = "Nombre del menú", example = "Menú Principal")
        String name
    ) {}

    /**
     * Información de marca y logo del sitio.
     */
    @Schema(description = "Información de marca y logo del sitio")
    public record BrandDTO(
        @Schema(description = "URL de la página principal de la marca", example = "https://coopac-elsalvador.pe")
        String url,
        @Schema(description = "URL del logo de la marca", example = "https://cdn.coopac-elsalvador.pe/logo.svg")
        String logoUrl,
        @Schema(description = "Texto alternativo del logo para accesibilidad", example = "Logo COOPAC El Salvador")
        String logoAlt
    ) {}

    /**
     * Item individual de navegación.
     */
    @Schema(description = "Item individual de navegación")
    public record NavigationItemDTO(
        @Schema(description = "Texto visible del enlace de navegación", example = "Productos")
        String label,
        @Schema(description = "URL de destino del enlace", example = "/productos")
        String url,
        @Schema(description = "Indica si el enlace abre en una pestaña nueva", example = "false")
        Boolean external,
        @Schema(description = "Orden de aparición en el menú", example = "1")
        Integer order
    ) {}

    /**
     * Accesos directos rápidos a canales de contacto.
     */
    @Schema(description = "Accesos directos a canales de contacto rápido")
    public record ShortcutsDTO(
        @Schema(description = "URL de WhatsApp incluyendo número", example = "https://wa.me/51970003173")
        String whatsappUrl,
        @Schema(description = "Número de teléfono de contacto", example = "+51-44-544011")
        String phone
    ) {}
}
