package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomePromotionsAdminDTO;
import com.elsalvador.coopac.entity.home.HomePromotions;
import com.elsalvador.coopac.repository.HomePromotionsRepository;
import com.elsalvador.coopac.service.admin.home.GetHomePromotionsService;
import com.elsalvador.coopac.service.admin.home.mapper.HomePromotionsAdminMapper;
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
@Transactional(readOnly = true)
public class GetHomePromotionsServiceImpl implements GetHomePromotionsService {

    private final HomePromotionsRepository repository;
    private final HomePromotionsAdminMapper mapper;

    @Override
    public List<HomePromotionsAdminDTO> getPromotionsBySection(UUID sectionId) {
        log.info("Obteniendo promociones activas para sección ID: {}", sectionId);

        List<HomePromotions> promotions = repository.findBySectionIdAndIsActiveTrueOrderByIsFeaturedDescDisplayOrderAsc(sectionId);

        return promotions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HomePromotionsAdminDTO getPromotionById(UUID id) {
        log.info("Obteniendo promoción con ID: {}", id);

        Optional<HomePromotions> promotion = repository.findById(id);

        if (promotion.isEmpty()) {
            log.warn("Promoción no encontrada con ID: {}", id);
            throw new RuntimeException("Promotion not found with ID: " + id);
        }

        return mapper.toDTO(promotion.get());
    }

    @Override
    public List<HomePromotionsAdminDTO> getAllPromotionsBySection(UUID sectionId) {
        log.info("Obteniendo todas las promociones para sección ID: {}", sectionId);

        // Necesitamos crear un método en el repository para obtener todas las promociones (activas e inactivas)
        List<HomePromotions> promotions = repository.findBySectionIdOrderByIsFeaturedDescDisplayOrderAsc(sectionId);

        return promotions.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
