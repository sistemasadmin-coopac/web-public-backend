package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;

/**
 * Servicio para gestionar misión y visión
 */
public interface ManageAboutMissionVisionService {

    /**
     * Crea o actualiza la configuración de misión y visión
     */
    AboutAdminDTO.AboutMissionVisionDTO saveMissionVision(AboutAdminDTO.AboutMissionVisionDTO dto);

}
