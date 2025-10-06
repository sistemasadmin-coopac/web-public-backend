package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar información financiera de productos
 */
public interface ManageProductFinancialInfoService {

    /**
     * Crea información financiera para un producto
     * @param productId ID del producto
     * @param createDTO datos de información financiera
     * @return información financiera creada
     */
    ProductsAdminDTO.ProductFinancialInfoDTO createFinancialInfo(UUID productId, ProductsAdminDTO.CreateProductFinancialInfoDTO createDTO);

    /**
     * Actualiza información financiera de un producto
     * @param productId ID del producto
     * @param updateDTO datos de actualización
     * @return información financiera actualizada
     */
    ProductsAdminDTO.ProductFinancialInfoDTO updateFinancialInfo(UUID productId, ProductsAdminDTO.UpdateProductFinancialInfoDTO updateDTO);

    /**
     * Elimina información financiera de un producto
     * @param productId ID del producto
     */
    void deleteFinancialInfo(UUID productId);
}
