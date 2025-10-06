package com.elsalvador.coopac.service.admin.contact;

import com.elsalvador.coopac.dto.admin.ContactAdminDTO;

/**
 * Servicio para obtener datos completos de contacto en administración
 */
public interface GetContactAdminService {

    /**
     * Obtiene todos los datos de contacto completos para administración
     * @return datos completos de contacto
     */
    ContactAdminDTO.ContactPageResponseDTO getContactCompleteData();
}
