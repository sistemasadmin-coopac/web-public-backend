package com.elsalvador.coopac.controller.admin;

import com.elsalvador.coopac.dto.admin.SiteSettingsAdminDTO;
import com.elsalvador.coopac.service.admin.ManageSiteSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para administración de configuración del sitio
 */
@RestController
@RequestMapping("/api/admin/site-settings")
@RequiredArgsConstructor
@Slf4j
public class SiteSettingsController {

    private final ManageSiteSettingsService manageSiteSettingsService;

    /**
     * Obtiene la configuración actual del sitio
     */
    @GetMapping
    public ResponseEntity<SiteSettingsAdminDTO.SiteSettingsResponseDTO> getSiteSettings() {
        log.debug("GET /api/admin/site-settings - Obteniendo configuración del sitio");
        SiteSettingsAdminDTO.SiteSettingsResponseDTO settings = manageSiteSettingsService.getSiteSettings();
        return ResponseEntity.ok(settings);
    }

    /**
     * Actualiza la configuración del sitio
     */
    @PutMapping
    public ResponseEntity<SiteSettingsAdminDTO.SiteSettingsResponseDTO> updateSiteSettings(
            @Valid @RequestBody SiteSettingsAdminDTO.UpdateSiteSettingsDTO dto) {
        log.debug("PUT /api/admin/site-settings - Actualizando configuración del sitio");
        SiteSettingsAdminDTO.SiteSettingsResponseDTO updatedSettings = manageSiteSettingsService.updateSiteSettings(dto);
        return ResponseEntity.ok(updatedSettings);
    }
}

