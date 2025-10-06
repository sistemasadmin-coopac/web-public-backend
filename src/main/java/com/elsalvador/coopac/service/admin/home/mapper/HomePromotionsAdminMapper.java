package com.elsalvador.coopac.service.admin.home.mapper;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre HomePromotions y HomePromotionsAdminDTO
 */
@Component
@RequiredArgsConstructor
public class HomePromotionsAdminMapper {

    private final HomePromotionFeaturesAdminMapper featuresMapper;

    /**
     * Convierte entidad a DTO
     */
    public HomePromotionsAdminDTO toDTO(HomePromotions entity) {
        if (entity == null) return null;

        List<com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO> features =
            entity.getFeatures() != null ?
            entity.getFeatures().stream()
                .map(featuresMapper::toDTO)
                .collect(Collectors.toList()) :
            Collections.emptyList();

        return new HomePromotionsAdminDTO(
            entity.getId(),
            entity.getSection() != null ? entity.getSection().getId() : null,
            entity.getTitle(),
            entity.getTag(),
            entity.getDescription(),
            entity.getHighlightText(),
            entity.getCtaText(),
            entity.getCtaUrl(),
            entity.getIsFeatured(),
            entity.getDisplayOrder(),
            entity.getIsActive(),
            features
        );
    }

    /**
     * Actualiza entidad con datos del DTO
     */
    public void updateEntity(HomePromotions entity, HomePromotionsAdminDTO dto, HomePromotionsSection section) {
        if (entity == null || dto == null) return;

        entity.setSection(section);
        entity.setTitle(dto.title());
        entity.setTag(dto.tag());
        entity.setDescription(dto.description());
        entity.setHighlightText(dto.highlightText());
        entity.setCtaText(dto.ctaText());
        entity.setCtaUrl(dto.ctaUrl());
        entity.setIsFeatured(dto.isFeatured());
        entity.setDisplayOrder(dto.displayOrder());
        entity.setIsActive(dto.isActive());
    }
}
