package com.elsalvador.coopac.service.admin.impl;

import com.elsalvador.coopac.dto.admin.SiteSettingsAdminDTO;
import com.elsalvador.coopac.entity.config.SiteSettings;
import com.elsalvador.coopac.repository.SiteSettingsRepository;
import com.elsalvador.coopac.service.admin.ManageSiteSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

/**
 * Implementación del servicio para administrar la configuración del sitio
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageSiteSettingsServiceImpl implements ManageSiteSettingsService {

    private final SiteSettingsRepository siteSettingsRepository;

    @Override
    @Transactional(readOnly = true)
    public SiteSettingsAdminDTO.SiteSettingsResponseDTO getSiteSettings() {
        log.debug("Obteniendo configuración actual del sitio");

        var settings = siteSettingsRepository.findFirstByOrderByUpdatedAtDesc();

        if (settings.isEmpty()) {
            log.warn("No se encontró configuración de sitio");
            return null;
        }

        return mapToResponseDTO(settings.get());
    }

    @Override
    @CacheEvict(value = {HOME_PAGE_CACHE, ABOUT_PAGE_CACHE, CONTACT_PAGE_CACHE, FINANCIAL_PAGE_CACHE, PRODUCT_PAGE_CACHE, FOOTER_PAGE_CACHE, NAVIGATION_PAGE_CACHE}, allEntries = true)
    public SiteSettingsAdminDTO.SiteSettingsResponseDTO updateSiteSettings(SiteSettingsAdminDTO.UpdateSiteSettingsDTO dto) {
        log.info("Actualizando configuración del sitio");

        var settings = siteSettingsRepository.findFirstByOrderByUpdatedAtDesc();

        SiteSettings siteSettings;
        if (settings.isPresent()) {
            // Actualizar configuración existente
            siteSettings = settings.get();
            log.info("Actualizando configuración existente con ID: {}", siteSettings.getId());
        } else {
            // Crear nueva configuración
            log.info("Creando nueva configuración de sitio");
            siteSettings = new SiteSettings();
        }

        // Actualizar solo los campos indicados
        if (dto.phoneMain() != null) {
            siteSettings.setPhoneMain(dto.phoneMain());
        }
        if (dto.whatsappNumber() != null) {
            siteSettings.setWhatsappNumber(dto.whatsappNumber());
        }
        if (dto.whatsappUrl() != null) {
            siteSettings.setWhatsappUrl(dto.whatsappUrl());
        }
        if (dto.emailMain() != null) {
            siteSettings.setEmailMain(dto.emailMain());
        }
        if (dto.addressLine1() != null) {
            siteSettings.setAddressLine1(dto.addressLine1());
        }
        if (dto.city() != null) {
            siteSettings.setCity(dto.city());
        }
        if (dto.state() != null) {
            siteSettings.setState(dto.state());
        }
        if (dto.country() != null) {
            siteSettings.setCountry(dto.country());
        }
        if (dto.googleMapsUrl() != null) {
            siteSettings.setGoogleMapsUrl(dto.googleMapsUrl());
        }
        if (dto.facebookUrl() != null) {
            siteSettings.setFacebookUrl(dto.facebookUrl());
        }
        if (dto.instagramUrl() != null) {
            siteSettings.setInstagramUrl(dto.instagramUrl());
        }
        if (dto.linkedinUrl() != null) {
            siteSettings.setLinkedinUrl(dto.linkedinUrl());
        }
        if (dto.twitterUrl() != null) {
            siteSettings.setTwitterUrl(dto.twitterUrl());
        }
        if (dto.tiktokUrl() != null) {
            siteSettings.setTiktokUrl(dto.tiktokUrl());
        }

        SiteSettings updatedSettings = siteSettingsRepository.save(siteSettings);
        log.info("Configuración del sitio actualizada exitosamente con ID: {}", updatedSettings.getId());

        return mapToResponseDTO(updatedSettings);
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private SiteSettingsAdminDTO.SiteSettingsResponseDTO mapToResponseDTO(SiteSettings settings) {
        return SiteSettingsAdminDTO.SiteSettingsResponseDTO.builder()
                .id(settings.getId())
                .phoneMain(settings.getPhoneMain())
                .whatsappNumber(settings.getWhatsappNumber())
                .whatsappUrl(settings.getWhatsappUrl())
                .emailMain(settings.getEmailMain())
                .addressLine1(settings.getAddressLine1())
                .city(settings.getCity())
                .state(settings.getState())
                .country(settings.getCountry())
                .googleMapsUrl(settings.getGoogleMapsUrl())
                .facebookUrl(settings.getFacebookUrl())
                .instagramUrl(settings.getInstagramUrl())
                .linkedinUrl(settings.getLinkedinUrl())
                .twitterUrl(settings.getTwitterUrl())
                .tiktokUrl(settings.getTiktokUrl())
                .build();
    }
}

