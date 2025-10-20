package com.elsalvador.coopac.service.publicpages.mapper;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionFeaturesRepository;
import com.elsalvador.coopac.service.storage.FileStorageService;
import com.elsalvador.coopac.util.ImagePlaceholderUtil;
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
    private final FileStorageService fileStorageService;

    private static final String FOLDER = "home-promotions";

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

                // Obtener imagen desde Storage
                String imageBase64 = null;
                try {
                    // Buscar la imagen con extensiones comunes
                    String[] extensions = {".jpg", ".jpeg", ".png", ".webp"};
                    for (String ext : extensions) {
                        String fileName = promotion.getId().toString() + ext;
                        imageBase64 = fileStorageService.getFileAsBase64(fileName, FOLDER);
                        if (imageBase64 != null) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    // Si no existe imagen, continuamos sin ella
                }

                // Si no hay imagen, usar placeholder genérico
                if (imageBase64 == null) {
                    imageBase64 = ImagePlaceholderUtil.PROMOTION_PLACEHOLDER;
                }

                return new HomePageDTO.PromotionDTO(
                    promotion.getTitle(),
                    promotion.getTag(),
                    promotion.getDescription(),
                    promotion.getHighlightText(),
                    features,
                    promotion.getDisplayOrder(),
                    imageBase64
                );
            })
            .collect(Collectors.toList());
    }
}
