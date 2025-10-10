package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar valores organizacionales
 */
public interface ManageAboutValuesService {

    /**
     * Crea un nuevo valor
     */
    AboutAdminDTO.AboutValueDTO createValue(AboutAdminDTO.AboutValueDTO dto);

    /**
     * Actualiza un valor existente
     */
    AboutAdminDTO.AboutValueDTO updateValue(UUID id, AboutAdminDTO.AboutValueDTO dto);

    /**
     * Elimina un valor
     */
    void deleteValue(UUID id);

    /**
     * Obtiene la configuración de la sección de valores
     */
    AboutAdminDTO.AboutValuesSectionDTO getValuesSection();

    /**
     * Actualiza la configuración de la sección de valores
     */
    AboutAdminDTO.AboutValuesSectionDTO updateValuesSection(AboutAdminDTO.AboutValuesSectionDTO dto);
}
