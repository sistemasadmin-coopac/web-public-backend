package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomeStatsDTO;
import com.elsalvador.coopac.entity.home.HomeStats;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsRepository;
import com.elsalvador.coopac.service.admin.home.GetHomeStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para obtener estadísticas del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetHomeStatsServiceImpl implements GetHomeStatsService {

    private final HomeStatsRepository homeStatsRepository;

    @Override
    public List<HomeStatsDTO> getAllStats() {
        log.debug("Obteniendo todas las estadísticas del home");
        return homeStatsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HomeStatsDTO> getAllActiveStats() {
        log.debug("Obteniendo estadísticas activas del home");
        return homeStatsRepository.findByIsActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HomeStatsDTO getStatById(UUID id) {
        log.debug("Obteniendo estadística con ID: {}", id);
        HomeStats stats = homeStatsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadística no encontrada con ID: " + id));
        return convertToDTO(stats);
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

