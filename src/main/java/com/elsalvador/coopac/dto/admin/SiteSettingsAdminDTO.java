package com.elsalvador.coopac.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO para administración de configuración del sitio
 */
public class SiteSettingsAdminDTO {

    @Builder
    @Schema(description = "Respuesta con la configuracion actual del sitio")
    public record SiteSettingsResponseDTO(
        @Schema(description = "ID único de la configuración", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,
        @Schema(description = "Numero de telefono principal", example = "+503 2555-1234")
        String phoneMain,
        @Schema(description = "Numero de WhatsApp", example = "+503 7890-1234")
        String whatsappNumber,
        @Schema(description = "URL de WhatsApp", example = "https://wa.me/5037890123")
        String whatsappUrl,
        @Schema(description = "Email principal del sitio", example = "info@coopac-elsalvador.com")
        String emailMain,
        @Schema(description = "Primera linea de direccion", example = "Av. Los Heroes 123")
        String addressLine1,
        @Schema(description = "Ciudad", example = "San Salvador")
        String city,
        @Schema(description = "Estado o region", example = "San Salvador")
        String state,
        @Schema(description = "Pais", example = "El Salvador")
        String country,
        @Schema(description = "URL de Google Maps", example = "https://maps.google.com/...")
        String googleMapsUrl,
        @Schema(description = "URL de Facebook", example = "https://facebook.com/coopac")
        String facebookUrl,
        @Schema(description = "URL de Instagram", example = "https://instagram.com/coopac")
        String instagramUrl,
        @Schema(description = "URL de LinkedIn", example = "https://linkedin.com/company/coopac")
        String linkedinUrl,
        @Schema(description = "URL de Twitter/X", example = "https://twitter.com/coopac")
        String twitterUrl,
        @Schema(description = "URL de TikTok", example = "https://tiktok.com/@coopac")
        String tiktokUrl
    ) {}

    @Builder
    @Schema(description = "Datos para actualizar la configuracion del sitio")
    public record UpdateSiteSettingsDTO(
        @Schema(description = "Numero de telefono principal", example = "+503 2555-1234")
        String phoneMain,
        @Schema(description = "Numero de WhatsApp", example = "+503 7890-1234")
        String whatsappNumber,
        @Schema(description = "URL de WhatsApp", example = "https://wa.me/5037890123")
        String whatsappUrl,
        @Schema(description = "Email principal del sitio", example = "info@coopac-elsalvador.com")
        String emailMain,
        @Schema(description = "Primera linea de direccion", example = "Av. Los Heroes 123")
        String addressLine1,
        @Schema(description = "Ciudad", example = "San Salvador")
        String city,
        @Schema(description = "Estado o region", example = "San Salvador")
        String state,
        @Schema(description = "Pais", example = "El Salvador")
        String country,
        @Schema(description = "URL de Google Maps", example = "https://maps.google.com/...")
        String googleMapsUrl,
        @Schema(description = "URL de Facebook", example = "https://facebook.com/coopac")
        String facebookUrl,
        @Schema(description = "URL de Instagram", example = "https://instagram.com/coopac")
        String instagramUrl,
        @Schema(description = "URL de LinkedIn", example = "https://linkedin.com/company/coopac")
        String linkedinUrl,
        @Schema(description = "URL de Twitter/X", example = "https://twitter.com/coopac")
        String twitterUrl,
        @Schema(description = "URL de TikTok", example = "https://tiktok.com/@coopac")
        String tiktokUrl
    ) {}
}



