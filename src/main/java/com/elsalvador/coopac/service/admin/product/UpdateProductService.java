package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.UUID;

/**
 * Servicio para actualizar productos
 */
public interface UpdateProductService {

    /**
     * Actualiza un producto básico (sin relaciones)
     * @param id ID del producto a actualizar
     * @param updateDTO datos de actualización
     * @return producto actualizado
     */
    ProductsAdminDTO.ProductResponseDTO updateProduct(UUID id, ProductsAdminDTO.UpdateProductDTO updateDTO);

    /**
     * Activa o desactiva un producto
     * @param id ID del producto
     * @param isActive estado activo/inactivo
     * @return producto actualizado
     */
    ProductsAdminDTO.ProductResponseDTO toggleProductStatus(UUID id, Boolean isActive);
}
