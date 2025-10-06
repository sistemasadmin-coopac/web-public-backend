package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.UpdateProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para actualizar productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UpdateProductServiceImpl implements UpdateProductService {

    private final ProductsRepository productsRepository;
    private final GetProductsAdminServiceImpl getProductsService;

    @Override
    public ProductsAdminDTO.ProductResponseDTO updateProduct(UUID id, ProductsAdminDTO.UpdateProductDTO updateDTO) {
        log.info("Actualizando producto con ID: {}", id);

        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Actualizar solo los campos proporcionados
        if (updateDTO.title() != null) {
            product.setTitle(updateDTO.title());
        }
        if (updateDTO.cardSummary() != null) {
            product.setCardSummary(updateDTO.cardSummary());
        }
        if (updateDTO.detailIntro() != null) {
            product.setDetailIntro(updateDTO.detailIntro());
        }
        if (updateDTO.icon() != null) {
            product.setIcon(updateDTO.icon());
        }
        if (updateDTO.highlightText() != null) {
            product.setHighlightText(updateDTO.highlightText());
        }
        if (updateDTO.isFeatured() != null) {
            product.setIsFeatured(updateDTO.isFeatured());
        }
        if (updateDTO.displayOrder() != null) {
            product.setDisplayOrder(updateDTO.displayOrder());
        }
        if (updateDTO.isActive() != null) {
            product.setIsActive(updateDTO.isActive());
        }

        Products updatedProduct = productsRepository.save(product);
        log.info("Producto actualizado exitosamente con ID: {}", id);

        // Retornar producto completo con todas las relaciones
        return getProductsService.getProductById(updatedProduct.getId());
    }

    @Override
    public ProductsAdminDTO.ProductResponseDTO toggleProductStatus(UUID id, Boolean isActive) {
        log.info("Cambiando estado del producto {} a: {}", id, isActive);

        Products product = productsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        product.setIsActive(isActive);
        productsRepository.save(product);

        log.info("Estado del producto {} actualizado a: {}", id, isActive);

        return getProductsService.getProductById(id);
    }
}
