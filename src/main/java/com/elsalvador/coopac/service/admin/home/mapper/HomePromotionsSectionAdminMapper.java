package com.elsalvador.coopac.service.admin.home.mapper;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre HomePromotionsSection y HomePromotionsSectionAdminDTO
 */
@Component
public class HomePromotionsSectionAdminMapper {

    /**
     * Convierte entidad a DTO
     */
    public HomePromotionsSectionAdminDTO toDTO(HomePromotionsSection entity) {
        if (entity == null) return null;

        return new HomePromotionsSectionAdminDTO(
            entity.getId(),
            entity.getTitleMain(),
            entity.getTitleHighlight(),
            entity.getSubtitle(),
            entity.getIsActive()
        );
    }

    /**
     * Actualiza entidad con datos del DTO
     */
    public void updateEntity(HomePromotionsSection entity, HomePromotionsSectionAdminDTO dto) {
        if (entity == null || dto == null) return;

        entity.setTitleMain(dto.titleMain());
        entity.setTitleHighlight(dto.titleHighlight());
        entity.setSubtitle(dto.subtitle());
        entity.setIsActive(dto.isActive());
    }
}
