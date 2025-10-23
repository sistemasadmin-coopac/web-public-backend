package com.elsalvador.coopac.dto.admin;

import lombok.Builder;

import java.util.UUID;

/**
 * DTO para administración de configuración del sitio
 */
public class SiteSettingsAdminDTO {

    @Builder
    public record SiteSettingsResponseDTO(
        UUID id,
        String phoneMain,
        String whatsappNumber,
        String whatsappUrl,
        String emailMain,
        String addressLine1,
        String city,
        String state,
        String country,
        String googleMapsUrl,
        String facebookUrl,
        String instagramUrl,
        String linkedinUrl,
        String twitterUrl,
        String tiktokUrl
    ) {}

    @Builder
    public record UpdateSiteSettingsDTO(
        String phoneMain,
        String whatsappNumber,
        String whatsappUrl,
        String emailMain,
        String addressLine1,
        String city,
        String state,
        String country,
        String googleMapsUrl,
        String facebookUrl,
        String instagramUrl,
        String linkedinUrl,
        String twitterUrl,
        String tiktokUrl
    ) {}
}

