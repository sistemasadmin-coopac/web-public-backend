package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionFeaturesAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotionFeatures;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.repository.HomePromotionFeaturesRepository;
import com.elsalvador.coopac.repository.HomePromotionsRepository;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionFeaturesService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionFeaturesAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageHomePromotionFeaturesServiceImpl implements ManageHomePromotionFeaturesService {

    private final HomePromotionFeaturesRepository repository;
    private final HomePromotionsRepository promotionsRepository;
    private final HomePromotionFeaturesAdminMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<HomePromotionFeaturesAdminDTO> getFeaturesByPromotion(UUID promotionId) {
        log.info("Obteniendo características para promoción ID: {}", promotionId);

        List<HomePromotionFeatures> features = repository.findByPromotionIdOrderByDisplayOrderAsc(promotionId);

        return features.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HomePromotionFeaturesAdminDTO createFeature(HomePromotionFeaturesAdminDTO featureDTO) {
        log.info("Creando nueva característica para promoción ID: {}", featureDTO.promotionId());

        // Verificar que la promoción existe
        Optional<HomePromotions> promotion = promotionsRepository.findById(featureDTO.promotionId());
        if (promotion.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", featureDTO.promotionId());
            throw new RuntimeException("Promotion not found with ID: " + featureDTO.promotionId());
        }

        HomePromotionFeatures newFeature = HomePromotionFeatures.builder()
                .promotion(promotion.get())
                .featureText(featureDTO.featureText())
                .displayOrder(featureDTO.displayOrder() != null ? featureDTO.displayOrder() : 0)
                .build();

        HomePromotionFeatures savedFeature = repository.save(newFeature);
        log.info("Característica creada exitosamente con ID: {}", savedFeature.getId());

        return mapper.toDTO(savedFeature);
    }

    @Override
    public HomePromotionFeaturesAdminDTO updateFeature(HomePromotionFeaturesAdminDTO featureDTO) {
        log.info("Actualizando característica con ID: {}", featureDTO.id());

        Optional<HomePromotionFeatures> featureDb = repository.findById(featureDTO.id());
        if (featureDb.isEmpty()) {
            log.warn("Característica no encontrada con ID: {}", featureDTO.id());
            throw new RuntimeException("Feature not found with ID: " + featureDTO.id());
        }

        // Verificar que la promoción existe si se está cambiando
        HomePromotions promotion = null;
        if (featureDTO.promotionId() != null) {
            Optional<HomePromotions> promotionOpt = promotionsRepository.findById(featureDTO.promotionId());
            if (promotionOpt.isEmpty()) {
                log.warn("Promoción no encontrada con ID: {}", featureDTO.promotionId());
                throw new RuntimeException("Promotion not found with ID: " + featureDTO.promotionId());
            }
            promotion = promotionOpt.get();
        }

        mapper.updateEntity(featureDb.get(), featureDTO, promotion);
        HomePromotionFeatures savedFeature = repository.save(featureDb.get());

        log.info("Característica actualizada exitosamente con ID: {}", savedFeature.getId());
        return mapper.toDTO(savedFeature);
    }

    @Override
    public void deleteFeature(UUID featureId) {
        log.info("Eliminando característica con ID: {}", featureId);

        Optional<HomePromotionFeatures> featureDb = repository.findById(featureId);
        if (featureDb.isEmpty()) {
            log.warn("Característica no encontrada con ID: {}", featureId);
            throw new RuntimeException("Feature not found with ID: " + featureId);
        }

        repository.delete(featureDb.get());
        log.info("Característica eliminada exitosamente con ID: {}", featureId);
    }

    @Override
    public List<HomePromotionFeaturesAdminDTO> updatePromotionFeatures(UUID promotionId, List<HomePromotionFeaturesAdminDTO> features) {
        log.info("Actualizando todas las características para promoción ID: {}", promotionId);

        // Verificar que la promoción existe
        Optional<HomePromotions> promotion = promotionsRepository.findById(promotionId);
        if (promotion.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionId);
            throw new RuntimeException("Promotion not found with ID: " + promotionId);
        }

        // Eliminar todas las características existentes de la promoción
        List<HomePromotionFeatures> existingFeatures = repository.findByPromotionIdOrderByDisplayOrderAsc(promotionId);
        repository.deleteAll(existingFeatures);

        // Crear las nuevas características
        List<HomePromotionFeatures> newFeatures = features.stream()
                .map(featureDTO -> HomePromotionFeatures.builder()
                        .promotion(promotion.get())
                        .featureText(featureDTO.featureText())
                        .displayOrder(featureDTO.displayOrder() != null ? featureDTO.displayOrder() : 0)
                        .build())
                .collect(Collectors.toList());

        List<HomePromotionFeatures> savedFeatures = repository.saveAll(newFeatures);

        log.info("Características actualizadas exitosamente para promoción ID: {}. Total: {}", promotionId, savedFeatures.size());

        return savedFeatures.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
