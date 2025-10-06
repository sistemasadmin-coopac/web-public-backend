package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;
import com.elsalvador.coopac.entity.home.HomeCtaBlocks;
import com.elsalvador.coopac.repository.HomeCtaBlocksRepository;
import com.elsalvador.coopac.service.admin.home.GetHomeCtaBlocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para obtener bloques CTA del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetHomeCtaBlocksServiceImpl implements GetHomeCtaBlocksService {

    private final HomeCtaBlocksRepository homeCtaBlocksRepository;

    @Override
    public List<HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO> getAllActiveCtaBlocks() {
        log.info("Obteniendo todos los bloques CTA activos");

        List<HomeCtaBlocks> ctaBlocks = homeCtaBlocksRepository
                .findByIsActiveTrueOrderByPositionAscDisplayOrderAsc();

        return ctaBlocks.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO mapToResponseDTO(HomeCtaBlocks ctaBlock) {
        return new HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO(
                ctaBlock.getId(),
                ctaBlock.getPosition(),
                ctaBlock.getTitle(),
                ctaBlock.getSubtitle(),
                ctaBlock.getButtonText(),
                ctaBlock.getButtonUrl(),
                ctaBlock.getDisplayOrder(),
                ctaBlock.getIsActive()
        );
    }
}
