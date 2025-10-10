package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;

/**
 * Service para actualizar HomePromotionsSection
 */
public interface UpdateHomePromotionsSectionService {

    /**
     * Actualiza una sección de promociones
     */
    HomePromotionsSectionAdminDTO updatePromotionsSection(HomePromotionsSectionAdminDTO sectionDTO);
}
