package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionFeaturesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entidades de promociones a DTOs
 */
@Component
@RequiredArgsConstructor
public class PromotionsMapper {

    private final HomePromotionFeaturesRepository homePromotionFeaturesRepository;

    /**
     * Mapea una sección de promociones a PromotionsSectionDTO
     */
    public HomePageDTO.PromotionsSectionDTO toPromotionsSectionDTO(
            HomePromotionsSection promotionsSection,
            List<HomePromotions> promotions) {

        if (promotionsSection == null) {
            return null;
        }

        return new HomePageDTO.PromotionsSectionDTO(
            promotionsSection.getTitleMain(),
            promotionsSection.getTitleHighlight(),
            promotionsSection.getSubtitle(),
            mapPromotions(promotions)
        );
    }

    /**
     * Mapea las promociones con sus características
     */
    private List<HomePageDTO.PromotionDTO> mapPromotions(List<HomePromotions> promotions) {
        return promotions.stream()
            .map(promotion -> {
                var features = homePromotionFeaturesRepository
                    .findByPromotionIdOrderByDisplayOrderAsc(promotion.getId())
                    .stream()
                    .map(HomePromotionFeatures::getFeatureText)
                    .collect(Collectors.toList());

                var cta = new HomePageDTO.CtaLinkDTO(
                    promotion.getCtaText(),
                    promotion.getCtaUrl()
                );

                return new HomePageDTO.PromotionDTO(
                    promotion.getTitle(),
                    promotion.getTag(),
                    promotion.getDescription(),
                    promotion.getHighlightText(),
                    cta,
                    features,
                    promotion.getDisplayOrder()
                );
            })
            .collect(Collectors.toList());
    }
}
