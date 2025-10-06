package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;

import java.util.List;
import java.util.UUID;

/**
 * Servicio para gestionar estadísticas del home
 */
public interface HomeStatsService {

    /**
     * Obtiene todas las estadísticas
     */
    List<HomeStatsDTO> getAllStats();

    /**
     * Obtiene las estadísticas activas ordenadas
     */
    List<HomeStatsDTO> getActiveStats();

    /**
     * Obtiene una estadística por ID
     */
    HomeStatsDTO getStatsById(UUID id);

    /**
     * Crea una nueva estadística
     */
    HomeStatsDTO createStats(HomeStatsDTO dto);

    /**
     * Actualiza una estadística existente
     */
    HomeStatsDTO updateStats(UUID id, HomeStatsDTO dto);

    /**
     * Elimina una estadística
     */
    void deleteStats(UUID id);
}
