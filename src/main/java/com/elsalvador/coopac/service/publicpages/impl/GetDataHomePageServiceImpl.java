package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.home.HomePageDTO;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.*;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.publicpages.GetDataHomePageService;
import com.elsalvador.coopac.service.publicpages.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.HOME_PAGE_CACHE;

/**
 * Servicio para la página de inicio que orquesta todos los datos necesarios
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataHomePageServiceImpl implements GetDataHomePageService {

    // Repositorios
    private final PageHeadersRepository pageHeadersRepository;
    private final PageHeaderCardsRepository pageHeaderCardsRepository;
    private final HomePromotionsSectionRepository homePromotionsSectionRepository;
    private final HomePromotionsRepository homePromotionsRepository;
    private final ProductsRepository productsRepository;
    private final HomeStatsSectionRepository homeStatsSectionRepository;
    private final HomeStatsRepository homeStatsRepository;
    private final HomeCtaBlocksRepository homeCtaBlocksRepository;
    private final SiteSettingsRepository siteSettingsRepository;

    // Mappers
    private final PageHeaderMapper pageHeaderMapper;
    private final PromotionsMapper promotionsMapper;
    private final ProductsMapper productsMapper;
    private final StatsMapper statsMapper;
    private final CtaMapper ctaMapper;

    /**
     * Obtiene todos los datos necesarios para la página de inicio
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = HOME_PAGE_CACHE, key = "'home'")
    public HomePageDTO getHomePageData() {
        log.debug("Obteniendo datos de la página de inicio desde la base de datos");

        var header = buildHeader();
        var sections = buildSections();

        return new HomePageDTO(header, sections);
    }

    /**
     * Construye el header de la página con sus tarjetas
     */
    private HomePageDTO.HeaderDTO buildHeader() {
        var pageHeader = pageHeadersRepository
            .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.HOME.getValue())
            .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página home");
            return null;
        }

        var headerCards = pageHeaderCardsRepository
            .findByHeaderIdAndIsActiveTrueOrderByDisplayOrderAsc(pageHeader.getId());

        return pageHeaderMapper.toHeaderDTO(pageHeader, headerCards);
    }

    /**
     * Construye todas las secciones de la página
     */
    private HomePageDTO.SectionsDTO buildSections() {
        return new HomePageDTO.SectionsDTO(
            buildPromotionsSection(),
            buildProductsSection(),
            buildStatsSection(),
            buildCtasSection()
        );
    }

    /**
     * Construye la sección de promociones
     */
    private HomePageDTO.PromotionsSectionDTO buildPromotionsSection() {
        var promotionsSection = homePromotionsSectionRepository
            .findFirstByIsActiveTrue()
            .orElse(null);

        if (promotionsSection == null) {
            log.warn("No se encontró sección de promociones activa");
            return null;
        }

        var promotions = homePromotionsRepository
            .findBySectionIdAndIsActiveTrueOrderByIsFeaturedDescDisplayOrderAsc(promotionsSection.getId());

        return promotionsMapper.toPromotionsSectionDTO(promotionsSection, promotions);
    }

    /**
     * Construye la sección de productos
     */
    private HomePageDTO.ProductsSectionDTO buildProductsSection() {
        var featuredProducts = productsRepository.findFeaturedCards();

        if (featuredProducts.isEmpty()) {
            log.warn("No se encontraron productos destacados");
            return null;
        }

        return productsMapper.toProductsSectionDTO(featuredProducts);
    }

    /**
     * Construye la sección de estadísticas
     */
    private HomePageDTO.StatsSectionDTO buildStatsSection() {
        var statsSection = homeStatsSectionRepository
            .findFirstByIsActiveTrue()
            .orElse(null);

        if (statsSection == null) {
            log.warn("No se encontró sección de estadísticas activa");
            return null;
        }

        var stats = homeStatsRepository.findByIsActiveTrueOrderByDisplayOrderAsc();

        return statsMapper.toStatsSectionDTO(statsSection, stats);
    }

    /**
     * Construye la sección de CTAs
     */
    private HomePageDTO.CtasSectionDTO buildCtasSection() {
        var allCtas = homeCtaBlocksRepository
            .findByIsActiveTrueOrderByPositionAscDisplayOrderAsc();

        var midCtas = allCtas.stream()
            .filter(cta -> "mid".equals(cta.getPosition()))
            .toList();

        var finalCtas = allCtas.stream()
            .filter(cta -> "final".equals(cta.getPosition()))
            .toList();

        return ctaMapper.toCtasSectionDTO(midCtas, finalCtas);
    }
}
