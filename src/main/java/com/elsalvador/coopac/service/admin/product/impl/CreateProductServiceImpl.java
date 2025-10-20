package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.*;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.*;
import com.elsalvador.coopac.service.admin.product.CreateProductService;
import com.elsalvador.coopac.util.SlugGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para crear productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreateProductServiceImpl implements CreateProductService {

    private final ProductsRepository productsRepository;
    private final ProductCategoriesPageRepository categoriesRepository;
    private final ProductFeaturesRepository featuresRepository;
    private final ProductActionsRepository actionsRepository;
    private final ProductBadgesRepository badgesRepository;
    private final ProductStepsRepository stepsRepository;
    private final ProductFinancialInfoRepository financialInfoRepository;
    private final SlugGeneratorUtil slugGeneratorUtil;

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE}, allEntries = true)
    public ProductsAdminDTO.ProductResponseDTO createProduct(ProductsAdminDTO.CreateProductDTO createDTO) {
        log.info("Creando nuevo producto con título: {}", createDTO.title());

        // Validar que existe la categoría
        ProductCategories category = categoriesRepository.findById(createDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + createDTO.categoryId()));

        // Generar slug único automáticamente basado en el título
        String uniqueSlug = slugGeneratorUtil.generateUniqueSlug(
                createDTO.title(),
                productsRepository::existsBySlug,
                "producto"
        );

        // Asignar displayOrder por defecto si el request no lo provee
        Integer displayOrderToUse = createDTO.displayOrder();
        if (displayOrderToUse == null) {
            Integer maxOrder = productsRepository.findMaxDisplayOrder();
            displayOrderToUse = (maxOrder != null) ? (maxOrder + 1) : 1;
            log.debug("displayOrder no proporcionado, asignando por defecto: {}", displayOrderToUse);
        }

        // Crear producto principal
        Products product = Products.builder()
                .category(category)
                .slug(uniqueSlug)
                .title(createDTO.title())
                .cardSummary(createDTO.cardSummary())
                .detailIntro(createDTO.detailIntro())
                .icon(createDTO.icon())
                .highlightText(createDTO.highlightText())
                .isFeatured(createDTO.isFeatured() != null ? createDTO.isFeatured() : false)
                .displayOrder(displayOrderToUse)
                .isActive(true)
                .build();

        Products savedProduct = productsRepository.save(product);
        log.info("Producto creado con ID: {} y slug: {}", savedProduct.getId(), savedProduct.getSlug());

        // Crear características si existen
        if (createDTO.features() != null && !createDTO.features().isEmpty()) {
            createFeatures(savedProduct, createDTO.features());
        }

        // Crear acciones si existen
        if (createDTO.actions() != null && !createDTO.actions().isEmpty()) {
            createActions(savedProduct, createDTO.actions());
        }

        // Crear badges si existen
        if (createDTO.badges() != null && !createDTO.badges().isEmpty()) {
            createBadges(savedProduct, createDTO.badges());
        }

        // Crear pasos si existen
        if (createDTO.steps() != null && !createDTO.steps().isEmpty()) {
            createSteps(savedProduct, createDTO.steps());
        }

        // Crear información financiera si existe
        if (createDTO.financialInfo() != null) {
            createFinancialInfo(savedProduct, createDTO.financialInfo());
        }

        // Recargar producto con todas las relaciones
        Products productWithRelations = productsRepository.findByIdWithAllRelations(savedProduct.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Error al recargar producto creado"));

        return mapToResponseDTO(productWithRelations);
    }

    /**
     * Crea características del producto
     */
    private void createFeatures(Products product, List<ProductsAdminDTO.CreateProductFeatureDTO> featuresDTO) {
        List<ProductFeatures> features = featuresDTO.stream()
                .map(dto -> ProductFeatures.builder()
                        .product(product)
                        .featureText(dto.featureText())
                        .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                        .build())
                .collect(Collectors.toList());

        featuresRepository.saveAll(features);
        log.debug("Creadas {} características para producto {}", features.size(), product.getId());
    }

    /**
     * Crea acciones del producto
     */
    private void createActions(Products product, List<ProductsAdminDTO.CreateProductActionDTO> actionsDTO) {
        List<ProductActions> actions = actionsDTO.stream()
                .map(dto -> ProductActions.builder()
                        .product(product)
                        .label(dto.label())
                        .actionType(dto.actionType())
                        .actionValue(dto.actionValue())
                        .isPrimary(dto.isPrimary() != null ? dto.isPrimary() : false)
                        .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                        .build())
                .collect(Collectors.toList());

        actionsRepository.saveAll(actions);
        log.debug("Creadas {} acciones para producto {}", actions.size(), product.getId());
    }

    /**
     * Crea badges del producto
     */
    private void createBadges(Products product, List<ProductsAdminDTO.CreateProductBadgeDTO> badgesDTO) {
        List<ProductBadges> badges = badgesDTO.stream()
                .map(dto -> ProductBadges.builder()
                        .product(product)
                        .badgeText(dto.badgeText())
                        .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                        .build())
                .collect(Collectors.toList());

        badgesRepository.saveAll(badges);
        log.debug("Creados {} badges para producto {}", badges.size(), product.getId());
    }

    /**
     * Crea pasos del producto
     */
    private void createSteps(Products product, List<ProductsAdminDTO.CreateProductStepDTO> stepsDTO) {
        List<ProductSteps> steps = stepsDTO.stream()
                .map(dto -> ProductSteps.builder()
                        .product(product)
                        .title(dto.title())
                        .description(dto.description())
                        .icon(dto.icon())
                        .estimatedTime(dto.estimatedTime())
                        .displayOrder(dto.displayOrder() != null ? dto.displayOrder() : 0)
                        .isActive(true)
                        .build())
                .collect(Collectors.toList());

        stepsRepository.saveAll(steps);
        log.debug("Creados {} pasos para producto {}", steps.size(), product.getId());
    }

    /**
     * Crea información financiera del producto
     */
    private void createFinancialInfo(Products product, ProductsAdminDTO.CreateProductFinancialInfoDTO financialDTO) {
        ProductFinancialInfo financialInfo = ProductFinancialInfo.builder()
                .product(product)
                .interestRateText(financialDTO.interestRateText())
                .interestRateValue(financialDTO.interestRateValue())
                .termText(financialDTO.termText())
                .termMonths(financialDTO.termMonths())
                .minAmountText(financialDTO.minAmountText())
                .minAmount(financialDTO.minAmount())
                .currency(financialDTO.currency())
                .notes(financialDTO.notes())
                .build();

        financialInfoRepository.save(financialInfo);
        log.debug("Creada información financiera para producto {}", product.getId());
    }

    /**
     * Mapea entidad a DTO de respuesta
     */
    private ProductsAdminDTO.ProductResponseDTO mapToResponseDTO(Products product) {
        return new ProductsAdminDTO.ProductResponseDTO(
                product.getId(),
                product.getCategory().getId(),
                product.getSlug(),
                product.getTitle(),
                product.getCardSummary(),
                product.getDetailIntro(),
                product.getIcon(),
                product.getHighlightText(),
                product.getIsFeatured(),
                product.getDisplayOrder(),
                product.getIsActive(),
                mapFeatures(product.getFeatures()),
                mapActions(product.getActions()),
                mapBadges(product.getBadges()),
                mapSteps(product.getSteps()),
                mapFinancialInfo(product.getFinancialInfo())
        );
    }

    // ...métodos de mapeo iguales que en GetProductsAdminServiceImpl...
    private List<ProductsAdminDTO.ProductFeatureDTO> mapFeatures(List<ProductFeatures> features) {
        if (features == null) return List.of();
        return features.stream()
                .map(feature -> new ProductsAdminDTO.ProductFeatureDTO(
                        feature.getId(),
                        feature.getFeatureText(),
                        feature.getDisplayOrder()
                ))
                .collect(Collectors.toList());
    }

    private List<ProductsAdminDTO.ProductActionDTO> mapActions(List<ProductActions> actions) {
        if (actions == null) return List.of();
        return actions.stream()
                .map(action -> new ProductsAdminDTO.ProductActionDTO(
                        action.getId(),
                        action.getLabel(),
                        action.getActionType(),
                        action.getActionValue(),
                        action.getIsPrimary(),
                        action.getDisplayOrder()
                ))
                .collect(Collectors.toList());
    }

    private List<ProductsAdminDTO.ProductBadgeDTO> mapBadges(List<ProductBadges> badges) {
        if (badges == null) return List.of();
        return badges.stream()
                .map(badge -> new ProductsAdminDTO.ProductBadgeDTO(
                        badge.getId(),
                        badge.getBadgeText(),
                        badge.getDisplayOrder()
                ))
                .collect(Collectors.toList());
    }

    private List<ProductsAdminDTO.ProductStepDTO> mapSteps(List<ProductSteps> steps) {
        if (steps == null) return List.of();
        return steps.stream()
                .map(step -> new ProductsAdminDTO.ProductStepDTO(
                        step.getId(),
                        step.getTitle(),
                        step.getDescription(),
                        step.getIcon(),
                        step.getEstimatedTime(),
                        step.getDisplayOrder(),
                        step.getIsActive()
                ))
                .collect(Collectors.toList());
    }

    private ProductsAdminDTO.ProductFinancialInfoDTO mapFinancialInfo(ProductFinancialInfo financialInfo) {
        if (financialInfo == null) return null;
        return new ProductsAdminDTO.ProductFinancialInfoDTO(
                financialInfo.getInterestRateText(),
                financialInfo.getInterestRateValue(),
                financialInfo.getTermText(),
                financialInfo.getTermMonths(),
                financialInfo.getMinAmountText(),
                financialInfo.getMinAmount(),
                financialInfo.getCurrency(),
                financialInfo.getNotes()
        );
    }
}
