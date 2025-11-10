package com.elsalvador.coopac.service.admin.home.impl;

import com.elsalvador.coopac.dto.admin.HomeStatsSectionDTO;
import com.elsalvador.coopac.entity.home.HomeStatsSection;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.HomeStatsSectionRepository;
import com.elsalvador.coopac.service.admin.home.GetHomeStatsSectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para obtener secciones de estadísticas del home
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetHomeStatsSectionServiceImpl implements GetHomeStatsSectionService {

    private final HomeStatsSectionRepository homeStatsSectionRepository;

    @Override
    public HomeStatsSectionDTO getActiveStatsSection() {
        log.debug("Obteniendo sección activa de estadísticas del home");
        return homeStatsSectionRepository.findFirstByIsActiveTrue()
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("No hay sección activa de estadísticas"));
    }

    @Override
    public HomeStatsSectionDTO getStatsSectionById(UUID id) {
        log.debug("Obteniendo sección de estadísticas con ID: {}", id);
        HomeStatsSection section = homeStatsSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sección de estadísticas no encontrada con ID: " + id));
        return convertToDTO(section);
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

