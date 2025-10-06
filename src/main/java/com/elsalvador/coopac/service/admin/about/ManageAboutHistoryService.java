package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar la historia y timeline de eventos
 */
public interface ManageAboutHistoryService {

    /**
     * Crea un nuevo evento del timeline
     */
    AboutAdminDTO.AboutTimelineEventDTO createTimelineEvent(AboutAdminDTO.AboutTimelineEventDTO dto);

    /**
     * Actualiza un evento del timeline existente
     */
    AboutAdminDTO.AboutTimelineEventDTO updateTimelineEvent(UUID id, AboutAdminDTO.AboutTimelineEventDTO dto);

    /**
     * Elimina un evento del timeline
     */
    void deleteTimelineEvent(UUID id);

    /**
     * Actualiza la configuración de la sección de historia
     */
    AboutAdminDTO.AboutHistorySectionDTO updateHistorySection(AboutAdminDTO.AboutHistorySectionDTO dto);
}
