package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomeCtaBlocksAdminDTO;
import com.elsalvador.coopac.entity.home.HomeCtaBlocks;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeCtaBlocksRepository;
import com.elsalvador.coopac.service.admin.home.UpdateHomeCtaBlocksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.HOME_PAGE_CACHE;

import java.util.UUID;

/**
 * Implementación del servicio para actualizar bloques CTA del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateHomeCtaBlocksServiceImpl implements UpdateHomeCtaBlocksService {

    private final HomeCtaBlocksRepository homeCtaBlocksRepository;

    @Override
    @CacheEvict(value = HOME_PAGE_CACHE, allEntries = true)
    public HomeCtaBlocksAdminDTO.HomeCtaBlockResponseDTO updateCtaBlock(
            UUID id,
            HomeCtaBlocksAdminDTO.UpdateHomeCtaBlockDTO updateDTO) {

        log.info("Actualizando bloque CTA con ID: {}", id);

        HomeCtaBlocks ctaBlock = homeCtaBlocksRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bloque CTA no encontrado con ID: " + id));

        // Actualizar solo los campos proporcionados
        if (updateDTO.title() != null) {
            ctaBlock.setTitle(updateDTO.title());
        }
        if (updateDTO.subtitle() != null) {
            ctaBlock.setSubtitle(updateDTO.subtitle());
        }
        if (updateDTO.buttonText() != null) {
            ctaBlock.setButtonText(updateDTO.buttonText());
        }
        if (updateDTO.buttonUrl() != null) {
            ctaBlock.setButtonUrl(updateDTO.buttonUrl());
        }
        if (updateDTO.isActive() != null) {
            ctaBlock.setIsActive(updateDTO.isActive());
        }

        HomeCtaBlocks updatedCtaBlock = homeCtaBlocksRepository.save(ctaBlock);

        log.info("Bloque CTA actualizado exitosamente con ID: {}", id);

        return mapToResponseDTO(updatedCtaBlock);
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
                ctaBlock.getIsActive()
        );
    }
}
