package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionsSectionAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotionsSection;
import com.elsalvador.coopac.repository.HomePromotionsSectionRepository;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsSectionService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionsSectionAdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetHomePromotionsSectionServiceImpl implements GetHomePromotionsSectionService {

    private final HomePromotionsSectionRepository repository;
    private final HomePromotionsSectionAdminMapper mapper;

    @Override
    public HomePromotionsSectionAdminDTO getActivePromotionsSection() {
        log.info("Obteniendo sección de promociones activa");

        Optional<HomePromotionsSection> section = repository.findFirstByIsActiveTrue();

        if (section.isEmpty()) {
            log.warn("No se encontró sección de promociones activa");
            throw new RuntimeException("No active promotions section found");
        }

        return mapper.toDTO(section.get());
    }

    @Override
    public HomePromotionsSectionAdminDTO getPromotionsSectionById(UUID id) {
        log.info("Obteniendo sección de promociones con ID: {}", id);

        Optional<HomePromotionsSection> section = repository.findById(id);

        if (section.isEmpty()) {
            log.warn("Sección de promociones no encontrada con ID: {}", id);
            throw new RuntimeException("Promotions section not found with ID: " + id);
        }

        return mapper.toDTO(section.get());
    }
}
