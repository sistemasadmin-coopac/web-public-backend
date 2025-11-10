package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;

import java.util.UUID;

/**
 * Service para obtener secciones de estadísticas del home
 */
public interface GetHomeStatsSectionService {

    /**
     * Obtiene la primera sección activa
     */
    HomeStatsSectionDTO getActiveStatsSection();

    /**
     * Obtiene una sección por ID
     */
    HomeStatsSectionDTO getStatsSectionById(UUID id);
}

