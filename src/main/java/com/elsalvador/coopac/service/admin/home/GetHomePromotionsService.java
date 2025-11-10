package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;

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
}
