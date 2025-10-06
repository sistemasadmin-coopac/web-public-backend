package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.entity.home.HomeStats;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsRepository;
import com.elsalvador.coopac.service.admin.home.HomeStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar estadísticas del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class HomeStatsServiceImpl implements HomeStatsService {

    private final HomeStatsRepository homeStatsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HomeStatsDTO> getAllStats() {
        log.debug("Obteniendo todas las estadísticas del home");
        return homeStatsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HomeStatsDTO> getActiveStats() {
        log.debug("Obteniendo estadísticas activas del home");
        return homeStatsRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HomeStatsDTO getStatsById(UUID id) {
        log.debug("Obteniendo estadística con ID: {}", id);
        HomeStats stats = homeStatsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));
        return convertToDTO(stats);
    }

    @Override
    @Transactional
    public HomeStatsDTO createStats(HomeStatsDTO dto) {
        log.debug("Creando nueva estadística: {}", dto.getLabel());

        HomeStats stats = HomeStats.builder()
                .label(dto.getLabel())
                .valueText(dto.getValueText())
                .icon(dto.getIcon())
                .displayOrder(dto.getDisplayOrder())
                .isActive(dto.getIsActive())
                .build();

        HomeStats savedStats = homeStatsRepository.save(stats);
        log.info("Estadística creada con ID: {}", savedStats.getId());

        return convertToDTO(savedStats);
    }

    @Override
    @Transactional
    public HomeStatsDTO updateStats(UUID id, HomeStatsDTO dto) {
        log.debug("Actualizando estadística con ID: {}", id);

        HomeStats existingStats = homeStatsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));

        existingStats.setLabel(dto.getLabel());
        existingStats.setValueText(dto.getValueText());
        existingStats.setIcon(dto.getIcon());
        existingStats.setDisplayOrder(dto.getDisplayOrder());
        existingStats.setIsActive(dto.getIsActive());

        HomeStats updatedStats = homeStatsRepository.save(existingStats);
        log.info("Estadística actualizada con ID: {}", updatedStats.getId());

        return convertToDTO(updatedStats);
    }

    @Override
    @Transactional
    public void deleteStats(UUID id) {
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
                .displayOrder(stats.getDisplayOrder())
                .isActive(stats.getIsActive())
                .build();
    }
}
