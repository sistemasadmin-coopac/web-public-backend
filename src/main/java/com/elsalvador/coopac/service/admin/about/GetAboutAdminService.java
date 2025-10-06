package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;

/**
 * Servicio para obtener datos completos de la página About
 */
public interface GetAboutAdminService {

    /**
     * Obtiene todos los datos completos de la página About
     * @return datos completos de la página About
     */
    AboutAdminDTO.AboutPageResponseDTO getAboutCompleteData();
}
