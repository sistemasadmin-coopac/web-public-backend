package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;

import java.util.UUID;

/**
 * Service para obtener HomePromotionsSection
 */
public interface GetHomePromotionsSectionService {

    /**
     * Obtiene la sección de promociones activa
     */
    HomePromotionsSectionAdminDTO getActivePromotionsSection();

    /**
     * Obtiene una sección de promociones por ID
     */
    HomePromotionsSectionAdminDTO getPromotionsSectionById(UUID id);
}
