package com.elsalvador.coopac.service.admin.about.impl;

import com.elsalvador.coopac.dto.admin.AboutAdminDTO;
import com.elsalvador.coopac.entity.about.AboutImpactMetrics;
import com.elsalvador.coopac.entity.about.AboutImpactSection;
import com.elsalvador.coopac.exception.EntityNotFoundException;
import com.elsalvador.coopac.repository.about.AboutImpactMetricsRepository;
import com.elsalvador.coopac.repository.about.AboutImpactSectionRepository;
import com.elsalvador.coopac.service.admin.about.ManageAboutImpactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.ABOUT_PAGE_CACHE;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar métricas de impacto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManageAboutImpactServiceImpl implements ManageAboutImpactService {

    private final AboutImpactMetricsRepository impactMetricsRepository;
    private final AboutImpactSectionRepository impactSectionRepository;

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutImpactMetricDTO updateImpactMetric(UUID id, AboutAdminDTO.AboutImpactMetricDTO dto) {
        log.info("Actualizando métrica de impacto con ID: {}", id);

        AboutImpactMetrics existingMetric = impactMetricsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Métrica de impacto no encontrada con ID: " + id));

        existingMetric.setLabel(dto.getLabel());
        existingMetric.setValueText(dto.getValueText());
        existingMetric.setFootnote(dto.getFootnote());
        existingMetric.setIcon(dto.getIcon());
        existingMetric.setDisplayOrder(dto.getDisplayOrder());
        existingMetric.setIsActive(dto.getIsActive());

        AboutImpactMetrics updatedMetric = impactMetricsRepository.save(existingMetric);
        log.info("Métrica de impacto actualizada exitosamente");

        return mapMetricToDTO(updatedMetric);
    }

    @Override
    @Transactional
    @CacheEvict(value = ABOUT_PAGE_CACHE, allEntries = true)
    public AboutAdminDTO.AboutImpactSectionDTO updateImpactSection(AboutAdminDTO.AboutImpactSectionDTO dto) {
        log.info("Actualizando configuración de la sección de impacto");

        AboutImpactSection section = impactSectionRepository.findFirstByOrderByUpdatedAtDesc()
                .orElse(createDefaultImpactSection());

        section.setTitle(dto.getTitle());
        section.setSubtitle(dto.getSubtitle());
        section.setIsActive(dto.getIsActive());

        AboutImpactSection updatedSection = impactSectionRepository.save(section);
        log.info("Configuración de la sección de impacto actualizada exitosamente");

        return mapSectionToDTO(updatedSection);
    }

    private AboutImpactSection createDefaultImpactSection() {
        return AboutImpactSection.builder()
                .title("Nuestro Impacto")
                .subtitle("Las cifras que demuestran nuestro compromiso con la comunidad")
                .isActive(true)
                .build();
    }

    private AboutAdminDTO.AboutImpactMetricDTO mapMetricToDTO(AboutImpactMetrics metric) {
        return AboutAdminDTO.AboutImpactMetricDTO.builder()
                .id(metric.getId())
                .label(metric.getLabel())
                .valueText(metric.getValueText())
                .footnote(metric.getFootnote())
                .icon(metric.getIcon())
                .displayOrder(metric.getDisplayOrder())
                .isActive(metric.getIsActive())
                .build();
    }

    private AboutAdminDTO.AboutImpactSectionDTO mapSectionToDTO(AboutImpactSection section) {
        return AboutAdminDTO.AboutImpactSectionDTO.builder()
                .id(section.getId())
                .title(section.getTitle())
                .subtitle(section.getSubtitle())
                .isActive(section.getIsActive())
                .build();
    }
}
