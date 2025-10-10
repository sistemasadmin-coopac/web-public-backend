package com.elsalvador.coopac.service.admin.product;

import com.elsalvador.coopac.dto.admin.ProductsAdminDTO;

import java.util.UUID;

/**
 * Servicio para gestionar acciones de productos
 */
public interface ManageProductActionsService {

    /**
     * Añade una acción a un producto
     * @param productId ID del producto
     * @param createDTO datos de la acción
     * @return acción creada
     */
    ProductsAdminDTO.ProductActionDTO addAction(UUID productId, ProductsAdminDTO.CreateProductActionDTO createDTO);

    /**
     * Actualiza una acción
     * @param actionId ID de la acción
     * @param updateDTO datos de actualización
     * @return acción actualizada
     */
    ProductsAdminDTO.ProductActionDTO updateAction(UUID actionId, ProductsAdminDTO.UpdateProductActionDTO updateDTO);

    /**
     * Elimina una acción
     * @param actionId ID de la acción
     */
    void deleteAction(UUID actionId);
}
