package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.financial.FinancialPageDTO;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.financial.FinancialReportCategoriesRepository;
import com.elsalvador.coopac.repository.financial.FinancialReportsRepository;
import com.elsalvador.coopac.repository.financial.FinancialsIntroRepository;
import com.elsalvador.coopac.service.publicpages.GetDataFinancialPageService;
import com.elsalvador.coopac.service.publicpages.mapper.financial.FinancialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.FINANCIAL_PAGE_CACHE;

import java.util.stream.Collectors;

/**
 * Servicio para la página Financial que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataFinancialPageServiceImpl implements GetDataFinancialPageService {

    // Repositorios
    private final PageHeadersRepository pageHeadersRepository;
    private final FinancialsIntroRepository financialsIntroRepository;
    private final FinancialReportCategoriesRepository financialReportCategoriesRepository;
    private final FinancialReportsRepository financialReportsRepository;

    // Mapper
    private final FinancialMapper financialMapper;

    /**
     * Obtiene todos los datos necesarios para la página Financial
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = FINANCIAL_PAGE_CACHE, key = "'financial'")
    public FinancialPageDTO getFinancialPageData() {
        log.debug("Obteniendo datos de la página Financial desde la base de datos");

        var header = buildHeader();
        var sections = buildSections();

        return new FinancialPageDTO(header, sections);
    }

    /**
     * Construye el header de la página Financial usando PageHeadersRepository
     */
    private FinancialPageDTO.HeaderDTO buildHeader() {
        var pageHeader = pageHeadersRepository
            .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.FINANCIALS.getValue())
            .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página Financial");
            return createDefaultHeader();
        }

        return financialMapper.mapHeader(pageHeader);
    }

    /**
     * Crea header por defecto como fallback
     */
    private FinancialPageDTO.HeaderDTO createDefaultHeader() {
        return new FinancialPageDTO.HeaderDTO(
                "Reportes Financieros",
                "Transparencia y responsabilidad en nuestra gestión financiera"
        );
    }

    /**
     * Construye todas las secciones de la página Financial
     */
    private FinancialPageDTO.SectionsDTO buildSections() {
        return new FinancialPageDTO.SectionsDTO(
                buildIntroSection(),
                buildCategoriesSection()
        );
    }

    /**
     * Construye la sección de introducción
     */
    private FinancialPageDTO.IntroDTO buildIntroSection() {
        var introOpt = financialsIntroRepository.findFirstByIsActiveTrueOrderByCreatedAtDesc();

        if (introOpt.isEmpty()) {
            log.warn("No se encontró introducción activa para la página Financial");
            return createDefaultIntro();
        }

        return financialMapper.mapIntro(introOpt.get());
    }

    /**
     * Crea introducción por defecto como fallback
     */
    private FinancialPageDTO.IntroDTO createDefaultIntro() {
        return new FinancialPageDTO.IntroDTO(
                "Consulta nuestros reportes financieros organizados por categorías, reflejando nuestro compromiso con la transparencia."
        );
    }

    /**
     * Construye la sección de categorías con sus reportes
     */
    private java.util.List<FinancialPageDTO.CategoryDTO> buildCategoriesSection() {
        var categories = financialReportCategoriesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        if (categories.isEmpty()) {
            log.warn("No se encontraron categorías de reportes activas");
            return java.util.List.of();
        }

        return categories.stream()
                .map(this::buildCategoryWithReports)
                .collect(Collectors.toList());
    }

    /**
     * Construye una categoría individual con sus reportes
     */
    private FinancialPageDTO.CategoryDTO buildCategoryWithReports(com.elsalvador.coopac.entity.financial.FinancialReportCategories category) {
        var reports = financialReportsRepository
                .findByCategoryAndIsPublicTrueAndIsActiveTrueOrderByDisplayOrderAscYearDescPublishDateDesc(category);

        var reportCount = financialReportsRepository
                .countByCategoryAndIsPublicTrueAndIsActiveTrue(category);

        if (reports.isEmpty()) {
            log.info("No se encontraron reportes para la categoría: {}", category.getName());
        }

        return financialMapper.mapCategory(category, reports, reportCount);
    }
}
