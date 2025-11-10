package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.config.CacheConfig;
import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;
import com.elsalvador.coopac.entity.home.HomeStatsSection;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsSectionRepository;
import com.elsalvador.coopac.service.admin.home.UpdateHomeStatsSectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación del servicio para actualizar secciones de estadísticas del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateHomeStatsSectionServiceImpl implements UpdateHomeStatsSectionService {

    private final HomeStatsSectionRepository homeStatsSectionRepository;

    @Override
    @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
    public HomeStatsSectionDTO updateStatsSection(HomeStatsSectionDTO dto) {
        log.debug("Actualizando sección de estadísticas con ID: {}", dto.getId());

        HomeStatsSection existingSection = homeStatsSectionRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sección de estadísticas no encontrada con ID: " + dto.getId()));

        existingSection.setTitle(dto.getTitle());
        existingSection.setSubtitle(dto.getSubtitle());
        existingSection.setIsActive(dto.getIsActive());

        HomeStatsSection updatedSection = homeStatsSectionRepository.save(existingSection);
        log.info("Sección de estadísticas actualizada con ID: {}", updatedSection.getId());

        return convertToDTO(updatedSection);
    }

    /**
     * Convierte entidad a DTO
     */
    private HomeStatsSectionDTO convertToDTO(HomeStatsSection section) {
        return HomeStatsSectionDTO.builder()
                .id(section.getId())
                .title(section.getTitle())
                .subtitle(section.getSubtitle())
                .isActive(section.getIsActive())
                .build();
    }
}

