package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;

import java.util.UUID;

/**
 * Service para gestionar HomePromotions (crear, actualizar, eliminar)
 */
public interface ManageHomePromotionsService {

    /**
     * Crea una nueva promoción
     */
    HomePromotionsAdminDTO createPromotion(HomePromotionsAdminDTO promotionDTO);

    /**
     * Actualiza una promoción existente
     */
    HomePromotionsAdminDTO updatePromotion(HomePromotionsAdminDTO promotionDTO);

    /**
     * Elimina una promoción (desactiva)
     */
    void deletePromotion(UUID promotionId);

    /**
     * Activa una promoción
     */
    HomePromotionsAdminDTO activatePromotion(UUID promotionId);
}
