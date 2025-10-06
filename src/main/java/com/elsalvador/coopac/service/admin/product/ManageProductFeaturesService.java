package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar características de productos
 */
public interface ManageProductFeaturesService {

    /**
     * Añade una característica a un producto
     * @param productId ID del producto
     * @param createDTO datos de la característica
     * @return característica creada
     */
    ProductsAdminDTO.ProductFeatureDTO addFeature(UUID productId, ProductsAdminDTO.CreateProductFeatureDTO createDTO);

    /**
     * Actualiza una característica
     * @param featureId ID de la característica
     * @param updateDTO datos de actualización
     * @return característica actualizada
     */
    ProductsAdminDTO.ProductFeatureDTO updateFeature(UUID featureId, ProductsAdminDTO.UpdateProductFeatureDTO updateDTO);

    /**
     * Elimina una característica
     * @param featureId ID de la característica
     */
    void deleteFeature(UUID featureId);
}
