package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;

/**
 * Service para actualizar secciones de estadísticas del home
 */
public interface UpdateHomeStatsSectionService {

    /**
     * Actualiza una sección existente
     */
    HomeStatsSectionDTO updateStatsSection(HomeStatsSectionDTO dto);
}

