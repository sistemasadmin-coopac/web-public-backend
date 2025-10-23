package com.elsalvador.coopac.service.admin.home.mapper;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.service.storage.FileStorageService;
import com.elsalvador.coopac.util.ImagePlaceholderUtil;
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
    private final FileStorageService fileStorageService;

    private static final String FOLDER = "home-promotions";

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

        // Obtener la imagen desde Storage (nombre del archivo es el ID de la promoción)
        String imageBase64 = null;
        try {
            // Buscar la imagen con extensiones comunes
            String[] extensions = {".jpg", ".jpeg", ".png", ".webp"};
            for (String ext : extensions) {
                String fileName = entity.getId().toString() + ext;
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

        return new HomePromotionsAdminDTO(
            entity.getId(),
            entity.getSection() != null ? entity.getSection().getId() : null,
            entity.getTitle(),
            entity.getTag(),
            entity.getDescription(),
            entity.getHighlightText(),
            entity.getIsFeatured(),
            entity.getIsActive(),
            features,
            imageBase64
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
        entity.setIsFeatured(dto.isFeatured());
        entity.setIsActive(dto.isActive());
    }
}
