package com.elsalvador.coopac.service.admin.product.impl;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;
import com.elsalvador.coopac.entity.product.ProductBadges;
import com.elsalvador.coopac.entity.product.Products;
import com.elsalvador.coopac.exception.ResourceNotFoundException;
import com.elsalvador.coopac.repository.product.ProductBadgesRepository;
import com.elsalvador.coopac.repository.product.ProductsRepository;
import com.elsalvador.coopac.service.admin.product.ManageProductBadgesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementación del servicio para gestionar badges de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ManageProductBadgesServiceImpl implements ManageProductBadgesService {

    private final ProductBadgesRepository badgesRepository;
    private final ProductsRepository productsRepository;

    @Override
    public ProductsAdminDTO.ProductBadgeDTO addBadge(UUID productId, ProductsAdminDTO.CreateProductBadgeDTO createDTO) {
        log.info("Añadiendo badge al producto: {}", productId);

        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + productId));

        ProductBadges badge = ProductBadges.builder()
                .product(product)
                .badgeText(createDTO.badgeText())
                .displayOrder(createDTO.displayOrder() != null ? createDTO.displayOrder() : 0)
                .build();

        ProductBadges savedBadge = badgesRepository.save(badge);
        log.info("Badge creado con ID: {}", savedBadge.getId());

        return mapToDTO(savedBadge);
    }

    @Override
    public ProductsAdminDTO.ProductBadgeDTO updateBadge(UUID badgeId, ProductsAdminDTO.UpdateProductBadgeDTO updateDTO) {
        log.info("Actualizando badge con ID: {}", badgeId);

        ProductBadges badge = badgesRepository.findById(badgeId)
                .orElseThrow(() -> new ResourceNotFoundException("Badge no encontrado con ID: " + badgeId));

        if (updateDTO.badgeText() != null) {
            badge.setBadgeText(updateDTO.badgeText());
        }
        if (updateDTO.displayOrder() != null) {
            badge.setDisplayOrder(updateDTO.displayOrder());
        }

        ProductBadges updatedBadge = badgesRepository.save(badge);
        log.info("Badge actualizado exitosamente");

        return mapToDTO(updatedBadge);
    }

    @Override
    public void deleteBadge(UUID badgeId) {
        log.info("Eliminando badge con ID: {}", badgeId);

        if (!badgesRepository.existsById(badgeId)) {
            throw new ResourceNotFoundException("Badge no encontrado con ID: " + badgeId);
        }

        badgesRepository.deleteById(badgeId);
        log.info("Badge eliminado exitosamente");
    }

    /**
     * Mapea entidad a DTO
     */
    private ProductsAdminDTO.ProductBadgeDTO mapToDTO(ProductBadges badge) {
        return new ProductsAdminDTO.ProductBadgeDTO(
                badge.getId(),
                badge.getBadgeText(),
                badge.getDisplayOrder()
        );
    }
}
