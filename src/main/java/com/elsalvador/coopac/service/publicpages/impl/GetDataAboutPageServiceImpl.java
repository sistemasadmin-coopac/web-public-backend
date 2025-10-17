package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.about.AboutPageDTO;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.about.*;
import com.elsalvador.coopac.service.publicpages.GetDataAboutPageService;
import com.elsalvador.coopac.service.publicpages.mapper.about.AboutMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.ABOUT_PAGE_CACHE;

/**
 * Servicio para la página About que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataAboutPageServiceImpl implements GetDataAboutPageService {

    // Repositorios
    private final PageHeadersRepository pageHeadersRepository;
    private final AboutHistorySectionRepository aboutHistorySectionRepository;
    private final AboutTimelineEventRepository aboutTimelineEventRepository;
    private final AboutMissionVisionRepository aboutMissionVisionRepository;
    private final AboutValuesSectionRepository aboutValuesSectionRepository;
    private final AboutValuesRepository aboutValuesRepository;
    private final AboutImpactSectionRepository aboutImpactSectionRepository;
    private final AboutImpactMetricsRepository aboutImpactMetricsRepository;
    private final AboutBoardSectionRepository aboutBoardSectionRepository;
    private final AboutBoardMembersRepository aboutBoardMembersRepository;

    // Mapper
    private final AboutMapper aboutMapper;

    /**
     * Obtiene todos los datos necesarios para la página About
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = ABOUT_PAGE_CACHE, key = "'about'")
    public AboutPageDTO getAboutPageData() {
        log.debug("Obteniendo datos de la página About desde la base de datos");

        var header = buildHeader();
        var sections = buildSections();

        return new AboutPageDTO(header, sections);
    }

    /**
     * Construye el header de la página About usando PageHeadersRepository
     */
    private AboutPageDTO.HeaderDTO buildHeader() {
        var pageHeader = pageHeadersRepository
            .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.ABOUT.getValue())
            .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página About");
            return null;
        }

        return aboutMapper.mapHeader(pageHeader);
    }

    /**
     * Construye todas las secciones de la página About
     */
    private AboutPageDTO.SectionsDTO buildSections() {
        return new AboutPageDTO.SectionsDTO(
                buildHistorySection(),
                buildMissionVisionSection(),
                buildValuesSection(),
                buildImpactSection(),
                buildBoardSection()
        );
    }

    /**
     * Construye la sección de historia con timeline
     */
    private AboutPageDTO.HistoryDTO buildHistorySection() {
        var historySectionOpt = aboutHistorySectionRepository.findFirstByIsActiveTrue();

        if (historySectionOpt.isEmpty()) {
            log.warn("No se encontró sección de historia activa");
            return null;
        }

        var timelineEvents = aboutTimelineEventRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (timelineEvents.isEmpty()) {
            log.warn("No se encontraron eventos de timeline activos");
        }

        return aboutMapper.mapHistory(historySectionOpt.get(), timelineEvents);
    }

    /**
     * Construye la sección de misión y visión
     */
    private AboutPageDTO.MissionVisionDTO buildMissionVisionSection() {
        var missionVisionOpt = aboutMissionVisionRepository.findFirstByIsActiveTrueOrderByCreatedAtAsc();

        if (missionVisionOpt.isEmpty()) {
            log.warn("No se encontró información de misión y visión activa");
            return null;
        }

        return aboutMapper.mapMissionVision(missionVisionOpt.get());
    }

    /**
     * Construye la sección de valores
     */
    private AboutPageDTO.ValuesDTO buildValuesSection() {
        var valuesSectionOpt = aboutValuesSectionRepository.findFirstByIsActiveTrueOrderByCreatedAtAsc();

        if (valuesSectionOpt.isEmpty()) {
            log.warn("No se encontró sección de valores activa");
            return null;
        }

        var values = aboutValuesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (values.isEmpty()) {
            log.warn("No se encontraron valores activos");
        }

        return aboutMapper.mapValues(valuesSectionOpt.get(), values);
    }

    /**
     * Construye la sección de impacto
     */
    private AboutPageDTO.ImpactDTO buildImpactSection() {
        var impactSectionOpt = aboutImpactSectionRepository.findFirstByIsActiveTrueOrderByCreatedAtAsc();

        if (impactSectionOpt.isEmpty()) {
            log.warn("No se encontró sección de impacto activa");
            return null;
        }

        var metrics = aboutImpactMetricsRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (metrics.isEmpty()) {
            log.warn("No se encontraron métricas de impacto activas");
        }

        return aboutMapper.mapImpact(impactSectionOpt.get(), metrics);
    }

    /**
     * Construye la sección del consejo de administración
     */
    private AboutPageDTO.BoardDTO buildBoardSection() {
        var boardSectionOpt = aboutBoardSectionRepository.findFirstByIsActiveTrueOrderByCreatedAtAsc();

        if (boardSectionOpt.isEmpty()) {
            log.warn("No se encontró sección del consejo activa");
            return null;
        }

        var members = aboutBoardMembersRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (members.isEmpty()) {
            log.warn("No se encontraron miembros del consejo activos");
        }

        return aboutMapper.mapBoard(boardSectionOpt.get(), members);
    }
}
