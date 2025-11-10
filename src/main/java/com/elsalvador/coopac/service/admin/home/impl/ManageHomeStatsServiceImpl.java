package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.config.CacheConfig;
import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.entity.home.HomeStats;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsRepository;
import com.elsalvador.coopac.service.admin.home.ManageHomeStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar estadísticas del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageHomeStatsServiceImpl implements ManageHomeStatsService {

    private final HomeStatsRepository homeStatsRepository;

    @Override
    @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
    public HomeStatsDTO createStat(HomeStatsDTO dto) {
        log.debug("Creando nueva estadística: {}", dto.getLabel());

        // Calcular automáticamente el siguiente displayOrder
        Integer displayOrder = homeStatsRepository.findMaxDisplayOrder() != null ?
                homeStatsRepository.findMaxDisplayOrder() + 1 : 0;

        HomeStats stats = HomeStats.builder()
                .label(dto.getLabel())
                .valueText(dto.getValueText())
                .icon(dto.getIcon())
                .displayOrder(displayOrder)
                .isActive(dto.getIsActive())
                .build();

        HomeStats savedStats = homeStatsRepository.save(stats);
        log.info("Estadística creada con ID: {}", savedStats.getId());

        return convertToDTO(savedStats);
    }

    @Override
    @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
    public HomeStatsDTO updateStat(UUID id, HomeStatsDTO dto) {
        log.debug("Actualizando estadística con ID: {}", id);

        HomeStats existingStats = homeStatsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));

        existingStats.setLabel(dto.getLabel());
        existingStats.setValueText(dto.getValueText());
        existingStats.setIcon(dto.getIcon());
        existingStats.setIsActive(dto.getIsActive());

        HomeStats updatedStats = homeStatsRepository.save(existingStats);
        log.info("Estadística actualizada con ID: {}", updatedStats.getId());

        return convertToDTO(updatedStats);
    }

    @Override
    @CacheEvict(value = {CacheConfig.HOME_PAGE_CACHE}, allEntries = true)
    public void deleteStat(UUID id) {
        log.debug("Eliminando estadística con ID: {}", id);

        if (!homeStatsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estadística no encontrada con ID: " + id);
        }

        homeStatsRepository.deleteById(id);
        log.info("Estadística eliminada con ID: {}", id);
    }

    /**
     * Convierte entidad a DTO
     */
    private HomeStatsDTO convertToDTO(HomeStats stats) {
        return HomeStatsDTO.builder()
                .id(stats.getId())
                .label(stats.getLabel())
                .valueText(stats.getValueText())
                .icon(stats.getIcon())
                .isActive(stats.getIsActive())
                .build();
    }
}

