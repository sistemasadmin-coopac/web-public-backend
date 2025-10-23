package com.elsalvador.coopac.service.admin.home.mapper;

import com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import com.elsalvador.coopac.entity.home.HomePromotions;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre HomePromotionFeatures y HomePromotionFeaturesAdminDTO
 */
@Component
public class HomePromotionFeaturesAdminMapper {

    /**
     * Convierte entidad a DTO
     */
    public HomePromotionFeaturesAdminDTO toDTO(HomePromotionFeatures entity) {
        if (entity == null) return null;

        return new HomePromotionFeaturesAdminDTO(
            entity.getId(),
            entity.getPromotion() != null ? entity.getPromotion().getId() : null,
            entity.getFeatureText()
        );
    }

    /**
     * Actualiza entidad con datos del DTO
     */
    public void updateEntity(HomePromotionFeatures entity, HomePromotionFeaturesAdminDTO dto, HomePromotions promotion) {
        if (entity == null || dto == null) return;

        entity.setPromotion(promotion);
        entity.setFeatureText(dto.featureText());
    }
}
