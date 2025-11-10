package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service para obtener estadísticas del home
 */
public interface GetHomeStatsService {

    /**
     * Obtiene todas las estadísticas
     */
    List<HomeStatsDTO> getAllStats();

    /**
     * Obtiene las estadísticas activas ordenadas
     */
    List<HomeStatsDTO> getAllActiveStats();

    /**
     * Obtiene una estadística por ID
     */
    HomeStatsDTO getStatById(UUID id);
}

