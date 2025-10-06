package com.elsalvador.coopac.service.admin.home;

import com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service para gestionar HomePromotionFeatures
 */
public interface ManageHomePromotionFeaturesService {

    /**
     * Obtiene todas las características de una promoción
     */
    List<HomePromotionFeaturesAdminDTO> getFeaturesByPromotion(UUID promotionId);

    /**
     * Crea una nueva característica para una promoción
     */
    HomePromotionFeaturesAdminDTO createFeature(HomePromotionFeaturesAdminDTO featureDTO);

    /**
     * Actualiza una característica existente
     */
    HomePromotionFeaturesAdminDTO updateFeature(HomePromotionFeaturesAdminDTO featureDTO);

    /**
     * Elimina una característica
     */
    void deleteFeature(UUID featureId);

    /**
     * Actualiza todas las características de una promoción (reemplaza las existentes)
     */
    List<HomePromotionFeaturesAdminDTO> updatePromotionFeatures(UUID promotionId, List<HomePromotionFeaturesAdminDTO> features);
}
