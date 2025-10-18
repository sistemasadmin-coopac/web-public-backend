package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionsRepository;
import com.elsalvador.coopac.repository.HomePromotionsSectionRepository;
import com.elsalvador.coopac.service.admin.home.ManageHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionsAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.HOME_PAGE_CACHE;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageHomePromotionsServiceImpl implements ManageHomePromotionsService {

    private final HomePromotionsRepository repository;
    private final HomePromotionsSectionRepository sectionRepository;
    private final HomePromotionsAdminMapper mapper;

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsAdminDTO createPromotion(HomePromotionsAdminDTO promotionDTO) {
        log.info("Creando nueva promoción para sección ID: {}", promotionDTO.sectionId());

        // Verificar que la sección existe
        Optional<HomePromotionsSection> section = sectionRepository.findById(promotionDTO.sectionId());
        if (section.isEmpty()) {
            log.warn("Sección de promociones no encontrada con ID: {}", promotionDTO.sectionId());
            throw new RuntimeException("Promotions section not found with ID: " + promotionDTO.sectionId());
        }

        HomePromotions newPromotion = HomePromotions.builder()
                .section(section.get())
                .title(promotionDTO.title())
                .tag(promotionDTO.tag())
                .description(promotionDTO.description())
                .highlightText(promotionDTO.highlightText())
                .ctaText(promotionDTO.ctaText())
                .ctaUrl(promotionDTO.ctaUrl())
                .isFeatured(promotionDTO.isFeatured() != null ? promotionDTO.isFeatured() : false)
                .displayOrder(promotionDTO.displayOrder() != null ? promotionDTO.displayOrder() : 0)
                .isActive(promotionDTO.isActive() != null ? promotionDTO.isActive() : true)
                .build();

        HomePromotions savedPromotion = repository.save(newPromotion);
        log.info("Promoción creada exitosamente con ID: {}", savedPromotion.getId());

        return mapper.toDTO(savedPromotion);
    }

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsAdminDTO updatePromotion(HomePromotionsAdminDTO promotionDTO) {
        log.info("Actualizando promoción con ID: {}", promotionDTO.id());

        Optional<HomePromotions> promotionDb = repository.findById(promotionDTO.id());
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionDTO.id());
            throw new RuntimeException("Promotion not found with ID: " + promotionDTO.id());
        }

        // Verificar que la sección existe si se está cambiando
        HomePromotionsSection section = null;
        if (promotionDTO.sectionId() != null) {
            Optional<HomePromotionsSection> sectionOpt = sectionRepository.findById(promotionDTO.sectionId());
            if (sectionOpt.isEmpty()) {
                log.warn("Sección de promociones no encontrada con ID: {}", promotionDTO.sectionId());
                throw new RuntimeException("Promotions section not found with ID: " + promotionDTO.sectionId());
            }
            section = sectionOpt.get();
        }

        mapper.updateEntity(promotionDb.get(), promotionDTO, section);
        HomePromotions savedPromotion = repository.save(promotionDb.get());

        log.info("Promoción actualizada exitosamente con ID: {}", savedPromotion.getId());
        return mapper.toDTO(savedPromotion);
    }

    @Override
    public void deletePromotion(UUID promotionId) {
        log.info("Desactivando promoción con ID: {}", promotionId);

        Optional<HomePromotions> promotionDb = repository.findById(promotionId);
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionId);
            throw new RuntimeException("Promotion not found with ID: " + promotionId);
        }

        promotionDb.get().setIsActive(false);
        repository.save(promotionDb.get());

        log.info("Promoción desactivada exitosamente con ID: {}", promotionId);
    }

    @Override
    public HomePromotionsAdminDTO activatePromotion(UUID promotionId) {
        log.info("Activando promoción con ID: {}", promotionId);

        Optional<HomePromotions> promotionDb = repository.findById(promotionId);
        if (promotionDb.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", promotionId);
            throw new RuntimeException("Promotion not found with ID: " + promotionId);
        }

        promotionDb.get().setIsActive(true);
        HomePromotions savedPromotion = repository.save(promotionDb.get());

        log.info("Promoción activada exitosamente con ID: {}", promotionId);
        return mapper.toDTO(savedPromotion);
    }
}
