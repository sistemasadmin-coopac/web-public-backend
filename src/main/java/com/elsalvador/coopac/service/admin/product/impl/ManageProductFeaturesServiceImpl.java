package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.ProductFeatures;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductFeaturesRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductFeaturesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar características de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductFeaturesServiceImpl implements ManageProductFeaturesService {

    private final ProductFeaturesRepository featuresRepository;
    private final ProductsRepository productsRepository;

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE}, allEntries = true)
    public ProductsAdminDTO.ProductFeatureDTO addFeature(UUID productId, ProductsAdminDTO.CreateProductFeatureDTO createDTO) {
        log.info("Añadiendo característica al producto: {}", productId);

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        ProductFeatures feature = ProductFeatures.builder()
                .product(product)
                .featureText(createDTO.featureText())
                .displayOrder(createDTO.displayOrder() != null ? createDTO.displayOrder() : 0)
                .build();

        ProductFeatures savedFeature = featuresRepository.save(feature);
        log.info("Característica creada con ID: {}", savedFeature.getId());

        return mapToDTO(savedFeature);
    }

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE}, allEntries = true)
    public ProductsAdminDTO.ProductFeatureDTO updateFeature(UUID featureId, ProductsAdminDTO.UpdateProductFeatureDTO updateDTO) {
        log.info("Actualizando característica con ID: {}", featureId);

        ProductFeatures feature = featuresRepository.findById(featureId)
                .orElseThrow(() -> new ResourceNotFoundException("Característica no encontrada con ID: " + featureId));

        if (updateDTO.featureText() != null) {
            feature.setFeatureText(updateDTO.featureText());
        }
        if (updateDTO.displayOrder() != null) {
            feature.setDisplayOrder(updateDTO.displayOrder());
        }

        ProductFeatures updatedFeature = featuresRepository.save(feature);
        log.info("Característica actualizada exitosamente");

        return mapToDTO(updatedFeature);
    }

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE}, allEntries = true)
    public void deleteFeature(UUID featureId) {
        log.info("Eliminando característica con ID: {}", featureId);

        if (!featuresRepository.existsById(featureId)) {
            throw new ResourceNotFoundException("Característica no encontrada con ID: " + featureId);
        }

        featuresRepository.deleteById(featureId);
        log.info("Característica eliminada exitosamente");
    }

    /**
     * Mapea entidad a DTO
     */
    private ProductsAdminDTO.ProductFeatureDTO mapToDTO(ProductFeatures feature) {
        return new ProductsAdminDTO.ProductFeatureDTO(
                feature.getId(),
                feature.getFeatureText(),
                feature.getDisplayOrder()
        );
    }
}
