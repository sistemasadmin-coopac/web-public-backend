package com.elsalvador.coopac.service.publicpages.impl;

import com.elsalvador.coopac.dto.publicpage.product.ProductPageDTO;
import com.elsalvador.coopac.enums.PageSlug;
import com.elsalvador.coopac.repository.PageHeadersRepository;
import com.elsalvador.coopac.repository.product.ProductCategoriesPageRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.publicpages.GetDataProductPageService;
import com.elsalvador.coopac.service.publicpages.mapper.product.ProductPageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.PRODUCT_PAGE_CACHE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetDataProductPageServiceImpl implements GetDataProductPageService {

    private final PageHeadersRepository pageHeadersRepository;
    private final ProductsRepository productsRepository;
    private final ProductCategoriesPageRepository productCategoriesRepository;
    private final ProductPageMapper productPageMapper;

    /**
     * Obtiene los datos para la página de productos
     * Cache se actualiza cada 6 horas
     */
    @Cacheable(value = PRODUCT_PAGE_CACHE, key = "'products'")
    public ProductPageDTO getProductsPageData() {
        log.info("Obteniendo datos para la página de productos desde la base de datos");

        var header = buildHeader();
        var sections = buildSections();

        log.info("Datos de la página de productos obtenidos exitosamente");
        return new ProductPageDTO(header, sections);
    }

    /**
     * Construye el header de la página usando datos de la base de datos
     */
    private ProductPageDTO.HeaderDTO buildHeader() {
        var pageHeader = pageHeadersRepository
            .findFirstByPageSlugAndIsActiveTrueOrderByDisplayOrderAsc(PageSlug.PRODUCTS.getValue())
            .orElse(null);

        if (pageHeader == null) {
            log.warn("No se encontró header activo para la página de productos, usando valores por defecto");
            // Fallback a valores por defecto solo si no hay datos en BD
            return createDefaultHeader();
        }

        // Usar datos de la base de datos
        var actions = new ProductPageDTO.ActionsDTO(
                new ProductPageDTO.ActionLinkDTO("Inicio", "/")
        );

        return new ProductPageDTO.HeaderDTO(
                pageHeader.getBadgeText(),
                pageHeader.getTitleMain(),
                pageHeader.getTitleHighlight(),
                pageHeader.getSubtitle(),
                actions
        );
    }

    /**
     * Crea header por defecto solo como fallback
     */
    private ProductPageDTO.HeaderDTO createDefaultHeader() {
        var actions = new ProductPageDTO.ActionsDTO(
                new ProductPageDTO.ActionLinkDTO("Inicio", "/")
        );

        return new ProductPageDTO.HeaderDTO(
                "Productos Financieros",
                "Nuestros",
                "Productos",
                "Elige la opción que mejor se adapte a ti.",
                actions
        );
    }

    /**
     * Construye las secciones de la página
     */
    private ProductPageDTO.SectionsDTO buildSections() {
        var filters = buildFilters();
        var grid = buildGrid();

        return new ProductPageDTO.SectionsDTO(filters, grid);
    }

    /**
     * Construye la sección de filtros
     */
    private ProductPageDTO.FiltersDTO buildFilters() {
        var categories = productCategoriesRepository.findByIsActiveTrueOrderByDisplayOrderAsc();
        var categoryDtos = productPageMapper.mapCategories(categories);

        // Agregar categoría "Todos" al inicio
        categoryDtos.addFirst(new ProductPageDTO.CategoryDTO("all", "Todos", "grid", 0));

        return new ProductPageDTO.FiltersDTO(
                "Buscar productos...",
                categoryDtos
        );
    }

    /**
     * Construye la grilla de productos
     */
    private ProductPageDTO.GridDTO buildGrid() {
        var products = productsRepository.findAllActiveForGrid();
        var productDtos = productPageMapper.mapProducts(products);
        var pagination = productPageMapper.createDefaultPagination(productDtos.size());

        log.info("Se encontraron {} productos activos", productDtos.size());

        return new ProductPageDTO.GridDTO(productDtos, pagination);
    }
}
