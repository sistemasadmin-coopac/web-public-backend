package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.*;
import com.elsalvador.coopac.service.admin.product.DeleteProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elsalvador.coopac.config.CacheConfig.*;

import java.util.UUID;

/**
 * Implementación del servicio para eliminar productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DeleteProductServiceImpl implements DeleteProductService {

    private final ProductsRepository productsRepository;
    private final ProductFeaturesRepository productFeaturesRepository;
    private final ProductActionsRepository productActionsRepository;
    private final ProductBadgesRepository productBadgesRepository;
    private final ProductStepsRepository productStepsRepository;
    private final ProductFinancialInfoRepository productFinancialInfoRepository;

    @Override
    @CacheEvict(value = {PRODUCT_PAGE_CACHE, PRODUCT_DETAIL_CACHE, HOME_PAGE_CACHE}, allEntries = true)
    public void deleteProduct(UUID productId) {
        log.info("Eliminando producto con ID: {}", productId);

        // Verificar que el producto existe
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        // Eliminar todas las relaciones del producto en orden correcto
        deleteProductRelations(productId);

        // Finalmente eliminar el producto
        productsRepository.delete(product);

        log.info("Producto eliminado exitosamente con ID: {}", productId);
    }

    /**
     * Elimina todas las relaciones del producto
     */
    private void deleteProductRelations(UUID productId) {
        log.debug("Eliminando relaciones del producto con ID: {}", productId);

        // Eliminar features
        productFeaturesRepository.deleteByProductId(productId);
        log.debug("Features eliminadas para producto: {}", productId);

        // Eliminar actions
        productActionsRepository.deleteByProductId(productId);
        log.debug("Actions eliminadas para producto: {}", productId);

        // Eliminar badges
        productBadgesRepository.deleteByProductId(productId);
        log.debug("Badges eliminados para producto: {}", productId);

        // Eliminar steps
        productStepsRepository.deleteByProductId(productId);
        log.debug("Steps eliminados para producto: {}", productId);

        // Eliminar financial info
        productFinancialInfoRepository.deleteByProductId(productId);
        log.debug("Financial info eliminada para producto: {}", productId);
    }
}
