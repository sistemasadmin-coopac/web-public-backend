package com.elsalvador.coopac.service.admin.about;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar métricas de impacto
 */
public interface ManageAboutImpactService {

    /**
     * Actualiza una métrica de impacto existente
     */
    AboutAdminDTO.AboutImpactMetricDTO updateImpactMetric(UUID id, AboutAdminDTO.AboutImpactMetricDTO dto);

    /**
     * Actualiza la configuración de la sección de impacto
     */
    AboutAdminDTO.AboutImpactSectionDTO updateImpactSection(AboutAdminDTO.AboutImpactSectionDTO dto);
}
