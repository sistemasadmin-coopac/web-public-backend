package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar badges de productos
 */
public interface ManageProductBadgesService {

    /**
     * Añade un badge a un producto
     * @param productId ID del producto
     * @param createDTO datos del badge
     * @return badge creado
     */
    ProductsAdminDTO.ProductBadgeDTO addBadge(UUID productId, ProductsAdminDTO.CreateProductBadgeDTO createDTO);

    /**
     * Actualiza un badge
     * @param badgeId ID del badge
     * @param updateDTO datos de actualización
     * @return badge actualizado
     */
    ProductsAdminDTO.ProductBadgeDTO updateBadge(UUID badgeId, ProductsAdminDTO.UpdateProductBadgeDTO updateDTO);

    /**
     * Elimina un badge
     * @param badgeId ID del badge
     */
    void deleteBadge(UUID badgeId);
}
