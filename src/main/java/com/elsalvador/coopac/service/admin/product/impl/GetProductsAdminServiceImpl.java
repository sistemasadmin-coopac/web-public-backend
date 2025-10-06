package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.*;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.*;
import com.elsalvador.coopac.service.admin.product.GetProductsAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para obtener productos en administración
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GetProductsAdminServiceImpl implements GetProductsAdminService {

    private final ProductsRepository productsRepository;
    private final ProductFeaturesRepository featuresRepository;
    private final ProductActionsRepository actionsRepository;
    private final ProductBadgesRepository badgesRepository;
    private final ProductStepsRepository stepsRepository;
    private final ProductFinancialInfoRepository financialInfoRepository;

    @Override
    public List<ProductsAdminDTO.ProductListItemDTO> getAllActiveProducts() {
        log.info("Obteniendo todos los productos activos para administración");

        List<Products> products = productsRepository.findAllActiveWithCategory();

        return products.stream()
                .map(this::mapToListItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsAdminDTO.ProductListItemDTO> getAllProducts() {
        log.info("Obteniendo todos los productos para administración");

        List<Products> products = productsRepository.findAllWithCategory();

        return products.stream()
                .map(this::mapToListItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsAdminDTO.ProductListItemDTO> getProductsByCategory(UUID categoryId) {
        log.info("Obteniendo productos por categoría: {}", categoryId);

        List<Products> products = productsRepository.findByCategoryIdWithCategory(categoryId);

        return products.stream()
                .map(this::mapToListItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductsAdminDTO.ProductResponseDTO getProductById(UUID id) {
        log.info("Obteniendo producto completo por ID: {}", id);

        // Cargar la entidad base con categoría y financial info (sin colecciones múltiples)
        Products product = productsRepository.findByIdWithAllRelations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Cargar las colecciones de manera separada para evitar MultipleBagFetchException
        loadProductCollections(product);

        return mapToResponseDTO(product);
    }

    @Override
    public ProductsAdminDTO.ProductResponseDTO getProductBySlug(String slug) {
        log.info("Obteniendo producto completo por slug: {}", slug);

        // Cargar la entidad base con categoría y financial info (sin colecciones múltiples)
        Products product = productsRepository.findBySlugWithAllRelations(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con slug: " + slug));

        // Cargar las colecciones de manera separada para evitar MultipleBagFetchException
        loadProductCollections(product);

        return mapToResponseDTO(product);
    }

    @Override
    public List<ProductsAdminDTO.ProductResponseDTO> getAllProductsComplete() {
        log.info("Obteniendo todos los productos completos para administración (OPTIMIZADO)");

        List<Products> products = productsRepository.findAllWithCategory();

        return products.stream()
                .map(this::loadProductWithAllRelations)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductsAdminDTO.ProductResponseDTO> getAllActiveProductsComplete() {
        log.info("Obteniendo todos los productos activos completos para administración (OPTIMIZADO)");

        List<Products> products = productsRepository.findAllActiveWithCategory();

        return products.stream()
                .map(this::loadProductWithAllRelations)
                .collect(Collectors.toList());
    }

    /**
     * Carga un producto con todas sus relaciones de manera eficiente
     */
    private ProductsAdminDTO.ProductResponseDTO loadProductWithAllRelations(Products product) {
        // Cargar las colecciones por separado para evitar MultipleBagFetchException
        List<ProductFeatures> features = featuresRepository.findEntitiesByProductIdOrderByDisplayOrderAsc(product.getId());
        List<ProductActions> actions = actionsRepository.findByProductIdOrderByDisplayOrderAsc(product.getId());
        List<ProductBadges> badges = badgesRepository.findEntitiesByProductIdOrderByDisplayOrderAsc(product.getId());
        List<ProductSteps> steps = stepsRepository.findEntitiesByProductIdAndIsActiveTrueOrderByDisplayOrderAsc(product.getId());
        ProductFinancialInfo financialInfo = financialInfoRepository.findEntityByProductId(product.getId()).orElse(null);

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
                mapFeaturesList(features),
                mapActionsList(actions),
                mapBadgesList(badges),
                mapStepsList(steps),
                mapFinancialInfo(financialInfo)
        );
    }

    /**
     * Carga todas las colecciones de un producto de manera segura
     */
    private void loadProductCollections(Products product) {
        UUID productId = product.getId();

        // Cargar features
        productsRepository.findByIdWithFeatures(productId)
                .ifPresent(p -> product.setFeatures(p.getFeatures()));

        // Cargar actions
        productsRepository.findByIdWithActions(productId)
                .ifPresent(p -> product.setActions(p.getActions()));

        // Cargar badges
        productsRepository.findByIdWithBadges(productId)
                .ifPresent(p -> product.setBadges(p.getBadges()));

        // Cargar steps
        productsRepository.findByIdWithSteps(productId)
                .ifPresent(p -> product.setSteps(p.getSteps()));
    }

    /**
     * Mapea entidad a DTO de lista simplificada
     */
    private ProductsAdminDTO.ProductListItemDTO mapToListItemDTO(Products product) {
        return new ProductsAdminDTO.ProductListItemDTO(
                product.getId(),
                product.getCategory().getName(),
                product.getSlug(),
                product.getTitle(),
                product.getHighlightText(),
                product.getIsFeatured(),
                product.getDisplayOrder(),
                product.getIsActive()
        );
    }

    /**
     * Mapea entidad completa a DTO de respuesta
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

    /**
     * Mapea características del producto
     */
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

    /**
     * Mapea acciones del producto
     */
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

    /**
     * Mapea badges del producto
     */
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

    /**
     * Mapea pasos del producto
     */
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

    /**
     * Mapea información financiera del producto
     */
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

    /**
     * Mapea características del producto (para listas cargadas por separado)
     */
    private List<ProductsAdminDTO.ProductFeatureDTO> mapFeaturesList(List<ProductFeatures> features) {
        if (features == null) return List.of();

        return features.stream()
                .map(feature -> new ProductsAdminDTO.ProductFeatureDTO(
                        feature.getId(),
                        feature.getFeatureText(),
                        feature.getDisplayOrder()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Mapea acciones del producto (para listas cargadas por separado)
     */
    private List<ProductsAdminDTO.ProductActionDTO> mapActionsList(List<ProductActions> actions) {
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

    /**
     * Mapea badges del producto (para listas cargadas por separado)
     */
    private List<ProductsAdminDTO.ProductBadgeDTO> mapBadgesList(List<ProductBadges> badges) {
        if (badges == null) return List.of();

        return badges.stream()
                .map(badge -> new ProductsAdminDTO.ProductBadgeDTO(
                        badge.getId(),
                        badge.getBadgeText(),
                        badge.getDisplayOrder()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Mapea pasos del producto (para listas cargadas por separado)
     */
    private List<ProductsAdminDTO.ProductStepDTO> mapStepsList(List<ProductSteps> steps) {
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
}
