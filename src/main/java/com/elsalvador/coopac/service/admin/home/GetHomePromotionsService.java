package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service para obtener HomePromotions
 */
public interface GetHomePromotionsService {

    /**
     * Obtiene todas las promociones activas de una sección
     */
    List<HomePromotionsAdminDTO> getPromotionsBySection(UUID sectionId);

    /**
     * Obtiene una promoción por ID
     */
    HomePromotionsAdminDTO getPromotionById(UUID id);

    /**
     * Obtiene todas las promociones (activas e inactivas) de una sección
     */
    List<HomePromotionsAdminDTO> getAllPromotionsBySection(UUID sectionId);

    /**
     * Servicio para gestionar secciones de estadísticas del home
     */
    interface HomeStatsSectionService {

        /**
         * Obtiene todas las secciones de estadísticas
         */
        List<HomeStatsSectionDTO> getAllSections();

        /**
         * Obtiene la primera sección activa
         */
        HomeStatsSectionDTO getActiveSection();

        /**
         * Obtiene una sección por ID
         */
        HomeStatsSectionDTO getSectionById(UUID id);

        /**
         * Crea una nueva sección de estadísticas
         */
        HomeStatsSectionDTO createSection(HomeStatsSectionDTO dto);

        /**
         * Actualiza una sección existente
         */
        HomeStatsSectionDTO updateSection(UUID id, HomeStatsSectionDTO dto);

        /**
         * Elimina una sección de estadísticas
         */
        void deleteSection(UUID id);
    }

    /**
     * Servicio para gestionar estadísticas del home
     */
    interface HomeStatsService {

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
}
