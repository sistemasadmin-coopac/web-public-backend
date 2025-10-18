package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionsSectionRepository;
import com.elsalvador.coopac.service.admin.home.UpdateHomePromotionsSectionService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionsSectionAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.HOME_PAGE_CACHE;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateHomePromotionsSectionServiceImpl implements UpdateHomePromotionsSectionService {

    private final HomePromotionsSectionRepository repository;
    private final HomePromotionsSectionAdminMapper mapper;

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomePromotionsSectionAdminDTO updatePromotionsSection(HomePromotionsSectionAdminDTO sectionDTO) {
        log.info("Actualizando sección de promociones con ID: {}", sectionDTO.id());

        Optional<HomePromotionsSection> sectionDb = repository.findById(sectionDTO.id());

        if (sectionDb.isEmpty()) {
            log.warn("Sección de promociones no encontrada con ID: {}", sectionDTO.id());
            throw new RuntimeException("Promotions section not found with ID: " + sectionDTO.id());
        }

        mapper.updateEntity(sectionDb.get(), sectionDTO);
        HomePromotionsSection savedSection = repository.save(sectionDb.get());

        log.info("Sección de promociones actualizada exitosamente con ID: {}", savedSection.getId());
        return mapper.toDTO(savedSection);
    }
}
