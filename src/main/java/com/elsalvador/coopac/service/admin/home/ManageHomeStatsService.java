package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;

import java.util.UUID;

/**
 * Service para gestionar estadísticas del home
 */
public interface ManageHomeStatsService {

    /**
     * Crea una nueva estadística
     */
    HomeStatsDTO createStat(HomeStatsDTO dto);

    /**
     * Actualiza una estadística existente
     */
    HomeStatsDTO updateStat(UUID id, HomeStatsDTO dto);

    /**
     * Elimina una estadística
     */
    void deleteStat(UUID id);
}

