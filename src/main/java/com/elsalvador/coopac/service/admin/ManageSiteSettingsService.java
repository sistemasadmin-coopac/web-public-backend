package com.elsalvador.coopac.service.admin;

import com.elsalvador.coopac.dto.admin.SiteSettingsAdminDTO;

/**
 * Servicio para administrar la configuración del sitio
 */
public interface ManageSiteSettingsService {

    /**
     * Obtiene la configuración actual del sitio
     * @return configuración del sitio con los campos actuales
     */
    SiteSettingsAdminDTO.SiteSettingsResponseDTO getSiteSettings();

    /**
     * Actualiza la configuración del sitio
     * @param dto datos a actualizar
     * @return configuración del sitio actualizada
     */
    SiteSettingsAdminDTO.SiteSettingsResponseDTO updateSiteSettings(SiteSettingsAdminDTO.UpdateSiteSettingsDTO dto);
}

